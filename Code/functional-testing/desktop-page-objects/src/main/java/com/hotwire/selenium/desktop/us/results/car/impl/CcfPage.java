/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results.car.impl;

import java.util.List;

import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import com.hotwire.testing.UnimplementedTestException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * User: v-vzyryanov
 * Date: 1/16/13
 * Time: 8:18 AM
 * <p/>
 * This class is no longer supported.
 * CCF project is now on hold(more than a year already)
 * And there is no plans to support it in future.
 */

public class CcfPage extends CarResultsPageTemplate {
    public static final String ID_SEARCH_UPD_LAYER = "#carSearchingLayer-panel_c";
    protected static final String RETAIL_SELECTION_TYPE = "retail";
    protected static final String OPAQUE_SELECTION_TYPE = "opaque";
    private static final String CSS_PAYPAL_RADIOBUTTON = "input#payPal";
    private static final String CSS_CREDIT_CARD_RADIOBUTTON = "input#creditCard";
    private static final String[] PAGE_TILE_DEFINITION =
            new String[]{"tile.car-spa.results", "tile.car-spa.polling", "tile.car-spa.noResults"};
    private static final String CSS_INSURANCE = "div#carSpaInsurance";
    private static final String CSS_CREDIT_CARD_NUM = "input[id*='CreditCardNumber']";
    private static final String CLASS_NO_RESULTS_MSG = "noResultsMsg";
    private static final String NO_RESULTS_MSG  = "We couldn't find any cars from that search\n" +
                                                    "Try a nearby location or different dates.";
    private static final String CSS_UPDATE_BUTTON = "button[class*='seleniumContinueButton']";
    /**
    CSS SELECTORS for common elements.
    */
    private String cssDayPrice = "div.perDay .amount";
    private String cssTotalPrice = "div.total";
    private String cssCurrency = "div.perDay .amount";
    private String cssCarname = "h3.carTypeName";
    private String cssModel = "div.carExample";
    private String cssPeopleCapacity = "div.carOptions  li.seating";
    private String cssLargePackCapacity = "div.carOptions  li.trunk";
    private String cssRefNumber = "div#tileName-resultsLiveChat span#referenceNumberResults";
    private String cssPhoneNum = "div#tileName-resultsLiveChat div.phone";
    private String cssLiveChat = "div#tileName-resultsLiveChat a.chatLink";
    private String opaqueIdentifier = "hotRate";
    private String cssLowestPrice = "a.lowestPrice";
    private WebElement appropriateSearchCandidate;


    public CcfPage(WebDriver webDriver) {
        super(webDriver, PAGE_TILE_DEFINITION);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.xpath("//a[contains(@class, 'yui3-g result')]");
    }

    @Override
    public String getDesignId() {
        return "vt.CCF13=04";
    }
    public WebElement getAppropriateSearchCandidate() {
        return appropriateSearchCandidate;
    }
    public void setAppropriateSearchCandidate(WebElement solution) {
        this.appropriateSearchCandidate = solution;
    }
    public void setPrepaidSolution(Boolean getPrepaidSolution) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void select() {
        CarSolutionModel solution = getSolutionOptionsSet();
        LOGGER.info("Selecting [Opq:" + solution.isOpaque() + "] a car... " + solution);
        getAppropriateSearchCandidate().click();
    }

    public void selectByCdCode(String carCdCode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void selectCarByTypeAndCdCode(String carType, String carCdCode) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isOpaque() {
        return getAppropriateSearchCandidate().getAttribute("class").contains(opaqueIdentifier);
    }

    public String getCdCode() {
        return getAppropriateSearchCandidate().getAttribute("id").replaceAll("carType", "");
    }



//    @Override
//    public float getSolutionPerDayPrice() {
//        String price = getAppropriateSearchCandidate().findElement(By.cssSelector(cssDayPrice)).getText();
//        return Float.parseFloat(price.replaceAll("[^0-9.]", ""));
//    }

    public String getReferenceNumber() {
        return getWebDriver().findElement(By.cssSelector(cssRefNumber)).getText();
    }

    public String getPhoneNumber() {
        return getWebDriver().findElement(By.cssSelector(cssPhoneNum)).getText();
    }

    public void openLiveChat() {
        getWebDriver().findElement(By.cssSelector(cssLiveChat)).click();
    }

    public String getSolutionMileage() {
        return "unlimited";
    }
    public String getSolutionDistance() {
        return "not defined";
    }

    public Integer getSolutionPeopleCapacity() {
        try {
            return Integer.parseInt(getAppropriateSearchCandidate()
                    .findElement(By.cssSelector(cssPeopleCapacity)).getText().replaceAll("[^0-9]", ""));
        }
        catch (NoSuchElementException e) { /* No-op */ }
        return null;
    }

    public Integer getSolutionLargePackageCapacity() {
        try {
            return Integer.parseInt(getAppropriateSearchCandidate()
                    .findElement(By.cssSelector(cssLargePackCapacity)).getText().replaceAll("[^0-9]", ""));
        }
        catch (Exception e) { /* No-op */ }
        return null;
    }
    public List<WebElement> getStrikeThroughPrices() {
        throw new UnimplementedTestException("IMPLEMENT ME " + getDesignId());
    }

    public boolean isAirportLocation(WebElement appropriateSearchCandidate) {
        try {
            appropriateSearchCandidate.findElement(By.cssSelector(".airportLocation"));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<String> getCarLocationDetails() {
        throw new UnimplementedTestException("IMPLEMENT ME " + getDesignId());
    }

    public String getSolutionDisplayNumber() {
        return getWebDriver().findElement(By.cssSelector(cssRefNumber)).getText();
    }

    public float getSolutionTotalPrice() {
        return Float.parseFloat(
                getAppropriateSearchCandidate()
                        .findElement(By.cssSelector(cssTotalPrice)).getText().replaceAll("[^0-9.]", ""));
    }

    public String getSolutionCurrency() {
        try {
            return getAppropriateSearchCandidate()
                    .findElement(By.cssSelector(cssCurrency))
                    .getText().replaceAll("[0-9.]", "");
        }
        catch (NoSuchElementException e) {
            // In some version of results page, US currency has no element that can be used. Default to $.
            return "$";
        }
    }

    public String getSolutionCarName() {
        return getAppropriateSearchCandidate()
                .findElement(By.cssSelector(cssCarname)).getText().replaceAll("^[a-zA-Z0-9]+:", "");
    }

    /**
     * @param selectionType String {retail|opaque}
     * @return list of car solutions by type
     */
    public List<WebElement> getListOfSolutionsByType(String selectionType) {
        if (selectionType.isEmpty()) {
            return getFullSolutionSet();
        }
        return getWebDriver().findElements(By.xpath("//a[contains(@class, 'yui3-g result')]" +
                getOpaqueRetail(selectionType)));
    }

    public List<WebElement> getFullSolutionSet() {
        return getWebDriver().findElements(By.xpath("//a[contains(@class, 'yui3-g result')]"));
    }


    public CarSolutionModel getSolutionOptionsSet() {
        CarSolutionModel carSolutionModel = new CarSolutionModel();

        carSolutionModel.setOpaque(isOpaque());
        carSolutionModel.setCdCode(getCdCode());
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

        return carSolutionModel;
    }

    private String getOpaqueRetail(String selectionType) {
        return RETAIL_SELECTION_TYPE.equals(selectionType) ?
                "[not(contains(@class, 'hotRate'))]" : "[contains(@class, 'hotRate')]";
    }

    public boolean isLiveChatDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(cssLiveChat)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void choosePayPalPaymentMethod() {
        getWebDriver().findElement(By.cssSelector(CSS_PAYPAL_RADIOBUTTON)).click();
    }

    public void chooseCreditCardPaymentMethod() {
        getWebDriver().findElement(By.cssSelector(CSS_CREDIT_CARD_RADIOBUTTON)).click();
    }

    public WebElement getInsurance() {
        return getWebDriver().findElement(By.cssSelector(CSS_INSURANCE));
    }

    public WebElement getCreditCardField() {
        return getWebDriver().findElement(By.cssSelector(CSS_CREDIT_CARD_NUM));
    }

    public CarSolutionModel getSelectedOptionSet() {
        CarSolutionModel solution = new CarSolutionModel();
        solution.setLowestPrice(isLowestPriceForSelectedSolution());
        return solution;
    }

    public boolean isPrepaid() {
        return "true".equals(getAppropriateSearchCandidate().getAttribute("data-prepaid"));
    }

    public String getSolutionCarModels() {
        return getAppropriateSearchCandidate()
                .findElement(By.cssSelector(cssModel)).getText().
                        replaceAll("[^a-zA-Z .,-]", " ").trim().replaceAll("( )+", " ");
    }

    public float getSolutionPerDayPrice() {
        return Float.parseFloat(getAppropriateSearchCandidate()
                .findElement(By.cssSelector(cssDayPrice)).
                        getText().replaceAll("[^0-9.]", ""));
    }

    public boolean isLowestPriceForSelectedSolution() {
        return getWebDriver().findElement(By.cssSelector("a.selected")).getAttribute("class").contains("lowestPrice");
    }

    public void clickUpdate() {
        getWebDriver().findElement(By.cssSelector(CSS_UPDATE_BUTTON)).click();
    }
}
