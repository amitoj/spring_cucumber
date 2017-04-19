/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.results;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.AllExpectedConditions;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.po.PageObjectUtils;

/**
 * This represents the page object for the mobile hotel results page
 *
 * @author prbhat
 */
public class MobileHotelResultsPage extends MobileAbstractPage {

    public static final AllExpectedConditions HOTEL_RESULTS_LOADED = new AllExpectedConditions(
            Arrays.<Function<WebDriver, ?>>asList(
                    new InvisibilityOf(By.cssSelector(".loading img")),
                    new VisibilityOf(By.id("results")),
                    new VisibilityOf(By.name("hotelResult0"))
        )
    );
    private static Logger LOGGER = LoggerFactory.getLogger(MobileHotelResultsPage.class.getSimpleName());
    private static final String RESULTS_CONTAINER = "div[id='results']";
    private static final String RESULTS_HREF_LIST = RESULTS_CONTAINER + " div.result";

    @FindBy(css = "div#results .result")
    private WebElement result1;

    @FindBy(css = "div[id='hotel'] .hwRef")
    private WebElement reference;

    @FindBy(css = "div[id='hotel' div[id='footer-buttons'] a[id='footer-feedback']")
    private WebElement feedback;

    @FindBy(css = "div#results .result .price")
    private List<WebElement> hotelResults;

    @FindBy(xpath = "//*[@data-bdd='date']")
    private WebElement date;

    @FindBy(css = "span[id='sort-button'], div[id='sort-button']")
    private WebElement sortButton;

    @FindBy(css = "div[id='sort-bar']")
    private WebElement sortBar;

    @FindBy(css = "span[id='filter-button'], div[id='filter-button']")
    private WebElement filterButton;

    @FindBy(css = "div[id='hotel'] #submitFilter")
    private WebElement filterDoneButton;

    @FindBy(css = ".hoods .hood-names li input.hood")
    private WebElement hood1;

    @FindBy(xpath = "//*[@data-bdd='sort-price']")
    private WebElement sortByPrice;

    @FindBy(xpath = "//*[@data-bdd='sort-star-rating']")
    private WebElement sortByStarRating;

    @FindBy(xpath = "//*[data-bdd='sort-distance']")
    private WebElement sortByDistance;

    @FindBy(xpath = "//*[@data-bdd='sort-best-value']")
    private WebElement sortByBestValue;

    @FindBy(xpath = "//*[@data-bdd='currency-selection']")
    private WebElement currencySelection;

    @FindBy(css = "div#results .result span.rating, div#results .result div.tag img")
    private List<WebElement> resultRatings;

    @FindBy(css = "div#results .result span.rate")
    private List<WebElement> resultRates;

    @FindBy(xpath = "//*[@data-bdd='result-distance']")
    private List<WebElement> resultDistances;

    @FindBy(xpath = "//*[@data-bdd='bv']")
    private List<WebElement> bestValueResultContainers;

    @FindBy(xpath = "//*[@data-bdd='retailhero']")
    private WebElement retailHeroModule;

    @FindBy(xpath = "//*[@data-bdd='hotel-discount-banner']")
    private WebElement discountBanner;

    @FindBy(xpath = "//*[@data-bdd='filter-button']")
    private WebElement clickFliter;

    @FindBy(xpath = "//*[@class='hood']")
    private List<WebElement> neighborhoods;

    @FindBy(xpath = "//*[@data-bdd='filter-done-button']")
    private WebElement doneFliter;

    @FindBy(xpath = "//*[@data-bdd='change-search']")
    private WebElement changeSearch;

    @FindBy(css = RESULTS_HREF_LIST)
    private List<WebElement> resultsHrefList;

    @FindBy(css = "div[id='starButtons'] span")
    private List<WebElement> starRatingFilters;

    public MobileHotelResultsPage(WebDriver webdriver) {
        super(webdriver, "tile.hotel.results.*", HOTEL_RESULTS_LOADED, TIME_TO_WAIT, MAX_SEARCH_PAGE_WAIT_SECONDS);
        waitForTemplateLoad(this.date);
    }

    public void waitForTemplateLoad(WebElement elem) {
        new WebDriverWait(getWebDriver(), TIME_TO_WAIT).
                until(PageObjectUtils.webElementVisibleTestFunction(elem, true));
    }

    public void select(Integer resultNumberToSelect) {
        this.hotelResults.get(resultNumberToSelect).click();
        new WebDriverWait(getWebDriver(), TIME_TO_WAIT).until(PageObjectUtils.webElementVisibleTestFunction(
            By.cssSelector(".loading img"), false));
    }

    public List<WebElement> getClickableHrefList() {
        return resultsHrefList;
    }

    /**
     * On the mobile hotel results page, click on the "See areas" button
     */
    public void clickAreaMap() {
        WebElement areaMap = getWebDriver().findElement(By.xpath("//*[@data-bdd='area-map']"));
        areaMap.click();
    }

    public void filterByStarRating(String rating) {
        filterButton.click();
        for (WebElement element : starRatingFilters) {
            if (element.getAttribute("data-star").equals(rating)) {
                element.click();
                break;
            }
        }
        filterDoneButton.click();
    }

    /**
     * If there is more than one hood, filter by the first one
     */
    public void filteredByHoodName(String hoodName) {
        filterButton.click();
        List<WebElement> hoodsNames = getWebDriver().findElements(By.xpath(".//*[@class='hood-names']//li"));
        int i = 0;
        for (i = 0; i < hoodsNames.size(); i++) {

            if (hoodsNames.get(i).getText().equals(hoodName)) {
                break;
            }
        }
        hoodsNames.get(i).findElement(By.tagName("input")).click();
        filterDoneButton.click();
    }

    public void filterByFirstHood() {
        LOGGER.info("Open filter screen");
        int resultsTotalBefore = getWebDriver().findElements(By.xpath("//*[contains(@data-bdd,'priceLockup-')]"))
            .size();
        this.filterButton.click();
        new WebDriverWait(getWebDriver(), TIME_TO_WAIT).until(PageObjectUtils.webElementVisibleTestFunction(
            this.filterDoneButton, true));
        List<WebElement> hoods = getWebDriver().findElements(By.xpath("//*[contains(@data-bdd,'hood-')]"));
        int hoodsTotal = hoods.size();
        if (hoodsTotal > 0) {
            this.hood1.click();
        }
        this.filterDoneButton.click();
        new MobileHotelResultsPage(getWebDriver());
        if (hoodsTotal > 1) {
            List<WebElement> filteredResults = getWebDriver().findElements(
                By.xpath("//*[contains(@data-bdd,'priceLockup-')]"));
            boolean resultsAreFiltered = filteredResults.size() < resultsTotalBefore;
            if (!resultsAreFiltered) {
                throw new RuntimeException("Whoops, results were not filtered.");
            }
            else {
                LOGGER.info(resultsTotalBefore + " results were filtered to " + filteredResults.size());
            }
        }
        else {
            LOGGER.info("Only one hood available, not enough to try filtering.");
        }
        LOGGER.info("Close filter screen");
    }

    /**
     * You are on the results page only if you have atleast one hotel result
     */
    public void assertResultOnPage() {
        new WebDriverWait(getWebDriver(), MAX_SEARCH_PAGE_WAIT_SECONDS).until(PageObjectUtils
            .webElementVisibleTestFunction(this.result1, true));
        assertThat(this.hotelResults.isEmpty())
            .as("Expected to be on mobile hotel results page with atleast one result. Number of results is zero")
            .isFalse();
        this.assertReferenceNumberExists();
    }

    public void sortByCriteria(String sortCriteria) {
        String xp;

        if ("price".equalsIgnoreCase(sortCriteria)) {
            xp = "//*[@data-bdd='sort-price']";
        }
        else if ("distance".equalsIgnoreCase(sortCriteria)) {
            xp = "//*[@data-bdd='sort-distance']";
        }
        else if ("star rating".equalsIgnoreCase(sortCriteria)) {
            xp = "//*[@data-bdd='sort-star-rating']";
        }
        else if ("best value".equalsIgnoreCase(sortCriteria)) {
            xp = "//*[@data-bdd='sort-best-value']";
        }
        else {
            throw new RuntimeException("Unrecognized sort criteria.");
        }

        if (!this.sortBar.isDisplayed()) {
            // The sorting button is hidden.
            this.sortButton.click();
            new MobileHotelResultsPage(getWebDriver());
        }
        new WebDriverWait(getWebDriver(), TIME_TO_WAIT).until(PageObjectUtils.webElementVisibleTestFunction(
            getWebDriver().findElement(By.xpath(xp)), true));
        // Now the actual button should be visible
        getWebDriver().findElement(By.xpath(xp)).click();
        new MobileHotelResultsPage(getWebDriver());
        // @TO DO, TECHDEBT: Piggy backing here for now until we have bandwidth to separate out into it's own step
        // definition
        filterByFirstHood();
        new MobileHotelResultsPage(getWebDriver());
    }

    /**
     * Assert that the result set is sorted by the criteria by looking at the URL
     */
    public void assertSortSuccessful(String criteria) {
        new MobileHotelResultsPage(getWebDriver());
        ArrayList<Double> elementValues = new ArrayList<Double>();
        boolean sortAscending = false;
        if ("price".equalsIgnoreCase(criteria)) {
            sortAscending = (this.sortByPrice.getAttribute("class").contains("arrowUp")) ? true : false;
            for (WebElement element : this.resultRates) {
                elementValues.add(new Double(element.getText().replaceAll("[^0-9.]", "")));
            }
        }
        else if ("distance".equalsIgnoreCase(criteria)) {
            // Distance always sorted in ascending order.
            sortAscending = true;
            for (WebElement element : this.resultDistances) {
                String text = element.getText();
                elementValues.add(new Double(text.substring(text.indexOf("(") + 1, text.indexOf(" "))));
            }
        }
        else if ("star rating".equalsIgnoreCase(criteria)) {
            sortAscending = (this.sortByStarRating.getAttribute("class").contains("arrowUp")) ? true : false;
            for (WebElement element : this.resultRatings) {
                String starRating = element.getAttribute("title").trim();
                // Handle the case where there is no retail hero module on the results page
                if (starRating != null && !starRating.isEmpty()) {
                    elementValues.add(new Double(starRating));
                }
            }
        }
        else if ("best value".equalsIgnoreCase(criteria)) {
            // All results on the page are sorted in ascending order by the best value index exposed via the
            // data attribute 'data-bv-index'
            sortAscending = true;
            for (WebElement element : this.bestValueResultContainers) {
                // Add in the rate for best value result.
                elementValues.add(Double.valueOf(element.getAttribute("data-bv-index")));
            }
        }
        else {
            throw new RuntimeException("Unrecognized sort criteria.");
        }

        if (elementValues.size() > 1) {
            assertThat(areValuesInListSorted(elementValues, sortAscending))
                .as("Expected " + ((!sortAscending) ? "descending" : "ascending") +
                    " ordered results to be true but got false instead")
                .isTrue();
        }
        else if (elementValues.isEmpty()) {
            throw new ZeroResultsTestException("0 results returned.");
        }
        else {
            // 1 result list is always sorted.
            LOGGER.info("1 result returned.");
        }
    }

    /**
     * Verify the retail hero module is present and visible. Verify the path of the deep link url, and the presence of
     * certain query string parameters
     */
    public void assertRetailHeroIsPresent() {
        // assert the module that is being targeted is displayed before proceeding
        assertThat(this.retailHeroModule.isDisplayed())
            .as("Expected to see retail hero module present on results page")
            .isTrue();

        String uRL_PATH = "/hotel/deeplink-details.jsp";
        StringBuilder errorString = new StringBuilder();
        boolean wellFormed = false;

        // get the deep link/retail hero url from the retail hero module
        URI uri = URI.create(this.retailHeroModule.getAttribute("data-hero-url"));
        // create a list of all query string parameters
        List<NameValuePair> urlParameters = URLEncodedUtils.parse(uri, "UTF-8");

        // check that the path is correct for the url
        wellFormed = hasValidRetailHeroPath(uRL_PATH, uri.getPath(), errorString);
        wellFormed = wellFormed && hasValidQueryStringValues(urlParameters, errorString);

        assertThat(wellFormed)
            .as(errorString.toString())
            .isTrue();
    }

    public void clickRetailHeroResult() {
        retailHeroModule.findElement(By.cssSelector(".hero-price")).click();
    }

    /**
     * Compare two paths for equality
     *
     * @param expectedPath expected url path
     * @param actualPath   actual path
     * @param errorString  StringBuilder to append any errors to
     * @return true if both paths are non-null, non-empty, and equal, false otherwise
     */
    private boolean hasValidRetailHeroPath(String expectedPath, String actualPath, StringBuilder errorString) {
        if (StringUtils.isNotEmpty(actualPath) && StringUtils.isNotEmpty(expectedPath)) {
            return expectedPath.equals(actualPath);
        }
        errorString.append("Expected path: " + expectedPath + " instead got: " + actualPath);
        return false;
    }

    /**
     * Compare expected query strings and values to actual query strings and values
     *
     * @param urlParameters list of query string parameters and values
     * @param errorString   StringBuilder to append any errors to
     * @return true if all required parameters exist and have correct values, false otherwise
     */
    private boolean hasValidQueryStringValues(List<NameValuePair> urlParameters, StringBuilder errorString) {
        String resultId = "resultId";
        String inputId = "inputId";
        String actionType = "actionType";
        String expectedActionType = "303";
        String expectedInputId = "hotel-index";
        boolean wellFormed = true;

        for (NameValuePair param : urlParameters) {
            boolean parameterIsValid = false;
            if (resultId.equals(param.getName())) {
                parameterIsValid = StringUtils.isNotEmpty(param.getValue());
                errorString.append(parameterIsValid ? "" : " | Expected " + resultId +
                    "to not be empty instead got: " + param.getValue());
            }
            else if (inputId.equals(param.getName())) {
                parameterIsValid = expectedInputId.equals(param.getValue());
                errorString.append(parameterIsValid ? "" : " | Expected " + inputId + " to be: " + expectedInputId +
                    " instead got: " + param.getValue());
            }
            else if (actionType.equals(param.getName())) {
                parameterIsValid = expectedActionType.equals(param.getValue());
                errorString.append(parameterIsValid ? "" : " | Expected " + actionType + " to be: " +
                    expectedActionType + " instead got: " + param.getValue());
            }
            else {
                // all other parameters will fall here ex: marketing sid/bid values. They do not need to be verified
                // and shouldn't affect the outcome of the test
                parameterIsValid = true;
            }
            // keep track of the overall status of the deep link
            wellFormed = wellFormed && parameterIsValid;
        }
        return wellFormed;
    }

    private boolean areValuesInListSorted(List<Double> values, boolean sortAscending) {
        for (int i = 1; i < values.size(); i++) {
            Double a = values.get(i);
            Double b = values.get(i - 1);
            LOGGER.info("Compare " + b + (sortAscending ? " <= " : " >= ") + a);
            if (sortAscending) {
                if (!(b.compareTo(a) == 0 || b.compareTo(a) == -1)) {
                    return false;
                }
            }
            else {
                // Descending order.
                if (!(b.compareTo(a) == 0 || b.compareTo(a) == 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkKey(String val, String key) {
        if (!val.contains(key)) {
            throw new RuntimeException("Did not find: " + key);
        }
    }

    public void changeCurrency(String currencyCode) {
        Select selectMenu = new Select(this.currencySelection);
        selectMenu.selectByValue(currencyCode);
        if (currencyCode.equals("USD")) {
            currencyCode = "$";
        }
        else if (currencyCode.equals("GBP")) {
            currencyCode = "£";
        }
        else if (currencyCode.equals("EUR")) {
            currencyCode = "€";
        }
        new MobileHotelResultsPage(getWebDriver());
        new WebDriverWait(getWebDriver(), TIME_TO_WAIT).
                until(PageObjectUtils.webElementVisibleTestFunction(this.result1, true));

        // Assert that the first result's currency symbol or code has changed
        this.checkKey(this.result1.getText(), currencyCode);
    }

    private void assertReferenceNumberExists() {
        assertThat(this.reference != null)
            .as("Expected to see a reference number but did not find any")
            .isTrue();
        assertThat(this.reference.isDisplayed())
            .as("Expected the reference number to be visible but is not")
            .isTrue();
    }

    @SuppressWarnings("unused")
    private void assertFeedbackFormExists(WebDriver webdriver) {
        this.feedback.click();
        webdriver.navigate().back();
    }

    public boolean verifyDiscountBannerOnResultsPage() {
        return discountBanner.isDisplayed();
    }

    public List<SearchSolution> getSearchSolutionList() {
        List<SearchSolution> searchSolutionList = new ArrayList<>();

        List<WebElement> elements = getWebDriver().findElements(By.cssSelector("div.result"));
        for (int i = 0; i < elements.size(); i++) {
            SearchSolution searchSolution = new SearchSolution();
            searchSolution.setNumber(i);

            String hotelName = elements.get(i).findElement(By.cssSelector("strong.ng-binding")).getText();
            searchSolution.setHotelName(hotelName);

            searchSolutionList.add(searchSolution);
        }

        return searchSolutionList;
    }

    public String getCurrency() {
        return new Select(getWebDriver().findElement(By.id("currencies"))).getFirstSelectedOption().getText();
    }

    public void clickFliter() {
        clickFliter.click();
    }

    public void sortByNeighborhood() {
        clickFliter();
        for (WebElement hood : neighborhoods) {
            if (hood.isSelected()) {
                hood.click();
                break;
            }
        }
        doneFliter.click();
    }

    public void changeHotelSearch() {
        changeSearch.click();
    }

    public void selectFirstResultByType(String resultsType) {
        if (resultsType.equals("opaque")) {
            List<WebElement> results = getWebDriver().findElements(By.cssSelector(".cost.opaque"));
            results.get(0).click();
        }
        else {
            List<WebElement> results = getWebDriver().findElements(By.cssSelector(".cost.retail"));
            results.get(0).click();
        }

    }

    public boolean isSoldOutMessageDisplayed() {
        return getWebDriver().findElement(By.cssSelector("div.hero-soldOut")).isDisplayed();
    }
}
