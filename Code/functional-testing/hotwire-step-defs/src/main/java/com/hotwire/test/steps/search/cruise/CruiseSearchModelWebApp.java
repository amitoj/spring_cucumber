/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.cruise;

import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.index.CruiseIndexPage;
import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.testing.ZeroResultsTestException;
import cucumber.api.PendingException;
import org.openqa.selenium.NoAlertPresentException;
import org.springframework.util.Assert;

/**
 * @author akrivin
 * @since 10/16/12
 */
public class CruiseSearchModelWebApp extends SearchModelTemplate<CruiseSearchParameters> implements CruiseSearchModel {

    public void findFare(String selectionCriteria) {

        HomePage homePage = new HomePage(getWebdriverInstance());
        homePage.findCruise().
            withDestination(searchParameters.getDestination()).
                    withCruiseLine(searchParameters.getCruiseLine()).
                    withDeparturePort(searchParameters.getDeparturePort()).
                    withDepartureMonth(searchParameters.getDepartureMonth()).
                    withDuration(searchParameters.getCruiseDuration()).
                    withSeniorRates(searchParameters.getCheckSeniorRates()).
                    findFare();
    }

    @Override
    public void navigateToCruisePage() {
        getWebdriverInstance().navigate().to(applicationUrl + "/" + "cruise/index.jsp");
    }

    @Override
    public void clickLastMinuteCruiseDeal() {
        CruiseIndexPage cruisePage = new CruiseIndexPage(getWebdriverInstance());
        if (cruisePage.isLastMinuteDealEmpty()) {
            throw new PendingException("Last minute deals empty!");
        }
        cruisePage.clickLastMinuteDeal();
    }

    @Override
    public void clickTopCruiseDeal() {
        CruiseIndexPage cruisePage = new CruiseIndexPage(getWebdriverInstance());
        if (cruisePage.isTopCruiseDealsEmpty()) {
            throw new PendingException("Top cruise deals empty!");
        }
        cruisePage.clickTopCruiseDeal();
    }

    @Override
    public void verifyCruiseSearchResultsUrl() {
        try {
            getWebdriverInstance().switchTo().alert().accept();
            throw new ZeroResultsTestException("No search results returned.");
        }
        catch (NoAlertPresentException e) {
            // Search results returned.
            Assert.isTrue(getWebdriverInstance().getCurrentUrl().startsWith(SEARCH_RESULTS_CSC_WEB_URL_PREFIX));
            Assert.isTrue(getWebdriverInstance().getCurrentUrl().contains(searchParameters.getDeparturePort()));
            if (searchParameters.getDepartureMonth() != null) {
                Assert.isTrue(getWebdriverInstance().getCurrentUrl().contains(searchParameters.getDepartureMonth()));
            }
        }
    }

    @Override
    public void verifyCruiseDetailsUrl() {
        Assert.isTrue(getWebdriverInstance().getCurrentUrl().startsWith(DETAILS_CSC_WEB_URL_PREFIX));
    }

    @Override
    public void verifyOpinionLink() {
        CruiseIndexPage cruisePage = new CruiseIndexPage(getWebdriverInstance());
        Assert.isTrue(cruisePage.verifyOpinionLink());
    }

    @Override
    public void navigateBackFromCruiseResultsPage() {
        getWebdriverInstance().navigate().back();
    }
}
