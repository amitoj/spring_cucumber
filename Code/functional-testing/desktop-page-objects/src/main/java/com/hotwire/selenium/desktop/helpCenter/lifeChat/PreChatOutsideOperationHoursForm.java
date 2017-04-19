/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.helpCenter.lifeChat;

import com.hotwire.selenium.desktop.helpCenter.HelpCenterAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-asnitko
 * Date: 4/7/14
 * Page object for live chat pop-up when it is outside operation hours.
 */
public class PreChatOutsideOperationHoursForm extends HelpCenterAbstractPage {

    public PreChatOutsideOperationHoursForm(WebDriver webdriver) {
        super(webdriver, By.className("unavailable"));
    }

    public void clickClose() {
        findOne("button.close").click();
    }

    public String getPreChatFomText() {
        return findOne("div.body").getText();
    }
}
