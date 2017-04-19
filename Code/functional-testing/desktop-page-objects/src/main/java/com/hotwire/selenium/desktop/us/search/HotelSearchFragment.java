/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.search;

import static org.openqa.selenium.By.cssSelector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.widget.AutoComplete;
import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.selenium.desktop.widget.DropDownSelector;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * This class provides the methods to access the hotel fare finder module on us home page.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public class HotelSearchFragment extends AbstractPageObject {
    public static final By CONTAINER = By.cssSelector("form.hotelFields, form#homeFareFinder," +
        " #collapsibleFareFinderContainer");
    public static final String SEARCH_BUTTON =
            "button[id='findHotelButton'], div.buttons button.btn, form[id='miniFareFinderWide'] button.btn, " +
                    "div.hotelSearchDetails div.finderExpanded form button.btn, .landingFareFinder .btn, " +
            "div.tripDetails div.travelers button.btn";
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelSearchFragment.class);

    /**
     enum AutocompleteVisibilityState
     */
    public enum AutocompleteVisibilityState {
        VISIBLE, INVISIBLE
    }

    private static final String FARE_FINDER_AUTOCOMPLETE_CONTENT =
        ".destination ul, .location ul, ul.ui-autocomplete, .where ul";

    private static final String FARE_FINDER_AUTOCOMPLETE_CONTENT_BLOCK =
            ".yui-ac-content";

    private static final String HCOM_CHECKBOX = "input[name='selectedPartners']";
    private static final long MENU_VISIBLE_WAIT = 10;
    private static final long SLEEP_INTERVAL_MILLIS = 5;
    private static final int DEFAULT_DESTINATION_STRING_LEN = 3;
    private static final String START_DATE =
        "form[id='homeFareFinder'] input[name='startDate'], " +              // UHP/hotel landing pages
        "form[id='miniFareFinderWide'] input[name='startDate'], " +          // Hotel results pages
        "div[id='retailDatePickerTop'] .startDate input[name='startDate'], " +   // Retail details pages
        "#startDateCalendarHotel-field";                                         //hotel.geo.low.level page

    private static final String END_DATE =
        "form[id='homeFareFinder'] input[name='endDate'], " +              // UHP/hotel landing pages
        "form[id='miniFareFinderWide'] input[name='endDate'], " +          // Hotel results pages
        "div[id='retailDatePickerTop'] .endDate input[name='endDate'], " +    // Retail details pages
        "#endDateCalendarHotel-field";                                       //hotel.geo.low.level page

    @FindBy(xpath = "//input[@name='destCity' or @name='location']")
    private WebElement destCity;

    @FindBy(xpath = "//form[@name='hotelIndexForm' or @id='miniFareFinderWide' or @id='homeFareFinder']")
    private WebElement fareFinderForm;

    @FindBy(css = "#hotelRooms, #numRooms")
    private WebElement hotelRooms;

    @FindBy(css = "#hotelRoomAdults, #numAdults")
    private WebElement hotelRoomAdults;

    @FindBy(css = "#hotelRoomChild, #numAdults")
    private WebElement hotelRoomChild;

    @FindBy(css = "#hotelRoomAdults-menu, #numAdults")
    private WebElement hotelRoomAdults_menu;

    @FindBy(css = "#hotelRoomChild-menu, #numAdults")
    private WebElement hotelRoomChildren_menu;

    @FindBy(xpath = "//*[@id='hotelRoomAdults-button']/span[@class='ui-icon ui-icon-triangle-1-s']")
    private WebElement hotelRoomAdultsButton;

    @FindBy(xpath = "//*[@id='hotelRoomChild-button']/span[@class='ui-icon ui-icon-triangle-1-s']")
    private WebElement hotelRoomChildrenButton;

    @FindBy(css = HCOM_CHECKBOX)
    private WebElement hcomCheckbox;

    @FindBy(css = SEARCH_BUTTON)
    private WebElement searchButton;

    public HotelSearchFragment(WebDriver webdriver, By containerLocator) {
        super(webdriver, containerLocator);
    }

    public HotelSearchFragment(WebDriver webDriver) {
        this(webDriver, CONTAINER);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(SEARCH_BUTTON);
    }

    public boolean isEditSearchLinkDisplayed() {
        try {
            return getEditSearch().isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCancelSearchLinkDisplayed() {
        try {
            return getCancelSearch().isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void openFareFinderOnResultsPage() {
        if (isEditSearchLinkDisplayed()) {
            getEditSearch().click();
        }
        else if (isCancelSearchLinkDisplayed()) {
            LOGGER.info("Fare finder on results page is already open");
        }
        else {
            LOGGER.info("Fare finder for results page is absent on the page");
        }
    }

    public boolean isDestinationLocationDisplayed() {
        try {
            return destCity.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectHcomSearch() {
        if (!hcomCheckbox.isSelected()) {
            hcomCheckbox.click();
        }
    }

    public void unselectHcomSearch() {
        if (hcomCheckbox.isSelected()) {
            hcomCheckbox.click();
        }
    }

    private boolean isJQueryLocationAutocomplete() {
        boolean newLocationAutocomplete = false;
        try {
            // Test if new autocomplete is being used. The following line will
            // be found if the autocomplete is not using jquery.
            fareFinderForm.findElement(cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT));
        }
        catch (NoSuchElementException e) {
            // Make sure we really are going to use new location auto complete
            // and we're not on some kind of error page...
            getWebDriver().findElement(cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT));
            newLocationAutocomplete = true;
        }
        LOGGER.info("Using JQuery location autocomplete: " + newLocationAutocomplete);
        return newLocationAutocomplete;
    }

    /**
     * Wait for autocomplete visibility state.
     *
     * @param state The state to wait for, either visible or invisible.
     */
    public void waitForLocationAutocompleteVisibilityState(AutocompleteVisibilityState state) {
        boolean visibility = state.equals(AutocompleteVisibilityState.VISIBLE);
        if (isJQueryLocationAutocomplete()) {
            new WebDriverWait(getWebDriver(), MENU_VISIBLE_WAIT, SLEEP_INTERVAL_MILLIS).until(PageObjectUtils
                .webElementVisibleTestFunction(cssSelector(
                        "ul.ui-autocomplete, div[id='intlHomeFareFinder'] .where ul"), visibility));
        }
        else {
            By by = cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT_BLOCK);
            new WebDriverWait(getWebDriver(), MENU_VISIBLE_WAIT, SLEEP_INTERVAL_MILLIS)
                .until(ExpectedConditions.visibilityOfElementLocated(by));
        }
    }

    public WebElement getDestCity() {
        return destCity;
    }

    public WebElement getEndDateField() {
        return getWebDriver().findElement(By.cssSelector(END_DATE));
    }

    public void typeLocation(String location) {
        sendKeys(destCity, location);
    }

    public WebElement getEditSearch() {
        return getWebDriver().findElement(cssSelector(".editSearchLinkLabel"));
    }

    public WebElement getCancelSearch() {
        return getWebDriver().findElement(cssSelector(".cancelSearchLinkLabel"));
    }

    public void isAutoCompleteDisplayed() {
        new WebDriverWait(getWebDriver(),
               MENU_VISIBLE_WAIT, SLEEP_INTERVAL_MILLIS).until(PageObjectUtils.webElementVisibleTestFunction(
                fareFinderForm.findElement(cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT)), true));
    }

    public ArrayList<String> getAttibutesForAutocomplete(String attributeName) {
        List<WebElement> autocompleteList = fareFinderForm.findElement(cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT))
                .findElements(By.tagName("li"));
        ArrayList<String> autocompleteAttributes = new ArrayList<>();

        for (WebElement item: autocompleteList) {
            autocompleteAttributes.add(item.getAttribute(attributeName));
        }
        return autocompleteAttributes;
    }


    public ArrayList <String> getAutocompleteContents() {
        List<WebElement> autocompleteList = fareFinderForm.findElement(cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT))
                .findElements(By.tagName("div"));
        ArrayList<String> autocompleteTextContents = new ArrayList<>();

        for (WebElement item: autocompleteList) {
            if ((item.getAttribute("title") != null) && !(item.getAttribute("title").equals(""))) {
                autocompleteTextContents.add(item.getAttribute("title").trim());
            }
            else {
                autocompleteTextContents.add(item.getText().trim());
            }
        }
        return autocompleteTextContents;
    }

    public WebElement getAutoComplete() {
        return  fareFinderForm.findElement(cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT));
    }

    public List <WebElement> getAutocompleteElements() {
        return fareFinderForm.findElement(cssSelector(FARE_FINDER_AUTOCOMPLETE_CONTENT))
                                           .findElements(By.tagName("div"));
    }

    public WebElement getStartDateField() {
        return getWebDriver().findElement(By.cssSelector(START_DATE));
    }

    public void selectStartDate(Date startDate) {
        WebElement startDateField = getWebDriver().findElement(By.cssSelector(START_DATE));
        if (startDateField != null) {
            try {
                new DatePicker(getWebDriver(), startDateField).selectDate(startDate);
            }
            catch (WebDriverException e) {
                LOGGER.info("Date picker issue for start date. Entering date manually.");
                SimpleDateFormat sdf = DatePicker.getSelectedCountryDateFormat(getWebDriver());
                startDateField.clear();
                startDateField.sendKeys(sdf.format(startDate));
            }
        }
        else {
            LOGGER.error("Start date field is not displayed on hotel landing page..");
        }
    }

    public void selectEndDate(Date endDate) {
        WebElement endDateField = getWebDriver().findElement(By.cssSelector(END_DATE));
        if (endDateField != null) {
            try {
                new DatePicker(getWebDriver(), endDateField).selectDate(endDate);
            }
            catch (WebDriverException e) {
                LOGGER.info("Date picker issue for end date. Entering date manually.");
                SimpleDateFormat sdf = DatePicker.getSelectedCountryDateFormat(getWebDriver());
                endDateField.clear();
                endDateField.sendKeys(sdf.format(endDate));
            }
        }
        else {
            LOGGER.error("End date field is not displayed on hotel landing page..");
        }
    }

    public void selectHotelRooms(Integer numberOfHotelRooms) {
        if (numberOfHotelRooms == null) {
            return;
        }
        String numRooms = String.valueOf(numberOfHotelRooms.equals(7) ? "7+" : numberOfHotelRooms);
        new DropDownSelector(getWebDriver(), hotelRooms).selectByVisibleText(numRooms);
    }

    public void selectHotelRoomAdults(Integer numberOfAdults) {
        if (numberOfAdults == null) {
            return;
        }
        String numAdults = String.valueOf(numberOfAdults);
        new DropDownSelector(getWebDriver(), hotelRoomAdults).selectByVisibleText(numAdults);
    }

    public void selectHotelRoomChildren(Integer numberOfChildren) {
        if (numberOfChildren == null) {
            return;
        }
        String numChildren = String.valueOf(numberOfChildren);
        new DropDownSelector(getWebDriver(), hotelRoomChild).selectByVisibleText(numChildren);
    }

    public void launchSearch() {
        new WebDriverWait(getWebDriver(), 5).until(
            new IsElementLocationStable(getWebDriver(), By.cssSelector(SEARCH_BUTTON), 4));
        getWebDriver().findElement(By.cssSelector(SEARCH_BUTTON)).click();
        LOGGER.info("Hotel search is started..");
    }

    public WebElement getHotelRoomChild() {
        return hotelRoomChild;
    }

    public WebElement getHotelRoomAdults() {
        return hotelRoomAdults;
    }

    public WebElement hotelRoomChildren() {
        return hotelRoomChildren_menu;
    }

    public WebElement hotelRoomAdults() {
        return hotelRoomAdults_menu;
    }

    public HotelSearchFragment withDestinationLocation(String destination) {
        new AutoComplete(getWebDriver(), destCity).select(destination, DEFAULT_DESTINATION_STRING_LEN);
        return this;
    }

    public HotelSearchFragment withDestinationLocationAsFirstResultInAutocomplete(String destination) {
        new AutoComplete(getWebDriver(), destCity).selectFirstResult(destination);
        return this;
    }

    public HotelSearchFragment withStartDate(Date startDate) {
        WebElement dateField = getWebDriver().findElement(By.cssSelector(START_DATE));
        if (startDate != null) {
            SimpleDateFormat sdf = DatePicker.getSelectedCountryDateFormat(getWebDriver());
            sendKeys(dateField, sdf.format(startDate));
        }
        return this;
    }

    public HotelSearchFragment withEndDate(Date endDate) {
        WebElement dateField = getWebDriver().findElement(By.cssSelector(END_DATE));
        if (endDate != null) {
            SimpleDateFormat sdf = DatePicker.getSelectedCountryDateFormat(getWebDriver());
            sendKeys(dateField, sdf.format(endDate));
        }
        return this;
    }

    public HotelSearchFragment withNumberOfHotelRooms(Integer numberOfHotelRooms) {
        if (numberOfHotelRooms != null) {
            selectHotelRooms(numberOfHotelRooms);
        }
        return new HotelSearchFragment(getWebDriver());
    }

    public HotelSearchFragment withNumberOfAdults(Integer numberOfAdults) {
        if (numberOfAdults != null) {
            selectHotelRoomAdults(numberOfAdults);
        }
        return new HotelSearchFragment(getWebDriver());
    }

    public HotelSearchFragment withNumberOfChildren(Integer numberOfChildren) {
        if (numberOfChildren != null) {
            selectHotelRoomChildren(numberOfChildren);
        }
        return new HotelSearchFragment(getWebDriver());
    }

    public HotelSearchFragment withEnableSearchPartner(Boolean searchPartner) {
        if (getWebDriver().findElements(cssSelector(HCOM_CHECKBOX)).size() > 0 && hcomCheckbox.isDisplayed()) {
            if (searchPartner) {
                selectHcomSearch();
            }
            else {
                unselectHcomSearch();
            }
        }
        return this;
    }

    public void clickRoomAdultsButton() {
        hotelRoomAdultsButton.click();
    }

    public void clickRoomChildrenButton() {
        hotelRoomChildrenButton.click();
    }
}
