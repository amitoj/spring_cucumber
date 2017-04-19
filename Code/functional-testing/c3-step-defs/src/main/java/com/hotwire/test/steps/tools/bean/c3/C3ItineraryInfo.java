/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean.c3;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;
import com.hotwire.util.db.c3.C3SearchDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * Information about itinerary that used in C3 workflow.
 * User: v-ozelenov
 */

public class C3ItineraryInfo extends ToolsAbstractBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(C3ItineraryInfo.class.getSimpleName());
    private String itineraryNumber;
    private String purchaseOrderId;
    private String reservationId;
    private String hotelReservationNumber;
    private String carReservationNumber;
    private String airTicketNumber;
    private Double refundAmount;
    private Double refundHotDollarAmount;
    private Double totalCost;
    private String currencyCode;
    private Map<String, String> confirmationPoints;
    private Map<String, String> talkingPoints;
    private String refundReason;

    private String caseNotesNumber;
    private String caseNotesText;
    private String referenceNumberResults;
    private String referenceNumberDetails;
    private String pnrNumber;
    private String workflowEntry;

    /*
    * Car specific fields
     */
    private String driverName;
    private String company;
    private String pickUpLocation;
    private String dropOffLocation;
    private String pickUpDate;
    private String dropOffDate;


    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(String dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = parseAmount(totalCost);
    }



    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    public Map<String, String> getTalkingPoints() {
        return talkingPoints;
    }

    public void setTalkingPoints(Map<String, String> talkingPoints) {
        this.talkingPoints = talkingPoints;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public void setConfirmationPoints(Map<String, String> confirmationPoints) {
        this.confirmationPoints = confirmationPoints;
    }

    public Map<String, String> getConfirmationPoints() {
        return confirmationPoints;
    }

    public String getCaseNotesText() {
        return caseNotesText;
    }

    public void setCaseNotesText(String caseNotesText) {
        this.caseNotesText = caseNotesText;
    }

    public String getReservationNumByItinerary() {
        if (itineraryNumber == null) {
            return null;
        }
        return new C3SearchDao(getDataBaseConnection()).getReservationNumByItinerary(itineraryNumber);
    }

    public String getWorkflowEntry() {
        return workflowEntry;
    }

    public void setWorkflowEntry(String workflowEntry) {
        this.workflowEntry = workflowEntry;
    }

    public String getItineraryNumber() {
        return itineraryNumber;
    }

    public void setItineraryNumber(String itineraryNumber) {
        this.itineraryNumber = itineraryNumber;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = parseAmount(refundAmount);
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getCaseNotesID() {
        return caseNotesNumber;
    }

    public void setCaseNotesNumber(String caseNotesNumber) {
        this.caseNotesNumber = caseNotesNumber;
    }

    public String getReferenceNumberResults() {
        return referenceNumberResults;
    }

    public void setReferenceNumberResults(String referenceNumberResults) {
        this.referenceNumberResults = referenceNumberResults;
    }

    public String getReferenceNumberDetails() {
        return referenceNumberDetails;
    }

    public void setReferenceNumberDetails(String referenceNumberDetails) {
        this.referenceNumberDetails = referenceNumberDetails;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    /**
     * Extracting price and currency
     * Save currency to c3Itinerary
     * @param textToBeParsed  - from this text price and currency will be extracted
     * @return price
     */
    public Double parseAmount(String textToBeParsed) {
        setCurrencyCode(extractCurrency(textToBeParsed));
        return extractPrice(textToBeParsed);
    }

    public Double extractPrice(String inputText) {
        LOGGER.info("AMOUNT TO BE PARSED: " + inputText);
        Double result;
        try {
            result = Double.valueOf(inputText.replaceAll("[^0-9.]", ""));
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException("Error occurred. Message:" + inputText);
        }
        return result;
    }

    public String extractCurrency(String inputText) {
        String currency;
        if (inputText.matches("^.*[£$€].*$")) {
            currency = inputText.replaceAll("[^£$€]", "");
        }
        else {
            currency = inputText.replaceAll("[0-9.,]+\\s+.*$", "").replaceAll("[0-9,.]", "");
        }
        return currency;
    }

    public boolean compareAmounts(String expectedAmount, String actualAmount) {
        Double exAm = extractPrice(expectedAmount);
        Double acAm = extractPrice(actualAmount);
        return Math.abs(exAm - acAm) < 0.05;
    }

    public String getHotelReservationNumber() {
        return hotelReservationNumber;
    }

    public void setHotelReservationNumber(String hotelReservationNumber) {
        this.hotelReservationNumber = hotelReservationNumber;
    }

    public String getCarReservationNumber() {
        return carReservationNumber;
    }

    public void setCarReservationNumber(String carReservationNumber) {
        this.carReservationNumber = carReservationNumber;
    }

    public String getAirTicketNumber() {
        return airTicketNumber;
    }

    public void setAirTicketNumber(String airTicketNumber) {
        this.airTicketNumber = airTicketNumber;
    }

    public void setRefundHotDollarAmount(Double refundHotDollarAmount) {
        this.refundHotDollarAmount = refundHotDollarAmount;
    }

    public Double getRefundHotDollarAmount() {
        return refundHotDollarAmount;
    }
}
