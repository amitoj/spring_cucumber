/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.billing;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents the mobile car insurance page
 *
 * @author prbhat
 *
 */
public class CarInsuranceFragment extends MobileAbstractPage {

    // Get the radio button that corresponds to
    // "Yes, add rental car protection ... "
    @FindBy(id = "rentalCarProtectionInsuranceRequested1")
    private WebElement cdwInsuranceOption;

    @FindBy(className = "submit")
    private WebElement btnInsuranceInfo;

    public CarInsuranceFragment(WebDriver webdriver, String expectedPageName) {
        super(webdriver, expectedPageName);
    }

    /**
     * If the insurance option is selected, then click on the radio button that
     * opts you in and continue. If not, just continue as the default state of
     * insurance is opted out
     *
     * @param isInsuranceSelected
     *            does the user want to get CDW insurance
     */
    public void continuePanel(boolean isInsuranceSelected) {

        if (isInsuranceSelected) {
            cdwInsuranceOption.click();
        }

        btnInsuranceInfo.click();
    }
}
