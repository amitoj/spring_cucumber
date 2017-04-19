/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.packages;

import java.util.Date;

import com.hotwire.test.steps.search.SearchParameters;

/**
 * Created with IntelliJ IDEA. User: akrivin Date: 10/11/12
 */
public interface PackagesSearchParameters {

    SearchParameters getGlobalSearchParameters();

    String getVacationPackage();

    void setvacationPackage(String vacationPackage);

    String getOrigLocation();

    void setOrigLocation(String origLocation);

    String getStartAnytime();

    void setStartAnytime(String startAnytime);

    String getEndAnytime();

    void setEndAnytime(String endAnytime);

    Integer getNumberOfHotelRooms();

    void setNumberOfHotelRooms(Integer numberOfHotelRooms);

    Integer getNumberOfAdults();

    void setNumberOfAdults(Integer numberOfAdults);

    Integer getNumberOfChildren();

    void setNumberOfChildren(Integer numberOfChildren);

    Integer getNumberOfSeniors();

    void setNumberOfSeniors(Integer numberOfChildren);

    Integer getNumberOfChildAge();

    void setNumberOfChildAge(Integer numberOfChildAge);

    Date gethotelStartdate();

    void sethotelStartdate(Date hotelStartdate);

    Date gethotelEnddate();

    void sethotelEnddate(Date hotelEnddate);


}
