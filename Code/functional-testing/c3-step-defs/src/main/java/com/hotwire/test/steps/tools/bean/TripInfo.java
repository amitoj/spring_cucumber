/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * User: v-ozelenov
 */

public class TripInfo extends ToolsAbstractBean {
    private Integer numberOfHotelRooms;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private String destinationLocation;
    private Integer shiftStartDate;
    private Integer shiftEndDate;
    private String pickupTime = "noon";
    private String dropoffTime = "noon";
    private String fromLocation;
    private Integer passengers;
    private String bookingType;
    private boolean tripInsurance;
    private boolean carInsurance;

    public Integer getNumberOfHotelRooms() {
        return numberOfHotelRooms;
    }

    public void setNumberOfHotelRooms(Integer numberOfHotelRooms) {
        this.numberOfHotelRooms = numberOfHotelRooms;
    }

    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public void setShiftStartDate(Integer shiftStartDate) {
        this.shiftStartDate = shiftStartDate;
    }

    public Date getStartDate() {
        return (new DateTime()).plusDays(shiftStartDate).toDate();
    }

    public void setShiftEndDate(Integer shiftEndDate) {
        this.shiftEndDate = shiftEndDate;
    }

    public Date getEndDate() {
        return (new DateTime()).plusDays(shiftEndDate).toDate();
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public String getDropoffTime() {
        return dropoffTime;
    }

    public void setPickupTime(String startTime) {
        pickupTime = startTime;
    }

    public void setDropoffTime(String endTime) {
        dropoffTime = endTime;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengerNumber) {
        this.passengers = passengerNumber;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public Integer getShiftStartDate() {
        return shiftStartDate;
    }

    public Integer getShiftEndDate() {
        return shiftEndDate;
    }

    public void setTripInsurance(boolean insurance) {
        this.tripInsurance = insurance;
    }

    public boolean getTripInsurance() {
        return this.tripInsurance;
    }

    public boolean getCarInsurance() {
        return carInsurance;
    }

    public void setCarInsurance(boolean carInsurance) {
        this.carInsurance = carInsurance;
    }
}
