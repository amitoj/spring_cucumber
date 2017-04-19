/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car.fragments;

import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: achotemskoy
 * Date: 1/9/15
 * Time: 3:06 PM
 * <p></p>
 * This class represents one car solution from list of car results.
 * It is used to move logic of setting values from CarResultsPage to separate class.
 * You can use it to get one ".resultDetails" element on car results page
 * To have a list of solutions and select cheapest/prepaid/retail/etc use CarSolutionFragmentsList class.
 * <p></p>
 * Also this class contains carSolutionModel inside itself, so you can get model from the fragment for future use.
 */
public class CarSolutionFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSolutionFragment.class.getName());

    //Raw, unwrapped WebElement of solution. Used to find all the data for it.
    private WebElement carSolutionWebElement;
    /**
     * Common data for car solution. These elements describing different parameters for single car solution.
     * Used for grabbing info from page and filling CarSolutionModel with them.
     * For other CarResultsPage implementations can be redefined.
     */
    private float perDayPrice;
    private float totalPrice;
    private float strikeThroughPrice;
    private String currency;
    private int peopleCapacity;
    private int largePackCapacity;
    private int smallPackCapacity;
    private String distanceDescription;
    private String mileage;
    private String model;
    //Name of car type - like Mini (Manual, no A/C) , Standart or Standard Wagon (Manual)
    private String carTypeName;
    //Code of car type - SPAR, ECAR, CCAR and others. It is carCd code.
    private String carTypeCode;
    private String referenceNumber;
    private boolean isOpaque;
    //prepaid retail solution means that you should pay first for car (credit card required)
    private boolean isPrepaid;
    private boolean isLowestPrice;
    private boolean isAirportLocation;
    private CarSolutionModel carSolutionModel;

    public CarSolutionFragment(WebElement carSolutionWebElement) {
        this.carSolutionWebElement = carSolutionWebElement;
        setAllFragmentData();
    }

    public CarSolutionModel select() {
        setCarSolutionModel();
        carSolutionWebElement.findElement(
                By.cssSelector(".continueBtnHover, .continueBtn")).click();
        LOGGER.info(getCarSolutionModel().toString());
        return getCarSolutionModel();
    }

    //Car solution model is used to compare info between results and billing or confirmation.
    private void setCarSolutionModel() {
        carSolutionModel = new CarSolutionModel();
        carSolutionModel.setPerDayPrice(perDayPrice);
        carSolutionModel.setTotalPrice(totalPrice);
        carSolutionModel.setCurrency(currency);
        carSolutionModel.setPeopleCapacity(peopleCapacity);
        carSolutionModel.setLargePackageCapacity(largePackCapacity);
        carSolutionModel.setSmallPackageCapacity(smallPackCapacity);
        carSolutionModel.setDistance(distanceDescription);
        carSolutionModel.setMileage(mileage);
        carSolutionModel.setCarModels(model);
        carSolutionModel.setCarName(carTypeName);
        carSolutionModel.setCdCode(carTypeCode);
        carSolutionModel.setOpaque(isOpaque);
        carSolutionModel.setDisplayNumber(referenceNumber);
        carSolutionModel.setPrepaid(isPrepaid);
        carSolutionModel.setLowestPrice(isLowestPrice);

    }

    private CarSolutionModel getCarSolutionModel() {
        return this.carSolutionModel;
    }

    /**
     * SETTERS
     * Sets all information about solution into object.
     * This methods are private, because usually you don't need to set value for solution manually.
     * All these setters are executed in constructor, so solution will have all these info in object fields at creation.
     * <p></p>
     * Not quite sure if this possible when we have solution without some information,
     * but better to handle this with try/catch, because this was in previous version.
     */
    private void setAllFragmentData() {
        setPerDayPrice();
        setTotalPrice();
        setStrikeThroughPrice();
        setCurrency();
        setPeopleCapacity();
        setLargePackCapacity();
        setSmallPackCapacity();
        setDistanceDescription();
        setMileage();
        setModel();
        setCarTypeName();
        setCarTypeCode();
        setIsOpaque();
        setReferenceNumber();
        setIsPrepaid();
        setIsLowestPrice();
        setIsAirportLocation();
    }

    private void setPerDayPrice() {
        String price = carSolutionWebElement.findElement(By.cssSelector("div.priceWrapper")).getText();
        String cents = carSolutionWebElement.findElement(By.cssSelector("div.priceWrapper .cents")).getText();

        // removing currency chars and adding dot before cents
        perDayPrice = Float.parseFloat(price.replaceAll("[^0-9]", "").replaceAll(cents + "$", "." + cents));
    }

    private void setTotalPrice() {
        totalPrice = Float.parseFloat(
                carSolutionWebElement
                        .findElement(By.cssSelector("label.priceTotalWrapper .priceTotal")).getText()
                        .replaceAll("[^0-9.]", ""));

    }

    public void setStrikeThroughPrice() {
        try {
            strikeThroughPrice = Float.parseFloat(
                carSolutionWebElement.findElement(By.cssSelector("div.strikeThrough label.price")).getText()
                        .replaceAll("[^0-9]", ""));
        }
        catch (Exception ignored) {
            LOGGER.debug("Cannot find Strike Through Price in this fragment ", ignored);
        }

    }

    private void setCurrency() {
        try {
            currency = carSolutionWebElement
                    .findElement(By.cssSelector("div.priceWrapper .pricePerDay"))
                    .getText().replaceAll("[0-9.]", "");
        }
        catch (NoSuchElementException ignored) {
            LOGGER.debug("Cannot find Currency Sign in this fragment. Currency will be $.", ignored);
            currency = "$";
        }
    }

    private void setPeopleCapacity() {
        try {
            peopleCapacity = Integer.parseInt(carSolutionWebElement
                    .findElement(By.cssSelector("div.additionalCarDetails .c1")).getText().replaceAll("[^0-9]", ""));
        }
        catch (Exception ignored) {
            LOGGER.debug("Cannot find People Capacity in this fragment ", ignored);
        }
    }

    private void setLargePackCapacity() {
        try {
            largePackCapacity = Integer.parseInt(carSolutionWebElement
                    .findElement(By.cssSelector("div.additionalCarDetails .c2")).getText().replaceAll("[^0-9]", ""));
        }
        catch (Exception ignored) {
            LOGGER.debug("Cannot find Large Pack Capacity in this fragment ", ignored);
        }

    }

    private void setSmallPackCapacity() {
        try {
            smallPackCapacity = Integer.parseInt(carSolutionWebElement
                    .findElement(By.cssSelector("div.additionalCarDetails .c3")).getText().replaceAll("[^0-9]", ""));
        }
        catch (Exception ignored) {
            LOGGER.debug("Cannot find Small Pack Capacity in this fragment. ", ignored);
        }

    }

    private void setDistanceDescription() {
        distanceDescription = carSolutionWebElement
                .findElement(By.cssSelector("div.locationDetails .distance")).getText().replaceAll("\\n", " ");
    }

    private void setMileage() {
        String value = carSolutionWebElement
                .findElement(By.cssSelector("div.additionalCarDetails .miles")).getText().replaceAll("\\n", " ");
        mileage = (value.toLowerCase().contains("unlimited")) ? "unlimited" : value.toLowerCase();
    }

    private void setModel() {
        model = carSolutionWebElement.findElement(By.cssSelector("div.additionalCarDetails .models"))
                .getText().replaceAll("[^a-zA-Z .,-]", " ").trim().replaceAll("( )+", " ");
    }

    private void setCarTypeName() {
        carTypeName = carSolutionWebElement
                .findElement(By.cssSelector("div.carDetails .carname")).getText().replaceAll("^[a-zA-Z0-9]+:", "");
    }

    private void setCarTypeCode() {
        carTypeCode = carSolutionWebElement.findElement(By.className("continueBtn"))
                .getAttribute("id").replaceAll("carType", ""); //id attribute looks like this - id="carTypeCCAR".
    }

    private void setReferenceNumber() {
        referenceNumber = carSolutionWebElement.findElement(By.cssSelector("div.refNumber"))
                .getText().replace("Ref No. ", "").replaceAll("^[a-zA-Z0-9]+:", "").replaceAll("^1", "");
    }

    private void setIsOpaque() {
        //In case of opaque solution there will be div with "Hotwire Hot Rate".
        //We can assume that this solution is opaque if that div exists.
        try {
            isOpaque = carSolutionWebElement
                    .findElement(By.cssSelector(".hotRateMsg"))
                    .isDisplayed();
        }
        catch (NoSuchElementException ignored) {
            LOGGER.debug("Cannot Hot Rate Message in this fragment. That means this is retail solution", ignored);
            isOpaque = false;
        }
    }

    private void setIsPrepaid() {
        isPrepaid = Boolean.parseBoolean(carSolutionWebElement.getAttribute("data-prepaid"));
    }

    private void setIsLowestPrice() {
        //In case of lowest price solution there will be div with "Our Lowest Price".
        //We can assume that this solution is lowest price if that div exists.
        //In most cases that means that the price for solution is cheapest from all search results.
        //Can be more that one lowest price solution - that happens when we have same price.
        try {
            isLowestPrice = carSolutionWebElement
                    .findElement(By.cssSelector(".lowestPriceIcon"))
                    .isDisplayed();
        }
        catch (NoSuchElementException ignored) {
            LOGGER.debug("Cannot find Lowest Price label in this fragment. Solution is not lowest price.", ignored);
            isLowestPrice = false;
        }
    }

    private void setIsAirportLocation() {
        try {
            isAirportLocation = carSolutionWebElement.findElement(By.cssSelector(".airportLocation")).isDisplayed();
        }
        catch (NoSuchElementException ignored) {
            LOGGER.debug("Cannot find Is Airport Location in this fragment ", ignored);
            isAirportLocation = false;
        }
    }

    //Getters for the data of this fragment.

    /**
    *If you need just raw webElement of solution - use this.
     * @return WebElement for solution. You can do something with it.
    */
    public WebElement getCarSolutionWebElement() {
        return carSolutionWebElement;
    }

    public float getPerDayPrice() {
        return perDayPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public float getStrikeThroughPrice() {
        return strikeThroughPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public int getPeopleCapacity() {
        return peopleCapacity;
    }

    public int getLargePackCapacity() {
        return largePackCapacity;
    }

    public int getSmallPackCapacity() {
        return smallPackCapacity;
    }

    public String getDistanceDescription() {
        return distanceDescription;
    }

    public String getMileage() {
        return mileage;
    }

    public String getModel() {
        return model;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public String getCarTypeCode() {
        return carTypeCode;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public boolean isOpaque() {
        return isOpaque;
    }

    public boolean isPrepaid() {
        return isPrepaid;
    }

    public boolean isLowestPrice() {
        return isLowestPrice;
    }

    public boolean isAirportLocation() {
        return isAirportLocation;
    }

}
