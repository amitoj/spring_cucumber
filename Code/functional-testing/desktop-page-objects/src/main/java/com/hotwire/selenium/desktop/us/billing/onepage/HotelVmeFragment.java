/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.onepage;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 *
 */
public class HotelVmeFragment extends AbstractUSPage {
    private static final String LIGHTBOX_IFRAME_XPATH = "//iframe[contains(@src, 'v.me')]";
    // We cannot use FindBy here because the elements occur inside an iframe.
    // FindBy binds them to the top iframe.
    private static final String USERNAME_ID = "login_userName";
    private static final String PASSWORD_ID = "login_password";
    private static final String SIGN_IN_BUTTON_ID = "#sign_in_button, #v-sign-in-button";
    private static final String CONTINUE_BUTTON_XPATH = "//button[contains(text(), 'Continue')]";

    private static final int DEFAULT_WAIT = 10;

    @FindBy(xpath = "//div[@class='cardMethod']/input[@radiobtnname='vme']")
    private WebElement vmePaymentTypeRadioButton;

    @FindBy(xpath = "//div[@class='vmePaymentFields']//button")
    private WebElement vmeBuyButton;

    @FindBy(xpath = LIGHTBOX_IFRAME_XPATH)
    private WebElement vmeIframe;

    public HotelVmeFragment(WebDriver webDriver) {
        super(webDriver, By.id("vmePaymentFields"));
    }

    public void fillVmePaymentMethodPanel(String username, String password) {

        // Choose Vme as payment method
        this.vmePaymentTypeRadioButton.click();

        // Click the Vme buy button to launch the lightbox
        this.vmeBuyButton.click();

        // Switch to the lightbox iframe
        getWebDriver().switchTo().frame(this.vmeIframe);

        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
            .until(PageObjectUtils.webElementVisibleTestFunction(By.id(USERNAME_ID), true));
        getWebDriver().findElement(By.id(USERNAME_ID)).clear();
        getWebDriver().findElement(By.id(USERNAME_ID)).sendKeys(username);
        getWebDriver().findElement(By.id(PASSWORD_ID)).clear();
        getWebDriver().findElement(By.id(PASSWORD_ID)).sendKeys(password);

        getWebDriver().findElement(By.cssSelector(SIGN_IN_BUTTON_ID)).click();
        // We assume sign in was successful here...

        // Switch to the main billing window
        getWebDriver().switchTo().defaultContent();
        // Switch to the lightbox iframe
        getWebDriver().switchTo().frame(this.vmeIframe);
        WebDriverWait wait = new WebDriverWait(getWebDriver(), DEFAULT_WAIT);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CONTINUE_BUTTON_XPATH))).click();

        // Done interacting with lightbox, switch back to main billing window
        getWebDriver().switchTo().defaultContent();

        // Wait for the lightbox to go away
        wait = new WebDriverWait(getWebDriver(), DEFAULT_WAIT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LIGHTBOX_IFRAME_XPATH)));
    }

    public boolean isEnabled() {
        return vmePaymentTypeRadioButton.isEnabled();
    }
}
