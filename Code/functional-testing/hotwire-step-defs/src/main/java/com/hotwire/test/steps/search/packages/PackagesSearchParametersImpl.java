/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.packages;

import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.search.BaseSearchParameters;

import java.util.Date;

/**
 * @author akrivin
 * @since 10/16/12
 */
public class PackagesSearchParametersImpl extends BaseSearchParameters
    implements PackagesSearchParameters {

    private String vacationPackage;
    private String origLocation;
    private Integer numberOfHotelRooms;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private Integer numberOfSeniors;
    private Integer numberOfChildAge;
    private String startAnytime;
    private String endAnytime;
    private Date hotelStartdate;
    private Date hotelEnddate;

    @Override
    public Date gethotelStartdate() {
        return hotelStartdate;
    }

    @Override
    public void sethotelStartdate(Date hotelStartdate) {
        this.hotelStartdate = hotelStartdate;
    }

    @Override
    public Date gethotelEnddate() {
        return hotelEnddate;
    }

    @Override
    public void sethotelEnddate(Date hotelEnddate) {
        this.hotelEnddate = hotelEnddate;
    }

    @Override
    public String getVacationPackage() {
        return vacationPackage;
    }

    @Override
    public void setvacationPackage(String vacationPackage) {
        this.vacationPackage = vacationPackage;
    }

    @Override
    public String getOrigLocation() {
        return origLocation;
    }

    @Override
    public void setOrigLocation(String origLocation) {
        this.origLocation = origLocation;
    }

    @Override
    public String getStartAnytime() {
        return startAnytime;
    }

    @Override
    public void setStartAnytime(String startAnytime) {
        this.startAnytime = startAnytime;
    }

    @Override
    public String getEndAnytime() {
        return endAnytime;
    }

    @Override
    public void setEndAnytime(String endAnytime) {
        this.endAnytime = endAnytime;
    }

    @Override
    public Integer getNumberOfHotelRooms() {
        return numberOfHotelRooms;
    }

    @Override
    public void setNumberOfHotelRooms(Integer numberOfHotelRooms) {
        this.numberOfHotelRooms = numberOfHotelRooms;
    }

    @Override
    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    @Override
    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    @Override
    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    @Override
    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    @Override
    public Integer getNumberOfSeniors() {
        return numberOfSeniors;
    }

    @Override
    public void setNumberOfSeniors(Integer numberOfSeniors) {
        this.numberOfSeniors = numberOfSeniors;
    }

    @Override
    public Integer getNumberOfChildAge() {
        return numberOfChildAge;
    }

    @Override
    public void setNumberOfChildAge(Integer numberOfChildAge) {
        this.numberOfChildAge = numberOfChildAge;
    }

    @Override
    protected PGoodCode getPGoodCode() {
        return PGoodCode.P;
    }

}
