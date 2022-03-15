package com.stakhiyevich.xmlparsing.builder;

import com.stakhiyevich.xmlparsing.entity.*;
import com.stakhiyevich.xmlparsing.exception.DepositDataException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class DepositDomBuilderTest {

    private static final String DEPOSIT_XML_FILE = "data/deposit.xml";
    private AbstractDepositBuilder builder;
    private DemandDeposit demandDeposit;
    private TimeDeposit timeDeposit;

    @BeforeClass
    public void setUp() {

        try {
            builder = DepositBuilderFactory.getInstance().createDepositBuilder(ParserType.DOM);
        } catch (DepositDataException e) {
            fail(e.getMessage(), e);
        }

        demandDeposit = new DemandDeposit();
        demandDeposit.setId("ABC-000000");
        demandDeposit.setAutoRenewable(true);
        demandDeposit.setName("Demand deposit 1");
        demandDeposit.setCountry(DepositCountry.CHINA);
        demandDeposit.setDepositor("Eric Cartman");
        demandDeposit.setAmount(10000);
        demandDeposit.setProfitability(3);
        demandDeposit.setTimeConstraint(YearMonth.parse("2022-04"));
        demandDeposit.setDemandDepositType(DemandDepositType.CHECKING_ACCOUNT);

        timeDeposit = new TimeDeposit();
        timeDeposit.setId("AAC-233212");
        timeDeposit.setName("Time deposit 1");
        timeDeposit.setCountry(DepositCountry.SWITZERLAND);
        timeDeposit.setDepositor("Butters Stotch");
        timeDeposit.setAmount(30500);
        timeDeposit.setProfitability(7);
        timeDeposit.setTimeConstraint(YearMonth.parse("2024-08"));
        timeDeposit.setPenalty(1000);

    }

    @Test
    public void testBuildDeposits() {
        Set<Deposit> expected = new HashSet<>();
        expected.add(demandDeposit);
        expected.add(timeDeposit);


        try {
            builder.buildDeposits(DEPOSIT_XML_FILE);
        } catch (DepositDataException e) {
            fail(e.getMessage(), e);
        }

        Set<Deposit> actual = builder.getDepositSet();

        assertEquals(actual, expected);
    }
}