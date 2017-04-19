/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile;

import com.hotwire.selenium.mobile.account.SignInPage;
import com.hotwire.selenium.mobile.search.CarSearchFragment;
import com.hotwire.selenium.mobile.search.HotelSearchFragment;
import com.hotwire.selenium.mobile.search.MultiVerticalFareFinder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.ehoffman.testing.fest.webdriver.WebElementAssert.assertThat;
/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 */
public class MobileHotwireHomePage extends MobileAbstractPage {

    private static Logger LOGGER = LoggerFactory.getLogger(MobileHotwireHomePage.class.getSimpleName());

    @FindBy(css = "div.errors")
    private WebElement formError;

    @FindBy(xpath = "//*[@data-bdd='hotel-tonight-button']")
    private WebElement roomTonight;

    @FindBy(xpath = "//*[@data-bdd='search-hotel-button']")
    private WebElement searchHotel;

    @FindBy(xpath = "//*[@data-bdd='search-car-button']")
    private WebElement searchCar;

    @FindBy(xpath = "//*[@data-bdd='my-trips-button']")
    private WebElement myTrips;

    @FindBy(xpath = "//*[@data-bdd='footer-link-signinout']")
    private WebElement signInOrRegister;

    @FindBy(xpath = "//*[@data-bdd='help-center-button']")
    private WebElement helpCenter;

    @FindBy(xpath = "//*[@data-bdd='footer-button-contact']")
    private WebElement contact;

    @FindBy(xpath = "//*[@data-bdd='footer-link-fullsite']")
    private WebElement fullSite;

    @FindBy(css = "div.timeout")
    private WebElement timeoutError;

    @FindBy(linkText = "Legal")
    private WebElement lnkLegal;

    public MobileHotwireHomePage(WebDriver webdriver) {
        super(webdriver, "tile.home");
    }

    @Override
    public SignInPage navigateToSignInOrRegister() {
        if (signedIn()) {
            //ensure the link at the bottom of the page point to
            // the same location as the sign in or register button on the home page
            //assert( super.getLinkOfSignInOrOut().equals(signInOrRegister.getAttribute("href")));
            //TO DO: from Rex: how do want to ensure this Brian/Preethi, do we need to? Seems important to me.
            LOGGER.info("Already logged in! Check webdriver session cleaning.");
        }
        signInOrRegister.click();
        return new SignInPage(getWebDriver());
    }

    public void setupLocale(String locale) {
        String[] tokens = getWebDriver().getCurrentUrl().split("\\?");
        String rootUrl = tokens[0];
        String url = rootUrl + locale;
        getWebDriver().get(url);
    }

    public void setupCountry(String countryCode) {
        String[] tokens = getWebDriver().getCurrentUrl().split("\\?");
        String rootUrl = tokens[0];
        String url = rootUrl + countryCode;
        getWebDriver().get(url);
    }

    public void setupURLToVisit(String uRL) {
        String[] tokens = getWebDriver().getCurrentUrl().split("mobile");
        String rootUrl = tokens[0];
        String url = rootUrl + "mobile/";
        if (uRL != null && !uRL.isEmpty() && uRL.startsWith("/")) {
            url += uRL.substring(1);
        }
        else {
            url += uRL;
        }
        LOGGER.info("Launching uRL: " + url);

        getWebDriver().get(url);
    }

    public void accessExternalHotelIndexLink() {
        String[] tokens = getWebDriver().getCurrentUrl().split("\\/mobile");
        String rootUrl = tokens[0];
        String url = rootUrl + "/mobile/hotel/search";
        getWebDriver().get(url);
    }

    public void accessExternalHotelIndexLinkWithDiscountCode(String discountCode) {
        String[] tokens = getWebDriver().getCurrentUrl().split("\\/mobile");
        String rootUrl = tokens[0];
        String url = rootUrl + "/mobile/hotel/search?dccid=" + discountCode;
        getWebDriver().get(url);
    }

    public void accessExternalCarIndexLink() {
        String[] tokens = getWebDriver().getCurrentUrl().split("\\/mobile");
        String rootUrl = tokens[0];
        String url = rootUrl + "/mobile/car/search";
        getWebDriver().get(url);
    }

    public void accessExternalHotelResultsLink() {
        String[] tokens = getWebDriver().getCurrentUrl().split("\\/mobile");
        String rootUrl = tokens[0];
        String url = "/mobile/hotel/launchsearch?inputId=index&rs=0&isFromSearchPopup=false&location=" +
            "SFO&startDate=10%2F16%2F12&endDate=10%2F17%2F12&numRooms=1&noOfAdults=2&noOfChildren=0" + rootUrl;
        getWebDriver().get(url);
    }

    public void accessExternalCarResultsLink() {
        String[] tokens = getWebDriver().getCurrentUrl().split("\\/mobile");
        String rootUrl = tokens[0];
        String url = rootUrl +
            "/mobile/car/launchsearch?startLocation=SFO&startDate=10%2F16%2F12&endDate=10" +
            "%2F17%2F12&startTime=1200&endTime=1200&startSearchType=N&inputId=index&" +
            "selectedCarTypes=ECAR&isOneWaySearch=false&endLocation=SFO&sid=S358&bid=B378867&mid=M3437";
        getWebDriver().get(url);
    }

    public void navigateToHelpCenter() {
        helpCenter.click();
    }

    public void navigateToCarSearch() {
        searchCar.click();
    }

    public void clickContactButton() {
        contact.click();
    }

    public void navigateToFullSite() {
        fullSite.click();
    }

    public void navigateToMyTripsPage() {
        myTrips.click();
    }

    public HotelSearchFragment getHotelSearchFragment() {
        return getMultiVerticalFarefinder().chooseHotel();
    }

    public CarSearchFragment findCarRental() {
        return getMultiVerticalFarefinder().chooseCar();
    }

    private MultiVerticalFareFinder getMultiVerticalFarefinder() {
        return new MultiVerticalFareFinder(getWebDriver());
    }

    public String getContactNumber() {
        return contact.getAttribute("href").replaceAll("%20", " ");
    }

    public void hasValidationErrorOnField(String validationError, String field) {
        String err1 = "Minimum 3 characters required to perform successful search.";
        String err2 = "Check-out date is not valid. The check-out date must be after the check-in date.";
        String err3 = "Check-in date or check-out date is not valid. Reservations must " +
                "include at least one night's stay.";
        String err4 = "Drop off date is not valid. The drop-off date must be after the pick-up date.";
        String err5 = "Drop off time is not valid. The drop off time must be 30 minutes after of your pick up.";


        if ("city".equalsIgnoreCase(field)) {
            if ("empty destination".equalsIgnoreCase(validationError)) {
                assertThat(assertHasFormErrors(err1, formError))
                        .as("The error \'" + err1 +  "\' doesn't found on the page")
                        .isTrue();
            }
            else if ("minimum length".equalsIgnoreCase(validationError)) {
                assertThat(assertHasFormErrors(err1, formError))
                        .as("The error \'" + err1 +  "\' doesn't found on the page")
                        .isTrue();
            }

        }
        else if ("date".equalsIgnoreCase(field)) {
            if ("invalid check-out".equalsIgnoreCase(validationError)) {
                assertThat(assertHasFormErrors(err2, formError))
                        .as("The error \'" + err2 +  "\' doesn't found on the page")
                        .isTrue();
            }
            else if ("one night reservation".equalsIgnoreCase(validationError)) {
                assertThat(assertHasFormErrors(err3, formError))
                        .as("The error \'" + err3 +  "\' doesn't found on the page")
                        .isTrue();
            }
            else if ("invalid drop-off".equalsIgnoreCase(validationError)) {
                assertThat(assertHasFormErrors(err4, formError))
                        .as("The error \'" + err4 +  "\' doesn't found on the page")
                        .isTrue();
            }

        }
        else if ("time".equalsIgnoreCase(field)) {
            if ("invalid time".equalsIgnoreCase(validationError)) {
                assertThat(assertHasFormErrors(err5, formError))
                        .as("The error \'" + err5 +  "\' doesn't found on the page")
                        .isTrue();
            }
        }
    }

    public void hasTimeoutMessage() {
        assertThat(timeoutError).isNotHidden().isDisplayed();
    }

    public void clickLegalLink() {
        LOGGER.info("Click Legal link");
        lnkLegal.click();
    }

    public void verifyErrorMessage(String errorText) {
        assertHasFormErrors(errorText, formError);
    }


}
