/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class AccountEmailSubscriptionsFormFragment extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountEmailSubscriptionsFormFragment.class);

    private static final String SUBSCRIPTION_FORM_ID = "subscriptionForm";
    private static final String SUBSCRIBE_CLASS = "subscribe";
    private static final String UNSUBSCRIBE_CLASS = "unsubscribe";

    @FindBy(css = "ul[class$='yui3-g']")
    List<WebElement> subscribes;

    /**
     * A map of subscription names, e.g. Big Deals, to whether boolean values
     * indicating whether or not the user is subscribed to it.
     */
    private Map<String, Boolean> subscriptionMap = new HashMap<String, Boolean>();

    public AccountEmailSubscriptionsFormFragment(WebDriver webDriver) {
        super(webDriver, By.id(SUBSCRIPTION_FORM_ID));
        populateSubscriptionMap();
    }

    public boolean isSubscribed(final String subscriptionName) {
        Boolean isSubscribed = subscriptionMap.get(subscriptionName);
        return BooleanUtils.isTrue(isSubscribed);
    }

    public boolean isUnsubscribedFromAll() {
        for (Boolean isSubscribed : subscriptionMap.values()) {
            if (isSubscribed) {
                return false;
            }
        }
        return true;
    }

    private WebElement getSubscribeByName(String name) {
        for (WebElement elm : subscribes) {
            if (name.equals(elm.findElement(By.xpath(".//li//strong")).getText())) {
                LOGGER.info("'{}' is selected..", name);
                return elm;
            }
        }
        return null;
    }

    public void updateSubscriptions(Map<String, Boolean> subscriptionMap) {
        for (Map.Entry<String, Boolean> entry : subscriptionMap.entrySet()) {
            updateSubscription(entry.getKey(), entry.getValue());
        }
    }

    private void updateSubscription(String name, Boolean isSubscribed) {
        // Get <ul> (row) containing all elements for this subscription type.
        WebElement row = getSubscribeByName(name);
        if (isSubscribed) {
            getSubscribeOrUnsubsribeButton(row, SUBSCRIBE_CLASS).click();
        }
        else {
            getSubscribeOrUnsubsribeButton(row, UNSUBSCRIBE_CLASS).click();
        }
    }

    private void populateSubscriptionMap() {
        // Find <ul>s in the form that have input elements.
        for (WebElement row : subscribes) {
            addToSubscriptionMap(row);
        }
    }

    private void addToSubscriptionMap(WebElement row) {
        boolean isSubscribed = isSubscribed(row);
        String description = row.findElement(By.className("description")).findElement(By.tagName("strong")).getText();
        subscriptionMap.put(description, isSubscribed);
    }

    private boolean isSubscribed(WebElement row) {
        WebElement input = getSubscribeOrUnsubsribeButton(row, SUBSCRIBE_CLASS);
        String checked = input.getAttribute("checked");
        return StringUtils.equals(checked, "true");
    }

    private WebElement getSubscribeOrUnsubsribeButton(WebElement subscriptionRow, String cssClass) {
        return subscriptionRow.findElement(By.cssSelector("li." + cssClass + " input"));
    }
}
