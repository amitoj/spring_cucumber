/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.car.steps;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.search.car.CarSearchModel;
import com.hotwire.test.steps.search.car.CarSearchParameters;
import com.hotwire.test.steps.search.car.db.CarSearchDao;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.hotwire.selenium.desktop.us.results.car.fragments.depositFilter.CarDepositFilter.DepositTypes;
import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-vzyryanov
 * Date: 6/27/13
 * Time: 3:40 AM
 */
public class CarSearchDepositFilterSteps extends AbstractSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSearchDepositFilterSteps.class.getName());
    private static final Map<String, String[]> DEPOSIT_TYPE_PARAM = new HashMap<String, String[]>();

    static {
        DEPOSIT_TYPE_PARAM.put("card of any type", new String[]{"Y", "Y"});
        DEPOSIT_TYPE_PARAM.put("traveler's debit card", new String[]{"Y", "N"});
        DEPOSIT_TYPE_PARAM.put("credit card", new String[]{"N", "N"});
    }

    @Autowired
    @Qualifier("carSearchModel")
    private CarSearchModel carSearchModel;

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParameters carSearchParameters;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Given("^I am searching in location where (credit card|traveler's debit card|card of any type) is allow to use$")
    public void i_am_from_location_with_specific_deposit_type(String depositType) {

        CarSearchDao dao = new CarSearchDao(jdbcTemplate);
        String[] options = DEPOSIT_TYPE_PARAM.get(depositType);

        String pickUpLocation = dao.getLocationByDepositFilterType(options[0], options[1]);
        StringBuilder sb =
                new StringBuilder("<" + pickUpLocation + "> was selected as location where " +
                        depositType + " are allowed..");
        sb.append("\nis_debit_card_ok_for_deposit=" + options[0] + "; accepts_local_debit_card=" + options[1]);
        LOGGER.info(sb.toString());

        carSearchParameters.getGlobalSearchParameters().setDestinationLocation(pickUpLocation);
    }

    @Then("^I(?:(?:\\s)(don\'t))? see Deposit filtering block$")
    public void i_see_deposit_filtering_block(String negation) {
        assertThat("don't".trim().equals(negation))
            .as("Visibility of deposit filter block")
                .isNotEqualTo(carSearchModel.isDepositFilterPresent());
    }

    @When("^I select (DEBIT|CREDIT) card option$")
    public void i_select_deposit_card_option(DepositTypes cardType) {
        carSearchModel.selectDepositOptionByType(cardType);
    }

    @Then("^I choose \"(I_AM_TRAVELING|I_AM_LOCAL)\" option for debit card$")
    public void i_choose_travel_type_for_debit_card(DepositTypes type) {
        carSearchModel.selectDepositOptionByType(type);
    }

    @Then("^Deposit type \"(CREDIT|I_AM_TRAVELING|I_AM_LOCAL)\" is selected$")
    public void option_for_debit_card_is_selected(DepositTypes type) {
        carSearchModel.verifyDepositTypeSelection(type);
    }

    @Given("^I (open|close) debit deposit explanation popup$")
    public void openCloseDebitExplanationPopup(String action) {
        boolean doOpen = false;
        if ("open".equalsIgnoreCase(action)) {
            doOpen = true;
        }
        carSearchModel.openDepositFilterExplanationPopup(doOpen);
    }

    @Then("^The debit deposit explanation popup is (open|closed)$")
    public void is_debit_deposit_explanation_open(String action) {
        if ("open".equals(action)) {
            assertThat(carSearchModel.isDebitDepositExplanationOpen())
                    .as("Debit deposit explanation block is open")
                        .isTrue();
        }
        if ("closed".equals(action)) {
            assertThat(carSearchModel.isDebitDepositExplanationOpen())
                    .as("Debit deposit explanation block is open")
                    .isFalse();
        }
    }

    @Then("^Debit deposit explanation popup contains text$")
    public void verifyExplanationPopupText(String text) {
        carSearchModel.verifyDepositFilterPopupExplanation(text);
    }

    @Then("^Debit deposit explanation contains text$")
    public void verifyExplanationText(String text) {
        carSearchModel.verifyDepositFilterText(text);
    }
}
