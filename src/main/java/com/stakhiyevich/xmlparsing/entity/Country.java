package com.stakhiyevich.xmlparsing.entity;

public enum Country {

    BELARUS("Belarus"),
    SWITZERLAND("Switzerland"),
    CHINA("China"),
    USA("USA");

    private final String value;

    Country(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Country extractCountryFromString(String text) {
        for (Country country : Country.values()) {
            if (country.value.equalsIgnoreCase(text)) {
                return country;
            }
        }
        return null;
    }

}
