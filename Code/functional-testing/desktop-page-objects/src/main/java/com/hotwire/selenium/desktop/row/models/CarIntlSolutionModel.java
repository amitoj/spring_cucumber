/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.models;

import java.util.Date;

/**
 * Holder for specific intl parameters
 *
 * @author v-ypuchkova
 * @since 2012.14
 */
public class CarIntlSolutionModel {

    ////////////////////////////////////////////////////////////////
    // ATTRIBUTES

    /**
     * Payment details
     */
    private Float payableUponArrival;
    private Float amountPaid;
    private Float totalPrice;
    private String currency;

    /**
     * Mid-size SUV,
     * Compact car,
     * Convertible car, Economy car and etc.
     */
    private String carType;

    /**
     * Ford Escape, Toyota Rav4 or similar**
     * Chevy Aveo, Hyundai Accent, or similar**
     * Nissan Versa, Toyota Yaris, or similar**
     * Toyota Corolla, Nissan Sentra, or similar**
     * etc.
     */
    private String carModel;

    /**
     * Amenities
     */
    private Integer peopleCapacity;
    private Integer packageCapacity;
    private Boolean isConditionerEnabled;
    private String transmissionType;

    /**
     * Reservation details
     */
    private String driverName;
    private String pickUpLocation;
    private String dropOffLocation;
    private Date pickUpDate;
    private Date dropOffDate;

    private String cardNumber;
    private String expiryDate;

    private String country;
    private Integer driversAge;

    // GETTERS AND SETTERS
    ////////////////////////////////////////////////////////////////
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getDriversAge() {
        return driversAge;
    }

    public void setDriversAge(Integer driversAge) {
        this.driversAge = driversAge;
    }

    public boolean getConditionerEnabled() {
        return isConditionerEnabled;
    }

    public void setConditionerInfo(boolean conditionerType) {
        this.isConditionerEnabled = conditionerType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carName) {
        this.carType = carName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Integer getPeopleCapacity() {
        return peopleCapacity;
    }

    public void setPeopleCapacity(Integer peopleCapacity) {
        this.peopleCapacity = peopleCapacity;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDropOffLocation() {
        return dropOffLocation;
    }

    public void setDropOffLocation(String dropOffLocation) {
        this.dropOffLocation = dropOffLocation;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Float getPayableUponArrival() {
        return payableUponArrival;
    }

    public void setPayableUponArrival(Float payableUponArrival) {
        this.payableUponArrival = payableUponArrival;
    }

    public Integer getPackageCapacity() {
        return packageCapacity;
    }

    public void setPackageCapacity(Integer packageCapacity) {
        this.packageCapacity = packageCapacity;
    }

    public boolean match(CarIntlSolutionModel modelForMatch) {
        if (modelForMatch == null) {
            return false;
        }
        if (!matchEachElement(modelForMatch.payableUponArrival, payableUponArrival)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.amountPaid, amountPaid)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.totalPrice, totalPrice)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.pickUpDate, pickUpDate)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.dropOffDate, dropOffDate)) {
            return false;
        }
        if (!compareLocation(modelForMatch.pickUpLocation, pickUpLocation)) {
            return false;
        }
        if (!compareLocation(modelForMatch.dropOffLocation, dropOffLocation)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.currency, currency)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.carType, carType)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.carModel, carModel)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.peopleCapacity, peopleCapacity)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.packageCapacity, packageCapacity)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.isConditionerEnabled, isConditionerEnabled)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.transmissionType, transmissionType)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.driverName, driverName)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.cardNumber, cardNumber)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.expiryDate, expiryDate)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.driversAge, driversAge)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.country, country)) {
            return false;
        }

        return true;
    }

    private boolean matchEachElement(Object o1, Object o2) {
        if ((o1 == null) || (o2 == null)) {
            return true;
        }
        return o1.equals(o2);
    }
    private boolean compareLocation(String s1, String s2) {
        if ((s1 == null) || (s2 == null)) {
            return true;
        }
        return s1.contains(s2);
    }
}
