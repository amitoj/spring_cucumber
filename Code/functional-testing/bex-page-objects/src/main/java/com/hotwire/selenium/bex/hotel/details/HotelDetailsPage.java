/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.bex.hotel.details;

import com.hotwire.selenium.bex.BexAbstractPage;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelDetailsPage extends BexAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelDetailsPage.class.getSimpleName());

    @FindBy(xpath = "//div[@class='book-button-wrapper']/button")
    private WebElement recommendedBookButton;

    @FindBy(css = "span.search-summary")
    private WebElement searchSummary;


    public HotelDetailsPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("#hotel-name"));
        PageFactory.initElements(getWebDriver(), this);
    }

    public void selectRecommendedRoom() {
        recommendedBookButton.click();
    }

    public void selectPayNow() {
        try {
            WebDriver webdriver = getWebDriver();
            new WebDriverWait(webdriver, DEFAULT_WAIT * 2).until(
                    new IsElementLocationStable(webdriver, By.cssSelector("#pay-now-button"), 4));
            getWebDriver().findElement(By.id("pay-now-button")).click();
        }
        catch (RuntimeException ex) {
            LOGGER.info("Pay now button was not displayed.");
        }
    }

    public String getSearchSummary() {
        return searchSummary.getText();
    }
}

