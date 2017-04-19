/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesResultsPage extends AbstractUSPage {

    public ActivitiesResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean activitiesInvokedViaHotelConfirmationPage(String destinationCity, String startDate, String endDate) {
        List<String> handles = new ArrayList<>(getWebDriver().getWindowHandles());
        boolean verifyUrl = true;
        getWebDriver().switchTo().window(handles.get(1));
        new ActivitiesResultsPage(getWebDriver());
        String url = getWebDriver().getCurrentUrl();
        if (!url.contains(startDate) || !url.contains(endDate) || !url.contains(destinationCity) ||
            !url.contains("tmid")) {
            verifyUrl = false;
        }
        return verifyUrl;
    }

}
