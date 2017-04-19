/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.search;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 *
 */
public class AirMFILayer extends AbstractUSPage {
    private static final long DEFAULT_WAIT = 10;
    private static final String MFI_LAYER = "tileName-mfiD2C";
    private static final String LAYER_PARAGRAGHS =
            ".//div[@class='HwTilesComp MFIDare2CompareComp']//div[@class='innerContent']/p";
    private static final String PARTNER_CHECKBOXES = " .partners .partner input";
    private static final String BUTTON = " .d2c .footer .btn";

    @FindBy(id = MFI_LAYER)
    private WebElement mfiLayer;

    @FindBy(css = BUTTON)
    private WebElement searchButton;

    public AirMFILayer(WebDriver webDriver) {
        super(webDriver, By.id(MFI_LAYER), new Wait<Boolean>(
            new VisibilityOf(By.cssSelector(BUTTON))).maxWait((int) (DEFAULT_WAIT * 2.5)));
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public List<WebElement> getParagraphs() {
        By by = By.xpath(LAYER_PARAGRAGHS);
        new WebDriverWait(getWebDriver(), 20)
                .until(new IsElementLocationStable(getWebDriver(), by));
        return getWebDriver().findElements(by);
    }

    public List<WebElement> getCheckboxes() {
        By by = By.cssSelector(PARTNER_CHECKBOXES);
        new WebDriverWait(getWebDriver(), 20)
                .until(new IsElementLocationStable(getWebDriver(), by));
        return getWebDriver().findElements(by);
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    /*
     HaspMap is decalred in AirSearchModelWebApp and it has info of all the routes.
     Counters are used to iterate through hashmap(mapCounter) and paragraph o MFILayer(mapCounter),
     to validate the info routes displayed on MFI Layer.
    */
    public boolean isMfiD2CLayerDisplayedProperly(HashMap<Integer, String> mapOrigDestCities, String endDate) {
        boolean displayCorrectSearchParameters = true;
        boolean partnerCheckboxes = false;
        boolean verifyEndDate = true;
        AirMFILayer currentStateMfiLayer = new AirMFILayer(getWebDriver());
        int mapCounter = 0, listCounter = 0;
        while (listCounter < currentStateMfiLayer.getParagraphs().size() - 1) {
            if (!currentStateMfiLayer.getParagraphs().get(listCounter).getText().contains(
                    mapOrigDestCities.get(mapCounter)) || !currentStateMfiLayer.getParagraphs().get(listCounter)
                    .getText().contains(mapOrigDestCities.get(mapCounter + 1)) ||
                    !currentStateMfiLayer.getParagraphs().get(listCounter).getText().contains(
                            mapOrigDestCities.get(mapCounter + 2))) {
                displayCorrectSearchParameters = false;
                break;
            }
            else {
                if (listCounter != currentStateMfiLayer.getParagraphs().size() - 1) {
                    mapCounter = mapCounter + 3;
                    listCounter++;
                }
            }
        }
        //this if loop checks for endDate if foreign routes are involved.
        if (StringUtils.isNotBlank(endDate)) {
            if (!currentStateMfiLayer.getParagraphs().get(0).getText().contains(endDate)) {
                verifyEndDate = false;
            }
        }
        //This loop checks for checkboxes; at least one partner needs to have a checkbox checked.
        if (getCheckboxes().size() >= 1) {
            partnerCheckboxes = true;
        }

        return displayCorrectSearchParameters && partnerCheckboxes && currentStateMfiLayer.getSearchButton()
                .isDisplayed() && verifyEndDate;
    }
}
