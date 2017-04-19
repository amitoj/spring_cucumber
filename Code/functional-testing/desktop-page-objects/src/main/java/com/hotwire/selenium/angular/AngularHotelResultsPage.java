/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.angular;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.po.AbstractPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * @author vjong
 *
 */
public class AngularHotelResultsPage extends AbstractPage {
    public static final int DEFAULT_WAIT = 25;
    private static final int MAX_WAIT_FOR_RESULTS = 90;

    private static final String GOOGLE_MAPS_CONTENT = "a[href*='https://maps.google.com']";
    private static final String RESULTS_LIST = ".result-view ul.resultsList li.result";
    private static final String STAR_RATING_FILTER = "div[data-bdd='filters-desktop'] ul li[data-bdd='star-rating']";
    private static final String AREAS_FILTER = "div[data-bdd='filters-desktop'] ul li[data-bdd='desktop-area']";
    private static final String AMENITIES_FILTER =
        "div[data-bdd='filters-desktop'] ul li[data-bdd='desktop-amenities']";
    private static final String SORT_BY = "div[data-bdd='filters-desktop'] ul li[data-bdd='sort-menu']";
    private static final String RESET_FILTERS_LINK =
        "div[data-bdd='filters-desktop'] ul li.clear-filters-dropdown a[data-bdd='clear-filters-title']";

    @FindBy(css = RESULTS_LIST)
    private List<WebElement> resultsList;

    @FindBy(css = GOOGLE_MAPS_CONTENT)
    private WebElement googleMapsContent;

    @FindBy(css = "li.dropdown a[data-bdd='filter']")
    private WebElement filterDropdown;

    @FindBy(css = STAR_RATING_FILTER + " a")
    private WebElement starRatingDropdown;

    @FindBy(css = AREAS_FILTER + " a")
    private WebElement areasDropdown;

    @FindBy(css = AMENITIES_FILTER + " a")
    private WebElement amenitiesDropdown;

    @FindBy(css = SORT_BY + " a[data-bdd='sortByOpener']")
    private WebElement sortByOpener;

    @FindBy(css = "div[data-bdd='olab-feedback']")
    private WebElement olabLink;

    @FindBy(linkText = "Standard Rate Hotels")
    private WebElement standardRateHotelsTab;

    @FindBy(css = "ul.dropdown-menu")
    private WebElement dropDown;

    @FindBy(css = "#destination")
    private WebElement destinationLocation;

    public AngularHotelResultsPage(WebDriver webdriver) {
        super(webdriver, new InvisibilityOf(By.cssSelector("img[ng-show='isSearchInProgress']")), MAX_WAIT_FOR_RESULTS);
        new WebDriverWait(webdriver, DEFAULT_WAIT * 2).until(
            new IsElementLocationStable(webdriver, By.cssSelector(RESULTS_LIST), 4));
    }

    public AngularHotelResultsPage(WebDriver webdriver, Boolean waitForResultsVisible) {
        super(webdriver, new VisibilityOf(By.cssSelector(RESULTS_LIST)), MAX_WAIT_FOR_RESULTS);
        new WebDriverWait(webdriver, 5).until(
            new IsElementLocationStable(webdriver, By.cssSelector(RESULTS_LIST), 4));
    }

    public boolean isMapLoaded() {
        try {
            new WebDriverWait(getWebDriver(), DEFAULT_WAIT * 2).until(PageObjectUtils.webElementVisibleTestFunction(
                googleMapsContent, true));
            return true;
        }
        catch (TimeoutException e) {
            return false;
        }
    }

    public int getNumberOfSolutions() {
        return resultsList.size();
    }

    public SearchSolution bookFirstSolution() {
        WebElement element = resultsList.get(0).findElement(By.tagName("a"));
        SearchSolution solution = new SearchSolution();
        solution.setHotelName(element.findElement(By.cssSelector(" h2[data-bdd='title']")).getText().trim());
        solution.setPrice(element.findElement(By.cssSelector("div[data-bdd='displayPrice']")).getText().trim());
        solution.setRating(element.findElement(By.cssSelector("img[data-bdd='starRating']")).getAttribute("data-star"));
        element.click();
        return solution;
    }

    public WebElement getAllAreasFilterOption() {
        if (new InvisibilityOf(By.cssSelector(
                AREAS_FILTER + " ul.dropdown-menu")).apply(getWebDriver())) {
            areasDropdown.click();
        }
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
            By.cssSelector(AREAS_FILTER + " ul.dropdown-menu")));
        // This will always be the first option.
        return getWebDriver().findElement(By.cssSelector(AREAS_FILTER +  " ul.dropdown-menu li a"));
    }

    public boolean isAllAreasFilterOptionChecked() {
        return getAllAreasFilterOption().isSelected();
    }

    public void resetAreasFilter() {
        if (!isAllAreasFilterOptionChecked()) {
            getAllAreasFilterOption().click();
            waitForResultsLocationToBeStable();
        }
        else {
            logger.info("All areas option is already set.");
        }
    }

    public void filterByStarRating(String value) {
        starRatingDropdown.click();
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
            By.cssSelector(STAR_RATING_FILTER + " ul.dropdown-menu")));
        // Click on star rating value. The list index to click will be rating value - 1.
        getWebDriver().findElements(By.cssSelector(STAR_RATING_FILTER + " ul.dropdown-menu li a"))
            .get(5 - Integer.parseInt(value)).click();
        waitForResultsLocationToBeStable();
    }

    public int getStarRatingFilterCount(String value) {
        if (!getWebDriver().findElement(By.cssSelector(STAR_RATING_FILTER + " ul.dropdown-menu")).isDisplayed()) {
            starRatingDropdown.click();
            new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
                By.cssSelector(STAR_RATING_FILTER + " ul.dropdown-menu")));
        }

        return new Integer(getWebDriver().findElements(
                By.cssSelector(STAR_RATING_FILTER + " ul.dropdown-menu li a span.resultCount"))
            .get(5 - Integer.parseInt(value)).getText().replaceAll("[^\\d]", "")).intValue();
    }

    public void filterByAmenity(String value) {
        amenitiesDropdown.click();
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
            By.cssSelector(AMENITIES_FILTER + " ul.dropdown-menu")));
        for (WebElement element : getWebDriver()
                    .findElements(By.cssSelector(AMENITIES_FILTER + " ul li a[data-bdd='amenityFilterItem']"))) {
            logger.info("TEXT: " + element.findElement(By.cssSelector("span span")).getText() + " : " + value);
            if (element.findElement(By.cssSelector("span span")).getText().trim().equalsIgnoreCase(value)) {
                element.click();
                waitForResultsLocationToBeStable();
                return;
            }
        }
        throw new RuntimeException(value + " is not a valid amenity filter.");
    }

    public int getAmenitiesFilterCount(String value) {
        if (!getWebDriver().findElement(By.cssSelector(AMENITIES_FILTER + " ul.dropdown-menu")).isDisplayed()) {
            amenitiesDropdown.click();
            new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
                By.cssSelector(AMENITIES_FILTER + " ul.dropdown-menu")));
        }
        for (WebElement element : getWebDriver()
                .findElements(By.cssSelector(AMENITIES_FILTER + " ul li a[data-bdd='amenityFilterItem']"))) {
            List<WebElement> spanElems = element.findElements(By.cssSelector("span span"));
            if (spanElems.get(0).getText().trim().equalsIgnoreCase(value)) {
                return new Integer(spanElems.get(spanElems.size() - 1).getText().replaceAll("[^\\d]", "")).intValue();
            }
        }
        throw new RuntimeException(value + " is not a valid amenity filter.");
    }

    public void filterByAccessibilityAmenity(String value) {
        amenitiesDropdown.click();
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
            By.cssSelector(AMENITIES_FILTER + " ul.dropdown-menu")));
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
            By.cssSelector(AMENITIES_FILTER + " ul.dropdown-menu")));

        for (WebElement element : getWebDriver().findElements(
                    By.cssSelector(AMENITIES_FILTER + " ul li a[data-bdd='accessibilityAmenityFilterItem']"))) {
            if (element.findElement(By.cssSelector("span span")).getText().equalsIgnoreCase(value)) {
                element.click();
                waitForResultsLocationToBeStable();
                return;
            }
        }
        throw new RuntimeException(value + " is not a valid accessibility amenity filter.");
    }

    public void filterByFirstFilterableArea() {
        try {
            getFirstFilterableArea().click();
            waitForResultsLocationToBeStable();
        }
        catch (NullPointerException e) {
            throw new RuntimeException("null returned for filterable area.");
        }
    }

    public void filterByFirstFilterableAmenity() {
        try {
            getFirstFilterableAmenity().click();
            waitForResultsLocationToBeStable();
        }
        catch (NullPointerException e) {
            throw new RuntimeException("null returned for filterable amenity.");
        }
    }

    public int getFirstFilterableAreaResultCount() {
        List<WebElement> spans = getFirstFilterableArea().findElements(By.cssSelector("span span"));
        return new Integer(
            spans.get(spans.size() - 1).getText().trim().replaceAll("[^\\d]", "")).intValue();
    }

    public WebElement getFirstFilterableArea() {
        if (new InvisibilityOf(By.cssSelector(AREAS_FILTER + " ul.dropdown-menu")).apply(getWebDriver())) {
            areasDropdown.click();
            new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
                By.cssSelector(AREAS_FILTER + " ul.dropdown-menu")));
        }
        List<WebElement> areas = getWebDriver().findElements(
            By.cssSelector(AREAS_FILTER + " ul.dropdown-menu  li a"));
        for (WebElement area : areas) {
            logger.info("AREA: " + area.getText());
            if (!area.getText().trim().toLowerCase().equals("all areas")) {
                List<WebElement> spans = area.findElements(By.cssSelector("span span"));
                if (new Integer(spans.get(spans.size() - 1).getText().trim().replaceAll("[^\\d]", "")).intValue() > 0) {
                    return area;
                }
            }
        }
        return null;
    }

    public WebElement getFirstFilterableAmenity() {
        if (new InvisibilityOf(By.cssSelector(AMENITIES_FILTER + " ul.dropdown-menu")).apply(getWebDriver())) {
            amenitiesDropdown.click();
        }
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
            By.cssSelector(AMENITIES_FILTER + " ul.dropdown-menu")));
        List<WebElement> amenities = getWebDriver().findElements(
            By.cssSelector(AMENITIES_FILTER + " ul.dropdown-menu  li a"));
        for (WebElement amenity : amenities) {
            logger.info("AMENITY: " + amenity.getText());
            List<WebElement> spans = amenity.findElements(By.cssSelector("span span"));
            if (new Integer(spans.get(spans.size() - 1).getText().trim().replaceAll("[^\\d]", "")).intValue() > 0) {
                return amenity;
            }
        }
        return null;
    }

    public boolean areOptionsWithZeroCountDisabled(String filterType) {
        String root;
        WebElement rootElement;
        if (filterType.equals("Areas")) {
            root = AREAS_FILTER;
            rootElement = areasDropdown;
        }
        else if (filterType.equals("Star Rating")) {
            root = STAR_RATING_FILTER;
            rootElement = starRatingDropdown;
        }
        else if (filterType.equals("Amenities")) {
            root = AMENITIES_FILTER;
            rootElement = amenitiesDropdown;
        }
        else {
            throw new UnimplementedTestException(filterType + " is not valid for this method.");
        }

        if (new InvisibilityOf(By.cssSelector(root + " ul.dropdown-menu")).apply(getWebDriver())) {
            rootElement.click();
        }
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
            By.cssSelector(root + " ul.dropdown-menu")));
        List<WebElement> elements = getWebDriver().findElements(
            By.cssSelector(root + " ul.dropdown-menu li a"));

        ArrayList<WebElement> zeroCountElements = new ArrayList<WebElement>();
        int i = 5;
        for (WebElement element : elements) {
            logger.info("FILTER ELEMENT: " + (filterType.equals("Star Rating") ? i + " stars" : element.getText()));
            if (!filterType.equals("Star Rating")) {
                if (!(element.getText().trim().toLowerCase().equals("all areas") ||
                    element.getText().trim().toLowerCase().equals("accessibility options"))) {
                    List<WebElement> spans = element.findElements(By.cssSelector("span span"));
                    if (new Integer(spans.get(spans.size() - 1).getText().trim().replaceAll("[^\\d]", ""))
                            .intValue() == 0) {
                        zeroCountElements.add(element);
                        List<String> searchList = getAllAreaNames();
                        element.click();
                        new WebDriverWait(getWebDriver(), 10).until(
                            new IsElementLocationStable(getWebDriver(), By.cssSelector(RESULTS_LIST), 4));
                        if (!searchList.equals(getAllAreaNames())) {
                            return false;
                        }
                    }
                }
            }
            else {
                if (new Integer(element.findElement(By.cssSelector("span.resultCount")).getText().trim()
                        .replaceAll("[^\\d]", "")).intValue() == 0) {
                    zeroCountElements.add(element);
                    List<String> searchList = getAllAreaNames();
                    element.click();
                    new WebDriverWait(getWebDriver(), 5).until(
                        new IsElementLocationStable(getWebDriver(), By.cssSelector(RESULTS_LIST), 4));
                    if (!searchList.equals(getAllAreaNames())) {
                        return false;
                    }
                }
                --i;
            }
        }
        logger.info("ZERO COUNT: " + zeroCountElements.size());
        if (zeroCountElements.size() == 0) {
            throw new ZeroResultsTestException("Areas filter has 0 options with 0 count.");
        }
        return true;
    }

    public String getAreaNameFromAreaFilterElement(WebElement element) {
        return element.findElement(By.xpath("span/span")).getText().trim();
    }

    public String getAmenityNameFromAmenityFilterElement(WebElement element) {
        return element.findElement(By.xpath("span/span")).getText().trim();
    }

    public ArrayList<String> getAllResultsAmenitiesList() {
        ArrayList<String> amenities = new ArrayList<String>();
        if (0 == resultsList.size()) {
            throw new ZeroResultsTestException("Angular hotel results page: 0 results returned.");
        }
        for (WebElement result : getWebDriver().findElements(By.cssSelector(RESULTS_LIST + " ul.amenities"))) {
            List<String> text = new ArrayList<String>();
            for (WebElement list : result.findElements(By.cssSelector("li"))) {
                text.add(list.getText().trim());
            }
            if (text.size() > 0) {
                amenities.add(StringUtils.join(text, " ").trim());
            }
        }
        return amenities;
    }

    public ArrayList<String> getAllResultsPrices() {
        ArrayList<String> prices = new ArrayList<String>();
        if (0 == resultsList.size()) {
            throw new ZeroResultsTestException("Angular hotel results page: 0 results returned.");
        }
        for (WebElement result : getWebDriver().findElements(
                By.cssSelector(RESULTS_LIST + " a div[data-bdd='displayPrice']"))) {
            prices.add(result.getText().trim());
        }
        prices.remove(0);
        System.out.println("prices" + prices);
        return prices;
    }

    public ArrayList<String> getAllAreaNames() {
        ArrayList<String> areas = new ArrayList<String>();
        logger.info("RESULTS SIZE: " + resultsList.size());
        if (0 == resultsList.size()) {
            throw new ZeroResultsTestException("Angular hotel results page: 0 results returned.");
        }
        for (WebElement result : getWebDriver().findElements(
                By.cssSelector(RESULTS_LIST + " a h2[data-bdd='title']"))) {
            areas.add(result.getText().trim());
        }
        //areas.remove(0);
        return areas;
    }

    public ArrayList<String> getAllResultsRecommenedRatings() {
        ArrayList<String> recommendedRatings = new ArrayList<String>();
        if (0 == resultsList.size()) {
            throw new ZeroResultsTestException("Angular hotel results page: 0 results returned.");
        }
        for (WebElement result : getWebDriver().findElements(
                By.cssSelector(RESULTS_LIST + " a div span[data-bdd='recommended']"))) {
            if (!StringUtils.isEmpty(result.getText().trim())) {
                recommendedRatings.add(result.getText().trim());
            }
        }
        if (recommendedRatings.isEmpty()) {
            throw new ZeroResultsTestException("Zero hotel results with ratings.");
        }
        return recommendedRatings;
    }

    public ArrayList<String> getAllResultsStarRatings() {
        ArrayList<String> starRatings = new ArrayList<String>();
        if (0 == resultsList.size()) {
            throw new ZeroResultsTestException("Angular hotel results page: 0 results returned.");
        }
        for (WebElement result : getWebDriver().findElements(By.cssSelector(
                RESULTS_LIST + " a img[data-bdd='starRating']"))) {
            starRatings.add(result.getAttribute("data-star"));
        }
        //starRatings.remove(0);
        return starRatings;
    }

    public void sortResultsBy(String criteria) {
        sortByOpener.click();
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(
            By.cssSelector("div[data-bdd='filters-desktop'] ul[data-bdd='sortMenu']")));
        List<WebElement> criterias = getWebDriver().findElements(
            By.cssSelector("div[data-bdd='filters-desktop'] ul[data-bdd='sortMenu'] li"));
        if (criteria.equals("Popular")) {
            criterias.get(0).click();
        }
        else if (criteria.equals("Price (low to high)")) {
            criterias.get(1).click();
        }
        else if (criteria.equals("Price (high to low)")) {
            criterias.get(2).click();
        }
        else if (criteria.contains("Recommended (high to low)")) {
            criterias.get(3).click();
        }
        else if (criteria.equals("Star rating (high to low)")) {
            criterias.get(4).click();
        }
        else if (criteria.equals("Star rating (low to high)")) {
            criterias.get(5).click();
        }
        else {
            throw new RuntimeException("Invalid sort criteria - " + criteria);
        }
        waitForResultsLocationToBeStable();
    }

    private void waitForResultsLocationToBeStable() {
        new WebDriverWait(getWebDriver(), 15)
            .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(RESULTS_LIST), 4));
    }

    public String getCustomerCarePhoneNumber() {
        return getWebDriver().findElement(By.cssSelector("span[data-bdd='phoneNumber']")).getText().trim();
    }

    public String getCustomerCareRefNumber() {
        return getWebDriver().findElement(By.cssSelector("span[data-bdd='referenceNumber']")).getText().trim();
    }

    public void searchForHotels(String destination, Date startDate, Date endDate, Integer numberOfHotelRooms,
        Integer numberOfAdults, Integer numberOfChildren, Boolean enableHComSearch) {

        new AngularFareFinderFragment(getWebDriver()).searchForHotels(
            destination, startDate, endDate, numberOfHotelRooms, numberOfAdults, numberOfChildren, enableHComSearch);
    }

    public void clickResetFiltersLink() {
        getWebDriver().findElement(By.cssSelector(RESET_FILTERS_LINK)).click();
    }

    public boolean isResetFiltersLinkDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(RESET_FILTERS_LINK)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public AngularFareFinderFragment getFareFinderFragment() {
        return new AngularFareFinderFragment(getWebDriver());
    }

    public boolean isFareFinderFormDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector("form[name='searchForm']")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void openCollapsedFareFinder() {
        if (!isFareFinderFormDisplayed()) {
            getWebDriver().findElement(By.cssSelector("div[data-bdd='searchCriteriaInfo'] strong")).click();
        }
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector("form[name='searchForm']")));
    }

    public void clickOlabLink() {
        olabLink.click();
    }

    public void clickStandardRateHotelsTab() {
        standardRateHotelsTab.click();
    }

    public String getSearchId() {
        return getWebDriver().findElement(By.cssSelector("li.result")).getAttribute("sold-out-deal");
    }

    public String getDestinationLocation() {
        return destinationLocation.getText();
    }

}

