/*
        * Copyright 2012 Hotwire. All Rights Reserved.
        *
        * This software is the proprietary information of Hotwire.
        * Use is subject to license terms.
        */

package com.hotwire.test.steps.purchase.car.entities;

/**
 * User: v-vzyryanov Date: 6/14/13 Time: 4:45 AM
 */
public class VendorGridEntity implements Comparable {

    private Float totalPrice;
    private Float perDayPrice;
    private String vendor;
    private String currency;
    private String location;

    public VendorGridEntity() {
    }

    public VendorGridEntity(String[] args) {
        if (args.length > 0) {
            vendor = args[0];
        }
        if (args.length > 1) {
            perDayPrice = Float.parseFloat(args[1]);
        }
        if (args.length > 2) {
            totalPrice = Float.parseFloat(args[2]);
        }
        if (args.length > 3) {
            currency = args[3];
        }
        if (args.length > 4) {
            location = args[4];
        }
    }


    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getPerDayPrice() {
        return perDayPrice;
    }

    public void setPerDayPrice(Float perDayPrice) {
        this.perDayPrice = perDayPrice;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return vendor + " PRICE " + currency + perDayPrice + " (" + currency + totalPrice + ")";
    }

    @Override
    public int compareTo(Object obj) {
        VendorGridEntity en = (VendorGridEntity) obj;
        if (totalPrice > en.getTotalPrice()) {
            return 1;
        }
        if (totalPrice < en.getTotalPrice()) {
            return -1;
        }
        return 0;
    }
}
