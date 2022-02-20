package com.stakhiyevich.xmlparsing.builder;

import com.stakhiyevich.xmlparsing.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.YearMonth;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class DepositHandler extends DefaultHandler {

    private static final Logger logger = LogManager.getLogger();

    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';

    private final Set<Deposit> depositSet;
    public EnumSet<DepositXmlTag> textXmlTag;

    private Deposit currentDeposit;
    private DepositXmlTag currentXmlTag;

    public DepositHandler() {
        depositSet = new HashSet<>();
        textXmlTag = EnumSet.range(DepositXmlTag.NAME, DepositXmlTag.PENALTY);
    }

    public Set<Deposit> getDepositSet() {
        return depositSet;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equals(DepositXmlTag.DEMAND_DEPOSIT.getValue())
                || qName.equals(DepositXmlTag.TIME_DEPOSIT.getValue())) {

            currentDeposit = qName.equals(DepositXmlTag.DEMAND_DEPOSIT.getValue()) ? new DemandDeposit() : new TimeDeposit();

            currentDeposit.setId(attributes.getValue(0));
            currentDeposit.setAutoRenewable(Boolean.parseBoolean(attributes.getValue(1)) || Deposit.DEFAULT_AUTO_RENEW);

        } else {
            DepositXmlTag temp = DepositXmlTag.valueOf(qName.toUpperCase().replace(HYPHEN, UNDERSCORE));
            if (textXmlTag.contains(temp)) {
                currentXmlTag = temp;
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

        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case NAME -> currentDeposit.setName(data);
                case COUNTRY -> {
                    currentDeposit.setCountry(DepositCountry.extractCountryFromString(data));
                }
                case DEPOSITOR -> currentDeposit.setDepositor(data);
                case AMOUNT -> currentDeposit.setAmount(Integer.parseInt(data));
                case PROFITABILITY -> currentDeposit.setProfitability(Integer.parseInt(data));
                case TIME_CONSTRAINT -> currentDeposit.setTimeConstraint(YearMonth.parse(data));
                case TYPE -> {
                    DemandDeposit demandDeposit = (DemandDeposit) currentDeposit;;
                    demandDeposit.setDemandDepositType(DemandDepositType.extractTypeFromString(data));
                }
                case PENALTY -> {
                    TimeDeposit timeDeposit = (TimeDeposit) currentDeposit;
                    timeDeposit.setPenalty(Integer.parseInt(data));
                }
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name()
                );

            }
        }
        currentXmlTag = null;
    }
}
