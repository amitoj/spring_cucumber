/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 3/25/14
 * Time: 5:53 AM
 * SQL queries for CSR management operations verification
 */
public class C3CSRDao extends C3SearchDao {

    private static final String VALID_CSR_ACCOUNT =
        "select user_name from csr\n" +
        "where is_active = 'Y'\n" +
        "and user_name not like 'csr%'\n" +
        "and login_failures = 0\n" +
        "and access_level_code in " +
                "(3001, 3002, 3003, 3004, 3006, 3012, 3018, 3019, 3020, 3021, 3022,3023, 3024, 3025, 3026, 3027)\n" +
//        "and email NOT like '%hotwire.com%'\n" +
        "and LOGIN_FAILURES = 0\n" +
        "and IS_ACTIVE = 'Y'\n" +
        "and email NOT like '%expedia.com%'";

    private static final String VALID_CSR11_ACCOUNT =
            "select user_name from csr\n" +
                    "where is_active = 'Y'\n" +
                    "and user_name not like 'csr%'\n" +
                    "and access_level_code in (3011)" +
                    "and LOGIN_FAILURES = 0\n" +
                    "and IS_ACTIVE = 'Y'\n";

    private static final String FINANCE_CSR_ACCOUNT =
        "select user_name from csr\n" +
        "where is_active = 'Y'\n" +
        "and user_name not like 'csr%'\n" +
        "and access_level_code in (3007, 3008, 3009, 3010, 3011)";

    private static final String CSR_LOGIN_FAILURES =
        "select LOGIN_FAILURES, LOCKED_DATE from csr where user_name = ? and IS_ACTIVE = 'Y'";

    public C3CSRDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public String getFinanceCSR() {
        return getValueFromDB(FINANCE_CSR_ACCOUNT);
    }

    public String getRegularCSR() {
        return getValueFromDB(VALID_CSR_ACCOUNT);
    }

    public String getValidCsrAccount() {
        return getRandomValueFromDB(VALID_CSR_ACCOUNT);
    }

    public String getValidCsr11Account() {
        return getRandomValueFromDB(VALID_CSR11_ACCOUNT);
    }

    public boolean checkIfAgentExists(String agentName) {
        return checkResultInDB("select user_name from csr where user_name = ?", agentName);
    }

    public List<Map<String, Object>> getCsrLoginFailures(String agentName) {
        return verifyTableInDB(CSR_LOGIN_FAILURES, agentName);
    }
}
