/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.farefinder.HotelResultsFareFinderFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.filters.HotelResultsFilteringTabsFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.tripwatcher.HotelResultsTripwatcherLayerFragment;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.AreElementsOverlapping;
import com.hotwire.util.webdriver.functions.AreElementsVerticallyAligned;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * This class represent hotel results page.
 * @author Alok Gupta
 * @since 2012.04
 */

public class HotelResultsPage extends AbstractPageObject {
    public static final String[] PAGE_NAMES = new String[] {
        "tile.hotel.search.results.*",
        "tile.hotel.results.*",
        "tile.hotel.search.layout.blank"};

    @SuppressWarnings("serial")
    public static final List<String> ALL_INCLUSIVE_LABELS = new ArrayList<String>() {
        {
            add("total for your stay");
            add("I alt for dit ophold");
            add("Ihr Gesamtpreis");
            add("total por tu estancia");
            add("totalt for oppholdet ditt");
            add("totalt för hela vistelsen");
        }
    };

    public static final int DEFAULT_WAIT = 25;
    public static final int MAX_SEARCH_UPDATE_TIMEOUT = 90;
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelResultsPage.class);

    private static final String INFORMATIONAL_LOCATION = "informational";
    private static final String NO_RESULTS_MESSAGE = ".//div[contains(@class, 'filteredOut')]";
    private static final String TOP_CUSTOMER_CARE = ".CTB12.callToBookLayer";
    private static final String TOP_CUSTOMER_CARE_PHONE = TOP_CUSTOMER_CARE + " .CTB12phoneNumber";
    private static final String TOP_CUSTOMER_CARE_REF_NUM = TOP_CUSTOMER_CARE + " div";
    private static final String HOTEL_RESULTS_PAGE_OPAQUE_ID = "opaque-hotel-results";
    private static final String HOTEL_RESULTS_PAGE_RETAIL_ID = "retail-hotel-results";
    private static final String STANDARD_RATES_LINK = ".tab-links a.retail";
    private static final String SUPER_HOT_RATES_LINK = ".tab-links a.opaque";
    private static final String SEE_ALL_RESULTS_BUTTON = ".seeAllHotels";
    private static final String COMMENT_CARD_LINK = ".feedbackTab, .OpinionLabComp a, .opinionLabTabComp a";
    private static final String GOOGLE_MAPS_CONTENT = "a[href*='http://maps.google.com/maps']";
    private static final String MAP_CONTAINER = " .hotelMap";
    private static final String FIRST_RESULT_FROM_RESULTS_FRAGMENT = " ul.results";
    private static final String SORT_BY_DROPDOWN = "sortCriteria";
    private static final String TOP_CUSTOMER_CARE_HEADER = ".customerCareHeaderText";
    private static final String TOP_CUSTOMER_CARE_PHONE_NUMBER = ".customerCarePhoneNumber";
    private static final String TOP_CUSTOMER_CARE_REFERENCE_NUMBER = ".customerCareRefNumber";
    private static final String TOP_CUSTOMER_CARE_LIVE_CHAT = ".customerCareChatLayer a";
    private static final String REFERENCE_PRICE_LINK_CONTAINER = ".reference-price";
    private static final String REFERENCE_PRICE_PRICE = REFERENCE_PRICE_LINK_CONTAINER + " .price";
    private static final String MESSAGE_BOX_SUCCESS = ".msgBox.successMessages";
    private static final String MESSAGE_BOX_NOTIFICATION = ".msgBox.notificationMessages";
    private static final String DEAL_CALLOUT = ".dealcallout";
    private static final int MAX_THRESHOLD = 150;
    private static final String RESULTS_LIST = "#opaque-hotel-results ul.results li.opaque," +
        " #retail-hotel-results ul.results li.retail";

    @FindBy(css = "div.msgBoxHeader")
    private WebElement errorMessage;

    @FindBy(css = TOP_CUSTOMER_CARE)
    private WebElement topCustomerCareModule;

    @FindBy(css = TOP_CUSTOMER_CARE_HEADER)
    private WebElement topCustomerCareHeader;

    @FindBy(css = TOP_CUSTOMER_CARE_PHONE)
    private WebElement topCustomerCarePhone;

    @FindBy(css = TOP_CUSTOMER_CARE_REF_NUM)
    private WebElement topCustomerCareRefNum;

    @FindBy(css = ".opaqueTab a")
    private WebElement opaqueLink;

    @FindBy(css = ".retailTab a")
    private WebElement retailLink;

    @FindBy(id = "filterStarRatingForm")
    private WebElement filterStarRatingForm;

    @FindBy(id = "filterCustomerRatingForm")
    private WebElement filterCustomerRatingForm;

    @FindBy(css = ".DualSliderComp .sliderContainer")
    private WebElement priceSliderFilter;

    @FindBy(css = ".NeighborhoodFilterComp")
    private WebElement neighborhoodFilter;

    @FindBy(css = ".filters.HotelFeaturesFilter")
    private WebElement featuresFilter;

    @FindBy(css = ".filters.TravelerRatingFilterComp")
    private WebElement travelerRatingFilter;

    @FindBy(css = ".filters.HotelNameFilter")
    private WebElement hotelNameFilter;

    @FindBy(css = ".editSearchLinkLabel")
    private WebElement editSearch;

    @FindBy(className = INFORMATIONAL_LOCATION)
    private WebElement infoLocation;

    @FindBy(xpath = NO_RESULTS_MESSAGE)
    private WebElement noResultsMessage;

    @FindBy(id = HOTEL_RESULTS_PAGE_OPAQUE_ID)
    private WebElement opaqueResults;

    @FindBy(id = HOTEL_RESULTS_PAGE_RETAIL_ID)
    private WebElement retailResults;

    @FindBy(css = TOP_CUSTOMER_CARE_PHONE_NUMBER)
    private WebElement customerCarePhoneNumber;

    @FindBy(css = TOP_CUSTOMER_CARE_REFERENCE_NUMBER)
    private WebElement customerCareRefNumber;

    @FindBy(css = TOP_CUSTOMER_CARE_LIVE_CHAT)
    private WebElement customerCareLiveChatLink;

    @FindBy(css = STANDARD_RATES_LINK)
    private WebElement showRetailResultsLink;

    @FindBy(css = SUPER_HOT_RATES_LINK)
    private WebElement showOpaqueResultsLink;

    @FindBy(css = SEE_ALL_RESULTS_BUTTON)
    private WebElement seeAllHotelsButton;

    @FindBy(css = COMMENT_CARD_LINK)
    private WebElement commentCardLink;

    @FindBy(css = GOOGLE_MAPS_CONTENT)
    private WebElement googleMapsContent;

    @FindBy(css = MAP_CONTAINER)
    private WebElement mapContainer;

    @FindBy(css = FIRST_RESULT_FROM_RESULTS_FRAGMENT)
    private WebElement firstResult;

    @FindBy(id = SORT_BY_DROPDOWN)
    private WebElement sortByDropdown;

    @FindBy(css = REFERENCE_PRICE_LINK_CONTAINER)
    private WebElement referencePriceLinkContainer;

    @FindBy(css = REFERENCE_PRICE_PRICE)
    private WebElement referencePriceTextValue;

    @FindBy(css = MESSAGE_BOX_SUCCESS)
    private WebElement successMessageContainer;

    @FindBy(css = MESSAGE_BOX_NOTIFICATION)
    private WebElement notificationMessageContainer;

    @FindBy(css = DEAL_CALLOUT)
    private WebElement heresYourDeal;

    @FindBy(xpath = ".//*[@id='tileName-discountCode']/div[1]")
    private WebElement discountCodeNote;

    @FindBy(css = FIRST_RESULT_FROM_RESULTS_FRAGMENT + ", " + MAP_CONTAINER)
    private List<WebElement> alignmentElements;

    @FindBy(css = "span.destinationName")
    private WebElement destinationName;

    @FindBy(xpath = ".//*[@id='dbmSubscriptionModule']/form/input[@id='email']")
    private WebElement subscriptionEmail;

    @FindBy(xpath = ".//*[@id='dbmSubscriptionModule']/form/input[@id='placementCode']")
    private WebElement placementCode;

    @FindBy(xpath = "//*[@id='dbmSubscriptionModule']/form/button")
    private WebElement subscribeButton;

    @FindBy(xpath = "//div[contains(@class, 'rossSellModule')]//span")
    private List<WebElement> crossSellLinks;

    @FindBy(css = "div.discountCode")
    private WebElement discountBanner;

    @FindBy(css = "div.discountCodeText")
    private WebElement discountPriceInfo;

    public HotelResultsPage(WebDriver webdriver) {
        super(webdriver, PAGE_NAMES);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(RESULTS_LIST);
    }

    @Override
    protected int getTimeout() {
        return MAX_SEARCH_UPDATE_TIMEOUT;
    }

    public String getTopCustomerCareReferenceNumberText() {
        return topCustomerCareRefNum.getText();
    }

    public boolean isNoResultsMessageDisplayed() {
        try {
            return noResultsMessage.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void makeVisibleFareFinder() {
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(HotelSearchFragment.SEARCH_BUTTON)));
        if (!getWebDriver().findElement(By.cssSelector(HotelSearchFragment.SEARCH_BUTTON)).isDisplayed()) {
            this.getEditSearch().click();
        }
    }

    public boolean opaqueResultsAreDisplayed() {
        try {
            new WebDriverWait(getWebDriver(), 1).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By
                .id(getOpaqueResultsDisplayedLocator())));
            return true;
        }
        catch (TimeoutException e) {
            return false;
        }
    }

    public void selectSortBy(String itemText) {
        new Select(getSortByDropdown()).selectByVisibleText(itemText);
    }

    public boolean isStarRatingFilterDisplayed() {
        return filterStarRatingForm.isDisplayed();
    }

    public boolean isCustomerRatingFilterDisplayed() {
        return filterCustomerRatingForm.isDisplayed();
    }

    public boolean isPriceSliderFilterDisplayed() {
        return priceSliderFilter.isDisplayed();
    }

    public boolean isNeighborhoodFilterDisplayed() {
        return neighborhoodFilter.isDisplayed();
    }

    public boolean isFeaturesFilterDisplayed() {
        return featuresFilter.isDisplayed();
    }

    public boolean isTravelerRatingFilterDisplayed() {
        return travelerRatingFilter.isDisplayed();
    }

    public boolean isHotelNameFilterDisplayed() {
        return hotelNameFilter.isDisplayed();
    }

    public String getInformationalLocationText() {
        return infoLocation.getText();
    }

    public boolean isLiveChatDisplayed() {
        try {
            return customerCareLiveChatLink.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void openLiveChat() {
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT).until(new IsElementLocationStable(getWebDriver(), By
            .cssSelector(TOP_CUSTOMER_CARE_LIVE_CHAT)));
        customerCareLiveChatLink.click();
    }

    public String getTopCustomerCareHeader() {
        return topCustomerCareHeader.getText();
    }

    public String getCustomerCarePhoneNumber() {
        return customerCarePhoneNumber.getText();
    }

    public String getTopCustomerCareReferenceNumber() {
        return customerCareRefNumber.getText();
    }

    public boolean isReferencePriceModuleDisplayed() {
        try {
            return referencePriceLinkContainer.isDisplayed() && referencePriceTextValue.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickReferencePriceLinkContainer() {
        referencePriceLinkContainer.click();
    }

    public Double getReferencePriceValue() {
        return new Double(referencePriceTextValue.getText().replaceAll("[^0-9]", ""));
    }

    public boolean isCommentCardLinkDisplayed() {
        return commentCardLink.isDisplayed();
    }

    public HotelResultsPage chooseOpaque() {
        if (!opaqueResultsAreDisplayed()) {
            showOpaqueResultsLink.click();
        }
        return new HotelResultsPage(getWebDriver());
    }

    public HotelResultsPage chooseRetail() {
        if (opaqueResultsAreDisplayed()) {
            showRetailResultsLink.click();
        }
        return new HotelResultsPage(getWebDriver());
    }

    public int getTotalsFromOpaqueTab() {
        String tabText = showOpaqueResultsLink.getText();
        return new Integer(tabText.substring(tabText.indexOf('(') + 1, tabText.indexOf(')')));
    }

    public int getTotalsFromRetailTab() {
        String tabText = showRetailResultsLink.getText();
        return new Integer(tabText.substring(tabText.indexOf('(') + 1, tabText.indexOf(')')));
    }

    public String getOpaqueResultsDisplayedLocator() {
        return HOTEL_RESULTS_PAGE_OPAQUE_ID;
    }

    public void clickCommentCardLink() {
        commentCardLink.click();
    }

    public WebElement getSortByDropdown() {
        return sortByDropdown;
    }

    public boolean isMapVerticallyAlignedWithResults() {
        return new AreElementsVerticallyAligned(alignmentElements.toArray(new WebElement[alignmentElements.size()]),
            MAX_THRESHOLD).apply(getWebDriver());
    }

    public boolean isResultsAndMapNotOverlapping() {
        return new AreElementsOverlapping(alignmentElements.get(0), alignmentElements.get(1)).apply(getWebDriver());
    }

    public HotelResultsFilteringTabsFragment getFilterTabsFragment() {
        return new HotelResultsFilteringTabsFragment(getWebDriver());
    }

    public HotelResultsSearchResultsFragment getSearchResultsFragment() {
        return new HotelResultsSearchResultsFragment(getWebDriver());
    }

    public HotelResultsFareFinderFragment getFareFinderFragment() {
        return new HotelResultsFareFinderFragment(getWebDriver());
    }

    public HotelResultsTripwatcherLayerFragment getTripwatcherLayerFragment() {
        return new HotelResultsTripwatcherLayerFragment(getWebDriver());
    }

    public boolean dareToCompareModuleExists() {
        if (getWebDriver().findElements(By.cssSelector("#intentMedia, #intentMediaContent")).size() > 0) {
            return true;
        }
        return false;
    }

    public boolean heresYourDealIsDisplayed() {
        boolean isDisplayed = false;
        try {
            if (heresYourDeal.isDisplayed()) {
                isDisplayed = true;
            }
        }
        catch (NoSuchElementException e) {
            // use default value for isDisplayed.
        }
        if (!isDisplayed) {
            // Success message not displayed. Maybe notification message got
            // displayed.
            try {
                LOGGER
                    .info("Success message and here's your deal banner not seen. Checking for notification messages.");
                if (notificationMessageContainer.isDisplayed()) {
                    // This is negative test case if deal is gone or price compared to deal has gone up. Deals module
                    // is working.
                    isDisplayed = true;
                }
            }
            catch (NoSuchElementException e) {
                // use default value for isDisplayed. Which at this point is false.
            }
        }
        else {
            // Deal is displayed. See if success message was seen. If seen, then price got better. If not seen, then
            // price has not changed.
            try {
                if (successMessageContainer.isDisplayed()) {
                    LOGGER.info("Here's your deal banner and success message seen.");
                }
                else {
                    LOGGER.info("Here's your deal banner seen with no success message.");
                }
            }
            catch (NoSuchElementException e) {
                LOGGER.info("Here's your deal banner seen with no success message.");
            }
        }
        return isDisplayed;
    }

    public String getMessageBoxMessage() {
        return notificationMessageContainer.getText();
    }

    public void select(Integer resultNumberToSelect) {
        HotelResultsSearchResultsFragment resultsFragment = getSearchResultsFragment();
        if (resultsFragment.getSearchResultsHrefList().size() == 0) {
            throw new ZeroResultsTestException("0 hotel search results returned.");
        }
        WebElement selectedHotelResult = resultsFragment.getResultsHrefsElements().get(resultNumberToSelect);
        if (opaqueResultsAreDisplayed()) {
            selectedHotelResult.click();
        }
        else {
            // HACK! for retail results issue clicking result item.
            getWebDriver().navigate().to(selectedHotelResult.getAttribute("href"));
        }
    }

    public List<SearchSolution> getSearchSolutionList() {
        List<SearchSolution> searchSolutionList = new ArrayList<SearchSolution>();
        int number = 0;
        for (WebElement result : getWebDriver().findElements(By.cssSelector(RESULTS_LIST))) {
            SearchSolution searchSolution = new SearchSolution();
            searchSolution.setNumber(number++);

            String hotelName = result.findElement(By.cssSelector("div.neighborhoodName")).getText();
            searchSolution.setHotelName(hotelName);

            String price = result.findElement(By.cssSelector("div.price")).getText();
            searchSolution.setPrice(price);

            String priceInfo = result.findElement(By.cssSelector("div.priceInfo")).getText();
            searchSolution.setPriceLabel(priceInfo);

            try {
                WebElement customerRecommends = result.findElement(By
                    .cssSelector("div.recommendationContainer div.thumbsupRecom"));
                String rating = customerRecommends.getText().replaceAll("[^0-9%]", "");
                if (!rating.isEmpty()) {
                    searchSolution.setRating(rating);
                }
            }
            catch (NoSuchElementException e) {
                // No action needed
            }

            searchSolutionList.add(searchSolution);
        }
        return searchSolutionList;
    }

    public boolean isSeeAllHotelsButtonDisplayed() {
        try {
            return seeAllHotelsButton.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickSeeAllHotelsButton() {
        // only seen on retail results.
        if (!opaqueResultsAreDisplayed()) {
            seeAllHotelsButton.click();
        }
    }

    public Boolean verifyDiscountNoteShown() {
        try {
            discountCodeNote.isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String doSubscription(String email) {
        String sPlacementCode = placementCode.getAttribute("value");
        subscriptionEmail.sendKeys(email);
        subscribeButton.click();
        return sPlacementCode;
    }

    public boolean isOpaqueTabVisible() {
        return showOpaqueResultsLink.isDisplayed();
    }

    public boolean isRetailTabVisible() {
        return showRetailResultsLink.isDisplayed();
    }

    public String getDestinationName() {
        return destinationName.getText();
    }

    public WebElement getDestinationNameElement() {
        return destinationName;
    }

    public List<Integer> getPriceList() {
        List<Integer> lsPrices = new ArrayList<Integer>();
        Integer price = 0;
        List<SearchSolution> lsSearchSolution = this.getSearchSolutionList();

        for (Integer i = 0; i < lsSearchSolution.size(); i++) {
            price = Integer.valueOf(lsSearchSolution.get(i).getPrice().replace("$", "").replace(",", "")); // without $
                                                                                                           // sign and
                                                                                                           // string
                                                                                                           // like
                                                                                                           // '1,474'
            lsPrices.add(price);
        }
        return lsPrices;
    }

    public List<String> getDistanceList() {
        List<String> lsDistance = new ArrayList<String>();
        String distance;

        for (WebElement result : getWebDriver().findElements(By.xpath("//STRONG[@class='miles']"))) {
            distance = result.getText();
            assertThat(distance).matches("\\d+.\\d{1} - \\d+.\\d{1} mi").as("Distance not equals to '*-*mi'");
            lsDistance.add(distance);
        }
        return lsDistance;
    }

    public SearchSolution clickCrossSellLink(Integer number) {
        SearchSolution solution = new SearchSolution();
        WebElement crossSellModule = getWebDriver().findElements(By.xpath("//div[contains(@class, 'rossSellModule')]"))
            .get(number);
        solution.setPrice(crossSellModule.findElement(By.xpath("//div[contains(@class, 'hotelPriceModule')]//strong"))
            .getText());
        solution.setPriceLabel(crossSellModule.findElement(
            By.xpath("//div[contains(@class, 'hotelPriceModule')]//small")).getText());

        crossSellLinks.get(number).click();
        return solution;
    }

    public boolean isDiscountBannerPresent() {
        try {
            discountBanner.isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getDiscountAmount() {
        return discountBanner.findElement(By.cssSelector("div.discountCodeText")).getText();
    }

    public boolean getIsDiscountPercent() {
        String discountType = discountBanner.findElement(By.cssSelector("div.discountCodeText")).getText();
        return discountType.contains("%");
    }

    public String getSortingValue() {
        return new Select(getSortByDropdown()).getFirstSelectedOption().getAttribute("value");
    }

    public List<String> getHotelRefNumbersList() {
        // open hidden 'HW ref#' texts
        if (getWebDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) getWebDriver()).executeScript("$('.singleResult .detailsLink')" +
                ".each( function(index)" +
                " { var refNumber = $(this).attr('data-refnumber'); $(this).text('Hw ref# ' + refNumber);" +
                " $(this).css({position:'relative', width:'auto', height:'auto'});} );void(0);");
        }

        List<String> lsRefs = new ArrayList<String>();
        String ref;
        for (WebElement result : getWebDriver().findElements(By.xpath("//A[@class='detailsLink']"))) {
            ref = result.getText();
            assertThat(ref).matches("Hw ref# \\d{10}").as("Reference number not equals to 'Hw ref# 0123456789'");
            lsRefs.add(ref.replace("Hw ref# ", ""));
        }
        return lsRefs;
    }

    public String getHotelSearchId() {
        WebElement searchIdElement = getWebDriver().findElement(
            By.cssSelector("form[id='hotel-sort'] input[id='resolvedSearchId']"));
        return searchIdElement.getAttribute("value");
    }

    /**
     * @return the errorMessage
     */
    public WebElement getErrorMessage() {
        return errorMessage;
    }

    public boolean isErrorMessageDisplayed() {
        try {
            errorMessage.isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage(WebElement errorMessage) {
        this.errorMessage = errorMessage;
    }

    public WebElement getEditSearch() {
        return editSearch;
    }
}
