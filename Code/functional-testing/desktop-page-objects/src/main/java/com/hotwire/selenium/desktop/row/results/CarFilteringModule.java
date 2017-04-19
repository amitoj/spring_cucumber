/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.row.results;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author v-ypuchkova
 * @since 2013.10
 */
public class CarFilteringModule extends AbstractRowPage {

    protected static final int DEFAULT_WAIT = 50;

    private static final Logger LOGGER = LoggerFactory.getLogger(CarFilteringModule.class.getName());

    @FindBy(id = "auto")
    private WebElement checkBoxForAutomatic;

    @FindBy(id = "man")
    private WebElement checkBoxForManual;

    @FindBy(id = "ac")
    private WebElement checkBoxForAirConditioning;

    @FindBy(id = "noac")
    private WebElement checkBoxForNoAirConditioning;

    @FindBy(xpath = ".//*[@id='filterForm']/div[1]")
    private WebElement distanceFilteringModule;

    private List<WebElement> listOfFilteringCheckBoxes =
            Arrays.asList(checkBoxForAirConditioning,
                    checkBoxForNoAirConditioning,
                    checkBoxForAutomatic,
                    checkBoxForManual);

    public CarFilteringModule(WebDriver webdriver) {
        super(webdriver, By.cssSelector("form#filterForm"));
    }



    public void disableOnlyCheckboxForOneFilterOption(List<WebElement> checkboxForDisable) {
        for (WebElement checkBox : listOfFilteringCheckBoxes) {
            if (checkboxForDisable.contains(checkBox)) {
                if (checkBox.isSelected()) {
                    checkBox.click();
                    waitingWhenFilteringDone();
                }
            }
            else if (!checkBox.isSelected()) {
                checkBox.click();
                waitingWhenFilteringDone();
            }
        }
    }

    public boolean verifyDefaultValueForTransmissionFilterOption() {
        return checkBoxForAutomatic.isSelected() && checkBoxForManual.isSelected();
    }

    public boolean verifyDefaultValueForAirConditioningFilterOption() {
        return checkBoxForNoAirConditioning.isSelected() && checkBoxForAirConditioning.isSelected();
    }

    public boolean verifyDefaultValueForDistanceFilterOption() {
        return distanceFilteringModule.findElement(By.id("distance0")).isSelected();
    }


    private void waitingWhenFilteringDone() {
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            LOGGER.error("Waiting for filtering done is failed", e);
        }
//        new WebDriverWait(getWebDriver(), DEFAULT_WAIT);
    }

    public void selectManualFilterOption() {
        disableOnlyCheckboxForOneFilterOption(Arrays.asList(checkBoxForAutomatic));
    }

    public void selectAutomaticFilterOption() {
        disableOnlyCheckboxForOneFilterOption(Arrays.asList(checkBoxForManual));
    }

    public void selectAirConditioningFilterOption() {
        disableOnlyCheckboxForOneFilterOption(Arrays.asList(checkBoxForNoAirConditioning));
    }

    public void selectNoAirConditioningFilterOption() {
        disableOnlyCheckboxForOneFilterOption(Arrays.asList(checkBoxForAirConditioning));
    }
}
