/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.partners;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 12/12/13
 * Time: 4:52 AM
 */
public class PartnerIMLFragment extends AbstractUSPage {

    private static final String PARTNER_CHECKBOX_CSS = ".IM_input";

    @FindBy(css = ".IM_do_compare")
    private WebElement searchPartner;

    @FindBy(className = "HotelPlannerPopupComp")
    private WebElement hotelPlannerLayer;

    public PartnerIMLFragment(WebDriver webDriver) {
        super(webDriver, By.className("IM_lightbox_container"), 20);
    }

    public PartnerIMLFragment(WebDriver webDriver, By container) {
        super(webDriver, container, 20);
    }

    public void selectPartnerOnIML(int partner) {
        List<WebElement> partnerCb = new WebDriverWait(getWebDriver(), 20)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(PARTNER_CHECKBOX_CSS)));
        partnerCb.get(partner).click();
    }

    public void searchPartnerClick() {
        searchPartner.click();
    }

}
