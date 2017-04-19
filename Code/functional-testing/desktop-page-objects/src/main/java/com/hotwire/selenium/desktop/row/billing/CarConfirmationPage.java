/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.billing;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.row.models.CarIntlSolutionModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author v-ypuchkova
 * @since 2012.10
 */
public class CarConfirmationPage extends AbstractRowPage {

    @FindBy(className = "seleniumDriverName")
    private WebElement driverName;

    @FindBy(className = "seleniumStartDate")
    private WebElement pickUpDate;

    @FindBy(className = "seleniumEndDate")
    private WebElement dropOffDate;

    @FindBy(className = "seleniumPickupLocation")
    private WebElement pickUpLocation;

    @FindBy(className = "seleniumDropOffLocation")
    private WebElement dropOffLocation;

    @FindBy(className = "seleniumCarModel")
    private WebElement carModel;

    @FindBy(className = "carOptions")
    private WebElement amenities;

    @FindBy(className = "paymentDetails")
    private WebElement paymentDetailsModule;

    public CarConfirmationPage(WebDriver webdriver) {
        super(webdriver, "tile.car.confirmation", DEFAULT_SEARCH_LAYER_ID);
    }

    public CarIntlSolutionModel getSolutionOptionsSet() {
        CarIntlSolutionModel carSolutionModel = new CarIntlSolutionModel();

        carSolutionModel.setPickUpLocation(getPickUpLocation());
        carSolutionModel.setDropOffLocation(getDropOffLocation());
        carSolutionModel.setCarModel(getCarModel());
        carSolutionModel.setPeopleCapacity(getPeopleCapacity());
        carSolutionModel.setPackageCapacity(getPackageCapacity());
        carSolutionModel.setConditionerInfo(getConditionerInfo());
        carSolutionModel.setTransmissionType(getTransmissionType());
//        carSolutionModel.setTotalPrice(getTotalPrice());
//        carSolutionModel.setPayableUponArrival(getPayableUponArrival());
//        carSolutionModel.setAmountPaid(getPayableNowPrice());
        carSolutionModel.setCardNumber(getCardNumber());
        carSolutionModel.setExpiryDate(getExpiryDate());
        carSolutionModel.setCurrency(getCurrency());

        return carSolutionModel;
    }

    public String getDriverName() {
        return driverName.getText();
    }

    private String getPickUpLocation() {
        return pickUpLocation.getText();
    }

    private String getDropOffLocation() {
        return dropOffLocation.getText();
    }

    private String getCarModel() {
        return carModel.getText();
    }

    public String getDropOffDate() {
        return dropOffDate.getText();
    }

    public String getPickUpDate() {
        return pickUpDate.getText();
    }

    /*
     * Data about amenities
     */
    private Integer getPeopleCapacity() {
        WebElement peopleCapacity = amenities.findElement(By.className("seleniumSeatingCapacity"));
        return Integer.parseInt(peopleCapacity.getText().replaceAll("[^0-9]", ""));
    }

    private Integer getPackageCapacity() {
        WebElement packageCapacity = amenities.findElement(By.className("seleniumTrunkCapacity"));
        return Integer.parseInt(packageCapacity.getText().replaceAll("[^0-9]", ""));
    }

    private boolean getConditionerInfo() {
        try {
            return amenities.findElement(By.className("seleniumConditioner")).isEnabled();
        }
        catch (Exception e) {
            // no action
        }
        return false;
    }

    private String getTransmissionType() {
        return amenities.findElement(By.className("seleniumTransmission")).getText();
    }

    /*
     * Data from payment details module
     */
    private String getCurrency() {
        WebElement totalPrice = paymentDetailsModule.findElement(By.className("seleniumTotalPrice"));
        return totalPrice.getText().replaceAll("[0-9.]", "");
    }

    private Float getTotalPrice() {
        WebElement totalPrice = paymentDetailsModule.findElement(By.className("seleniumTotalPrice"));
        return Float.parseFloat(totalPrice.getText().replaceAll("[^0-9.]", ""));
    }

    private Float getPayableUponArrival() {
        WebElement payableUponArrival = paymentDetailsModule.findElement(By.className("seleniumPayableUponArrival"));
        return Float.parseFloat(payableUponArrival.getText().replaceAll("[^0-9.]", ""));
    }

    private Float getPayableNowPrice() {
        WebElement payableNowPrice = paymentDetailsModule.findElement(By.className("seleniumPayableNowPrice"));
        return Float.parseFloat(payableNowPrice.getText().replaceAll("[^0-9.]", ""));
    }

    public String getCardHolderName() {
        try {
            WebElement cardHolderName = paymentDetailsModule.findElement(By.className("seleniumFullCardHolderName"));
            return cardHolderName.getText();
        }
        catch (Exception e) {
            // no action
        }
        return null;
    }

    private String getCardNumber() {
        try {
            WebElement cardNumber = paymentDetailsModule.findElement(By.className("seleniumCardNumber"));
            return cardNumber.getText().replaceAll("[^0-9]", "");
        }
        catch (Exception e) {
            // no action
        }
        return null;
    }

    private String getExpiryDate() {
        try {
            return paymentDetailsModule.findElement(By.className("seleniumExpireDate")).getText();
        }
        catch (Exception e) {
            // no action
        }
        return null;
    }
}
