/*
* Copyright 2014 Hotwire. All Rights Reserved.
*
* This software is the proprietary information of Hotwire.
* Use is subject to license terms.
*/
package com.hotwire.selenium.desktop.us.confirm;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static org.ehoffman.testing.fest.webdriver.WebElementAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 1/29/14
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactInformationFragment extends AbstractUSPage {
    private static final String EXPRESS_PHONE = "//*[contains(text(),'Hotwire Express Customer Care')]//..//..//.." +
            "//*[contains(text(), 'Outside U.S./Canada')]//..//../LI[2]";
    private static final String NON_EXPRESS_PHONE = "//*[contains(text(),'Hotwire  Customer Care')]//..//..//.." +
            "//*[contains(text(), 'Outside U.S./Canada')]//..//../LI[2]";

    private static final Map<String, String> CUSTOMER_SERVICE_PHONE_NUMBERS_OUTSIDE_US = new HashMap<String, String>() {
        {
            put(EXPRESS_PHONE, "1-417-520-1695");
            put(NON_EXPRESS_PHONE, "1-417-520-1680");
        }
    };

    public ContactInformationFragment(WebDriver webdriver) {
        super(webdriver);
    }

    public void verifyPhoneForOutsideUSUsers() {
        String phone;
        try {
            phone = getWebDriver().findElement(By.xpath(EXPRESS_PHONE)).getText();
            assertThat(phone).isEqualTo(CUSTOMER_SERVICE_PHONE_NUMBERS_OUTSIDE_US.get(EXPRESS_PHONE));
        }
        catch (NoSuchElementException e) {
            phone = getWebDriver().findElement(By.xpath(NON_EXPRESS_PHONE)).getText();
            assertThat(phone).isEqualTo(CUSTOMER_SERVICE_PHONE_NUMBERS_OUTSIDE_US.get(NON_EXPRESS_PHONE));
        }
    }
}
