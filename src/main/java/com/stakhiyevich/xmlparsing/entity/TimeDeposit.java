package com.stakhiyevich.xmlparsing.entity;

import java.time.YearMonth;

public class TimeDeposit extends Deposit {

    private int penalty;

    public TimeDeposit() {

    }

    public TimeDeposit(String id, boolean isAutoRenewable, String name, DepositCountry country, String depositor, int amount, int profitability, YearMonth timeConstraint, int penalty) {
        super(id, isAutoRenewable, name, country, depositor, amount, profitability, timeConstraint);
        this.penalty = penalty;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeDeposit)) return false;
        if (!super.equals(o)) return false;

        TimeDeposit that = (TimeDeposit) o;

        return getPenalty() == that.getPenalty();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getPenalty();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeDeposit{");
        sb.append("penalty=").append(penalty);
        sb.append('}');
        return sb.toString();
    }
}
