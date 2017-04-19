/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.helpCenter;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 7/22/13
 * Time: 7:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class HCIndexPage extends ToolsAbstractPage {
    public HCIndexPage(WebDriver webDriver) {
        super(webDriver, By.className("landingSections"));
    }

    public void clickSendUsEmailLink() {
        clickOnLink("Send us an email", DEFAULT_WAIT);
    }

    public void clickLearnMoreLink() {
        clickOnLink("Learn more", DEFAULT_WAIT);
    }

    public String getContactUsText() {
        return findOne("div.contactInformation").getText().replace("\n", " ");

    }

    public void clickLiveChartLink() {
        //need to recheck
        findOne(".rightCol a").click();
    }

}
