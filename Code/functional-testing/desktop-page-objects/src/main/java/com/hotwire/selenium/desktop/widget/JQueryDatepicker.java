/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.widget;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents jQuery UI Datepicker widget and uses its API.
 * API documentation can be found here: http://api.jqueryui.com/datepicker/.
 */
public class JQueryDatepicker {

    private final JavascriptExecutor javascriptExecutor;
    private final WebElement webElement;

    public JQueryDatepicker(WebDriver webDriver, WebElement webElement) {
        this.javascriptExecutor = (JavascriptExecutor) webDriver;
        this.webElement = webElement;
    }

    public Date getDate() {
        try {
            String dateFormat = webElement.getAttribute("placeholder").replace("mm", "MM");
            return new SimpleDateFormat(dateFormat).parse(webElement.getAttribute("value"));
        }
        catch (ParseException e) {
            throw new RuntimeException("Can't extract date from input '" + webElement.getAttribute("id") + "'", e);
        }
    }

    public void setDate(Date date) {
        String script = "$(arguments[0]).datepicker('setDate', new Date(arguments[1]));";
        javascriptExecutor.executeScript(script, webElement, date.getTime());
    }
}
