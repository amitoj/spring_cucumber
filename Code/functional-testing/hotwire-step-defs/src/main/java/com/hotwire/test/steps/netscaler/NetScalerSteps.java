/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.netscaler;

import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.help.HelpCenterModel;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.test.steps.util.RetailHeroLinkFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.net.URL;

/**
 * This class represents the mapping of the Netscalar rules
 */
public class NetScalerSteps extends AbstractSteps {

    @Autowired
    @Qualifier("helpCenterModel")
    HelpCenterModel helpCenterModel;

    @Autowired
    @Qualifier("netScalerModel")
    private NetScalerModel netScalerModel;

    @Autowired
    private URL applicationUrl;

    @Autowired
    @Qualifier("applicationModel")
    private ApplicationModel applicationModel;

    @Autowired
    @Qualifier("retailHeroLinkFactory")
    private RetailHeroLinkFactory linkFactory;

    @Autowired
    private SearchParameters searchParameters;

    @When("^I open the URL on a mobile device$")
    public void openUrlOnMobileDevice(String url) throws IOException {
        applicationModel.openUrlOnMobileDevice(url);
    }

    @Given("^I am redirected to the URL$")
    public void verifyCurrentUrl(String url) throws IOException {
        applicationModel.verifyCurrentUrl(url);
    }

    @Given("^I see (.*) content$")
    public void verifyMobileAppAfterRedirection(String target) throws IOException {
        applicationModel.verifyMobileAppAfterRedirection(target);
    }

    @Given("^I visit Hotwire from (us|ca|uk|ie|no|au|nz|dk|se|de|hk|mx|fr|sg) on a mobile device$")
    public void setURL_ToVisitFromCountry(String location) {
        this.applicationModel.setupURL_ToVisit(location);
    }

    @Given("^I visit Hotwire from (us|ca|uk|ie|no|au|nz|dk|se|de|hk|mx|fr|sg) on a mobile device with default " +
           "currency$")
    public void setURL_ToVisitFromCountryWithDefaultCurrency(String location) {
        this.applicationModel.setupURL_ToVisit(location + "/?currencyCode=");
    }

    @Given("^I visit Hotwire (air|hotels|cars) from a search engine on a mobile device$")
    public void setURL_ToVisitFromSearchEngine(String vertical) {
        PGoodCode pGoodCode = PGoodCode.toPGoodType(vertical);
        switch (pGoodCode) {
            case H:
                this.applicationModel.setupURL_ToVisit("seo/hotel");
                break;
            case A:
                this.applicationModel.setupURL_ToVisit("seo/air");
                break;
            case C:
                this.applicationModel.setupURL_ToVisit("seo/car");
                break;
            default:
                break;
        }
    }

    @Given("^I visit Hotwire (air|hotels|cars)? from a search engine with marketing parameters on a mobile device$")
    public void setURL_ToVisitFromSearchEngineWithParams(String vertical) {
        if (vertical == null || vertical.isEmpty()) {
            // There is no vertical. Send them to the seo page without any vertical
            this.applicationModel.setupURL_ToVisit("seo/sid/S11/bid/B90000059/kid/K0000001/mid/M03&rct=j&q=hotwire");
        }
        else {
            PGoodCode pGoodCode = PGoodCode.toPGoodType(vertical);
            switch (pGoodCode) {
                case H:
                    this.applicationModel.setupURL_ToVisit("seo/hotel/sid/S11/bid/B30074641/kid/K3307665/mid/M02" +
                                                           "/d-city/San%20Francisco,%20CA");
                    break;
                case A:
                    this.applicationModel.setupURL_ToVisit("seo/air/sid/S462/bid/B90000786/kid/K0000035/mid/M01");
                    break;
                case C:
                    this.applicationModel.setupURL_ToVisit("seo/car/sid/S11/bid/B30074641/kid/K3307665/mid/M02" +
                                                           "/d-city/San%20Francisco,%20CA");
                    break;
                default:
                    break;
            }
        }
    }

    @Given("^I visit Hotwire (hotels|cars) on a mobile device$")
    public void setURL_ToVisit(String vertical) {
        PGoodCode pGoodCode = PGoodCode.toPGoodType(vertical);
        switch (pGoodCode) {
            case H:
                this.applicationModel.setupURL_ToVisit("hotel/index.jsp");
                break;
            case A:
                throw new UnsupportedOperationException("Invalid vertical: " + vertical);
            case C:
                this.applicationModel.setupURL_ToVisit("car/index.jsp");
                break;
            default:
                break;
        }
    }

    @Given("^I go to the air index page$")
    public void navigateToAirIndexPage() {
        this.applicationModel.setupURL_ToVisit("air/index.jsp");
    }


    @Given("^I go to a deals page(?: for (hotel|car))?$")
    public void navigateToDealsPage(String vertical) {
        if (vertical == null || vertical.isEmpty()) {
            // There is no vertical. Send them to the deals page
            this.applicationModel.setupURL_ToVisit("deals/index.jsp");
        }
        else {
            PGoodCode pGoodCode = PGoodCode.toPGoodType(vertical);
            switch (pGoodCode) {
                case H:
                    this.applicationModel.setupURL_ToVisit("deals/hotel-deals.jsp");
                    break;
                case A:
                    throw new UnsupportedOperationException("Invalid vertical: " + vertical);
                case C:
                    this.applicationModel.setupURL_ToVisit("deals/car-deals.jsp");
                    break;
                default:
                    break;
            }
        }
    }

    @Given("^I go to a (las-vegas|new-york|sun) destination page$")
    public void navigateToDestinationPage(String destination) {
        this.applicationModel.setupURL_ToVisit("destination/" + destination + ".jsp");
    }

    @Given("^I go to a (air|hotel|car) information page$")
    public void navigateToVerticalInformationPage(String vertical) {
        PGoodCode pGoodCode = PGoodCode.toPGoodType(vertical);
        switch (pGoodCode) {
            case H:
                this.applicationModel.setupURL_ToVisit("hotel-information/us/index.jsp");
                break;
            case A:
                this.applicationModel.setupURL_ToVisit("flight-information/us/index.jsp");
                break;
            case C:
                this.applicationModel.setupURL_ToVisit("car-information/us/index.jsp");
                break;
            default:
                break;
        }
    }

    @Given("^I go to (new to Hotwire|last minute vacation|weekend getaway|Chicago travel) information page$")
    public void navigateToInformationPage(String category) {
        if ("new to Hotwire".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("newtohotwire/index.jsp");
        }
        else if ("last minute vacation".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("theme/last-minute.jsp");
        }
        else if ("weekend getaway".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("theme/weekend-getaway.jsp");
        }
        else if ("Chicago travel".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("vacation-travel/chicago-illinois.jsp");
        }
        else {
            throw new IllegalArgumentException("Invalid argument : " + category);
        }
    }

    /**
     * Method to visit the seo links for destination pages We are allowing any string to be a valid destinationName
     * parameter as it is a business requirement
     *
     * @param seoLink         Category
     * @param destinationName Destination name such as Amsterdam
     */
    @Given("^I visit (destination|vacation-travel|hotel-rooms) (.*) page$")
    public void navigateToDestinationPage(String seoLink, String destinationName) {
        this.applicationModel.setupURL_ToVisit(seoLink + "/" + destinationName + ".jsp");
    }

    @Given("^I visit cheap page$")
    public void navigateToCheapTravelPage() {
        this.applicationModel.setupURL_ToVisit("cheap.jsp");
    }


    @Given("^I go to hotel (rate report|ratings|destinations|suppliers) information page$")
    public void navigateToHotelCategoryInformationPage(String category) {
        if ("rate report".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("hotelratereport.jsp");
        }
        else if ("ratings".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("pop-up/hotel-ratings-popup.jsp");
        }
        else if ("destinations".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("pop-up/hotelDestinations.jsp");
        }
        else if ("suppliers".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("travel-information/suppliers/hotel-suppliers.jsp");
        }
        else {
            throw new IllegalArgumentException("Invalid argument : " + category);
        }
    }

    @Given("^I go to trip protection (claim|insurance|purchase) information page$")
    public void navigateToTripProtectionCategoryInformationPage(String category) {
        if ("claim".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("helpcenter/travel-protection/trip-protection/file.jsp");
        }
        else if ("insurance".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("helpcenter/travel-protection/trip-protection/insurance.jsp");
        }
        else if ("purchase".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("helpcenter/travel-protection/trip-protection/purchase.jsp");
        }
        else {
            throw new IllegalArgumentException("Invalid argument : " + category);
        }
    }


    @Given("^I go to car (suppliers|types) information page$")
    public void navigateToCarCategoryInformationPage(String category) {
        if ("suppliers".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("travel-information/suppliers/car-suppliers.jsp");
        }
        else if ("types".equalsIgnoreCase(category)) {
            this.applicationModel.setupURL_ToVisit("travel-information/car-types.jsp");
        }
        else {
            throw new IllegalArgumentException("Invalid argument : " + category);
        }
    }

    @Given("^I want to fill out a (air|hotel|car) survey$")
    public void navigateToVerticalSurveyPage(String vertical) {
        PGoodCode pGoodCode = PGoodCode.toPGoodType(vertical);
        switch (pGoodCode) {
            case H:
                this.applicationModel.setupURL_ToVisit("hotel/survey-version1.jsp?res=10000076065&c=2328968251&" +
                                                       "q1=50&fromMail=true");
                break;
            case A:
                this.applicationModel.setupURL_ToVisit("air/survey.jsp?res=10000076116&c=2328968251&" +
                                                       "survey.wasFlightTaken=true");
                break;
            case C:
                this.applicationModel.setupURL_ToVisit("car/survey.jsp?resid=10000076115&" +
                                                       "email=imoskalets@hotwire.com&pageStepNumber=N1");
                break;
            default:
                break;
        }
    }


    @Given("^I choose to see the full site$")
    public void selectFullSiteView() {
        this.applicationModel.selectFullSiteView();
    }

    @Given("^I want to access my trip information$")
    public void navigateToTripSummaryPage() {
        this.applicationModel.setupURL_ToVisit("account/my-trips.jsp");
    }

    @Given("^I want to login$")
    public void navigateToLoginPage() {
        this.applicationModel.setupURL_ToVisit("login.jsp");
    }

    @Given("^I want to register as a new user$")
    public void navigateToRegistrationPage() {
        this.applicationModel.setupURL_ToVisit("registration.jsp");
    }

    @Given("^I want assistance as I forgot my password$")
    public void navigateToPasswordAssistancePage() {
        this.applicationModel.setupURL_ToVisit("account/forgot-password.jsp");
    }

    @Given("^I want to reset my password via the password assistance email$")
    public void navigateToPasswordResetPage() {
        this.applicationModel.setupURL_ToVisit("p.jsp?h=ef521d93c9244efe824f6e7f22af6b73");
    }

    @Given("^I visit Hotwire from an online partner with an (Ean|Expedia|Hotwire) hotel$")
    public void navigateToRetailAsGateway(String hotelType) {
        // Need to do this because ROW has some predefined stuff in the search parameters bean the screws up validation
        // of retail hero on RowWebApp
        searchParameters.setDestinationLocation(null);
        this.applicationModel.setupURL_ToVisit(this.linkFactory.getRetailHeroLink(hotelType));
    }

    @Then("^I should be on mobile optimized page$")
    public void verifyMobileSite() {
        // Check that you are on the mobile site by looking at the URL
        this.applicationModel.verifySite();
    }

    @Then("^I should be on mobile optimized (home|hotel search|car search|hotel results|car results|help center|" +
          "trip summary|sign in|registration|password assistance) page$")
    public void verifyMobileOptimzedPage(String targetPage) {
        if ("home".equalsIgnoreCase(targetPage)) {
            this.applicationModel.verifyHomePage("mobile");
        }
        else if ("hotel search".equalsIgnoreCase(targetPage)) {
            this.applicationModel.verifySearchPage(PGoodCode.H);
        }
        else if ("car search".equalsIgnoreCase(targetPage)) {
            this.applicationModel.verifySearchPage(PGoodCode.C);
        }
        else if ("hotel results".equalsIgnoreCase(targetPage)) {
            this.applicationModel.verifyResultsPage(PGoodCode.H);
        }
        else if ("car results".equalsIgnoreCase(targetPage)) {
            this.applicationModel.verifyResultsPage(PGoodCode.C);
        }
        else if ("help center".equalsIgnoreCase(targetPage)) {
            this.helpCenterModel.verifyHelpCenterPage();
        }
        else if ("trip summary".equalsIgnoreCase(targetPage)) {
            this.applicationModel.verifyTripSummaryPage();
        }
        else if ("sign in".equalsIgnoreCase(targetPage)) {
            this.applicationModel.verifySignInPage();
        }
        else if ("registration".equalsIgnoreCase(targetPage)) {
            this.applicationModel.verifyRegistrationPage();
        }
        else if ("password assistance".equalsIgnoreCase(targetPage)) {
            this.applicationModel.verifyPasswordAssistancePage();
        }
    }

    @Then("^I should be on Hotwire branded 3rd party mobile home page$")
    public void verifyHotwireBranded3rdPartyMobileSite() {
        this.applicationModel.verifyHotwireBranded3rdPartyMobileCarSite();
    }

    @Then("^I should be on (desktop) (domestic|international) home page$")
    public void verifyLandingPage(String channel, String pPOS) {
        this.applicationModel.verifyHomePage(channel + " " + pPOS);
    }

    @Then("^I should be on desktop domestic air index page$")
    public void verifyAirIndexPage() {
        this.applicationModel.verifyAirIndexPage();
    }

    @Then("^I should be on desktop domestic air results page$")
    public void verifyAirPageWithOrWithoutResults() {
        this.applicationModel.verifyAirPageWithOrWithoutResults();
    }

    @Then("^I should be on desktop domestic air information page$")
    public void verifyAirInformationPage() {
        this.applicationModel.verifyAirInformationPage();
    }

    @Then("^I should be on desktop domestic (air|hotel|car) survey page$")
    public void verifyVerticalSurveyPage(String vertical) {
        this.applicationModel.verifySurveyPage(vertical);
    }

    @Then("^I should be on desktop domestic (Amsterdam|Rome) information page$")
    public void verifyDestinationInformationPage(String destination) {
        this.applicationModel.verifyDestinationInformationPage(destination);
    }

    @Then("^I see dates in (.*) local format$")
    public void verifyLocalDateFormat(String format) {
        netScalerModel.verifyLocalDateFormat(format);
    }
}
