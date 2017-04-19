/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car.fragments.fareFinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 4/9/13
 * Time: 5:21 AM
 */
public interface CarFareFinder {

    void run();

    CarFareFinder setPickUpLocation(String value);

    CarFareFinder setDropOffLocation(String value);

    CarFareFinder setPickUpDate(Date date);

    CarFareFinder setDropOffDate(Date date);

    CarFareFinder setPickUpTime(String time);

    CarFareFinder setDropOffTime(String time);

    String getPickUpLocation();

    String getDropOffLocation();

    String getPickUpDate();

    String getDropOffDate();

    String getPickUpTime();

    String getDropOffTime();

    SimpleDateFormat getDateFormat();

    String getPickUpDatePlaceholder();

    String getDropOffDatePlaceholder();

    List<String> getExpectedListOfTime();

    List<String> getListOfTimes(String type);
}
