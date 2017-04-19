/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.itineraryDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v-ozelenov on 4/7/2014.
 * Air specific Itinerary Details Page
 */
public class C3AirItineraryPage extends C3ItineraryDetailsPage {
    public static String[] EXPECTED_HEADERS_FOR_DEPARTING_SEGMENTS =
            new String[] {"Segment", "Airline", "Flight", "Origin", "Dest.", "Departs (local time)",
                          "Arrives (local time)", "Elapsed Time", "Equipment", "Codeshare", "Airline record locator"};

    public C3AirItineraryPage(WebDriver webDriver) {
        super(webDriver);
    }

    private List<String> getTextOfWebElements(List<WebElement> webElements) {
        List<String> res = new ArrayList<String>();
        for (WebElement elem: webElements) {
            res.add(elem.getText());
        }
        return  res;
    }

    @Override
    public String getTotalCost() {
        return findOne("#silkId_TrAm_TotalCost, #silkId_TrAm_TotCostToCustomer").getText()
              .replaceFirst("^est.\\s", "").replaceAll("[()]", "");
    }
    public String getAirTicketNumbers() {
        List <WebElement> wes  = findMany("td[id*='silkId_TrDet_TicketNumber']");
        StringBuilder tickets = new StringBuilder();
        for (WebElement we : wes) {

            tickets.append(we.getText());
        }
        return String.valueOf(tickets);
    }

    public String getNamesOfPassengers() {
        List <WebElement> wes  = findMany("td[id*='silkId_TrDet_ParticipantName']");
        String tickets = "";
        for (WebElement we : wes) {
            tickets += we.getText();
        }

        return tickets;

    }

    public String getHeadersOfDepartingSegments() {
        return findMany(".purHisTbl").get(0).getText();
    }

    public String getHeadersOfReturningSegments() {
        return findMany(".purHisTbl").get(1).getText();
    }

    public List<String> getDepartingCarrier() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_Airline')]"));
        return getTextOfWebElements(webElem);
    }

    public  List<String> getDepartingFlightNumber() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_Flight')]"));
        return getTextOfWebElements(webElem);
    }

    public  List<String> getDepartingFromLocation() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_Origin')]"));
        return getTextOfWebElements(webElem);
    }

    public  List<String> getDepartingToLocation() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_Dest')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getDepartingStartDateAndTime() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_Departs')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getDepartingEndDateAndTime() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_Arrives')]"));
        return getTextOfWebElements(webElem);

    }

    public List<String> getDepartingTotalTripTime() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_ElapsedTiem')]"));
        return getTextOfWebElements(webElem);

    }

    public List<String>  getDepartingFlightType() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_Equipment')]"));
        return getTextOfWebElements(webElem);

    }

    public List<String> getDepartingCodeshare() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_Codeshare')]"));
        return getTextOfWebElements(webElem);

    }
    public List<String> getDepartingAirlineRecordLocator() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_DepSeg_RecordLocator')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getReturningCarrier() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_Airline')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getReturningFlightNumber() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_Flight')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getReturningFromLocation() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_Origin')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getReturningToLocation() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_Dest')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getReturningStartDateAndTime() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_Departs')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getReturningEndDateAndTime() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_Arrives')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getReturningTotalTripTime() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_ElapsedTiems')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getReturningFlightType() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_Equipment')]"));
        return getTextOfWebElements(webElem);
    }

    public List<String> getReturningCodeshare() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_Codeshare')]"));
        return getTextOfWebElements(webElem);
    }
    public List<String> getReturningAirlineRecordLocator() {
        List<WebElement> webElem = findMany(By.xpath("//td[contains(@id, 'silkId_RetSeg_RecordLocator')]"));
        return getTextOfWebElements(webElem);
    }

    public String getNumberOfPassengers() {
        return findOne("#silkId_TrDet_ParticipantsNumber").getText();
    }

    public String getDepartLocationFromItinerarySection() {
        return findOne("#silkId_ItInf_Depart").getText();
    }

    public String getReturnLocationFromItinerarySection() {
        return findOne("#silkId_ItInf_Return").getText();
    }

    public String getSearchedBookedByCSR() {
        return findOne("#silkId_ItInf_ByCsr").getText();
    }

    public String getOutBoundConnection() {
        return findOne("#silkId_ItInf_OutboundConnections").getText();
    }

    public String getInBoundConnection() {
        return findOne("#silkId_ItInf_InboundConnections").getText();
    }

    public String getTripProtectionBookedDetails() {
        String tripProtection = null;
        try {
            return findOne(By.xpath("//a[contains(text(), 'Trip Protection')]//..//../td[@class='monetaryElement']"))
                    .getText().replace(",", "");
        }
        catch (Exception e) {
            return null;
        }

    }

    public String getNumberOfTickets() {
        return findOne("#silkId_ItInf_TicketsNum").getText();
    }

    public String getPlatingCarrier() {
        return findOne("#silkId_ItInf_PlatingCarrier").getText();
    }

    public String getBilledBy() {
        return findOne("#silkId_ItInf_BilledBy").getText();
    }

    public String getBookingType() {
        return findOne("#silkId_ItInf_BookingType").getText();
    }

    public String getTransactionDate() {
        return findOne("#silkId_ItInf_TransactionDate").getText();
    }

    public String getDeviceType() {
        return findOne("#TESTID_deviceType").getText();
    }

    public String getApplicationType() {
        return findOne("#TESTID_appType").getText();
    }

    public String getLoggedIn1() {
        return findOne("#silkId_ItInf_IsLoggedIn1").getText();
    }

    public String getTripProtection() {
        return findOne("#silkId_ItInf_TripProtection1>a>b").getText();
    }

    public String getLoggedIn2() {
        return findOne("#silkId_ItInf_IsLoggedIn2").getText();
    }

    public String getRentalCarProtection() {
        return findOne("#silkId_ItInf_TripProtection2").getText();
    }
}
