/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.hotelConsumer;

import com.hotwire.selenium.tools.myAccount.HWDealsHeader;
import com.hotwire.test.steps.tools.ToolsAbstractModel;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: v-vyulun
 * Date: 9/24/14
 * Time: 1:36 PM
 * The model for hotel consumer steps and page objects.
 */
public class C3HotelConsumerModel extends ToolsAbstractModel {

    public void verifyDealMaintenanceTab(String vertical) {
        assertThat(new HWDealsHeader(getWebdriverInstance()).checkDealMaintenanceTab(vertical))
                .as(vertical + " is not available").isTrue();
    }

    public void clickDealMaintenanceTab(String vertical) {
        new HWDealsHeader(getWebdriverInstance()).clickDealMaintenanceTab(vertical);
    }

    public void clickPreviewButton() {
        new HWDealsHeader(getWebdriverInstance()).clickPreviewButton();
    }

    public void executeAnyQuery() {
        HWDealsHeader pg = new HWDealsHeader(getWebdriverInstance());
        pg.selectAnyQuery();
        pg.clickExecuteButton();
    }

    public void checkXIDisDisplayed(String param) {
        HWDealsHeader pg = new HWDealsHeader(getWebdriverInstance());
        if (param == null) {
            assertThat(pg.isXIDDisplayed()).as("XID is available, but have NOT to").isTrue();
        }
        else {
            assertThat(pg.isXIDDisplayed()).as("XID is NOT available, but have to").isFalse();
        }
    }

    public void checkDealsAreDisplayed() {
        HWDealsHeader pg = new HWDealsHeader(getWebdriverInstance());
        assertThat(pg.checkDealsAreDisplayed()).as("Deals are NOT available, but have to").isTrue();
    }
}
