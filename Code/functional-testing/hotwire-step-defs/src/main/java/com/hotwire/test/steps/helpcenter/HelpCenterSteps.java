/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.helpcenter;

import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 7/22/13
 * Time: 6:48 AM
 */
public class HelpCenterSteps extends AbstractSteps {

    @Autowired
    @Qualifier("applicationModel")
    ApplicationModel applicationModel;


    @Autowired
    private HelpCenterInfo helpCenterInfo;

    @Autowired
    private OldHelpCenterModel oldHelpCenterModel;

    @Autowired
    private SalesforceHelpCenterModel salesforceHelpCenterModel;

    private HelpCenterModel hcModel;

    private HelpCenterModel getHelpCenterModel(String hcType) {
        if ("salesforce".equals(hcType.toLowerCase().replace(" ", ""))) {
            return salesforceHelpCenterModel;
        }
        else {
            return oldHelpCenterModel;
        }
    }


    @Then("^(.*) Help Center is opened$")
    public void specified_help_center_is_opened(String hcType) {
        hcModel = getHelpCenterModel(hcType);
    }

    @Then("^I see Old Help center$")
    public void verifyOldHelpCenter() {
        hcModel = getHelpCenterModel("helpcenter");
        oldHelpCenterModel.verifyArticlePage();
    }



    @Then("^I see INTL SF Help Center$")
    public void verifyIntlSFHelpCenter() {
        hcModel = getHelpCenterModel("salesforce");
        applicationModel.verifyPageURL("/knowledgebase/");
        applicationModel.verifyPageURL("/help-centre");
        helpCenterInfo.setIntl(true);
        salesforceHelpCenterModel.verifyBreadCrumbs();
    }

    @Then("^I see SF Help Center$")
    public void verifySFHelpCenter() {
        hcModel = getHelpCenterModel("salesforce");
        applicationModel.verifyPageURL("/knowledgebase");
        salesforceHelpCenterModel.verifyBreadCrumbs();
    }

    @Given("^I login into Sales Force admin site$")
    public void loginInSFAdminSite() {
        salesforceHelpCenterModel.loginInSFAdminSite();
        hcModel = getHelpCenterModel("Salesforce");
    }

    @When("^I click on smart contacts$")
    public void clickOnSmartContact() {
        salesforceHelpCenterModel.clickOnSmartContact();
    }

    @When("^I turn (ON|OFF) contact modules$")
    public void chooseSFAdminCategory(HelpCenterInfo.CONTACTS_MODE mode) {
        String category = helpCenterInfo.getArticleCategory();
        helpCenterInfo.setMode(mode);
        salesforceHelpCenterModel.switchSFAdminCategory(category);
    }

    @When("^I click on travel advisory alert$")
    public void clickOnTravelAdvisoryAlert()  {
        salesforceHelpCenterModel.clickOnTravelAdvisoryAlert();
    }

    @When("^I see travel advisories$")
    public void verifyTravelAdvisoriesInSalesForce()  {
        salesforceHelpCenterModel.verifyTravelAdvisories();
    }


    @When("^I see travel advisories page")
    public void verifyTravelAdvisoriesPage()  {
        salesforceHelpCenterModel.verifyBreadCrumbs();
    }

    @Given("^I go to category link( as express)?$")
    public void goToCategoryLink(String express) {
        if (StringUtils.isNotEmpty(express)) {
            helpCenterInfo.setExpressSession(true);
        }
        String category = helpCenterInfo.getArticleCategory();
        salesforceHelpCenterModel.goToCategoryURL(category);
    }

    @Given("^I see contact modules correspond to SF Admin settings$")
    public void verifySmartContactModules() {
        salesforceHelpCenterModel.verifySmartContactModules();
    }

    @When("^I click Send us an email link$")
    public void i_click_link() {
        hcModel.clickSendUsEmailLink();
    }

    @Then("^I see /helpcenter/contact.jsp page for (.*) customer$")
    public void i_see_helpcenter_contact_jsp_page(String customerType) {
        hcModel.checkSendUsEmailAction(customerType);
    }


    @Then("^I see Send us an email link with certain href attribute$")
    public void i_see_certain_href_attribute() {
        hcModel.checkSendUsEmailAction("");
    }

    @Given("^I see copy \"([^\"]*)\"$")
    public void i_see_copy(String text) {
        oldHelpCenterModel.checkHCContactUsText(text);
    }

    @When("^I click Learn more link$")
    public void i_click_learn_more_link() {
        hcModel.clickLearnMoreLink();
    }

    @Then("^I see (.*) help center page$")
    public void i_see_about_hotwire_express_jsp_page(String url) {
        applicationModel.verifyPageURL(url);
    }

    @Given("^I fill in all the fields$")
    public void i_fill_in_all_the_fields() {
        hcModel.fillContactUs();
    }

    @When("^I click Send button$")
    public void i_click_Send_button() {
        hcModel.clickSendButton();
    }

    @Then("^I receive an error message$")
    public void i_receive_an_error_message() {
        hcModel.checkErrorMessage();
    }

    @Then("^I fill in Phone number$")
    public void i_fill_in_Phone_number() {
        hcModel.fillPhoneNumber("1-866-468-9473");
    }

    @When("^click Send button$")
    public void click_Send_button() {
        hcModel.clickSendButton();
    }

    @Then("^I see successful message$")
    public void i_see_successful_message() {
        hcModel.checkSuccessSendContactUsFormMessage();
    }

    @Given("^I see (.*) field is pre-filled$")
    public void i_see__element_is_pre_filled(String elementName) {
        hcModel.checkElementPreFilled(elementName);
    }

    @Then("^I see (.*) field is highlighted$")
    public void i_see_element_is_highlighted(String elementName) {
        hcModel.checkElementHighlighted(elementName);
    }


    @Then("^I am on (.*) Help Center page$")
    public void i_am_on_Help_Center_page(String hcType) {
        hcModel = getHelpCenterModel(hcType);
    }

    @Then("^URL of the page contains (.*)$")
    public void url_of_the_page_contains_string(String url) {
        applicationModel.verifyPageURL(url);
    }

    @When("^I open any article$")
    public void i_click_subcategory_article() {
        hcModel.clickAnyArticle();
    }

    @Given("^SF HelpCenter is accessible$")
    public void sF_HelpCenter_is_accessible() {
        hcModel = salesforceHelpCenterModel;
        hcModel.setupHC();
    }

    @When("^I search with phrase \"([^\"]*)\"$")
    public void i_search_with_word(String phrase) {
        salesforceHelpCenterModel.helpCenterSearch(phrase);
    }

    @Then("^I see error message \"([^\"]*)\"$")
    public void i_see_error_message(String message) {
        salesforceHelpCenterModel.verifyErrorMessage(message);
    }

    @Then("^I see search result for phrase \"([^\"]*)\"$")
    public void i_see_search_result(String phrase) {
        salesforceHelpCenterModel.verifySearchResults(phrase);
    }

    @When("^I click any article$")
    public void i_click_any_article() {
        hcModel.clickAnyArticle();
    }

    @When("^I\\s?(don't)? see Live Chat on article page$")
    public void chatOnSFArticlePage(String negation) {
        salesforceHelpCenterModel.verifyChatPresenceOnSFArticlePage(StringUtils.isEmpty(negation));
    }

    @When("^I click Live Chat icon$")
    public void clickChatOnSFArticlePage() {
        salesforceHelpCenterModel.clickLiveChatIcon();
    }

    @Then("^I see \"Vote for this article\" component$")
    public void voteComponentIsDisplayed() {
        salesforceHelpCenterModel.verifyVotingModulePresence();
    }

    @Then("^Article's feedback form is(\\snot)? displayed$")
    public void verifyArticleFeedbackFormIsDisplayed(String visibility) {
        salesforceHelpCenterModel.verifyFeedbackFormPresence(visibility == null);
    }

    @Then("^it contains (\\d+) blank stars$")
    public void it_contains_blank_stars(int count) {
        salesforceHelpCenterModel.verifyVotingModule(count);
    }

    @When("^I click any star$")
    public void i_click_any_star() {
        salesforceHelpCenterModel.clickAnyStar();
    }

    @Then("^stars changes their color$")
    public void stars_changes_their_color() {
        salesforceHelpCenterModel.starsChangeTheirColor();

    }

    @Then("^component becomes disabled$")
    public void component_becomes_disabled() {
        salesforceHelpCenterModel.starsComponentIsDisabled();

    }

    @Then("^I see breadcrumb module$")
    public void verifyBreadCrumbsModule() {
        salesforceHelpCenterModel.verifyBreadCrumbs();
    }

    @Then("^I see list of related articles$")
    public void i_see_related_articles() {
        salesforceHelpCenterModel.verifyRelatedArticles();
    }

    @Then("^I enter article feedback and submit$")
    public void enterArticleFeedbackAndSubmit() {
        salesforceHelpCenterModel.leaveArticleFeedback();
    }

    @Then("^I see feedback response message \"(.*)\"$")
    public void feedbackResponseMessage(String message) {
        salesforceHelpCenterModel.verifyArticleFeedbackResponseMessage(message);
    }

    @Then("^I don't want to add article feedback$")
    public void closeArticleFeedbackForm() {
        salesforceHelpCenterModel.declineArticleFeedbackForm();
    }

}

