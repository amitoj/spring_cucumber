/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.bex;

import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Describes a abstract page of BEX site.
 * A collection of common methods for on-page elements manipulation.
 * User: v-jolopez
 */
public abstract class BexAbstractPage extends AbstractPageObject {

    protected static final String US_DATE_FORMAT = "MM/dd/yy";
    protected static final String INTL_DATE_FORMAT = "dd/MM/yy";
    //Maximum wait for long time query results
    protected static final int MAX_WAIT = 90;
    //default wait for page loading
    protected static final int PAGE_WAIT = 30;
    //one note wait for slow operations
    protected static final int EXTRA_WAIT = 4;
    //half note wait for quick operations
    protected static final int DEFAULT_WAIT = 2;

    protected Logger logger = LoggerFactory.getLogger(getClass().getName());

    protected String dateFormat = US_DATE_FORMAT;

    protected  String progressBar = "div#indicator";

    /**
     * Major Page Object constructor.
     * webdriver - gets from getter getWebDriverInstance() in WebDriverAwareModel
     * keyPageElement - element defined that page is loaded.
     *
     */
    public BexAbstractPage(WebDriver webdriver, By keyPageElement) {
        super(webdriver);
        this.getLatestWindow();

        findOne(keyPageElement, PAGE_WAIT);
    }

    public BexAbstractPage(WebDriver webdriver) {
        super(webdriver);
        this.getLatestWindow();
    }


    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String df) {
        this.dateFormat = df;
    }

    public String getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(String progressBar) {
        this.progressBar = progressBar;
    }

    protected WebElement findOne(String css) {
        return getWebDriver().findElement(By.cssSelector(css));
    }

    protected WebElement findOne(By seleniumByObject) {
        return getWebDriver().findElement(seleniumByObject);
    }

    /**
     *
     * @param cssSelector - CSS selector
     * @param timeInSeconds  - Explicit time for searching an element
     * @return  first matched web element
     * <br><br/>
     */
    protected WebElement findOne(String cssSelector, int timeInSeconds) {
        return findOne(By.cssSelector(cssSelector), timeInSeconds);
    }

    protected WebElement findOne(By seleniumByObject, int timeInSeconds) {
        return new WebDriverWait(getWebDriver(), timeInSeconds)
                .until(ExpectedConditions.visibilityOfElementLocated(seleniumByObject));
    }

    /**
     *
     * @param cssSelector - CSS selector
     * @return  All matched web elements
     */
    protected List<WebElement> findMany(String cssSelector) {
        return getWebDriver().findElements(By.cssSelector(cssSelector));
    }

    protected List<WebElement> findMany(By byElement) {
        return getWebDriver().findElements(byElement);
    }

    protected List<WebElement> findMany(String cssSelector, int timeInSeconds) {
        return new WebDriverWait(getWebDriver(), timeInSeconds)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
    }

    protected List<WebElement> findMany(By seleniumObject, int timeInSeconds) {
        return new WebDriverWait(getWebDriver(), timeInSeconds)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(seleniumObject));
    }

    //Getting parent DOM element of the current. This function is not supported by css selectors.
    protected WebElement getParent(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    /**
     * Use it if select on webpage is visible
     * @param selectPath  - path to <select /> tag
     * @param value - value in <select /> tag
     */
    protected void selectValue(String selectPath, String value) {
        By css = By.cssSelector(selectPath + " " + String.format("option[value='%s']", value));
        getWebDriver().findElement(css).click();
    }

    protected void selectValue(WebElement selectElement, String value) {
        new Select(selectElement).selectByValue(value);
    }

    /**
     * For selectors that appear after other selectors
     * @param selectPath
     * @param value
     * @param timeout
     */
    protected void selectValue(String selectPath, String value, Integer timeout) {
        findOne(selectPath, timeout);
        By css = By.cssSelector(selectPath + " " + String.format("option[value='%s']", value));
        getWebDriver().findElement(css).click();
    }

    protected void selectValue(String selectPath, String value, int timeOut) {
        String css = selectPath + " " + String.format("option[value='%s']", value);
        findOne(css, timeOut).click();
    }

    protected void selectValueByIndex(String selectPath, int index, int timeOut) {
        List<WebElement> options = findMany(selectPath + " option", timeOut);
        String indexStr = String.valueOf(index);
        for (WebElement option : options) {
            if (option.getAttribute("index").equals(indexStr)) {
                option.click();
                break;
            }
        }
    }

    protected void selectValueByVisibleText(String selectPath, String text, int timeOut) {
        new Select(findOne(selectPath, timeOut)).selectByVisibleText(text);
    }

    protected String getSelectValue(String selectPath, int timeOut) {
        return findOne(selectPath + " option[selected]", timeOut).getText();
    }

    /**
     * Use it if select on webpage is invisible and decorated by links
     * @param selectLinkCss  - visible selector link
     * @param linkText   - text of the link in selector
     */
    protected void selectLink(String selectLinkCss, String linkText) {
        getWebDriver().findElement(By.cssSelector(selectLinkCss)).click();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText))).click();
    }

    protected void setText(By byElement, String inputText) {
        WebElement element = getWebDriver().findElement(byElement);
        element.clear();
        element.sendKeys(inputText);
    }

    protected void setText(String cssSelector, String inputText) {
        setText(By.cssSelector(cssSelector), inputText);
    }

    protected void setText(String cssSelector, String inputText, int timeInSeconds) {
        WebElement element = findOne(cssSelector, timeInSeconds);
        element.clear();
        element.sendKeys(inputText);
    }

    protected void setTextAndSubmit(String cssSelector, String inputText) {
        WebElement element = getWebDriver().findElement(By.cssSelector(cssSelector));
        element.clear();
        element.sendKeys(inputText);
        element.sendKeys(Keys.ENTER);
    }

    protected String formatDate(Date inputDate) {
        SimpleDateFormat df = new SimpleDateFormat(getDateFormat());
        return df.format(inputDate);
    }

    //use this only when there is no other way to handle ajax, max wait must be 4 seconds
    protected void freeze(double seconds) {
        long mils = (long) seconds * 1000;
        try {
            Thread.sleep(mils);
        }
        catch (InterruptedException e) {
            logger.info("Error while freezing WebDriver!");
        }
    }

    protected void clickOnLink(String linkText, int waitTimeout) {
        new WebDriverWait(getWebDriver(), waitTimeout)
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText))).click();
    }

    protected void waitProgressBar() {
        new WebDriverWait(getWebDriver(), MAX_WAIT)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(getProgressBar())));
    }

    protected void waitElementStopMoving(String cssSelector, int timeout) {
        new WebDriverWait(getWebDriver(), 5)
                .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(cssSelector), timeout));
    }

    /**
     * For handling tables especially with multiple tabs
     * @param rowCss - table row css selector with column specification i.e. "ul.accountRow li.column4"
     * @param rowText - text that should be matched
     */
    protected void selectTableRow(String rowCss, String rowText) {
        List<WebElement> rows = findMany(rowCss);
        boolean notfound = true;
        for (WebElement row : rows) {
            if ((rowText.equals(row.getText())) && (row.isDisplayed())) {
                logger.info("Selecting row with value: " + rowText);
                notfound = false;
                row.click();
                break;
            }
        }
        if (notfound) {
            throw new NoSuchElementException("Row not found");
        }
    }

    protected void selectDate(String inputCSS, Date date) {
        setText(inputCSS, formatDate(date));
    }

    protected void selectDate(By by, Date date) {
        setText(by, formatDate(date));
    }

    protected Date getDateBefore(int days) {
        return new DateTime(new DateTime()).minusDays(days).toDate();
    }

    protected Date getDateAfter(int days) {
        return new DateTime(new DateTime()).plusDays(days).toDate();
    }

    protected Date getCurrentDate() {
        return new DateTime().toDate();
    }

    protected DateTime getTimeBefore(int hours) {
        return new DateTime(new DateTime()).minusHours(hours);
    }

    protected String convertToAmPm(DateTime time2Convert) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("h:mm aa");
        return fmt.print(time2Convert);
    }

    protected void submitFormByName(String formName) {
        findOne("form[name='" + formName + "']").submit();
    }

    protected boolean isElementDisplayed(By byElement, int wait) {
        try {
            new WebDriverWait(getWebDriver(), wait)
                    .until(ExpectedConditions.visibilityOfElementLocated(byElement));
            return true;
        }
        catch (TimeoutException | NoSuchElementException | ElementNotVisibleException e) {
            return false;
        }
    }

    protected boolean isElementDisplayed(By byElement) {
        return isElementDisplayed(byElement, DEFAULT_WAIT);
    }


    protected boolean isElementDisplayed(String cssSelector) {
        return isElementDisplayed(By.cssSelector(cssSelector));
    }

    protected void waitUntilDisappear(String css) {
        waitUntilDisappear(css, DEFAULT_WAIT);
    }

    protected void waitUntilDisappear(String css, int wait) {
        new WebDriverWait(getWebDriver(), wait)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(css)));
    }

    protected void waitElementText(String css, String value) {
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(ExpectedConditions.textToBePresentInElementValue(By.cssSelector(css), value));
    }

    protected void checkBox(String css) {
        if (!findOne(css, DEFAULT_WAIT).isSelected()) {
            findOne(css).click();
        }
    }

    protected void checkBox(By by) {
        if (!findOne(by, DEFAULT_WAIT).isSelected()) {
            findOne(by).click();
        }
    }

    protected void uncheckBox(By by) {
        if (findOne(by, DEFAULT_WAIT).isSelected()) {
            findOne(by).click();
        }
    }

    protected boolean verifyTextOnPageBoolean(String text) {
        try {
            if (findMany(By.xpath("//*[text()[contains(.,'" + text + "')]]")).size() > 0) {
                logger.info("Expected text was found on page - " + text);
                return true;
            }

        }
        catch (Exception e) {
            logger.info("Expected text WAS NOT found on page - " + text);
            return false;
        }
        return false;
    }

    public void verifyTextOnPage(String text) {
        assertThat(this.verifyTextOnPageBoolean(text)).
                as("Text '" + text + "' NOT found").isTrue();
    }

    protected WebElement waitTextLoaded(By byElement, int timeout) {
        logger.info("Waiting for text");
        for (int count = 0; count < timeout + 1; count++) {
            freeze(1);
            logger.info("Wait" + count + "seconds");
            if (!"".equals(findOne(byElement).getText()))  {
                logger.info("Element ready");
                break;
            }
        }
        return findOne(byElement);
    }

    protected WebElement waitValueLoaded(By byElement, int timeout) {
        logger.info("Waiting for value");
        for (int count = 0; count < timeout + 1; count++) {
            freeze(1);
            logger.info("Wait" + count + "seconds");
            if (!"".equals(findOne(byElement).getAttribute("value")))  {
                logger.info("Element ready");
                break;
            }
        }
        return findOne(byElement);
    }

    protected void getLatestWindow() {
        for (String winHandle : getWebDriver().getWindowHandles()) {
            getWebDriver().switchTo().window(winHandle);
        }
    }

}

