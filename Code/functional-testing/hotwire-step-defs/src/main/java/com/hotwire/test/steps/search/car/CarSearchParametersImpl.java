/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.car;

import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.search.BaseSearchParameters;

import java.util.List;

/**
 * @author  LRyzhikov
 * @since  5/30/12
 */
public class CarSearchParametersImpl extends BaseSearchParameters implements CarSearchParameters {

    private String carType;
    private String dropOffLocation;
    private String pickupTime;
    private String dropoffTime;
    private String driversAge;
    private String countryOfResidence;
    private String state;
    private CarSolutionModel selectedSearchSolution;
    private List<CarSolutionModel> searchSolutionsList;

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    @Override
    public String getDropOffLocation() {
        return this.dropOffLocation;
    }

    @Override
    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    @Override
    public String getPickupTime() {
        return pickupTime;
    }

    @Override
    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    @Override
    public String getDropoffTime() {
        return dropoffTime;
    }

    @Override
    public void setDropoffTime(String dropoffTime) {
        this.dropoffTime = dropoffTime;
    }

    @Override
    public String getDriversAge() {
        return driversAge;
    }

    @Override
    public void setDriversAge(String driversAge) {
        this.driversAge = driversAge;
    }

    @Override
    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    @Override
    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setSate(String state) {
        this.state = state;
    }

    @Override
    protected PGoodCode getPGoodCode() {
        return PGoodCode.C;
    }

    public List<CarSolutionModel> getSearchSolutionsList() {
        return searchSolutionsList;
    }

    public void setSearchSolutionsList(List<CarSolutionModel> searchSolutionsList) {
        this.searchSolutionsList = searchSolutionsList;
    }

    public CarSolutionModel getSelectedSearchSolution() {
        return selectedSearchSolution;
    }

    public void setSelectedSearchSolution(CarSolutionModel selectedSearchSolution) {
        this.selectedSearchSolution = selectedSearchSolution;
    }
}
