/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.details.car;

import com.hotwire.selenium.mobile.models.CarSolutionModel;

/**
 * User: v-jolopez
 * Date: 12/11/14
 */
public interface MobileCarDetailsPage {

    void continueSubmit();

    void select();

    void verifyDetailsPageInfo(CarSolutionModel carModel);
}


