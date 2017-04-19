/*
 *  Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author v-cgonzalezroble
 * Zip code update page
 */
public class ZipUpdatePage extends AbstractUSPage {
    @FindBy(name = "zipCode")
    private WebElement zipCodeField;

    @FindBy(xpath = "//button[@type='submit']/child::img[@alt='Continue']")
    private WebElement saveChagesButton;

    @FindBy(xpath = "//p[contains(text(),'your email address is')]/child::strong")
    private WebElement emailLabel;

    @FindBy(xpath = "//*[@class='ml15 dbmEmailSignup']/child::h1/child::img")
    private WebElement dbmHeader;

    public ZipUpdatePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void enterZipCode(String zipCode) {
        zipCodeField.clear();
        zipCodeField.sendKeys(zipCode);
    }

    public void clickSaveChanges() {
        saveChagesButton.click();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getDBMHeader() {
        return dbmHeader.getAttribute("alt");
    }
}
