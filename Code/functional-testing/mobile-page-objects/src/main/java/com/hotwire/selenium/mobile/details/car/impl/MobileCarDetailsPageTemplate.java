/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.details.car.impl;

import com.hotwire.selenium.mobile.details.car.MobileCarDetailsPage;
import com.hotwire.selenium.mobile.models.CarSolutionModel;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: v-jolopez
 * Date: 12/11/14
 */

public abstract class MobileCarDetailsPageTemplate extends AbstractPageObject implements MobileCarDetailsPage {
    protected static final Logger LOGGER = LoggerFactory.getLogger(MobileCarDetailsPageTemplate.class);

    protected MobileCarDetailsPageTemplate(WebDriver webDriver, By container, String[] pageNames) {
        super(webDriver, container, pageNames);
    }

    protected MobileCarDetailsPageTemplate(WebDriver webDriver, String[] pageNames) {
        super(webDriver, pageNames);
    }


    @Override
    public void select() {
        throw new RuntimeException("Implement me!");
    }

    @Override
    public void verifyDetailsPageInfo(CarSolutionModel carModel) {
        throw new RuntimeException("Implement me!");
    }
}
