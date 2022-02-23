package com.stakhiyevich.xmlparsing.validator;

import com.stakhiyevich.xmlparsing.exception.DepositDataException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DepositXMLValidatorTest {

    private static final String XML_FILE = "data/deposit.xml";
    private static final String XML_FILE_WRONG = "data/deposit_wrong.xml";
    private DepositXMLValidator depositXMLValidator;

    @BeforeClass
    public void setUp() {
        depositXMLValidator = new DepositXMLValidator();
    }

    @Test
    public void testIsValidXML() {

        boolean actual = false;
        try {
            actual = depositXMLValidator.isValidXML(XML_FILE);
        } catch (DepositDataException e) {
            fail(e.getMessage(),e);
        }
        assertTrue(actual);
    }

    @Test
    public void testIsNotValid() {

        boolean actual = false;
        try {
            actual = depositXMLValidator.isValidXML(XML_FILE_WRONG);
        } catch (DepositDataException e) {
            fail(e.getMessage(),e);
        }
        assertFalse(actual);
    }
}