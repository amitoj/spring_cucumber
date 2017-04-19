/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.row.elements.Button;
import com.hotwire.selenium.desktop.row.elements.Input;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Base intl fare finder class.
 */
public abstract class AbstractFareFinder extends AbstractRowPage implements FareFinder {

    private static final String LAUNCH_SEARCH = "div.AutoCompleteComp button[type='submit']";

    @FindBy(css = "input#location")
    protected Input location;

    @FindBy(css = LAUNCH_SEARCH)
    private Button launchSearch;

    public AbstractFareFinder(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
    }

    @Override
    public void findFare() {
        new WebDriverWait(getWebDriver(), 5)
                .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(LAUNCH_SEARCH), 5));
        launchSearch.click();
    }

    @Override
    public String getDestinationLocation() {
        return location.getText();
    }

    @Override
    public FareFinder setDestinationLocation(String destinationLocation) {
        location.setText(destinationLocation);
        try {
            WebElement autocomplete = new WebDriverWait(getWebDriver(), 5).until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//input[@id='location']/..//div[@class='yui-ac-content']")));
            autocomplete.sendKeys(Keys.ESCAPE);
        }
        catch (TimeoutException e) {
            logger.info("Autocomplete failed to become visible... Continuing...");
        }
        return this;
    }

    @Override
    public List<String> getSearchSuggestions(String destinationLocation) {
        List<String> searchSuggestions = new ArrayList<String>();

        location.setText(destinationLocation);

        // Do not debug this code! When browser loose focus it closes autocomplete popup.
        new WebDriverWait(getWebDriver(), 10, WebDriverWait.DEFAULT_SLEEP_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.yui-ac-content ul li div")));

        for (WebElement e : getWebDriver().findElements(By.cssSelector("div.yui-ac-content ul li div"))) {
            searchSuggestions.add(e.getAttribute("title"));
        }
        // Bad magic is done, feel free to debug...

        return searchSuggestions;
    }
}
