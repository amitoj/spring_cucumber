/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.livechat;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LivePreChatUSPage extends AbstractUSPage {

    @FindBy(name = "firstName")
    private WebElement firstName;

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(name = "topic")
    private WebElement topicDropDown;

    @FindBy(css = ".call")
    private WebElement callHelpInfo;

    @FindBy(css = ".close")
    private WebElement cancelButton;

    @FindBy(css = ".start")
    private WebElement startChatButton;

    public LivePreChatUSPage(WebDriver webdriver) {
        super(webdriver, "tiles-def.chat.pre-chat", new VisibilityOf(By.cssSelector(".preChat form[name='preChat']")),
              15);
    }

    public void cancelChat() {
        cancelButton.click();
    }
}
