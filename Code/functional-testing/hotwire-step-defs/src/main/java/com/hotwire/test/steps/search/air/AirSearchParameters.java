/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.air;

import java.util.Date;

import com.hotwire.test.steps.search.SearchParameters;

/**
 * Created with IntelliJ IDEA.
 * User: LRyzhikov
 * Date: 5/30/12
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AirSearchParameters {

    SearchParameters getGlobalSearchParameters();

    String getOrigLocation();

    void setOrigLocation(String origLocation);

    void setPassengers(Integer passengers);

    Integer getPassengers();

    String getSpVisiableStatus();

    void setSpVisiableStatus(String spVisiableStatus);

    String getColumnAStatus();

    void setColumnAStatus(String columnAStatus);
    String getFirstToLocation();

    void setFirstToLocation(String toLocation);

    String getFirstFromLocation();

    void setFirstFromLocation(String fromLocation);

    Date getFirstStartDate();

    void setFirstStartDate(Date startDate);

    String getSecondToLocation();

    void setSecondToLocation(String toLocation);

    String getSecondFromLocation();
    void setSecondFromLocation(String fromLocation);

    Date getSecondStartDate();

    void setSecondStartDate(Date startDate);

    String getThirdToLocation();

    void setThirdToLocation(String toLocation);

    String getThirdFromLocation();

    void setThirdFromLocation(String fromLocation);

    Date getThirdStartDate();

    void setThirdStartDate(Date startDate);

    String getFourthToLocation();

    void setFourthToLocation(String toLocation);

    String getFourthFromLocation();

    void setFourthFromLocation(String fromLocation);

    Date getFourthStartDate();

    void setFourthStartDate(Date startDate);

}
