package com.stakhiyevich.xmlparsing.entity;

import java.time.YearMonth;

public class DemandDeposit extends Deposit {

    private DemandDepositType demandDepositType;

    public DemandDeposit() {

    }

    public DemandDeposit(String id, boolean isAutoRenewable, String name, DepositCountry country, String depositor, int amount, int profitability, YearMonth timeConstraint, DemandDepositType demandDepositType) {
        super(id, isAutoRenewable, name, country, depositor, amount, profitability, timeConstraint);
        this.demandDepositType = demandDepositType;
    }



    public DemandDepositType getDemandDepositType() {
        return demandDepositType;
    }

    public void setDemandDepositType(DemandDepositType demandDepositType) {
        this.demandDepositType = demandDepositType;
    }

}
