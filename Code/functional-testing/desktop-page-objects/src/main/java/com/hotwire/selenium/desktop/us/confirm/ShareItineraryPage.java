/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.confirm;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 8/13/14
 * Time: 1:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShareItineraryPage extends AbstractUSPage {
    private static final String  SHARE_ITINERARY_LAYER = "shareItineraryPopup-panel_c";
    private static final String CONFIRMATION_MESSAGE = ".//div[@class='itineraryShareConfirmLayer buttons']" +
            "//strong[contains(text(), 'Your itinerary has been shared')]";

    @FindBy(css = "input[name='_NAE_email']")
    private WebElement recipientEmail;

    @FindBy(css = "input[name='senderName']")
    private WebElement yourName;

    @FindBy(css = "input[name='senderEmail']")
    private WebElement senderEmail;

    @FindBy(css = ".textMessage")
    private WebElement textMessage;

    @FindBy(xpath = ".//form[@class='shareItineraryForm']//img[contains(@alt, 'Cancel')]//..")
    private WebElement cancelButton;

    @FindBy(xpath = ".//form[@class='shareItineraryForm']//img[contains(@alt, 'Share')]//..")
    private WebElement shareButton;



    @FindBy(xpath = ".//div[@class='itineraryShareConfirmLayer buttons']//button[@type='submit']")
    private WebElement continueButton;

    public ShareItineraryPage(WebDriver webdriver) {
        super(webdriver, By.id(SHARE_ITINERARY_LAYER));
    }

    public WebElement getCancelButton() {
        return cancelButton;
    }

    public boolean isVisible() {
        return getWebDriver().findElement(By.id(SHARE_ITINERARY_LAYER)).isDisplayed();
    }

    public void shareItinerary(String to) {
        recipientEmail.clear();
        recipientEmail.sendKeys(to);
        shareButton.click();
    }

    public boolean isSharedSuccessfully() {
        new WebDriverWait(getWebDriver(), 20)
                .until(new IsElementLocationStable(getWebDriver(), By.xpath(CONFIRMATION_MESSAGE), 10));
        return getWebDriver().findElement(By.xpath(CONFIRMATION_MESSAGE)).isDisplayed();
    }

    public WebElement getContinueButton() {
        return continueButton;
    }
}
