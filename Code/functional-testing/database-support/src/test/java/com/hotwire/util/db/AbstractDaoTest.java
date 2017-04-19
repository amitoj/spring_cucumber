/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db;

import cucumber.api.PendingException;
import org.fest.assertions.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Database access objects that  checks database connection and verify that purchase order table
 * is accessible and not empty
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:database-support-test-context.xml" })
@DirtiesContext
@Ignore
public class AbstractDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDaoTest.class.getName());
    private static final String TEST_SQL = "SELECT DISPLAY_NUMBER FROM PURCHASE_ORDER";
    private static final String TEST_SQL2 = "SELECT DISPLAY_NUMBER FROM PURCHASE_ORDER WHERE PGOOD_CLASS = ?";
    private static final String TEST_SQL_ORDER =
            "SELECT DISPLAY_NUMBER FROM PURCHASE_ORDER WHERE ROWNUM < 5 ORDER BY CREATE_DATE";
    private static final String TEST_SQL_ORDER2 =
            "SELECT DISPLAY_NUMBER FROM PURCHASE_ORDER WHERE ROWNUM < 5 AND PGOOD_CLASS = ? ORDER BY CREATE_DATE";
    /**
     * Negative case
     */
    private static final String TEST_SQL4 = "SELECT DISPLAY_NUMBER FROM PURCHASE_ORDER WHERE PGOOD_CLASS = 'X'";

    @Autowired
    private SimpleJdbcDaoSupport testDb;


    @Test
    public void getValueFromDB() {
        Assertions.assertThat(new DaoImplStub(testDb).getValueFromDB(TEST_SQL)).isNotEmpty();
    }

    @Test
    public void getRandValueFromDB() {
        Assertions.assertThat(new DaoImplStub(testDb).getRandomValueFromDB(TEST_SQL)).isNotEmpty();
    }

    @Test
    public void getRandValueFromDBWithParam() {
        Assertions.assertThat(new DaoImplStub(testDb).getRandomValueFromDB(TEST_SQL2, "H")).isNotEmpty();
    }

    @Test
    public void getRandValueFromDBWithParamAndOrderBy() {
        Assertions.assertThat(new DaoImplStub(testDb).getRandomValueFromDB(TEST_SQL_ORDER)).isNotEmpty();
    }

    @Test
    public void getRowFromDB() {
        Assertions.assertThat(new DaoImplStub(testDb).getRowFromDB(TEST_SQL_ORDER)
                .get("DISPLAY_NUMBER").toString()).isNotEmpty();
    }

    @Test
    public void getRandomRowFromDB() {
        Assertions.assertThat(new DaoImplStub(testDb).getRandomRowFromDB(TEST_SQL_ORDER)
                .get("DISPLAY_NUMBER").toString()).isNotEmpty();
    }

    @Test
    public void getRowFromDBWithParam() {
        Assertions.assertThat(new DaoImplStub(testDb).getRowFromDB(TEST_SQL_ORDER2, "H")
                .get("DISPLAY_NUMBER").toString()).isNotEmpty();
    }


    @Test
    public void verifyValueInDB() {
        Assertions.assertThat(new DaoImplStub(testDb).verifyValueInDB(TEST_SQL)).isNotEmpty();
        LOGGER.info("Connection established.\nPurchase order table is accessible.");
    }

    @Test
    public void verifyRowInDB() {
        Assertions.assertThat(new DaoImplStub(testDb).verifyRowInDB(TEST_SQL).get("DISPLAY_NUMBER").toString())
                .isNotEmpty();
    }

    @Test
    public void verifyAllResults() {
        Assertions.assertThat(new DaoImplStub(testDb).verifyTableInDB(TEST_SQL_ORDER2, "H")
                .get(0).get("DISPLAY_NUMBER")
                .toString()).isNotEmpty();
    }

    @Test
    public void checkResult() {
        Assertions.assertThat(new DaoImplStub(testDb).checkResultInDB(TEST_SQL2, "H")).isTrue();
    }

    @Test(expected = PendingException.class)
    public void verifyPendingException() {
        new DaoImplStub(testDb).getValueFromDB(TEST_SQL4);
    }

    @Test(expected = PendingException.class)
    public void verifyPendingException2() {
        new DaoImplStub(testDb).getValueFromDB(TEST_SQL2, "X");
    }

    @Test(expected = PendingException.class)
    public void verifyPendingException3() {
        new DaoImplStub(testDb).getRowFromDB(TEST_SQL4);
    }

    @Test(expected = PendingException.class)
    public void verifyPendingException4() {
        new DaoImplStub(testDb).getRandomRowFromDB(TEST_SQL4);
    }

    @Test(expected = PendingException.class)
    public void verifyPendingException5() {
        new DaoImplStub(testDb).getRandomValueFromDB(TEST_SQL4);
    }

    @Test(expected = PendingException.class)
    public void verifyPendingException6() {
        new DaoImplStub(testDb).getRowFromDB(TEST_SQL_ORDER2, "X");
    }

    @Test(expected = AssertionError.class)
    public void verifyNoResultsException() {
        new DaoImplStub(testDb).verifyValueInDB(TEST_SQL4);
    }

    @Test(expected = AssertionError.class)
    public void verifyNoResultsException2() {
        new DaoImplStub(testDb).verifyRowInDB(TEST_SQL2, "X");
    }

    @Test(expected = AssertionError.class)
    public void verifyNoResultsException3() {
        new DaoImplStub(testDb).verifyTableInDB(TEST_SQL2, "X");
    }
}
