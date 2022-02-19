package com.stakhiyevich.xmlparsing.builder;

enum DepositXmlTag {

    BANKS("Banks"),
    DEMAND_DEPOSIT("demand-deposit"),
    TIME_DEPOSIT("time-deposit"),
    NAME("name"),
    COUNTRY("country"),
    DEPOSITOR("depositor"),
    AMOUNT("amount"),
    PROFITABILITY("profitability"),
    TIME_CONSTRAINT("timeConstraint");


    private final String value;

    DepositXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
