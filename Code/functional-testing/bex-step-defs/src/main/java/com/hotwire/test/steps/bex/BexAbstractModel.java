/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex;

import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.WebDriverManager;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/9/15
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BexAbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(BexAbstractModel.class);

    private static final int DEFAULT_WAIT = 2;
    private static final int MAX_WAIT = 70;

    @Autowired
    private WebDriver webdriverInstance;

    public WebDriver getWebdriverInstance() {
        return webdriverInstance;
    }

    public void verifyPageURL(String url) {
        Assertions.assertThat(getWebdriverInstance().getCurrentUrl()).contains(url);
    }

    public void clickOnLink(String linkText) {
        getWebdriverInstance().findElement(By.linkText(linkText)).click();
    }

    public void switchToNewWindow() {
        if (getWebdriverInstance().getWindowHandles().size() == 1) {
            LOGGER.info("There is only one window.. You can't switch to another..");
            return;
        }

        String currentWindowHandle = getWebdriverInstance().getWindowHandle();
        LOGGER.info("Current window handle: {}", currentWindowHandle);

        for (String winHandle : getWebdriverInstance().getWindowHandles()) {
            if (!winHandle.equals(currentWindowHandle)) {
                getWebdriverInstance().switchTo().window(winHandle);
                LOGGER.info("Switched to {}", winHandle);
            }
        }
    }

    public void switchToDefaultWindow() {
        switchToDefaultContent();
    }

    public void switchToFrame(String frameName) {
        LOGGER.info("Try to switch into the frame {}", frameName);
        new WebDriverWait(getWebdriverInstance(), MAX_WAIT)
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
        LOGGER.info("Switch is done..");
    }

    public void switchToDefaultContent() {
        getWebdriverInstance().switchTo().defaultContent();
    }

    public void verifyMailTo(String email) {
        String template = "a[href*='mailto:%s']";
        String emailLink = String.format(template, email);
        getWebdriverInstance().findElement(By.cssSelector(emailLink));
    }

    public void waitPolling(String cssSelector) {
        new WebDriverWait(getWebdriverInstance(), MAX_WAIT)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(cssSelector)));
    }

    public void scrollPage() {
        JavascriptExecutor js = (JavascriptExecutor) getWebdriverInstance();
        js.executeScript(
                "window.scrollTo(0,Math.max(document.documentElement.scrollHeight," +
                        "document.body.scrollHeight,document.documentElement.clientHeight));");
    }

    public void acceptAlert() {
        LOGGER.info("Wait for alert message in {} sec..", DEFAULT_WAIT);
        new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT)
                .until(ExpectedConditions.alertIsPresent()).accept();
        LOGGER.info("Alert is accepted..");
    }

    public void maximize() {
        getWebdriverInstance().manage().window().maximize();
    }

    /**
     * Logging session ID for searching logs on View Server
     * Use grep -i YOUR_JSESSION_ID tcbiz.log
     * Path to logs such as  /p4/phoenix/main/deploy/dev
     * For QA environment use Splunk
     */
    public void logSession(String msg) {
        try {
            String session = getWebdriverInstance().manage().getCookieNamed("JSESSIONID").getValue();
            String host = getWebdriverInstance().manage().getCookieNamed("JSESSIONID").getDomain();
            LoggerFactory.getLogger(getLoggingClass())
                    .info("(JSESSIONID: {}, HOST: {}) {}",
                            session,
                            host,
                            msg);

        }
        catch (ClassNotFoundException | RuntimeException e) {
            LOGGER.warn("Session logging failed");
        }
    }

    private Class getLoggingClass() throws ClassNotFoundException {
        String className = "";
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            className = stackTraceElement.getClassName();
            if (className.contains("Model") && !className.endsWith("ToolsAbstractModel")) {
                break;
            }
        }
        return Class.forName(className);
    }

    public void verifyTextOnPage(String message) {
        logSession("Try to find text on page - " + message);
        new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        String.format("//*[text()[contains(.,'%s')]]", message))));
    }

    public boolean verifyTextOnPageBoolean(String message) {
        logSession("Try to find text on page - " + message);
        try {
            new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            String.format("//*[text()[contains(.,'%s')]]", message))));
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public void verifyTextOnPopupPage(String message) {
        logSession("Try to find text on Popup page - " + message);
        switchToNewWindow();
        new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        String.format("//*[text()[contains(.,'%s')]]", message))));
        switchToDefaultWindow();
    }

    public void addParamsToUrlAndGo(String url, Map<String, String> params) {
        String startURL = url;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (startURL.contains("?")) {
                startURL = String.format("%s&%s=%s", startURL, entry.getKey(), entry.getValue());
            }
            else {
                startURL = String.format("%s?%s=%s", startURL, entry.getKey(), entry.getValue());
            }
        }
        getWebdriverInstance().get(startURL);
    }

    public void unimplementedTest() {
        throw new UnimplementedTestException("This method is not implemented for this type of product");
    }

    public void deleteAllCookies() {
        getWebdriverInstance().manage().deleteAllCookies();
        WebDriverManager.cleanUpWebDriver(getWebdriverInstance());
    }

    public void killPopups() {
        WebDriverManager.closeAllPopups.apply(getWebdriverInstance());
    }

    public boolean isCaseNoteFrameDisplayed() {
        String frameId = getCurrentFrameId();
        try {
            switchToDefaultContent();
            switchToFrame("notesFrame");
            return getWebdriverInstance().findElement(By.cssSelector("div.fLft.headerLabel")).getText()
                    .contains("Case Entry - Open Case ID: ");
        }
        catch (NoSuchFrameException e1) {
            logSession("Case notes frame not found.");
            return false;
        }
        catch (NoSuchElementException e2) {
            logSession("Case notes header");
            return false;
        }
        finally {
            switchToDefaultContent();
            switchToFrame(frameId);
        }
    }

    private String getCurrentFrameId() {
        String frameId;
        try {
            new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.c3SingleColumnLayout")));
            frameId = "c3Frame";
        }
        catch (TimeoutException | NoSuchElementException | ElementNotVisibleException e) {
            frameId = "notesFrame";
        }
        return frameId;
    }

    public void waitForResultWindow() {
        if (getWebdriverInstance().getWindowHandles().size() == 1) {
            new WebDriverWait(getWebdriverInstance(), MAX_WAIT)
                    .until(new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver input) {
                            return getWebdriverInstance().getWindowHandles().size() == 2;
                        }
                    });
        }
    }
}
