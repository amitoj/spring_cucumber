/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.widget;

import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Date;

/**
 * DatePicker for JQuery UI DatePicker used in mobile (where there is only one actual Datepicker on the page, that is
 * not explicitly bound to its input element
 */
public class DatePicker extends AbstractPage {

    private WebElement inputElement;

    private WebElement datePickerContainer;

    /**
     * @param webdriver    WebDriver instance
     * @param inputElement input element bound to the datepicker
     */
    public DatePicker(WebDriver webdriver, WebElement inputElement) {
        super(webdriver, inputElement);
        this.inputElement = inputElement;
        datePickerContainer = webdriver.findElement(By.id("ui-datepicker-div"));
    }

    /**
     * @param webdriver           WebDriver instance
     * @param inputElementLocator input element bound to the datepicker
     */
    public DatePicker(WebDriver webdriver, By inputElementLocator) {
        this(webdriver, webdriver.findElement(inputElementLocator));
    }

    public void selectDate(Date d) {
        inputElement.click(); //force the datepicker open

        //block/wait until the calendar layer is actually visible
        new Wait<WebElement>(ExpectedConditions.visibilityOf(datePickerContainer)).maxWait(5).apply(getWebDriver());

        //force set a date
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getWebDriver();
        String id = inputElement.getAttribute("id");
        jsExecutor.executeScript("$('#" + id + "').datepicker('setDate', new Date(" + d.getTime() + "));$('#" + id +
                                 "').datepicker('hide');");

        //block/wait until the text is actually placed in the input field and the calendar is hidden
        new Wait<Boolean>(new InvisibilityOf(datePickerContainer)).maxWait(15).apply(getWebDriver());
    }
}
