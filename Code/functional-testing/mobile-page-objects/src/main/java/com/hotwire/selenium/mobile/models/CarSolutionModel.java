/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.models;

import java.util.Date;

/**
 * User: v-jolopez
 * Date: 12/15/14
 * Time: 2:17 AM
 */
public class CarSolutionModel {

    private boolean isOpaque;
    private boolean isLowestPrice;
    private String cdCode;
    private String displayNumber;
    private String perDayPrice;
    private String totalPrice;
    private String currency;
    private String location;
    private Date startDate;
    private Date endDate;
    private Float taxesAndFees;
    private Integer rentalDaysCount;
    private Float damageProtection;
    /**
     * Mid-size SUV,
     * Compact car,
     * Convertible car, Economy car and etc.
     */
    private String carName;
    /**
     * Ford Escape, Toyota Rav4 or similar**
     * Chevy Aveo, Hyundai Accent, or similar**
     * Nissan Versa, Toyota Yaris, or similar**
     * Toyota Corolla, Nissan Sentra, or similar**
     * etc.
     */
    private String carModels;
    private String vendor;
    /**
     * Unlimited miles
     */
    private String mileage;
    /**
     * On Airport - Shuttle to Vendor
     */
    private String distance;
    /**
     * Capacities
     */
    private Integer peopleCapacity;
    private Integer largePackageCapacity;
    private Integer smallPackageCapacity;

    /**
     * @return type of current solution {opaque|retail}
     */
    public boolean isOpaque() {
        return isOpaque;
    }

    public void setOpaque(boolean opaque) {
        isOpaque = opaque;
    }

    public boolean isLowestPrice() {
        return isLowestPrice;
    }

    public void setLowestPrice(boolean lowestPrice) {
        isLowestPrice = lowestPrice;
    }

    public String getCdCode() {
        return cdCode;
    }

    public void setCdCode(String cdCode) {
        this.cdCode = cdCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDisplayNumber() {
        return displayNumber;
    }

    public void setDisplayNumber(String displayNumber) {
        this.displayNumber = displayNumber;
    }

    public String getPerDayPrice() {
        return perDayPrice;
    }

    public void setPerDayPrice(String perDayPrice) {
        this.perDayPrice = perDayPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModels() {
        return carModels;
    }

    public void setCarModels(String carModels) {
        this.carModels = carModels;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPeopleCapacity() {
        return peopleCapacity;
    }

    public void setPeopleCapacity(Integer peopleCapacity) {
        this.peopleCapacity = peopleCapacity;
    }

    public int getLargePackageCapacity() {
        return largePackageCapacity;
    }

    public void setLargePackageCapacity(Integer largePackageCapacity) {
        this.largePackageCapacity = largePackageCapacity;
    }

    public int getSmallPackageCapacity() {
        return smallPackageCapacity;
    }

    public void setSmallPackageCapacity(Integer smallPackageCapacity) {
        this.smallPackageCapacity = smallPackageCapacity;
    }

    public Float getTaxesAndFees() {
        return taxesAndFees;
    }

    public void setTaxesAndFees(Float taxesAndFees) {
        this.taxesAndFees = taxesAndFees;
    }

    public Integer getRentalDaysCount() {
        return rentalDaysCount;
    }

    public void setRentalDaysCount(Integer rentalDaysCount) {
        this.rentalDaysCount = rentalDaysCount;
    }

    public Float getDamageProtection() {
        return damageProtection;
    }

    public void setDamageProtection(Float damageProtection) {
        this.damageProtection = damageProtection;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "\nCode: " + cdCode + "; Vendor: " + vendor + "; Display num: " + displayNumber +
                "\nRental days count: " + rentalDaysCount +
                "\nEstimated taxes and fees: " + taxesAndFees +
                "\nRental car damage protection: " + damageProtection +
                "\n" + carName + " (" + carModels + ")\nper day price is " + currency + perDayPrice +
                " Total: " + currency + totalPrice +
                "\nMileage: " + mileage +
                "\nDistance: " + distance +
                "\nCapacity: people - " + peopleCapacity +
                " package: " + largePackageCapacity + " + " + smallPackageCapacity;
    }

    public boolean match(CarSolutionModel modelForMatch) {

        if (modelForMatch == null) {
            return false;
        }
        if (!matchEachElement(modelForMatch.carModels, carModels)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.peopleCapacity, peopleCapacity)) {
            return false;
        }
        if (!matchEachElement(modelForMatch.perDayPrice, perDayPrice)) {
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

