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
import java.net.URL;
import java.time.YearMonth;

import static com.stakhiyevich.xmlparsing.entity.Deposit.DEFAULT_AUTO_RENEW;

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
            ClassLoader loader = getClass().getClassLoader();
            URL resource = loader.getResource(filePath);

            doc = docBuilder.parse(resource.getFile());
            Element root = doc.getDocumentElement();

            createDeposits(root, DepositXmlTag.DEMAND_DEPOSIT);
            createDeposits(root, DepositXmlTag.TIME_DEPOSIT);
        } catch (IOException | SAXException | DepositEntityException e) {
            logger.error("error while parsing using dom", e);
            throw new DepositEntityException("error while parsing using dom", e);
        }
    }

    public void createDeposits(Element root, DepositXmlTag depositXmlTag) throws DepositEntityException {
        NodeList depositList = root.getElementsByTagName(depositXmlTag.getValue());
        for (int i = 0; i < depositList.getLength(); i++) {
            Element depositElement = (Element) depositList.item(i);
            Deposit deposit = buildDeposit(depositElement, depositXmlTag);
            depositSet.add(deposit);
        }
    }

    private Deposit buildDeposit(Element depositElement, DepositXmlTag depositXmlTag) throws DepositEntityException {

        Deposit deposit;

        String id = depositElement.getAttribute(DepositXmlTag.ID.toString());
        boolean isAutoRenewable = depositElement.hasAttribute(DepositXmlTag.AUTO_RENEW.getValue()) ? Boolean.parseBoolean(depositElement.getAttribute(DepositXmlTag.AUTO_RENEW.getValue())) : DEFAULT_AUTO_RENEW;
        String name = getElementTextContent(depositElement, DepositXmlTag.NAME.getValue());
        Country country = Country.extractCountryFromString(getElementTextContent(depositElement, DepositXmlTag.COUNTRY.getValue()));
        String depositor = getElementTextContent(depositElement, DepositXmlTag.DEPOSITOR.getValue());
        int amount = Integer.parseInt(getElementTextContent(depositElement, DepositXmlTag.AMOUNT.getValue()));
        int profitability = Integer.parseInt(getElementTextContent(depositElement, DepositXmlTag.PROFITABILITY.getValue()));
        YearMonth timeConstraint = YearMonth.parse(getElementTextContent(depositElement, DepositXmlTag.TIME_CONSTRAINT.getValue()));

        switch (depositXmlTag) {
            case DEMAND_DEPOSIT -> {
                deposit = new DemandDeposit();
                DemandDepositType type = DemandDepositType.extractTypeFromString(getElementTextContent(depositElement, DepositXmlTag.TYPE.getValue()));
                ((DemandDeposit) deposit).setDemandDepositType(type);

            }
            case TIME_DEPOSIT -> {
                deposit = new TimeDeposit();
                ((TimeDeposit) deposit).setPenalty(Integer.parseInt(getElementTextContent(depositElement, DepositXmlTag.PENALTY.getValue())));
            }

            default -> throw new DepositEntityException("invalid tag");
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
