/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.escapeToEurope.rome;

import org.openqa.selenium.WebElement;

import com.hotwire.selenium.desktop.us.GlobalFooter;
import com.hotwire.selenium.desktop.us.SiteMapPage;
import com.hotwire.selenium.primer.RomePage;
import com.hotwire.testing.UnimplementedTestException;

/**
 *
 * @author jhernandez
 *
 */

public class RomeModelWebApp extends RomeModelTemplate {

    public void openGlobalFooter() {
        new GlobalFooter(getWebdriverInstance());
    }

    public void openSiteMapPage() {
        new GlobalFooter(getWebdriverInstance()).navigateToSiteMapPage();
    }

    public void openRomePage() {
        SiteMapPage page = new GlobalFooter(getWebdriverInstance()).navigateToSiteMapPage();
        page.getRomeLink().click();
    }

    @Override
    public void validateText(String arg1) {
        RomePage myPage = new RomePage(getWebdriverInstance());
        WebElement header = myPage.getHeader();

        header.isDisplayed();

        if (!header.getText().equalsIgnoreCase(arg1)) {
            throw new UnimplementedTestException("The text passed as argument " +
                "on the test case and the text shown on UI differs");
        }
    }
}
