/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.air;

import java.util.Date;
import java.util.List;

import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.BaseSearchParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created with IntelliJ IDEA.
 * User: LRyzhikov
 * Date: 5/30/12
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AirSearchParametersImpl extends BaseSearchParameters implements AirSearchParameters {

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    private String origLocation;
    private Integer passengers;
    private String spVisiableStatus;
    private String columnAStatus;
    private String toFirstLocation;
    private String fromFirstLocation;
    private Date firstStartDate;
    private String fromSecondLocation;
    private String toSecondLocation;
    private Date secondStartDate;
    private String fromThirdLocation;
    private String toThirdLocation;
    private Date thirdStartDate;
    private String fromFourthLocation;
    private String toFourthLocation;
    private Date fourthStartDate;

    @Override
    public String getOrigLocation() {
        return origLocation;
    }

    @Override
    public void setOrigLocation(String origLocation) {
        this.origLocation = origLocation;
    }

    @Override
    public Integer getPassengers() {
        return passengers;
    }

    @Override
    public String getFirstToLocation() {
        return toFirstLocation;
    }

    @Override
    public void setFirstToLocation(String toFirstLocation) {
        this.toFirstLocation = toFirstLocation;
    }

    @Override
    public String getFirstFromLocation() {
        return fromFirstLocation;
    }

    @Override
    public void setFirstFromLocation(String fromFirstLocation) {
        this.fromFirstLocation = fromFirstLocation;
    }

    @Override
    public Date getFirstStartDate() {
        return firstStartDate;
    }

    @Override
    public void setFirstStartDate(Date firstStartDate) {
        this.firstStartDate = firstStartDate;
    }

    @Override
    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
        for (int i = 1; i < passengers; i++) {
            PassengerData passengerData = new PassengerData();
            List<PassengerData> passengerDataList = purchaseParameters.getPassengerList();

            passengerData.setFirstName("");
            passengerData.setMiddleName("");
            passengerData.setLastName("");

            passengerData.setDayBirthdayDate("");
            passengerData.setMonthBirthdayDate("");
            passengerData.setYearBirthdayDate("");

            passengerData.setGender("");

            passengerData.setfFProgramName("");
            passengerData.setfFNumber("");

            passengerData.setRedressNumber("");


            passengerDataList.add(i, passengerData);
            purchaseParameters.setPassengerList(passengerDataList);
        }

    }

    @Override
    protected PGoodCode getPGoodCode() {
        return PGoodCode.A;
    }

    @Override
    public String getSpVisiableStatus() {
        return spVisiableStatus;
    }

    @Override
    public void setSpVisiableStatus(String spVisiableStatus) {
        this.spVisiableStatus = spVisiableStatus;
    }

    @Override
    public String getColumnAStatus() {
        return columnAStatus;
    }

    @Override
    public void setColumnAStatus(String columnAStatus) {
        this.columnAStatus = columnAStatus;
    }

    @Override
    public String getSecondToLocation() {
        return toSecondLocation;
    }

    @Override
    public void setSecondToLocation(String toSecondLocation) {
        this.toSecondLocation = toSecondLocation;
    }

    @Override
    public String getSecondFromLocation() {
        return fromSecondLocation;
    }

    @Override
    public void setSecondFromLocation(String fromSecondLocation) {
        this.fromSecondLocation = fromSecondLocation;
    }

    @Override
    public Date getSecondStartDate() {
        return secondStartDate;
    }

    @Override
    public void setSecondStartDate(Date secondStartDate) {
        this.secondStartDate = secondStartDate;
    }

    @Override
    public String getThirdToLocation() {
        return toThirdLocation;
    }

    @Override
    public void setThirdToLocation(String toThirdLocation) {
        this.toThirdLocation = toThirdLocation;
    }

    @Override
    public String getThirdFromLocation() {
        return fromThirdLocation;
    }

    @Override
    public void setThirdFromLocation(String fromThirdLocation) {
        this.fromThirdLocation = fromThirdLocation;
    }

    @Override
    public Date getThirdStartDate() {
        return thirdStartDate;
    }

    @Override
    public void setThirdStartDate(Date thirdStartDate) {
        this.thirdStartDate = thirdStartDate;
    }

    @Override
    public String getFourthToLocation() {
        return toFourthLocation;
    }

    @Override
    public void setFourthToLocation(String toFourthLocation) {
        this.toFourthLocation = toFourthLocation;
    }

    @Override
    public String getFourthFromLocation() {
        return fromFourthLocation;
    }

    @Override
    public void setFourthFromLocation(String fromFourthLocation) {
        this.fromFourthLocation = fromFourthLocation;
    }

    @Override
    public Date getFourthStartDate() {
        return fourthStartDate;
    }

    @Override
    public void setFourthStartDate(Date fourthStartDate) {
        this.fourthStartDate = fourthStartDate;
    }

}
