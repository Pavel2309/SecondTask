package com.stakhiyevich.xmlparsing.exception;

public class DepositDataException extends Exception {

    public DepositDataException() {
        super();
    }

    public DepositDataException(String message) {
        super(message);
    }

    public DepositDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositDataException(Throwable cause) {
        super(cause);
    }
}
