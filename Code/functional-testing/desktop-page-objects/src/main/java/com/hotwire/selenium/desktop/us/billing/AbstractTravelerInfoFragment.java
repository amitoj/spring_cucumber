/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * Created by IntelliJ IDEA.
 * User: jreichenberg
 * Date: 7/17/12
 * Time: 12:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractTravelerInfoFragment extends AbstractUSPage {

    @FindBy(name = "travelerForm.phoneNo")
    protected WebElement primaryPhoneNumber;

    @FindBy(name = "travelerForm._NAE_email")
    protected WebElement emailId;

    /**
     * When user was authenticated
     * Email is displayed on Traveler Information block
     */
    @FindBy(css = "div#changeEmailField div strong")
    protected WebElement userSavedEmail;

    @FindBy(name = "travelerForm._NAE_confirmEmail")
    protected WebElement confirmEmailId;

    @FindBy(name = "btnTravelerInfo")
    protected WebElement travelerContinueBtn;

    public AbstractTravelerInfoFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector("li#billingPanelTravelerInfoComp, div.billingSection, .travelerInfoModule"));
    }
}
