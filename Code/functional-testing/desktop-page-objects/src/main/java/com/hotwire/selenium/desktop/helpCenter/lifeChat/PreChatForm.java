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
 * User: v-abudyak
 * Date: 9/3/13
 * Page object for pre chat form.
 */
public class PreChatForm extends HelpCenterAbstractPage {
    private static final String START_CHAT = ".start.btn";

    public PreChatForm(WebDriver webdriver) {
        super(webdriver, By.cssSelector(".start.btn"));
    }

    public void clickCancel() {
        findOne(".close>img").click();
    }

    public void clickStartChat() {
        findOne(START_CHAT).click();
    }

    public boolean verifyPreChatForm() {
        return isElementDisplayed(By.cssSelector(START_CHAT));
    }

}
