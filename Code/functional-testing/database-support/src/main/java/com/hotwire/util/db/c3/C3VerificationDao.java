/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3;

import com.hotwire.testing.UnimplementedTestException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.List;
import java.util.Map;

/**
 * Created by v-ozelenov on 4/11/2014.
 * This class is for verification values in C3 specific tables.
 */
public class C3VerificationDao extends C3SearchDao {
    private static final String PURCHASE_ORDER_SQL =
        "select status_code from purchase_order where display_number = ?";

    private static final String RESERVATION_SQL =
        "select rs.status_code from reservation_status rs\n" +
        "join reservation r on r.reservation_id = rs.reservation_id\n" +
        "join purchase_order po on po.purchase_order_id = r.purchase_order_id\n" +
        "where po.display_number = ?";

    public C3VerificationDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    /**
     * DataBase tables supported by C3VerificationDao
     */
    public enum DataBaseTables { PURCHASE_ORDER("purchase_order"), RESERVATION("reservation");

        private DataBaseTables table;

        private String tableName;

        DataBaseTables(String tableName) {
            this.tableName =  tableName;

        }

        public void setTable(DataBaseTables table) {
            this.table = table;
        }

        public DataBaseTables getTable() {
            return table;
        }

        public static DataBaseTables validate(String table) {
            String tableName = table.toLowerCase().replace(" ", "");
            for (DataBaseTables t : DataBaseTables.values()) {
                if (String.valueOf(t.tableName).equals(tableName)) {
                    return t;
                }
            }
            throw new UnimplementedTestException("Not expected reservation type: " + tableName);
        }
    }



    public String verifyPurchaseOrderStatus(String itineraryNumber) {
        return verifyValueInDB(PURCHASE_ORDER_SQL, itineraryNumber);
    }

    public List<Map<String, Object>> verifyReservationStatus(String itineraryNumber) {
        return verifyTableInDB(RESERVATION_SQL, itineraryNumber);
    }

}
