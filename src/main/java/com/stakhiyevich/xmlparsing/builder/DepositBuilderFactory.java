package com.stakhiyevich.xmlparsing.builder;

import com.stakhiyevich.xmlparsing.exception.DepositDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DepositBuilderFactory {

    private static final Logger logger = LogManager.getLogger();

    private static final DepositBuilderFactory instance = new DepositBuilderFactory();

    private DepositBuilderFactory() {

    }

    public AbstractDepositBuilder createDepositBuilder(ParserType parserType) throws DepositDataException {
        switch (parserType) {
            case DOM -> {
                return new DepositDomBuilder();
            }
            case SAX -> {
                return new DepositSaxBuilder();
            }
            case STAX -> {
                return new DepositStaxBuilder();
            }
            default -> {
                throw new EnumConstantNotPresentException(parserType.getDeclaringClass(), parserType.name());
            }
        }
    }

    public static DepositBuilderFactory getInstance() {
        return instance;
    }


}
