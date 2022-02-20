package com.stakhiyevich.xmlparsing.builder;

enum DepositXmlTag {

    BANKS("Banks"),
    ID("id"),
    AUTO_RENEW("auto_renew"),
    DEMAND_DEPOSIT("demand-deposit"),
    TIME_DEPOSIT("time-deposit"),
    NAME("name"),
    COUNTRY("country"),
    DEPOSITOR("depositor"),
    AMOUNT("amount"),
    PROFITABILITY("profitability"),
    TIME_CONSTRAINT("timeConstraint"),
    PENALTY("penalty");

    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";

    @Override
    public String toString() {
        String result = this.name();
        result = result.toLowerCase();
        result = result.replace(UNDERSCORE, HYPHEN);
        return result;
    }
    private final String value;

    DepositXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
