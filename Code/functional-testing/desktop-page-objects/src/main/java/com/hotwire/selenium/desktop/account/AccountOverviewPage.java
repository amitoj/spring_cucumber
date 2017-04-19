/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Created by IntelliJ IDEA.
 * User: v-edjafarov
 * Date: Nov 10, 2011
 * Time: 3:59:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class AccountOverviewPage extends AbstractAccountPage {

    @FindBy(css = "div.userName")
    private WebElement userName;

    @FindBy(xpath = "//a[contains(@href,'hotwire.com/intl')]/img")
    private WebElement rowHeaderImage;

    @FindBy(xpath = "//div[@class='yui3-u-5-12']/div[@class='adress2']")
    private WebElement userEmail;

    public AccountOverviewPage(WebDriver driver) {
        super(driver, "tile.account.overview");
    }


    public WebElement getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return this.userEmail.getText();
    }
}
