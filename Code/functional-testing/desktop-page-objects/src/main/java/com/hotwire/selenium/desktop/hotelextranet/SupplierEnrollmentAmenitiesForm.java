/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Supplier enrollment hotel amenities form page object.
 *
 * TODO(vjong@hotwire.com): Add WebElements as needed since the happy path of enrollment flow does not require any
 *                          of the page's fields to be filled in.
 */
public class SupplierEnrollmentAmenitiesForm extends SupplierEnrollmentBaseForm {
    @FindBy(id = "wfPageNextId4")
    private WebElement nextButton;

    public SupplierEnrollmentAmenitiesForm(WebDriver webdriver) {
        super(webdriver);
    }

    @Override
    public void fillRequiredInputAndSubmit(List<String> inputs) {
        clickNextButton();
    }

    @Override
    public void clickNextButton() {
        nextButton.click();
    }
}
