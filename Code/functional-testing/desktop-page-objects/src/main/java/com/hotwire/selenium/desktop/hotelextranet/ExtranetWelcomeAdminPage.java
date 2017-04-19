/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.hotelextranet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.po.AbstractPageObject;

/**
 * This page is the welcome page of extranet.
 * @author Sridhar
 *
 */
public class ExtranetWelcomeAdminPage extends AbstractPageObject {

    @FindBy(name = "searchHotelId")
    private WebElement hotelId;

    @FindBy(css = "form[name=hotelSearchForm] button")
    private WebElement searchButton;


    @FindBy(linkText = "Change password")
    private WebElement changePasswordLink;


    /**
     * <div class="errorMessages">
     * <h6>Error</h6>
     * <p>Please correct the following field(s):</p>
     * <ul>
     * <li> <b class="bold">Hotwire ID</b> field is blank or invalid.</li>
     * </ul>
     * </div>
     */
    @FindBy(css = "div.errorMessages")
    private WebElement authenticationError;

    public ExtranetWelcomeAdminPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.xnet.welcome-admin"});
    }

    /**
     * Search for the given Hotel Id
     *
     * @param hotelId
     */
    public void searchHotel(long hotelId) {
        sendKeys(this.hotelId, hotelId + "");
        this.searchButton.click();
    }



    public void accessChangePasswordPage() {
        this.changePasswordLink.click();
    }


}
