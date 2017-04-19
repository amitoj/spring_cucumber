/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
/**
 *
 */
package com.hotwire.selenium.mobile.account;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

/**
 * This represents the mobile version of the subscription management page
 *
 * @author prbhat
 */
public class MobileAccountEmailSubscriptionsPage extends MobileAbstractPage {

    @FindBy(id = "subscriptionMap11")
    private WebElement personalizedHotRateAlerts;

    @FindBy(id = "subscriptionMap21")
    private WebElement savingsNotices;

    @FindBy(id = "subscriptionMap31")
    private WebElement bigDeals;

    @FindBy(id = "subscriptionMap41")
    private WebElement tripWatcher;

    @FindBy(id = "subscriptionMap81")
    private WebElement cruiseNews;

    @FindBy(xpath = "//form//button")
    private WebElement saveChangesButton;

    @FindBy(xpath = "//div[@class='msgBoxHeader' and contains(text(),'Confirmation')]")
    private WebElement confirmationText;

    @FindBy(name = "unsubscribeAll")
    private WebElement unsubscribeAll;

    public MobileAccountEmailSubscriptionsPage(WebDriver webdriver) {
        super(webdriver, new String[]{"tile.account.email.subscription"});
    }

    public void unsubscribeAll() {
        this.unsubscribeAll.click();
    }

    public void subscribeAll() {
        if (!this.personalizedHotRateAlerts.isSelected()) {
            this.personalizedHotRateAlerts.click();
        }

        if (!this.savingsNotices.isSelected()) {
            this.savingsNotices.click();
        }

        if (!this.bigDeals.isSelected()) {
            this.bigDeals.click();
        }

        if (!this.tripWatcher.isSelected()) {
            this.tripWatcher.click();
        }

        if (!this.cruiseNews.isSelected()) {
            this.cruiseNews.click();
        }
    }

    public void updateSubscriptions(Map<String, Boolean> subscriptionMap) {
        for (Map.Entry<String, Boolean> entry : subscriptionMap.entrySet()) {
            updateSubscription(entry.getKey(), entry.getValue());
        }
    }

    private void updateSubscription(String subscription, Boolean stateOfSubscription) {
        WebElement subscriptionToUpdate = getSubscriptionCorrespondingToName(subscription);

        if ((stateOfSubscription && !subscriptionToUpdate.isSelected()) || // Check the box
            (!stateOfSubscription && subscriptionToUpdate.isSelected())) { // Uncheck the box
            subscriptionToUpdate.click();
        }
    }

    public void saveChanges() {
        this.saveChangesButton.submit();
    }

    public boolean isConfirmationVisible() {
        return this.confirmationText.isDisplayed();
    }

    public boolean isUnsubscribedFromAll() {
        boolean isUnsubscribedFromAll = true;
        if (this.personalizedHotRateAlerts.isSelected() || this.savingsNotices.isSelected() ||
            this.bigDeals.isSelected() || this.tripWatcher.isSelected() || this.cruiseNews.isSelected()) {
            isUnsubscribedFromAll = false;
        }

        return isUnsubscribedFromAll;
    }

    public boolean isSubscribed(String subscriptionName) {
        WebElement subscriptionToCheck = getSubscriptionCorrespondingToName(subscriptionName);
        return subscriptionToCheck.isSelected();
    }

    private WebElement getSubscriptionCorrespondingToName(String subscriptionName) {
        WebElement subscriptionToReturn = null;
        if ("Personalized Hot Rate Alerts".equalsIgnoreCase(subscriptionName)) {
            subscriptionToReturn = this.personalizedHotRateAlerts;
        }
        else if ("Savings Notices".equalsIgnoreCase(subscriptionName)) {
            subscriptionToReturn = this.savingsNotices;
        }
        else if ("Big Deals".equalsIgnoreCase(subscriptionName)) {
            subscriptionToReturn = this.bigDeals;
        }
        else if ("Trip Watcher".equalsIgnoreCase(subscriptionName)) {
            subscriptionToReturn = this.tripWatcher;
        }
        else if ("Cruise News".equalsIgnoreCase(subscriptionName)) {
            subscriptionToReturn = this.cruiseNews;
        }

        if (subscriptionToReturn == null) {
            // We did not find the element to update. Throw an error
            throw new IllegalArgumentException("Could not find subscription " + subscriptionName +
                                               " in mobile subscription page");
        }
        else {
            return subscriptionToReturn;
        }
    }

}
