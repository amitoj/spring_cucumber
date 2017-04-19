/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bexRetail;


import com.hotwire.selenium.angular.AngularHotelResultsPage;
import com.hotwire.selenium.bex.hotel.Disambiguation.HotelDisambiguationPage;
import com.hotwire.selenium.bex.hotel.confirmation.HotelConfirmationPage;
import com.hotwire.selenium.bex.hotel.details.HotelDetailsPage;
import com.hotwire.selenium.bex.hotel.results.HotelResultsPage;
import com.hotwire.selenium.bex.hotel.review.HotelReviewPage;
import com.hotwire.selenium.desktop.row.search.SemLandingPage;
import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.search.hotel.HotelSearchModel;
import com.hotwire.test.steps.search.hotel.HotelSearchParameters;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;


/**
 * @author jgonzalez
 *
 */
public abstract class BexModelTemplate extends WebdriverAwareModel implements BexModel {
    protected static final Logger LOGGER = LoggerFactory.getLogger(
            com.hotwire.test.steps.bexRetail.BexModelTemplate.class.getSimpleName());

    @Autowired
    @Qualifier("hotelSearchParameters")
    protected HotelSearchParameters hotelSearchParameters;

    @Autowired
    @Qualifier("hotelSearchModel")
    private HotelSearchModel hotelSearchModel;

    private String searchID;
    private String bexTmid;
    private String destinationCity;
    private String startDate;
    private String endDate;
    private String itineraryNumber;


    public void goToOMPT(String bid, String sid) {
        destinationCity = getEncodedDestination();
        startDate = getFormatedStartDate("MM/dd/yy");
        endDate = getFormatedEndDate("MM/dd/yy");

        navigateTo("www.qa.hotwire.com/hotel/partner-search.jsp?" +
                "inputId=hotel-index&destCity=" + destinationCity + "&startDate=" + startDate +
                "&endDate=" + endDate +
                "&numRooms=1&noOfAdults=2&noOfChildren=0&starRating=3.0&displayPrice=1.00" +
                "&sid=" + sid + "&bid=" + bid + "&useCluster=1&vt.AWA14=2&vt.ALS14=2&vt.ESS14=2");
        fromAngularResultsToBex();
    }

    public void goToDBM(String nid, String vid, String did) {
        destinationCity = getEncodedDestination();
        startDate = getFormatedStartDate("MM/dd/yyyy");
        endDate = getFormatedEndDate("MM/dd/yyyy");
        String destinationState = destinationCity.substring(destinationCity.length() - 2, destinationCity.length());
        navigateTo("http://www.qa.hotwire.com/hotel/search-options.jsp?inputId=hotel-index" +
                "&nid=" + nid + "$&vid=" + vid + "&cid=986f427192cd10d35b8a39f96b94c323&did=" + did +
                "&cid=986f427192cd10d35b8a39f96b94c323&destCity=" + destinationCity + "&destState=" + destinationState +
                "&startDate=" + startDate + "&endDate=" + endDate + "&r=Y&vt.AWA14=2&vt.ALS14=2&vt.ESS14=2");
        fromAngularResultsToBex();
    }

    private void navigateTo(String url) {
        getWebdriverInstance().navigate().to(url);
    }

    public void goToSem(String sid, String bid, String cmid, String acid, String kid, String mid) {
        navigateTo("www.qa.hotwire.com/en/content/san-francisco?fid=F1&sid=" + sid +
                "&acid=" + acid + "&cmid=" + cmid + "&bid=" + bid + "&kid=" + kid +
                "&mid=" + mid + "&vt.AWA14=2&vt.ALS14=2&vt.ESS14=2");
        navigateTo(getWebdriverInstance().getCurrentUrl().concat("?vt.AWA14=2&vt.ALS14=2&vt.ESS14=2"));
        SemLandingPage semLandingPage = new SemLandingPage(getWebdriverInstance());
        semLandingPage.getFareFinder().searchForHotels(
                hotelSearchParameters.getGlobalSearchParameters().getDestinationLocation(),
                hotelSearchParameters.getGlobalSearchParameters().getStartDate(),
                hotelSearchParameters.getGlobalSearchParameters().getEndDate(),
                hotelSearchParameters.getNumberOfHotelRooms(),
                hotelSearchParameters.getNumberOfAdults(),
                hotelSearchParameters.getNumberOfChildren(),
                hotelSearchParameters.getEnableHComSearch()
        );
        fromAngularResultsToBex();
    }

    public void goToAff(String siteID) {
        navigateTo("http://www.qa.hotwire.com/?siteID=" + siteID + "&vt.AWA14=2&vt.ALS14=2&vt.ESS14=2&vt.NHP14=2");
        HomePage homePage = new HomePage(getWebdriverInstance());
        hotelSearchModel.findPropertyFare(homePage.findHotelFare());
        fromAngularResultsToBex();
    }

    public void goToMeta(String expediaHotelID, String rpe) {
        navigateTo("http://www.qa.hotwire.com/hotel/details/direct-retail-details?" +
                "inputId=hotel-index&selectedExpediaHotelId=" + expediaHotelID +
                "&sid=S525&rpid=RPE" + rpe + "&bid=b1&" +
                "startDate=04/04/2015&endDate=04/05/2015&numRooms=1&numAdults=2&numChildren=0" +
                "&vt.AWA14=2&vt.ALS14=2&vt.ESS14=2");
        fromAngularResultsToBex();
    }


    public void bookFromResultsPage() {
        HotelResultsPage bexHotelResults = new HotelResultsPage(getWebdriverInstance());
        bexTmid = getTempIdFromURL();
        bexHotelResults.selectRandomHotel();
        HotelDetailsPage bexHotelDetails = new HotelDetailsPage(getWebdriverInstance());
        bexHotelDetails.selectRecommendedRoom();
        bexHotelDetails.selectPayNow();
        HotelReviewPage bexHotelReview = new HotelReviewPage(getWebdriverInstance());
        bexHotelReview.fillContactInfo("Test User", "123 456 7890");
        bexHotelReview.fillEmailAddress("v-jgonzalez@expedia.com");
        bexHotelReview.fillPaymentInfo();
        bexHotelReview.completeBooking();
        try {
            Thread.sleep(10000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        HotelConfirmationPage bexHotelConfirmation = new HotelConfirmationPage(getWebdriverInstance());
        itineraryNumber = bexHotelConfirmation.getItineraryNumber();
    }

    private String getTempIdFromURL() {
        String[] params = getWebdriverInstance().getCurrentUrl().split("&");
        return params[2];
    }

    public void saveSearchDetails(String transactionNumber) {
        String string = "\nTransaction number: " + transactionNumber + " " + startDate + " to " + endDate +
                " searchID:" + searchID + "using a bex tmid: " + bexTmid +
                " itinerary:" + itineraryNumber + " with destination: " +
                hotelSearchParameters.getGlobalSearchParameters().getDestinationLocation();
        File file = new File("test.txt");
        try {
            FileUtils.writeStringToFile(file, string, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void fromAngularResultsToBex() {
        AngularHotelResultsPage angularHotelResultsPage = new AngularHotelResultsPage(getWebdriverInstance());
        searchID = angularHotelResultsPage.getSearchId();
        angularHotelResultsPage.clickStandardRateHotelsTab();
        try {
            new HotelDisambiguationPage(getWebdriverInstance()).selectFirstOption();
            LOGGER.info("Disambiguation was needed");
        }
        catch (RuntimeException ex) {
            LOGGER.info("Disambiguation was not needed");
        }
        new HotelResultsPage(getWebdriverInstance());
    }

    private String getEncodedDestination() {
        if (destinationCity == null) {
            return hotelSearchParameters.getGlobalSearchParameters().getDestinationLocation()
                    .replace(", ", "%2C");
        }
        else {
            return destinationCity;
        }
    }

    private String getFormatedStartDate(String format) {
        return new SimpleDateFormat(format).format(hotelSearchParameters.getGlobalSearchParameters().getStartDate());
    }

    private String getFormatedEndDate(String pattern) {
        return new SimpleDateFormat(pattern).format(hotelSearchParameters.getGlobalSearchParameters().getEndDate());
    }


}
