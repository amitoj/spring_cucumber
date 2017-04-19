/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.hotel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.search.SearchParameters;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.transformer.jchronic.ChronicConverter;

/**
 * Step definitions to go through hotel search steps.
 */
public class HotelSearchSteps extends AbstractSteps {

    @Autowired
    @Qualifier("hotelSearchModel")
    private HotelSearchModel hotelSearchModel;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier("hotelSearchParameters")
    private HotelSearchParameters hotelSearchParameters;

    @Autowired
    private SearchParameters searchParameters;

    @Autowired
    @Qualifier("applicationModel")
    private ApplicationModel applicationModel;

    private void setDestinationLocation(String destinationLocation) {
        searchParameters = this.hotelSearchParameters.getGlobalSearchParameters();
        searchParameters.setDestinationLocation(destinationLocation);
    }

    @Given("^I should see Destination Page with no errors$")
    public void verifyNoErrorsOnPage() throws Throwable {
        // Express the Regexp above with the code you wish you had
        hotelSearchModel.verifyNoErrorsOnPage();
    }

    @Given("^I save the hotel search id$")
    public void saveHotelSearchId() {
        hotelSearchModel.saveHotelSearchId();
    }

    @Given("^I want to (enable|disable) Hotels.com search$")
    public void enableHComSearch(String state) {
        hotelSearchParameters.setEnableHComSearch(state.equals("enable") ? true : false);
    }

    @Then("there (will|will not) be a Hotels.com window popup")
    public void verifyHComPopupWindow(String state) throws InterruptedException {
        Thread.sleep(5000);
        hotelSearchModel.verifyHComPopupWindow(state.equals("will") ? true : false);
    }

    @Given("^I'm searching for a hotel in \"([^\"]*)\"$")
    public void setHotelDestinationLocation(String city) {
        searchParameters.setPGoodCode(PGoodCode.H);
        setDestinationLocation(city);
    }

    @Given("^I am searching for a hotel (?:in|near|at|by) \"([^\"]*)\"" +
        "\\s?(?:address|airport|city|geolocation|landmark|latitude and longitude|postal code)?$")
    public void searchByLocation(String location) {
        searchParameters.setPGoodCode(PGoodCode.H);
        setDestinationLocation(location);
    }

    @Given("^the tripwatcher layer (is|is not) activated$")
    public void setTripWatcherLayer(String state) {
        this.hotelSearchModel.turnOnTripWatcherLayer();
    }

    @Given("^I want (\\d+) room\\(s\\)$")
    public void setNumberOfHotelRooms(Integer numberOfHotelRooms) {
        this.hotelSearchParameters.setNumberOfHotelRooms(numberOfHotelRooms);
    }

    @Given("^I will be traveling with (\\d+) adults$")
    public void setNumberOfAdults(Integer numberOfAdults) {
        this.hotelSearchParameters.setNumberOfAdults(numberOfAdults);
    }

    @Given("^I will be traveling with (\\d+) children$")
    public void setNumberOfChildren(Integer numberOfChildren) {
        this.hotelSearchParameters.setNumberOfChildren(numberOfChildren);
    }

    @Given("^I would place adults and children as following:$")
    public void setPlacement(DataTable placement) {
        List<HotelRoomParameters> hotelRoomParametersList = this.hotelSearchParameters.getHotelRoomParameters();

        for (HotelRoomParameters hotelRoomParameters : hotelRoomParametersList) {
            this.hotelSearchParameters.removeHotelRoomParameter(hotelRoomParameters);
        }

        for (Map<String, String> map : placement.asMaps(String.class, String.class)) {
            HotelRoomParameters hrp = new HotelRoomParameters();
            hrp.setAdultsCount(Integer.parseInt(map.get("adultsCount")));
            hrp.setChildrenCount(Integer.parseInt(map.get("childrenCount")));
            this.hotelSearchParameters.setNumberOfAdults(Integer.parseInt(map.get("adultsCount")));
            this.hotelSearchParameters.setNumberOfChildren(Integer.parseInt(map.get("childrenCount")));
            List<Integer> childrenAges = new ArrayList<>();

            if (map.get("childrenAges").trim().length() > 0) {
                for (String childrenAge : map.get("childrenAges").replace(" ", "").split("(,|and)")) {
                    childrenAges.add(Integer.parseInt(childrenAge));
                }
            }

            hrp.setChildrenAges(childrenAges);
            this.hotelSearchParameters.addHotelRoomParameter(hrp);
        }
    }

    @Given("^I am a (family man|business traveler|student|family group traveler|shoe string budget traveler|" +
        "single parent|personal assistant)$")
    public void setTravelerInformation(String persona) {
        if ("family man".equalsIgnoreCase(persona)) {
            this.hotelSearchParameters.setNumberOfHotelRooms(1);
            this.hotelSearchParameters.setNumberOfAdults(2);
            this.hotelSearchParameters.setNumberOfChildren(2);
            // Would be ok with the default best value sort
        }
        else if ("business traveler".equalsIgnoreCase(persona)) {
            this.hotelSearchParameters.setNumberOfHotelRooms(1);
            this.hotelSearchParameters.setNumberOfAdults(2);
            this.hotelSearchParameters.setNumberOfChildren(0);
            // Would be ok with the default best value sort
        }
        else if ("student".equalsIgnoreCase(persona)) {
            this.hotelSearchParameters.setNumberOfHotelRooms(1);
            this.hotelSearchParameters.setNumberOfAdults(1);
            this.hotelSearchParameters.setNumberOfChildren(0);
        }
        else if ("family group traveler".equalsIgnoreCase(persona)) {
            this.hotelSearchParameters.setNumberOfHotelRooms(6);
            this.hotelSearchParameters.setNumberOfAdults(6);
            this.hotelSearchParameters.setNumberOfChildren(18);
            // Would sort by star rating and pick the highest star
        }
        else if ("shoe string budget traveler".equalsIgnoreCase(persona)) {
            this.hotelSearchParameters.setNumberOfHotelRooms(6);
            this.hotelSearchParameters.setNumberOfAdults(24);
            // Would sort by price and pick the cheapest hotel
        }
        else if ("single parent".equalsIgnoreCase(persona)) {
            this.hotelSearchParameters.setNumberOfHotelRooms(1);
            this.hotelSearchParameters.setNumberOfAdults(1);
            this.hotelSearchParameters.setNumberOfChildren(3);
            // Would sort by price and pick the cheapest hotel
        }
        else if ("personal assistant".equalsIgnoreCase(persona)) {
            this.hotelSearchParameters.setNumberOfHotelRooms(3);
            this.hotelSearchParameters.setNumberOfAdults(8);
            this.hotelSearchParameters.setNumberOfChildren(4);
            // Would have saved credit cards and a few saved travelers in their profile. This person is booking
            // travel for others
        }
    }

    @Then("^I see hotel results$")
    public void verifyHotelSearchResultsArePresent() {
        this.hotelSearchModel.verifyHotelSearchResultsPage();
    }

    @Given("^I see search results match search criteria$")
    public void verifySearchCriteria() throws Throwable {
        this.hotelSearchModel.verifySearchCriteria();
    }

    @Given("^I choose (opaque|retail) hotels tab on results")
    public void chooseHotelsResultsByType(String resultsType) {
        this.hotelSearchModel.chooseHotelsResultsByType(resultsType);
    }

    @Given("^I choose (opaque|retail) results getting rid of tripwatcher$")
    public void chooseResultsTypeCloseTripwatcher(String resultsType) {
        this.hotelSearchModel.chooseResultsTypeCloseTripWatcher(resultsType);
    }

    @Given("^I choose hotel result with price less (\\d+) and more (\\d+)$")
    public void chooseResultBetweenPrice(Integer lessPrice, Integer morePrice) {
        this.hotelSearchModel.chooseResultByPrice(lessPrice, morePrice);
    }

    @Given("^I check distances on hotel results$")
    public void checkDistancesOnResults() {
        this.hotelSearchModel.checkResultsDistance();
    }

    @Then("^I see the featured hotel in the results$")
    public void verifyRetailHeroIsPresent() {
        this.hotelSearchModel.verifyRetailHeroIsPresent();
    }

    @When("^I select the (.*) neighborhood with (.*) star rating and CRS (PMN|TMN|IMN|IMP|PBN|TBN|IBN|IBP|XMN|XBN)$")
    public void chooseHotelFromResults(String neighborhood, String starRating, String crs) {
        this.hotelSearchModel.chooseHotelFromResults(neighborhood, starRating, crs);
    }

    @Given("^The retail example on opaque results (is|is not) enabled$")
    public void activateRetailExamplesForOpaque(String enableExamples) {
        this.hotelSearchModel.activateRetailExamplesForOpaque(enableExamples.trim().equals("is"));
    }

    @Then("^I should[\\s]*(not)?[\\s]*see examples of retail hotels$")
    public void verifyVisibilityOfRetailExamples(String retailExamplesVisible) {
        boolean examplesShouldNotBeVisible = "not".equals(retailExamplesVisible);
        this.hotelSearchModel.verifyVisibilityOfRetailExamples(!examplesShouldNotBeVisible);
    }

    @When("^I want to see examples of retail hotels$")
    public void viewRetailHotelExamples() {
        this.hotelSearchModel.viewRetailHotelExamples();
    }

    @Then("^I should see hotel planner layer$")
    public void verifyHotelPlannerLayer() {
        this.hotelSearchModel.verifyMediaPlannerLayerIsShown();
    }

    @Given("^I want to access the activities and services tab in Hotel billing page$")
    public void showActivateServicesTab() {
        this.hotelSearchModel.showActivateServicesTab();
    }

    @Then("^I see a (empty destination|invalid character|invalid destination|minimum length)" +
        " validation error on (city)$")
    public void verifyErrorOnCityField(String validationError, String field) {
        this.hotelSearchModel.verifyValidationErrorOnField(validationError, field);
    }

    @Then("^I see (unknown destination|children age|empty destination) validation error on (location|children age)$")
    public void verifyErrorOnFieldRow(String validationError, String field) {
        this.hotelSearchModel.verifyValidationErrorOnField(validationError, field);
    }

    @Then("^I see a (invalid check-out|one night reservation|invalid drop-off|invalid car time)" +
        " validation error on (date|time)$")
    public void verifyErrorOnDateTimeField(String validationError, String dateTime) {
        this.hotelSearchModel.verifyValidationErrorOnField(validationError, dateTime);
    }

    @When("^I click see all results link$")
    public void clickSeeAllResultsLink() {
        this.hotelSearchModel.clickSeeAllResultsLink();
    }

    @Given("^I want to append query parameter (.*) to the URL$")
    public void appendQueryParameterToUrl(String queryParameter) {
        this.hotelSearchModel.appendQueryParameterToUrl(queryParameter);
    }

    @When("^I click activities banner on hotel confirmation page$")
    public void clickOnActivitiesBanner() {
        this.hotelSearchModel.clickBanner();
    }

    @Then("^I will see activities results page in a new window with correct hotel search params$")
    public void verifyActivitiesPage() {
        String destinationCity = this.hotelSearchParameters.getGlobalSearchParameters().getDestinationLocation();
        Date startDate = this.hotelSearchParameters.getGlobalSearchParameters().getStartDate();
        Date endDate = this.hotelSearchParameters.getGlobalSearchParameters().getEndDate();
        this.hotelSearchModel.verifyActivitiesPage(destinationCity, endDate, startDate);
    }

    @Given("^I am looking for a hotel$")
    public void launchSearch() {
        searchParameters.setPGoodCode(PGoodCode.H);
        this.hotelSearchModel.launchSearch();
    }

    @Then("^I should see a hotel MFI layer$")
    public void validateMfiLayer() {
        this.hotelSearchModel.verifyMFI(this.hotelSearchParameters);
    }

    @Then("^I choose (KAYAK|EXPEDIA|PRICELINE|ORBITZ|HOTELS) partner$")
    public void choosePartner(String partner) {
        this.hotelSearchModel.choosePartner(partner);
    }

    @When("^I launch hotel partners search$")
    public void launchMfiLayerSearch() {
        this.hotelSearchModel.launchMFIPartnerSearch();
    }

    @Then("^Subscription layer is displayed for (KAYAK|EXPEDIA|PRICELINE|ORBITZ|HOTELS)$")
    public void verifyPartnersSearch(String partnerName) {
        this.hotelSearchModel.verifyPartnersSearch(this.hotelSearchParameters, partnerName);
    }

    @Then("^I verify next hotel partners \"(.*)\"$")
    public void verifyHotelPartners(String partnersName) {
        for (String name : partnersName.split(",")) {
            this.hotelSearchModel.verifyPartnersSearch(this.hotelSearchParameters, name);
        }
    }

    @Then("^verify (KAYAK|EXPEDIA|PRICELINE|ORBITZ|HOTELS) partner search record in DB from MFI$")
    public void verifyPartnerSearchInDB(String partnerName) {
        hotelSearchModel.verifyPartnersSearchInDB(partnerName);
    }

    @Then("^I check partners \"(.*)\" in click tracking table in DB$")
    public void verifyPartnersSearchInDB(String partnersName) {
        for (String partner : partnersName.split(",")) {
            hotelSearchModel.verifyPartnersSearchInDB(partner);
        }
    }

    @Then("^I should get (mapped|list) results view$")
    public void shouldGetResultsView(String viewType) {
        this.hotelSearchModel.verifyHotelResultsPageView(viewType);
    }

    @Then("^I land on (opaque|retail)(?:\\sinternational\\s|\\s)details page")
    public void getInternationalDetailsPage(String pageType) {
        this.hotelSearchModel.verifyHotelDetailsPage(pageType);
    }

    @Then("I see price (all inclusive|per room per day) at (mapped|list) results")
    public void shouldSeeSpecificPriceTypeAtResults(String priceType, String resultsType) {
        this.hotelSearchModel.verifyAllInclusivePriceAtResults(priceType, resultsType);
    }

    @Then("I see price all inclusive at details")
    public void shouldSeeSpecificPriceTypeAtDetails() {
        this.hotelSearchModel.verifyAllInclusivePriceAtDetails();
    }

    @When("^I notice and select solution with Hotwire customer reviews$")
    public void chooseCustomerRecommendedSolution() {
        hotelSearchModel.chooseCustomerRecommendedSolution();
    }

    @Then("^I see Hotwire customer reviews are the same I noticed$")
    public void verifyCustomerRating() {
        hotelSearchModel.verifyCustomerRating(hotelSearchParameters.getSelectedSearchSolution().getRating());
    }

    @When("^I choose \"([^\"]*)\" hotel$")
    public void selectHotelByName(final String hotelName) {
        hotelSearchModel.selectHotelByName(hotelName);
    }

    @Then("^price is the same I noticed on results$")
    public void verifyPrice() {
        hotelSearchModel.verifyPrice(hotelSearchParameters.getSelectedSearchSolution().getPrice());
    }

    @Then("^results are sorted by (Distance|Best Value)$")
    public void verifySortByResults(String sortCriteria) {
        this.hotelSearchModel.verifySortByResults(HotelResultsSortCriteria.fromString(sortCriteria), null);
    }

    @And("^I see expected cross-sell deal")
    public void verifyCrossSellDealData() {
        hotelSearchModel.verifyCrossSellData(hotelSearchParameters.getSelectedSearchSolution());
    }

    @Then("^I see the discount information on the hotel search$")
    public void verifyDiscountBanner() throws Throwable {
        hotelSearchModel.verifyDiscountBannerOnFF();
    }

    @Then("^I get (retail|opaque) deal as a search winner$")
    public void verifySearchWinnerDeal(String dealType) {
        hotelSearchModel.verifySearchWinnerDeal(dealType, hotelSearchParameters.getSelectedSearchSolution());
    }

    @Then("^I see the discount information on the hotel results$")
    public void verifyDiscountBannerOnHotelResults() throws Throwable {
        hotelSearchModel.verifyDiscountBannerOnHotelResults();
    }

    @Then("^I should see \"([^\"]*)\" label for opaque hotels on (list|mapped) results$")
    public void verifyOpaqueHotelsLabel(String label, String resultsType) {
        hotelSearchModel.verifyOpaqueHotelsLabel(label, resultsType);
    }

    @Then("^I see customer care phone number above the (search form|list|map|details) is \"([^\"]*)\"$")
    public void verifyCustomerCarePhoneNumber(String page, String phoneNumber) {
        hotelSearchModel.verifyCustomerCarePhoneNumber(page, phoneNumber);
    }

    @When("^I start hotel search without specifying the destination$")
    public void startHotelSearchWithoutSpecifyingDestination() {
        searchParameters.setPGoodCode(PGoodCode.H);
        hotelSearchModel.clickSearch();
    }

    @Then("^The FareFinder default adult value should be (\\d+)$")
    public void defaultFFAdultsValue(Integer iAdults) {
        hotelSearchModel.checkFareFinderDefaultAdultsValue(iAdults);
    }

    @Then("^The FareFinder default children value should be (\\d+)$")
    public void defaultFFChildrenValue(Integer iChildren) {
        hotelSearchModel.checkFareFinderDefaultChildrenValue(iChildren);
    }

    @Then("^I (see|do not see) savings layer$")
    public void verifySavingsLayer(String state) {
        hotelSearchModel.verifySavingsLayer("see".equalsIgnoreCase(state));
    }

    @When("^I change search$")
    public void changeHotelSearch() {
        hotelSearchModel.changeHotelSearch();
    }

    @Then("^I should see the hotel landing page with previous search destination$")
    public void verifyHotelSearchDetails() {
        hotelSearchModel.verifyHotelSearchDetails();
    }

    @Given("^I visit (results|details|billing-onepage|billing-entry|billing-payment)" +
        " page on (hotel|car) with invalid session parameters")
    public void redirectToUrl(String page, String vertical) {
        if (page.equals("results")) {
            this.applicationModel.setupURL_ToVisit(vertical + "/results?searchTokenId=1");
        }
        else if (page.equals("details")) {
            String detailsUrl = vertical + "/detail?searchId=1&solutionId=1";
            if (vertical.equals("car")) {
                detailsUrl += "&pGoodId=1";
                detailsUrl = detailsUrl.replace("detail", "details");
            }
            this.applicationModel.setupURL_ToVisit(detailsUrl);
        }
    }

    @Then("^I'm redirected to the homepage with session timeout message$")
    public void verifyHomepageWithMessage() {
        this.applicationModel.verifyHomePage("mobile");
        this.applicationModel.verifyHomepageHasTimeoutMessage();
    }

    @Then("^I should see corresponding fare finder destination")
    public void verifyFareFinderDestination() {
        hotelSearchModel.verifyFareFinderDestination(hotelSearchParameters.getDestination());
    }

    @Then("^I should see term (facilities|amenities) used$")
    public void verifyFacilitiesTermUsage(String term) {
        hotelSearchModel.verifyFacilitiesTermUsage(term);
    }

    @Then("^I should see room photos for an opaque hotel$")
    public void verifySemiOpaqueRoomPhotos() {
        hotelSearchModel.verifySemiOpaqueRoomPhotos();
    }

    @Then("^I get (opaque|retail) results$")
    public void verifyResultsPageType(String resultsType) {
        hotelSearchModel.verifyResultsPageType(resultsType);
    }

    @Then("^the hotel neighborhood is displayed$")
    public void verifyHotelNeighborhood() {
        hotelSearchModel.verifyHotelNeighborhood();
    }

    @When("^I put mouse over POI icon$")
    public void displayMapPopup() {
        hotelSearchModel.displayMapPopup();
    }

    @Then("^I see POI description$")
    public void verifyMapPopup() {
        hotelSearchModel.verifyMapPopup();
    }

    @Then("^no room photos available$")
    public void verifyNoRoomPhotos() {
        hotelSearchModel.verifyNoRoomPhotos();
    }

    @And("^I return back to search results using link at the (.*) of the page$")
    public void returnToSearchResults(String position) {
        hotelSearchModel.returnToSearchResults(position);
    }

    @When("^I send a link to another user$")
    public void removeSessionCookieAndReloadPage() {
        hotelSearchModel.removeSessionCookieAndReloadPage();
    }

    @Then("^the user does not see back to result links$")
    public void verifyBackToResultsLink() {
        hotelSearchModel.verifyBackToResultsLink();
    }

    @When("^I see Low Price Guarantee offer$")
    public void verifyLowPriceGuaranteeOffer() {
        hotelSearchModel.verifyLowPriceGuaranteeOffer();
    }

    @Then("^I see Know Before You Go notice$")
    public void verifyKnowBeforeYouGoNotice() {
        hotelSearchModel.verifyKnowBeforeYouGoNotice();
    }

    @Given("^the details page fare finder (is|is not) expanded$")
    public void verifyDetailsFareFinderState(String state) {
        hotelSearchModel.verifyDetailsFareFinderState(state.trim().equals("is"));
    }

    @When("^I (expand|collapse) the details page fare finder$")
    public void expandDetailsPageFareFinder(String mode) {
        if (mode.trim().equals("expand")) {
            hotelSearchModel.expandDetailsPageFareFinder();
        }
        else {
            hotelSearchModel.collapseDetailsPageFareFinder();
        }
    }

    @Then("^I get the details page travelling between (.*) and (.*)$")
    public void verifyDetailsPageFromDetailsPageSearch(@Transform(ChronicConverter.class) Calendar start,
        @Transform(ChronicConverter.class) Calendar end) {
        hotelSearchModel.verifyIntlDetailsPageFromIntlDetailsPageSearch(start.getTime(), end.getTime());
    }

    @Then("^I will see (opaque|retail|search) results page$")
    public void verifySearchResultsPage(String pageType) {
        hotelSearchModel.verifySearchResultsPage(pageType.trim());
    }

    @Then("^There will be results$")
    public void verifyResultsPageHasSolutions() {
        hotelSearchModel.verifyResultsPageHasSolutions();
    }

    @Then("^I remember search solutions list$")
    public void rememberSearchResultsList() {
        hotelSearchModel.rememberSearchSolutionsList();
    }

    @When("^I get hotel reference\\(display\\) numbers list$")
    public void getHotelReferenceNumberList() {
        hotelSearchModel.getHotelReferenceNumberList();
    }

    @When("^I note hotel search result reference number$")
    public void setHotelSearchResultReferenceNumber() {
        hotelSearchModel.setHotelSearchResultReferenceNumber();
    }

    @Then("^The search result (have|have not) the same reference number$")
    public void compareSearchResultRefernceNumber(String isTheSame) {
        hotelSearchModel.compareSearchResultReferenceNumber(isTheSame);
    }

    @When("^I set hotel (inactive|active) in DB$")
    public void turnOnOffHotel(String isOn) {
        if (isOn.contains("in")) {
            hotelSearchModel.turnOnOffHotelInDB(false);
        }
        else {
            hotelSearchModel.turnOnOffHotelInDB(true);
        }

    }

    @Given("^I set hotel dates between (.*) and (.*)$")
    public void hitSearchDates(@Transform(ChronicConverter.class) Calendar start,
        @Transform(ChronicConverter.class) Calendar end) {

        hotelSearchModel.setDates(start.getTime(), end.getTime());
    }

    @Given("^I type hotel dates between (.*) and (.*)$")
    public void typeSearchDates(@Transform(ChronicConverter.class) Calendar start,
        @Transform(ChronicConverter.class) Calendar end) {

        hotelSearchModel.typeDates(start.getTime(), end.getTime());
    }

    @Then("^I do a hotel search from result page$")
    public void doHotelSearchFromResult() {
        hotelSearchModel.changeHotelSearch();
    }

    @Then("^The search results (should|should not) be the same like previous$")
    public void doCompareSearchResults(String isTheSame) {
        hotelSearchModel.compareSearchResultsWithPrevious(isTheSame.trim().equals("should"));
    }

    @Then("^I should see hotel results for \"([^\"]*)\" location$")
    public void checkHotelResultsDestination(String location) {
        hotelSearchModel.verifyLocationOnResultsPage(location);
    }

    @Then("^I see subscription layer and do subscribe$")
    public void doSubscribeOnResultPage() {
        hotelSearchModel.doSubscribeOnResultPage();
    }

    @And("^I verify that number of adults couldn't be less number of rooms on UHP$")
    public void verifyCorrespondenceBetweenAdultsAndRooms() {
        hotelSearchModel.verifyCorrespondenceBetweenAdultsAndRooms();
    }

    @Then("^I verify default hotel neighborhoods values in DB$")
    public void verifyNeighborhoodsInDB() {
        hotelSearchModel.verifyNeighborhoodsInDB();
    }

    @Then("^I verify CHAIN_NAME from hotel_chain has correct values in DB$")
    public void verifyChainNamesInDB() {
        hotelSearchModel.verifyChainNamesInDB();
    }

    @Then("^I will see the same (USD|CAD|CHF|EUR|NZD|AUD|GBP|DKK|NOK|SEK|HKD|MXN|SGD) currency$")
    public void verifyResultsCurrency(String currency) {
        hotelSearchModel.verifyResultsCurrency(currency);
    }

    @Given("^I want to filter results by (Price|Star rating|Amenities|Facilities|Areas)$")
    public void selectFilteringTab(String filterName) {
        hotelSearchModel.selectFilteringTab(filterName.trim());
    }

    @Given("^I choose the recommended (Price|Star rating|Amenities|Facilities|Areas|Accessibility) filter value$")
    public void chooseRecommendedFilterValue(String filterName) {
        HotelResultsFilteringEnum filterEnum = HotelResultsFilteringEnum.PRICE;
        if (filterName.trim().equals("Star rating")) {
            filterEnum = HotelResultsFilteringEnum.STAR_RATING;
        }
        else if (filterName.trim().equals("Amenities") || filterName.trim().equals("Facilities")) {
            filterEnum = HotelResultsFilteringEnum.AMENITIES;
        }
        else if (filterName.trim().equals("Areas")) {
            filterEnum = HotelResultsFilteringEnum.AREAS;
        }
        else if (filterName.trim().equals("Accessibility")) {
            filterEnum = HotelResultsFilteringEnum.ACCESSIBILITY;
        }
        hotelSearchModel.chooseRecommendedFilterValue(filterEnum);
    }

    @When("^I click the comment card link$")
    public void clickCommentCardLink() {
        hotelSearchModel.clickCommentCardLink();
    }

    @When("^I get search results$")
    public void getSearchResult() {
        hotelSearchModel.getSearchResults();
    }

    @When("^I submit my comment$")
    public void typeAndSubmitCommentCard() {
        hotelSearchModel.typeAndSubmitCommentCard();
    }

    @When("^I choose sort by (Star rating|Price|Distance\\s*)(\\sfrom high to low|\\sfrom low to high)?$")
    public void sortResultsBy(String sortBy, String orderBy) {
        String selectBoxItem = (StringUtils.isEmpty(orderBy)) ? sortBy.trim() : sortBy.trim() + " (" +
            orderBy.replaceAll("from", "").trim() + ")";
        hotelSearchModel.sortResultsBy(selectBoxItem);
    }

    @Then("^I will see results sorted by (Star rating|Popular|" +
        "Price|Distance\\s*)(\\sfrom high to low|\\sfrom low to high)?$")
    public void verifySortByResults(String sortBy, String orderBy) {
        if (sortBy.trim().equals("Distance")) {
            orderBy = "near to far";
        }
        hotelSearchModel.verifySortByResults(
            HotelResultsSortCriteria.fromString(sortBy.trim()),
            HotelResultsSortOrderBy.fromString(StringUtils.isEmpty(orderBy) ? "low to high" : orderBy.replaceAll(
                "from", "").trim()));
    }

    @Then("^the comment card will (appear|disappear)$")
    public void verifyCommentCard(String visibility) {
        if (visibility.trim().equals("disappear")) {
            hotelSearchModel.verifyCommentCardGone();
        }
        else {
            hotelSearchModel.verifyCommentCardPopup();
        }
    }

    @Then("^distance (will|will not) be seen in search results$")
    public void verifyDistanceText(String state) {
        boolean isSeen = (state.trim().equals("will")) ? true : false;
        hotelSearchModel.verifyDistanceTextInResults(isSeen);
    }

    @When("^I go back to the previous page$")
    public void goBackToPreviousPage() {
        hotelSearchModel.goBackToPreviousPage();
    }

    @Then("^I (will|will not) see the trip watcher layer in the (opaque|retail) results$")
    public void verifyTripWatcherLayerInResults(String state, String resultsType) {
        hotelSearchModel.verifyTripWatcherLayerInResults(state.equals("will") ? true : false,
            resultsType.contains("opaque") ? true : false);
    }

    @Then("^The dare to compare module will exist$")
    public void verifyDareToCompareExists() {
        hotelSearchModel.verifyDareToCompareExists();
    }

    @When("^I compare hotel results with next partners \"(.*)\" for (first|second) D2C module$")
    public void compareHotelResultsWithPartners(String partners, String numberOfModule) {
        hotelSearchModel.compareHotelResultsWithPartners(partners, numberOfModule);
    }

    // Do only amenities and areas as they are checkboxes and only checkboxes can be unselected
    @When("^I unselect the recommended (Amenities|Areas) filter value$")
    public void unselectRecommendedFilterCheckbox(String filterName) {
        HotelResultsFilteringEnum filterEnum =
            (filterName.trim().equals("Amenities")) ? HotelResultsFilteringEnum.AMENITIES :
             HotelResultsFilteringEnum.AREAS;
        hotelSearchModel.unselectRecommendedFilterCheckbox(filterEnum);
    }

    @Then("^I see LPG layer$")
    public void verifyLPGLayer() {
        hotelSearchModel.verifyLPGLayer();
    }

    @Then("^I see cancellation policy panel$")
    public void verifyCancellationPolicyPanel() {
        hotelSearchModel.verifyCancellationPolicyPanel();
    }

    @Then("^I see what trip advisor layer$")
    public void verifyTripAdvisorLayer() {
        hotelSearchModel.verifyTripAdvisorLayer();
    }

    @Then("^I see features list$")
    public void verifyFeaturesList() {
        hotelSearchModel.verifyFeaturesList();
    }

    @Then("^I see location description text$")
    public void verifyLocationDescText() {
        hotelSearchModel.verifyLocationDesc();
    }

    // Add more to the capture string by appending |<value> between the parenthesis. -> "(foo|bar)"
    @When("^I filter hotels with (Free Breakfast|Free Parking|Casino|Tennis Nearby)$")
    public void checkHotelFeatureCheckbox(String feature) {
        hotelSearchModel.checkHotelFeatureCheckbox(feature);
    }

    // Add more to the capture string by appending |<value> between the parenthesis. -> "(foo|bar)"
    @Then("^I will see that all results will have" +
        " (Free Breakfast|Free Parking|the recommended Accessibility filter value)$")
    public void verifyHotelFeatureInResults(String feature) {
        if (!feature.equals("the recommended Accessibility filter value")) {
            hotelSearchModel.verifyHotelFeatureInResults(feature);
        }
        else {
            hotelSearchModel.verifyRecommendedAccessibilityInResults();
        }
    }

    // Add more to the capture string by appending |<value> between the parenthesis. -> "(foo|bar)"
    @Then("^I will see (Free Breakfast) highlighted in the hotel details page$")
    public void verifyFreeAmenityInDetailsPage(String feature) {
        hotelSearchModel.verifyFreeAmenityInDetailsPage(feature);
    }

    // Only do star rating and price as these are ranges and not specific like areas or amenities.
    @Then("^I will see only the recommended (Price|Star rating) filter in the hotel results$")
    public void verifyRecommendedRangeFromFiltersInResults(String filterType) {
        HotelResultsFilteringEnum resultsFilterEnum =
            (filterType.trim().equals("Price")) ? HotelResultsFilteringEnum.PRICE :
            HotelResultsFilteringEnum.STAR_RATING;
        hotelSearchModel.verifyRecommendedRangeFromFiltersInResults(resultsFilterEnum);
    }

    @Then("^I will see that all results will be of the recommended area$")
    public void verifyRecommendedAreaFilterInResults() {
        hotelSearchModel.verifyRecommendedAreaFilterInResults();
    }

    @Then("^the \"All areas\" checkbox (will|will not) be selected$")
    public void verifyAllAreasCheckboxSelectedState(String state) {
        hotelSearchModel.verifyAllAreasCheckboxSelectedState((state.trim().equals("will")) ? true : false);
    }

    @When("^I click Reset filters$")
    public void clickResetFilters() {
        hotelSearchModel.clickResetFilters();
    }

    @When("^I get the filtered search results$")
    public void getFilteredHotelSearchResults() {
        hotelSearchModel.getFilteredHotelSearchResults();
    }

    @Then("^Reset filters (will|will not) be seen$")
    public void verifyResetFiltersDisplayed(String state) {
        hotelSearchModel.verifyResetFiltersDisplayed((state.trim().equals("will")) ? true : false);
    }

    @Then("^search results are not filtered$")
    public void verifyResultsNotFiltered() {
        hotelSearchModel.verifyResultsNotFiltered();
    }

    @Then("^I will see here's your deal in the opaque hotel results page$")
    public void verifyDbmLink() {
        hotelSearchModel.verifyDbmLink();
    }

    @Then("^I (should|should not) see examples of retail hotels in the hotel results$")
    public void verifyVisibilityOfResultsRetailExamples(String state) {
        boolean retailExamplesShouldBeVisible = (state.trim().equals("should")) ? true : false;
        hotelSearchModel.verifyVisibilityOfResultsRetailExamples(retailExamplesShouldBeVisible);
    }

    @Then("^the map will be rendered correctly in the results$")
    public void verifyHotelResultsMapRenderedCorrectly() {
        hotelSearchModel.verifyHotelResultsMapRenderedCorrectly();
    }

    @Then("^The reference module will be displayed in the results$")
    public void verifyReferencPriceModuleDisplayed() {
        hotelSearchModel.verifyReferencPriceModuleDisplayed();
    }

    @Then("^The reference price will be the same as the first" + " result after clicking the reference price module$")
    public void verifyReferencePriceModuleValue() {
        hotelSearchModel.verifyReferencePriceModuleValue();
    }

    @Then("^I (will|will not) see trip advisor ratings in search results$")
    public void verifyTripAdvisorInResults(String state) {
        hotelSearchModel.verifyTripAdvisorInResults(state.equals("will") ? true : false);
    }

    @Then("^the trip watcher layer (will|will not) be from the speedbump$")
    public void verifyTripWatcherLayerFromSpeedbump(String state) {
        hotelSearchModel.verifyTripWatcherLayerFromSpeedbump(state.trim().equals("will") ? true : false);
    }

    @Then("^I (will|will not) see the tripwatcher module in the results list$")
    public void verifyTripWatcherModuleInHotelResultsList(String state) {
        hotelSearchModel.verifyTripWatcherModuleInHotelResultsList(state.trim().equals("will") ? true : false);
    }

    @When("^I click the tripwatcher module in the results list$")
    public void clickResultListTripWatcherModule() {
        hotelSearchModel.clickResultListTripWatcherModule();
    }

    @Then("^I want to watch this trip for(\\scurrent|empty|.*) email on results$")
    public void watchThisTrip(String email) {
        if (email.trim().equals("current")) {
            email = authenticationParameters.getUsername();
            System.out.println(email);
        }
        hotelSearchModel.watchThisTrip(email);
    }

    @Given("^I click on room adults menu$")
    public void clickRoomAdultsButton() throws Throwable {
        hotelSearchModel.clickHotelRoomAdultDdl();
    }

    @Given("^I click on room children menu$")
    public void clickRoomChildrenButton() throws Throwable {
        hotelSearchModel.clickHotelRoomChildrenDdl();
    }

    @Then("^no more than (\\d+) adults can be chosen from room menu$")
    public void verifyLimitOfAdultsToBeChosenFromRoomMenu(int maxAdults) throws Throwable {
        hotelSearchModel.verifyAdultNumberLimit(maxAdults);
    }

    @Then("^no more than (\\d+) children can be chosen from room menu$")
    public void verifyLimitOfChildrenToBeChosenFromRoomMenu(int maxChildren) throws Throwable {
        hotelSearchModel.verifyChildrenNumberLimit(maxChildren);
    }

    @Given("^I choose the amenity: \"([^\"]*)\"$")
    public void chooseAmenitiesValue(String amenityName) {
        hotelSearchModel.chooseAmenitiesValue(amenityName);
    }

    @Then ("I verify the \"([^\"]*)\" hotel amenity in results after filtering$")
    public void verifyAmenityInResults(String amenityName) {
        hotelSearchModel.verifyHotelFeatureInResults(amenityName);
    }

    @Then ("I confirm the search takes place since url contains searchTokenId as (\\d+)")
    public void confirmThatUrlContainsSearchTokenId(long tokenId) {
        hotelSearchModel.confirmThatUrlContainsSearchTokenId(tokenId);
    }
}
