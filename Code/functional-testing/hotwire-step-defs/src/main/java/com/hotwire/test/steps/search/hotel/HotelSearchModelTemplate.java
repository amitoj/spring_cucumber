/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.hotel;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.hotwire.selenium.desktop.details.HotelDetailsPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.selenium.desktop.thirdparty.CommentCardPage;
import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.selenium.desktop.us.results.HotelResultsSearchResultsFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.filters.HotelResultsAmenitiesFilteringTabPanelFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.filters.HotelResultsAreasFilteringTabPanelFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.filters.HotelResultsFilteringTabsFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.filters.HotelResultsPriceFilteringTabPanelFragement;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.filters.HotelResultsStarRatingFilteringTabPanelFragment;
import com.hotwire.selenium.desktop.us.results.hotel.fragments.tripwatcher.HotelResultsTripwatcherLayerFragment;
import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import com.hotwire.selenium.desktop.widget.DatePicker;
import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.test.steps.util.RefreshPageHandler;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.SessionModifingParams;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.predicates.HasPageName;

import cucumber.api.PendingException;

/**
 * Template for HotelSearchModel implementation
 */
public class HotelSearchModelTemplate extends SearchModelTemplate<HotelSearchParameters> implements HotelSearchModel {
    protected List<String> searchResults;
    protected List<String> filteredResults;
    protected URL applicationUrl;

    @Autowired
    protected SearchParameters commonSearchParameters;

    @Autowired
    @Qualifier("hotelSearchParameters")
    protected HotelSearchParameters hotelSearchParameters;

    @Autowired
    protected SessionModifingParams sessionModifingParams;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setApplicationUrl(URL applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    public void setFilteredResults(List<String> filteredResults) {
        this.filteredResults = filteredResults;
    }

    @Override
    public void verifyResultsPageType(String resultsType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHotelDetailsPage(String pageType) {
        if (pageType.equals("opaque")) {
            new HotelDetailsPage(getWebdriverInstance(), new String[] {HotelDetailsPage.TILE_HOTEL_DETAILS_OPAQUE,
                HotelDetailsPage.TILE_HOTEL_DETAILS_OPAQUE_OVERVIEW});
        }
        else {
            new HotelDetailsPage(getWebdriverInstance(), new String[] {HotelDetailsPage.TILE_HOTEL_DETAILS_RETAIL,
                HotelDetailsPage.TILE_HOTEL_DETAILS_RETAIL_OVERVIEW});
        }
    }

    @Override
    public void verifyOpaqueHotelsLabel(String label, String resultsType) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        String actHotelName = resultsPage.getSearchSolutionList().get(0).getHotelName();
        assertThat(actHotelName).contains(label);
    }

    @Override
    public void clickSearch() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySearchWinnerDeal(String dealType, SearchSolution solution) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHotelResultsPageView(String viewType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyAllInclusivePriceAtResults(String priceType, String resultsType) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());

        String priceLabel = resultsPage.getSearchSolutionList().get(0).getPriceLabel();
        boolean isAllInclusive = HotelResultsPage.ALL_INCLUSIVE_LABELS.contains(priceLabel);

        assertThat(isAllInclusive).as("Price type").isEqualTo("all inclusive".equalsIgnoreCase(priceType));
    }

    @Override
    public void verifyAllInclusivePriceAtDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHotelSearchResultsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseHotelsResultsByType(String resultsType) {
        HotelResultsPage hotelResults = new HotelResultsPage(getWebdriverInstance());
        if (resultsType.equals("opaque")) {
            hotelResults.chooseOpaque();
        }
        else if (resultsType.equals("retail")) {
            hotelResults.chooseRetail();
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void chooseHotelFromResults(String neighborhood, String starRating, String crs) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void activateRetailExamplesForOpaque(boolean enableExamples) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyVisibilityOfRetailExamples(boolean examplesShouldBeVisible) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void sortByCriteria(String sortCriteria) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyMediaPlannerLayerIsShown() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTripWatcherLayer(boolean seeInResults) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void turnOnTripWatcherLayer() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void showActivateServicesTab() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyValidationErrorOnField(String validationError, String field) {
        HomePage homePage = new HomePage(getWebdriverInstance());
        List<String> errorMessages = Arrays.asList(homePage.getErrorMessages().split("\n"));

        if ("location".equalsIgnoreCase(field)) {
            if ("unknown destination".equalsIgnoreCase(validationError)) {
                assertThat(StringUtils.join(errorMessages, " ")).contains(
                    "Where are you going? Please choose your location from the list. " +
                        "If your location is not listed, please check your spelling or make sure " +
                        "it is on our destination cities list.");
            }
            else if ("empty destination".equalsIgnoreCase(validationError)) {
                assertThat(StringUtils.join(errorMessages, " ")).contains("Enter a destination");
            }
            else {
                verifyValidationErrorOnField(validationError, field);
            }
        }
        else if ("children age".equalsIgnoreCase(field)) {
            if ("children age".equalsIgnoreCase(validationError)) {
                assertThat(StringUtils.join(errorMessages, " ")).contains("Please provide the ages of children below.");
            }
            else {
                verifyValidationErrorOnField(validationError, field);
            }
        }
        else {
            verifyValidationErrorOnField(validationError, field);
        }
    }

    @Override
    public void filterOutTopNeighborhood() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void clickSeeAllResultsLink() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void appendQueryParameterToUrl(String queryParameter) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySearchCriteria() {
        HotelSearchFragment hotelSearchFragment = new HotelResultsPage(getWebdriverInstance()).getFareFinderFragment();
        hotelSearchFragment.getEditSearch().click();
        List<HotelRoomParameters> roomParameters = searchParameters.getHotelRoomParameters();

        assertThat(roomParameters.get(0).getChildrenCount())
                .as("Number of children must be equals with index page paramenters")
                .isEqualTo(new Integer(new Select(hotelSearchFragment.getHotelRoomChild())
                        .getFirstSelectedOption().getText()));
    }

    @Override
    public void verifyActivitiesBanner(String destinationCity, Date startDate) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyActivitiesPage(String destinationCity, Date endDate, Date startDate) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void clickBanner() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyMFI(HotelSearchParameters hotelSearchParameters) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void choosePartner(String partner) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void launchMFIPartnerSearch() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPartnersSearch(HotelSearchParameters hotelSearchParameters, String partnerName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPartnersSearchInDB(String partnerName) {
        throw new UnimplementedTestException("Implement me!");
    }

//    row and domestic
    @Override
    public void verifyLocationOnResultsPage(String location) {
        HotelResultsPage hotelResultsPage = new HotelResultsPage(getWebdriverInstance());
        WebDriverWait waitForLocation = new WebDriverWait(getWebdriverInstance(), 30);
        try {
            waitForLocation.until(ExpectedConditions.visibilityOf(hotelResultsPage.getDestinationNameElement()));
            logger.info("The destination name element is visible");
        }
        catch (TimeoutException e) {
            logger.error(e.getMessage());
        }
        assertThat(hotelResultsPage.getDestinationName())
                .as("Location on results page as expected")
                .startsWith(location);
    }

    @Override
    public void verifyResultsContainLocation(String location) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseCustomerRecommendedSolution() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        for (SearchSolution searchSolution : resultsPage.getSearchSolutionList()) {
            if (searchSolution.getRating() != null) {
                searchParameters.setSelectedSearchSolution(searchSolution);
                resultsPage.select(searchSolution.getNumber());
                return;
            }
        }
        throw new ZeroResultsTestException("There are no customer recommended solutions!");
    }

    @Override
    public void verifyCustomerRating(String rating) {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(rating.equalsIgnoreCase(detailsPage.getCustomerRating()));
    }

    /**
     * For ROW and domestic POS
     */
    @Override
    public void verifyRetailHeroIsPresent() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        HotelResultsSearchResultsFragment results = resultsPage.getSearchResultsFragment();
        if (results.getSearchResultsHrefList().size() == 0) {
            throw new PendingException("Search returned 0 results.");
        }
        if (getWebdriverInstance().findElements(By.cssSelector(".errorMessage, .notificationMessages")).size() > 0) {
            throw new PendingException("Inventory check redirected to results page due to sold out.");
        }
        if (!resultsPage.opaqueResultsAreDisplayed()) {
            resultsPage = resultsPage.chooseOpaque();
            results = resultsPage.getSearchResultsFragment();
        }
        assertThat(resultsPage.opaqueResultsAreDisplayed())
                .as("Expected opaque results to be displayed with retail result on top.")
                .isTrue();
        assertThat(results.isRetailHeroResultDisplayed())
                .as("Expected retail hero to be displayed but it wasn't.")
                .isTrue();
        if (StringUtils.isEmpty(commonSearchParameters.getDestinationLocation())) {
            logger.info("Retail hero results from retail as gateway, OPM link.");
            return;
        }
        String actualResultName = results.getRetailHeroResultName();
        logger.info("Result hotel name: " + actualResultName + " - Search param destination: " +
                commonSearchParameters.getDestinationLocation());
        assertThat(actualResultName.toLowerCase())
                .as("Expected " + commonSearchParameters.getDestinationLocation() +
                        " to be contained in the result name: " + actualResultName)
                .contains(commonSearchParameters.getDestinationLocation().toLowerCase());
    }

    @Override
    public void selectHotelByName(String hotelName) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        for (SearchSolution solution : resultsPage.getSearchSolutionList()) {
            if (solution.getHotelName().contains(hotelName)) {
                searchParameters.setSelectedSearchSolution(solution);
                resultsPage.select(solution.getNumber());
                return;
            }
        }
        throw new ZeroResultsTestException("There are no hotels with name " + hotelName);
    }

    @Override
    public void verifyPrice(String price) {
        throw new UnimplementedTestException("Implement me!");
    }

    // Work with ROW and US as they share the same results/details page.
    @Override
    public void verifyCrossSellData(SearchSolution solution) {
        if (solution.getHotelName().equals("stackedLayout")) {
            // Came from XSD13=1 single cross sell banner on opaque details page.
            HotelResultsPage results = new HotelResultsPage(getWebdriverInstance());
            assertThat(results.opaqueResultsAreDisplayed())
                    .as("Expected retail results page. Got opaque results page.")
                    .isFalse();
            if (results.isSeeAllHotelsButtonDisplayed()) {
                results.clickSeeAllHotelsButton();
                results.waitForUpdatingLayer();
                results = new HotelResultsPage(getWebdriverInstance());
            }
            Double xsellPrice = new Double(solution.getPrice().replaceAll("[^0-9.]", ""));
            boolean foundXsellPrice = false;
            for (Double price : results.getSearchResultsFragment().getPricesFromSearchResults()) {
                Double diff = Math.abs(price - xsellPrice);
                logger.info("Absolute Diff of result: " + price + " and cross sell: " + xsellPrice + ": " + diff);
                if (diff <= 1.00) {
                    foundXsellPrice = true;
                    break;
                }
            }
            assertThat(foundXsellPrice)
                    .as("Expected at least on result solution to be priced at the " + xsellPrice +
                            " from the cross sell. Check log file for cross sell difference threshold <= 1.00.")
                    .isTrue();
        }
        else if (solution.getPrice().contains("%")) {
            // Came from first element of retails details cross sell scroll list. This is the generic cross sell.
            // Since this is a percentage, not much else we can verify.
            HotelResultsPage results = new HotelResultsPage(getWebdriverInstance());
            assertThat(results.opaqueResultsAreDisplayed())
                    .as("Expected opaque results page. Got retail results page.")
                    .isTrue();
        }
        else {
            // We came from the table list of cross sells on opaque details or from not the first cross sell on the
            // retail details scrollable list.
            if (new PageName().apply(getWebdriverInstance()).contains(".search.results") &&
                    getWebdriverInstance().findElements(By.cssSelector(".errorMessage")).size() > 0) {
                throw new PendingException("Inventory check redirected to results page due to sold out.");
            }
            HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
            Double expected = new Double(solution.getPrice().replaceAll("[^0-9.]", ""));
            Double actual = new Double(detailsPage.getPrice().replaceAll("[^0-9.]", ""));
            Double diff = Math.abs(expected - actual);
            logger.info("SOLUTION PRICE: " + expected + " - DETAILS PAGE PRICE: " + actual + " - DIFF: " + diff);
            assertThat(diff)
                    .as("HOTEL DATA: " + solution.getHotelData() + " - DETAILS PRICE: " + detailsPage.getPrice() +
                            " - allowed difference of 2.00 exceeded: " + diff)
                    .isLessThanOrEqualTo(2.00);

            String pageName = new PageName().apply(getWebdriverInstance());
            if (new HasPageName(HotelDetailsPage.TILE_HOTEL_DETAILS_RETAIL_OVERVIEW).apply(pageName)) {
                assertThat(solution.getHotelData().contains(detailsPage.getHotelName()))
                        .as("HOTEL DATA: " + solution.getHotelData() + " - " + detailsPage.getHotelName())
                        .isTrue();
            }
        }
    }

    @Override
    public void verifyDiscountBannerOnFF() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDiscountBannerOnHotelResults() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCustomerCarePhoneNumber(String page, String phoneNumber) {
        if ("list".equals(page)) {
            HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
            assertThat(resultsPage.getCustomerCarePhoneNumber()).as("customer care phone number")
                .isEqualTo(phoneNumber);
        }
        else if ("map".equals(page)) {
            HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
            assertThat(resultsPage.getCustomerCarePhoneNumber()).as("customer care phone number")
                .isEqualTo(phoneNumber);
        }
        else if ("details".equals(page)) {
            HotelDetailsPage hotelDetailsPage = new HotelDetailsPage(getWebdriverInstance());
            assertThat(hotelDetailsPage.getCustomerCarePhoneNumber()).as("customer care phone number").isEqualTo(
                phoneNumber);
        }
        else {
            throw new UnimplementedTestException("Implement me!");
        }
    }

    @Override
    public void doSubscribeOnResultPage() {
        String email = "testsub_" + new DateTime().getMillis() + "@hotwire.com";
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        String subscriptionCode = resultsPage.doSubscription(email);
        String sql = "select EMAIL, WANTS_NEWS_LETTER, PLACEMENT_CODE from CUSTOMER " + "where EMAIL = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, new Object[] {email});
        while (sqlRowSet.next()) {
            assertThat(subscriptionCode.equals(sqlRowSet.getString("PLACEMENT_CODE")))
                    .as("Subscription code from db " + sqlRowSet.getObject("PLACEMENT_CODE") + "not equal site code " +
                            subscriptionCode)
                    .isTrue();
        }
    }

    @Override
    public void verifyDisclaimer() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySavingsLayer(boolean state) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void changeHotelSearch() {
        new HotelResultsPage(getWebdriverInstance()).makeVisibleFareFinder();
        HotelSearchFragment hotelSearchFragment = new HotelSearchFragment(getWebdriverInstance());
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        hotelSearchFragment.withDestinationLocation(globalSearchParameters.getDestinationLocation());
        hotelSearchFragment.withStartDate(globalSearchParameters.getStartDate());
        hotelSearchFragment.withEndDate(globalSearchParameters.getEndDate());
        hotelSearchFragment.withNumberOfHotelRooms(searchParameters.getNumberOfHotelRooms());
        hotelSearchFragment.withNumberOfAdults(searchParameters.getNumberOfAdults());
        hotelSearchFragment.withNumberOfChildren(searchParameters.getNumberOfChildren());
        hotelSearchFragment.launchSearch();
    }

    @Override
    public void verifyHotelSearchDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyFareFinderDestination(String topDestination) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyFacilitiesTermUsage(String term) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySemiOpaqueRoomPhotos() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHotelNeighborhood() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void displayMapPopup() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyMapPopup() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyNoRoomPhotos() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void returnToSearchResults(String position) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void removeSessionCookieAndReloadPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyBackToResultsLink() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyLowPriceGuaranteeOffer() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyKnowBeforeYouGoNotice() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDisambiguationPage(boolean hasErrorMessage) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDisambiguationResults(List<String> results) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDetailsFareFinderState(boolean isFareFinderExpanded) {
        boolean isExpanded = new HotelDetailsPage(getWebdriverInstance()).isFareFinderExpanded();
        assertThat(isExpanded)
                .as("Expected expanded details fare finder to be " + isFareFinderExpanded + " but was " + isExpanded)
                .isEqualTo(isFareFinderExpanded);
    }

    @Override
    public void expandDetailsPageFareFinder() {
        new HotelDetailsPage(getWebdriverInstance()).expandFareFinder();
        // Wait until Ajax is silent
        new com.hotwire.util.webdriver.functions.Wait<Boolean>(new IsAjaxDone()).maxWait(30).apply(
            getWebdriverInstance());
        new HotelDetailsPage(getWebdriverInstance());
    }

    @Override
    public void collapseDetailsPageFareFinder() {
        new HotelDetailsPage(getWebdriverInstance()).collapseFareFinder();
        // Wait until Ajax is silent
        new com.hotwire.util.webdriver.functions.Wait<Boolean>(new IsAjaxDone()).maxWait(30).apply(
            getWebdriverInstance());
        new HotelDetailsPage(getWebdriverInstance());
    }

    @Override
    public void verifyIntlDetailsPageFromIntlDetailsPageSearch(Date startDate, Date endDate) {
        SimpleDateFormat sdf = DatePicker.getSelectedCountryDateFormat(getWebdriverInstance());
        if (new GlobalHeader(getWebdriverInstance()).currentlyOnDomesticSite()) {
            // Have to hack out modified US format because the details page doesn't do leading 0.
            sdf = new SimpleDateFormat("M/d/yy");
        }
        String startDateString = sdf.format(startDate);
        String endDateString = sdf.format(endDate);

        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        String stayText = detailsPage.getDatesOfStayString();
        assertThat(stayText.contains(startDateString))
                .as("Expected \"" + stayText + "\" to contain " + startDateString)
                .isTrue();
        assertThat(stayText.contains(endDateString))
                .as("Expected \"" + stayText + "\" to contain " + endDateString)
                .isTrue();
    }

    @Override
    public void verifySearchResultsPage(String resultsType) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        if (resultsType.equals("retail")) {
            assertThat(resultsPage.opaqueResultsAreDisplayed()).as("Expected retail results.").isFalse();
        }
        else if (resultsType.equals("opaque")) {
            assertThat(resultsPage.opaqueResultsAreDisplayed()).as("Expected opaque results.").isTrue();
        }
        else if (resultsType.equals("search")) {
            assertThat(resultsPage.isOpaqueTabVisible()).isTrue();
            assertThat(resultsPage.isRetailTabVisible()).isTrue();
        }
    }

    @Override
    public void clickOnTheHotelChildrenFiled() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setDates(Date startDate, Date endDate) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void typeDates(Date startDate, Date endDate) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareSearchResultsWithPrevious(boolean isTheSame) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void rememberSearchSolutionsList() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkFareFinderDefaultAdultsValue(Integer iAdults) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkFareFinderDefaultChildrenValue(Integer iChildren) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyResultsPageHasSolutions() {
        assertThat(new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment().getSearchResultsHrefList())
                .as("Expected more than 0 results.")
                .isNotEmpty();
    }

    /**
     * For ROW and domestic.
     */
    @Override
    public void findFare(String selectionCriteria) {

        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains("uhp.index") || pageName.contains("hotwire.home") || pageName.contains("hotel.index") ||
            pageName.contains("resultsList") ||
            pageName.contains("hoteldetails") || pageName.contains("hotelApp")) {
            findFare(selectionCriteria, new HomePage(getWebdriverInstance()).findHotelFare());
        }
        // Converged hotel landing page with converged elements.
        else if (pageName.contains("hotel.index") || pageName.contains("hotwire.hotel")) {
            findFare(selectionCriteria, new HotelSearchFragment(getWebdriverInstance()));
        }
        else {
            throw new UnimplementedTestException("Unknown hotel search method." +
                      " Hotel search is currently only valid from the UHP or the hotel landing page");
        }
        retrySearchHandler(0);
    }

    /**
     * Retry searching from UHP/landing page if we're still on that page with no error messages. This assumes that all
     * the criteria is still filled in.
     */
    private void retrySearchHandler(int tries) {
        String pageName = new PageName().apply(getWebdriverInstance());
        if ((pageName.contains("uhp.index") || pageName.contains("hotwire.home")) &&
            getWebdriverInstance().findElements(By.cssSelector(".errorMessages")).size() == 0) {
            logger.info("Search retry index: " + tries);
            if (tries > RefreshPageHandler.MAX_RETRIES) {
                throw new PendingException("Max retries (" + RefreshPageHandler.MAX_RETRIES +
                    ") hit trying to initiate search from UPH/hotel landing page.");
            }
            // If masking layer showed up, then search was initiated correctly. Don't retry as probably some kind of
            // MFI layer.
            try {
                if (getWebdriverInstance().findElement(By.cssSelector(".masked")).isDisplayed()) {
                    return;
                }
            }
            catch (NoSuchElementException e) { /* do nothing */
            }
            // Hit a weird issue. Maybe due to multithreading. Everything should still be filled out.
            logger.info("Still on homepage or UHP after clicking search. Attempting to try again.");
            if (pageName.contains("hotel.index") || pageName.contains("hotwire.hotel")) {
                logger.info("Retrying from hotel landing page.");
                new HotelIndexPage(getWebdriverInstance());
                new HotelSearchFragment(getWebdriverInstance()).getDestCity().submit();
            }
            else {
                logger.info("Retrying from UHP.");
                new HomePage(getWebdriverInstance());
                new HotelSearchFragment(getWebdriverInstance()).getDestCity().submit();
            }
            retrySearchHandler(++tries);
        }
    }

    /**
     * For ROW and domestic.
     */
    @Override
    public void findFare(String selectionCriteria, HotelSearchFragment searchFragment) {

        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();

        searchFragment.withNumberOfHotelRooms(searchParameters.getNumberOfHotelRooms())
            .withNumberOfAdults(searchParameters.getNumberOfAdults())
            .withNumberOfChildren(searchParameters.getNumberOfChildren())
            .withDestinationLocation(globalSearchParameters.getDestinationLocation())
            .withStartDate(globalSearchParameters.getStartDate()).withEndDate(globalSearchParameters.getEndDate())
            .withEnableSearchPartner(hotelSearchParameters.getEnableHComSearch()).launchSearch();

        if (StringUtils.isNotEmpty(selectionCriteria)) {
            HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
            this.sortByCriteria(selectionCriteria);
            resultsPage.getSearchResultsFragment();
        }
    }

    /**
     * For ROW and US
     */
    @Override
    public void findPropertyFare() {
        RefreshPageHandler.reloadPageIfDropdownSkinNotLoaded(getWebdriverInstance(),
            By.cssSelector(".numChildren span.ui-selectmenu-button a, .children span.ui-selectmenu-button a"));

        String pageName = new PageName().apply(getWebdriverInstance());
        if (pageName.contains("uhp.index") || pageName.contains("hotwire.home")) {
            findPropertyFare(new HomePage(getWebdriverInstance()).findHotelFare());
        }
        // Converged hotel landing page with converged elements.
        else if (pageName.contains("hotel.index") || pageName.contains("hotwire.hotel")) {
            findPropertyFare(new HotelSearchFragment(getWebdriverInstance()));
        }
        else {
            throw new UnimplementedTestException("Unknown hotel search method." +
                      " Hotel search is currently only valid from the UHP or the hotel landing page");
        }
        retrySearchHandler(0);
    }

    /**
     * For ROW and US
     */
    @Override
    public void findPropertyFare(HotelSearchFragment searchFragment) {

        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();

        searchFragment.withNumberOfHotelRooms(searchParameters.getNumberOfHotelRooms())
            .withNumberOfAdults(searchParameters.getNumberOfAdults())
            .withNumberOfChildren(searchParameters.getNumberOfChildren())
            .withDestinationLocation(globalSearchParameters.getDestinationLocation())
            .withStartDate(globalSearchParameters.getStartDate()).withEndDate(globalSearchParameters.getEndDate())
            .withEnableSearchPartner(hotelSearchParameters.getEnableHComSearch()).launchSearch();
    }

    @Override
    public void verifySuggestedLocation(String number, String location, String style, String click) {
        HotelSearchFragment searchFragment = new HotelSearchFragment(getWebdriverInstance());
        searchFragment.typeLocation(location);
        searchFragment
            .waitForLocationAutocompleteVisibilityState(HotelSearchFragment.AutocompleteVisibilityState.VISIBLE);
        List<String> searchSuggestions = new HotelSearchFragment(getWebdriverInstance()).getAutocompleteContents();
        assertThat(searchSuggestions).startsWith(location);
    }

    @Override
    public void verifyCorrespondenceBetweenAdultsAndRooms() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseResultByPrice(Integer lessPrice, Integer morePrice) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkResultsDistance() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyNeighborhoodsInDB() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyChainNamesInDB() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyResultsCurrency(String currency) {
        String selectedCurrency = new GlobalHeader(getWebdriverInstance()).getSelectedCurrency();
        assertThat(currency.equals(selectedCurrency)).isTrue();
    }

    @Override
    public void verifyDistanceTextInResults(boolean isSeen) {
        new HotelResultsPage(getWebdriverInstance());
        List<WebElement> distanceElements = getWebdriverInstance().findElements(
            By.cssSelector(".resultBody .distance .miles"));
        logger.info("Expecting: " + isSeen);
        if (isSeen) {
            assertThat(distanceElements.size() > 0).isTrue();
            for (WebElement distanceElement : distanceElements) {
                assertThat(distanceElement.isDisplayed())
                        .as("Expected distance element seen to be " + isSeen +
                                " but was " + distanceElement.isDisplayed())
                        .isEqualTo(isSeen);
            }
        }
        else {
            assertThat(distanceElements.size() == 0).isTrue();
        }
    }

    @Override
    public void sortResultsBy(String sortBy) {
        new HotelResultsPage(getWebdriverInstance()).selectSortBy(sortBy);
        new HotelResultsPage(getWebdriverInstance());
    }

    @Override
    public void verifySortByResults(HotelResultsSortCriteria sortCriteria, HotelResultsSortOrderBy orderBy) {
        HotelResultsPage hotelResults = new HotelResultsPage(getWebdriverInstance());
        ArrayList<Double> results = new ArrayList<Double>();
        switch (sortCriteria) {
            case PRICE:
                results = hotelResults.getSearchResultsFragment().getPricesFromSearchResults();
                break;
            case STAR:
                for (String starText : hotelResults.getSearchResultsFragment().getStarRatingsTexts()) {
                    results.add(new Double(starText.substring(0, starText.indexOf(' '))));
                }
                break;
            case DISTANCE:
                results = hotelResults.getSearchResultsFragment().getDistancesFromSearchResults();
                break;
            case RECOMMENDED:
                throw new UnimplementedTestException(sortCriteria.getText() + " is not implemented in yet.");
            default:
                throw new RuntimeException("Invalid sort criteria.");
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
                throw new RuntimeException("Invalid order by criteria.");
        }
    }

    private void assertResultsSortedBy(ArrayList<Double> results, boolean doAscendingOrderBy) {
        for (int i = 1; i < results.size(); i++) {
            Double a = results.get(i);
            Double b = results.get(i - 1);
            logger.info("Compare " + b + (doAscendingOrderBy ? " <= " : " >= ") + a);
            if (doAscendingOrderBy) {
                assertThat(b.compareTo(a) == 0 || b.compareTo(a) == -1)
                        .as("Expected (" + b + " <= " + a + ") to be true but got false instead.")
                        .isTrue();
            }
            else {
                // Descending order.
                assertThat(b.compareTo(a) == 0 || b.compareTo(a) == 1)
                        .as("Expected (" + b + " >= " + a + ") to be true but got false instead.")
                        .isTrue();
            }
        }
    }

    @Override
    public void clickCommentCardLink() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        resultsPage.clickCommentCardLink();
        logger.info(">>>>> Switching to comment card window <<<<<");
        // Need to switch to comment card window. Looping through set as it is not guaranteed what order will be
        // returned. Assuming there will only be 2 windows opened as anymore will be problematic.
        WebDriver webdriverInstance = getWebdriverInstance();
        String parentWindow = webdriverInstance.getWindowHandle();
        // String parentTitle = webdriverInstance.getTitle();
        for (String handle : webdriverInstance.getWindowHandles()) {
            logger.info("Browser window handle: " + handle);
            if (!handle.equals(parentWindow)) {
                webdriverInstance.switchTo().window(handle);
                // Assume the window that doesn't have the parent title is the comment card.
                if (webdriverInstance.getTitle().equals("Comment card")) {
                    logger.info("Found comment card window.");
                    break;
                }
            }
        }
    }

    @Override
    public void getSearchResults() {
        searchResults = new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment()
            .getSearchResultsHrefList();
    }

    @Override
    public void verifyCommentCardGone() {
        // After submitting the comment card, it will disappear back to the calling search results page. We can
        // verify that the set of browser window handles will be 1, the parent browser window, and that the comment has
        // been sent.
        new HotelResultsPage(getWebdriverInstance());
        int windowHandleSize = getWebdriverInstance().getWindowHandles().size();
        logger.info("Browser handles total: " + getWebdriverInstance().getWindowHandles().size());
        assertThat(getWebdriverInstance().getWindowHandles().size())
                .as("Expected window handle size to be 1 but was " + windowHandleSize)
                .isEqualTo(windowHandleSize);
    }

    @Override
    public void verifyCommentCardPopup() {
        assertThat(getWebdriverInstance().getWindowHandles().size() > 1)
                .as("Expected comment card popup window to be displayed. " +
                        "Window handle size suggests otherwise - was less than 1")
                .isTrue();
    }

    @Override
    public void typeAndSubmitCommentCard() {
        CommentCardPage commentCard = new CommentCardPage(getWebdriverInstance());
        commentCard.fillAndSubmitCommentCard("I <3 HOTWIRE!!!", 3, 4, 3, 4);
        // The comment card window will disappear as it is being sent to where-ever. Switching back to the parent
        // window.
        logger.info("Comment card sent. Switching to parent browser window.");
        getWebdriverInstance().switchTo().window(getWebdriverInstance().getWindowHandles().toArray()[0].toString());
    }

    @Override
    public void goBackToPreviousPage() {
        getWebdriverInstance().navigate().back();
    }

    @Override
    public void selectFilteringTab(String filterName) {
        // This is a hack!!! Intl calls amenities "facilities". This does not impair tests sending in "Facilities" for
        // the filter name.
        // This will needs further hacking when doing localized sites.
        if (!(new GlobalHeader(getWebdriverInstance()).hasVerticalNavigationTabs()) && filterName.equals("Amenities")) {
            filterName = "Facilities";
        }
        new HotelResultsFilteringTabsFragment(getWebdriverInstance()).selectTabByTabText(filterName);
    }

    @Override
    public void chooseRecommendedFilterValue(HotelResultsFilteringEnum filterEnum) {
        // The recommended value for amenities, accessiblity and areas is just the first filter checkbox.
        new HotelResultsPage(getWebdriverInstance());
        boolean validFilterType = true; // Assume true
        switch (filterEnum) {
            case PRICE:
                HotelResultsPriceFilteringTabPanelFragement priceFilterFragment =
                    new HotelResultsPriceFilteringTabPanelFragement(getWebdriverInstance());
                priceFilterFragment.selectRecommendedRadioButton();
                break;
            case STAR_RATING:
                HotelResultsStarRatingFilteringTabPanelFragment starRatingFilterFragment =
                    new HotelResultsStarRatingFilteringTabPanelFragment(getWebdriverInstance());
                starRatingFilterFragment.selectRecommendedRadioButton();
                break;
            case AMENITIES:
                HotelResultsAmenitiesFilteringTabPanelFragment amenitiesFilterFragment =
                    new HotelResultsAmenitiesFilteringTabPanelFragment(getWebdriverInstance());
                amenitiesFilterFragment.checkFirstFilteringCheckbox();
                break;
            case ACCESSIBILITY:
                HotelResultsAmenitiesFilteringTabPanelFragment filterFragment =
                    new HotelResultsAmenitiesFilteringTabPanelFragment(getWebdriverInstance());
                if (!filterFragment.isShowHideAccessibilityAmenitiesLinkDisplayed()) {
                    throw new PendingException(
                        "Show/Hide accessibility options link not visible meaning results do not contain " +
                            "accessibility options.");
                }
                filterFragment.showAccessibilityAmenities();
                filterFragment.checkFirstAccessibilityAmenityCheckbox();
                break;
            case AREAS:
                HotelResultsAreasFilteringTabPanelFragment areasFilterFragment =
                    new HotelResultsAreasFilteringTabPanelFragment(getWebdriverInstance());
                areasFilterFragment.checkFirstFilteringCheckbox();
                break;
            default:
                validFilterType = false;
                break;
        }

        if (!validFilterType) {
            throw new RuntimeException("Invalid filter type specified.");
        }
        new HotelResultsPage(getWebdriverInstance());
    }

    @Override
    public void verifyHotelFeatureInResults(String feature) {
        List<String> amenities = new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment()
            .getSearchResultsAmenitiesContent();
        for (String value : amenities) {
            assertThat(value.contains(feature)).isTrue();
        }
    }

    @Override
    public void checkHotelFeatureCheckbox(String feature) {
        new HotelResultsAmenitiesFilteringTabPanelFragment(getWebdriverInstance()).checkAmenityByName(feature);
    }

    @Override
    public void verifyFreeAmenityInDetailsPage(String feature) {
        List<String> freebies = new HotelDetailsPage(getWebdriverInstance()).getFreeAmenitiesList();
        assertThat(freebies.contains(feature) ||
        // intl details page.
            getWebdriverInstance().findElement(By.cssSelector(".amenitiesList")).getText().contains(feature))
                .as("Expected " + feature + " to be in freebies list. It was not.").isTrue();
    }

    @Override
    public void verifyRecommendedRangeFromFiltersInResults(HotelResultsFilteringEnum resultsFilterEnum) {
        HotelResultsSearchResultsFragment searchFragment =
            new HotelResultsSearchResultsFragment(getWebdriverInstance());
        // Assume true.
        boolean validFilterType = true;
        switch (resultsFilterEnum) {
            case PRICE:
                ArrayList<Double> results = searchFragment.getPricesFromSearchResults();
                for (int i = 0; i < results.size(); i++) {
                    Double price = results.get(i);
                    logger.info("Result index " + i + ": " + price);
                    assertThat(price <= 200.00)
                            .as("Result index " + i +
                                ": Expected price in result to be less than or equal to 200.00. Instead was " + price)
                            .isTrue();
                }
                break;
            case STAR_RATING:
                ArrayList<String> ratings = searchFragment.getStarRatingsTexts();
                for (int i = 0; i < ratings.size(); i++) {
                    String rating = ratings.get(i);
                    Double starRating = new Double(rating.substring(0, rating.indexOf(' ')));
                    logger.info("Result index " + i + ": " + starRating + " : " + rating);
                    assertThat(starRating >= 3.0 && starRating < 4.0)
                            .as("Result index " + i +
                                ": Expected star rating in result to be greater than or equal to 3 and less than 4." +
                                "Instead was " + starRating)
                            .isTrue();
                }
                break;
            default:
                validFilterType = false;
                break;
        }

        if (!validFilterType) {
            throw new RuntimeException("Invalid filter type specified.");
        }
    }

    @Override
    public void verifyRecommendedAreaFilterInResults() {
        // Right now the areas filter, as all the filters, automatically update on element selection AND this filter
        // fragment does not disappear, so it should still be visible.
        String recommendedArea = new HotelResultsAreasFilteringTabPanelFragment(getWebdriverInstance())
            .getLabelForFirstAreaCheckbox();
        ArrayList<String> areas = new HotelResultsSearchResultsFragment(getWebdriverInstance())
            .getNeighborhoodsFromOpaqueSearchResults();
        for (int i = 0; i < areas.size(); i++) {
            String area = areas.get(i);
            logger.info("Result index " + i + ": " + area);
            assertThat(area.toLowerCase().contains(recommendedArea.toLowerCase()))
                    .as("Result index " + i + ": Expected area in result to contain " +
                            recommendedArea + ". Result area was: " + area)
                    .isTrue();
        }
    }

    @Override
    public void unselectRecommendedFilterCheckbox(HotelResultsFilteringEnum filterEnum) {
        // Use only filter fragments that are checkbox filters.
        boolean validFilterType = true; // Assume true
        switch (filterEnum) {
            case AMENITIES:
                new HotelResultsAmenitiesFilteringTabPanelFragment(getWebdriverInstance())
                    .uncheckFirstFilteringCheckbox();
                break;
            case AREAS:
                new HotelResultsAreasFilteringTabPanelFragment(getWebdriverInstance()).uncheckFirstFilteringCheckbox();
                break;
            default:
                validFilterType = false;
                break;
        }

        if (!validFilterType) {
            throw new RuntimeException("Invalid filter type specified.");
        }
    }

    @Override
    public void verifyAllAreasCheckboxSelectedState(boolean isSelected) {
        boolean actualState = new HotelResultsAreasFilteringTabPanelFragment(getWebdriverInstance())
            .isAllAreasCheckboxSelected();
        logger.info("Expected selected state: " + isSelected + ". Actual selected state: " + actualState);
        assertThat(actualState)
                .as("Expected \"All areas\" checkbox selected state to be " + isSelected +
                        " but was instead " + actualState)
                .isEqualTo(isSelected);
    }

    @Override
    public void verifyResetFiltersDisplayed(boolean resetFiltersVisible) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        // Get the current state of the filter tabs fragment.
        resultsPage.getFilterTabsFragment();
        boolean actualVisibility = resultsPage.getFilterTabsFragment().isResetFiltersDisplayed();
        assertThat(actualVisibility)
                .as("Expecting Reset Filters visibility to be " + resetFiltersVisible +
                        " but it was " + actualVisibility)
                .isEqualTo(resetFiltersVisible);
    }

    @Override
    public void clickResetFilters() {
        new HotelResultsFilteringTabsFragment(getWebdriverInstance()).clickResetFilters();
    }

    @Override
    public void getFilteredHotelSearchResults() {
        filteredResults = new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment()
            .getSearchResultsHrefList();
    }

    @Override
    public void verifyResultsNotFiltered() {
        assertThat(filteredResults.size())
                .as("Expected sizes to be different: " + filteredResults.size() + " : " + searchResults.size())
                .isNotEqualTo(searchResults.size());
    }

    @Override
    public void viewRetailHotelExamples() {
        new HotelResultsPage(getWebdriverInstance())
            .getSearchResultsFragment().clickRetailExample();
    }

    @Override
    public void verifyVisibilityOfResultsRetailExamples(boolean retailExamplesShouldBeVisible) {
        HotelResultsSearchResultsFragment resultsFragment = new HotelResultsPage(getWebdriverInstance())
            .getSearchResultsFragment();
        if (retailExamplesShouldBeVisible) {
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
    public void verifyHotelResultsMapRenderedCorrectly() {
        assertThat(new HotelResultsPage(getWebdriverInstance()).isMapVerticallyAlignedWithResults()).isTrue();
        assertThat(new HotelResultsPage(getWebdriverInstance()).isResultsAndMapNotOverlapping()).isFalse();
    }

    @Override
    public void verifyDareToCompareExists() {
        assertThat(new HotelResultsPage(getWebdriverInstance()).dareToCompareModuleExists())
                .as("Expected dare to compare module to exist, but it is not found.")
                .isTrue();
    }

    @Override
    public void verifyTripWatcherLayerInResults(boolean tripwatcherIsDisplayed, boolean verifyInOpaque) {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());

        boolean isOpaque = resultsPage.opaqueResultsAreDisplayed();
        assertThat(isOpaque)
                .as("Expected opaque results to be " + verifyInOpaque + ", but was " + isOpaque + ".")
                .isEqualTo(verifyInOpaque);
        // Verify tripwatcher layer.
        boolean tripwatcherElementsDisplayed = false;
        try {
            HotelResultsTripwatcherLayerFragment.waitForTripWatcherVisibility(getWebdriverInstance());
            logger.info("Tripwatcher layer container is visible.");
            tripwatcherElementsDisplayed = true;
        }
        catch (TimeoutException e) {
            // Do nothing. Expected tripwatcher should not be visible. Use default value for
            // tripwatcherElementsDisplayed.
            logger.info("Timed out waiting for tripwatcher layer to be visible.");
        }
        catch (NoSuchElementException e) {
            // Do nothing. Expected when tripwatcher should not be visible. Use default value for
            // tripwatcherElementsDisplayed.
            logger.info("NoSuchElementException for tripwatcher layer.");
        }
        assertThat(tripwatcherElementsDisplayed)
                .as("Expected " + tripwatcherIsDisplayed + " for seeing the tripwatcher layer, but got " +
                    tripwatcherElementsDisplayed + " instead.")
                .isEqualTo(tripwatcherIsDisplayed);
    }

    @Override
    public void verifyReferencPriceModuleDisplayed() {
        assertThat(new HotelResultsPage(getWebdriverInstance()).isReferencePriceModuleDisplayed())
                .as("Expected reference price module to be displayed correctly. It was not.")
                .isTrue();
    }

    @Override
    public void verifyReferencePriceModuleValue() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        Double referencePrice = resultsPage.getReferencePriceValue();
        resultsPage.clickReferencePriceLinkContainer();
        resultsPage = new HotelResultsPage(getWebdriverInstance());
        Double resultPrice = resultsPage.getSearchResultsFragment().getPricesFromSearchResults().get(0);
        assertThat(referencePrice)
                .as("Expected reference price to equal the first result's price. Got reference price: " +
                        referencePrice + " and result price: " + resultPrice)
                .isEqualTo(resultPrice);
    }

    @Override
    public void verifyDbmLink() {
        assertThat(new HotelResultsPage(getWebdriverInstance()).heresYourDealIsDisplayed())
                .as("Expected here's your deal to be displayed, but it was not.")
                .isTrue();
    }

    // For intl site only.
    @Override
    public void verifyTripAdvisorInResults(boolean isVisible) {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void verifyRecommendedAccessibilityInResults() {
        HotelResultsPage resultsPage = new HotelResultsPage(getWebdriverInstance());
        // Recommended accessibility is the first accessibility option.
        String firstAccessibilityChoice = new HotelResultsAmenitiesFilteringTabPanelFragment(getWebdriverInstance())
            .getFirstAccessibilityAmenityCheckboxElement().getAttribute("amenityname");
        List<String> amenities = resultsPage.getSearchResultsFragment().getSearchResultsAmenitiesContent();
        for (String value : amenities) {
            assertThat(value.contains(firstAccessibilityChoice)).isTrue();
        }
    }

    @Override
    public void verifyLPGLayer() {
        assertThat(new HotelDetailsPage(getWebdriverInstance()).isLPGLayerPresent())
                .as("LPG layer is not presenting")
                .isTrue();
    }

    @Override
    public void verifyCancellationPolicyPanel() {
        assertThat(new HotelDetailsPage(getWebdriverInstance()).isCancellationPolicyPanelPresent())
                .as("LPG layer is not presenting")
                .isTrue();
    }

    @Override
    public void verifyTripAdvisorLayer() {
        assertThat(new HotelDetailsPage(getWebdriverInstance()).isTripAdvisorLayerPresent())
                .as("Trip advisor layer is not presenting")
                .isTrue();
    }

    @Override
    public void verifyLocationDesc() {
        assertThat(new HotelDetailsPage(getWebdriverInstance()).isLocationDescTextPresent())
                .as("Location description is not presenting")
                .isTrue();
    }

    @Override
    public void verifyFeaturesList() {
        assertThat(new HotelDetailsPage(getWebdriverInstance()).isFeaturesListPresent())
                .as("Feature list is not presenting")
                .isTrue();
    }

    @Override
    public void verifyTripWatcherModuleInHotelResultsList(boolean isDisplayed) {
        boolean actual = new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment()
            .isTripWatcherSpeedBumpDisplayed();
        assertThat(actual)
                .as("Expected tripwatcher results list module visibility to be: " + isDisplayed + " but was: " + actual)
                .isEqualTo(isDisplayed);
    }

    @Override
    public void clickResultListTripWatcherModule() {
        new HotelResultsPage(getWebdriverInstance()).getSearchResultsFragment().clickTripWatcherSpeedBump();
    }

    @Override
    public void verifyTripWatcherLayerFromSpeedbump(boolean isFromSpeedbump) {
        boolean fromSpeedbump = new HotelResultsTripwatcherLayerFragment(getWebdriverInstance())
            .isTripWatcherLayerFromSpeedBump();
        assertThat(fromSpeedbump)
                .as("Expected layer from speedbump to be: " + isFromSpeedbump + " but was: " + fromSpeedbump)
                .isEqualTo(isFromSpeedbump);
    }

    @Override
    public void verifyDetailsPageFarefinder() {
        HotelDetailsPage detailsPage = new HotelDetailsPage(getWebdriverInstance());
        assertThat(detailsPage.isAllFarefindersPresent()).as("Set of farefinders isn't completed").isTrue();
        String windowHandler = getWebdriverInstance().getWindowHandle();
        String url = getWebdriverInstance().getCurrentUrl();
        getWebdriverInstance().manage().deleteAllCookies();
        detailsPage.addWindow();
        for (String winHandle : getWebdriverInstance().getWindowHandles()) {
            if (!winHandle.equals(windowHandler)) {
                getWebdriverInstance().switchTo().window(winHandle);
            }
        }
        getWebdriverInstance().navigate().to(url);
    }

    @Override
    public void watchThisTrip(String email) {
        throw new UnimplementedTestException("Implement Me!");
    }

    @Override
    public void getHotelReferenceNumberList() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setHotelSearchResultReferenceNumber() {
        searchParameters.setHotelSearchResultReference(new HotelResultsPage(getWebdriverInstance())
                .getTopCustomerCareReferenceNumber());
        logger.info("Reference number is:" + new HotelResultsPage(getWebdriverInstance())
                .getTopCustomerCareReferenceNumber());
    }

    @Override
    public void compareSearchResultReferenceNumber(String isTheSame) {
        logger.info("Reference number is:" + new HotelResultsPage(getWebdriverInstance())
                .getTopCustomerCareReferenceNumber());
        if (isTheSame.equals("have")) {
            assertThat(searchParameters.getHotelSearchResultReference())
                    .as("Search result reference is not the same")
                    .isEqualTo(new HotelResultsPage(getWebdriverInstance()).getTopCustomerCareReferenceNumber());
        }
        else {
            assertThat(searchParameters.getHotelSearchResultReference())
                    .as("Search result reference is not the same")
                    .doesNotMatch(new HotelResultsPage(getWebdriverInstance()).getTopCustomerCareReferenceNumber());
        }
    }

    @Override
    public void turnOnOffHotelInDB(boolean isOn) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHComPopupWindow(boolean hcomPopupDisplayed) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    // ROW and domestic
    public void verifyCallAndBookModule(String country) {
        String careHeader = new HotelResultsPage(getWebdriverInstance()).getTopCustomerCareHeader();
        String carePhone = new HotelResultsPage(getWebdriverInstance()).getCustomerCarePhoneNumber();
        String reference = new HotelResultsPage(getWebdriverInstance()).getTopCustomerCareReferenceNumber();
        if ("United Kingdom".equalsIgnoreCase(country)) {
            assertThat("Same deals, expert advice".equals(careHeader))
                    .as("Care header expected " + " but actual " + careHeader).isTrue();
            assertThat("0808 234 5903".equals(carePhone))
                    .as("Care phone number expected " + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Reference"))
                    .as("Reference text should be start with 'Reference'").isTrue();
        }
        else if ("Ireland".equalsIgnoreCase(country)) {
            assertThat("Same deals, expert advice".equals(careHeader))
                    .as("Care header expected " + " but actual " + careHeader).isTrue();
            assertThat("1 800 760738".equals(carePhone))
                    .as("Care phone number expected " + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Reference"))
                    .as("Reference text should be start with 'Reference'").isTrue();
        }
        else if ("Sverige".equalsIgnoreCase(country)) {
            assertThat("Samma erbjudanden, expertråd".equals(careHeader))
                    .as("Care header expected 'Samma erbjudanden, expertråd'" + " but actual " + careHeader).isTrue();
            assertThat("020 797 237".equals(carePhone))
                    .as("Care phone number expected '020 797 237'" + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Referens"))
                    .as("Reference text should be start with 'Referens'").isTrue();
        }
        else if ("Norge".equalsIgnoreCase(country)) {
            assertThat("De samme tilbudene, med ekspertråd".equals(careHeader))
                    .as("Care header expected 'De samme tilbudene, med ekspertråd'" + " but actual " + careHeader)
                    .isTrue();
            assertThat("80 01 72 28".equals(carePhone))
                    .as("Care phone number expected '80 01 72 28'" + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Referansenummer"))
                    .as("Reference text should be start with 'Referansenummer'").isTrue();
        }
        else if ("Danmark".equalsIgnoreCase(country)) {
            assertThat("Samme tilbud, ekspertrådgivning".equals(careHeader))
                    .as("Care header expected 'Samme tilbud, ekspertrådgivning'" + " but actual " + careHeader)
                    .isTrue();
            assertThat("80 25 00 72".equals(carePhone))
                    .as("Care phone number expected '80 25 00 72'" + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Reference")).as("Reference text should be start with 'Reference'")
                .isTrue();
        }
        else if ("Australia".equalsIgnoreCase(country)) {
            assertThat("Same deals, expert advice".equals(careHeader))
                    .as("Care header expected 'Same deals, expert advice'" + " but actual " + careHeader).isTrue();
            assertThat("1 800 306 217".equals(carePhone))
                    .as("Care phone number expected '1 800 306 217'" + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Reference")).as("Reference text should be start with 'Reference'")
                .isTrue();
        }
        else if ("New Zealand".equalsIgnoreCase(country)) {
            assertThat("Same deals, expert advice".equals(careHeader))
                    .as("Care header expected 'Same deals, expert advice'" + " but actual " + careHeader).isTrue();
            assertThat("0800 452 690".equals(carePhone))
                    .as("Care phone number expected '0800 452 690'" + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Reference"))
                    .as("Reference text should be start with 'Reference'").isTrue();
        }
        else if ("Deutschland".equalsIgnoreCase(country)) {
            assertThat("Dieselben Angebote, kompetente Beratung".equals(careHeader))
                    .as("Care header expected 'Dieselben Angebote, kompetente Beratung'" + " but actual " + careHeader)
                    .isTrue();
            assertThat("0800 723 5637".equals(carePhone))
                    .as("Care phone number expected '0800 723 5637'" + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Referenz")).as("Reference text should be start with 'Referenz'").isTrue();
        }
        else if ("Hong Kong".equalsIgnoreCase(country)) {
            assertThat("Same deals, expert advice".equals(careHeader))
                    .as("Care header expected 'Same deals, expert advice'" + " but actual " + careHeader).isTrue();
            assertThat("800 933 475".equals(carePhone))
                    .as("Care phone number expected '800 933 475'" + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Reference"))
                    .as("Reference text should be start with 'Reference'").isTrue();
        }
        else if ("Singapore".equalsIgnoreCase(country)) {
            assertThat("Same deals, expert advice".equals(careHeader))
                    .as("Care header expected 'Same deals, expert advice'" + " but actual " + careHeader).isTrue();
            assertThat("800 120 6272".equals(carePhone))
                    .as("Care phone number expected '800 120 6272'" + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Reference"))
                    .as("Reference text should be start with 'Reference'").isTrue();
        }
        else if ("México".equalsIgnoreCase(country) || "Mexico".equalsIgnoreCase(country)) {
            assertThat("Las mismas ofertas, consejos de expertos".equals(careHeader))
                    .as("Care header expected 'Las mismas ofertas, consejos de expertos'" + " but actual " + careHeader)
                    .isTrue();
            assertThat("001-8554643751".equals(carePhone))
                    .as("Care phone number expected '001-8554643751'" + " but actual " + carePhone).isTrue();
            assertThat(reference.startsWith("Referencia"))
                    .as("Reference text should be start with 'Referencia'").isTrue();
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void chooseResultsTypeCloseTripWatcher(String resultsType) {
        throw new UnimplementedTestException("Implement ME!");
    }

    @Override
    public void saveHotelSearchId() {
        hotelSearchParameters.setHotelSearchId(new HotelResultsPage(getWebdriverInstance()).getHotelSearchId());
        logger.info("Storing resolved hotel search ID: " + hotelSearchParameters.getHotelSearchId());
    }

    @Override
    public void clickHotelRoomAdultDdl() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void clickHotelRoomChildrenDdl() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyAdultNumberLimit(int maxAdults) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyChildrenNumberLimit(int maxChildren) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyNoErrorsOnPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmThatUrlContainsSearchTokenId(long tokenId) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseAmenitiesValue(String amenityName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareHotelResultsWithPartners(String partners, String numberOfModule) {
        throw new UnimplementedTestException("Implement me!");
    }
}
