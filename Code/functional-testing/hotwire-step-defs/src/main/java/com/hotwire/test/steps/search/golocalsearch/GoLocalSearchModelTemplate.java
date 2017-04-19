/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.golocalsearch;

import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.testing.UnimplementedTestException;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 1/30/14
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoLocalSearchModelTemplate extends SearchModelTemplate implements GoLocalSearchModel {

    @Override
    public void launchSearch() {
        throw  new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void typeLocation(String location) {
        throw  new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void setDates(Date startDate, Date endDate) {
        throw  new UnimplementedTestException("Implement Me!");
    }
}
