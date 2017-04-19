/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * Created by IntelliJ IDEA.
 * User: vjong
 * Date: Aug 3, 2012
 * Time: 8:49:01 AM
 */
public class HotelResultsSearchResultsFragment extends AbstractPageObject {

    protected static final int DEFAULT_WAIT = 30;
    protected static final String HOTEL_RESULTS_LIST_CLASS = "ul.results";

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelResultsSearchResultsFragment.class);
    private static final String SEE_ALL_RESULTS_LINK = "a.seeAllHotels";
    private static final String COMPRESSION_MESSAGE = ".compressionMsg";
    private static final String CROSS_SELL_CONTAINER = ".hotelCrossSell a, .crosssell a";
    private static final String SEARCH_RESULTS_PRICE = " .resultCTA .price, .priceLockup .price";
    private static final String HOTEL_RESULTS_BODY = HOTEL_RESULTS_LIST_CLASS + " li";
    private static final String HOTEL_RESULTS_HREF_LIST =
        "li .neighborhoodName a.hotelNameLink, li .singleResult a.detailsLink";
    private static final String HOTEL_AMENITIES_CONTENT = "resultAmenitiesContent";
    private static final String STAR_RATINGS = ".//span[contains(@class, 'starsAmount')]";
    private static final String RESULTS_AREAS_LINKS = ".resultBody .neighborhoodName span[id='hotelOrHoodName']";
    private static final String SECOND_RETAIL_EXAMPLE_ID = "retailExamplesOpaque_1";
    private static final String RESULT_ITEMS_DISTANCE = ".distanceContainer .distance .miles";
    private static final String RETAIL_NAME = ".resultBody .neighborhoodName span[id='hotelOrHoodName']";
    private static final String TRIPWATCHER_SPEEDBUMP = ".tripWatcherSpeedBump";
    private static final String RETAIL_HERO_RESULT = HOTEL_RESULTS_LIST_CLASS + " li[id='retailGateway']";
    private static final String TRIP_ADVISOR_RATING = ".recommendationContainer .tripAdvisorRatingModule .rating";

    @FindBy(css = SEE_ALL_RESULTS_LINK)
    private WebElement seeAllResultsLink;

    @FindBy(css = CROSS_SELL_CONTAINER)
    private WebElement crossSellContainer;

    @FindBy(css = HOTEL_RESULTS_LIST_CLASS)
    private WebElement resultsGroup;

    @FindBy(css = HOTEL_RESULTS_HREF_LIST)
    private List<WebElement> resultsHrefList;

    @FindBy(className = HOTEL_AMENITIES_CONTENT)
    private List<WebElement> amenitiesContent;

    @FindBy(xpath = STAR_RATINGS)
    private List<WebElement> searchResultsStarRatings;

    @FindBy(css = RESULTS_AREAS_LINKS)
    private List<WebElement> areaLinks;

    @FindBy(id = SECOND_RETAIL_EXAMPLE_ID)
    private WebElement retailExample;

    @FindBy(css = HOTEL_RESULTS_BODY)
    private List<WebElement> resultBodies;

    @FindBy(css = RESULT_ITEMS_DISTANCE)
    private List<WebElement> resultItemsDistanceElements;

    @FindBy(css = RETAIL_NAME)
    private List<WebElement> retailNeighborhoodNames;

    @FindBy(css = TRIPWATCHER_SPEEDBUMP)
    private WebElement tripwatcherSpeedbump;

    @FindBy(css = RETAIL_HERO_RESULT)
    private WebElement retailHero;

    private HashMap<String, String> amenitiesCodesMap;
    private HashMap<String, String> accessibilityCodesMap;

    public HotelResultsSearchResultsFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector(HOTEL_RESULTS_LIST_CLASS));
        initMaps();
    }

    public boolean isCrossSellDisplayedAndEnabled() {
        try {
            return crossSellContainer.isDisplayed() && crossSellContainer.isEnabled();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickCrossSellLink() {
        crossSellContainer.click();
    }

    /**
     * Get href's of search results.
     */
    public List<String> getSearchResultsHrefList() {
        ArrayList<String> itemHrefs = new ArrayList<String>();
        for (WebElement resultHref : getResultsHrefsElements()) {
            String href = resultHref.getAttribute("href").toString().trim();
            itemHrefs.add(href);
        }
        return itemHrefs;
    }

    public ArrayList<Double> getPricesFromSearchResults() {
        ArrayList<Double> prices = new ArrayList<Double>();
        By by = By.cssSelector(SEARCH_RESULTS_PRICE);
        verifyResultsSizeAndLocationStability(by);
        for (WebElement price : getWebDriver().findElements(by)) {
            Double num = new Double(price.getText().replaceAll("[^0-9.]", ""));
            prices.add(num);
        }
        return prices;
    }


    protected void verifyResultsSizeAndLocationStability(By by) {
        if (getWebDriver().findElements(by).size() == 0) {
            throw new ZeroResultsTestException("0 results returned after filtering.");
        }
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(new IsElementLocationStable(getWebDriver(), by));
    }

    public void clickSeeAllResultsLink() {
        seeAllResultsLink.click();
    }

    public boolean isSeeAllResultsLinkAvailable() {
        try {
            // Checking web element for visibility and being enabled should also implicitely check that the element
            // exists.
            return seeAllResultsLink.isDisplayed() && seeAllResultsLink.isEnabled();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean hasCompressionMessage() {
        return getWebDriver().findElements(By.cssSelector(COMPRESSION_MESSAGE)).size() > 0;
    }

    public String getCompressionMessage() {
        // Just get the first compression message.
        return getWebDriver().findElement(By.cssSelector(COMPRESSION_MESSAGE)).getText();
    }

    public boolean isRetailExampleLayerVisible() {
        return getRetailExampleLayer().isDisplayed();
    }

    public void clickRetailExample() {
        getRetailExample().click();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(getRetailExampleLayer(), true));
    }

    public List<WebElement> getResultsHrefsElements() {
        return resultsHrefList;
    }

    public List<String> getAccessibilitySearchResultsHrefList() {
        ArrayList<String> itemHrefs = new ArrayList<String>();
        if (resultBodies.size() == 0) {
            throw new ZeroResultsTestException("0 results returned from search.");
        }
        List<WebElement> resultsItems = getWebDriver().findElement(
            By.cssSelector(HOTEL_RESULTS_LIST_CLASS)).findElements(By.cssSelector("li .singleResult"));
        for (WebElement result : resultsItems) {
            WebElement hrefElement = result.findElement(By.cssSelector("a.hotelNameLink"));
            String href = hrefElement.getAttribute("href");
            String resultName = result.findElement(
                By.cssSelector(".neighborhoodName span[id='hotelOrHoodName']")).getText().trim();
            try {
                String elements = result.findElement(By.cssSelector(".resultAmenities")).getAttribute("data-amenities");
                ArrayList<String> labels = new ArrayList<String>();
                LOGGER.info("AMENITIES CODE STRING: " + elements);
                for (String item : elements.split("\\|")) {
                    if (accessibilityCodesMap.containsKey(item)) {
                        String text = accessibilityCodesMap.get(item);
                        labels.add(text);
                    }
                }
                if (labels.size() == 0) {
                    LOGGER.info(resultName + " has no accessibility amenities.");
                }
                else {
                    itemHrefs.add(href);
                    String amenityLabels = StringUtils.join(labels.toArray(), ", ");
                    LOGGER.info(resultName + " accessibility amenities: " + amenityLabels);
                }
            }
            catch (NoSuchElementException e) {
                LOGGER.info(resultName + " has no amenities listed.");
            }
        }
        return itemHrefs;
    }

    public ArrayList<Double> getDistancesFromSearchResults() {
        ArrayList<Double> distanceValues = new ArrayList<Double>();
        By by = By.cssSelector(RESULT_ITEMS_DISTANCE);
        verifyResultsSizeAndLocationStability(by);
        for (WebElement resultItem : resultItemsDistanceElements) {
            // Get the first number from the distance text.
            String resultText = resultItem.getText().trim();
            distanceValues.add(new Double(resultText.replaceAll(" - \\d+.\\d.*", "").replaceAll("[^\\d.]", "")));
        }
        return distanceValues;
    }

    public List<String> getDistanceTextsFromResults() {
        ArrayList<String> distanceTexts = new ArrayList<String>();
        for (WebElement element : resultItemsDistanceElements) {
            distanceTexts.add(element.getText().trim());
        }
        return distanceTexts;
    }

    public List<String> getSearchResultsAmenitiesContent() {
        List<WebElement> resultBodiesList = getWebDriver().findElements(
            By.cssSelector(HOTEL_RESULTS_BODY + " .singleResult"));
        ArrayList<String> items = new ArrayList<String>();
        if (resultBodiesList.size() == 0) {
            throw new ZeroResultsTestException("0 results returned from search.");
        }
        for (WebElement result : resultBodiesList) {
            String resultName = result.findElement(
                By.cssSelector(".resultBody .neighborhoodName")).getText().trim();
            try {
                String elements = result.findElement(By.cssSelector(".resultAmenities")).getAttribute("data-amenities");
                List<String> label = new ArrayList<String>();
                for (String item : elements.split("\\|")) {
                    String text = amenitiesCodesMap.get(item);
                    label.add(text);
                }
                String amenityLabels = StringUtils.join(label.toArray(), ", ");
                items.add(amenityLabels);
                LOGGER.info(resultName + " amenities: " + amenityLabels);
            }
            catch (NoSuchElementException e) {
                LOGGER.info(resultName + " has no amenities listed.");
            }
        }
        return items;
    }

    public ArrayList<String> getStarRatingsTexts() {
        ArrayList<String> texts = new ArrayList<String>();
        for (WebElement rating : searchResultsStarRatings) {
            String text = rating.getAttribute("title").trim();
            if (!text.equals("")) {
                texts.add(text);
            }
        }
        return texts;
    }

    public ArrayList<String> getNeighborhoodsFromOpaqueSearchResults() {
        ArrayList<String> linkTexts = new ArrayList<String>();
        for (WebElement link : areaLinks) {
            linkTexts.add(link.getText());
        }
        return linkTexts;
    }

    public void clickFirstResultInSearch() {
        if (new HotelResultsPage(getWebDriver()).opaqueResultsAreDisplayed()) {
            getResultsHrefsElements().get(0).click();
        }
        else {
            // on retail page. Navigate with href attribute.
            getWebDriver().navigate().to(getResultsHrefsElements().get(0).getAttribute("href"));
        }
    }

    public void selectOpaqueResultWithRoomType(String roomType) {
        new HotelResultsPage(getWebDriver()).chooseOpaque();
        if (resultBodies.size() == 0) {
            throw new ZeroResultsTestException("0 results returned from search.");
        }
        int i = 0;
        for (WebElement result : resultBodies) {
            WebElement hrefElement = result.findElement(By.className("neighborhoodName"));
            String resultName = hrefElement.getText();
            if (resultName.contains(roomType)) {
                result.findElement(By.className("hotelNameLink")).click();
                break;
            }
            i++;
        }
    }

    public WebElement getRetailExample() {
        return retailExample;
    }

    public WebElement getRetailExampleLayer() {
        return getWebDriver().findElement(By.id("retailExamplesOpaqueToolTip"));
    }

    public List<WebElement> getResultsWithTripAdvisorRatings() {
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT).until(new IsElementLocationStable(getWebDriver(), By
            .cssSelector(TRIP_ADVISOR_RATING)));
        return getWebDriver().findElements(By.cssSelector(TRIP_ADVISOR_RATING));
    }

    public List<WebElement> getRetailNeighborhoodNameElements() {
        return retailNeighborhoodNames;
    }

    @SuppressWarnings("unused")
    public boolean clickSolutionByNeighborhoodStarRatingAndCrs(String neighborhood, String starRating, String crs) {
        if (getWebDriver().getCurrentUrl().contains("preprod")) {
            crs = "null";
        }
        List<WebElement> elements = getWebDriver().findElements(By.cssSelector("li div.singleResult"));
        for (WebElement element : elements) {
            WebElement href = element.findElement(By.cssSelector("a.hotelNameLink"));
            WebElement refNoCrsElement = element.findElement(By.cssSelector("a.detailsLink"));
            String refNoText = refNoCrsElement.getAttribute("data-refnumber").trim();
            String crsText = refNoCrsElement.getAttribute("data-debuginfo").trim();
            String neighborhoodText = element.findElement(
                By.cssSelector(".neighborhoodName span[id='hotelOrHoodName']")).getText().trim();
            String starRatingText = element.findElement(By.cssSelector(".starsAmount")).getAttribute("title")
                .split(" ")[0];
            LOGGER.info("NEIGHBORHOOD: " + neighborhoodText + "  - CRS: " + crsText + " - STAR RATING: " +
                starRatingText);
            if (neighborhoodText.equalsIgnoreCase(neighborhood) && starRatingText.equals(starRating) &&
                crsText.trim().equalsIgnoreCase(crs)) {
                LOGGER.info("Solution found: " + neighborhoodText + " - " + starRatingText + " - " + crsText);
                refNoCrsElement.click();
                return true;
            }
        }
        return false;
    }

    public boolean selectResultByName(String neighborhoodName) {
        List<WebElement> resultsItems = getWebDriver().findElement(
            By.cssSelector(HOTEL_RESULTS_LIST_CLASS)).findElements(By.cssSelector("li .singleResult"));
        for (WebElement result : resultsItems) {
            WebElement hrefElement = result.findElement(By.cssSelector("a.hotelNameLink"));
            String href = hrefElement.getAttribute("href");
            String resultName = result.findElement(
                By.cssSelector(".neighborhoodName")).getText().trim();
            resultName = resultName.split("\n")[0];
            if (resultName.equalsIgnoreCase(neighborhoodName)) {
                getWebDriver().navigate().to(href);
                return true;
            }
        }
        return false;
    }

    public boolean selectResultWhoseNameContains(String subString) {
        List<WebElement> resultsItems = getWebDriver().findElement(
            By.cssSelector(HOTEL_RESULTS_LIST_CLASS)).findElements(By.cssSelector("li .singleResult"));
        for (WebElement result : resultsItems) {
            WebElement hrefElement = result.findElement(By.cssSelector("a.hotelNameLink"));
            String href = hrefElement.getAttribute("href");
            String resultName = result.findElement(
                By.cssSelector(".neighborhoodName")).getText().trim();

            if (resultName.toLowerCase().contains(subString.toLowerCase())) {
                getWebDriver().navigate().to(href);
                return true;
            }
        }
        return false;
    }

    public boolean isTripWatcherSpeedBumpDisplayed() {
        try {
            return tripwatcherSpeedbump.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickTripWatcherSpeedBump() {
        tripwatcherSpeedbump.findElement(By.tagName("a")).click();
    }

    public boolean isRetailHeroResultDisplayed() {
        try {
            return retailHero.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getRetailHeroResultName() {
        return retailHero.findElement(
            By.cssSelector(".singleResult .resultsBody .resultBody .neighborhoodName")).getText();
    }

    public void clickRetailHeroResult() {
        retailHero.findElement(By.cssSelector("a[id='pictureLink_retail']")).click();
    }

    @SuppressWarnings("serial")
    protected void initMaps() {
        amenitiesCodesMap = new HashMap<String, String>() {
            {
                put("BF", "Beachfront Property");
                put("BA", "Near Beach");
                put("OF", "Oceanfront Property");
                put("PO", "Pool(s)");
                put("KI", "Kitchenette");
                put("RE", "Restaurant(s)");
                put("FC", "Fitness Center");
                put("LF", "Laundry Facilities (self-service)");
                put("CB", "Free Breakfast");
                put("SF", "Spa Services");
                put("GN", "Golf Nearby");
                put("TN", "Tennis Nearby");
                put("BC", "Business Center");
                put("CO", "Condo");
                put("SU", "Suite");
                put("CA", "Casino");
                put("BH", "Boutique Hotel");
                put("RS", "Resort");
                put("AS", "Airport Shuttle");
                put("HS", "High-Speed Internet Access");
                put("CH", "Children's Activity Program");
                put("SL", "Slopeside");
                put("FI", "Food and Beverages Included");
                put("BI", "Premium Brand Beverages Included");
                put("DO", "A la Carte Dining Options");
                put("AT", "Airport Transfers Included");
                put("WO", "Water Sports/Ocean Activities Included");
                put("EA", "Evening Entertainment Included");
                put("DA", "Daily Activities Included");
                put("ST", "Studio");
                put("1B", "1 Bedroom Suite");
                put("2B", "2 Bedroom Suite");
                put("3B", "3 Bedroom Suite");
                put("FK", "Fully-equipped Kitchen");
                put("DH", "Daily Housekeeping");
                put("AC", "Air-Conditioning");
                put("WD", "Washer and Dryer in Room");
                put("FP", "Free Parking");
                put("FD", "Front Desk");
                put("HF", "24-hour Front Desk");
                put("CI", "Free Internet");
                put("IP", "Indoor pool(s)");
                put("SB", "Shared Bath");
                put("AP", "American Plan");
                put("MP", "Modified American Plan");
                put("SR", "Smoke Free Rooms");
                put("AI", "All-inclusive");
                put("SP", "Self Parking");
                put("VP", "Valet Parking");
                put("AB", "Accessible for the blind");
                put("AD", "Accessible for the deaf");
                put("AL", "Accessible path of travel");
                put("AR", "In-room accessibility");
                put("AV", "Audio-visual equipment");
                put("AW", "Wheelchair accessible");
                put("BD", "Billiards");
                put("BL", "Bar/lounge");
                put("BN", "Banquet Facilities");
                put("BQ", "Barbecue grill");
                put("BM", "Ballroom");
                put("BR", "Breakfast (surcharge)");
                put("CT", "Catering");
                put("CC", "Child care");
                put("CF", "Conference facilities");
                put("CS", "Coffee shop/cafe");
                put("CM", "ATM/Banking");
                put("CN", "Concierge");
                put("DR", "Dry cleaning");
                put("EL", "Elevator/lift");
                put("EX", "Express check-out");
                put("FX", "Currency exchange");
                put("GR", "Game room");
                put("HB", "Accessible bathroom");
                put("HP", "Handicapped parking");
                put("MF", "Meeting facilities");
                put("ML", "Multingual staff");
                put("OS", "Area shuttle");
                put("PE", "Pet friendly");
                put("RI", "Roll-in shower");
                put("RV", "Room service");
                put("SA", "Spa tub");
            }
        };

        accessibilityCodesMap = new HashMap<String, String>() {
            {
                put("AB", "Accessible for the blind");
                put("AD", "Accessible for the deaf");
                put("AL", "Accessible path of travel");
                put("AR", "In-room accessibility");
                put("AW", "Wheelchair accessible");
                put("HB", "Accessible bathroom");
                put("HP", "Handicapped parking");
                put("RI", "Roll-in shower");

            }
        };
    }
}
