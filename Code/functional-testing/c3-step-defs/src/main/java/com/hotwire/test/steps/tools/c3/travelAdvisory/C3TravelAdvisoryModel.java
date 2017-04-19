/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.travelAdvisory;

//import com.hotwire.selenium.tools.travelAdvisory.TravelAdvisoryAlertsSiteFragment;
import com.hotwire.selenium.desktop.helpCenter.TravelAdvisoryAlertsSiteFragment;
import com.hotwire.selenium.tools.travelAdvisory.TravelAdvisoryUpdatesPage;
import com.hotwire.test.steps.tools.ToolsAbstractModel;

import java.util.Map;

import com.hotwire.test.steps.tools.bean.TravelAdvisoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-vzyryanov
 * Date: 9/26/13
 * Time: 4:41 AM
 */
public class C3TravelAdvisoryModel extends ToolsAbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3TravelAdvisoryModel.class);

    @Resource(name = "travelAdvisoryProfiles")
    Map<String, TravelAdvisoryInfo> travelAdvisoryProfiles;

    @Autowired
    private TravelAdvisoryInfo travelAdvisoryIssue;

    public void removeIssue() {
        new TravelAdvisoryUpdatesPage(getWebdriverInstance())
                .removeIssue(getProfile().getIndex());
        LOGGER.info("Travel advisory issue was removed.");
    }

    public void publishIssue() {
        new TravelAdvisoryUpdatesPage(getWebdriverInstance())
                .publishIssue(getProfile().getIndex());
        LOGGER.info("Travel advisory issue was published.");
    }

    public void verifyBlankFieldsErrorMessage() {
        assertThat(new TravelAdvisoryUpdatesPage(getWebdriverInstance()).getMsg())
                .contains("The title field is blank.")
                .contains("The message field is blank.")
                .contains("The updated date is blank or invalid.")
                .contains("Neither domestic nor international site is selected for the alert");
    }

    public void verifyAlertAppliedMsg() {
        assertThat(new TravelAdvisoryUpdatesPage(getWebdriverInstance()).getMsg())
                .isEqualTo("This alert has been published to Hotwire.com and/or international sites, as applicable");
    }

    public void verifyIncorrectDate() {
        assertThat(new TravelAdvisoryUpdatesPage(getWebdriverInstance()).getMsg())
                .isEqualTo("The updated date is blank or invalid.");
    }

    public void fillTravelAdvisory() {
        TravelAdvisoryInfo profile = getProfile();
        final TravelAdvisoryUpdatesPage updatesPage = new TravelAdvisoryUpdatesPage(getWebdriverInstance());
        updatesPage.setIndex(profile.getIndex());
        updatesPage.setTitle(profile.getTitle());
        updatesPage.setMessage(profile.getMessage());
        updatesPage.setDateUpdated(profile.getDateUpdated());
        updatesPage.setExpirationDate(profile.getDateExpired());
        updatesPage.setDomesticCheckBox(profile.isDisplayOnDomestic());
        updatesPage.setIntlCheckBox(profile.isDisplayOnIntl());
    }

    public TravelAdvisoryInfo getProfile() {
        String type = travelAdvisoryIssue.getTravelAdvisoryIssue().toString();
        return travelAdvisoryProfiles.get(type);
    }

    public void verifyModuleNotDisplayed(boolean negation) {
        boolean isTravelAdvisoryDisplayed = new TravelAdvisoryAlertsSiteFragment(getWebdriverInstance()).isDisplayed();
        assertThat(negation).as("Travel Advisory module is not displayed").isEqualTo(isTravelAdvisoryDisplayed);
    }

    public void verifyTravelAdvisoryOnSite(boolean negation) {
        final String title = getProfile().getTitle();
        final boolean availability = new TravelAdvisoryAlertsSiteFragment(getWebdriverInstance())
                .getAlerts().contains(title);
        assertThat(availability).isEqualTo(negation);
    }
}
