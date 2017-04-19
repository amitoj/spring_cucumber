/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vzyryanov
 * Date: 11/22/12
 * Time: 6:21 AM
 */
public class NotificationBillingPage extends AbstractUSPage {

    @FindBy(xpath = "//div[contains(@class,'ml15')]//div[contains(@class,'headline')]//following-sibling::div")
    private WebElement notificationMessage;

    public NotificationBillingPage(WebDriver webDriver) {
        super(webDriver, "tiles-def.notify");
    }

    public String getNotificationMessage() {
        return notificationMessage.getText().replaceAll("\n", " ");
    }
}
