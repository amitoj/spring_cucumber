/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.air;

import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.confirm.AirConfirmationPage;
import com.hotwire.selenium.desktop.us.index.AirIndexPage;
import com.hotwire.selenium.desktop.us.partners.PartnerIMLFragment;
import com.hotwire.selenium.desktop.us.results.ActivitiesResultsPage;
import com.hotwire.selenium.desktop.us.results.AirResultsPage;
import com.hotwire.selenium.desktop.us.results.AirTripWatcherFragment;
import com.hotwire.selenium.desktop.us.results.air.ExpediaAirMultiResultsPage;
import com.hotwire.selenium.desktop.us.search.AirChangeSearchLayer;
import com.hotwire.selenium.desktop.us.search.AirMFILayer;
import com.hotwire.selenium.desktop.us.search.AirSearchFragment;
import com.hotwire.selenium.desktop.us.search.MultiVerticalFareFinder;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.WebDriverManager;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import cucumber.api.PendingException;
import org.apache.commons.lang3.StringUtils;
import org.ehoffman.testing.fest.webdriver.WebElementAssert;
import org.joda.time.LocalTime;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created with IntelliJ IDEA.
 * User: LRyzhikov
 * Date: 5/30/12
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class AirSearchModelWebApp extends AirSearchModelTemplate {

    private static final String OPAQUE_DETAIL_WITHOUT_PRICEMASK = "vt.ORF01=2";
    private static final String OPAQUE_DETAIL_WITH_PRICEMASK = "vt.ORF01=1";
    private static final String FORCED_INSURANCE_PANEL = "vt.IFC01=2";
    private static final String OLD_INSURANCE_PANEL = "vt.IFC01=1";
    private static final String RETAIL_FLIGHT_WITH_SPEEDBUMP = "vt.psm01=3";
    private static final String OPAQUE_FLIGHT_WITHOUT_SPEEDBUMP = "vt.psm01=3";
    private static final String BFS_AIRSEARCH_RESULTS = "vt.AGS01=1";
    private static final String ITA_AIRSEARCH_RESULTS = "vt.AGS01=2";
    private static final Logger LOGGER = LoggerFactory.getLogger(AirSearchModelWebApp.class);
    //Store all the locations and date info in this hashmap.
    private final HashMap<Integer, String> mapOrigdestCities = new HashMap<>();
    //Store all the error messages mentioned below in a hashmap for better understanding.
    private final HashMap<String, String> errors = new HashMap<>();
    //error messages
    private final String disambiguationForOriginCity = "From Please choose your location from the list.";
    private final String disambiguationForDestinationCity = "To Please choose your location from the list.";
    private final String disambiguationForBothOriginAndDestinationCities =
            "From and To: Did we guess what you wrote correctly?";
    private final String incorrectOriginCity = "From Please choose your location from the list.";
    private final String incorrectDestinationCity = "To Please choose your location from the list.";
    private final String incorrectOriginAndDestinationCities = "From and To: Did we guess what you wrote correctly?";
    private List<String> selectedDateList;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void findFare(String selectionCriteria) {
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();

        AirSearchFragment searchFragment = new HomePage(getWebdriverInstance()).findAirFare();
        searchFragment.withOrigLocation(searchParameters.getOrigLocation()).
            withNumberOfPassengers(searchParameters.getPassengers()).
            withDestinationLocation(globalSearchParameters.getDestinationLocation()).
            withStartDate(globalSearchParameters.getStartDate()).
            withEndDate(globalSearchParameters.getEndDate()).
            multiCityRoute(
                searchParameters.getFirstFromLocation(),
                searchParameters.getFirstToLocation(),
                searchParameters.getFirstStartDate(),
                searchParameters.getSecondFromLocation(),
                searchParameters.getSecondToLocation(),
                searchParameters.getSecondStartDate()
            ).
            addMoreFlights(
                searchParameters.getThirdFromLocation(),
                searchParameters.getThirdToLocation(),
                searchParameters.getThirdStartDate(),
                searchParameters.getFourthFromLocation(),
                searchParameters.getFourthToLocation(),
                searchParameters.getFourthStartDate()
            ).launchSearch();
    }

    public void setSelectedDateList(List<String> selectedDateList) {
        this.selectedDateList = selectedDateList;
    }

    @Override
    public void showOpaqueFlightWithOutPriceMask() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + OPAQUE_DETAIL_WITHOUT_PRICEMASK);
    }

    @Override
    public void showOpaqueFlightWithPriceMask() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + OPAQUE_DETAIL_WITH_PRICEMASK);
    }

    @Override
    public void showRetailFlightWithSpeedBump() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + RETAIL_FLIGHT_WITH_SPEEDBUMP);
    }

    @Override
    public void showOpaqueFlightWithSpeedBump() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + OPAQUE_FLIGHT_WITHOUT_SPEEDBUMP);
    }

    @Override
    public void verifySpeedBumpAndSaveText(AirSearchParameters airSearchParameters) {
        AirResultsPage airResultsPage = new AirResultsPage(getWebdriverInstance());
        if (airSearchParameters.getSpVisiableStatus().equals("Display")) {
            assertThat(airResultsPage.verifySpeedBumpAndSaveText()).isEqualTo(true);
        }
        else {
            assertThat(airResultsPage.verifySpeedBumpAndSaveText()).isEqualTo(false);
        }
    }

    @Override
    public void verifyColumnA(AirSearchParameters airSearchParameters) {
        AirResultsPage airResultsPage = new AirResultsPage(getWebdriverInstance());
        if (airSearchParameters.getColumnAStatus().equals("Display")) {
            assertThat(airResultsPage.verifyColumnA()).isEqualTo(true);
        }
        else {
            assertThat(airResultsPage.verifyColumnA()).isEqualTo(false);
        }
    }

    /*
     we need to add all the location and date data to a certain hashmap so that it can be reused to validate MFI.
     */
    @Override
    public HashMap<Integer, String> mapOrigDestCities(AirSearchParameters airSearchParameters) {
        SearchParameters searchParams = airSearchParameters.getGlobalSearchParameters();
        mapOrigdestCities.put(0, airSearchParameters.getOrigLocation());
        mapOrigdestCities.put(1, searchParams.getDestinationLocation());
        String date1 = new SimpleDateFormat("EEE, MMM d").format(searchParams.getStartDate());
        mapOrigdestCities.put(2, date1);
        if (StringUtils.isNotBlank(airSearchParameters.getFirstFromLocation())) {
            mapOrigdestCities.put(3, airSearchParameters.getFirstFromLocation());
            mapOrigdestCities.put(4, airSearchParameters.getFirstToLocation());
            String date2 = new SimpleDateFormat("EEE, MMM d")
                    .format(airSearchParameters.getFirstStartDate());
            mapOrigdestCities.put(5, date2);
            mapOrigdestCities.put(6, airSearchParameters.getSecondFromLocation());
            mapOrigdestCities.put(7, airSearchParameters.getSecondToLocation());
            String date3 = new SimpleDateFormat("EEE, MMM d")
                    .format(airSearchParameters.getSecondStartDate());
            mapOrigdestCities.put(8, date3);
        }
        if (StringUtils.isNotBlank(airSearchParameters.getThirdFromLocation())) {
            mapOrigdestCities.put(9, airSearchParameters.getThirdFromLocation());
            mapOrigdestCities.put(10, airSearchParameters.getThirdToLocation());
            String date4 = new SimpleDateFormat("EEE, MMM d")
                    .format(airSearchParameters.getThirdStartDate());
            mapOrigdestCities.put(11, date4);
            mapOrigdestCities.put(12, airSearchParameters.getFourthFromLocation());
            mapOrigdestCities.put(13, airSearchParameters.getFourthToLocation());
            String date5 = new SimpleDateFormat("EEE, MMM d")
                    .format(airSearchParameters.getFourthStartDate());
            mapOrigdestCities.put(14, date5);
        }
        return mapOrigdestCities;
    }

    //all the error message for incorrect input parameters are stored in this hashmap
    //all the error short-forms are explained in air.feature
    public HashMap<String, String> errorMessages() {
        errors.put("DOC", disambiguationForOriginCity);
        errors.put("DDC", disambiguationForDestinationCity);
        errors.put("DODC", disambiguationForBothOriginAndDestinationCities);
        errors.put("IOC", incorrectOriginCity);
        errors.put("IDC", incorrectDestinationCity);
        errors.put("IODC", incorrectOriginAndDestinationCities);
        return errors;
    }

    @Override
    public void verifyMFI(AirSearchParameters airSearchParameters) {
        AirMFILayer mfiLayer = new HomePage(getWebdriverInstance()).getMFILayer();
        SearchParameters searchParams = airSearchParameters.getGlobalSearchParameters();
        String endDate;
        if (searchParams.getEndDate() != null) {
            endDate = new SimpleDateFormat("EEE, MMM d").format(searchParams.getEndDate());
        }
        else {
            endDate = null;
        }
        assertThat(mfiLayer.isMfiD2CLayerDisplayedProperly(mapOrigDestCities(airSearchParameters), endDate))
                .as("content in the paragraphs is incorrect")
                .isTrue();
    }

    @Override
    public void verifyErrorMessage(String error) {
        HomePage homepage = new HomePage(getWebdriverInstance());
        assertThat(homepage.errorHandling(errorMessages().get(error)))
                .as("Incorrect Error Message")
                .isTrue();
    }

    @Override
    public void invokeAirFareFinder() {
        MultiVerticalFareFinder fareFinder = new MultiVerticalFareFinder(getWebdriverInstance());
        fareFinder.chooseAir();
    }

    @Override
    public void seeAllCitiesCount() {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        assertThat(airSearchFragment.getTotalCitiesLinks() == 2)
                .as("Count of 'see all cities' link is incorrect")
                .isTrue();
    }

    //Content in the See All Cities pop-up is validated in this function.
    @Override
    public void validateSeeAllCitiesPopUp(String field) {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        assertThat(airSearchFragment.validatePopUp(field))
                .as("Pop Up doesn't have the required content")
                .isTrue();
    }

    @Override
    public void paginationCheckInResultsPage() {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        assertThat(airSearchFragment.verifyForwardPaginationInResultsPage())
                .as("Pagination did not worked in expected way")
                .isTrue();
    }

    @Override
    public void setRoutesInfo(AirSearchParameters airSearchParameters) {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        SearchParameters searchParams = airSearchParameters.getGlobalSearchParameters();
        airSearchFragment.setRouteInfo(airSearchParameters.getOrigLocation(),
                searchParams.getDestinationLocation(),
                searchParams.getStartDate(),
                searchParams.getEndDate());
    }

    @Override
    public void doFlexSearch() {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        airSearchFragment.doFlexSearch();
    }

    @Override
    public void showBFSSearchResults() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + BFS_AIRSEARCH_RESULTS);
    }

    @Override
    public void showITASearchResults() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + ITA_AIRSEARCH_RESULTS);
    }

    @Override
    public void verifyIntentMediaModule() {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        assertThat(airSearchFragment.verifyIntentMediaModule())
                .as("Checking intent media module on results page")
                .isTrue();
    }

    @Override
    public void clickChangeSearch() {
        AirResultsPage airResultsPage = new AirResultsPage(getWebdriverInstance());
        Integer airResultsCount = airResultsPage.getAirResultsCount();
        if (airResultsCount == 0) {
            //results were not served due to lack of inventory.
            throw new PendingException("Results were not displayed due to sold out inventory " +
                    "or request didn't go through(have devs look into this)");
        }
        else {
            airResultsPage.clickChangeSearchLink();
        }
    }

    @Override
    public void verifyChangeSearchLayer(String fromLocation, String toLocation) {
        AirChangeSearchLayer airChangeSearchLayer = new AirChangeSearchLayer(getWebdriverInstance());
        assertThat(airChangeSearchLayer.isChangeSearchLayerInvoked(fromLocation, toLocation)).
                as("Layer is not invoked or parameters are incorrent").isTrue();
    }

    @Override
    public void changeSearchLocations(AirSearchParameters airSearchParameters) {
        AirChangeSearchLayer airChangeSearchLayer = new AirChangeSearchLayer(getWebdriverInstance());
        String fromLocation = airSearchParameters.getOrigLocation();
        String toLocation = airSearchParameters.getGlobalSearchParameters().getDestinationLocation();
        airChangeSearchLayer.changeSearchLocations(fromLocation, toLocation);
        WebDriverManager.closeAllPopups.apply(getWebdriverInstance());
    }

    @Override
    public void verifyChangeofRoute(String fromLocation, String toLocation) {
        AirResultsPage aireResultsPage = new AirResultsPage(getWebdriverInstance());
        assertThat(aireResultsPage.verifyChangeRoutes(fromLocation, toLocation))
                .as("Route hasn't updated").isTrue();
    }

    @Override
    public void verifyStartEndDates() {
        WebDriverManager.closeAllPopups.apply(getWebdriverInstance());
        AirChangeSearchLayer airChangeSearchLayer = new AirChangeSearchLayer(getWebdriverInstance());
        assertThat(airChangeSearchLayer
                .verifySelectedDates(selectedDateList))
                .as("Selected Dates are not the same")
                .isTrue();
    }

    @Override
    public void verifyResultsPage() {
        AirResultsPage results = new AirResultsPage(getWebdriverInstance());
        Integer airResultsCount = results.getAirResultsCount();
        LOGGER.info("Total {} air solutions are found..", airResultsCount);
        assertThat(airResultsCount).isGreaterThan(0);
    }

    @Override
    public void getSelectedDateList() {
        AirChangeSearchLayer airChangeSearchLayer = new AirChangeSearchLayer(getWebdriverInstance());
        selectedDateList = airChangeSearchLayer.getSelectedDates();
    }

    @Override
    public void verifyActivitiesBanner(String destinationCity, Date startDate) {
        DateFormat date = new SimpleDateFormat("MMMM d");
        String start = date.format(startDate);
        AirConfirmationPage airConfirmationPage = new AirConfirmationPage(getWebdriverInstance());
        assertThat(airConfirmationPage.verifyActivitiesBanner(destinationCity, start)).
                as("Acitivities Banner doesn't have expected result").isTrue();
    }

    @Override
    public void clickBanner() {
        AirConfirmationPage airConfirmationPage = new AirConfirmationPage(getWebdriverInstance());
        airConfirmationPage.clickBanner();
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
    public void verifyBFSAirResults() {
        AirResultsPage airResultsPage = new AirResultsPage(getWebdriverInstance());
        airResultsPage.getCRSTypeForBFS();
        assertThat(airResultsPage.getCRSTypeForBFS().contentEquals("G")).
                as("BFS results CRS type")
                .isTrue();
    }

    @Override
    public void verifyITAAirResults() {
        AirResultsPage airResultsPage = new AirResultsPage(getWebdriverInstance());
        airResultsPage.getCRSTypeForITA();
        assertThat(airResultsPage.getCRSTypeForITA().contentEquals("Q")).
                as("ITA results CRS type")
                .isTrue();
    }

    @Override
    public void forcedInsurancePanel() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + FORCED_INSURANCE_PANEL);
    }

    @Override
    public void oldInsurancePanel() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + OLD_INSURANCE_PANEL);
    }

    @Override
    public void verifyCrossCellParameters(String startDate, String endDate, String destinationLocation) {
        AirConfirmationPage airConfirmationPage = new AirConfirmationPage(getWebdriverInstance());
        assertThat(airConfirmationPage.verifyCrossSellParameters(destinationLocation, startDate, endDate))
                .as("Parameters in the cross sell modules are incorrect")
                .isTrue();
    }

    @Override
    public void verifyLocationsOnAirResultsPage(String fromLocation, String toLocation) {
        AirResultsPage airResultsPage = new AirResultsPage(getWebdriverInstance());
        //String location = airResultsPage.getLocation();
        String fromLocationOnPage = airResultsPage.getFromLocation();
        String toLocationOnPage = airResultsPage.getToLocation();
        assertThat(fromLocationOnPage)
                .as("'From' location is wrong")
                .contains(fromLocation);
        assertThat(toLocationOnPage)
                .as("'To' location is wrong")
                .contains(toLocation);
    }

    @Override
    public void searchPartnerWithIML() {
        PartnerIMLFragment fragment;
        String pageName = new PageName().apply(getWebdriverInstance());
        if (!pageName.contains("result")) {
            fragment = new HomePage(getWebdriverInstance()).getPartnerIMLFragment();
        }
        else {
            fragment = new AirResultsPage(getWebdriverInstance(), true).getPartnerIMLFragment();
        }
        fragment.selectPartnerOnIML(0);
        fragment.searchPartnerClick();
    }

    @Override
    public void verifyPartnerSearchResult() {
        assertThat(getWebdriverInstance().getWindowHandles().size())
            .as("Partner window with search results don't present")
            .isGreaterThan(1);
    }

    @Override
    public void typeDeparture(String location) {
        new AirSearchFragment(getWebdriverInstance()).setFromLocation(location);
    }

    @Override
    public void typeDestination(String location) {
        new AirSearchFragment(getWebdriverInstance()).setToLocation(location);
    }

    @Override
    public void verifySuggestedLocation(String number, String expectedAutoCompleteItem, String style, String click) {
        try {
            //Let's give a little bit time for the rendering auto-complete layer  - 0.2 sec
            Thread.sleep(200);
        }
        catch (InterruptedException e) {
            //...
        }
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        airSearchFragment.isAutoCompleteDisplayed();
        LOGGER.info("Auto-complete is visible");

        ArrayList<String> autocomleteContents = airSearchFragment.getAutoCompleteContents();
        ArrayList<String> autocompleteAttributes = airSearchFragment.getAttibutesForAutocomplete("class");
        List<WebElement> autocomleteElements = airSearchFragment.getAutocompleteElements();


        Integer i = autocomleteContents.indexOf(expectedAutoCompleteItem);

        if (number != null) {
            if (number.trim().equals("first")) {
                assertThat(i == 0).as("First auto-complete item is : " + expectedAutoCompleteItem).isTrue();
            }
            else if (number.trim().equals("second")) {
                assertThat(i == 1).as("Second auto-complete item is : " + expectedAutoCompleteItem).isTrue();
            }
            else if (number.trim().equals("third")) {
                assertThat(i == 2).as("Third auto-complete item is : " + expectedAutoCompleteItem).isTrue();
            }
        }
        else {
            assertThat(i != -1).isTrue();
        }

        if (style != null) {
            if (style.trim().equals("underlined and highlighted")) {
                assertThat(autocompleteAttributes.get(i).contains("yui-ac-highlight"))
                        .as("N" + i.toString() + " auto-complete item is highlighted and underlined").isTrue();
            }
        }

        if (click != null) {
            if (click.trim().equals("and I click it")) {
                autocomleteElements.get(i).click();
                try {
                    airSearchFragment.isAutoCompleteDisplayed();
                    throw new InvalidElementStateException();
                }
                catch (NullPointerException e) {
                    LOGGER.info("Auto-complete is invisible");
                }
            }
        }
    }

    @Override
    public void setDates(Date startDate, Date endDate) {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        airSearchFragment.withStartDate(startDate);
        airSearchFragment.withEndDate(endDate);
    }

    @Override
    public void clickAirSearch() {
        new AirSearchFragment(getWebdriverInstance()).launchSearch();
    }

    @Override
    public void verifyDisambiguationLayerIsVisible() {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        try {
            WebElementAssert.assertThat(airSearchFragment.getAutoComplete().findElement(By.xpath("..//../div")))
                    .isNotHidden().isDisplayed().textContains("Choose a");

        }
        catch (NullPointerException e) {
            throw new InvalidElementStateException();
        }
    }

    @Override
    public void verifyDisambiguationLayerIsNotVisible() {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        try {
            WebElementAssert.assertThat(airSearchFragment.getAutoComplete().findElement(By.xpath("..//../div")))
                    .isNotDisplayed();
        }
        catch (NullPointerException e) {
            LOGGER.info("Disambiguation layer isn't visible");
        }
    }

    @Override
    public void verifyResultsContainLocation(String location) {
        AirResultsPage airResultsPage = new AirResultsPage(getWebdriverInstance());
        String[] listOfLocation = location.split("to");
        String fromLocation = listOfLocation[0];
        String toLocation = listOfLocation[1];
        airResultsPage.verifyChangeRoutes(fromLocation, toLocation);
    }

    @Override
    public void clickOutOfDisambiguationLayer() {
        AirSearchFragment airSearchFragment = new AirSearchFragment(getWebdriverInstance());
        Actions action = new Actions(getWebdriverInstance());
        action.moveToElement(airSearchFragment.getFareFinderForm(), 500, 500)
                .click().perform();
    }

    @Override
    public void clickAirDepartureField() {
        new AirSearchFragment(getWebdriverInstance()).clickOnAirDepartureField();

    }

    @Override
    public void watchThisTrip(boolean state, String email) {
        AirTripWatcherFragment airTripWatcherFragment = new AirTripWatcherFragment(getWebdriverInstance());
        if (!state) {
            airTripWatcherFragment.getEmail().clear();
            airTripWatcherFragment.getEmail().sendKeys(email);
        }
        airTripWatcherFragment.watchThisTrip();
    }

    @Override
    public void verifyLowestPriceModule() {
        AirResultsPage resultsPage = new AirResultsPage(getWebdriverInstance());
        assertThat(resultsPage.isLowPriceModuleDisplayed())
            .as("Expected to see lowest price module but didn't.")
            .isTrue();
        Double modulePrice = resultsPage.getLowPriceModulePrice();
        List<Double> resultsPrices = resultsPage.getAllSolutionsPrices();
        Double firstPrice = resultsPrices.get(0);
        Double secondPrice = resultsPrices.get(1);
        assertThat(modulePrice.equals(firstPrice) || modulePrice.equals(secondPrice))
            .as("Expected lowest price module price of " + modulePrice + " to be either the first result price of " +
                firstPrice + " or the seconde result price of " + secondPrice)
            .isTrue();
    }

    @Override
    public void sortResultsAndVerify(String sortType) {
        AirResultsPage resultsPage = new AirResultsPage(getWebdriverInstance());
        boolean isAsc = resultsPage.sortResultsBy(sortType);
        resultsPage = new AirResultsPage(getWebdriverInstance());
        if (sortType.equalsIgnoreCase("price")) {
            assertResultsSortedBy(resultsPage.getAllSolutionsPrices(), isAsc);
            // Test the other sort.
            isAsc = resultsPage.sortResultsBy(sortType);
            resultsPage = new AirResultsPage(getWebdriverInstance());
            assertResultsSortedBy(resultsPage.getAllSolutionsPrices(), isAsc);
        }
        else if (sortType.equalsIgnoreCase("departure")) {
            assertResultsSortedBy(resultsPage.getOriginDepartureTimeList(), isAsc);
            // Test the other sort.
            isAsc = resultsPage.sortResultsBy(sortType);
            resultsPage = new AirResultsPage(getWebdriverInstance());
            assertResultsSortedBy(resultsPage.getOriginDepartureTimeList(), isAsc);
        }
        else if (sortType.equalsIgnoreCase("arrival")) {
            assertResultsSortedBy(resultsPage.getDepartureDestinationTimesList(), isAsc);
            // Test the other sort.
            isAsc = resultsPage.sortResultsBy(sortType);
            resultsPage = new AirResultsPage(getWebdriverInstance());
            assertResultsSortedBy(resultsPage.getDepartureDestinationTimesList(), isAsc);
        }
        else if (sortType.equalsIgnoreCase("stops")) {
            assertResultsSortedBy(resultsPage.getTotalNumberOfStopsPerSolution(), isAsc);
            // Test the other sort.
            isAsc = resultsPage.sortResultsBy(sortType);
            resultsPage = new AirResultsPage(getWebdriverInstance());
            assertResultsSortedBy(resultsPage.getTotalNumberOfStopsPerSolution(), isAsc);
        }
        else if (sortType.equalsIgnoreCase("duration")) {
            assertResultsSortedBy(resultsPage.getTotalDurationPerSolution(), isAsc);
            // Test the other sort.
            isAsc = resultsPage.sortResultsBy(sortType);
            resultsPage = new AirResultsPage(getWebdriverInstance());
            assertResultsSortedBy(resultsPage.getTotalDurationPerSolution(), isAsc);
        }
        else {
            throw new UnimplementedTestException("Unknown air results sort criteria type.");
        }
    }

    @Override
    public void clickAndVerifyUpdateLayerCalendarElement(boolean useDatePicker) {
        AirChangeSearchLayer changeLayer = new AirChangeSearchLayer(getWebdriverInstance());
        changeLayer.clickStartDate(useDatePicker);
        new WebDriverWait(getWebdriverInstance(), 2).until(new VisibilityOf(changeLayer.getDatePickerLayerByElement()));
        changeLayer.closeDatePickerLayer();
        new WebDriverWait(getWebdriverInstance(), 2)
            .until(new InvisibilityOf(changeLayer.getDatePickerLayerByElement()));
        changeLayer.clickEndDate(useDatePicker);
        new WebDriverWait(getWebdriverInstance(), 2).until(new VisibilityOf(changeLayer.getDatePickerLayerByElement()));
        changeLayer.closeDatePickerLayer();
    }

    private void assertResultsSortedBy(ArrayList<?> results, boolean doAscendingOrderBy) {
        for (int i = 1; i < results.size(); i++) {
            Object a = results.get(i);
            Object b = results.get(i - 1);
            // We only need to check one item because this is a list of the same types.
            if (Integer.class.isAssignableFrom(a.getClass())) {
                compareValues((Integer) a, (Integer) b, doAscendingOrderBy);
            }
            else if (Double.class.isAssignableFrom(a.getClass())) {
                compareValues((Double) a, (Double) b, doAscendingOrderBy);
            }
            else if (LocalTime.class.isAssignableFrom(a.getClass())) {
                compareValues((LocalTime) a, (LocalTime) b, doAscendingOrderBy);
            }
            else {
                throw new UnimplementedTestException("Unknown class type.");
            }
        }
    }

    private void compareValues(Double a, Double b, boolean doAscendingOrderBy) {
        LOGGER.info("Compare " + b + (doAscendingOrderBy ? " <= " : " >= ") + a);
        if (doAscendingOrderBy) {
            assertThat(b.compareTo(a) == 0 || b.compareTo(a) == -1)
                .as("Expected (" + a + " <= " + b + ") to be true but got false instead.")
                .isTrue();
        }
        else {
            // Descending order.
            assertThat(b.compareTo(a) == 0 || b.compareTo(a) == 1)
                .as("Expected (" + a + " >= " + b + ") to be true but got false instead.")
                .isTrue();
        }
    }

    private void compareValues(Integer a, Integer b, boolean doAscendingOrderBy) {
        LOGGER.info("Compare " + b + (doAscendingOrderBy ? " <= " : " >= ") + a);
        if (doAscendingOrderBy) {
            assertThat(b.compareTo(a) == 0 || b.compareTo(a) == -1)
                .as("Expected (" + a + " <= " + b + ") to be true but got false instead.")
                .isTrue();
        }
        else {
            // Descending order.
            assertThat(b.compareTo(a) == 0 || b.compareTo(a) == 1)
                .as("Expected (" + a + " >= " + b + ") to be true but got false instead.")
                .isTrue();
        }
    }

    private void compareValues(LocalTime a, LocalTime b, boolean doAscendingOrderBy) {
        LOGGER.info("Compare " + b + (doAscendingOrderBy ? " <= " : " >= ") + a);
        if (doAscendingOrderBy) {
            assertThat(a.isEqual(b) || a.isAfter(b))
                    .as("Expected (" + a + " <= " + b + ") to be true but got false instead.")
                    .isTrue();
        }
        else {
            // Descending order.
            assertThat(a.isEqual(b) || a.isBefore(b))
                    .as("Expected (" + a + " >= " + b + ") to be true but got false instead.")
                    .isTrue();
        }
    }

    @Override
    public void launchSearch() {
        AirSearchFragment searchFragment = new AirSearchFragment(getWebdriverInstance());

        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        searchFragment.withDestinationLocation(globalSearchParameters.getDestinationLocation())
                .withOrigLocation(searchParameters.getOrigLocation())
                .withStartDate(globalSearchParameters.getStartDate())
                .withEndDate(globalSearchParameters.getEndDate())
                .clickFlightSearch();
    }

    @Override
    public void verifyDestinationFieldIsFilledProperly() {
        String uRL = getWebdriverInstance().getCurrentUrl();
        String[] auxURL = uRL.split("destinationCity=");
        uRL = auxURL[1];
        auxURL = uRL.split("&noOfTickets");
        uRL = auxURL[0];
        auxURL = uRL.split(",");
        String destCity = auxURL[0];
        String destState = auxURL[1].substring(3, 11);
        String destinationCity = destCity.toUpperCase() + ", " + destState.toUpperCase();
        assertThat(
            destinationCity.equals(getWebdriverInstance().findElement(
                By.name("destinationCity")).getAttribute("value")))
                .as("Destination city field should be: [" + destinationCity + "]").isTrue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void confirmNumberSellableRetailAndAirportTypeCodeInDB(AuthenticationParameters authenticationParameters) {
        List<Map<String, Object>> listResult;
        String sql = "select customer_id from customer where email = '" + authenticationParameters.getUsername()
            .toLowerCase() + "'";
        listResult = jdbcTemplate.queryForList(sql);
        String customerID = listResult.get(0).toString();
        String[] auxCustomerID = customerID.split("=");
        customerID = auxCustomerID[1];
        auxCustomerID = customerID.split("}");
        customerID = auxCustomerID[0];
        System.out.println("customer id: " + customerID);

        listResult = null;
        sql = "select num_sellable_retail from search where customer_id = '" +
            customerID + "' and class = 'A' order by create_date desc";
        listResult = jdbcTemplate.queryForList(sql);
        String sellableRetail = listResult.get(0).toString();
        String[] auxSellableRetail = sellableRetail.split("=");
        sellableRetail = auxSellableRetail[1];
        auxSellableRetail = sellableRetail.split("}");
        sellableRetail = auxSellableRetail[0];
        System.out.println("sellable retail: " + sellableRetail);
        AirResultsPage airResPage = new AirResultsPage(getWebdriverInstance());
        System.out.println("Page num: " + airResPage.getResultsPageNumber());
        assertThat(sellableRetail.equals(airResPage.getResultsPageNumber())).as(
            "Sellable retail numbers mismatch, # in DB is: [" + sellableRetail + "] and # in UI is: [" +
                airResPage.getResultsPageNumber() + "]").isTrue();

        listResult = null;
        sql = "select alt_airport_type_code from search where customer_id = '" +
            customerID + "' and class = 'A' order by create_date desc";
        listResult = jdbcTemplate.queryForList(sql);
        String airportTypeCode = listResult.get(0).toString();
        String[] auxAirportTypeCode = airportTypeCode.split("=");
        airportTypeCode = auxAirportTypeCode[1];
        auxAirportTypeCode = airportTypeCode.split("}");
        airportTypeCode = auxAirportTypeCode[0];
        System.out.println("airport type code: " + airportTypeCode);
        assertThat(airportTypeCode.equals("Y")).as(
            "Airport type code should be [Y], DB is displaying: " + airportTypeCode + "").isTrue();
    }

    @Override
    public void validateMultiCityPage() throws ParseException {

        ExpediaAirMultiResultsPage multiCityResultPage = new ExpediaAirMultiResultsPage(this.getWebdriverInstance());
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        assertThat(format.parse(multiCityResultPage.getDepartureDate1()))
                .as("Departure Date 1 is not displaying the correct information")
                .isEqualTo(format.parse(format.format(searchParameters.getGlobalSearchParameters().getStartDate())));
        assertThat(multiCityResultPage.getFromCity1())
                .as("From City 1 is not displaying the correct information")
                .containsIgnoringCase(searchParameters.getOrigLocation());
        assertThat(multiCityResultPage.getToCity1())
                .as("To City 1 is not displaying the correct information")
                .containsIgnoringCase(searchParameters.getGlobalSearchParameters().getDestinationLocation());
        if (searchParameters.getFirstStartDate() != null) {
            assertThat(format.parse(multiCityResultPage.getDepartureDate2()))
                    .as("Departure Date 2 is not displaying the correct information")
                    .isEqualTo(format.parse(format.format(searchParameters.getFirstStartDate())));
            assertThat(multiCityResultPage.getFromCity2())
                    .as("From City 2 is not displaying the correct information")
                    .containsIgnoringCase(searchParameters.getFirstFromLocation());
            assertThat(multiCityResultPage.getToCity2())
                    .as("To City 2 is not displaying the correct information")
                    .containsIgnoringCase(searchParameters.getFirstToLocation());

            if (searchParameters.getSecondStartDate() != null) {
                assertThat(format.parse(multiCityResultPage.getDepartureDate3()))
                        .as("Departure Date 3 is not displaying the correct information")
                        .isEqualTo(format.parse(format.format(searchParameters.getSecondStartDate())));
                assertThat(multiCityResultPage.getFromCity3())
                        .as("From City 3 is not displaying the correct information")
                        .containsIgnoringCase(searchParameters.getSecondFromLocation());
                assertThat(multiCityResultPage.getToCity3())
                        .as("To City 3 is not displaying the correct information")
                        .containsIgnoringCase(searchParameters.getSecondToLocation());

                if (searchParameters.getThirdStartDate() != null) {
                    assertThat(format.parse(multiCityResultPage.getDepartureDate4()))
                            .as("Departure Date 4 is not displaying the correct information")
                            .isEqualTo(format.parse(format.format(searchParameters.getThirdStartDate())));
                    assertThat(multiCityResultPage.getFromCity4())
                            .as("From City 4 is not displaying the correct information")
                            .containsIgnoringCase(searchParameters.getThirdFromLocation());
                    assertThat(multiCityResultPage.getToCity4())
                            .as("To City 4 is not displaying the correct information")
                            .containsIgnoringCase(searchParameters.getThirdToLocation());

                    if (searchParameters.getFourthStartDate() != null) {
                        assertThat(format.parse(multiCityResultPage.getDepartureDate5()))
                                .as("Departure Date 5 is not displaying the correct information")
                                .isEqualTo(format.parse(format.format(searchParameters.getFourthStartDate())));
                        assertThat(multiCityResultPage.getFromCity5())
                                .as("From City 5 is not displaying the correct information")
                                .containsIgnoringCase(searchParameters.getFourthFromLocation());
                        assertThat(multiCityResultPage.getToCity5())
                                .as("To City 5 is not displaying the correct information")
                                .containsIgnoringCase(searchParameters.getFourthToLocation());
                    }

                }
            }
        }


    }

    @Override
    public void validateMeSoBanners() {
        AirIndexPage airIndexPage = new AirIndexPage(getWebdriverInstance());
        assertThat(airIndexPage.isMesoBannersVisible())
                .as("MeSo Banners are not being displayed correctly on Air Landing Page").isTrue();
    }

    @Override
    public void validateNumberOfPassengers() {
        WebElement fareFinderForm = new AirSearchFragment(getWebdriverInstance()).getFareFinderForm();
        //Clicking to make options visible.
        fareFinderForm.findElement(By.cssSelector("a#ui-id-1-button")).click();
        List<WebElement> options = fareFinderForm.findElements(By.cssSelector(".ui-menu li"));
        for (WebElement option: options) {
            assertThat(option.getText()).isNotIn("7", "8")
                    .as("AMP14 verison test should reduce maximum number of passengers to 6");
        }
    }

}
