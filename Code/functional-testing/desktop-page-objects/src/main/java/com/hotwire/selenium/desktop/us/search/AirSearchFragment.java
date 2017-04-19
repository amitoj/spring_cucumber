/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.search;

import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.selenium.desktop.widget.DropDownSelector;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: v-abudyak Date: 5/4/12 Time: 6:32 PM To
 * change this template use File | Settings | File Templates.
 */
public class AirSearchFragment extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirSearchFragment.class);
    private static final By CONTAINER = By.cssSelector("form[name='airIndexForm'], .airFields");

    private static final String FARE_FINDER_AUTOCOMPLETE_CONTENT = ".yui-ac-content ul, " +
            ".field destination yui-ac .yui-ac-content ul, " +
            ".commonInputs div[class*='origin'] .yui-ac-content ul, " +
            ".commonInputs div[class*='destination'] .yui-ac-content ul";

    private static final String FLEX_SEARCH_LINK = " .msg.flex a";

    private static final long DEFAULT_WAIT = 20;
    private static final long SLEEP_INTERVAL_MILLIS = 5;
    private static final long MENU_VISIBLE_WAIT = 10;

    @FindBy(xpath = "//form[@name='airIndexForm']")
    private WebElement fareFinderForm;

    @FindBy(xpath = ".//input[contains(@id, 'roundTripRadio')]")
    private WebElement roundTripRadioButton;

    @FindBy(xpath = ".//input[contains(@id, 'oneWayRadio')]")
    private WebElement oneWayRadioButton;

    @FindBy(name = "origCity")
    private WebElement fromLocation;

    //@FindBy(name = "destinationCity")
    //private WebElement toLocation;
    @FindBy(xpath = "//input[@name='destinationCity']")
    private WebElement toLocation;

    @FindBy(name = "startDate")
    private WebElement startDateField;

    @FindBy(name = "endDate")
    private WebElement endDateField;

    @FindBy(name = "noOfTickets")
    private WebElement passengers;

    @FindBy(css = FLEX_SEARCH_LINK)
    private WebElement flexSearch;

    @FindBy(xpath = ".//input[contains(@id, 'multiCityRadio')]")
    private WebElement multiRadioButton;

    @FindBy(name = "routeInfo[0].destCity")
    private WebElement toLocationForRoute1;

    @FindBy(name = "routeInfo[0].origCity")
    private WebElement fromLocationForRoute1;

    @FindBy(name = "routeInfo[0].departureDateString")
    private WebElement startDateForRoute1Field;

    @FindBy(name = "routeInfo[1].origCity")
    private WebElement fromLocationForRoute2;

    @FindBy(name = "routeInfo[1].destCity")
    private WebElement toLocationForRoute2;

    @FindBy(name = "routeInfo[1].departureDateString")
    private WebElement startDateForRoute2;

    @FindBy(css = "a.seeAllInputsLink")
    private WebElement addMoreFlights;

    @FindBy(name = "routeInfo[2].origCity")
    private WebElement fromLocationForRoute3;

    @FindBy(name = "routeInfo[2].destCity")
    private WebElement toLocationForRoute3;

    @FindBy(name = "routeInfo[2].departureDateString")
    private WebElement startDateForRoute3;

    @FindBy(name = "routeInfo[3].origCity")
    private WebElement fromLocationForRoute4;

    @FindBy(name = "routeInfo[3].destCity")
    private WebElement toLocationForRoute4;

    @FindBy(name = "routeInfo[3].departureDateString")
    private WebElement startDateForRoute4;

    @FindBy(linkText = "See all cities")
    private List<WebElement> seeAllCitiesLinks;

    @FindBy(linkText = "Next")
    private WebElement next;

    @FindBy(linkText = "Previous")
    private WebElement previous;

    @FindBy(id = "IM_hotwire_rail_blue")
    private WebElement leftIntentMediaModule;

    @FindBy(id = "IM_hotwire_ic_gray_blue_thin")
    private WebElement centerIntentMediaModule;

    @FindBy(css = "button#ffAirRoundTripButton, button.btn")
    private WebElement roundTripSearchButton;

    @FindBy(css = "button#ffAirOneWayButton, button.btn")
    private WebElement oneWaySearchButton;

    public AirSearchFragment(WebDriver webdriver) {
        super(webdriver, CONTAINER);
    }

    public int getTotalCitiesLinks() {
        return seeAllCitiesLinks.size();
    }

  /*  @Override
    protected By getLocatorOfElementToWaitFor() {
        //return By.cssSelector("button#ffAirRoundTripButton, button#ffAirOneWayButton, button.btn");
        return By.id("genRandom7-origCity-airIndexForm");
    }*/

    public boolean validatePopUp(String field) {
        // since we have two 'see all cities' pop-up, for 'from' and 'to'
        // fields,
        // we will need to create a if/else-if loops to invoke the right pop-up
        if (field.contains("From")) {
            getWebDriver().findElement(
                    By.cssSelector("div.field.origin.yui-ac label a")).click();
            int count = getSeeAllCitiesCount();
            if (count == 0) {
                return true;
            }
        }
        else if (field.contains("To")) {
            getWebDriver().findElement(
                    By.cssSelector("div.field.destination.yui-ac label a"))
                    .click();
            int count = getSeeAllCitiesCount();
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    private int getSeeAllCitiesCount() {
        ArrayList<String> handles = new ArrayList<>(getWebDriver().getWindowHandles());
        getWebDriver().switchTo().window(handles.get(1));

        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector("div#tileName-A1"), true));

        List<WebElement> links = getWebDriver().findElements(By.linkText("international cities"));
        return links.size();
    }

    private void setRoutesInfo(String fromThisLocation, String toThisLocation, Date startDate, Date endDate) {
        fromLocation.clear();
        fromLocation.click();
        fromLocation.sendKeys(fromThisLocation);

        try {
            new WebDriverWait(getWebDriver(), DEFAULT_WAIT, SLEEP_INTERVAL_MILLIS)
                .until(PageObjectUtils.webElementVisibleTestFunction(
                    fareFinderForm.findElement(By.cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT)), false));
        }
        catch (TimeoutException e) {
            LOGGER.info("Timed out waiting for autocomplete to vanish. Attempt to manually make it disappear.");
            fromLocation.sendKeys(Keys.ESCAPE);
        }
        LOGGER.info("Departure location is \"{}\"", fromThisLocation);

        toLocation.clear();
        toLocation.click();
        toLocation.sendKeys(toThisLocation);
        try {
            new WebDriverWait(getWebDriver(), DEFAULT_WAIT, SLEEP_INTERVAL_MILLIS)
                .until(PageObjectUtils.webElementVisibleTestFunction(
                        fareFinderForm.findElement(By.cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT)),
                        false));
        }
        catch (TimeoutException e) {
            LOGGER.info("Timed out waiting for autocomplete to vanish. Attempt to manually make it disappear.");
            toLocation.sendKeys(Keys.ESCAPE);
        }
        LOGGER.info("Arrived to \"{}\"", toThisLocation);

        selectStartDate(startDate);

        if (endDate != null && roundTripRadioButton.isEnabled()) {
            selectEndDate(endDate);
        }
    }

    private void selectStartDate(Date startDate) {
        sendKeys(this.startDateField, DatePicker.getFormattedDate(startDate) + Keys.TAB);
        LOGGER.info("Selecting start date: {}", DatePicker.getFormattedDate(startDate));
    }

    private void selectEndDate(Date endDate) {
        sendKeys(this.endDateField, DatePicker.getFormattedDate(endDate) + Keys.TAB);
        LOGGER.info("Selecting end date: {}", DatePicker.getFormattedDate(endDate));
    }

    public void setRouteInfo(String fromLocation, String toLocation, Date startDate, Date endDate) {
        new MultiVerticalFareFinder(getWebDriver()).chooseAir();
        setRoutesInfo(fromLocation, toLocation, startDate, endDate);
    }

    public void doFlexSearch() {
        flexSearch.click();
    }

    public boolean verifyForwardPaginationInResultsPage() {
        try {
            if (next.isDisplayed()) {
                next.click();
                return true;
            }
            return false;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean verifyBackwardPaginationInResultsPage() {
        try {
            return previous.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean verifyIntentMediaModule() {
        try {
            new WebDriverWait(getWebDriver(), DEFAULT_WAIT, SLEEP_INTERVAL_MILLIS);
            return leftIntentMediaModule.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getFromLocation() {
        return fromLocation.getAttribute("value");
    }
    public void  clickOnAirDepartureField() {
        fromLocation.click();
    }

    public void setFromLocation(String location) {
        fromLocation.clear();
        fromLocation.sendKeys(location);
    }

    public String getToLocation() {
        return toLocation.getAttribute("value");
    }

    public void setToLocation(String location) {
        toLocation.clear();
        toLocation.sendKeys(location);
    }

    public WebElement getNumberOfPassengersDropdown() {
        return passengers;
    }

    public void isAutoCompleteDisplayed() {
        new WebDriverWait(getWebDriver(),
                MENU_VISIBLE_WAIT, SLEEP_INTERVAL_MILLIS).until(PageObjectUtils.webElementVisibleTestFunction(
                getAutoComplete(), true));
    }

    public ArrayList<String> getAttibutesForAutocomplete(String attributeName) {
        List<WebElement> autocompleteList =
                getAutoComplete().findElements(By.tagName("li"));
        ArrayList<String> autocompleteAttributes = new ArrayList<>();

        for (WebElement item: autocompleteList) {
            autocompleteAttributes.add(item.getAttribute(attributeName));
        }
        return autocompleteAttributes;


    }

    public ArrayList <String> getAutoCompleteContents() {
        List<WebElement> autocompleteList =
                getAutoComplete().findElements(By.tagName("div"));
        ArrayList<String> autocompleteTextContents = new ArrayList<>();

        for (WebElement item : autocompleteList) {
            autocompleteTextContents.add(item.getText());
        }
        return autocompleteTextContents;
    }

    public WebElement getAutoComplete() {
        String[] list = FARE_FINDER_AUTOCOMPLETE_CONTENT.split(",");
        for (String elem : list) {
            try {
                if (fareFinderForm.findElement(By.cssSelector(elem)).isDisplayed()) {
                    return fareFinderForm.findElement(By.cssSelector(elem));
                }
            }
            catch (NoSuchElementException e) {
                LOGGER.info("css locator :" + elem + " isn't visible on the page");
            }
        }
        return  null;
    }

    public List <WebElement> getAutocompleteElements() {
        return getAutoComplete().findElements(By.tagName("div"));
    }

    public WebElement getFareFinderForm() {
        return fareFinderForm;
    }

    public void clickFlightSearch() {
        roundTripSearchButton.click();
    }

    public void launchSearch() {
        if (roundTripRadioButton.isSelected()) {
            roundTripSearchButton.click();
        }
        else if (oneWayRadioButton.isSelected() || multiRadioButton.isSelected()) {
            if (oneWaySearchButton.isDisplayed()) {
                oneWaySearchButton.click();
            }
            else {
                fromLocation.submit();
            }
        }
        else {
            throw new UnimplementedTestException("Search type not supported!");
        }
        LOGGER.info("Search is started..");
    }

    public AirSearchFragment withOrigLocation(String origLocation) {
        sendKeys(fromLocation, origLocation);
        return new AirSearchFragment(getWebDriver());
    }

    public AirSearchFragment withDestinationLocation(String destinationLocation) {
        sendKeys(toLocation, destinationLocation);
        return new AirSearchFragment(getWebDriver());
    }

    public AirSearchFragment withStartDate(Date date) {
        if (date != null) {
            sendKeys(startDateField, (new SimpleDateFormat("MM/dd/yy")).format(date) + Keys.TAB);
        }
        return new AirSearchFragment(getWebDriver());
    }

    public AirSearchFragment withEndDate(Date date) {
        if (date != null) {
            sendKeys(endDateField, (new SimpleDateFormat("MM/dd/yy")).format(date) + Keys.TAB);
        }
        else {
            oneWayRadioButton.click();
           /* new WebDriverWait(getWebDriver(), MENU_VISIBLE_WAIT).until(
                    ExpectedConditions.elementSelectionStateToBe(oneWayRadioButton, true));*/
        }
        return new AirSearchFragment(getWebDriver());
    }

    public AirSearchFragment withNumberOfPassengers(Integer numOfPassengers) {
        if (numOfPassengers != null) {
            new DropDownSelector(getWebDriver(), passengers).selectByVisibleText(String.format("%d", numOfPassengers));
        }
        else {
            LOGGER.info("Failed to select specified number of passengers. Using whatever is default.");
        }
        return new AirSearchFragment(getWebDriver());
    }

    public AirSearchFragment multiCityRoute(
            String departure1, String arrival1, Date date1,
            String departure2, String arrival2, Date date2
    ) {
        if (departure1 != null)  {
            multiRadioButton.click();

            new WebDriverWait(getWebDriver(), MENU_VISIBLE_WAIT).until(
                    ExpectedConditions.elementSelectionStateToBe(multiRadioButton, true));

            sendKeys(fromLocationForRoute1, departure1);
            sendKeys(toLocationForRoute1, arrival1);
            sendKeys(startDateForRoute1Field, (new SimpleDateFormat("MM/dd/yy")).format(date1) + Keys.TAB);

            sendKeys(fromLocationForRoute2, departure2);
            sendKeys(toLocationForRoute2, arrival2);
            sendKeys(startDateForRoute2, (new SimpleDateFormat("MM/dd/yy")).format(date2) + Keys.TAB);
        }
        return new AirSearchFragment(getWebDriver());
    }

    public AirSearchFragment addMoreFlights(
            String departure1, String arrival1, Date date1,
            String departure2, String arrival2, Date date2
    ) {
        if (departure1 != null)  {
            addMoreFlights.click();

            new WebDriverWait(getWebDriver(), MENU_VISIBLE_WAIT).until(
                    ExpectedConditions.visibilityOf(fromLocationForRoute3));

            sendKeys(fromLocationForRoute3, departure1);
            sendKeys(toLocationForRoute3, arrival1);
            sendKeys(startDateForRoute3, (new SimpleDateFormat("MM/dd/yy")).format(date1) + Keys.TAB);

            sendKeys(fromLocationForRoute4, departure2);
            sendKeys(toLocationForRoute4, arrival2);
            sendKeys(startDateForRoute4, (new SimpleDateFormat("MM/dd/yy")).format(date2) + Keys.TAB);
        }
        return new AirSearchFragment(getWebDriver());
    }

    public AirSearchFragment doOneWaySearch() {
        oneWayRadioButton.click();
        return this;
    }
}



