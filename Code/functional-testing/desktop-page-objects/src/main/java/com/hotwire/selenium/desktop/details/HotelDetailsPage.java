/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.details;

import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Converged hotel details page.
 *
 * @author vjong
 */
public class HotelDetailsPage extends AbstractPageObject {
    public static final String TILE_HOTEL_DETAILS_OPAQUE = "tile.hotel.details.opaque";
    public static final String TILE_HOTEL_DETAILS_RETAIL = "tile.hotel.details.retail";
    public static final String TILE_HOTEL_DETAILS_OPAQUE_OVERVIEW = TILE_HOTEL_DETAILS_OPAQUE + ".overview";
    public static final String TILE_HOTEL_DETAILS_RETAIL_OVERVIEW = TILE_HOTEL_DETAILS_RETAIL + ".overview";
    public static final String[] TILE_HOTEL_DETAILS_OVERVIEW = {
        TILE_HOTEL_DETAILS_OPAQUE,
        TILE_HOTEL_DETAILS_RETAIL,
        TILE_HOTEL_DETAILS_OPAQUE_OVERVIEW,
        TILE_HOTEL_DETAILS_RETAIL_OVERVIEW
    };
    private static final String AMENITIES_LIST = ".accessibilityList li .generalAmenityName";
    private static final String FREE_AMENITIES_LIST = ".greenAmenityName";
    private static final String LAST_BOOKED_HOTEL_MESSAGE = ".lastHotelBookedInMHC .message";
    private static final String RESORT_FEE_MODULE = ".fees ul li, .mandatoryFeeModule";
    private static final String BOOKING_BUTTON = ".bookNowBtn, .largeButton, .priceCol a.btn";
    private static final String DISCOUNT_BANNER = ".discountCodeNote";
    private static final String TOP_CUSTOMER_CARE_PHONE_NUMBER = ".customerCarePhoneNumber, div.callAndBook span";
    private static final String TOP_CUSTOMER_CARE_REFERENCE_NUMBER = ".customerCareRefNumber";
    private static final String TOP_CUSTOMER_CARE_LIVE_CHAT = ".customerCareChatLayer a";
    private static final String BACK_TO_RESULTS = "div.backResultsLink a";
    private static final String LPG_LINK_CLASS = "lowPriceGuarantee";
    private static final String TOOLTIP_LAYER_CLASS = "ui-tooltip-content";
    private static final String CANCELLATION_POLICY_PANEL_OPENER_LINK =
        "See the cancellation policy for the selected room type.";
    private static final String CANCELLATION_POLICY_PANEL_CLOSE_LINK_CLASS = "container-close";
    private static final String TRIP_ADVISOR_OPENER_ID = "tripAdvisor-opener";
    private static final String TRIP_ADVISOR_LAYER_ID = "tripAdvisor-layer";
    private static final String FEATURES_LIST_CSS = "#facilitiesModule ul";
    private static final String LOCATION_DESC_XPATH = ".//*[@id='facilitiesModule']//p[1]";
    private static final String HOTEL_SECRET_TYPE_CSS = ".secretHotel";
    private static final String FARE_FINDER_EXPANDED = ".finderExpanded .finderWrapper";
    private static final String FARE_FINDER_EXPANDER = ".finderCollapsed";
    private static final String FARE_FINDER_COLLAPSER = ".finderExpanded";
    private static final String DATES_OF_STAY = ".hotelSearchDetails .heading, .fareFinderOnDetails .searchDetails";

    @FindBy(css = FARE_FINDER_EXPANDER)
    private WebElement fareFinderExpander;

    @FindBy(css = FARE_FINDER_COLLAPSER)
    private WebElement fareFinderCollapser;

    @FindBy(css = FARE_FINDER_EXPANDED)
    private WebElement fareFinderExpanded;

    @FindBy(linkText = CANCELLATION_POLICY_PANEL_OPENER_LINK)
    private WebElement lnkOpenCancellationPolicy;

    @FindBy(id = TRIP_ADVISOR_OPENER_ID)
    private WebElement lnkOpenWhatTripAdvisor;

    @FindBy(xpath = "//div[contains(@class,'topHotelDetails')]/div[contains(@class,'primaryContentModule')]")
    private WebElement primaryContentModule;

    @FindBy(css = "div.hotelPriceModule strong")
    private WebElement price;

    @FindBy(css = ".hotelPriceModule.details small, .hotelPriceModule.blue>div>small")
    private WebElement priceLabel;

    @FindBy(css = "div.ratingLockup strong")
    private WebElement rating;

    @FindBy(css = "div.opaqueDetailsCrossSell, .crossSellDeals, .hotelCrossSell, .crossSellModuleContainer")
    private WebElement crossSellModule;

    @FindBy(css = "div.hotelName")
    private WebElement hotelName;

    @FindBy(css = TOP_CUSTOMER_CARE_PHONE_NUMBER)
    private WebElement customerCarePhoneNumber;

    @FindBy(css = TOP_CUSTOMER_CARE_REFERENCE_NUMBER)
    private WebElement customerCareRefNumber;

    @FindBy(css = TOP_CUSTOMER_CARE_LIVE_CHAT)
    private WebElement customerCareLiveChatLink;

    @FindBy(css = "div.facilitiesModule h6, div.detailsAmenityIcons h6")
    private WebElement facilitiesBlock;

    @FindBy(css = AMENITIES_LIST)
    private List<WebElement> amenities;

    @FindBy(css = FREE_AMENITIES_LIST)
    private List<WebElement> freeAmenities;

    @FindBy(css = LAST_BOOKED_HOTEL_MESSAGE)
    private WebElement lastBookedHotelMessage;

    @FindBy(css = RESORT_FEE_MODULE)
    private WebElement resortFeeModule;

    @FindBy(css = DISCOUNT_BANNER)
    private WebElement discountBanner;

    @FindBy(css = BOOKING_BUTTON)
    private WebElement bookingButton;

    @FindBy(css = BACK_TO_RESULTS)
    private WebElement backToResults;

    @FindBy(css = HOTEL_SECRET_TYPE_CSS)
    private List<WebElement> hotelSecretType;

    @FindBy(xpath = ".//div[@class='contentModule topHotelDetails retail']//a[contains(text(), 'Change dates')]")
    private WebElement ff1;

    @FindBy(xpath = ".//div[@class='retailRoomMatrix']//a[contains(text(), 'Change dates')]")
    private WebElement ff2;

    @FindBy(xpath = ".//div[@class='additionalInformation retail contentModule']//a[contains(text(), 'Change dates')]")
    private WebElement ff3;

    @FindBy(className = LPG_LINK_CLASS)
    private WebElement lpgLink;

    public HotelDetailsPage(WebDriver webDriver) {
        super(webDriver, ArrayUtils.addAll(TILE_HOTEL_DETAILS_OVERVIEW,
                // This is the tiles def for the old US details page that Hotel Supply
                // needs. They are so behind on everything...
                new String[]{
                    "tiles-def.hotel.details.*",
                    "tiles-def.hotel.details-new-car-add-on-top",
                    "tile.hotel.details.*"
                }));
    }

    public HotelDetailsPage(WebDriver webdriver, String expectedPageName) {
        super(webdriver, new String[]{expectedPageName});
    }

    public HotelDetailsPage(WebDriver webdriver, String[] expectedPageNames) {
        super(webdriver, expectedPageNames);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(DATES_OF_STAY);
    }

    @Override
    protected Integer waitForAjaxIsDoneInSec() {
        return 30;
    }

    private void expandMap() {
        if (!getWebDriver().findElement(By.cssSelector("div#expandableMapModule a.collapseLink")).isDisplayed()) {
            getWebDriver().findElement(By.cssSelector("div#expandableMapModule a.expandLink")).click();
            new WebDriverWait(getWebDriver(), 20)
                .until(visibilityOfElementLocated(By.cssSelector("div#expandableMapModule a.collapseLink")));
        }
    }

    public void select() {
        jsClick(bookingButton);
    }

    public void clickOnBookButton() {
        bookingButton.click();
    }

    public void selectCrossSell(Integer number) {
        // If the element is hotelCrossSell then use 0 because this is not a list but a single element on the
        // Opaque details page.
        try {
            getWebDriver()
                .findElements(By.cssSelector("div.opaqueDetailsCrossSell a, .crossSellDeal a, .hotelCrossSell a"))
                .get(crossSellModule.getAttribute("class").contains("hotelCrossSell") ? 0 : number).click();
        }
        catch (IndexOutOfBoundsException e) {
            getWebDriver()
                .findElements(By.xpath("//a[contains(text(), 'See More')]"))
                .get(number).click();
        }
    }

    public WebElement getCrossSellModule() {
        return crossSellModule;
    }

    public String getPrice() {
        return price.getText();
    }

    public String getHotelName() {
        return hotelName.getText();
    }

    public boolean isPriceAllInclusive() {
        return priceLabel.getText().length() > 0;
    }

    public String getCustomerRating() {
        return rating.getText();
    }

    public String getHotelSecretType() {
        return hotelSecretType.get(0).getText();
    }

    public Collection<String> getMandatoryFees() {
        Collection<String> mandatoryFees = new ArrayList<String>();

        for (WebElement element : getWebDriver().findElements(By.cssSelector("div.mandatoryFeeModule li"))) {
            mandatoryFees.add(element.getText());
        }

        return mandatoryFees;
    }

    public boolean isHotelCrossSellDisplayed() {
        try {
            return crossSellModule.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getCustomerCarePhoneNumber() {
        return customerCarePhoneNumber.getText();
    }

    public String getTopCustomerCareReferenceNumber() {
        return customerCareRefNumber.getText();
    }

    public void openLiveChat() {
        new WebDriverWait(getWebDriver(), 20).until(new IsElementLocationStable(getWebDriver(), By
            .cssSelector(TOP_CUSTOMER_CARE_LIVE_CHAT)));
        customerCareLiveChatLink.click();
    }

    public String getFacilitiesModuleText() {
        return facilitiesBlock.getText();
    }

    public List<String> getAmenitiesList() {
        ArrayList<String> list = new ArrayList<String>();
        for (WebElement amenity : amenities) {
            list.add(amenity.getText().replaceAll(",", "").trim());
        }
        return list;
    }

    public List<String> getFreeAmenitiesList() {
        ArrayList<String> list = new ArrayList<String>();
        for (WebElement amenity : freeAmenities) {
            list.add(amenity.getText().replaceAll(",", "").trim());
        }
        return list;
    }

    public boolean isOpaqueHotelDetailsPageDisplayed() {
        return new PageName().apply(getWebDriver()).contains("opaque");
    }

    public void clickHotelCrossSell(Integer dealIndex) {
        selectCrossSell(dealIndex);
    }

    public boolean isLastBookedHotelMessageDisplayed() {
        try {
            return lastBookedHotelMessage.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getLastBookedHotelMessage() {
        return lastBookedHotelMessage.getText();
    }

    public boolean isResortFeesDisplayed() {
        try {
            return resortFeeModule.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getResortFeeText() {
        return resortFeeModule.getText();
    }

    public boolean isDiscountBannerDisplayed() {
        try {
            return discountBanner.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<SearchSolution> getCrossSellsList() {
        List<SearchSolution> crossSellList = new ArrayList<SearchSolution>();
        int number = 0;

        for (WebElement result : getWebDriver().findElements(
            By.cssSelector("div.opaqueDetailsCrossSell a, .crossSellDeal a, .hotelCrossSell a, .priceHoodContent"))) {
            SearchSolution searchSolution = new SearchSolution();
            searchSolution.setNumber(number++);
            String dealData = result.getText();
            searchSolution.setDealData(dealData);
            boolean isSingleCrossSell = result.findElements(By.cssSelector(".stackedLayout")).size() > 0;
            if (!isSingleCrossSell) {
                try {
                    searchSolution.setPrice(result.findElement(By.cssSelector(".price")).getText().trim());
                    searchSolution.setHotelName(
                        result.findElement(By.cssSelector(".neighborhoodName")).getText().trim());
                }
                catch (WebDriverException e) {
                    searchSolution.setPrice(
                        result.findElements(By.cssSelector("span ul li")).get(3).getText().split("\\D+")[1].trim());
                    searchSolution.setHotelName(
                        result.findElements(By.cssSelector("span ul li")).get(1).getText().trim());
                }


            }
            else {
                // XSD13=1 cross sell.
                WebElement singleOpaqueXsell = result.findElement(By.cssSelector(".stackedLayout"));
                searchSolution.setPrice(
                    singleOpaqueXsell.findElements(By.cssSelector(".heading strong")).get(1).getText().trim());
                searchSolution.setHotelName("stackedLayout");
            }
            crossSellList.add(searchSolution);
        }
        return crossSellList;
    }

    public void addWindow() {
        ((JavascriptExecutor) getWebDriver()).executeScript("window.open();");
    }

    public void clickBackToResults() {
        backToResults.click();
    }

    public boolean isAllFarefindersPresent() {
        return ff1.isDisplayed() && ff2.isDisplayed() && ff3.isDisplayed() ? true : false;
    }

    public boolean isLPGLayerPresent() {
        lpgLink.click();
        return new WebDriverWait(getWebDriver(), 3)
            .until(ExpectedConditions.presenceOfElementLocated(By.className(TOOLTIP_LAYER_CLASS))).isDisplayed() ?
            true : false;
    }

    public boolean isTripAdvisorLayerPresent() {
        lnkOpenWhatTripAdvisor.click();
        return new WebDriverWait(getWebDriver(), 3)
            .until(ExpectedConditions
                .presenceOfElementLocated(By.id(TRIP_ADVISOR_LAYER_ID))).isDisplayed() ? true : false;
    }

    public boolean isCancellationPolicyPanelPresent() {
        lnkOpenCancellationPolicy.click();
        return new WebDriverWait(getWebDriver(), 3)
            .until(ExpectedConditions
                .presenceOfElementLocated(By.className(CANCELLATION_POLICY_PANEL_CLOSE_LINK_CLASS)))
            .isDisplayed() ? true : false;
    }

    public boolean isFeaturesListPresent() {
        return new WebDriverWait(getWebDriver(), 3)
            .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(FEATURES_LIST_CSS)))
            .getText().length() > 25 ? true : false;
    }

    public boolean isLocationDescTextPresent() {
        lnkOpenCancellationPolicy.click();
        return new WebDriverWait(getWebDriver(), 3)
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(LOCATION_DESC_XPATH)))
            .getText().length() > 25 ? true : false;
    }

    public boolean isHotelNeighborhoodDisplayed() {
        expandMap();
        return getWebDriver().findElement(By.cssSelector("polygon")).isDisplayed();
    }

    public void putMouseOverPOI() {
        expandMap();
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getWebDriver();
        javascriptExecutor.executeScript("$('div.poiIcon').mouseover()");
    }

    public boolean isMapPopupDisplayed() {
        return getWebDriver().findElement(By.cssSelector("div.VE_Pushpin_Popup_Body")).isDisplayed();
    }

    public String getNoRoomPhotosMessageText() {
        return getWebDriver().findElement(By.cssSelector("div.noPhotosModule div.info")).getText();
    }

    public List<WebElement> getBackToResultsLinks() {
        return getWebDriver().findElements(By.cssSelector("div.backResultsLink a"));
    }

    public void returnToSearchResults(String position) {
        List<WebElement> elements = this.getBackToResultsLinks();
        if ("top".equals(position)) {
            elements.get(0).click();
        }
        else {
            elements.get(1).click();
        }
    }

    public String getLowPriceGuaranteeOffer() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getWebDriver();
        javascriptExecutor.executeScript("$('span.lpgLink').mouseover();");
        return getWebDriver().findElement(By.cssSelector("div.ui-tooltip-content")).getText();
    }

    public String getKnowBeforeYouGoNotice() {
        return getWebDriver().findElement(By.cssSelector("div.knowBeforeYouGo")).getText();
    }

    public boolean isLiveChatDisplayed() {
        try {
            return customerCareLiveChatLink.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isFareFinderExpanded() {
        try {
            return new DetailsHotelSearchFragment(getWebDriver()).isExpanded();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void expandFareFinder() {
        fareFinderExpander.findElement(By.cssSelector("a")).click();
    }

    public void collapseFareFinder() {
        fareFinderCollapser.findElement(By.cssSelector("a")).click();
    }

    public String getDatesOfStayString() {
        return getWebDriver().findElement(By.cssSelector(DATES_OF_STAY)).getText();
    }

    public void waitForUpperCrossSellNeighborhoodNamesVisibility() {
        if (getWebDriver().findElements(By.cssSelector(".crossSellDeals")).size() > 0) {
            for (int i = 0;
                 i < getWebDriver().findElements(By.cssSelector(".crossSellDeal .neighborhoodName")).size();
                 i++) {
                new WebDriverWait(getWebDriver(), 5)
                    .until(new VisibilityOf(By.cssSelector(".crossSellDeal .neighborhoodName")));
            }
        }
    }

    public HotelSearchFragment findHotelFare() {
        return new DetailsHotelSearchFragment(getWebDriver());
    }
}
