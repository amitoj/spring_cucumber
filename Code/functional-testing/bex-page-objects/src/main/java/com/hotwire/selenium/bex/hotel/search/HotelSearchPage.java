/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.bex.hotel.search;

import com.hotwire.selenium.bex.BexAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/5/15
 * Time: 2:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotelSearchPage extends BexAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelSearchPage.class.getSimpleName());

    @FindBy(id = "inpSearchNear")
    private WebElement searchTextBox;

    @FindBy(id = "hotelNewSearchLnk")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id='inpStartDate']")
    private WebElement startDateField;

    @FindBy(xpath = "//*[@id='inpEndDate']")
    private WebElement endDateField;


    public HotelSearchPage(WebDriver webdriver) {
        super(webdriver, By.id("experiments"));
        PageFactory.initElements(getWebDriver(), this);
    }

    public void selectProxDates() {
        if (startDateField != null) {

            startDateField.click();
            WebElement dateWidget = getWebDriver().findElement(By.xpath("//ul[@class='cal-dates']"));
            List<WebElement> options = dateWidget.findElements(By.tagName("li"));
            for (WebElement opt : options) {
                try {
                    opt.findElement(By.tagName("a"));
                    opt.click();
                    break;
                }
                catch (NoSuchElementException e) {
                    LOGGER.info("date was not clickable");
                }
            }
        }
    }

    public void selectFarDates() {
        if (startDateField != null) {

            startDateField.click();
            getWebDriver().findElement(By.xpath("//button[@class='btn-paging btn-secondary next']")).click();
            WebElement dateWidget = getWebDriver().findElement(By.xpath("//ul[@class='cal-dates']"));
            List<WebElement> options = dateWidget.findElements(By.tagName("li"));
            for (WebElement opt : options) {
                try {
                    opt.findElement(By.tagName("a"));
                    opt.click();
                    break;
                }
                catch (NoSuchElementException e) {
                    LOGGER.info("date not clickable");
                }
            }
        }
    }

    public void searchForHotelsInCity(String city) {
        searchTextBox.clear();
        searchTextBox.sendKeys(city);
        try {
            getWebDriver().findElement(By.xpath("//a[@class='close']")).click();
        }
        catch (NoSuchElementException ex) {
            LOGGER.info("hotel option was not clicked successfully");
        }
        finally {
            searchButton.click();
        }



    }


}
