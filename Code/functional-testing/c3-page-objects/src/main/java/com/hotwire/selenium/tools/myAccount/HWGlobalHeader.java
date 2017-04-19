/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.myAccount;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 8/8/13
 * Time: 2:32 AM
 * Hotwire WebApp Global Header. Simplified version for CAPS.
 */
public class HWGlobalHeader extends ToolsAbstractPage {

    public HWGlobalHeader(WebDriver webDriver) {
        super(webDriver, By.className("hwGlobalHeader"));
    }

    public void clickHelpCenterLink() {
        findOne("div.helpCenter a", DEFAULT_WAIT).click();
    }

    public void goToSignInPage() {
        findOne("span.signup", DEFAULT_WAIT).click();
        findOne("a#headerSignIn", DEFAULT_WAIT).click();
    }

    public void goToAirLandingPage() {
        findOne("div.hwMainNavigation  a[href*='/air/index.jsp']").click();
    }

    public void goToCarLandingPage() {
        findOne("div.hwMainNavigation a[href*='/car/index.jsp']").click();
    }

    public void goToHotelLandingPage() {
        findOne("div.hwMainNavigation a[href*='/hotel']").click();
    }

    public void verifyDealHash() {
        findOne(By.cssSelector("div.dealTablesContainer, div.dealDescription"), DEFAULT_WAIT);
    }

    public void setCurrency(String currency) {
        findOne("dl.currencyDropdown a").click();
        findOne(String.format("a[data-currencycode='%s']", currency), DEFAULT_WAIT).click();
    }

    public void goToMyTrips() {
        findOne("span.showStatusActionIcon", DEFAULT_WAIT).click();
        findOne("a[href$='/trips']", DEFAULT_WAIT).click();
    }
}
