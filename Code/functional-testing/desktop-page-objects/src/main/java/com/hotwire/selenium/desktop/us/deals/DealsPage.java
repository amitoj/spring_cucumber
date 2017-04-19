/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.deals;

import org.openqa.selenium.WebDriver;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 2/5/14
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DealsPage extends AbstractDealsPage {
    private static final String  ULR = "/deals";

    public DealsPage(WebDriver webDriver) {
        super(webDriver, "tiles-def.deals.index");
    }

    @Override
    public void verifyPage() {
        assertThat(getWebDriver().getCurrentUrl())
            .as("URL for deals page should contains " + ULR)
            .contains(ULR);
    }
}
