/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.packages;

import static org.fest.assertions.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.util.Map;

import com.hotwire.util.db.PackageDao;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.index.HotwireVacationsIndexPage;
import com.hotwire.selenium.desktop.us.index.PackagesIndexPage;
import com.hotwire.selenium.desktop.us.search.MultiVerticalFareFinder;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.util.webdriver.functions.PageName;

/**
 * User: akrivin
 * Date: 10/16/12
 * Time: 2:22 PM
 */
public class PackagesSearchModelWebApp extends SearchModelTemplate<PackagesSearchParameters>
        implements PackagesSearchModel {

    private static final String FLIGHT_CAR = "vt.FPC02=02";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    protected final Logger logger = LoggerFactory.getLogger(PackagesSearchModelWebApp.class.getSimpleName());

    @Autowired
    @Qualifier("applicationModel")
    ApplicationModel applicationModel;

    @Override
    public void findFare(String selectionCriteria) {
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();

        if (searchParameters.getVacationPackage().equals("AC")) {
            new HomePage(getWebdriverInstance()).findPackage(searchParameters.getVacationPackage()).
                    withOrigLocation(searchParameters.getOrigLocation()).
                    withDestinationLocation(globalSearchParameters.getDestinationLocation()).
                    withStartDate(globalSearchParameters.getStartDate()).
                    withEndDate(globalSearchParameters.getEndDate())
                    .findFare();

        }
        else {
            new HomePage(getWebdriverInstance()).findPackage(searchParameters.getVacationPackage()).
                    withOrigLocation(searchParameters.getOrigLocation()).
                    withNumberOfHotelRooms(searchParameters.getNumberOfHotelRooms()).
                    withNumberOfAdults(searchParameters.getNumberOfAdults()).
                    withNumberOfSeniors(searchParameters.getNumberOfSeniors()).
                    withNumberOfChildren(searchParameters.getNumberOfChildren()).
                    withNumberOfChildAge(searchParameters.getNumberOfChildAge()).
                    withStartAnyTime(searchParameters.getStartAnytime()).
                    withEndAnyTime(searchParameters.getEndAnytime()).
                    withHotelStartDate(searchParameters.gethotelStartdate()).
                    withHotelEndDate(searchParameters.gethotelEnddate()).
                    withDestinationLocation(globalSearchParameters.getDestinationLocation()).
                    withStartDate(globalSearchParameters.getStartDate()).
                    withEndDate(globalSearchParameters.getEndDate()).
                    findFare();
        }
    }

    @Override
    public void verifyPackagesResultsPage() {
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        String currentURL = getWebdriverInstance().getCurrentUrl();

        String endDate = FORMAT.format(globalSearchParameters.getEndDate());
        String startDate = FORMAT.format(globalSearchParameters.getStartDate());

        assertThat(getWebdriverInstance().findElement(By.cssSelector("div#ACOL font.pageHeading")).getText())
                .as("Results page should display header")
                .contains("Create your trip");

        logger.info("CURRENT URL: " + currentURL + "\nDEST: " + globalSearchParameters.getDestinationLocation());
        assertThat(getWebdriverInstance().getCurrentUrl().startsWith(PACKAGE_SEARCH_RESULTS_WEB_URL_PREFIX)).isTrue();

        if (StringUtils.isNotBlank(searchParameters.getOrigLocation())) {
            assertThat(currentURL.contains(searchParameters.getOrigLocation())).isTrue();
        }
        else {
            assertThat(currentURL.contains("dcty=&")).isTrue();
        }
        assertThat(currentURL.toLowerCase().contains(globalSearchParameters.getDestinationLocation().toLowerCase()))
            .isTrue();
        assertThat(currentURL.contains(startDate)).isTrue();
        assertThat(currentURL.contains(endDate)).isTrue();
    }

    @Override
    public void invokeHotelPartialStay() {
        PackagesIndexPage packagesPage = new PackagesIndexPage(getWebdriverInstance());
        packagesPage.clickonPartialHotelStay();
    }

    @Override
    public void addMoreDestinations() {
        PackagesIndexPage packagesPage = new PackagesIndexPage(getWebdriverInstance());
        packagesPage.clickonAddMoreDestinations();
    }

    @Override
    public void verifyAddMoreDestinationResults() {
        assertThat(getWebdriverInstance().getCurrentUrl().startsWith(PACKAGE_SEARCH_RESULTS_WEB_URL_PREFIX)).isTrue();

        HotwireVacationsIndexPage vacationsIndexPage = new HotwireVacationsIndexPage(getWebdriverInstance());
        assertThat(vacationsIndexPage.checkTwoDestinations()).isTrue();
    }

    @Override
    public void clickVacationPackageRadioButton(String vacationPackage) {
        new MultiVerticalFareFinder(getWebdriverInstance()).clickVacationPackageRadioButton(vacationPackage);
    }

    @Override
    public void showAirPlusCar() {
        getWebdriverInstance().navigate().to(applicationUrl + "?" + FLIGHT_CAR);
    }

    @Override
    public void accessExternalLink(String sURI) {
        getWebdriverInstance().navigate().to(applicationUrl + sURI);
    }

    @Override
    public void verifyVacationURL(String destination, String origin) {
        HotwireVacationsIndexPage vacationPage = new HotwireVacationsIndexPage(getWebdriverInstance());
        assertThat(vacationPage.verifyVacationPageForPackages(destination, origin)).
                as("Vacation Page didn't load or origCity is incorrect").
                isTrue();
    }

    @Override
    public void verifyAHCVacationSearchInDBForUser(String userName) {

        Map mapResult = new PackageDao(getDataBaseConnection()).getPackageSearchParametersByCustomer(userName);
        assertThat("V".equalsIgnoreCase(mapResult.get("CLASS").toString()))
                .as("CLASS value expected 'V' but actual is " + mapResult.get("CLASS"))
                .isTrue();
        assertThat("ACH".equalsIgnoreCase(mapResult.get("BUNDLED_CODE").toString()))
                .as("BUNDLED_CODE value expected 'ACH' but actual is " + mapResult.get("BUNDLED_CODE"))
                .isTrue();
        assertThat("Morning".equalsIgnoreCase(mapResult.get("TRAVEL_START_TIME").toString()))
                .as("TRAVEL_START_TIME value expected 'Morning' but actual is " + mapResult.get("TRAVEL_START_TIME"))
                .isTrue();
        assertThat("Evening".equalsIgnoreCase(mapResult.get("TRAVEL_END_TIME").toString()))
                .as("TRAVEL_END_TIME value expected 'Evening' but actual is " + mapResult.get("TRAVEL_END_TIME"))
                .isTrue();
        assertThat("".equalsIgnoreCase(mapResult.get("DEST_CITY_ID").toString()))
                .as("DESTINATION_CITY_ID value expected 'not NULL' but actual is " +
                        mapResult.get("DEST_CITY_ID"))
                .isFalse();
        try {
            assertThat("".equalsIgnoreCase(mapResult.get("PACKAGE_DESTINATION_ID").toString()))
                    .as("PACKAGE_DESTINATION_ID value expected 'NULL' but actual is " +
                            mapResult.get("PACKAGE_DESTINATION_ID"))
                    .isTrue();
        }
        catch (NullPointerException e) {
            logger.info(""); //Happy path. PACKAGE_DESTINATION_ID should be NULL
        }
    }

    @Override
    public void launchExternalAHCSearch() {
        applicationModel.setupURL_ToVisit("package/results.jsp?packageDestinationId=42" +
                "&packagedPGoodTypeCodes=ACH" +
                "&origCity=SEA" +
                "&noOfAdults=1" +
                "&noOfChildren=1" +
                "&noOfRooms=1" +
                "&startSearchType=N" +
                "&inputId=index" +
                "&startMonth=01" +
                "&startDay=12" +
                "&endMonth=01" +
                "&endDay=16");
        getWebdriverInstance().findElement(By.xpath("//input[@id='adis1']")).click();
        getWebdriverInstance().findElement(By.xpath("//a/b[contains(text(),'Search for trips')]/..")).click();
    }

    @Override
    public void verifyDefaultChildAgeIs11() {
        assertThat(getWebdriverInstance()
            .findElement(By.xpath("//td//b[contains(text(),'Child')]/.."))
            .getText()
            .contains("Child age: 11"))
                .as(getWebdriverInstance()
                    .findElement(By.xpath("//td//b[contains(text(),'Child')]/.."))
                    .getText())
                .isTrue();
    }

    @Override
    public void launchExternalCSearchToThemePage() {
        applicationModel.setupURL_ToVisit("package/theme.jsp" +
                "?originCity=San%20Francisco" +
                "&packageDestinationId=22" +
                "&index=package-index");
    }

    @Override
    public void verifyPackageThemePage(String origination, String destination) {
        assertThat("tiles-def.package.theme".equalsIgnoreCase(new PageName().apply(getWebdriverInstance()).toString()))
                .as("Page name expected 'tiles-def.package.theme' but actual " +
                        new PageName().apply(getWebdriverInstance()).toString())
                .isTrue();
        assertThat(origination.equalsIgnoreCase(getWebdriverInstance()
                            .findElement(By.xpath("//form[@name='vacationIndexForm']" +
                                    "//input[@name='origCity']"))
                            .getText()));
        assertThat(destination.equalsIgnoreCase(getWebdriverInstance()
                .findElement(By.xpath("//form[@name='vacationIndexForm']" +
                        "//input[@name='destCity']"))
                .getText()));
    }

    @Override
    public void startPackageThemeSearchWithoutSpecifyingParameters() {
        getWebdriverInstance().findElement(By.xpath("//form[@name='vacationIndexForm']" +
                        "//button/img[@alt='Find a package']/..")).click();
    }

    @Override
    public void verifyODPairsOnPackagesResultsPage(String origination, String destination) {
        assertThat(getWebdriverInstance().getCurrentUrl().startsWith(PACKAGE_SEARCH_RESULTS_WEB_URL_PREFIX)).isTrue();
        assertThat(getWebdriverInstance()
                    .findElement(By.xpath("//div[@id='fromBarCollapsed']//p"))
                    .getText()
                    .contains(origination.replaceAll(", .*", "")))
                .as("Origination expected: " + origination + " but actual: " + getWebdriverInstance()
                        .findElement(By.xpath("//div[@id='fromBarCollapsed']//p"))
                        .getText())
                .isTrue();
        assertThat(getWebdriverInstance()
                    .findElement(By.xpath("//input[@name='destination']"))
                    .getAttribute("value")
                    .contains(destination.replaceAll(", .*", "")))
                .as("Destination expected: " + destination + " but actual: " + getWebdriverInstance()
                        .findElement(By.xpath("//input[@name='destination']"))
                        .getAttribute("value"))
                .isTrue();
    }

    @Override
    public void corfirmErrorMsgIsReceivedAs(String errMsg) {
        PackagesIndexPage packagesPage = new PackagesIndexPage(getWebdriverInstance());
        assertThat(packagesPage.getErrorMessages().equals(errMsg)).as("Error message should be displayed as: " +
            errMsg).isTrue();
    }
}
