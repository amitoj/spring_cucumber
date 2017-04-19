/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.results;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.row.models.CarIntlSolutionModel;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author v-ypuchkova
 * @since 2012.10
 */
public class CarResultsPage extends AbstractRowPage {

    private WebElement selectedCarResult;

    public CarResultsPage(final WebDriver webdriver) {
        super(webdriver, "tile.car.results*", DEFAULT_SEARCH_LAYER_ID);
    }

    public CarIntlSolutionModel getSolutionOptionsSet() {
        CarIntlSolutionModel carSolutionModel = new CarIntlSolutionModel();

        carSolutionModel.setTotalPrice(getTotalPrice());
        carSolutionModel.setCurrency(getSolutionCurrency());
        carSolutionModel.setCarType(getSolutionCarType());
        carSolutionModel.setCarModel(getSolutionCarModel());
        carSolutionModel.setPickUpLocation(getSolutionLocation());
        carSolutionModel.setPeopleCapacity(getSolutionPeopleCapacity());
        carSolutionModel.setPackageCapacity(getSolutionPackageCapacity());
        carSolutionModel.setTransmissionType(getSolutionTransmission());
        carSolutionModel.setConditionerInfo(getSolutionConditioner());

        return carSolutionModel;
    }

    private String getSolutionCarModel() {
        String xpathForCarModels = "//h2[contains(@class, 'seleniumModelName')]";
        return selectedCarResult.findElement(By.xpath(xpathForCarModels)).getText();
    }

    private float getTotalPrice() {
        String xpathForPrice = "//div[contains(@class, 'seleniumTotalPrice')]";
        String price = selectedCarResult.findElement(By.xpath(xpathForPrice)).getAttribute("data-price");

        return Float.parseFloat(price);
    }

    private String getSolutionCurrency() {
        String xpathForPrice = "//div[contains(@class, 'seleniumTotalPrice')]";
        String priceWithCurrency = selectedCarResult.findElement(By.xpath(xpathForPrice)).getText();
        return priceWithCurrency.replaceAll("[0-9.]", "");
    }

    private String getSolutionCarType() {
        String xpathForCarName = "//span[contains(@class, 'seleniumCarType')]";
        return selectedCarResult.findElement(By.xpath(xpathForCarName)).getText().replaceAll("[()]", "");
    }

    private String getSolutionLocation() {
        String selectorForLocation = "//div[contains(@class, 'seleniumStartLocation')]";
        return selectedCarResult.findElement(By.xpath(selectorForLocation)).getText();
    }

    private Integer getSolutionPeopleCapacity() {
        String selectorForSeating = "//span[contains(@class, 'seleniumSeatingCapacity')]";
        return Integer.parseInt(selectedCarResult.findElement(By.xpath(selectorForSeating)).getText());
    }

    private Integer getSolutionPackageCapacity() {
        String selectorForPackage = "//span[contains(@class, 'seleniumTrunkCapacity')]";
        return Integer.parseInt(selectedCarResult.findElement(By.xpath(selectorForPackage)).getText());
    }

    private String getSolutionTransmission() {
        String selectorForTransmission = "//span[contains(@class, 'seleniumTransmission')]";
        String transmissionType = selectedCarResult.findElement(By.xpath(selectorForTransmission)).getText();
        if (transmissionType.equals("A")) {
            return "Automatic transmission";
        }
        return "Manual transmission";
    }

    private boolean getSolutionConditioner() {
        try {
            String selectorForConditioner = "//span[contains(@class, 'seleniumConditioner')]";
            return selectedCarResult.findElement(By.cssSelector(selectorForConditioner)).isEnabled();
        }
        catch (Exception e) {
            // no action
        }
        return false;
    }

    public void selectCarResult(Integer resultNumberToSelect, Boolean isPrepaidPaymentType) {

        By selectCarResultKey;

        if (isPrepaidPaymentType == null) {
            selectCarResultKey = By.xpath("//div[contains(@class,'seleniumResultItem')" +
                                          "and position() = " + resultNumberToSelect + "]//a[contains(@class,'btn')]");
        }
        else {
            selectCarResultKey = By.xpath("//div[contains(@class,'seleniumResultItem')" +
                                          "and @payment-required = '" + isPrepaidPaymentType +
                                          "']//a[contains(@class,'btn')]");
        }

        selectedCarResult = getWebDriver().findElement(selectCarResultKey);
    }

    public void clickContinue() {
        selectedCarResult.click();
    }

    public boolean verifyIsLocalResults() {
        try {
            // See if you can find the first local result on this page
            return getWebDriver().findElements(By.cssSelector("div.resultItemWrapper a.smallBtn")).get(0).isDisplayed();
        }
        catch (NoSuchElementException e) {
            // Could not find a local result. Hence this is a car airport results page
            return false;
        }
    }

    public void clickOnTotalInclusiveRatePopup() {
        getWebDriver().findElement(By.xpath(".//div[contains(@class,'seleniumResultItem')and position()=1]" +
                                            "//div[contains(@class,' carHireDetailsComp')]//a")).click();
    }

    public int getNumberOfResults() {
        return getWebDriver().findElements(By.cssSelector("div.resultItemWrapper")).size();
    }
}
