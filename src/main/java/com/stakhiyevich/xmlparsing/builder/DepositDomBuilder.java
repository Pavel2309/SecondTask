package com.stakhiyevich.xmlparsing.builder;

import com.stakhiyevich.xmlparsing.entity.*;
import com.stakhiyevich.xmlparsing.exception.DepositEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.YearMonth;
import java.util.Locale;

public class DepositDomBuilder extends AbstractDepositBuilder {

    private static final Logger logger = LogManager.getLogger();
    private final DocumentBuilder docBuilder;

    public DepositDomBuilder() throws DepositEntityException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("dom configuration failed", e);
            throw new DepositEntityException("dom configuration failed", e);
        }
    }

    @Override
    public void buildDeposits(String filePath) throws DepositEntityException {
        Document doc;
        try {
            doc = docBuilder.parse(filePath);
            Element root = doc.getDocumentElement();
            createDeposits(root, DepositXmlTag.DEMAND_DEPOSIT);
            createDeposits(root, DepositXmlTag.TIME_DEPOSIT);
        } catch (IOException | SAXException | DepositEntityException e) {
            logger.error("error while parting by dom", e);
            throw new DepositEntityException("error while parting by dom", e);
        }
    }

    public void createDeposits(Element root, DepositXmlTag depositXmlTag) throws DepositEntityException {
        NodeList depositList = root.getElementsByTagName(depositXmlTag.toString());
        for (int i = 0; i < depositList.getLength(); i++) {
            Element depositElement = (Element) depositList.item(i);
            Deposit deposit = buildDeposit(depositElement, depositXmlTag);
            depositSet.add(deposit);
        }
    }

    private Deposit buildDeposit(Element depositElement, DepositXmlTag depositXmlTag) throws DepositEntityException {

        Deposit deposit;

        String id = depositElement.getAttribute(DepositXmlTag.ID.toString());
        boolean isAutoRenewable = Boolean.parseBoolean(depositElement.getAttribute(DepositXmlTag.AUTO_RENEW.toString()));
        String name = depositElement.getAttribute(DepositXmlTag.NAME.toString());
        Country country = Country.extractCountryFromString(depositElement.getAttribute(DepositXmlTag.COUNTRY.toString()));
        String depositor = depositElement.getAttribute(DepositXmlTag.DEPOSITOR.toString());
        int amount = Integer.parseInt(depositElement.getAttribute(DepositXmlTag.AMOUNT.toString()));
        int profitability = Integer.parseInt(depositElement.getAttribute(DepositXmlTag.PROFITABILITY.toString()));
        YearMonth timeConstraint = YearMonth.parse(depositElement.getAttribute(DepositXmlTag.TIME_CONSTRAINT.toString()));

        switch (depositXmlTag) {
            case DEMAND_DEPOSIT -> {
                deposit = new DemandDeposit();
                String type = getElementTextContent(depositElement, DepositXmlTag.DEMAND_DEPOSIT.getValue());
                ((DemandDeposit) deposit).setDemandDepositType(DemandDepositType.valueOf(type.toUpperCase(Locale.ROOT)));

            }
            case TIME_DEPOSIT -> {
                deposit = new TimeDeposit();
                ((TimeDeposit) deposit).setPenalty(Integer.parseInt(
                        getElementTextContent(depositElement,
                                DepositXmlTag.PENALTY.getValue())));
            }
            default -> {
                throw new DepositEntityException("invalid tag");
            }
        }

        deposit.setId(id);
        deposit.setAutoRenewable(isAutoRenewable);
        deposit.setName(name);
        deposit.setCountry(country);
        deposit.setDepositor(depositor);
        deposit.setAmount(amount);
        deposit.setProfitability(profitability);
        deposit.setTimeConstraint(timeConstraint);

        return deposit;
    }

    private String getElementTextContent(Element element, String elementName) {
        NodeList nodeList = element.getElementsByTagName(elementName);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }
}
