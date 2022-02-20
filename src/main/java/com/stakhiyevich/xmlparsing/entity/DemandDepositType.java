package com.stakhiyevich.xmlparsing.entity;

public enum DemandDepositType {

    CHECKING_ACCOUNT("checking-account"),
    SAVING_ACCOUNT("saving-account");

    private final String value;

    DemandDepositType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DemandDepositType extractTypeFromString(String text) {
        for (DemandDepositType type : DemandDepositType.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
