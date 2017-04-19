/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.cruise;

import com.hotwire.test.steps.search.SearchParameters;

/**
 * Created with IntelliJ IDEA.
 * User: akrivin
 * Date: 10/11/12
 */
public interface CruiseSearchParameters {

    SearchParameters getGlobalSearchParameters();

    String getDestination();

    void setDestination(String destination);

    String getCruiseLine();

    void setCruiseLine(String cruiseLine);

    String getDeparturePort();

    void setDeparturePort(String departurePort);

    String getDepartureMonth();

    void setDepartureMonth(String departureMonth);

    String getCruiseDuration();

    void setCruiseDuration(String cruiseDuration);

    boolean getCheckSeniorRates();

    void setCheckSeniorRates(boolean checkSeniorRates);
}
