/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.widget;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.AbstractPage;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * Created with IntelliJ IDEA.
 * User: v-vzyryanov
 * Date: 10/2/12
 * Time: 7:48 AM
 */
public class ErrorMessenger extends AbstractPage {

    /**
     * This is a counter for error message type
     */
    public enum MessageType {
        error, priceCheck, successful
    }

    private static final String ERROR_MESSAGE_CONTAINER_LOCATOR = "div.errorMessages";

    private static final String SUCCESS_MESSAGE_CONTAINER_CLASS_NAME = "successMessages";

    private static final String PRICE_CHECK_MESSAGE_CONTAINER_CLASS_NAME = "priceCheckMsg";

    private static final String PRICE_CHECK_MESSAGE_CSS = ".priceCheckMsg, .unavailableMessage, .priceChangeMessage";

    List<WebElement> messageBlock;

    @FindBy(css = "div.errorMessages")
    private List<WebElement> errorMessagesBlock;

    @FindBy(css = "." + SUCCESS_MESSAGE_CONTAINER_CLASS_NAME)
    private List<WebElement> successMessagesBlock;

    @FindBy(css = "." + PRICE_CHECK_MESSAGE_CONTAINER_CLASS_NAME)
    private List<WebElement> priceCheckBlock;

    private MessageType messageType;

    public ErrorMessenger(WebDriver webdriver) {
        super(webdriver);
    }

    private List<WebElement> getMessageBlock() {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 20);
        switch (messageType) {
            case successful:
                wait.until(new IsElementLocationStable(getWebDriver(),
                        By.cssSelector("." + SUCCESS_MESSAGE_CONTAINER_CLASS_NAME), 5));
                messageBlock = getWebDriver().findElements(By.cssSelector("." + SUCCESS_MESSAGE_CONTAINER_CLASS_NAME));
                break;
            case priceCheck:
                wait.until(new IsElementLocationStable(getWebDriver(),
                        By.cssSelector(PRICE_CHECK_MESSAGE_CSS), 5));
                messageBlock = getWebDriver().findElements(By.cssSelector(PRICE_CHECK_MESSAGE_CSS));
                break;
            default:
                wait.until(new IsElementLocationStable(getWebDriver()
                        , By.cssSelector(ERROR_MESSAGE_CONTAINER_LOCATOR), 5));
                messageBlock = getWebDriver().findElements(By.cssSelector(ERROR_MESSAGE_CONTAINER_LOCATOR));
                break;
        }
        return messageBlock;
    }

    public List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        List<WebElement> webElementList = getMessageBlock();

        if (messageType.toString().equals("priceCheck")) {
            for (WebElement blk : webElementList) {
                if (blk.isDisplayed()) {
                    messages.add(blk.getText().trim());
                }
            }
        }
        else {
            for (WebElement blk : webElementList) {
                if (blk.isDisplayed()) {
                    for (WebElement el : blk.findElements(By.cssSelector("ul li"))) {
                        messages.add(el.getText().trim());
                    }
                }
            }
        }
        return messages;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public List<WebElement> getErrorMessagesBlock() {
        return errorMessagesBlock;
    }
}
