/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.discount.air;

import com.hotwire.selenium.desktop.us.index.AirIndexPage;
import com.hotwire.test.steps.discount.DiscountModelTemplate;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: v-ikomarov
 * Date: 1/19/14
 * Time: 6:16 PM
 */
public class AirDiscountModelWebApp extends DiscountModelTemplate {

    @Override
    public void setApplicationUrl(URL applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    @Override
    public void navigateToLandingPage() {
        try {
            URL airLandingPageUrl = new URL(applicationUrl, "air/index.jsp");
            getWebdriverInstance().navigate().to(airLandingPageUrl);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void verifyLandingPage() {
        new AirIndexPage(getWebdriverInstance());
    }
}
