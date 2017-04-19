/*
* Copyright 2014 Hotwire. All Rights Reserved.
*
* This software is the proprietary information of Hotwire.
* Use is subject to license terms.
*/
package com.hotwire.selenium.desktop.us.results;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 12/22/14
 * Time: 11:26 AM
 */
public class ExpediaAirResultsPage extends AbstractUSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpediaAirResultsPage.class);

    private List<WebElement> resultList;

    @FindBy(xpath = "//span[@class='loader loader-light loader-animated loading']")
    private WebElement resultsProgressLoader;

    @FindBy(css = ".secondary-playback-summary")
    private WebElement travelSummary;

    @FindBy(id = "departureAirport")
    private WebElement departureLocation;

    @FindBy(id = "returnAirport")
    private WebElement returnLocation;

    @FindBy(id = "departDate")
    private WebElement departDate;

    @FindBy(id = "returnDate")
    private WebElement returnDate;

    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public ExpediaAirResultsPage(WebDriver webDriver) {
        super(webDriver);
        waitForLoadedResults();
    }

    private void waitForLoadedResults() {

        WebDriverWait wait = new WebDriverWait(getWebDriver(), 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='flightModule0']")));
    }

    public List<WebElement> getResultsList() {
        return getWebDriver().findElements(By.xpath("//li[contains(@id,'flightModule')]"));
    }

    public int getNumberOfTravelers() {
        Matcher matcher = Pattern.compile("\\d+").matcher(travelSummary.getText());
        matcher.find();
        return Integer.valueOf(matcher.group());
    }

    public String getDepartureLocation() {
        return departureLocation.getAttribute("value");
    }

    public String getReturnLocation() {
        return returnLocation.getAttribute("value");
    }

    public Date getDepartureDate() throws ParseException {


        return sdf.parse(departDate.getAttribute("value"));
    }

    public Date getReturnDate() throws ParseException {

        return sdf.parse(returnDate.getAttribute("value"));
    }


}

