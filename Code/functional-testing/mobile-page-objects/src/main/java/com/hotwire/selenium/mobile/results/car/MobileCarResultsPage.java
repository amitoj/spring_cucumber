/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.results.car;

import com.hotwire.selenium.mobile.models.CarSolutionModel;

/**
 * User: achotemskoy
 * Date: 12/17/14
 * Time: 5:31 PM
 *
 */
public interface MobileCarResultsPage {

    CarSolutionModel select(Integer resultNumberToSelect, String opaqueRetail);

    String getSearchDatesAndTime();

}
