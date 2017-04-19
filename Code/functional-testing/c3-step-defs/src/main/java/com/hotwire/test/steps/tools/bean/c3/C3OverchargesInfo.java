/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.bean.c3;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;

/**
 * User: v-ngolodiuk
 * Date: 10/23/13
 * Contains all overcharges related getters and setters
 */
public class C3OverchargesInfo extends ToolsAbstractBean {
    private int searchPeriod;
    private String assignee;
    private String searchBy;
    private String amountCharged;
    private String overchargeStatus;
    private boolean sorting;
    private OverchargeColumns overchargeColumn;
    private String overchargeColumnString;
    private String hotelName;
    private String hotelState;
    /**
     * Columns in Overcharges results list table
     */
    public enum OverchargeColumns {
        HOTEL_NAME,
        OVERCHARGE_COUNT,
        DATE_MODIFIED,
        LAST_OVERCHARGE_DATE,
        HOTEL_STATUS,
        COUNTRY,
        TOTAL_OVERCHARGES
    }

    public OverchargeColumns getOverchargeColumn() {
        return overchargeColumn;
    }

    public void setOverchargeColumn(String column) {
        this.overchargeColumnString = column;
        this.overchargeColumn = OverchargeColumns.valueOf(column.toUpperCase().replace(" ", "_"));
    }

    public String getOverchargeColumnString() {
        return overchargeColumnString;
    }

    public boolean isSorting() {
        return sorting;
    }

    public void setSorting(boolean sorting) {
        this.sorting = sorting;
    }

    public String getOverchargeStatus() {
        return overchargeStatus;
    }

    public void setOverchargeStatus(String overchargeStatus) {
        this.overchargeStatus = overchargeStatus;
    }

    public String getAmountCharged() {
        return amountCharged;
    }

    public void setAmountCharged(String amountCharged) {
        this.amountCharged = amountCharged;
    }

    public void setSearchPeriod(int searchPeriod) {
        this.searchPeriod = searchPeriod;
    }

    public int getSearchPeriod() {
        return searchPeriod;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelState() {
        return hotelState;
    }

    public void setHotelState(String hotelState) {
        this.hotelState = hotelState;
    }
}
