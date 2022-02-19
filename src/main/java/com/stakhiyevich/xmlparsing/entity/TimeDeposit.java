package com.stakhiyevich.xmlparsing.entity;

import java.time.YearMonth;

public class TimeDeposit extends Deposit {

    private int penalty;

    public TimeDeposit(String id, boolean isAutoRenewable, String name, Country country, String depositor, int amount, int profitability, YearMonth timeConstraint, int penalty) {
        super(id, isAutoRenewable, name, country, depositor, amount, profitability, timeConstraint);
        this.penalty = penalty;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}
