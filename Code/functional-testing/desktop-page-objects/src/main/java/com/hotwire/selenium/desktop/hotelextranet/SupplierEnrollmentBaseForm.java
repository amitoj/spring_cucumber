/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Base supplier enrollment form.
 */
public abstract class SupplierEnrollmentBaseForm {
    protected WebDriver webdriver;

    public SupplierEnrollmentBaseForm(WebDriver webdriver) {
        this.webdriver = webdriver;
    }

    public abstract void fillRequiredInputAndSubmit(List<String> inputs);

    public abstract void clickNextButton();
}
