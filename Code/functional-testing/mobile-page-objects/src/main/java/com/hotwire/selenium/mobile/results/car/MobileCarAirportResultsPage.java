/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.results.car;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.models.CarSolutionModel;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.PageName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: achotemskoy
 * Date: 12/17/14
 * Time: 4:13 PM
 * <p/>
 * This class is represents mobile car airport results - we have this results when the search goes for airport.
 * For example - SFO
 * Airport results have opaque solutions, and this page does not have agency blocks on results.
 */

public class MobileCarAirportResultsPage extends MobileAbstractPage implements MobileCarResultsPage {
    static final Logger LOGGER = LoggerFactory.getLogger(MobileCarAirportResultsPage.class.getSimpleName());
    @FindBy(css = ".search span")
    private WebElement dateAndTime;
    @FindBy(css = ".result.ref")
    private List<WebElement> searchResults;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = "div[data-solution*='isRetail:false']")
    private List<WebElement> opaqueSearchResults;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @FindBy(css = "div[data-solution*='isRetail:true']")
    private List<WebElement> retailSearchResults;

    public MobileCarAirportResultsPage(WebDriver webdriver) {
        super(webdriver, "tile.car.results", new InvisibilityOf(By.cssSelector(".loading img")), TIME_TO_WAIT,
              MAX_SEARCH_PAGE_WAIT_SECONDS);
    }

    /**
     * This method will select either an opaque or retail car type from the car results page
     *
     * @param resultNumberToSelect is not used for now.
     * @param opaqueRetail  String that contains "opaque" or "retail"
     */
    public CarSolutionModel select(Integer resultNumberToSelect, String opaqueRetail) {

        //Step 1: Select car search solution to click.
        WebElement selectedCarResult;
        if (opaqueRetail.equals("opaque")) {
            assertThat(opaqueSearchResults.isEmpty()).as(
                    "Was expecting at least one " + opaqueRetail + "car results. Could not find any.").isFalse();
            selectedCarResult = opaqueSearchResults.get(0);
        }
        else {
            assertThat(retailSearchResults.isEmpty()).as(
                    "Was expecting at least one " + opaqueRetail + "car results. Could not find any.").isFalse();
            selectedCarResult = retailSearchResults.get(0);
        }

        //Step 2: Filling model with data that we have on results page. Nice to have - seats, cargo, pickup location
        CarSolutionModel carModel = new CarSolutionModel();

        carModel.setOpaque(opaqueRetail.equals("opaque"));

        carModel.setCarName(selectedCarResult.findElement(By.cssSelector(".value strong")).getText());
        carModel.setPerDayPrice(selectedCarResult.findElement(By.cssSelector(".rate")).getText());
        carModel.setTotalPrice(selectedCarResult.findElement(By.cssSelector(".total")).getText());

        //Step 3: Open details for selected solution.
        selectedCarResult.click();

        //Checking if there is inventory errors.
        if ("tile.car.results".contains(new PageName().apply(getWebDriver())) &&
                getWebDriver().findElements(By.cssSelector(".priceChangeMssg")).size() > 0) {
            LOGGER.info("Inventory check redirected to results page due to sold out. Selecting the next solution.");
            select(0, opaqueRetail); //it is doesn't matter what we pass as number in first param. We are not using it
        }

        return carModel;

    }

    public String getSearchDatesAndTime() {
        return dateAndTime.getText();
    }
}

