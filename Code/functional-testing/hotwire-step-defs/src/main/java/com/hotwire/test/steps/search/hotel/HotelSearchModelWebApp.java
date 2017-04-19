/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hotwire.selenium.desktop.us.partners.HotelExpediaPage;
import com.hotwire.selenium.desktop.us.partners.HotelHotelsPage;
import com.hotwire.selenium.desktop.us.partners.HotelKayakPage;
import com.hotwire.selenium.desktop.us.partners.HotelPartnerPageDetector;
import com.hotwire.selenium.desktop.us.partners.HotelPartnersPage;
import com.hotwire.selenium.desktop.us.partners.HotelTrivagoPage;
import com.hotwire.selenium.desktop.us.results.D2CFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.d2c.D2CHotelFragment;
import com.hotwire.util.db.hotel.HotelsDao;

import org.ehoffman.testing.fest.webdriver.WebElementAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.confirm.HotelConfirmationPage;
import com.hotwire.selenium.desktop.us.results.ActivitiesResultsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsSearchResultsFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.farefinder.HotelResultsFareFinderFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.filters.HotelResultsAmenitiesFilteringTabPanelFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.tripwatcher.HotelResultsTripwatcherLayerFragment;
import com.hotwire.selenium.desktop.us.search.HotelMFILayer;
import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import com.hotwire.selenium.desktop.widget.ErrorMessenger;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.test.steps.util.RefreshPageHandler;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.WebDriverManager;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.ExtendedSelect;

import cucumber.api.PendingException;

/**
 * WebApp implementation of HotelSearchModel.
 */
public class HotelSearchModelWebApp extends HotelSearchModelTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelSearchModelWebApp.class.getName());
    private static final String RETAIL_EXAMPLE_ENABLED_FOR_OPAQUE = "vt.RHE12=";
    private static final String ACTIVITIES_SERVICES_ENABLED = "vt.DSS01=3";
    private URL applicationUrl;

    @Autowired
    private PurchaseParameters purchaseParameters;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setApplicationUrl(URL applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    @Override
    public void verifyNoErrorsOnPage() {
        HotelResultsPage hotelResultsPage = new HotelResultsPage(getWebdriverInstance());
        assertThat(hotelResultsPage.isErrorMessageDisplayed()).as("List must be displayed without errors").isFalse();
    }

    @Override
    public void verifyResultsContainLocation(String location) {
        HotelResultsFareFinderFragment hotelSearchFragment = new HotelResultsFareFinderFragment(getWebdriverInstance());
        try {
            hotelSearchFragment.getEditSearch().click();
        }
        catch (NoSuchElementException e) {
            LOGGER.info("Edit search link is absent on the page ");
        }
        assertThat(hotelSearchFragment.getDestCity().getAttribute("value"))
                .as("Location on results page as expected")
                .contains(location);
    }

    @Override
    public void verifyDatesValue(String webElement, Date dateValue) {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        String actualValue = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");
        String expectedValue = dateFormat.format(dateValue);

        if (webElement.equals("check-in")) {
            actualValue = hotelSearchFragment.getStartDateField().getAttribute("value");
        }
        else if (webElement.equals("check-out")) {
            actualValue = hotelSearchFragment.getEndDateField().getAttribute("value");
        }

        assertThat(expectedValue).as("Expected date " + expectedValue + " actual " + actualValue).contains(actualValue);
    }

    private HotelResultsPage clickSeeAllResultsLinkInternal() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        if (resultsPage.getSearchResultsFragment().isSeeAllResultsLinkAvailable()) {
            LOGGER.info("See all results link exists. Clicking link.");
            HotelResultsSearchResultsFragment searchFragment = resultsPage.getSearchResultsFragment();
            searchFragment.clickSeeAllResultsLink();
            resultsPage = new HotelResultsPage(getWebdriverInstance());
        }
        return resultsPage;
    }

    @Override
    public void verifyHotelFeatureInResults(String feature) {
        HotelResultsSearchResultsFragment resultsFragment = new HotelResultsPage(getWebdriverInstance())
            .getSearchResultsFragment();
        for (String featuresText : resultsFragment.getSearchResultsAmenitiesContent()) {
            assertThat(featuresText.contains(feature)).as("Expected " + feature + " to be in " + featuresText).isTrue();
        }
    }

    @Override
    public void chooseHotelFromResults(String neighborhood, String starRating, String crs) {
        boolean resultFound = new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment()
            .clickSolutionByNeighborhoodStarRatingAndCrs(neighborhood, starRating, crs);
        assertThat(resultFound)
                .as(String.format("Expected %s , %s rating and '%s' CRS not found", neighborhood, starRating, crs))
                .isTrue();
    }

    /**
     * Turn on/off Retail Examples for Opaque (RHE12=2/RHE12=2)
     */
    @Override
    public void activateRetailExamplesForOpaque(boolean enableExamples) {
        getWebdriverInstance().navigate()
         .to(String.format("%s/?%s%s", applicationUrl, RETAIL_EXAMPLE_ENABLED_FOR_OPAQUE, enableExamples ? "2" : "1"));
    }

    @Override
    public void verifyVisibilityOfRetailExamples(boolean examplesShouldBeVisible) {
        HotelResultsSearchResultsFragment resultsFragment = new HotelResultsPage(getWebdriverInstance())
            .getSearchResultsFragment();
        if (examplesShouldBeVisible) {
            assertThat(resultsFragment.isRetailExampleLayerVisible())
                    .as("Expecting the retail examples layer to be visible")
                    .isTrue();
        }
        else {
            assertThat(resultsFragment.isRetailExampleLayerVisible())
                    .as("Expecting the retail examples layer NOT to be visible")
                    .isFalse();
        }
    }

    @Override
    public void viewRetailHotelExamples() {
        HotelResultsSearchResultsFragment resultsFragment = new HotelResultsPage(getWebdriverInstance())
            .getSearchResultsFragment();
        resultsFragment.clickRetailExample();
    }

    @Override
    public void sortByCriteria(String sortCriteria) {

        /*
         * Change the interface model to allow the passing of the ordering of the sort criteria. ie. Low to high, High
         * to low, etc. For now just select the first option the sortCriteria matches.
         */
        new ExtendedSelect(new HotelResultsPage(getWebdriverInstance()).getSortByDropdown())
            .selectIfVisibleTextStartsWithText(sortCriteria.substring(0, 1).toUpperCase());
    }

    @Override
    public void verifyMediaPlannerLayerIsShown() {
        new WebDriverWait(getWebdriverInstance(), 20)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(".HotelPlannerPopupComp"), true));
        WebElement hotelPlannerLayer = getWebdriverInstance().findElement(By.cssSelector(".HotelPlannerPopupComp"));
        assertThat(hotelPlannerLayer.isDisplayed()).as("Expecting the hotel planner layer to be visible").isTrue();
    }

    @Override
    public void changeCurrency(String currencyCode) {
        new GlobalHeader(getWebdriverInstance()).changeCurrency(currencyCode);
        purchaseParameters.setCurrencyCode(currencyCode);
    }

    @Override
    public void turnOnTripWatcherLayer() {
        getWebdriverInstance().navigate().to(applicationUrl + "/?vt.SAL12=3");
    }

    @Override
    public void showActivateServicesTab() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + ACTIVITIES_SERVICES_ENABLED);
    }

    @Override
    public void sortResultsBy(String sortBy) {
        new ExtendedSelect(new HotelResultsPage(getWebdriverInstance()).getSortByDropdown())
            .selectByVisibleText(sortBy);
    }

    @Override
    public void verifySortByResults(HotelResultsSortCriteria sortCriteria, HotelResultsSortOrderBy orderBy) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        ArrayList<Double> results = new ArrayList<>();
        boolean throwException = false;
        String exceptionString = "";
        switch (sortCriteria) {
            case PRICE:
                results = resultsPage.getSearchResultsFragment().getPricesFromSearchResults();
                break;
            case STAR:
                for (String starText : resultsPage.getSearchResultsFragment().getStarRatingsTexts()) {
                    results.add(new Double(starText.substring(0, starText.indexOf(' '))));
                }
                break;
            case RECOMMENDED:
                throwException = true;
                exceptionString = sortCriteria.getText() + " is not implemented yet.";
                break;
            case DISTANCE:
                results = resultsPage.getSearchResultsFragment().getDistancesFromSearchResults();
                break;
            case POPULAR:
                assertThat(new Select(resultsPage.getSortByDropdown()).getFirstSelectedOption().getText())
                        .as("Default sort criteria").isEqualTo("Popular");
                return;
            default:
                throwException = true;
                exceptionString = "Invalid sort criteria.";
                break;
        }
        if (throwException) {
            throw new PendingException(exceptionString);
        }

        // Got ArrayList from criteria. Verify sorting.
        switch (orderBy) {
            case LOW_TO_HIGH:
                assertResultsSortedBy(results, true /* doAscendingOrderBy */);
                break;
            case HIGH_TO_LOW:
                assertResultsSortedBy(results, false /* doAscendingOrderBy */);
                break;
            case NEAR_TO_FAR:
                // This is the same thing as low to high
                assertResultsSortedBy(results, true /* doAscendingOrderBy */);
                break;
            default:
                throw new PendingException("Invalid order by criteria.");
        }
    }

    private void assertResultsSortedBy(ArrayList<Double> results, boolean doAscendingOrderBy) {
        for (int i = 1; i < results.size(); i++) {
            Double a = results.get(i);
            Double b = results.get(i - 1);
            LOGGER.info(String.format("Compare %s%s%s", b, doAscendingOrderBy ? " <= " : " >= ", a));
            if (doAscendingOrderBy) {
                assertThat(b.compareTo(a) == 0 || b.compareTo(a) == -1)
                        .as(String.format("Expected (%s <= %s) to be true but got false instead.", a, b))
                        .isTrue();
            }
            else {
                assertThat(b.compareTo(a) == 0 || b.compareTo(a) == 1)
                        .as(String.format("Expected (%s >= %s) to be true but got false instead.", a, b))
                        .isTrue();
            }
        }
    }

    @Override
    public void clickSeeAllResultsLink() {
        clickSeeAllResultsLinkInternal();
    }

    /**
     * Append query parameter to the URL.
     */
    @Override
    public void appendQueryParameterToUrl(String queryParameter) {
        getWebdriverInstance().navigate().to(applicationUrl + "/?" + queryParameter);
    }

    @Override
    public void verifyActivitiesBanner(String destinationCity, Date startDate) {
        DateFormat date = new SimpleDateFormat("MMMM d");
        String start = date.format(startDate);
        HotelConfirmationPage hotelConfirmationPage = new HotelConfirmationPage(getWebdriverInstance());
        assertThat(hotelConfirmationPage.verifyActivitiesBanner(destinationCity, start))
                .as("Acitivities Banner doesn't have expected result")
                .isTrue();
    }

    @Override
    public void clickBanner() {
        new HotelConfirmationPage(getWebdriverInstance())
                .clickBanner();
    }

    // Media Fill-In Layer
    @Override
    public void verifyMFI(HotelSearchParameters hotelSearchParameters) {
        String expectedMessage;
        HotelMFILayer mfiLayer = new HomePage(getWebdriverInstance()).getHotelMFILayer();
        SearchParameters searchParams = hotelSearchParameters.getGlobalSearchParameters();
        String startDate, endDate;

        if (searchParams.getStartDate() != null) {
            startDate = new SimpleDateFormat("EEE, MMM d").format(searchParams.getStartDate());
        }
        else {
            startDate = null;
        }

        if (searchParams.getEndDate() != null) {
            endDate = new SimpleDateFormat("EEE, MMM d").format(searchParams.getEndDate());
        }
        else {
            endDate = null;
        }
        // Verify location and startDate, endDate
        expectedMessage = searchParams.getDestinationLocation() + " - " + startDate + " to " + endDate;
        LOGGER.info("Looking for text on MFI - " + expectedMessage);
        assertThat(mfiLayer.getParagraphs().get(0).getText().contains(expectedMessage))
                .as(String.format("destination+startDate+endDate is INCORRECT, was expected -%s but actual - %s",
                        expectedMessage, mfiLayer.getParagraphs().get(0).getText()))
                .isTrue();
        // Verify, at least 1 checkbox should be checked
        assertThat(mfiLayer.getCheckboxes().size() >= 1).as("All checkboxs are UNCHECK").isTrue();
        mfiLayer.uncheckAllPartners(); // prepare to next actions from white paper
    }

    @Override
    public void choosePartner(String partner) {
        HotelMFILayer mfiLayer = new HomePage(getWebdriverInstance()).getHotelMFILayer();
        mfiLayer.choosePartner(partner);
    }

    @Override
    public void launchMFIPartnerSearch() {
        HotelMFILayer mfiLayer = new HomePage(getWebdriverInstance()).getHotelMFILayer();
        mfiLayer.clickSearchButton();
    }

    @Override
    public void verifyActivitiesPage(String destinationCity, Date endDate, Date startDate) {
        DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        String start = date.format(startDate);
        String end = date.format(endDate);
        ActivitiesResultsPage activitiesResultsPage = new ActivitiesResultsPage(getWebdriverInstance());
        assertThat(activitiesResultsPage.activitiesInvokedViaHotelConfirmationPage(destinationCity, start, end))
                .as("vacation.hotwire.com is invoked")
                .isTrue();
    }

    @Override
    public void verifyPartnersSearch(HotelSearchParameters hotelSearchParameters, String partnerName) {
        SearchParameters searchParams = hotelSearchParameters.getGlobalSearchParameters();
        WebDriver partnerPopupWebDriver;
        HotelPartnersPage partnersPage = null;
        String rootWindowHandler = getWebdriverInstance().getWindowHandle(); // remember hotwire window

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            /**
             * Wait while all popup windows were loaded... Doesn't work without it.
             */
        }

        /**
         * Get handler of partner's popup windows and wait while inner content was appear
         */
        for (String h : getWebdriverInstance().getWindowHandles()) {
            if (!h.equals(rootWindowHandler) && !h.isEmpty()) {
                partnerPopupWebDriver = getWebdriverInstance().switchTo().window(h);

                if (HotelPartnerPageDetector.validate(partnerName) ==
                        HotelPartnerPageDetector.detect(partnerPopupWebDriver)) {

                    switch (HotelPartnerPageDetector.validate(partnerName)) {
                        case PRICELINE_COM:
                            throw new PendingException("Partner '" +
                                    partnerName + "' not supported yet. Complete me...");
                        case EXPEDIA_COM:
                            partnersPage = new HotelExpediaPage(partnerPopupWebDriver);
                            break;
                        case KAYAK_COM:
                            partnersPage = new HotelKayakPage(partnerPopupWebDriver);
                            break;
                        case HOTELS_COM:
                            partnersPage = new HotelHotelsPage(partnerPopupWebDriver);
                            break;
                        case TRIVAGO:
                            partnersPage = new HotelTrivagoPage(partnerPopupWebDriver);
                            break;
                        case ORBITZ_COM:
                            throw new PendingException("Partner '" +
                                    partnerName + "' not supported yet. Complete me...");
                        default:
                            assertThat(true).as("Partner's window wasn't identified").isFalse();

                    }
                    break;
                }
            }
        }
        assertThat(partnersPage.checkSearchOptions(searchParams.getDestinationLocation(), searchParams.getStartDate(),
                    searchParams.getEndDate()))
                .as("Search options at " + partnerName + " are not the same")
                .isTrue();

        getWebdriverInstance().switchTo().window(rootWindowHandler);
    }

    @Override
    public void verifyPartnersSearchInDB(String partnerName) {
        Map partnerSearchData = new HotelsDao(getDataBaseConnection()).getPartnerSearchData();
        String partnerNameCode;
        switch (partnerName.toUpperCase().trim()) {
            case "EXPEDIA":
                partnerNameCode = "EX";
                break;
            case "HOTELS":
                partnerNameCode = "HO";
                break;
            case "PRICELINE":
                partnerNameCode = "PC";
                break;
            case "ORBITZ":
                partnerNameCode = "OB";
                break;
            case "TRIVAGO":
                partnerNameCode = "TR";
                break;
            default:
                partnerNameCode = "";
        }

        assertThat(partnerSearchData.get("PARTNER_CODE")).isEqualTo(partnerNameCode)
                .as("PARTNER_CODE DB value should be " + partnerNameCode +
                        " but actual is " + partnerSearchData.get("PARTNER_CODE"));
        assertThat(partnerSearchData.get("CLICK_SOURCE").toString()).isEqualTo("4")
                .as("CLICK_SOURCE DB value should be '4', but actual is " + partnerSearchData.get("CLICK_SOURCE"));
        assertThat(partnerSearchData.get("PGOOD_CODE")).isEqualTo("H")
                .as("PGOOD_CODE DB value should be 'H', but actual is " + partnerSearchData.get("PGOOD_CODE"));
        assertThat(partnerSearchData.get("ORIGIN_TYPE")).isEqualTo("I")
                .as("ORIGIN_TYPE DB value should be 'I' but actual is " + partnerSearchData.get("ORIGIN_TYPE"));
        assertThat(partnerSearchData.get("SEARCH_TYPE")).isEqualTo("U")
                .as("SEARCH_TYPE DB value should be 'U' but actual is " + partnerSearchData.get("SEARCH_TYPE "));
    }

    @Override
    public void verifyFacilitiesTermUsage(String term) {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        String text = detailsPage.getFacilitiesModuleText();
        if (text.isEmpty()) {
            logger.info("No facilities for this hotel");
            throw new ZeroResultsTestException("No facilities for this hotel");
        }
        assertThat(text.toLowerCase().contains(term)).isTrue();
        if ("facilities".equalsIgnoreCase(term)) {
            assertThat(text.contains("amenities")).isFalse();
        }
        else {
            assertThat(text.contains("facilities")).isFalse();
        }
    }

    @Override
    public void verifyLocation(String expectedLocation) {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        assertThat(hotelSearchFragment.getDestCity().getAttribute("value").equals(expectedLocation))
                .as("Expected location should be :" + expectedLocation)
                .isTrue();
    }

    @Override
    public void typeLocation(String location) {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        hotelSearchFragment.getDestCity().sendKeys(location);
    }

    @Override
    public void verifySuggestedLocation(String number, String expectedAutoCompleteItem, String style, String click) {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        hotelSearchFragment.isAutoCompleteDisplayed();
        LOGGER.info("Auto-complete is visible");

        ArrayList<String> autocomleteContents = hotelSearchFragment.getAutocompleteContents();
        ArrayList<String> autocompleteAttributes = hotelSearchFragment.getAttibutesForAutocomplete("class");
        List<WebElement> autocomleteElements = hotelSearchFragment.getAutocompleteElements();

        Integer i;
        i = autocomleteContents.indexOf(expectedAutoCompleteItem);

        if (number != null) {
            if (number.trim().equals("first")) {
                assertThat(i == 0)
                        .as("First auto-complete item is : " + expectedAutoCompleteItem)
                        .isTrue();
            }
        }
        else {
            assertThat(i != -1).isTrue();
        }

        if (style != null) {
            if (style.trim().equals("underlined and highlighted")) {
                assertThat(autocompleteAttributes.get(i).contains("yui-ac-highlight"))
                        .as("N" + i.toString() + " auto-complete item is highlighted and underlined")
                        .isTrue();
            }
        }

        if (click != null) {
            if (click.trim().equals("and I click it")) {
                autocomleteElements.get(i).click();

                try {
                    hotelSearchFragment.isAutoCompleteDisplayed();
                    throw new InvalidElementStateException();
                }
                catch (TimeoutException e) {
                    LOGGER.info("Auto-complete is invisible");
                }
                // This is a hack. For some reason, focus is still on this element and typing start date appends to
                // this element instead of the start date element. Attempt to send tab key to get off this element.
                hotelSearchFragment.getDestCity().sendKeys(Keys.TAB);
            }
        }

    }

    @Override
    public void verifyDisambiguationPage(boolean hasErrorMessage) {

        ErrorMessenger msg = new ErrorMessenger(getWebdriverInstance());
        msg.setMessageType(ErrorMessenger.MessageType.valueOf("error"));
        if (hasErrorMessage) {
            for (String m : msg.getMessages()) {
                assertThat(m.contains("Where are you going?")).isTrue();
                assertThat(m.contains("Please choose your location from the list.")).isTrue();
                assertThat(m.contains("If your location is not listed, please check " +
                        "your spelling or make sure it is on our")).isTrue();
                assertThat(m.contains("destination cities list")).isTrue();
            }
        }
        verifyDisambiguationLayerIsVisible();
    }

    @Override
    public void verifyDisambiguationResults(List<String> results) {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        ArrayList<String> disambuguationContent = hotelSearchFragment.getAutocompleteContents();
        for (String res : results) {
            res = res.trim();
            assertThat(disambuguationContent.contains(res))
                    .as("Disambiguation content contains expected location : " + res)
                    .isTrue();
        }
    }

    @Override
    public void verifyDisambiguationLayerIsVisible() {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());

        WebElementAssert.assertThat(hotelSearchFragment.getAutoComplete().findElement(By.xpath("..//../div")))
            .isNotHidden().isDisplayed().textContains("Choose a location");
    }

    @Override
    public void verifyDisambiguationLayerIsNotVisible() {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());

        WebElementAssert.assertThat(hotelSearchFragment.getAutoComplete().findElement(By.xpath("..//../div")))
            .isNotDisplayed();
    }

    @Override
    public void clickOnTheDestinationFiled() {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        hotelSearchFragment.getDestCity().submit();
    }

    @Override
    public void launchSearch() {
        // make a hotel search from UHP page
        RefreshPageHandler
            .reloadPageIfDropdownSkinNotLoaded(
                getWebdriverInstance(),
                By.cssSelector(".numChildren span.ui-selectmenu-button a, " +
                    ".children span.ui-selectmenu-button a, #numChildren"));

        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        String pageName = new PageName().apply(getWebdriverInstance());

        HotelSearchFragment hotelSearchFragment;

        if (pageName.contains("tile.hotel.geo.low.level")) {
            // tile.hotel.geo.low.level page
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance(), By.className("landingFareFinder"));
        }
        else {
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance()); // UHP page
        }

        hotelSearchFragment.getDestCity().sendKeys(globalSearchParameters.getDestinationLocation());
        hotelSearchFragment.selectStartDate(globalSearchParameters.getStartDate());
        hotelSearchFragment.selectEndDate(globalSearchParameters.getEndDate());
        hotelSearchFragment.selectHotelRooms(searchParameters.getNumberOfHotelRooms());
        hotelSearchFragment.selectHotelRoomAdults(searchParameters.getNumberOfAdults());
        hotelSearchFragment.withNumberOfChildren(searchParameters.getNumberOfChildren());
        hotelSearchFragment.withEnableSearchPartner(hotelSearchParameters.getEnableHComSearch());
        hotelSearchFragment.launchSearch();
    }

    @Override
    public void clickSearch() {
        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains("uhp.index") || pageName.contains("hotwire.home") || pageName.contains("hotel.index") ||
                pageName.contains("hotwire.hotel")) {
            RefreshPageHandler.reloadPageIfDropdownSkinNotLoaded(getWebdriverInstance(),
                By.cssSelector(".numChildren span.ui-selectmenu-button a, .children span.ui-selectmenu-button a"));
        }

        HotelSearchFragment hotelSearchFragment;
        pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains("uhp.index") || pageName.contains("hotwire.home")) {
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        }
        else if (pageName.contains("hotel.index") || pageName.contains("hotwire.hotel")) { // hotel landing page.
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        }
        else if (pageName.contains("hotel.search.layout")) { // What page is this?
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
            hotelSearchFragment.openFareFinderOnResultsPage();
        }
        else {
            throw new UnimplementedTestException("Unknown hotel search method." +
                " Hotel search is currently only valid from the UHP or the hotel landing page");
        }
        hotelSearchFragment.withEnableSearchPartner(hotelSearchParameters.getEnableHComSearch());
        hotelSearchFragment.launchSearch();
    }

    @Override
    public void clickOnTheHotelChildrenFiled() {
        new HotelSearchFragment(getWebdriverInstance()).getHotelRoomChild().click();
    }

    @Override
    public void setDates(Date startDate, Date endDate) {
        HotelSearchFragment hotelSearchFragment;
        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains("uhp.index") || pageName.contains("hotwire.home")) {
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        }
        else if (pageName.contains("hotel.index") || pageName.contains("hotwire.hotel")) { // hotel landing page.
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        }
        else if (pageName.contains("hotel.search.layout")) { // What page is this?
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
            hotelSearchFragment.openFareFinderOnResultsPage();
        }
        else {
            throw new UnimplementedTestException("Unknown hotel search method." +
                " Hotel search is currently only valid from the UHP or the hotel landing page");
        }
        hotelSearchFragment.withStartDate(startDate);
        hotelSearchFragment.withEndDate(endDate);
    }

    @Override
    public void typeDates(Date startDate, Date endDate) {
        HotelSearchFragment hotelSearchFragment;
        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains("uhp.index") || pageName.contains("hotwire.home")) {
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        }
        else if (pageName.contains("hotel.index") || pageName.contains("hotwire.hotel")) { // hotel landing page.
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        }
        else if (pageName.contains("hotel.search.layout")) { // What page is this?
            hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
            hotelSearchFragment.openFareFinderOnResultsPage();
        }
        else {
            throw new UnimplementedTestException("Unknown hotel search method." +
                " Hotel search is currently only valid from the UHP or the hotel landing page");
        }
        hotelSearchFragment.withStartDate(startDate);
        hotelSearchFragment.withEndDate(endDate);
    }

    @Override
    public void compareSearchResultsWithPrevious(boolean isTheSame) {
        boolean isEquals = true;
        String priceA, priceE;
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        List<SearchSolution> lsExpected = searchParameters.getSearchSolutionsList();
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            logger.info("Error while freezing WebDriver!");
        }
        List<SearchSolution> lsActual = resultsPage.getSearchSolutionList();
        if (isTheSame) {
            for (int i = 0; i < lsExpected.size(); i++) {
                priceA = lsActual.get(i).getPrice();
                priceE = lsExpected.get(i).getPrice();
                System.out.println("actual " + priceA + " expected " + priceE);
                assertThat(priceE.equals(priceA))
                        .as("Search solution price, in place " + i + ", expected " + priceE + ", actual " + priceA)
                        .isTrue();
            }
        }
        else {
            if (lsActual.size() == lsExpected.size()) {
                for (int i = 0; i < lsExpected.size(); i++) {
                    System.out.println(lsExpected.get(i).getPrice() + "       " + lsActual.get(i).getPrice());
                    if (!lsExpected.get(i).getPrice().equals(lsActual.get(i).getPrice())) {
                        isEquals = false; // lsExpected and lsActual is not equals
                    }
                }
            }
            else {
                isEquals = false;
            }
            assertThat(isEquals).as("the two search results are equals!").isFalse();
        }
    }

    @Override
    public void rememberSearchSolutionsList() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        searchParameters.setSearchSolutionsList(resultsPage.getSearchSolutionList());
    }

    @Override
    public void checkFareFinderDefaultAdultsValue(Integer iAdults) {
        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains("uhp.index") || pageName.contains("hotwire.home")) {
            new HotelSearchFragment(getWebdriverInstance());
        }
        else if (pageName.contains("hotel.index") || pageName.contains("hotwire.hotel")) { // hotel landing page.
            new HotelSearchFragment(getWebdriverInstance());
        }
        else {
            throw new UnimplementedTestException("Unknown hotel search method." +
                " Hotel search is currently only valid from the UHP or the hotel landing page");
        }
        WebElement option = getWebdriverInstance().findElement(
            By.cssSelector("a#hotelRoomAdults-button span.ui-selectmenu-text"));

        assertThat(option.getText().equals(iAdults.toString()))
                .as("Expected default u value " + iAdults + " but actual value " + option.getText())
                .isTrue();
    }

    @Override
    public void checkFareFinderDefaultChildrenValue(Integer iChildren) {
        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains("uhp.index") || pageName.contains("hotwire.home")) {
            new HotelSearchFragment(getWebdriverInstance());
        }
        else if (pageName.contains("hotel.index") || pageName.contains("hotwire.hotel")) { // hotel landing page.
            new HotelSearchFragment(getWebdriverInstance());
        }
        else {
            throw new UnimplementedTestException("Unknown hotel search method." +
                " Hotel search is currently only valid from the UHP or the hotel landing page");
        }
        WebElement option = getWebdriverInstance().findElement(
            By.cssSelector("a#hotelRoomChild-button span.ui-selectmenu-text"));

        assertThat(option.getText().equals(iChildren.toString()))
                .as("Expected default children value " + iChildren + " but actual value " + option.getText())
                .isTrue();
    }

    @Override
    public void verifyAllInclusivePriceAtDetails() {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(detailsPage.isPriceAllInclusive());
    }

    @Override
    public void verifyPrice(String price) {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(detailsPage.getPrice()).as("Price on details page").isEqualTo(price);
    }

    @Override
    public void verifyCorrespondenceBetweenAdultsAndRooms() {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        hotelSearchFragment.selectHotelRooms(searchParameters.getNumberOfHotelRooms());
        Integer numberOfAdults = Integer.parseInt(hotelSearchFragment.getHotelRoomAdults().getAttribute("value"));

        assertThat(numberOfAdults).isGreaterThanOrEqualTo(searchParameters.getNumberOfHotelRooms())
                .as("Number of adults is equal or more number of rooms");

        String[] availableNumbersOfAdults = hotelSearchFragment.getHotelRoomAdults().getText().split("\n");
        String[] unavailableNumbersOfAdults = new String[searchParameters.getNumberOfHotelRooms()];

        for (Integer i = 0; i < unavailableNumbersOfAdults.length; i++) {
            unavailableNumbersOfAdults[i] = i.toString();
            for (String availableNumbersOfAdult : availableNumbersOfAdults) {
                assertThat(availableNumbersOfAdult.equals(unavailableNumbersOfAdults[i]))
                        .as("List of number of adults contains: " + unavailableNumbersOfAdults[i] +
                                " which less then hotel rooms: " + searchParameters.getNumberOfHotelRooms().toString())
                        .isFalse();
            }
        }
    }

    @Override
    public void chooseResultByPrice(Integer lessPrice, Integer morePrice) {
        Integer i;
        HotelResultsPage hotelResults = new HotelResultsPage(getWebdriverInstance());
        hotelResults.selectSortBy("Price (low to high)");
        List<Integer> priceList = hotelResults.getPriceList();

        for (i = 0; i < priceList.size(); i++) {
            if ((priceList.get(i) <= morePrice) && (priceList.get(i) >= lessPrice)) {
                break;
            }
        }
        hotelResults.select(i);
    }

    @Override
    public void checkResultsDistance() {
        HotelResultsPage hotelResults = new HotelResultsPage(getWebdriverInstance());
        List<String> listDistances = hotelResults.getDistanceList();
        assertThat(listDistances.size() > 0).as("List with hotel results distances are empty").isTrue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void verifyNeighborhoodsInDB() {
        List<Map<String, Object>> listResult;

        String sql1 = "SELECT neighborhood_name,city_id,destination_id " +
            "FROM neighborhood WHERE city_id IS NULL AND active_ind = \'Y\'";

        listResult = jdbcTemplate.queryForList(sql1);
        assertThat(listResult.size() == 0).as("SQL returns an empty results").isTrue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void verifyChainNamesInDB() {

        List<Map<String, Object>> listResult;
        List<String> partnersName = Arrays.asList("AmeriSuites", "Days Inns", "DoubleTree by Hilton",
            "Extended StayAmerica Hotels", "Hilton Hotels & Resorts", "Holiday Inn Hotels & Resorts",
            "Hyatt Hotels & Resorts", "Hyatt Place", "La Quinta Inns", "Radisson Hotels & Resorts", "Ramada Hotels",
            "Sheraton Hotels & Resorts");

        int i = 0;

        String sql1 = "Select chain_name from hotel_chain" + " where PEGASUS_CHAIN_CODE" +
            " in (\'HI\', \'EA\', \'SI\', \'HH\', \'HY\', \'RAD\'," + " \'DT\',\'LQ\', \'RA\', \'DI\', \'AJ\') " +
            "order by chain_name";

        listResult = jdbcTemplate.queryForList(sql1);

        while (i < listResult.size()) {
            assertThat(listResult.get(i).get("CHAIN_NAME").equals(partnersName.get(i)))
                    .as("CHAIN_NAME Expected: \'" + partnersName.get(i) + "\';  Actual in DB: \'" +
                            listResult.get(i).get("CHAIN_NAME") + "\'")
                    .isTrue();
            i++;
        }
    }

    @Override
    public void watchThisTrip(String email) {
        HotelResultsTripwatcherLayerFragment tripWatcher = new HotelResultsTripwatcherLayerFragment(
            getWebdriverInstance());
        tripWatcher.watchThisTrip(email);
    }

    @Override
    public void getHotelReferenceNumberList() {
        HotelResultsPage hotelResults = new HotelResultsPage(getWebdriverInstance());
        searchParameters.setHotelRefNumbersList(hotelResults.getHotelRefNumbersList());
    }

    @Override
    public void turnOnOffHotelInDB(boolean isOn) {
        String active;
        if (isOn) {
            active = "Y";
        }
        else {
            active = "N";
        }

        String hotelIDQuery = "select h.hotel_id from hotel h," +
            " hotel_room hr, search_pgood sp, search_solution ss where h.hotel_id = hr.hotel_id" +
            " and hr.pgood_id = sp.pgood_id   and sp.search_solution_id = ss.search_solution_id" +
            " and ss.display_number =" +
            hotelSearchParameters.getHotelRefNumbersList().get(
                hotelSearchParameters.getSelectedSearchSolution().getNumber());

        Map<String, Object> result = jdbcTemplate.queryForMap(hotelIDQuery);
        String hotelID = result.get("HOTEL_ID").toString();

        String updateQuery = "update hotel set active_ind = '" + active + "' where hotel_id = " + hotelID;
        jdbcTemplate.execute(updateQuery);
        jdbcTemplate.execute("commit;");
    }

    @Override
    public void verifyHComPopupWindow(boolean hcomPopupDisplayed) {
        Set<String> windowHandles = getWebdriverInstance().getWindowHandles();
        String rootHandle = WebDriverManager.getRootWindowHandle(getWebdriverInstance());
        if (hcomPopupDisplayed) {
            assertThat(windowHandles.size() == 2)
                    .as("Expected window handle size to be 2. One for root window and one for Hotels.com popup.")
                    .isTrue();
            for (String handle : windowHandles) {
                if (!handle.equals(rootHandle)) {
                    getWebdriverInstance().switchTo().window(handle);
                    assertThat(getWebdriverInstance().getPageSource())
                            .as("Expected Hotels.com popup window.").contains("Hotels.com");
                }
            }
            WebDriverManager.closeAllPopups.apply(getWebdriverInstance());
            new HotelResultsPage(getWebdriverInstance());
        }
        else {
            assertThat(windowHandles.size() == 1).as("Expected window handle size to be 1.").isTrue();
            new HotelResultsPage(getWebdriverInstance());
        }
    }

    @Override
    public void chooseResultsTypeCloseTripWatcher(String resultsType) {
        new HotelResultsPage(getWebdriverInstance());
        try {
            HotelResultsTripwatcherLayerFragment.waitForTripWatcherVisibility(getWebdriverInstance());
            new HotelResultsTripwatcherLayerFragment(getWebdriverInstance()).closeTripWatcherLayer();
            new HotelResultsPage(getWebdriverInstance());
        }
        catch (WebDriverException e) {
            // If there is a trip watcher issue, manually go to the right results type.
            logger.info("Issue waiting for and closing trip watcher. Manually go to correct results type.");
            getWebdriverInstance().navigate().to(
                getWebdriverInstance().getCurrentUrl().replaceAll("results\\?", "results-" + resultsType + "\\?"));
        }

        super.chooseHotelsResultsByType(resultsType);
    }

    @Override
    public void verifyAdultNumberLimit(int maxAdults) {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        assertThat(hotelSearchFragment.hotelRoomAdults().findElements(By.tagName("li")).size() == maxAdults)
                .as("Hotel room menu should allow to select at most " + maxAdults + " adults")
                .isTrue();
    }

    @Override
    public void verifyChildrenNumberLimit(int maxChildren) {
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        assertThat(hotelSearchFragment.hotelRoomChildren().findElements(By.tagName("li")).size() == maxChildren + 1)
                .as("Hotel room menu should allow to select at most " + maxChildren + " children")
                .isTrue();
    }

    @Override
    public void clickHotelRoomAdultDdl() {
        new HotelSearchFragment(getWebdriverInstance()).clickRoomAdultsButton();
    }
    @Override
    public void clickHotelRoomChildrenDdl() {
        new HotelSearchFragment(getWebdriverInstance()).clickRoomChildrenButton();
    }

    @Override
    public void verifyHotelSearchResultsPage() {
        new HotelResultsPage(getWebdriverInstance());
    }

    @Override
    public void confirmThatUrlContainsSearchTokenId(long tokenId) {
        assertThat(getWebdriverInstance().getCurrentUrl())
                .as("Search results url should contain the searchTokenId as: " + Long.toString(tokenId))
                .contains("searchTokenId=" + Long.toString(tokenId));
    }

    @Override
    public void chooseAmenitiesValue(String amenityName) {
        new HotelResultsAmenitiesFilteringTabPanelFragment(getWebdriverInstance())
                .checkAmenityByName(amenityName);
    }

    @Override
    public void compareHotelResultsWithPartners(String partners, String numberOfModule) {
        D2CFragment fragment = null;
        if ("first".equalsIgnoreCase(numberOfModule)) {
            fragment =  new D2CHotelFragment(getWebdriverInstance());
        }
        else if ("second".equalsIgnoreCase(numberOfModule)) {
            fragment = new D2CHotelFragment(getWebdriverInstance(), By.cssSelector(D2CHotelFragment.SECOND_D2C_MODULE));
        }

        for (String partner : partners.split(",")) {
            fragment.selectPartner(partner.trim());
        }
        fragment.comparePartners();
    }
}
