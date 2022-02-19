package com.stakhiyevich.xmlparsing.builder;

import com.stakhiyevich.xmlparsing.entity.Deposit;
import com.stakhiyevich.xmlparsing.exception.DepositEntityException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDepositBuilder {

    protected Set<Deposit> depositSet;

    public AbstractDepositBuilder() {
        this.depositSet = new HashSet<>();
    }

    public AbstractDepositBuilder(Set<Deposit> depositSet) {
        this.depositSet = depositSet;
    }

    public Set<Deposit> getDepositSet() {
        return depositSet;
    }

    public abstract void buildDeposits(String filename) throws DepositEntityException;

}
