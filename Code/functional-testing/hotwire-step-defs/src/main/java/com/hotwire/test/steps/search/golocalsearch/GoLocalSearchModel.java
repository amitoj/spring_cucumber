/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.golocalsearch;

import com.hotwire.test.steps.search.SearchModel;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 1/30/14
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GoLocalSearchModel extends SearchModel  {

    void launchSearch();

    void typeLocation(String location);

    void setDates(Date startDate, Date endDate);


}
