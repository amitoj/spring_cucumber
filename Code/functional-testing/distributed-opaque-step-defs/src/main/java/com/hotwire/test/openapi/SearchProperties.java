/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.openapi;

import java.net.URI;

/**
 * User: v-ngolodiuk
 * Since: 2/12/15
 */
public class SearchProperties {

    private String destinationLocation;
    private String pickupTime;
    private String pickupDay;
    private String dropoffDay;
    private String dropoffTime;
    private String carSearchPath;
    private String hotelSearchPath;
    private String hotelDetailsPath;
    private String carDetailsPath;
    private String neighborhoodPath;
    private String hotelDealsPath;
    private String amenitiesPath;
    private String hotelTrtPath;
    private String rooms;
    private String adults;
    private String children;
    private String totalPrice;
    private String resultId;
    private String hwRefNumber;
    private float starRating;
    private URI deepLink;

    public String getPickupDay() {
        return pickupDay;
    }

    public void setPickupDay(String pickupDay) {
        this.pickupDay = pickupDay;
    }

    public String getDropoffDay() {
        return dropoffDay;
    }

    public void setDropoffDay(String dropoffDay) {
        this.dropoffDay = dropoffDay;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getDropoffTime() {
        return dropoffTime;
    }

    public void setDropoffTime(String dropoffTime) {
        this.dropoffTime = dropoffTime;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getCarSearchPath() {
        return carSearchPath;
    }

    public void setCarSearchPath(String carSearchPath) {
        this.carSearchPath = carSearchPath;
    }


    public String getHotelSearchPath() {
        return hotelSearchPath;
    }

    public void setHotelSearchPath(String hotelSearchPath) {
        this.hotelSearchPath = hotelSearchPath;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getHwRefNumber() {
        return hwRefNumber;
    }

    public void setHwRefNumber(String hwRefNumber) {
        this.hwRefNumber = hwRefNumber;
    }

    public String getHotelDetailsPath() {
        return hotelDetailsPath;
    }

    public void setHotelDetailsPath(String solutionDetailsPath) {
        this.hotelDetailsPath = solutionDetailsPath;
    }

    public String getCarDetailsPath() {
        return carDetailsPath;
    }

    public void setCarDetailsPath(String carDetailsPath) {
        this.carDetailsPath = carDetailsPath;
    }

    public String getNeighborhoodPath() {
        return neighborhoodPath;
    }

    public void setNeighborhoodPath(String neighborhoodPath) {
        this.neighborhoodPath = neighborhoodPath;
    }

    public float getStarRating() {
        return starRating;
    }

    public void setStarRating(float starRating) {
        this.starRating = starRating;
    }

    public String getHotelDealsPath() {
        return hotelDealsPath;
    }

    public void setHotelDealsPath(String hotelDealsPath) {
        this.hotelDealsPath = hotelDealsPath;
    }

    public String getAmenitiesPath() {
        return amenitiesPath;
    }

    public void setAmenitiesPath(String amenitiesPath) {
        this.amenitiesPath = amenitiesPath;
    }

    public URI getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(URI deepLink) {
        this.deepLink = deepLink;
    }

    public String getHotelTrtPath() {
        return hotelTrtPath;
    }

    public void setHotelTrtPath(String hotelTrtPath) {
        this.hotelTrtPath = hotelTrtPath;
    }
}
