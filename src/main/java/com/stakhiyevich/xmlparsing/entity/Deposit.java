package com.stakhiyevich.xmlparsing.entity;

import java.time.YearMonth;

public abstract class Deposit {

    public static final boolean AUTO_RENEW = false;

    private String id;
    private boolean isAutoRenewable;
    private String name;
    private Country country;
    private String depositor;
    private int amount;
    private int profitability;
    private YearMonth timeConstraint;

    public Deposit(String id, boolean isAutoRenewable, String name, Country country, String depositor, int amount, int profitability, YearMonth timeConstraint) {
        this.id = id;
        this.isAutoRenewable = isAutoRenewable;
        this.name = name;
        this.country = country;
        this.depositor = depositor;
        this.amount = amount;
        this.profitability = profitability;
        this.timeConstraint = timeConstraint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAutoRenewable() {
        return isAutoRenewable;
    }

    public void setAutoRenewable(boolean autoRenewable) {
        isAutoRenewable = autoRenewable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getDepositor() {
        return depositor;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getProfitability() {
        return profitability;
    }

    public void setProfitability(int profitability) {
        this.profitability = profitability;
    }

    public YearMonth getTimeConstraint() {
        return timeConstraint;
    }

    public void setTimeConstraint(YearMonth timeConstraint) {
        this.timeConstraint = timeConstraint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deposit)) return false;

        Deposit deposit = (Deposit) o;

        if (isAutoRenewable() != deposit.isAutoRenewable()) return false;
        if (getAmount() != deposit.getAmount()) return false;
        if (getProfitability() != deposit.getProfitability()) return false;
        if (!getId().equals(deposit.getId())) return false;
        if (getName() != null ? !getName().equals(deposit.getName()) : deposit.getName() != null) return false;
        if (getCountry() != deposit.getCountry()) return false;
        if (getDepositor() != null ? !getDepositor().equals(deposit.getDepositor()) : deposit.getDepositor() != null)
            return false;
        return getTimeConstraint() != null ? getTimeConstraint().equals(deposit.getTimeConstraint()) : deposit.getTimeConstraint() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (isAutoRenewable() ? 1 : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getDepositor() != null ? getDepositor().hashCode() : 0);
        result = 31 * result + getAmount();
        result = 31 * result + getProfitability();
        result = 31 * result + (getTimeConstraint() != null ? getTimeConstraint().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Deposit{");
        sb.append("id='").append(id).append('\'');
        sb.append(", isAutoRenewable=").append(isAutoRenewable);
        sb.append(", name='").append(name).append('\'');
        sb.append(", country=").append(country);
        sb.append(", depositor='").append(depositor).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", profitability=").append(profitability);
        sb.append(", timeConstraint=").append(timeConstraint);
        sb.append('}');
        return sb.toString();
    }
}
