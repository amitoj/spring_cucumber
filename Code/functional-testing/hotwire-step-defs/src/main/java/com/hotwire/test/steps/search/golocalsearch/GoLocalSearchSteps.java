/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.golocalsearch;


import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.transformer.jchronic.ChronicConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 1/30/14
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoLocalSearchSteps extends AbstractSteps {

    @Autowired
    @Qualifier("goLocalSearchModel")
    private GoLocalSearchModel goLocalSearchModel;

    @Given("^I am searching (.*) location via GoLocalSearch page$")
    public void setLocation(String location) {
        goLocalSearchModel.typeLocation(location);
    }

    @Given("^I want to travel via GoLocalSearch page between (.*) and (.*)$")
    public void setSearchDates(@Transform(ChronicConverter.class)
                               Calendar start, @Transform(ChronicConverter.class) Calendar end) {
        goLocalSearchModel.setDates(start.getTime(), end.getTime());
    }

    @And("^I launch a search on the GoLocalSearch page$")
    public  void launchSearch() {
        goLocalSearchModel.launchSearch();
    }

}
