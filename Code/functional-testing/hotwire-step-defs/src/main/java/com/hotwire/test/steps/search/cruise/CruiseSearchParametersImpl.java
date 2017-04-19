/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.cruise;

import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.search.BaseSearchParameters;

/**
 * Created with IntelliJ IDEA.
 * User: akrivin
 * Date: 10/16/12
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */

public class CruiseSearchParametersImpl extends BaseSearchParameters implements CruiseSearchParameters {

    private String destination;
    private String cruiseLine;
    private String departurePort;
    private String departureMonth;
    private String cruiseDuration;
    private boolean checkSeniorRates;

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String getCruiseLine() {
        return cruiseLine;
    }

    @Override
    public void setCruiseLine(String cruiseLine) {
        this.cruiseLine = cruiseLine;
    }

    @Override
    public String getDeparturePort() {
        return departurePort;
    }

    @Override
    public void setDeparturePort(String departurePort) {
        this.departurePort = departurePort;
    }

    @Override
    protected PGoodCode getPGoodCode() {
        return PGoodCode.R;
    }

    @Override
    public String getDepartureMonth() {
        return departureMonth;
    }

    @Override
    public void setDepartureMonth(String departureMonth) {
        this.departureMonth = departureMonth;
    }

    @Override
    public String getCruiseDuration() {
        return cruiseDuration;
    }

    @Override
    public void setCruiseDuration(String cruiseDuration) {
        this.cruiseDuration = cruiseDuration;
    }

    @Override
    public boolean getCheckSeniorRates() {
        return checkSeniorRates;
    }

    @Override
    public void setCheckSeniorRates(boolean checkSeniorRates) {
        this.checkSeniorRates = checkSeniorRates;
    }
}

