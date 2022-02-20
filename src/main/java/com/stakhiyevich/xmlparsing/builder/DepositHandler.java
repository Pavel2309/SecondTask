package com.stakhiyevich.xmlparsing.builder;

import com.stakhiyevich.xmlparsing.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.YearMonth;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class DepositHandler extends DefaultHandler {

    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';

    private final Set<Deposit> depositSet;
    public EnumSet<DepositXmlTag> depositXmlTags;
    private Deposit currentDeposit;
    private DepositXmlTag currentTag;

    public DepositHandler() {
        depositSet = new HashSet<>();
        depositXmlTags = EnumSet.range(DepositXmlTag.NAME, DepositXmlTag.TIME_CONSTRAINT);
    }

    public Set<Deposit> getDepositSet() {
        return depositSet;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        String demandDepositTag = DepositXmlTag.DEMAND_DEPOSIT.getValue();
        String timeDepositTag = DepositXmlTag.TIME_DEPOSIT.getValue();

        if (demandDepositTag.equals(qName) || timeDepositTag.equals(qName)) {
            currentDeposit = demandDepositTag.equals(qName) ? new DemandDeposit() : new TimeDeposit();

            String depositId = attributes.getValue(0);
            boolean autoRenewable = Boolean.parseBoolean(attributes.getValue(1));

            currentDeposit.setId(depositId);
            currentDeposit.setAutoRenewable(autoRenewable || Deposit.DEFAULT_AUTO_RENEW);

        } else {
            String formattedName = formatName(qName);
            DepositXmlTag tag = DepositXmlTag.valueOf(formattedName);

            if (depositXmlTags.contains(tag)) {
                currentTag = tag;
            }

        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String demandDepositTag = DepositXmlTag.DEMAND_DEPOSIT.getValue();
        String timeDepositTag = DepositXmlTag.TIME_DEPOSIT.getValue();

        if (demandDepositTag.equals(qName) || timeDepositTag.equals(qName)) {
            depositSet.add(currentDeposit);
            currentDeposit = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length);

        if (currentTag != null) {
            switch (currentTag) {
                case NAME -> currentDeposit.setName(data);
                case COUNTRY -> {
                    String formattedName = formatName(data);
                    currentDeposit.setCountry(Country.valueOf(formattedName));
                }
                case DEPOSITOR -> currentDeposit.setDepositor(data);
                case AMOUNT -> currentDeposit.setAmount(Integer.parseInt(data));
                case PROFITABILITY -> currentDeposit.setProfitability(Integer.parseInt(data));
                case TIME_CONSTRAINT -> currentDeposit.setTimeConstraint(YearMonth.parse(data));
                case DEMAND_DEPOSIT -> {
                    DemandDeposit demandDeposit = (DemandDeposit) currentDeposit;
                    String formattedName = formatName(data);
                    demandDeposit.setDemandDepositType(DemandDepositType.valueOf(formattedName));
                }
                case TIME_DEPOSIT -> {
                    TimeDeposit timeDeposit = (TimeDeposit) currentDeposit;
                    timeDeposit.setPenalty(Integer.parseInt(data));
                }
                default -> throw new EnumConstantNotPresentException(
                        currentTag.getDeclaringClass(), currentTag.name()
                );

            }
        }
        currentTag = null;
    }

    private String formatName(String string) {
        return string.strip().replace(HYPHEN, UNDERSCORE).toUpperCase();
    }

}
