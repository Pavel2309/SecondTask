package com.stakhiyevich.xmlparsing.builder;

import com.stakhiyevich.xmlparsing.entity.*;
import com.stakhiyevich.xmlparsing.exception.DepositDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;

public class DepositStaxBuilder extends AbstractDepositBuilder {

    private static final Logger logger = LogManager.getLogger();
    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';

    private final XMLInputFactory inputFactory;

    public DepositStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildDeposits(String filename) throws DepositDataException {
        XMLStreamReader reader;
        String name;

        ClassLoader loader = getClass().getClassLoader();
        URL resource = loader.getResource(filename);

        try (FileInputStream inputStream = new FileInputStream(resource.getFile())) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(DepositXmlTag.DEMAND_DEPOSIT.getValue()) || name.equals(DepositXmlTag.TIME_DEPOSIT.getValue())) {
                        Deposit deposit = buildDeposit(reader);
                        depositSet.add(deposit);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            logger.error("error during stax parsing: {}", filename);
        }

    }

    private Deposit buildDeposit(XMLStreamReader reader) throws XMLStreamException {

        Deposit currentDeposit = reader.getLocalName().equals(DepositXmlTag.DEMAND_DEPOSIT.getValue()) ? new DemandDeposit() : new TimeDeposit();

        currentDeposit.setId(reader.getAttributeValue(null, DepositXmlTag.ID.getValue()));

        String autoRenew = reader.getAttributeValue(null, DepositXmlTag.AUTO_RENEW.getValue());
        if (autoRenew == null) {
            currentDeposit.setAutoRenewable(Deposit.DEFAULT_AUTO_RENEW);
        } else {
            currentDeposit.setAutoRenewable(Boolean.parseBoolean(autoRenew));
        }

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName().toUpperCase().replace(HYPHEN, UNDERSCORE);
                    buildDepositProperties(reader, name, currentDeposit);
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (name.equals(DepositXmlTag.DEMAND_DEPOSIT.getValue()) || name.equals(DepositXmlTag.TIME_DEPOSIT.getValue())) {
                        return currentDeposit;
                    }
                }
            }
        }

        throw new XMLStreamException("unknown tag");
    }

    private void buildDepositProperties(XMLStreamReader reader, String name, Deposit deposit) throws XMLStreamException {
        String data = getXMLText(reader);
        switch (DepositXmlTag.valueOf(name)) {
            case NAME -> {
                deposit.setName(data);
            }
            case COUNTRY -> {
                deposit.setCountry(DepositCountry.valueOf(data.toUpperCase()));
            }
            case DEPOSITOR -> {
                deposit.setDepositor(data);
            }
            case AMOUNT -> {
                deposit.setAmount(Integer.parseInt(data));
            }
            case PROFITABILITY -> {
                deposit.setProfitability(Integer.parseInt(data));
            }
            case TIME_CONSTRAINT -> {
                deposit.setTimeConstraint(YearMonth.parse(data));
            }
            case TYPE -> {
                ((DemandDeposit) deposit).setDemandDepositType(DemandDepositType.valueOf(data.toUpperCase().replace(HYPHEN, UNDERSCORE)));
            }
            case PENALTY -> {
                ((TimeDeposit) deposit).setPenalty(Integer.parseInt(data));
            }
            default -> {
                logger.error("unknown element");
                throw new XMLStreamException("unknown element");
            }

        }
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
