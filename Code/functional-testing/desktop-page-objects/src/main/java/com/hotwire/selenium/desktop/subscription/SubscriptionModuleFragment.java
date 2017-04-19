/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.subscription;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.util.webdriver.functions.IsAjaxDone;

/**
 * Page fragment for home page subscription module.
 * @author vjong
 */
public class SubscriptionModuleFragment extends AbstractDesktopPage {

    private static final String SUBSCRIPTION_MODULE = ".SignUp .bannerContainer";
    private static final int BANNER_TEXT_CHANGE_WAIT = 10;

    @FindBy(css = SUBSCRIPTION_MODULE + " button")
    private WebElement submit;

    public SubscriptionModuleFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector(SUBSCRIPTION_MODULE));
        new WebDriverWait(getWebDriver(), BANNER_TEXT_CHANGE_WAIT).until(new IsAjaxDone());
    }

    public boolean isSubscriptionLayerDisplayed() {
        if (getWebDriver().findElements(By.cssSelector(SUBSCRIPTION_MODULE + " .banner")).size() == 0) {
            return false;
        }
        return getWebDriver().findElement(By.cssSelector(SUBSCRIPTION_MODULE + " .banner")).isDisplayed();
    }

    public boolean subscriptionLayerHasCoupon() {
        try {
            getWebDriver().findElement(By.cssSelector(SUBSCRIPTION_MODULE + ".coupon"));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void typeEmail(String email) {
        WebElement emailElement =
            getWebDriver().findElement(By.cssSelector(SUBSCRIPTION_MODULE + " input[name='email']"));
        emailElement.clear();
        emailElement.sendKeys(email);
    }

    public void typeZipcode(String zipCode) {
        WebElement emailElement =
            getWebDriver().findElement(By.cssSelector(SUBSCRIPTION_MODULE + " input[name='zipCode']"));
        emailElement.clear();
        emailElement.sendKeys(zipCode);
    }

    public void clickSubmit() {
        submit.click();
    }

    public void subscribe(String email, String zipCode) {
        // Get old banner text.
        String bannerText =
            getWebDriver().findElement(By.cssSelector(SUBSCRIPTION_MODULE + " .banner")).getText();
        typeEmail(email);
        typeZipcode(zipCode);
        clickSubmit();
        new WebDriverWait(getWebDriver(), BANNER_TEXT_CHANGE_WAIT)
            .until(didBannerTextChange(getWebDriver(), bannerText));
    }

    public boolean subscriptionEmailIsDisplayed() {
        if (getWebDriver().findElements(By.cssSelector(SUBSCRIPTION_MODULE + " input[name='email']")).size() == 0) {
            return false;
        }
        return getWebDriver().findElement(By.cssSelector(SUBSCRIPTION_MODULE + " input[name='email']")).isDisplayed();
    }

    public boolean subscriptionZipCodeIsDisplayed() {
        if (getWebDriver().findElements(By.cssSelector(SUBSCRIPTION_MODULE + " input[name='zipCode']")).size() == 0) {
            return false;
        }
        return getWebDriver().findElement(By.cssSelector(SUBSCRIPTION_MODULE + " input[name='zipCode']")).isDisplayed();
    }

    public boolean isSubscriptionSuccessful() {
        try {
            return getWebDriver().findElement(By.cssSelector(SUBSCRIPTION_MODULE + " .success")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            logger.info("Success element not found. returning false.");
            return false;
        }
    }

    private ExpectedCondition<Boolean> didBannerTextChange(final WebDriver webdriver, final String oldText) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webdriver) {
                new HotelIndexPage(getWebDriver()).getSubscriptionModuleFragment();
                String currentBannerText;
                try {
                    currentBannerText = getWebDriver().findElement(By.cssSelector(
                        SUBSCRIPTION_MODULE + " .banner")).getText();
                    if (currentBannerText != oldText) {
                        return true;
                    }
                    return false;
                }
                catch (WebDriverException e) {
                    return false;
                }
            }
        };
    }
}
