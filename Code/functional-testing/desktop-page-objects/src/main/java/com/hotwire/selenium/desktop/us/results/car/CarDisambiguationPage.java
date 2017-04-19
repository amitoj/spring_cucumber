/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 9/11/13
 * Time: 5:10 AM
 * Disambiguation Page - page that we get when Hotwire Car App search can't resolve the location.
 */
public class CarDisambiguationPage extends AbstractUSPage {
    private static final String XPATH_DIS_RESULTS = "//label[input[@name='selectedStartAddress']]";

    @FindBy(xpath = ".//button[@class=' btn']")
    private WebElement disContinueBtn;

    public CarDisambiguationPage(final WebDriver webdriver) {
        super(webdriver, "tiles-def.car.select-address", DEFAULT_SEARCH_LAYER_ID);
    }

    public void continueToResults() {
        disContinueBtn.click();
    }


    public List<String> getResults() {
        List<WebElement> resultWebElement = getWebDriver().findElements(By.xpath(XPATH_DIS_RESULTS));
        List<String> resultValues = new ArrayList<String>();
        for (WebElement result:resultWebElement) {
            resultValues.add(result.getText());
        }
        return resultValues;

    }
}
