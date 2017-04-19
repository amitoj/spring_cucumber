/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.car;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 6/18/14
 * Time: 4:51 AM
 * To change this template use File | Settings | File Templates.
 */
import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object
 */
public class C3CarTravellerFragment extends ToolsAbstractPage {
    public C3CarTravellerFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.driverInfoWrapper"));
    }

    public void setDriverName(String fn, String ln) {
        setText("input[name='billingForm.travelerForm.driverFirstName']", fn);
        setText("input[name='billingForm.travelerForm.driverLastName']", ln);
    }

    public void travelerPhoneNumber(String phoneNumber) {
        //use US phone instead of international
        try {
            getWebDriver().findElement(By.cssSelector("a.linkForUSFields")).click();
            logger.info("INTL phone selected. Switching to US.");
        }
        catch (ElementNotVisibleException e) {
            logger.info("US phone selected");
        }
        setText("input[name='billingForm.travelerForm.phoneNo']", phoneNumber);
    }

    public void setTravellerEmail(String email) {
        try {
            setEmails(email);
        }
        catch (NoSuchElementException e) {
            logger.info("Email field is not found.");
        }
    }

    private void setEmails(String email) {
        setText("input[name='billingForm.travelerForm._NAE_email']", email);
        setText("input[name='billingForm.travelerForm._NAE_confirmEmail']", email);
    }

    public void confirmAgeAndDeposit() {
        List<WebElement> checkboxes = findMany("div.confirm25box input");
        for (WebElement checkbox : checkboxes) {
            if (checkbox.isDisplayed() && !checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }
}
