/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 1/17/13
 * Time: 7:47 AM
 * C3 Page that appears before billing page in C3 frame
 */
public class C3InterstitialPage extends ToolsAbstractPage {

    public C3InterstitialPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("table.infobar"));
    }

    public C3InterstitialPage(WebDriver webdriver, By keyElement) {
        super(webdriver, keyElement);
    }

    public void search() {
        findOne("input[name = 'saveButton']").click();
    }

    public void continueAsGuestUser() {
        findOne(By.xpath(".//td[contains(text(), 'Guest booking')]//input")).click();
        findOne("input[name='saveButton']").click();
    }

    public void continueAsUser(String email) {
        setText("input[name='customerEmail']", email);
        search();
        clickOnLink("Select user", DEFAULT_WAIT);
    }

    public void continueAsNewUser(String email, String firstName, String lastName) {
        findOne(By.name("firstName")).sendKeys(firstName);
        findOne(By.name("lastName")).sendKeys(lastName);
        findOne(By.name("email")).sendKeys(email);
        findOne(By.name("confirmEmail")).sendKeys(email);
        selectCountry();
        findOne(By.name("postalCode")).sendKeys("94001");
        findOne(By.xpath("//button[text()='Register']")).click();
    }

    public void searchByPhoneNumber(String phoneNumber) {
        setText("input[name='customerPhoneNumber']", phoneNumber);
        search();
        findOne("div.customerExistContainer ul.userRow a", EXTRA_WAIT).click();
    }

    public void searchByLastName(String lastName) {
        setText("input[name='customerLastName']", lastName);
        findOne("form[action='/findCustomer'] button").click();
    }

    public Boolean checkCustomerSearchResultIsPresent() {
        return findOne("div.customerExistContainer ul.userRow a", EXTRA_WAIT).isDisplayed();
    }

    private void selectCountry() {
        JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
        executor.executeScript("var obj = document.getElementsByName('countryCode');obj[0].options[0].selected=true;");
    }
}
