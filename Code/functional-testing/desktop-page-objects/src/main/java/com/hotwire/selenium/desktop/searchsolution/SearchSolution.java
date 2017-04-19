/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.searchsolution;

public class SearchSolution {

    private int number;
    private String hotelName;
    private String location;
    private String rating;
    private String price;
    private String priceLabel;
    private String hotelData;
    private boolean isRoomPhotosOpaque;

    public boolean isRoomPhotosOpaque() {
        return isRoomPhotosOpaque;
    }

    public void setRoomPhotosOpaque(boolean roomPhotosOpaque) {
        isRoomPhotosOpaque = roomPhotosOpaque;
    }

    public String getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel = priceLabel;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHotelData() {
        return hotelData;
    }

    public void setDealData(String dealData) {
        this.hotelData = dealData;
    }
}
