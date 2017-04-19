/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * User: v-abudyak
 * Date: 6/19/13
 * Time: 5:46 AM
 */
public class C3CreateCustomerPage extends ToolsAbstractPage {

    public C3CreateCustomerPage(WebDriver webDriver) {
        super(webDriver, By.className("createCustomerContainer"));
    }

    public void setFullName(String fn, String ln) {
        setText("input[name='firstName']", fn);
        setText("input[name='lastName']", ln);
    }

    public void setEmail(String email) {
        setText("input[name='email']", email);
        setText("input[name='confirmationEmail']", email);
    }

    public void setWrongEmail(String email) {
        setText("input[name='email']", email);
        setText("input[name='confirmationEmail']", "wrongemail@hotwire.com");
    }

    public void setAddress(String zip) {
        //Country hardCoded United States
        selectCountry();
        //set zip Code
        setText("input[name='zipCode']", zip);
    }

    private void selectCountry() {
        JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
        executor.executeScript("var obj = document.getElementsByName('country');obj[0].options[0].selected=true;");
    }

    public  void  submit() {
        findOne("div.lastFormRow button").click();
    }

    public String getMessage() {
        return findOne("span.error", EXTRA_WAIT).getText();
    }

    public String getFirstNameError() {
        return findOne(By.xpath("//span[@id='firstName*.errors']"), EXTRA_WAIT).getText();
    }

    public String getLastNameError() {
        return findOne(By.xpath("//span[@id='lastName*.errors']"), EXTRA_WAIT).getText();
    }

    public String getEmailError() {
        return findOne(By.xpath("//span[@id='email*.errors']"), EXTRA_WAIT).getText();
    }

    public String getConfirmationEmailError() {
        return findOne(By.xpath("//span[@id='confirmationEmail*.errors']"), EXTRA_WAIT).getText();
    }

    public String getCountryError() {
        return findOne(By.xpath("//span[@id='country*.errors']"), EXTRA_WAIT).getText();
    }

    public void setSubscription(boolean subscription) {
        WebElement checkbox = findOne(By.xpath("//input[@name='rocketNews']"));
        if (subscription) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
        else {
            if (checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }
}
