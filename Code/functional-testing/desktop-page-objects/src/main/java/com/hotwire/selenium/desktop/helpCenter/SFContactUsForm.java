/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.helpCenter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Sales Force Help Center INTL contact US form
 */
public class SFContactUsForm extends HelpCenterAbstractPage {

    public SFContactUsForm(WebDriver webDriver) {
        super(webDriver, By.id("contactus"));
    }

    public SFContactUsForm fillCustomerName(String firstName, String lastName) {
        setText("input#firstName", firstName);
        setText("input#lastName", lastName);
        return this;
    }

    public SFContactUsForm fillCustomerContacts(String email, String phone) {
        setText("input#email", email);
        setText("input#phoneNumber", phone);
        return this;
    }

    public SFContactUsForm fillMessageInfo(String subject, String text) {
        selectLink("a.sbSelector", subject);
        setText("textarea#message", text);
        return this;

    }

    public void submitForm() {
        findOne("span.submitBtn").click();
    }

    public String getMessage() {
        return findOne("div#messageBox span b", PAGE_WAIT).getText();
    }

    public boolean isElementHighlighted(String elementName) {
        WebElement field;
        if ("subject".equals(elementName)) {
            field = findOne("div.sbHolder");
        }
        else {
            field = getWebDriver().findElement(By.id(elementName));
        }
        return  field.getAttribute("class").contains("errorInput");
    }
}
