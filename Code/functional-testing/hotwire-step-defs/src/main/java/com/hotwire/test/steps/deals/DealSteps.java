/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.deals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.search.hotel.HotelSearchParameters;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Deals steps.
 */
public class DealSteps extends AbstractSteps {

    @Autowired
    @Qualifier("dealsModel")
    private DealsModel dealsModel;

    @Autowired
    @Qualifier("hotelSearchParameters")
    private HotelSearchParameters hotelSearchParameters;

    @Given("^I navigate to an?(\\sinventory restricted)?\\s+(expedia|hotwire|ean) " +
            "opm deal and opm (is|is not) enabled$")
    public void navigateToOpmEnablerDeal(String noInventory, String provider, String useOpm) {
        boolean testOpm = (useOpm.trim().equals("is")) ? true : false;
        String inventoryTypeLink =
                (noInventory == null || noInventory.trim().length() == 0) ? "inventory" : "no inventory";
        dealsModel.navigateToOpmEnablerDeal(provider, inventoryTypeLink, testOpm);
    }

    @Given("^I view a specific (hotel|car) deal for a (city|neighborhood) from a (international )?marketing email$")
    public void goToMarketingLinks(String vertical, String location, String channel) {
        if ("international".equalsIgnoreCase(channel)) {
            // This is a international super page link
            dealsModel.goToSuperPageLink(vertical, channel);
        }
        else {
            if ("city".equalsIgnoreCase(location)) {
                // This is a super page
                dealsModel.goToSuperPageLink(vertical, null);
            }
            else if ("neighborhood".equalsIgnoreCase(location)) {
                // this is a DBM link
                dealsModel.goToDBMLink(vertical);
            }
            else {
                throw new UnsupportedOperationException("Invalid category of marketing link :" + location);
            }
        }
    }

    @Given("^I view a (hotel) (neighborhood) deal from a marketing email$")
    public void goToSandboxMarketingLink(String vertical, String location) {
        String channel = (location.equals("neighborhood")) ?
                "dbm sandbox" : null;
        if (channel != null) {
            dealsModel.goToDBMLink(vertical, channel);
        }
        else {
            throw new PendingException("Unknown location/channel.");
        }
    }

    @Given("^I want to find (air|hotel|car) deals at a destination for given dates from a marketing email$")
    public void goToSearchOptionsLink(String vertical) {
        dealsModel.goToSearchOptionsLink(vertical);
    }

    @Given("^I want to find (air|hotel|car) cross sell deals at a destination" +
            " for given dates from a confirmation email$")
    public void goToCrossSellLink(String vertical) {
        dealsModel.goToCrossSellLink(vertical);
    }

    @Given("^I want to find (flight|hotel|car|vacation package) deals from landing page$")
    public void findDealsFromLandingPage(String vertical) {
        dealsModel.findDealsFromLandingPage(vertical);
    }

    @Given("^I will see the hotel deals module on the landing page$")
    public void verifyDealsModuleIsDisplayed() {
        dealsModel.verifyDealsModuleIsDisplayed();
    }

    @Given("^I navigate to (flight|hotel|car|vacations|deals) deals from footer$")
    public void navigateToDealsFromFooter(String vertical) {
        dealsModel.navigateToDealsFromFooter(vertical);
    }

    @Then("^I will see a (retail detailed quote|opaque detailed quote|inventory error)$")
    public void verifyPage(String pageType) {
        if (pageType.trim().contains("detailed quote")) {
            boolean shouldBeOpaque = (pageType.split(" ")[0].equals("opaque")) ? true : false;
            dealsModel.verifyHotelDetails(shouldBeOpaque);
        }
        else if (pageType.trim().equals("inventory error")) {
            dealsModel.verifyInventoryErrorDisplayed();
        }
    }

    /*  Remove this method when winner of cross sell deal happens. */
    @Given("^The(\\snew)?\\s+opaque cross sell deal is activated$")
    public void activateNewOpaqueCrossSell(String isNewCrossSell) {
        boolean doNewCrossSell =
                (isNewCrossSell == null || isNewCrossSell.trim().length() == 0) ? false : true;
        dealsModel.activateNewOpaqueCrossSell(doNewCrossSell);
    }

    @When("^I see the cross sell deal in search results$")
    public void verifyCrossSellInSearchResults() {
        dealsModel.verifyCrossSellInSearchResults();
    }

    @When("^I navigate to the cross sell deal from search results$")
    public void navigateToCrossSellDeal() {
        dealsModel.navigateToCrossSellDeal();
    }

    /*  Remove (\\snew)?\\s+ and fix method sig when winner of cross sell deal happens. */
    @Then("^I will see the[\\s]*(new)?[\\s]*(opaque|retail)?[\\s]*cross sell deal details$")
    public void verifyCrossSellLandingPage(String isNewCrossSell, String expectedMarket) {
        boolean doNewCrossSell =
                (isNewCrossSell == null || isNewCrossSell.trim().length() == 0) ? false : true;
        dealsModel.verifyCrossSellDetails(doNewCrossSell, expectedMarket);
    }

    @When("^I access (Last minute|Airport Deal) Hotels in \"([^\"]*)\"$")
    public void navigateToLastMinuteHotels(String dealType, String topDestination) {
        dealsModel.navigateToLastMinuteHotels(dealType, topDestination);
    }

    @When("^I access Secret Hot Rate Hotels in \"([^\"]*)\"$")
    public void navigateToSecretHotRateHotels(String topDestination) {
        dealsModel.navigateToSecretHotRateHotels(topDestination);
    }

    @Then("^the (Last minute|Airport Deal) Hotels are available$")
    public void verifyMarketingDealHotels(String dealType) {
        dealsModel.verifyMarketingDealHotels(dealType);
    }

    @Then("^The Secret Hot Rate Hotels are available$")
    public void verifySecretHotRateHotels() {
        dealsModel.verifySecretHotRateHotels();
    }

    @Then("^I see hotel with (custom|default) tagline$")
    public void shouldSeeOpaqueWithParticularTagline(String taglineType) {
        dealsModel.verifyOpaqueTaglines(taglineType);
    }

    @Then("^I will see the (hotel|car|air|vacation package) deal from the landing page$")
    public void verifyDealFromLandingPage(String vertical) {
        if (vertical.equals("hotel")) {
            dealsModel.verifyHotelDealFromLandingPage();
        }
        else if (vertical.equals("car")) {
            dealsModel.verifyCarDealFromLandingPage();
        }
        else if (vertical.equals("air")) {
            dealsModel.verifyAirDealFromLandingPage();
        }
        else if (vertical.equals("vacation package")) {
            dealsModel.verifyVacationDealFromLandingPage();
        }
    }

    @When("^I select (first|second) cross-sell deal on details$")
    public void selectDetailsCrossSell(String dealNumber) {
        int dealIndex = (dealNumber.equalsIgnoreCase("first")) ? 0 : 1;
        dealsModel.selectDetailsCrossSell(dealIndex);
    }

    @When("^I select a top destination city$")
    public void selectTopDestination() {
        dealsModel.selectTopDestinationCity();
    }

    @When("^I select (airport|landmark) on the landing page$")
    public void selectSuggestedDestinationFromLandingPage(String destinationType) {
        dealsModel.selectSuggestedDestination(destinationType);
    }

    @And("^access destination popular hotel")
    public void accessDestinationPopularHotel() {
        dealsModel.accessDestinationPopularHotel();
    }

    @Then("^I should get corresponding hotel landing page$")
    public void getHotelLandingPage() {
        dealsModel.verifyHotelLAndingPage(hotelSearchParameters.getSelectedSearchSolution());
    }

    @Then("^I verify the (Hotel|Car|Flight) radiobutton is checked$")
    public void verifyFareFiderRadiobutton(String vertical) {
        dealsModel.verifyFarefiderOnDealsPage(vertical);
    }

    @Then("^I navigate to discount international (flights|hotels|cars)$")
    public void navigateToDiscountInternational(String vertical) {
        dealsModel.navigateToDiscountInternational(vertical);
    }

    @Then("^I verify URL structure for the discount international (car|hotel|flight) deals page$")
    public void verifyURLDiscountInternationalPage(String vertical) {
        dealsModel.verifyURLDiscountInternationalPage(vertical);
    }

    @Then("^I verify URL structure for the discount US (car|hotel|flight) deals page$")
    public void verifyURLDiscountUSPage(String vertical) {
        dealsModel.verifyURLDiscountUSPage(vertical);
    }

    @Then("^I choose the first link from the (car|hotel|flight) most popular destinations$")
    public void chooseTheFirstLinkFromMostPopularDestination(String vertical) {
        dealsModel.chooseTheFirstLinkFromMostPopularDestination(vertical);
    }

    @Then("^I choose the first link from the (car|hotel|flight) all cities and verify URL structure$")
    public void chooseTheFirstLinkFromAllCitiesAndVerifyURL(String vertical) {
        dealsModel.chooseTheFirstLinkFromAllCitiesAndVerifyURL(vertical);
    }

    @Then("^I choose the first link from the (car|hotel|flight) all cities" +
            " and verify location prepopulated value and URL$")
    public void chooseTheFirstLinkFromAllCitiesAndVerifyLocationAndURL(String vertical) {
        dealsModel.chooseTheFirstLinkFromAllCitiesAndVerifyLocationAndURL(vertical);
    }

    @Then("^I verify(\\sinternational)? discount (flights|hotels|cars) deals sorted alphabetically$")
    public void verifyDiscountDealsSortedAlphabetically(String site, String vertical) {
        dealsModel.verifyDiscountDealsSortedAlphabetically(vertical);
    }

    @Then("^I verify header for discount international (flights|hotels|cars) deals$")
    public void verifyHeaderForIntlDeals(String vertical) {
        dealsModel.verifyHeaderForIntlDeals(vertical);
    }

    @Then("^I verify the the header for (Hotel|Car|Flight) deals page$")
    public void verifyHeader(String vertical) {
        dealsModel.verifyHeaderOnDealsPage(vertical);
    }

    @Then("I verify the all destination links are unique on (Hotel|Car|Flight) deals page$")
    public void verifyAllLinksAreUnique(String vertical) {
        dealsModel.verifyAllLinksAreUnique(vertical);
    }

    @Then("^I verify the browser title for (Hotel|Car|Flight) deals page$")
    public void verifyBrowserTitle(String vertical) {
        dealsModel.verifyBrowserTitle(vertical);

    }

    @Given("^I ensure what encryptedDealHash variable is$")
    public void verifyEnctiptedDealHash() {
        dealsModel.verifyEnctiptedDealHash();
    }

    @Then("^I verify (deals|vacations) page$")
    public void verifyDealsPage(String page) {
        dealsModel.verifyDealsPage(page);
    }

    @When("^I select one of the hotel deals$")
    public void selectHotelDeal() {
        dealsModel.selectHotelDeal();
    }

    @Then("^I see deal details match the selected deal$")
    public void verifyDealDetails() {
        dealsModel.verifyDealDetails();
    }

    @When("^I select one of the deal options$")
    public void selectHotelDealOption() {
        dealsModel.selectHotelDealOption();
    }

    @Then("^I see search criteria match the selected deal$")
    public void verifySearchCriteria() {
        dealsModel.verifySearchCriteria();
    }

    @Then("I verify About Our Car module$")
    public void verifyAboutOurCarsModule() {
        dealsModel.verifyAboutOurCarsModule();
    }

    @Given("^I navigate through the flights destination pagination by clicking on \"(.*?)\" link$")
    public void navigateThruFlightsDestinationPagination(String paginationOption) throws Throwable {
        dealsModel.navigateThruFlightsDestinationPagination(paginationOption);
    }

    @Given("^I click on Back to previous page link$")
    public void clickOnBacktoPreviousPageLink() throws Throwable {
        dealsModel.clickOnBacktoPreviousPageLink();
    }

    @Given("^I verify that a result set of (\\d+) states is displayed$")
    public void verifyNextResultSetOfStates(int statesNumber) throws Throwable {
        dealsModel.verifyNextResultSetOfStates(statesNumber);
    }
}
