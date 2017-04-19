/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.confirm;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Page object of car confirmation page
 */
public class CarConfirmationPage extends AbstractUSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarConfirmationPage.class);

    private static final String XPATH_HOTWIRE_ITINERARY = //div[contains(@id, 'tileName-A3')]
            "//div[contains(@class, 'confirmCodes')]" +
                    "//strong[contains(text(), 'Itinerary:')]//..";
    private static final String XPATH_HOTWIRE_DISPLAY_NUMBER =
            "//span[contains(text(), 'silkCarItineraryNumber:')]//..";
    private static final String XPATH_DAMAGE_PROTECTION =
            "//div[contains(@class, 'summaryOfCharges')]//div[contains(text(), 'Rental Car Protection:')]" +
                    "//following-sibling::div[contains(@class, 'value')]";

    private static final String XPATH_MILEAGE =
            "//div[contains(@class, 'tripDetails')]//div//strong[contains(text(), 'Miles')]//following-sibling::div";

    private static final String XPATH_MODEL =
            "//div[contains(@class, 'tripDetails')]//div//strong[contains(text(), 'Models')]//following-sibling::div";

    private static final String XPATH_PEOPLE_CAPACITY =
            "//div[contains(@class, 'tripDetails')]//div//strong[contains(text(), 'Seating')]//following-sibling::div";

    private static final String XPATH_PACKAGE_CAPACITY =
            "//div[contains(@class, 'tripDetails')]//div//strong[contains(text(), 'Cargo')]//following-sibling::div";

    @FindBy(xpath = XPATH_HOTWIRE_ITINERARY)
    private WebElement hotwireItinerary;

    @FindBy(xpath = "//div[contains(text(), 'Protect your car ')]")
    private WebElement addCarInsuranceText;

    @FindBy(xpath = "//div[contains(text(), 'Discounts:')]")
    private WebElement discountText;

    @FindBy(xpath = "//span[contains(text(), 'silkCarBasePricePerDay:')]//..")
    private WebElement perDayPrice;

    @FindBy(xpath = "//span[contains(text(), 'silkCarTotalEstimatedDue:')]//..")
    private WebElement totalPrice;

    @FindBy(xpath = "//span[contains(text(), 'silkCarQuantity:')]//..")
    private WebElement rentalDaysCount;

    @FindBy(xpath = XPATH_DAMAGE_PROTECTION)
    private WebElement damageProtection;

    @FindBy(xpath = "//span[contains(text(), 'silkCarTotalTaxesAndSurcharges:')]//..")
    private WebElement estimatedTaxesAndFees;

    @FindBy(xpath = "//span[contains(text(), 'silkCarTypeName:')]//..")
    private WebElement carName;

    @FindBy(xpath = XPATH_MODEL)
    private WebElement carModels;

    @FindBy(xpath = XPATH_MILEAGE)
    private WebElement mileage;

    @FindBy(xpath = XPATH_PEOPLE_CAPACITY)
    private WebElement peopleCapacity;

    @FindBy(xpath = XPATH_PACKAGE_CAPACITY)
    private WebElement packageCapacity;

    @FindBy(xpath = "//div[@class = 'confirmCodes']/div[2]")
    private WebElement itineraryNumber;

    @FindBy(xpath = "//div[@class = 'confirmCodes']//div//strong[contains(text(), 'Hertz Tour No')]//..")
    private WebElement hertzTourNumber;

    @FindBy(xpath = "//div[@class = 'passengerDetails']/div[@class = 'colFirst']/div")
    private WebElement driverName;

    @FindBy(xpath = XPATH_HOTWIRE_DISPLAY_NUMBER)
    private WebElement displayNumber;

    @FindBy(css = ".confirmCopy")
    private WebElement confirmAndItineraryText;

    @FindBy(xpath = "//FORM[@id='confirmReceiptPrint']//DIV[@class='button fRt printRec']//A")
    private WebElement printReceipt;

    @FindBy(xpath = "//FORM[@id='confirmPrint']//DIV[@class='button fRt printItn']//A")
    private WebElement printTripDetails;

    @FindBy(xpath = "//div[@class='paymentInfo']//span[contains(text(), 'silkCarPurchaseDate:')]//..")
    private WebElement purchaseDate;

    @FindBy(xpath = "//div[@class='paymentInfo']//span[contains(text(), 'silkCarBillToName')]//..")
    private WebElement billedTo;

    @FindBy(xpath = "//div[@class='paymentInfo']//A")
    private WebElement contactEmail;

    @FindBy(xpath = "//div[@class='paymentInfo']//span[contains(text(), 'silkContactPhone')]//..")
    private WebElement contactPhone;

    @FindBy(xpath = "//div[@class='paymentInfo']//span[contains(text(), 'silkChargedTo')]//..")
    private WebElement cardNumber;

    @FindBy(xpath = "//div[@class='trip']//span[contains(text(), 'silkCarStartAirport:')]//..")
    private WebElement pickupLocationAirport;

    @FindBy(xpath = "//div[@class='trip']//span[contains(text(), 'silkCarStartCityState:')]//..")
    private WebElement pickupLocationCity;

    @FindBy(xpath = "//div[@class='trip']//span[contains(text(), 'silkCarStartDateTime:')]//..")
    private WebElement pickupDateTime;

    @FindBy(xpath = "//div[@class='trip']//span[contains(text(), 'silkCarEndAirport:')]//..")
    private WebElement dropoffLocationAirport;

    @FindBy(xpath = "//div[@class='trip']//span[contains(text(), 'silkCarEndCityState:')]//..")
    private WebElement dropoffLocationCity;

    @FindBy(xpath = "//div[@class='trip']//span[contains(text(), 'silkCarEndDateTime:')]//..")
    private WebElement dropoffDateTime;

    @FindBy(xpath = ".//div[@class='sideModuleButtons']//a[@class='shareItn']")
    private WebElement shareItinerary;

    public CarConfirmationPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.car.confirm.base", "tiles-def.account.car-purchase-details"});
        LOGGER.info(hotwireItinerary.getText() + " (Car was successfully booked)");
    }

    public CarConfirmationPage(WebDriver webdriver, String tile) {
        super(webdriver, new  String[] {tile});
        LOGGER.info(hotwireItinerary.getText() + " (Car was successfully booked)");
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.xpath(XPATH_HOTWIRE_ITINERARY);
    }

    public String getConfirmationCode() {
        String codesText = confirmAndItineraryText.getText();
        String subString = codesText.substring(codesText.indexOf("code:"));
        int startIndex = subString.indexOf(": ") + 2;
        int endIndex = subString.indexOf(" - Your");
        return subString.substring(startIndex, endIndex).trim();
    }

    public String getHotwireItinerary() {
        String codesText = confirmAndItineraryText.getText();
        String subString = codesText.substring(codesText.indexOf("Hotwire Itinerary:"));
        int startIndex = subString.indexOf(":");
        int endIndex = subString.indexOf("Thank");
        return subString.substring(startIndex + 1, endIndex).trim();
    }


    public Boolean verifyDiscountApplied() {
        try {
            return discountText.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public Boolean verifyCarInsuranceLogoAvaliable() {
        try {
            return addCarInsuranceText.isDisplayed();
        }
        catch (NoSuchElementException e) {
            LOGGER.info("While car purchase with non USD, non USA resident living in WA " +
                    "the car insurance banner will be suppressed ");
            return false;
        }
    }

    public CarSolutionModel getCarOptionsSet() {
        CarSolutionModel carSolutionModel = new CarSolutionModel();

        carSolutionModel.setCurrency(getCurrency());
        carSolutionModel.setPerDayPrice(getPerDayPrice());
        carSolutionModel.setRentalDaysCount(getRentalDaysCount());
        carSolutionModel.setTaxesAndFees(getTaxesAndFees());
        carSolutionModel.setDamageProtection(getDamageProtection());
        carSolutionModel.setTotalPrice(getTotalPrice());

        carSolutionModel.setMileage(getMileage());
        carSolutionModel.setCarName(getCarName());
        carSolutionModel.setCarModels(getCarModels());
        carSolutionModel.setPeopleCapacity(getPeopleCapacity());
        carSolutionModel.setLargePackageCapacity(getLargePackageCapacity());
        carSolutionModel.setSmallPackageCapacity(getSmallPackageCapacity());

        return carSolutionModel;
    }

    public float getPerDayPrice() {
        return Float.parseFloat(perDayPrice.getText().replaceAll("[^0-9.]", ""));
    }

    public float getTotalPrice() {
        return Float.parseFloat(totalPrice.getText().replaceAll("[^0-9.]", ""));
    }

    public String getCurrency() {
        String perDayCurrency = perDayPrice.getText().replaceAll("[0-9., ]", "").replaceAll("^[a-zA-Z]+:", "");
        String totalCurrency = totalPrice.getText().replaceAll("[0-9., ]", "").replaceAll("^[a-zA-Z]+:", "");

        if (perDayCurrency.equals(totalCurrency) && perDayCurrency != "") {
            return perDayCurrency;
        }
        return null;
    }

    public String getMileage() {
        String unlimited = "unlimited";
        if (mileage.getText().toLowerCase().contains(unlimited)) {
            return unlimited;
        }
        return mileage.getText().toLowerCase();
    }

    public int getRentalDaysCount() {
        return Integer.parseInt(rentalDaysCount.getText().replaceAll("[^0-9.]", ""));
    }

    public float getTaxesAndFees() {
        return Float.parseFloat(estimatedTaxesAndFees.getText().replaceAll("[^0-9.]", ""));
    }

    public Float getDamageProtection() {
        try {
            LOGGER.info("CONFIRMATION RENTAL CAR DAMAGE TEXT: " + damageProtection.getText());
            return new Float(damageProtection.getText().replaceAll("[^\\d.]", "").trim());
        }
        catch (NoSuchElementException e) {
            LOGGER.info("NoSuchElementException occurred parsing damage protection text.");
        }
        return null;
    }

    public String getCarName() {
        return carName.getText().replaceAll("^[a-zA-Z0-9]+:", "");
    }

    public String getDriverName() {
        return driverName.getText();
    }

    public String getCarModels() {
        return carModels.getText().replaceAll("[^a-zA-Z .,-]", " ").trim().replaceAll("( )+", " ");
    }

    public String getPeopleCapacityAsString() {
        LOGGER.info("SEATING TEXT: " + peopleCapacity.getText());
        return peopleCapacity.getText();
    }

    /**
     * Calculate total people capacity (like on results page)
     * Possible values from database (car_type table)
     * 4 adults
     * 2 adults, 2 children
     * 2 adults
     * 5 adults
     * 4 adults or more
     * 7 adults
     *
     * @return int
     */
    public Integer getPeopleCapacity() {
        String[] seatingTexts = getPeopleCapacityAsString().split(",");
        int capacity = 0;
        for (String part : seatingTexts) {
            try {
                capacity += new Integer(part.replaceAll("[^\\d]", "").trim()).intValue();
            }
            catch (NumberFormatException e) {
                capacity += 0;
            }
        }
        return capacity;
    }

    public String getPackageCapacityAsString() {
        return packageCapacity.getText();
    }

    /**
     * Possible values
     * 5 large suitcases
     * 3 large, 3 small suitcases
     * 1 large, 2 small suitcases or more
     * 2 large, 1 small suitcase
     * 1 large, 1 small suitcase
     * 2 large suitcases
     * 1 large, 3 small suitcases
     * 1 large, 2 small suitcases
     * 3 large, 2 small suitcases
     * 1 large, 2 small suitcase
     * 2 large, 2 small suitcases
     * 3 large suitcases
     *
     * @return Integer
     */
    public Integer getLargePackageCapacity() {
        try {
            String[] arr = getPackageCapacityAsString().replaceAll("[^0-9,]", "").trim().split(",");
            return Integer.parseInt(arr[0].trim());
        }
        catch (Exception e) {
            return null;
        }
    }

    public Integer getSmallPackageCapacity() {
        try {
            String[] arr = getPackageCapacityAsString().replaceAll("[^0-9,]", "").trim().split(",");
            return Integer.parseInt(arr[1].trim());
        }
        catch (Exception e) {
            return null;
        }
    }

    public String getItineraryNumber() {
        return hotwireItinerary.getText().replaceFirst("(.*):", "").trim();
    }

    public String getHertzTourNumber() {
        return hertzTourNumber.getText();
    }

    public String getDisplayNumber() {
        return displayNumber.getText().replaceFirst("(.*):", "").replaceFirst(" -(.*)", "").trim();
    }

    public String getInfoMsg() {
        return getWebDriver().findElement(By.cssSelector("div.confirmMessages p")).getText();
    }

    public WebElement getPrintReceipt() {
        return printReceipt;
    }

    public String getPurchaseDate() {
        return purchaseDate.getText();
    }

    public String getContactEmail() {
        return contactEmail.getText();
    }

    public String getCardNumber() {
        return cardNumber.getText();
    }

    public String getPickupLocationAirport() {
        return pickupLocationAirport.getText();
    }

    public String getPickupLocationCity() {
        return pickupLocationCity.getText();
    }

    public String getPickupDate() {
        String pDateTime = pickupDateTime.getText();
        String[] splits;
        if (pDateTime.contains("at")) {
            splits = pDateTime.split("at");
            return splits[0].trim();
        }
        else {
            throw new IllegalArgumentException("String " + pDateTime + " does not contain 'at'");
        }
    }

    public String getPickupTime() {
        String pDateTime = pickupDateTime.getText();
        String[] splits;
        if (pDateTime.contains("at")) {
            splits = pDateTime.split("at");
            return splits[1].trim();
        }
        else {
            throw new IllegalArgumentException("String " + pDateTime + " does not contain 'at'");
        }
    }

    public String getDropoffLocationAirport() {
        return dropoffLocationAirport.getText();
    }

    public String getDropoffLocationCity() {
        return dropoffLocationCity.getText();
    }

    public String getDropoffDate() {
        String dDateTime = dropoffDateTime.getText();
        String[] splits;
        if (dDateTime.contains("at")) {
            splits = dDateTime.split("at");
            return splits[0].trim();
        }
        else {
            throw new IllegalArgumentException("String " + dDateTime + " does not contain 'at'");
        }
    }

    public String getDropoffTime() {
        String dDateTime = dropoffDateTime.getText();
        String[] splits;
        if (dDateTime.contains("at")) {
            splits = dDateTime.split("at");
            return splits[1].trim();
        }
        else {
            throw new IllegalArgumentException("String " + dDateTime + " does not contain 'at'");
        }
    }

    public WebElement getShareItinerary() {
        return shareItinerary;
    }

    public WebElement getPrintTripDetails() {
        return printTripDetails;
    }

    public String getContactPhone() {
        return contactPhone.getText();
    }

    public String getBilledTo() {
        return billedTo.getText();
    }


}
