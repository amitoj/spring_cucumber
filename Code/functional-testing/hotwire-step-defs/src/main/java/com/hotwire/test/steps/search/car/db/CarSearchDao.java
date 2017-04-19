/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.car.db;

import com.hotwire.test.steps.search.car.db.entity.LocationEntity;
import com.hotwire.test.steps.search.car.db.mapper.DepositFilterLocationMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author v-vzyryanov
 * @since 7/25/13
 */
public class CarSearchDao {

    private static final String GET_LOCATION_BY_DEPOSIT_TYPE =
        "SELECT pickup_airport_code FROM car_service " +
        "HAVING COUNT(CASE WHEN is_debit_card_ok_for_deposit = ? OR accepts_local_debit_card = ? THEN 1 END) = 0 " +
        "AND count(*) > 1 " +
        "GROUP BY pickup_airport_code";

    private JdbcTemplate dataSource;

    public CarSearchDao(JdbcTemplate dataSource) {
        this.dataSource = dataSource;
    }

    private String not(String arg) {
        return "Y".equals(arg) ? "N" : "Y";
    }

    public String getLocationByDepositFilterType(String acceptDebitCard, String acceptLocalDebitCard) {
        List<LocationEntity> locations = dataSource.query(
            GET_LOCATION_BY_DEPOSIT_TYPE,
            new Object[]{not(acceptDebitCard), not(acceptLocalDebitCard)},
            new DepositFilterLocationMapper()
        );
        return (locations.size() > 0) ? locations.get(0).getPickUpLocation() : null;
    }
}
