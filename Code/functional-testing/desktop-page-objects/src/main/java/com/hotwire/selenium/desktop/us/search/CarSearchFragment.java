/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.search;

import com.hotwire.selenium.desktop.widget.DropDownSelector;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lryzhikov
 * @since 2012.06
 */
public class CarSearchFragment extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSearchFragment.class);
    private static final By CONTAINER = By.name("carIndexForm");

    private static final String FARE_FINDER_AUTOCOMPLETE_CONTENT =
            ".destination ul, .location ul, ul.ui-autocomplete, .where ul";
    private static final long MENU_VISIBLE_WAIT = 10;
    private static final long SLEEP_INTERVAL_MILLIS = 5;
    private static final String CAR_RENTALS_COM = "CarRentals.com";
    private static final String EXPEDIA_COM = "Expedia";
    private static final String START_TIME = ".startTime";
    private static final String END_TIME = ".endTime";

    @FindBy (css = ".editSearchLinkLabel")
    private WebElement editSearch;

    @FindBy(xpath = "//form[@name='carIndexForm' or @id='miniFareFinderWide']")
    private WebElement fareFinderForm;

    @FindBy(id = "carRoundTrip")
    private WebElement roundTripRadio;

    @FindBy(id = "carOneWay")
    private WebElement oneWayRadio;

    @FindBy(id = "carEndLocation")
    private WebElement carEndLocation;

    @FindBy(css = "input[name='startLocation'], input[name='carRefineSearch-startLocation']")
    private WebElement startLocation;

    @FindBy(name = "startDate")
    private WebElement startDateField;

    @FindBy(css = START_TIME)
    private WebElement startTimeCar;

    @FindBy(name = "endDate")
    private WebElement endDateField;

    @FindBy(css = END_TIME)
    private WebElement endTimeCar;

    @FindBy(css = "#findCarButton, #carRefineSearch .btn")
    private WebElement searchButton;

    @FindBy(css = "div.partnerWrapper input#CR-C")
    private WebElement cbCarRentals;

    @FindBy(css = "div.partnerWrapper input#EX-C")
    private WebElement cbExpedia;

    public CarSearchFragment(WebDriver webDriver) {
        super(webDriver, CONTAINER);
    }

    public CarSearchFragment(WebDriver webDriver, By container) {
        super(webDriver, container);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector("#findCarButton, #carRefineSearch .btn");
    }

    public void findFare() {
        searchButton.click();
    }

    public CarSearchFragment endLocation(String dropOffLocation) {
        if (StringUtils.isNotEmpty(dropOffLocation)) {
            oneWayRadio.click();
            sendKeys(carEndLocation, dropOffLocation);
        }
        return new CarSearchFragment(getWebDriver());
    }

    public CarSearchFragment endTime(String endTime) {
        if (endTime != null) {
            new DropDownSelector(getWebDriver(), By.name("endTime")).selectByVisibleText(endTime);
        }
        return new CarSearchFragment(getWebDriver());
    }

    public CarSearchFragment endDate(Date endDate) {
        if (endDate != null) {
            sendKeys(endDateField, (new SimpleDateFormat("MM/dd/yy")).format(endDate) + Keys.TAB);
        }
        return this;
    }

    public CarSearchFragment startTime(String startTime) {
        if (startTime != null) {
            new DropDownSelector(getWebDriver(), By.name("startTime")).selectByVisibleText(startTime);
        }
        return new CarSearchFragment(getWebDriver());
    }

    public CarSearchFragment startDate(Date startDate) {
        // DatePicker solution doesn't work when start or end date more than 330 days from now so doing sendKeys.
        if (startDate != null) {
            sendKeys(startDateField, (new SimpleDateFormat("MM/dd/yy")).format(startDate) + Keys.TAB);
        }
        return this;
    }

    public CarSearchFragment startLocation(String destinationLocation) {
        sendKeys(startLocation, destinationLocation);
        return this;
    }

    private WebElement getCompareWithCheckboxByPartnerName(String partnerName) {
        if (CAR_RENTALS_COM.equals(partnerName)) {
            return cbCarRentals;
        }
        if (EXPEDIA_COM.equals(partnerName)) {
            return cbExpedia;
        }
        return null;
    }

    public boolean isRoundTripRadioChecked() {
        return this.roundTripRadio.isSelected();
    }

    public boolean isOneWayTripRadioChecked() {
        return this.oneWayRadio.isSelected();
    }

    public boolean checkCompareWithCheckboxState(String partnerName, String state) {

        try {
            WebElement cb = getCompareWithCheckboxByPartnerName(partnerName);
            if (cb.isDisplayed() && !"hidden".equals(state)) {
                if ("checked".equals(state) && cb.isSelected()) {
                    return true;
                }
                if ("unchecked".equals(state) && !cb.isSelected()) {
                    return true;
                }
            }
        }
        catch (NoSuchElementException exception) {
            if ("hidden".equals(state)) {
                return true;
            }
        }
        return false;
    }

    public void checkComparableCarPartner(List<String> partners) {
        for (String p : partners) {
            WebElement cb = getCompareWithCheckboxByPartnerName(p.trim());
            if (!cb.isSelected()) {
                cb.click();
            }
        }
    }

    public void isAutoCompleteDisplayed() {
        new WebDriverWait(getWebDriver(),
                MENU_VISIBLE_WAIT, SLEEP_INTERVAL_MILLIS).until(PageObjectUtils.webElementVisibleTestFunction(
                fareFinderForm.findElement(By.cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT)), true));
    }

    public ArrayList<String> getAttibutesForAutocomplete(String attributeName) {
        List<WebElement> autocompleteList = fareFinderForm.findElement(By.cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT))
                .findElements(By.tagName("li"));
        ArrayList<String> autocompleteAttributes = new ArrayList<String>();

        for (WebElement item: autocompleteList) {
            autocompleteAttributes.add(item.getAttribute(attributeName));
        }
        return autocompleteAttributes;


    }

    public ArrayList <String> getAutocomleteContents() {
        List<WebElement> autocompleteList = fareFinderForm.findElement(By.cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT))
                .findElements(By.tagName("div"));
        ArrayList<String> autocompleteTextContents = new ArrayList<String>();

        for (WebElement item: autocompleteList) {
            autocompleteTextContents.add(item.getText());
        }
        return autocompleteTextContents;


    }

    public WebElement getAutoComplete() {
        return  fareFinderForm.findElement(By.cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT));
    }

    public List <WebElement> getAutocompleteElements() {
        List<WebElement> autocompleteElements = fareFinderForm.
                findElement(By.cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT))
                .findElements(By.tagName("div"));
        return autocompleteElements;

    }

    public WebElement getStartLocation() {
        return startLocation;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public WebElement getEditSearch() {
        return editSearch;
    }

    public WebElement getStartTimeCar() {
        return startTimeCar;
    }
}
