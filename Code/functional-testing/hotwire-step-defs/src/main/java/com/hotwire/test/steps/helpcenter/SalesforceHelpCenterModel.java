/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.helpcenter;

import com.hotwire.selenium.desktop.helpCenter.SFContactUsForm;
import com.hotwire.selenium.desktop.helpCenter.Salesforce.SFArticlePage;
import com.hotwire.selenium.desktop.helpCenter.Salesforce.SFIntlIndexPage;
import com.hotwire.selenium.desktop.helpCenter.Salesforce.SFHCIndexPage;
import com.hotwire.selenium.desktop.helpCenter.Salesforce.SFAdminIndexPage;
import com.hotwire.selenium.desktop.helpCenter.Salesforce.SFAdminLoginPage;

import com.hotwire.selenium.desktop.helpCenter.TravelAdvisoryAlertsSiteFragment;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.testing.UnimplementedTestException;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-asnitko
 * Date: 8/7/13
 */
public class SalesforceHelpCenterModel extends HelpCenterModel {


    public static final String SF_HELPCENTER_URL = "http://hw.force.com/knowledgebase";

    public static final String SF_ADMIN_URL = "https://test.salesforce.com/";

    private static final String EDIT_CATEGORY_TEMP =
            "//div[contains(text(), '%s')]/../..//a/span[contains(text(), 'Edit')]";

    @Autowired
    private HelpCenterInfo helpCenterInfo;


    @Autowired
    @Qualifier("payableUser")
    private UserInformation userInformation;

    @Override
    public void clickSendUsEmailLink() {
        getWebdriverInstance().findElement(By.linkText("Send us an email")).click();
    }

    @Override
    public void fillContactUs() {
        new SFContactUsForm(getWebdriverInstance())
                .fillCustomerName(userInformation.getFirstName(), userInformation.getLastName())
                .fillCustomerContacts(userInformation.getEmailId(), userInformation.getPrimaryPhoneNumber())
                .fillMessageInfo("Other", "This is the test message. Please ignore");
    }


    @Override
    public void clickSendButton() {
        new SFContactUsForm(getWebdriverInstance()).submitForm();
    }

    public void checkSuccessSendContactUsFormMessage() {
        Assertions.assertThat(new SFContactUsForm(getWebdriverInstance()).getMessage())
                .isEqualTo("Your form was successfully sent.");
    }

    @Override
    public void checkElementPreFilled(String elementName) {
        throw new UnimplementedTestException("No implemented for this model");
    }

    @Override
    public void checkElementHighlighted(String elementName) {
        Assertions.assertThat(new SFContactUsForm(getWebdriverInstance()).isElementHighlighted(elementName)).isTrue();
    }

    @Override
    public void setupHC() {
        getWebdriverInstance().navigate().to(SF_HELPCENTER_URL);
        getWebdriverInstance().findElement(By.id("j_id0:loginForm:verifText"))
                .sendKeys("samba123dijaneiro!@#knowledgebase");
        getWebdriverInstance().findElement(By.id("j_id0:loginForm:loginButton")).click();
    }


    @Override
    public void clickAnyArticle() {
        SFHCIndexPage indexPage;
        if (helpCenterInfo.isIntl()) {
            indexPage = new SFIntlIndexPage(getWebdriverInstance());
        }
        else {
            indexPage = new SFHCIndexPage(getWebdriverInstance());
        }
        indexPage.clickAnyArticle();
    }

    @Override
    public void checkSendUsEmailAction(String customerType) {
        throw new UnimplementedTestException("checkSendUsEmailAction no implemented for this model");
    }


    @Override
    public void clickLearnMoreLink() {
        throw new UnimplementedTestException("No implemented for this model");
    }

    @Override
    public void checkErrorMessage() {
        Assertions.assertThat(new SFContactUsForm(getWebdriverInstance()).getMessage())
                .contains("Please correct the following");
    }

    @Override
    public void fillPhoneNumber(String s) {
        throw new UnimplementedTestException("No implemented for this model");
    }


    public void helpCenterSearch(String text) {
        new SFHCIndexPage(getWebdriverInstance()).searchWithText(text);
    }


    public void verifyErrorMessage(String message) {
        assertThat(new SFHCIndexPage(getWebdriverInstance()).verifyErrorMessage())
                .as("Error message isn't valid")
                .contains(message);
    }


    public void verifySearchResults(String text) {
        assertThat(new SFHCIndexPage(getWebdriverInstance()).verifySearchResults())
                .as("Search result isn't valid")
                .containsIgnoringCase(text);
    }

    public void verifyChatPresenceOnSFArticlePage(boolean negation) {
        boolean chatIconVisibility = new SFArticlePage(getWebdriverInstance()).isChatIconOnPage();
        if (negation) {
            assertThat(chatIconVisibility).isTrue();
        }
        else {
            assertThat(chatIconVisibility).isFalse();
        }

    }

    public void clickLiveChatIcon() {
        new SFArticlePage(getWebdriverInstance()).clickLiveChatIcon();
    }

    public void loginInSFAdminSite() {
        getWebdriverInstance().navigate().to(SF_ADMIN_URL);
        new SFAdminLoginPage(getWebdriverInstance()).loginToSalesForce();
    }

    public void clickOnSmartContact() {
        new SFAdminIndexPage(getWebdriverInstance()).clickOnSmartContact();
    }

    public void switchSFAdminCategory(String category) {
        //Too many sub pages, that's why without page object's. Also this method will be used only for one scenario.
        new WebDriverWait(getWebdriverInstance(), 2)
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("go"))).click();
        //Paste category name to xpath locator
        String editCategory = String.format(EDIT_CATEGORY_TEMP, category);
        new WebDriverWait(getWebdriverInstance(), 4)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(editCategory))).click();
        new WebDriverWait(getWebdriverInstance(), 2)
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("save")));
        List<WebElement> checks = getWebdriverInstance().findElements(By.cssSelector("table.detailList input"));
        for (WebElement check : checks) {
            if (helpCenterInfo.getMode().equals(HelpCenterInfo.CONTACTS_MODE.ON)) {
                if (!check.isSelected()) {
                    check.click();
                }
            }
            else {
                if (check.isSelected()) {
                    check.click();
                }
            }
        }
        getWebdriverInstance().findElement(By.name("save")).click();
    }

    public void goToCategoryURL(String category) {
        String url = "http://hw.qa.cs7.force.com/knowledgebase/category?categoryName=" + category;
        if (helpCenterInfo.isExpressSession()) {
            url = url + "&customerType=express";
        }
        getWebdriverInstance().navigate().to(url);
    }


    public void verifySmartContactModules() {
        if (helpCenterInfo.isExpressSession()) {
            verifyExpressContacts();
        }
        else {
            verifyNonExpressContacts();
        }
    }

    private void verifyNonExpressContacts() {
        SFArticlePage articlePage = new SFArticlePage(getWebdriverInstance());
        if (helpCenterInfo.getMode().equals(HelpCenterInfo.CONTACTS_MODE.ON)) {
            List<String> phones =  articlePage.getPhones();
            Assertions.assertThat(phones.get(0)).isEqualTo(helpCenterInfo.getServicePhone());
            Assertions.assertThat(phones.get(1)).isEqualTo(helpCenterInfo.getSalesPhone());
            Assertions.assertThat(phones.get(2)).isEqualTo(helpCenterInfo.getRowPhone());
            Assertions.assertThat(articlePage.getEmail()).isTrue();
        }
        else {
            Assertions.assertThat(articlePage.getPhones()).isNull();
            Assertions.assertThat(articlePage.getEmail()).isFalse();
        }
    }

    private void verifyExpressContacts() {
        SFArticlePage articlePage = new SFArticlePage(getWebdriverInstance());
        if (helpCenterInfo.getMode().equals(HelpCenterInfo.CONTACTS_MODE.ON)) {
            List<String> phones =  articlePage.getExpressPhones();
            Assertions.assertThat(phones.get(0)).isEqualTo(helpCenterInfo.getUsExpressPhone());
            Assertions.assertThat(phones.get(1)).isEqualTo(helpCenterInfo.getRowExpressPhone());
            Assertions.assertThat(articlePage.getExpressEmail()).isEqualTo(helpCenterInfo.getExpressEmail());
        }
        else {
            Assertions.assertThat(articlePage.getExpressPhones()).isNull();
            Assertions.assertThat(articlePage.getExpressEmail()).isNull();
        }
    }

    public void verifyVotingModulePresence() {
        assertThat(new SFArticlePage(getWebdriverInstance()).isVotingContainerOnPage())
                .as("Voting container is not displayed on the  page").isTrue();
        assertThat(new SFArticlePage(getWebdriverInstance()).areStarsDisabled()).
                as("Stars are disabled").isFalse();
    }


    public void verifyFeedbackFormPresence(boolean isDisplayed) {
        assertThat(new SFArticlePage(getWebdriverInstance()).isFeedbackFormVisible())
                .as("Article's feedback form is visible")
                .isEqualTo(isDisplayed);
    }

    public void verifyVotingModule(int count) {
        assertThat(new SFArticlePage(getWebdriverInstance()).starsCount()).isEqualTo(count);
    }

    public void clickAnyStar() {
        new SFArticlePage(getWebdriverInstance()).clickAnyStar();
    }


    public void starsChangeTheirColor() {
        assertThat(new SFArticlePage(getWebdriverInstance()).areStarsHighlighted()).isTrue();

    }

    public void starsComponentIsDisabled() {
        assertThat(new SFArticlePage(getWebdriverInstance()).areStarsDisabled()).
                as("Stars are not disabled").isTrue();
    }


    public void verifyBreadCrumbs() {
        String topLink = "Help Center";
        if (helpCenterInfo.isIntl()) {
            topLink = "Help Centre";
        }
        new SFArticlePage(getWebdriverInstance()).verifyBreadCrumbs(topLink);
    }


    public void verifyRelatedArticles() {
        assertThat(new SFArticlePage(getWebdriverInstance()).verifyRelatedArcticles())
                .as("Related articles don't present")
                .isTrue();
    }


    public void verifyTravelAdvisories() {
        if (helpCenterInfo.isIntl()) {
            Assertions.assertThat(new SFIntlIndexPage(getWebdriverInstance()).verifyTravelAdvisories()).isTrue();
        }
        else {
            Assertions.assertThat(new SFHCIndexPage(getWebdriverInstance()).verifyTravelAdvisories()).isTrue();
        }
    }

    public void clickOnTravelAdvisoryAlert() {
        new TravelAdvisoryAlertsSiteFragment(getWebdriverInstance()).clickOnTravelAdvisoryAlert();
    }



    public void leaveArticleFeedback() {
        new SFArticlePage(getWebdriverInstance()).typeFeedbackMessage("Hello world!").submitFeedbackForm();
    }


    public void verifyArticleFeedbackResponseMessage(String message) {
        assertThat(new SFArticlePage(getWebdriverInstance()).getFeedbackMessageAfterFormClosed()).isEqualTo(message);
    }

    public void declineArticleFeedbackForm() {
        new SFArticlePage(getWebdriverInstance()).cancelFeedbackForm();
    }

}

