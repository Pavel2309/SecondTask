package com.stakhiyevich.xmlparsing.validator;

import com.stakhiyevich.xmlparsing.exception.DepositDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DepositXMLValidator {

    private static final Logger logger = LogManager.getLogger();
    private static final String SCHEMA_NAME = "data/deposit.xsd";

    public boolean isValidXML(String xmlFile) throws DepositDataException {
        ClassLoader loader = DepositXMLValidator.class.getClassLoader();
        URL resource = loader.getResource(SCHEMA_NAME);
        File schemaFile = new File(resource.getFile());

        URL resourceXML = loader.getResource(xmlFile);
        String xmlPath = new File(resourceXML.getFile()).getPath();
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        try {
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlPath);
            validator.validate(source);
        } catch (IOException e) {
            logger.error("can't read file {}", xmlPath);
            throw new DepositDataException("can't open file " + xmlPath, e);
        } catch (SAXException e) {
            logger.error("file {} is not valid", xmlPath, e);
            return false;
        }

        return true;
    }

}
