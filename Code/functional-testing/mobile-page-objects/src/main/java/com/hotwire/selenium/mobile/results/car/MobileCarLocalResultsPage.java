/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.results.car;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.models.CarSolutionModel;
import com.hotwire.testing.UnimplementedTestException;
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
 * This class is represents mobile car local results - we have this results when the search goes for city.
 * For example - San Francisco, CA
 * Local results does not have any opaque solutions, and this page has different layout with agency accordions.
 */
public class MobileCarLocalResultsPage extends MobileAbstractPage implements MobileCarResultsPage {

    static final Logger LOGGER = LoggerFactory.getLogger(MobileCarLocalResultsPage.class.getSimpleName());

    // The first local agency result on the page
    @FindBy(id = "local-0")
    private WebElement localAgency;

    // The list of ALL the car types of ALL agencies on the page (all of them will be hidden until you click on agency)
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") //suppressing due to FindBy is returns list
    @FindBy(className = "type")
    private List<WebElement> allCarTypesOnResults;

    @FindBy(css = ".search>h1>span")
    private WebElement dateAndTime;

    public MobileCarLocalResultsPage(WebDriver webdriver) {
        super(webdriver, "tile.car.results", new InvisibilityOf(By.cssSelector(".loading img")), TIME_TO_WAIT,
                MAX_SEARCH_PAGE_WAIT_SECONDS);
    }

    /**
     * This method will select either an opaque or retail car type from the car results page This will work for both
     * airport and local car results page.
     *
     * @param resultNumberToSelect number of result
     * @param opaqueRetail         String that contains "opaque" or "retail"
     */
    @Override
    public CarSolutionModel select(Integer resultNumberToSelect, String opaqueRetail) {

        // We will not have any opaque results for local car search
        if (opaqueRetail.equals("opaque")) {
            throw new UnimplementedTestException("There is no opaque results for local car search. If you need them -" +
                    "you should update car simulator for jslaves.");
        }

        //Assert that we have search results.
        assertThat(allCarTypesOnResults.isEmpty()).as(
                "Was expecting at least one car type result. Could not find any.").isFalse();

        // Step 1: Pick the first agency. Nice to have - possibility to select agency.
        localAgency.click();

        /** Step 2: Choose first car type solution. Nice to have - possibility to get result depending on
         *   @param resultNumberToSelect
         */
        WebElement selectedCarResult = allCarTypesOnResults.get(0);

        // Filling carModel with info that we have on results page for this solution.
        CarSolutionModel carModel = new CarSolutionModel();

        carModel.setOpaque(opaqueRetail.equals("opaque"));
        carModel.setCarName(selectedCarResult.findElement(By.cssSelector(".class .value")).getText());
        carModel.setPerDayPrice(selectedCarResult.findElement(By.cssSelector(".rate")).getText());
        carModel.setTotalPrice(selectedCarResult.findElement(By.cssSelector(".total")).getText());

        //Step 3: Open details page.
        selectedCarResult.click();

        if ("tile.car.results".contains(new PageName().apply(getWebDriver())) &&
                getWebDriver().findElements(By.cssSelector(".priceChangeMssg")).size() > 0) {
            LOGGER.info("Inventory check redirected to results page due to sold out. Selecting the next solution.");
            select(0, opaqueRetail); //it is doesn't matter what we pass as number in first param. We are not using it
        }
        return carModel;
    }

    @Override
    public String getSearchDatesAndTime() {
        return dateAndTime.getText();
    }
}
