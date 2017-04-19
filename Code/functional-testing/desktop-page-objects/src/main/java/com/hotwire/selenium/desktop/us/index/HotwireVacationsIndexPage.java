/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.index;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;

/**
 * Created with IntelliJ IDEA. User: akrivin Date: 10/17/12 Time: 1:28 PM To change this template use File | Settings |
 * File Templates.
 */
public class HotwireVacationsIndexPage extends AbstractUSPage {

    private static boolean expediaPageLoaded = true;

    private final String vacationURL = "http://vacation.hotwire.com";

    @FindBy(css = "span.errorTextStd")
    private WebElement errorMessage;

    @FindBy(id = "mdp")
    private WebElement twoDestinations;

    @FindBy(css = "div#hwGlobalHeader")
    private WebElement hotwireGLobalHeader;

    public HotwireVacationsIndexPage(WebDriver webdriver) {
        super(webdriver);
    }

    public Boolean checkTwoDestinations() {

        try {
            return twoDestinations.isSelected();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean verifyExpediaPage() {
        try {
            logger.info("Checking for globalHeader");
            if (!hotwireGLobalHeader.isDisplayed()) {
                expediaPageLoaded = false;
            }
        }
        catch (NoSuchElementException e) {
            List<WebElement> iframes = getWebDriver().findElements(By.tagName("iframe"));
            logger.info("Checking for iFrames on expedia's landing page");
            if (!(iframes.size() > 0)) {
                expediaPageLoaded = false;
            }
        }
        return expediaPageLoaded;
    }

    public boolean verifyVacationPageForActivities(String destination, String startDate, String endDate) {
        String currentUrl = getWebDriver().getCurrentUrl();
        boolean verifyVacationURL = false;
        if (currentUrl.startsWith(vacationURL) && currentUrl.contains(destination) &&
                currentUrl.contains(startDate) && currentUrl.contains(endDate)) {
            verifyVacationURL = true;
        }
        return verifyVacationURL && verifyExpediaPage();
    }

    public boolean verifyErrorMessage(String destination, String startDate, String endDate) {
        String currentUrl = getWebDriver().getCurrentUrl();
        boolean verifyVacationURL = false, errorDisplayed = false;
        if (currentUrl.startsWith(vacationURL) && currentUrl.contains(destination) &&
                currentUrl.contains(startDate) && currentUrl.contains(endDate)) {
            verifyVacationURL = true;
        }
        new WebDriverWait(getWebDriver(), MAX_SEARCH_PAGE_WAIT_SECONDS).until(
                PageObjectUtils.webElementVisibleTestFunction(errorMessage, true));
        try {
            logger.info("Checking Error Message Span on vacation landing page");
            if (errorMessage.isDisplayed()) {
                errorDisplayed = true;
            }
        }
        catch (NoSuchElementException e) {
            logger.info("ErrorMessage is not displayed");
        }

        return verifyVacationURL && errorDisplayed && verifyExpediaPage();
    }

    public boolean verifyVacationPageForPackages(String destination, String origin) {
        String url = getWebDriver().getCurrentUrl();
        boolean packagesLoaded = false;
        if (url.contains(destination) && url.startsWith(vacationURL) && url.contains(origin)) {
            packagesLoaded = true;
        }

        return packagesLoaded;
    }
}
