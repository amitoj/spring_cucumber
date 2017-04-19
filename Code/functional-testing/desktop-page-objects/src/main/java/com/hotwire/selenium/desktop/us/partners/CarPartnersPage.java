/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.partners;

import java.util.Date;

/**
 * User: v-vzyryanov
 * Date: 12/17/12
 * Time: 4:52 AM
 */
public interface CarPartnersPage {

    String QUERY_FORMAT = "%LOCATION% %PICKUPDATE% - %DROPOFFDATE%";
    public enum NAME { NONE, EXPEDIA, CR, BBUDDY, KAYAK, ORBITZ }

    NAME getName();

    String getActualSearchQuery();

    String getExpectedSearchQuery(Date pickUp, Date dropOff);

    void close();
}
