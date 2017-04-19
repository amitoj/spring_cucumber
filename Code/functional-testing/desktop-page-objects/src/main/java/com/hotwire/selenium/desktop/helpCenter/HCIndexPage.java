/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.helpCenter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * User: v-abudyak
 * Date: 7/22/13

 */
public class HCIndexPage extends HelpCenterAbstractPage {
    public HCIndexPage(WebDriver webDriver) {
        super(webDriver, By.className("landingSections"));
    }

    public void clickSendUsEmailLink() {
        clickOnLink("Send us an email", HelpCenterAbstractPage.DEFAULT_WAIT);
    }

    public void clickLearnMoreLink() {
        clickOnLink("Learn more", HelpCenterAbstractPage.DEFAULT_WAIT);
    }

    public String getContactUsText() {
        return findOne("div.contactInformation").getText().replace("\n", " ");

    }

    public void clickLiveChartLink() {
        //need to recheck
        findOne(".rightCol a").click();
    }

}
