package com.stakhiyevich.xmlparsing.entity;

public enum DepositCountry {

    BELARUS("Belarus"),
    SWITZERLAND("Switzerland"),
    CHINA("China"),
    USA("USA");

    private final String value;

    DepositCountry(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DepositCountry extractCountryFromString(String text) {
        for (DepositCountry country : DepositCountry.values()) {
            if (country.value.equalsIgnoreCase(text)) {
                return country;
            }
        }
        return null;
    }

}
