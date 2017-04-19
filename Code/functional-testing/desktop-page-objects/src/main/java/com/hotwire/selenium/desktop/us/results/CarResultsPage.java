/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lryzhikov
 * Date: 5/3/12
 * Time: 12:39 PM
 *
 * This class represent a us car results page.
*/
public class CarResultsPage extends AbstractUSPage {

    private static final String OPAQUE_RATES_DISCLAIMER_TEXT =
        "* Rates are shown in US dollars. Total cost for Hotwire Hot Rates includes applicable tax recovery charges " +
        "and fees. Total cost for retail rate reservations is an estimate based on the agency";

    private static final String RETAIL_RATES_DISCLAIMER_TEXT =
        "* Rates are shown in US dollars. Total cost for retail rate reservations is an estimate based on the agency";

    private static final String FOOTNOTES =
        "** The makes/models shown are examples only. We are unable to guarantee a specific make/model. " +
        "Actual makes/models are subject to availability and vary by supplier.";

    private static final String FOOTNOTES_SAVINGS =
        "Savings based on average published rate we\'ve found on leading retail travel sites in the last 24-48 hours " +
        "for the same booking date, location, and car class. Availability is limited and rates are subject to " +
        "change. Prices are dynamic and vary based on date of booking, length of itinerary, and type of product " +
        "booked. There is no guarantee that these savings or rates will be in effect at the time of your search. " +
        "Availability is limited. Car rental rate excludes taxes and fees";


    private static final String RETAIL_SELECTION_TYPE = "retail";
    private static final String OPAQUE_SELECTION_TYPE = "opaque";

    private static final String CSS_SOLUTION_CENTS_VALUE = "div.price strong span.cents, div.priceWrapper .cents";
    private static final String XPATH_WHAT_IS_HOT_RATE = "//*/img[@alt='Hotwire Hot Rate']";

    private static final String XPATH_OPAQUE_RATES_DISCLAIMER = "//div[contains(@class,'resultsFooter')]" +
            "//div[contains(text(), '" + OPAQUE_RATES_DISCLAIMER_TEXT + "')]";

    private static final String XPATH_RETAIL_RATES_DISCLAIMER = "//div[contains(@class,'resultsFooter')]" +
            "//div[contains(text(), '" + RETAIL_RATES_DISCLAIMER_TEXT + "')]";

    @FindBy(xpath = "id('tileName-discountCode')/div[1]")
    private WebElement discountCodeNote;

    @FindBy(xpath = XPATH_WHAT_IS_HOT_RATE)
    private WebElement whatIsHotRateModule;

    @FindBy(xpath = XPATH_OPAQUE_RATES_DISCLAIMER)
    private WebElement opaqueRatesDisclaimer;

    @FindBy(xpath = XPATH_RETAIL_RATES_DISCLAIMER)
    private WebElement retailRatesDisclaimer;

    @FindBy(xpath = "//div[contains(@class,'resultsFooter')]//div")
    private WebElement footNotes;

    @FindBy(css = "div#tileName-results div.resultDetails div.strikeThrough label.price")
    private List<WebElement> strikeThroughPrices;

    @FindBy(css = "div.layoutColumnLeft div#tileName-carResultPageBanner iframe")
    private WebElement supplierAd;

    private WebElement appropriateSearchCandidate;
    /**
     * This flag says us that we should select car with data-prepaid=true
     * If NULL do not pay attention when selecting
     */
    private Boolean getPrepaidSolution;

    public CarResultsPage(final WebDriver webdriver) {
        super(webdriver, "tiles-def.car.results", DEFAULT_SEARCH_LAYER_ID);
    }

    @SuppressWarnings("unused")
    public void select(final Integer resultNumberToSelect, final String selectionType) {
        setAppropriateSearchCandidate(resultNumberToSelect, selectionType);
        getAppropriateSearchCandidate().findElement(By.cssSelector("form")).click();
    }

    /**
     * Search for a car to select.
     *
     * @param resultNumberToSelect
     * @param selectionType
     * @return WebElement if solution was found.
     * @throws NoSuchElementException if solution was not found
     */
    public void setAppropriateSearchCandidate(final Integer resultNumberToSelect, final String selectionType) {
        String prepaid = "";
        if (getPrepaidSolution != null) {
            prepaid = "[contains(@data-prepaid, '" + getPrepaidSolution + "')]";
        }
        try {

            StringBuilder xpath = new StringBuilder();
            xpath.append("//input[contains(@data-retail, '");
            xpath.append(RETAIL_SELECTION_TYPE.equals(selectionType)).append("')]");
            xpath.append(prepaid).append("[");
            xpath.append(resultNumberToSelect).append("]/../../..");

            this.appropriateSearchCandidate = getWebDriver().findElement(By.xpath(xpath.toString()));
        }
        catch (NoSuchElementException e) {
            appropriateSearchCandidate = null;
            StringBuilder sb = new StringBuilder();
            sb.append((RETAIL_SELECTION_TYPE.equals(selectionType)) ? "RETAIL " : "OPAQUE ");
            sb.append("CAR");
            if (getPrepaidSolution != null) {
                sb.append(" with prepaid payment type");
            }
            sb.append(" not found on results page");
            logger.info(sb.toString());
        }
    }

    public void setPrepaidSolution(Boolean getPrepaidSolution) {
        this.getPrepaidSolution = getPrepaidSolution;
    }

    /**
     * @param selectionType String {retail|opaque}
     * @return list of car solutions by type
     */
    public List<WebElement> getListOfSolutionsByType(String selectionType) {
        StringBuilder xpath = new StringBuilder("//input[contains(@data-retail, '");
        xpath.append(RETAIL_SELECTION_TYPE.equals(selectionType)).append("')]/../../..");

        return getWebDriver().findElements(By.xpath(xpath.toString()));
    }

    /**
     * @return car fulloptions of selected solution
     */
    public CarSolutionModel getSolutionOptionsSet() {
        CarSolutionModel carSolutionModel = new CarSolutionModel();

        // Keep mileage, capacity, models and etc from Results page.
        carSolutionModel.setDisplayNumber(getSolutionDisplayNumber());
        carSolutionModel.setPerDayPrice(getSolutionPerDayPrice());
        carSolutionModel.setTotalPrice(getSolutionTotalPrice());
        carSolutionModel.setCurrency(getSolutionCurrency());
        carSolutionModel.setCarName(getSolutionCarName());
        carSolutionModel.setCarModels(getSolutionCarModels());
        carSolutionModel.setMileage(getSolutionMileage());
        carSolutionModel.setDistance(getSolutionDistance());
        carSolutionModel.setPeopleCapacity(getSolutionPeopleCapacity());
        carSolutionModel.setLargePackageCapacity(getSolutionLargePackageCapacity());
        carSolutionModel.setSmallPackageCapacity(getSolutionSmallPackageCapacity());

        return carSolutionModel;
    }

    /**
     * @return perDayPrice of selected item.
     */
    public float getSolutionPerDayPrice() {
        String perDayPrice = "div.price strong, div.priceWrapper .pricePerDay";
        String price = getAppropriateSearchCandidate().findElement(By.cssSelector(perDayPrice)).getText();
        String cents = getAppropriateSearchCandidate().findElement(By.cssSelector(CSS_SOLUTION_CENTS_VALUE)).getText();

        // remove currency chars and add dot before cents
        return Float.parseFloat(price.replaceAll("[^0-9]", "").replaceAll(cents + "$", "." + cents));
    }

    /**
     * @return DISPLAY NUMBER of selected solution
     */
    public String getSolutionDisplayNumber() {
        By by = By.cssSelector("div.refNumber");
        return getAppropriateSearchCandidate().findElement(by).getText()
                .replace("Ref No. ", "").replaceAll("^[a-zA-Z0-9]+:", "").replaceAll("^1", "");
    }

    /**
     * @return total price of selected solution
     */
    public float getSolutionTotalPrice() {
        String cssTotalPrice = "div.priceInfo strong.priceColor, label.priceTotalWrapper .priceTotal";
        return Float.parseFloat(
                getAppropriateSearchCandidate()
                        .findElement(By.cssSelector(cssTotalPrice)).getText().replaceAll("[^0-9.]", ""));
    }

    /**
     * @return sign of currency
     */
    public String getSolutionCurrency() {
        By by = By.cssSelector("div.price strong, div.priceWrapper .pricePerDay");
        return getAppropriateSearchCandidate().findElement(by).getText().replaceAll("[0-9.]", "");
    }

    /**
     * @return true if it's a prepaid retail car and FALSE in any other ways.
     */
    public boolean isPrepaid() {
        return "true".equals(getAppropriateSearchCandidate().getAttribute("data-prepaid"));
    }

    /**
     * @return car name (Economy car, Compact car, etc) for selected solution
     */
    public String getSolutionCarName() {
        By by = By.cssSelector("div.carname, div.carDetails .carname");
        return getAppropriateSearchCandidate().findElement(by).getText().replaceAll("^[a-zA-Z0-9]+:", "");
    }

    /**
     * Car names
     * Nissan Versa, Toyota Yaris, or similar**
     * Toyota Corolla, Nissan Sentra, or similar**
     * Ford Crown Victoria, Buick Lucerne, or similar**
     * etc
     *
     * @return car models for selected solution
     */
    public String getSolutionCarModels() {
        By by = By.cssSelector("div.models, div.additionalCarDetails .models");
        return getAppropriateSearchCandidate().findElement(by).getText().replace('*', ' ').trim();
    }

    /**
     * @return car mileage for selected solution
     */
    public String getSolutionMileage() {
        By by = By.cssSelector("div.miles, div.additionalCarDetails .miles");
        String value = getAppropriateSearchCandidate().findElement(by).getText().replaceAll("\\n", " ");
        String unlimited = "unlimited";
        if (value.toLowerCase().contains(unlimited)) {
            return unlimited;
        }
        return value.toLowerCase();
    }

    /**
     * @return distance for selected solution
     */
    public String getSolutionDistance() {
        By by = By.cssSelector("div.distance, div.locationDetails .distance");
        return getAppropriateSearchCandidate().findElement(by).getText().replaceAll("\\n", " ");
    }

    /**
     * @return people capacity for selected solution
     */
    public Integer getSolutionPeopleCapacity() {
        try {
            By by = By.cssSelector("div.capacity .c1, div.additionalCarDetails .c1");
            return Integer.parseInt(getAppropriateSearchCandidate().findElement(by).getText());
        }
        catch (NoSuchElementException e) {
            // no action
        }
        return null;
    }

    /**
     * @return large package capacity for selected solution
     */
    public Integer getSolutionLargePackageCapacity() {
        try {
            By by = By.cssSelector("div.capacity .c2, div.additionalCarDetails .c2");
            return Integer.parseInt(getAppropriateSearchCandidate().findElement(by).getText());
        }
        catch (Exception e) {
            // no action
        }
        return null;
    }

    /**
     * @return small package capacity for selected solution
     */
    public Integer getSolutionSmallPackageCapacity() {
        try {
            By by = By.cssSelector("div.capacity .c3, div.additionalCarDetails .c3");
            return Integer.parseInt(getAppropriateSearchCandidate().findElement(by).getText());
        }
        catch (Exception e) {
            // no action
        }
        return null;
    }

    public Boolean verifyDiscountNoteShown() {
        try {
            discountCodeNote.isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public WebElement getAppropriateSearchCandidate() {
        return appropriateSearchCandidate;
    }

    public WebElement getWhatIsHotRateModule() {
        return whatIsHotRateModule;
    }

    public WebElement getOpaqueRatesDisclaimer() {
        return opaqueRatesDisclaimer;
    }

    public WebElement getRetailRatesDisclaimer() {
        return retailRatesDisclaimer;
    }

    public boolean checkFootNotes() {
        return footNotes.getText().replaceAll("\\n", "").contains(FOOTNOTES);
    }

    public boolean checkFootSavingsNote() {
        return footNotes.getText().replaceAll("\\n", "").contains(FOOTNOTES_SAVINGS);
    }

    public boolean strikeThroughPriceExists() {
        return strikeThroughPrices.size() > 0;
    }

    /**
     * We currently have commitments for advertising expenditures to our car suppliers
     * An IAB standard 180 x 150 pixel banner will be used.
     * RTC 4362
     *
     * @return true if module was displayed
     */
    public boolean checkSupplierAdBlock() {
        try {
            return supplierAd.isDisplayed() &&
                    "180".equals(supplierAd.getAttribute("width")) && "150".equals(supplierAd.getAttribute("height"));
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
