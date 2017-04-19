/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.bean.c3;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;
import com.hotwire.util.db.c3.C3HotelSupplyDao;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contain information about hotel
 */
public class C3HotelSupplyInfo extends ToolsAbstractBean {
    private String hotelID;
    private String fullHotelName;
    private String hotelGuestName;
    private DateTime startDate;
    private String checkInDate;
    private String checkOutDate;
    private String hotelAddress;
    private Boolean isActive;
    private String hotelPhoneNumber;
    private String hotelFaxNumber;
    private String hotelCity;
    private String hotelState;
    private String hotelNeighborhood;
    private String hotelZipCode;
    private String hotelCountry;
    private List<String> amenities = new ArrayList<>();
    private String expediaRating;
    private String hotwireRating;
    private DateTime lastRatingChange;
    private boolean ratingChanged;
    private boolean hotelSurvey;
    private String rooms;
    private String nights;
    private String starRating;

    private ACTION_TYPE action;
    private boolean majorAmenity;
    private String manualBillingCreditAmount;

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setManualBillingCreditAmount(String manualBillingCreditAmount) {
        this.manualBillingCreditAmount = manualBillingCreditAmount;
    }

    public String getManualBillingCreditAmount() {
        return manualBillingCreditAmount;
    }


    /**
     * Describes action needed to do with hotel amenity
     */
    public enum ACTION_TYPE { ADD, REMOVE }

    public boolean isMajorAmenity() {
        return majorAmenity;
    }

    public void setMajorAmenity(boolean majorAmenity) {
        this.majorAmenity = majorAmenity;
    }

    public ACTION_TYPE getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = ACTION_TYPE.valueOf(action.toUpperCase());
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenity) {
        amenities.add(amenity);
    }

    public void setAmenities(List<String> amenity) {
        amenities.addAll(amenity);
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getHotelPhoneNumber() {
        return hotelPhoneNumber;
    }

    public void setHotelPhoneNumber(String hotelPhoneNumber) {
        this.hotelPhoneNumber = hotelPhoneNumber;
    }

    public String getHotelFaxNumber() {
        return hotelFaxNumber;
    }

    public void setHotelFaxNumber(String hotelFaxNumber) {
        this.hotelFaxNumber = hotelFaxNumber;
    }

    public String getHotelCity() {
        return hotelCity;
    }

    public void setHotelCity(String hotelCity) {
        this.hotelCity = hotelCity;
    }

    public String getHotelState() {
        return hotelState;
    }

    public void setHotelState(String hotelState) {
        this.hotelState = hotelState;
    }

    public String getHotelNeighborhood() {
        return hotelNeighborhood;
    }

    public void setHotelNeighborhood(String hotelNeighborhood) {
        this.hotelNeighborhood = hotelNeighborhood;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getFullHotelName() {
        return fullHotelName;
    }

    public void setFullHotelName(String fullHotelName) {
        this.fullHotelName = fullHotelName;
    }

    public String getHotelID() {
        return hotelID;
    }

    public String getHotelID(String itineraryNumber) {
        return new C3HotelSupplyDao(getDataBaseConnection()).getHotelIDByItinerary(itineraryNumber);
    }

    public void setBookingStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public void setHotelZipCode(String hotelZipCode) {
        this.hotelZipCode = hotelZipCode;
    }

    public String getHotelZipCode() {
        return hotelZipCode;
    }

    public void setHotelCountry(String hotelCountry) {
        this.hotelCountry = hotelCountry;
    }

    public String getHotelCountry() {
        return hotelCountry;
    }

    public String getExpediaRating() {
        return expediaRating;
    }

    public void setExpediaRating(String expediaRating) {
        this.expediaRating = expediaRating;
    }

    public String getHotwireRating() {
        return hotwireRating;
    }

    public void setHotwireRating(String hotwireRating) {
        this.hotwireRating = hotwireRating;
    }

    public DateTime getLastRatingChange() {
        return lastRatingChange;
    }

    public void setLastRatingChange(DateTime lastRatingChange) {
        this.lastRatingChange = lastRatingChange;
    }

    public boolean isHotelSurvey() {
        return hotelSurvey;
    }

    public void setHotelSurvey(boolean hotelSurvey) {
        this.hotelSurvey = hotelSurvey;
    }

    public boolean isRatingChanged() {
        return ratingChanged;
    }

    public void setRatingChanged(boolean ratingChanged) {
        this.ratingChanged = ratingChanged;
    }

    public String getHotelGuestName() {
        return hotelGuestName;
    }

    public void setHotelGuestName(String hotelGuestName) {
        this.hotelGuestName = hotelGuestName;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getNights() {
        return nights;
    }

    public void setNights(String nights) {
        this.nights = nights;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }
}





