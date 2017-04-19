/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.help;

import static org.fest.assertions.Assertions.assertThat;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.row.GlobalFooter;
import com.hotwire.selenium.desktop.row.help.HelpCenterPage;
import com.hotwire.selenium.desktop.row.help.TermsOfUsePage;

import cucumber.runtime.CucumberException;

public final class HelpCenterModelRowWebApp extends HelpCenterModelTemplate {

    @Override
    public void accessTravelFormalities() {
        GlobalFooter footer = new GlobalFooter(getWebdriverInstance());
        footer.clickTravelFormalitiesLink();
    }

    @Override
    public void verifyTravelFormalities() {
        TermsOfUsePage termsOfUse = new TermsOfUsePage(getWebdriverInstance());
        termsOfUse.clickFranceDiplomatieLink();
    }

    @Override
    public void openHelpCenterPage() {
        GlobalHeader header = new GlobalHeader(getWebdriverInstance());
        header.navigateToHelpCenterPage();
    }

    @Override
    public void verifyCenterAvailabilityHours(String countryName, String supportHours) {
        HelpCenterPage helpCenterPage = new HelpCenterPage(getWebdriverInstance());
        String countrySupportInfo = helpCenterPage.getSupportInfo(countryName);
        if (countrySupportInfo.isEmpty()) {
            throw new CucumberException("Support info is empty.");
        }
        assertThat(countrySupportInfo.contains(supportHours)).isTrue();
    }
}
