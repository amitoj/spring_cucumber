/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.refund;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-abudyak
 * Date: 11/1/12
 * Time: 9:27 AM
 */
public class C3RefundConfirmationPage extends ToolsAbstractPage {

    public C3RefundConfirmationPage(WebDriver webDriver) {
        super(webDriver, By.id("errorMessaging"));
    }

    public String getRefundConfirmationText() {
        return findOne("#errorMessaging").getText();
    }

    public void openItineraryDetailsViaBreadcrumb() {
        findOne("form[name='purchaseDetailForm']").submit();
    }
}
