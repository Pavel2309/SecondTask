package com.stakhiyevich.xmlparsing.builder;

import com.stakhiyevich.xmlparsing.exception.DepositEntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URL;

public class DepositSaxBuilder extends AbstractDepositBuilder {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void buildDeposits(String filename) throws DepositEntityException {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            DepositHandler depositHandler = new DepositHandler();

            ClassLoader loader = getClass().getClassLoader();
            URL resource = loader.getResource(filename);

            reader.setContentHandler(depositHandler);
            reader.setErrorHandler(new DepositErrorHandler());
            reader.parse(resource.getFile());

            depositSet = depositHandler.getDepositSet();

        } catch (SAXException | IOException | ParserConfigurationException e) {
            logger.error("error during sax parting", e);
            throw new DepositEntityException("error during sax parting", e);
        }
    }
}
