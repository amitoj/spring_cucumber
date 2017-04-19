/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.widget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPage;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * DatePicker to drive the YUI-based Calendar component
 */
public class DatePicker extends AbstractPage {

    private static final int MAX_WAIT = 5;
    private static final String CSS_CALENDAR = ".calendarContainer";

    /**
     * Formats
     */
    public enum DateFormats {
        DESKTOP_US,
        DESKTOP_US_YYYY,
        DESKTOP_ROW,
        MOBILE_US,
        MOBILE_ROW,
        DESKTOP_ROW_SWEDEN,
        DESKTOP_ROW_NORWAY_GERMANY,
        DESKTOP_ROW_DENMARK,
        DESKTOP_ROW_NZD_AUSTRALIA_MEXICO,
        DESKTOP_ROW_HKG_SINGAPORE,
    }

    @SuppressWarnings("serial")
    private static final HashMap<DateFormats, String> FORMATTED_DATE_STRINGS = new HashMap<DateFormats, String>() {
        {
            put(DateFormats.DESKTOP_US, "MM/dd/yy");
            put(DateFormats.DESKTOP_US_YYYY, "MM/dd/yyyy");
            put(DateFormats.DESKTOP_ROW, "dd/MM/yy");
            put(DateFormats.DESKTOP_ROW_SWEDEN, "yyyy-MM-dd");
            put(DateFormats.DESKTOP_ROW_NORWAY_GERMANY, "dd.MM.yy");
            put(DateFormats.DESKTOP_ROW_DENMARK, "dd-MM-yy");
            put(DateFormats.DESKTOP_ROW_NZD_AUSTRALIA_MEXICO, "d/MM/yy");
            put(DateFormats.DESKTOP_ROW_HKG_SINGAPORE, "M/dd/yy");
            put(DateFormats.MOBILE_US, "MM/dd/yyyy");
            put(DateFormats.MOBILE_ROW, "dd/MM/yyyy");
        }
    };

    @FindBy(tagName = "input")
    private WebElement inputElement;

    @FindBy(css = CSS_CALENDAR)
    private WebElement datePickerContainer;

    private final boolean isLegacy;

    /**
     * Since it is often easier to locate the input field that is part of the datepicker, we allow passing in
     * either the container itself or any descendant of it into the containerOrDescendantLocator argument. If a
     * descendant is passed in, we use xpath to find the appropriate ancestor to pass to the superclass constructor.
     *
     * @param webdriver                    WebDriver instance
     * @param containerOrDescendantLocator A locator for finding the container of this page fragment.
     */
    public DatePicker(WebDriver webdriver, By containerOrDescendantLocator) {
        this(webdriver, webdriver.findElement(containerOrDescendantLocator));
    }

    /**
     * Since it is often easier to locate the input field that is part of the datepicker, we allow passing in
     * either the container itself or any descendant of it into the containerOrDescendantElement argument. If a
     * descendant is passed in, we use xpath to find the appropriate ancestor to pass to the superclass constructor.
     *
     * @param webdriver                    WebDriver instance
     * @param containerOrDescendantElement Element that is either the container or a descendant of the container
     */
    public DatePicker(WebDriver webdriver, WebElement containerOrDescendantElement) {
        super(webdriver, resolveToContainer(containerOrDescendantElement));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webdriver;
        String inputElementId = inputElement.getAttribute("id");
        isLegacy = (Boolean) jsExecutor.executeScript("var cal = CompSupport.getJsObj('" + inputElementId +
                "'); if (cal.yCal) { return true; } else { return false; }");
        logger.info("Using Legacy Calendar: " + isLegacy);
        if (!isLegacy) {
            // redefine datepicker container. The JQuery calendar container is not located in the fare finder
            // DOM snippet or in this widget, so we have to do a find element from
            // the whole page to get the JQuery calendar.
            datePickerContainer = webdriver.findElement(By.id("ui-datepicker-div"));
        }
    }

    public void selectDate(Date d) {
        if (isLegacy) {
            selectDateLegacyCalendar(d);
        }
        else {
            selectDateJQueryCalendar(d);
        }
    }

    public void selectDateLegacyCalendar(Date d) {
        //make sure we advance to the correct month before showing the calendar
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getWebDriver();
        String inputElementId = inputElement.getAttribute("id");
        jsExecutor.executeScript("var cal = CompSupport.getJsObj('" + inputElementId +
                "'); cal.yCal.cfg.setProperty('pagedate', new Date(" + d.getTime() +
                ")); cal.renderRequired=true;");

        inputElement.click();

        //block/wait until the calendar layer is actually visible
        new Wait<WebElement>(ExpectedConditions.visibilityOf(datePickerContainer))
                .maxWait(MAX_WAIT).apply(getWebDriver());

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(d);

        StringBuilder cssSelectorForAnchor = new StringBuilder();
        cssSelectorForAnchor.append("#").append(datePickerContainer.getAttribute("id")).append(" ");
        cssSelectorForAnchor.append(".m").append(cal.get(Calendar.MONTH) + 1).append(" ");
        cssSelectorForAnchor.append(".d").append(cal.get(Calendar.DAY_OF_MONTH)).append(" a");

        WebElement anchor = datePickerContainer.findElement(By.cssSelector(cssSelectorForAnchor.toString()));

        anchor.click();

        //block/wait until the text is actually placed in the input field and the calendar is hidden
        new WebDriverWait(getWebDriver(), MAX_WAIT)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CSS_CALENDAR)));
    }

    public void selectDateJQueryCalendar(Date d) {
        //make sure we advance to the correct month before showing the calendar
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getWebDriver();
        String inputElementId = inputElement.getAttribute("id");
        jsExecutor.executeScript("$('#" + inputElementId + "').datepicker('setDate', new Date(" + d.getTime() + "));");

        inputElement.click();

        //block/wait until the calendar layer is actually visible
        new WebDriverWait(getWebDriver(), MAX_WAIT)
                .until(ExpectedConditions.visibilityOf(datePickerContainer));

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        String fmt = ".//table[@class='ui-datepicker-calendar']//td[@data-year='%d'][@data-month='%d']//a[text()='%d']";
        WebElement anchor = datePickerContainer.findElement(
                By.xpath(String.format(fmt,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))));

        anchor.click();

        //block/wait until the text is actually placed in the input field and the calendar is hidden
        new WebDriverWait(getWebDriver(), MAX_WAIT)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CSS_CALENDAR)));
    }

    public Date getDate() throws ParseException {
        String dateFormat = inputElement.getAttribute("placeholder").replace("mm", "MM");
        Date date = new SimpleDateFormat(dateFormat).parse(inputElement.getAttribute("value"));
        return date;
    }

    public static String getFormattedDate(Date d) {
        return getFormattedDate(d, DateFormats.DESKTOP_US);
    }

    public static String getFormattedDate(Date d, DateFormats dateFormat) {
        SimpleDateFormat formattedDate = new SimpleDateFormat(FORMATTED_DATE_STRINGS.get(dateFormat));
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(d);
        return formattedDate.format(cal.getTime());
    }

    public void typeDate(String formattedDateString) {
        logger.info("Typing date: " + formattedDateString);
        inputElement.click();
        inputElement.clear();
        inputElement.sendKeys(formattedDateString + Keys.ESCAPE);

        new WebDriverWait(getWebDriver(), MAX_WAIT)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(CSS_CALENDAR)));
    }

    public static String getDateFormat(DateFormats dateFormat) {
        return FORMATTED_DATE_STRINGS.get(dateFormat);
    }

    private static WebElement resolveToContainer(WebElement containerOrDescendantElement) {
        WebElement test = containerOrDescendantElement;
        while (test != null) {
            String cssClass = test.getAttribute("class");
            if (cssClass != null && (cssClass.contains("CalendarComp") || cssClass.contains("datepicker"))) {
                return test;
            }
            test = test.findElement(By.xpath(".."));
        }
        throw new RuntimeException("Could not find valid Calendar container based on input " +
                containerOrDescendantElement.toString());
    }

    public static SimpleDateFormat getSelectedCountryDateFormat(final WebDriver webDriver) {
        GlobalHeader header = new GlobalHeader(webDriver);
        String format;
        if (header.hasVerticalNavigationTabs()) {
            format = DatePicker.getDateFormat(DateFormats.DESKTOP_US);
        }
        else {
            String selectedCountry = header.getSelectedCountry();
            if (selectedCountry.equals("Sverige")) {
                format = DatePicker.getDateFormat(DateFormats.DESKTOP_ROW_SWEDEN);
            }
            else if (selectedCountry.equals("Norge") || selectedCountry.equals("Deutschland")) {
                format = DatePicker.getDateFormat(DateFormats.DESKTOP_ROW_NORWAY_GERMANY);
            }
            else if (selectedCountry.equals("Danmark")) {
                format = DatePicker.getDateFormat(DateFormats.DESKTOP_ROW_DENMARK);
            }
            else if (selectedCountry.equals("Australia") || selectedCountry.equals("New Zealand") ||
                    selectedCountry.equals("México")) {
                format = DatePicker.getDateFormat(DateFormats.DESKTOP_ROW_NZD_AUSTRALIA_MEXICO);
            }
            else if (selectedCountry.equals("Hong Kong") || selectedCountry.equals("Singapore")) {
                format = DatePicker.getDateFormat(DateFormats.DESKTOP_ROW_HKG_SINGAPORE);
            }
            else {
                // UK/Ireland
                format = DatePicker.getDateFormat(DateFormats.DESKTOP_ROW);
            }
        }
        return new SimpleDateFormat(format);
    }
}
