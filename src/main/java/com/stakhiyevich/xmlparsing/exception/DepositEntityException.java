package com.stakhiyevich.xmlparsing.exception;

public class DepositEntityException extends Exception {

    public DepositEntityException() {
        super();
    }

    public DepositEntityException(String message) {
        super(message);
    }

    public DepositEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositEntityException(Throwable cause) {
        super(cause);
    }
}
