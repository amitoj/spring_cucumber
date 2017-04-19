/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.overcharges;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.c3.C3OverchargesInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: v-abudyak
 * Date: 9/19/13
 * Time: 8:59 AM
 */

public class C3OverchargeSteps extends ToolsAbstractSteps {

    @Autowired
    private C3OverchargeModel model;

    @Autowired
    private C3OverchargesInfo c3OverchargesInfo;

    @Given("^I search overcharges by (Last Overcharge Date|Last Modified Date) (?:for recent|over) (.*)$")
    public void searchUnassignedOverchargesByDate(String searchBy, String period) {
        model.searchOverchargesWithPeriod(searchBy, period, "");
    }

    @Given("^I search overcharges assigned to (.*)" +
            " by (Last Overcharge Date|Last Modified Date) (?:for recent|over) (.*)$")
    public void searchOverchargesByAssigneeAndDate(String assignee, String searchBy, String period) {
        model.searchOverchargesWithPeriod(searchBy, period, assignee);
    }

    @Given("^I search for unassigned overcharges$")
    public void searchForUnassignedOvercharges() {
        model.chooseSearchForUnassignedOvercharges();
        model.startSearch();
    }

    @Given("^I start search for overcharges$")
    public void startSearchForOvercharges() {
        model.startSearch();
    }

    @When("^I click on unassigned checkbox$")
    public void clickOnUnassigned() {
        model.clickOnUnassigned();
    }

    @Given("^I choose (.*) as assignee$")
    public void clickOnUnassigned(String hsr) {
        model.fillHSR(hsr);
    }

    @Given("^I enter a part of overcharge hotel name$")
    public void setHotelName() {
        model.setOverchargeHotelNamePart();
    }

    @Given("^I set overcharge state$")
    public void setState() {
        model.setState(c3OverchargesInfo.getHotelState());
    }

    @Given("^overcharge hotel name contains \"(.*)\"$")
    public void verifyHotelName(String hotelName) {
        model.verifyHotelName(hotelName);
    }

    @Given("^I see overcharges search result page$")
    public void i_see_overcharges_search_result_page() {
        model.verifySearchResult();
    }

    @Given("^I check unassigned overcharges results according DB$")
    public void check_unassigned_overcharges_from_DB_On_page() {
        model.checkUnassignedOverchargesFromDB();
    }

    @Given("^I open any overcharge details$")
    public void openAnyOverchargeDetails() {
        model.openAnyOverchargeDetails();
    }

    @Given("^I update any overcharge itinerary with bulk editor$")
    public void updateAnyOverchargeItinerary() {
        model.updateAnyOverchargeItinerary();
    }

    @Given("^I check overcharge itinerary update in DB$")
    public void checkOverchargesItineraryUpdateInDB() {
        model.checkOverchargesItineraryUpdateInDB();
    }

    @Given("^I open hotel with minimum overcharges$")
    public void openHotelWithMinimumOvercharges() {
        model.openHotelWithMinimumOvercharges();
    }

    @Given("^I return back to results$")
    public void returnBackToResultsFromDetails() {
        model.returnBackToResultsFromDetails();
    }

    @When("^I sort results \"([^\"]*)\" field (asc|desc)$")
    public void i_search_results_field_decs(String field, String order) {
        c3OverchargesInfo.setOverchargeColumn(field);
        if (order.equals("asc")) {
            c3OverchargesInfo.setSorting(true);
        }
        else {
            c3OverchargesInfo.setSorting(false);
        }
        model.sortFieldByOrder();
    }

    @Then("^I verify results sorted correctly$")
    public void verifySortingResults() {
        model.verifySortingOrder();
    }

    @Then("^I see overcharges results$")
    public void verifyOverchargeResults() {
        model.verifyOverchargeResults();
    }

    @Then("^I see Hotel Overcharge Information$")
    public void verifyHotelOverchargeInfo() {
        model.verifyHotelOverchargeInfoPage();
    }

    @Then("^I fix Supplier Contact Information$")
    public void fixSupplierContactInformation() {
        model.fixSupplierContactInformation();
    }

    @Then("^I see less or equal than (.*) overcharge results$")
    public void verifyOverchargesNum(Integer number) {
        model.verifyOverchargesNum(number);
    }

    @When("^I click on overcharge amount$")
    public void clickOnOverchargeAmount() {
        model.clickOnOverchargeAmount();
    }

    @Then("^I see overcharge amount pop-up$")
    public void verifyOverchargeAmountPopUp() {
        model.verifyOverchargeAmountPopUp();
    }

    @Then("^I change status of overcharge$")
    public void changeOverchargeStatus() {
        model.changeOverchargeStatus();
        model.submitChangedStatus();
    }

    @Then("^I (don't )?see changed overcharge$")
    public void verifyChangedOverchargeVisibility(String negation) {
        model.verifyChangedOverchargeVisibility(StringUtils.isEmpty(negation));
    }

    @Then("^I see overcharge in notes$")
    public void verifyChangedOverchargeInNotes() {
        model.verifyChangedOverchargeInNotes();
    }

    @When("I click on show History$")
    public void showOverchargeHistory() {
        model.showOverchargeHistory();
    }

    @Given("^the hotel with(out)? overcharges$")
    public void setupOverchargeHotel(String withOvercharges) {
        model.setupOverchargeHotel(StringUtils.isEmpty(withOvercharges));
    }

    @Given("^I open overcharge details$")
    public void openOverchargeDetails() {
        model.openOverchargeDetails();
    }

    @Given("^I see overcharges report$")
    public void verifyOverchargesReport() {
        model.verifyOverchargesReport();
    }

    @Given("^I update any overcharge with csrcroz9$")
    public void updateOvercharges() {
        model.updateOvercharges();
    }

    @Given("^I unassign all overcharges$")
    public void unAsssignAll() {
        model.unAsssignAll();
    }

    @Given("^updated overcharge (is|is not) displayed in the list$")
    public void checkOverchargeInList(String isDisplayedOrNot) {
        model.checkOverchargeInList("is".equals(isDisplayedOrNot));
    }

    @Given("^I click on search for overcharges information$")
    public void clickOnSearchForOverchargesInfo() {
        model.clickOnSearchForOverchargesInfo();
    }


}


