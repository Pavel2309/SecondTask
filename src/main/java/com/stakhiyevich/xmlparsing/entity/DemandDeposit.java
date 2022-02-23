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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemandDeposit)) return false;
        if (!super.equals(o)) return false;

        DemandDeposit that = (DemandDeposit) o;

        return getDemandDepositType() == that.getDemandDepositType();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getDemandDepositType() != null ? getDemandDepositType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DemandDeposit{");
        sb.append("demandDepositType=").append(demandDepositType);
        sb.append('}');
        return sb.toString();
    }
}
