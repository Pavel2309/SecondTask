package com.stakhiyevich.xmlparsing.entity;

public enum DemandDepositType {

    CHECKING_ACCOUNT("checkingAccount"),
    SAVING_ACCOUNT("savingAccount");

    private final String value;

    DemandDepositType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
