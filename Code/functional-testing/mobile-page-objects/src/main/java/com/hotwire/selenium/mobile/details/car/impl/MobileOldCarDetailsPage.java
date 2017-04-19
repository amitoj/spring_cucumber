/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.details.car.impl;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.details.car.MobileCarDetailsPage;
import com.hotwire.selenium.mobile.models.CarSolutionModel;
import com.hotwire.selenium.mobile.view.ViewIds;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by IntelliJ IDEA.
 * User: v-sshubey
 * Date: 4/17/12
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileOldCarDetailsPage extends MobileAbstractPage implements MobileCarDetailsPage {


    @FindBy(xpath = "//p[@class='i price']")
    private WebElement carPrice;

    @FindBy(xpath = "//ul/li[@class='result ref']/a")
    private WebElement continueBtn;

    @FindBy (css = "form button[name='carDetailsOpaqueContinue'], form button[name='carDetailsRetailContinue']")
    private WebElement continueSubmit;

    public MobileOldCarDetailsPage(WebDriver driver) {
        super(driver, new String[] {ViewIds.TILE_CAR_DETAILS_OPAQUE, ViewIds.TILE_CAR_DETAILS_RETAIL});
    }

    public MobileOldCarDetailsPage(WebDriver driver, String tile_container) {
        super(driver, tile_container);
    }

    public MobileOldCarDetailsPage(WebDriver webdriver, String[] tileNames) {
        super(webdriver, tileNames);
    }

    public void select() {
        continueBtn.submit();
    }

    @Override
    public void verifyDetailsPageInfo(CarSolutionModel carModel) {
        throw new RuntimeException("Implement me!");
    }

    @Override
    public void continueSubmit() {
        continueSubmit.submit();
    }


}

