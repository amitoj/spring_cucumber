/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.car.db.mapper;

import com.hotwire.test.steps.search.car.db.entity.LocationEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author v-vzyryanov
 * @since 7/25/13
 */
public class DepositFilterLocationMapper implements RowMapper<LocationEntity> {

    @Override
    public LocationEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        LocationEntity entity = new LocationEntity();
        entity.setPickUpLocation(resultSet.getString("pickup_airport_code"));
        return entity;
    }
}
