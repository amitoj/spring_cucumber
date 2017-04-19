/*
* Copyright 2014 Hotwire. All Rights Reserved.
*
* This software is the proprietary information of Hotwire.
* Use is subject to license terms.
*/
package com.hotwire.selenium.desktop.us.results.air;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.results.ExpediaAirResultsPage;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 01/26/15
 * Time: 11:26 AM
 */
public class ExpediaAirMultiResultsPage extends AbstractUSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpediaAirResultsPage.class);


    @FindBy(id = "flightSubmitLink")
    private WebElement searchButton;

    @FindBy(id = "//span[@class='h3']")
    private WebElement multipleDestinationsLabel;

    @FindBy(id = "//div[@class='h4 hd-title']")
    private WebElement flightsInfoLabels;

    @FindBy(id = "inpDepartureLocations0")
    private WebElement fromCity1;

    @FindBy(id = "inpArrivalLocations0")
    private WebElement toCity1;

    @FindBy(id = "inpDepartDate1")
    private WebElement departureDate1;

    @FindBy(id = "inpDepartureTimes0")
    private WebElement departureTime1;

    @FindBy(id = "inpDepartureLocations1")
    private WebElement fromCity2;

    @FindBy(id = "inpArrivalLocations1")
    private WebElement toCity2;

    @FindBy(id = "inpDepartDate2")
    private WebElement departureDate2;

    @FindBy(id = "inpDepartureTimes1")
    private WebElement departureTime2;

    @FindBy(id = "inpDepartureLocations2")
    private WebElement fromCity3;

    @FindBy(id = "inpArrivalLocations2")
    private WebElement toCity3;

    @FindBy(id = "inpDepartDate3")
    private WebElement departureDate3;

    @FindBy(id = "inpDepartureTimes2")
    private WebElement departureTime3;

    @FindBy(id = "inpDepartureLocations3")
    private WebElement fromCity4;

    @FindBy(id = "inpArrivalLocations3")
    private WebElement toCity4;

    @FindBy(id = "inpDepartDate4")
    private WebElement departureDate4;

    @FindBy(id = "inpDepartureTimes3")
    private WebElement departureTime4;

    @FindBy(id = "inpDepartureLocations4")
    private WebElement fromCity5;

    @FindBy(id = "inpArrivalLocations4")
    private WebElement toCity5;

    @FindBy(id = "inpDepartDate5")
    private WebElement departureDate5;

    @FindBy(id = "inpDepartureTimes4")
    private WebElement departureTime5;


    public ExpediaAirMultiResultsPage(WebDriver webDriver) {
        super(webDriver);
        waitForLoadedResults();
        PageFactory.initElements(webDriver, this);
    }

    private void waitForLoadedResults() {

        WebDriverWait wait = new WebDriverWait(getWebDriver(), 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("flightLegSummary")));
    }

    public String getFromCity1() {
        return fromCity1.getAttribute("value");
    }

    public String getToCity1() {
        return toCity1.getAttribute("value");
    }

    public String getDepartureDate1() throws ParseException {
        return this.departureDate1.getAttribute("value");

    }

    public String getFromCity2() {
        return fromCity2.getAttribute("value");
    }

    public String getToCity2() {
        return toCity2.getAttribute("value");
    }

    public String getDepartureDate2() throws ParseException {
        return this.departureDate2.getAttribute("value");

    }


    public String getFromCity3() {
        return fromCity3.getAttribute("value");
    }

    public String getToCity3() {
        return toCity3.getAttribute("value");
    }


    public String getDepartureDate3() throws ParseException {
        return this.departureDate3.getAttribute("value");

    }

    public String getFromCity4() {
        return fromCity4.getAttribute("value");
    }

    public String getToCity4() {
        return toCity4.getAttribute("value");
    }


    public String getDepartureDate4() throws ParseException {
        return this.departureDate4.getAttribute("value");

    }

    public String getFromCity5() {
        return fromCity5.getAttribute("value");
    }

    public String getToCity5() {
        return toCity5.getAttribute("value");
    }


    public String getDepartureDate5() throws ParseException {
        return this.departureDate5.getAttribute("value");

    }


}

