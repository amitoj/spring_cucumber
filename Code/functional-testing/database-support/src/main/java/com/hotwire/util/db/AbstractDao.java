/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db;

import cucumber.api.PendingException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
* This class provide database access for BDD tests
 * For more details please read: https://redspace.jiveon.com/docs/DOC-11616
 * All other DAO should extend it
 *
 * Data Access Objects is user for getting customer purchase from DataBase with predefined parameters
 */
public abstract class AbstractDao {
    private static final int RANDOM_ROW_RANGE = 500;
    /**
     * Constants below are for Oracle Database
     * for other databases these strings need to be overwritten
     */
    private static final String LIMIT_FUNC = "ROWNUM";
    private static final String RAND_FUNC = "dbms_random.value";

    private static final RandomizedValueRowMapper RANDOMIZED_VALUE_ROW_MAPPER = new RandomizedValueRowMapper();

    private SimpleJdbcDaoSupport dataSource;

    protected AbstractDao(SimpleJdbcDaoSupport dataSource) {
        this.dataSource = dataSource;
    }

    public SimpleJdbcDaoSupport getDataSource() {
        return dataSource;
    }

    /**
     * This method generate query for getting random value from Oracle DB
     * Random range specified in RANDOM_ROW_RANGE variable
     */
    private String randomizer(String query) {
        return String.format("SELECT * FROM (SELECT q.*, %s as rnd FROM (%s) q WHERE %s ORDER BY rnd) WHERE %s=1",
                             RAND_FUNC, query, LIMIT_FUNC + "<" + RANDOM_ROW_RANGE, LIMIT_FUNC);
    }

    /***
     * Adding line to SQL query. Does not work with sorting queries (aka ORDER BY)
     */
    protected String appendLine(String query, String line) {
        if (query.toUpperCase().contains("WHERE")) {
            return query + "\n AND " + line;
        }
        else {
            return query + "\n WHERE " + line;
        }
    }

    /***
     * Limiting SQL query result for better Database performance.
     * Too many results from SQL query can specifically increase test execution time.
     */
    protected String limitToOneResult(String query) {
        return String.format("SELECT * FROM (%s) WHERE %s", query, LIMIT_FUNC + "=1");
    }

    /** query must return only one value.
     *  if no result in DB will produce Pending Exception.
     */
    protected String getValueFromDB(String query) {
        try {
            return getDataSource().getSimpleJdbcTemplate().queryForObject(limitToOneResult(query), String.class);
        }
        catch (EmptyResultDataAccessException e) {
            throw new PendingException("No such purchase in DataBase...");
        }
    }

    /** query must return only one value.
     *  use this method if you have additional arguments (aka ? in SQLs)
     *  if no result in DB will produce Pending Exception.
     */
    protected String getValueFromDB(String query, String... arg) {
        try {
            return getDataSource().getSimpleJdbcTemplate().queryForObject(limitToOneResult(query), String.class, arg);
        }
        catch (EmptyResultDataAccessException e) {
            throw new PendingException("No such purchase in DataBase...");
        }
    }

    /**
     * query must return only one value from  shuffled results
     */
    protected String getRandomValueFromDB(String query) {
        try {
            return getDataSource().getSimpleJdbcTemplate().queryForObject(randomizer(query),
                                                                          RANDOMIZED_VALUE_ROW_MAPPER);
        }
        catch (EmptyResultDataAccessException e) {
            throw new PendingException("No such purchase in DataBase...");
        }
    }

    protected String getRandomValueFromDB(String query, String... args) {
        try {
            return getDataSource().getSimpleJdbcTemplate().queryForObject(randomizer(query),
                                                                          RANDOMIZED_VALUE_ROW_MAPPER, args);
        }
        catch (EmptyResultDataAccessException e) {
            throw new PendingException("No such purchase in DataBase...");
        }
    }

    /**
     * @return random row from DataBase as Map
     */
    protected Map getRowFromDB(String stringQuery) {
        List<Map<String, Object>> rows = getDataSource().getSimpleJdbcTemplate().queryForList(stringQuery);
        if (rows.size() == 0) {
            throw new PendingException("No results in DataBase");
        }
        return rows.get(0);
    }

    protected Map getRandomRowFromDB(String stringQuery) {
        List<Map<String, Object>> rows = getDataSource().getSimpleJdbcTemplate().queryForList(stringQuery);
        if (rows.size() == 0) {
            throw new PendingException("No results in DataBase");
        }
        Collections.shuffle(rows);
        return rows.get(0);
    }

    protected Map getRandomRowFromDB(String stringQuery, String... args) {
        List<Map<String, Object>> rows = getDataSource().getSimpleJdbcTemplate().
                queryForList(stringQuery, args);
        if (rows.size() == 0) {
            throw new PendingException("No results in DataBase");
        }
        Collections.shuffle(rows);
        return rows.get(0);
    }

    protected Map getRowFromDB(String stringQuery, String... args) {
        List<Map<String, Object>> rows =
                getDataSource().getSimpleJdbcTemplate().queryForList(stringQuery, args);
        if (rows.size() == 0) {
            throw new PendingException("No results in DataBase");
        }
        return rows.get(0);
    }

    protected boolean checkResultInDB(String query, String... arg) {
        List results = getDataSource().getSimpleJdbcTemplate().queryForList(limitToOneResult(query), arg);
        return results.size() != 0;
    }

    protected void executeQuery(String query, String... arg) {
        query = query.replace("?", arg[0]);
        getDataSource().getJdbcTemplate().execute(query);
    }

    protected String verifyValueInDB(String query, String... arg) {
        try {
            return getDataSource().getSimpleJdbcTemplate().queryForObject(limitToOneResult(query), String.class, arg);
        }
        catch (EmptyResultDataAccessException e) {
            throw new AssertionError("Row must be in DataBase");
        }
    }

    protected Map verifyRowInDB(String stringQuery, String... args) {
        List<Map<String, Object>> rows =
                getDataSource().getSimpleJdbcTemplate().queryForList(limitToOneResult(stringQuery), args);
        if (rows.size() == 0) {
            throw new AssertionError("Row must be in DataBase");
        }
        return rows.get(0);
    }

    protected List<Object> verifyRowsInDB(String stringQuery, String... args) {
        List<Map<String, Object>> rows;
        List<Object> results = new ArrayList<>();
        rows = getDataSource().getSimpleJdbcTemplate().queryForList(stringQuery, args);
        if (rows.size() == 0) {
            throw new AssertionError("Row must be in DataBase");
        }
        for (Map m : rows) {
            results.add(m.values().toString().replace("[", "").replace("]", ""));
        }
        return results;
    }

    protected List<Map<String, Object>> verifyTableInDB(String query, String... arg) {
        final List<Map<String, Object>> results = getDataSource().getSimpleJdbcTemplate().queryForList(query, arg);
        if (results.size() == 0) {
            throw new AssertionError("Row must be in DataBase");
        }
        else {
            return  results;
        }
    }

    protected void waitForDataBaseProcessing(double seconds) {
        long mils = (long) seconds * 1000;
        try {
            Thread.sleep(mils);
        }
        catch (InterruptedException e) {
            throw new RuntimeException("Error while waiting for Database processing!");
        }
    }

    /**
     * A mapper to extract single string value from randomized ResultSet as it always will contain additional column
     * 'rnd' added for randomization
     */
    private static final class RandomizedValueRowMapper implements ParameterizedRowMapper<String> {
        @Override
        public String mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString(1);
        }
    }
}
