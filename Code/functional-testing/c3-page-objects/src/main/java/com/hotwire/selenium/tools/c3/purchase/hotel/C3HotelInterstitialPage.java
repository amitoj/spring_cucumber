/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel;

import com.hotwire.selenium.tools.c3.purchase.C3InterstitialPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 3/14/14
 * Time: 7:02 AM
 * Hotel International specific Interstitial page
 */
public class C3HotelInterstitialPage extends C3InterstitialPage {
    public C3HotelInterstitialPage(WebDriver webdriver) {
        super(webdriver, By.className("formContainer"));
    }

    @Override
    public void continueAsGuestUser() {
        clickOnLink("Continue as Guest", DEFAULT_WAIT);
    }

    @Override
    public void search() {
        findOne("form[action='/findCustomer'] button").click();
    }

    @Override
    public void continueAsUser(String email) {
        setText("input[name='customerEmail']", email);
        search();
        clickOnLink("Select user", DEFAULT_WAIT);
    }
}
