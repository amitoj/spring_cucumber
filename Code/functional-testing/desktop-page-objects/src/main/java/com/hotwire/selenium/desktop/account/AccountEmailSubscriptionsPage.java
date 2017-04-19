/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

/**
 *
 */
public class AccountEmailSubscriptionsPage extends AbstractAccountPage {

    @FindBy(linkText = "Unsubscribe all")
    private WebElement unsubscribeAllButton;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveChangesButton;

    @FindBy(xpath = "//div[@class='msgBoxHeader' and contains(text(),'Confirmation')]")
    private WebElement confirmationText;

    @FindBy(id = "subscriptionStateMap81")
    private WebElement cruisenewsUnSubscribe;

    @FindBy(id = "subscriptionStateMap82")
    private WebElement cruisenewsSubscribe;

    @FindBy(id = "subscriptionStateMap42")
    private WebElement tripWatcherSubscribe;

    public AccountEmailSubscriptionsPage(WebDriver driver) {
        super(driver, "tile.account.emailsubscriptions");
    }

    public void unsubscribeAll() {
        unsubscribeAllButton.click();
    }

    public void saveChanges() {
        saveChangesButton.submit();
    }

    public boolean isConfirmationVisible() {
        return confirmationText.isDisplayed();
    }

    public void updateSubscriptions(Map<String, Boolean> subscriptionMap) {
        getSubscriptionForm().updateSubscriptions(subscriptionMap);
    }

    public boolean isUnsubscribedFromAll() {
        return getSubscriptionForm().isUnsubscribedFromAll();
    }

    public boolean isSubscribed(String subscriptionName) {
        return getSubscriptionForm().isSubscribed(subscriptionName);
    }

    public boolean isCruiseNewsSubscribe() {
        try {
            return cruisenewsSubscribe.isSelected();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCruiseNewsUnSubscribe() {
        try {
            return cruisenewsUnSubscribe.isSelected();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void tripWatcherSubscribe() {
        try {
            tripWatcherSubscribe.click();
            saveChangesButton.click();
        }
        catch (NoSuchElementException e) {
            // No op
        }
    }

    AccountEmailSubscriptionsFormFragment getSubscriptionForm() {
        return new AccountEmailSubscriptionsFormFragment(getWebDriver());
    }
}
