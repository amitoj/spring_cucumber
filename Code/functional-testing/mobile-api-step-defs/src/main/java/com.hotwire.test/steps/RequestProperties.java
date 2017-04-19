/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * User: v-ngolodiuk
 * Since: 1/8/15
 */
public class RequestProperties {
    private UserInformation userInformation;


    private long clientId;
    private long customerId;
    private String oauthToken;

    private String destinationLocation;
    private String pickupLocation;
    private String dropOffLocation;
    private XMLGregorianCalendar startDate;
    private XMLGregorianCalendar endDate;
    private int numberOfRooms;
    private int numberOfAdults;
    private int numberOfChildren;
    private String resultId;
    private String currencyCode;
    private String countryCode;
    private String latLong;
    private boolean isNewCard;
    private String channelID;
    private String depositMethod;
    private String ageOfDriver;
    private boolean isOpaque;
    private boolean isOneWayTrip;

    private int limit;
    private int offset;

    private String vertical;
    private boolean isInsuranceSelected;
    private boolean isPaymentRequired;
    private int resultIdIndexForCouponValidation;
    private String hotelSearchDealHash;
    private boolean isHotDollarsPayment;

    private int qtyOfSavedCards;
    private boolean excludeCanceledRefunded;
    private String promoCode;
    private String createdUserEmail;

    private long hotelId;

    public RequestProperties() {
    }

    public boolean isHotDollarsPayment() {
        return isHotDollarsPayment;
    }

    public void setHotDollarsPayment(boolean hotDollarsPayment) {
        isHotDollarsPayment = hotDollarsPayment;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }


    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public void setStartDate(XMLGregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }

    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public UserInformation getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public boolean getIsNewCard() {
        return isNewCard;
    }

    public void setIsNewCard(boolean newCard) {
        isNewCard = newCard;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDepositMethod() {
        return depositMethod;
    }

    public void setDepositMethod(String depositMethod) {
        this.depositMethod = depositMethod;
    }

    public String getAgeOfDriver() {
        return ageOfDriver;
    }

    public void setAgeOfDriver(String ageOfDriver) {
        this.ageOfDriver = ageOfDriver;
    }

    public boolean getIsOpaque() {
        return isOpaque;
    }

    public void setIsOpaque(boolean opaque) {
        isOpaque = opaque;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    public boolean getIsInsuranceSelected() {
        return isInsuranceSelected;
    }

    public void setIsInsuranceSelected(boolean insuranceSelected) {
        isInsuranceSelected = insuranceSelected;
    }

    public boolean getIsPaymentRequired() {
        return isPaymentRequired;
    }

    public void setIsPaymentRequired(boolean paymentRequired) {
        isPaymentRequired = paymentRequired;
    }

    public void setResultIdIndexForCouponValidation(int resultIdIndexForCouponValidation) {
        this.resultIdIndexForCouponValidation = resultIdIndexForCouponValidation;
    }

    public int getResultIdIndexForCouponValidation() {
        return resultIdIndexForCouponValidation;
    }


    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setIsOneWayTrip(boolean isOneWayTrip) {
        this.isOneWayTrip = isOneWayTrip;
    }

    public boolean getIsOneWayTrip() {
        return isOneWayTrip;
    }

    public void setHotelSearchDealHash(String hotelSearchDealHash) {
        this.hotelSearchDealHash = hotelSearchDealHash;
    }

    public String getHotelSearchDealHash() {
        return hotelSearchDealHash;
    }

    public int getQtyOfSavedCards() {
        return qtyOfSavedCards;
    }

    public void setQtyOfSavedCards(int qtyOfSavedCards) {
        this.qtyOfSavedCards = qtyOfSavedCards;
    }

    public void setExcludeCanceledRefunded(boolean excludeCanceledRefunded) {
        this.excludeCanceledRefunded = excludeCanceledRefunded;
    }

    public boolean getExcludeCanceledRefunded() {
        return excludeCanceledRefunded;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setCreatedUserEmail(String createdUserEmail) {
        this.createdUserEmail = createdUserEmail;
    }

    public String getCreatedUserEmail() {
        return createdUserEmail;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public long getHotelId() {
        return hotelId;
    }
}
