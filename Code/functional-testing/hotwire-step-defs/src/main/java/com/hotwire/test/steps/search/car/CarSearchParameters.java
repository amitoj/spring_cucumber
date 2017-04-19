/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.car;

import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import com.hotwire.test.steps.search.SearchParameters;

import java.util.List;

/**
 * @author LRyzhikov
 * @since  5/30/12
 */
public interface CarSearchParameters {

    SearchParameters getGlobalSearchParameters();

    String getDropOffLocation();

    String getCarType();

    void setCarType(String carType);

    void setDropOffLocation(String dropOffLocation);

    String getPickupTime();

    void setPickupTime(String pickupTime);

    String getDropoffTime();

    void setDropoffTime(String dropoffTime);

    String getDriversAge();

    void setDriversAge(String driversAge);

    String getCountryOfResidence();

    void setCountryOfResidence(String countryOfResidence);

    String getState();

    void setSate(String state);

    List<CarSolutionModel> getSearchSolutionsList();

    void setSearchSolutionsList(List<CarSolutionModel> searchSolutionsList);

    CarSolutionModel getSelectedSearchSolution();

    void setSelectedSearchSolution(CarSolutionModel selectedSearchSolution);
}
