/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.car.steps;

import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.car.CarPurchaseModel;
import com.hotwire.test.steps.purchase.car.entities.VendorGridEntity;
import com.hotwire.test.steps.purchase.hotel.HotelPurchaseModel;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-vzyryanov
 * Date: 6/14/13
 * Time: 3:58 AM
 */
public class CarPurchaseVendorGridSteps extends AbstractSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarPurchaseVendorGridSteps.class.getName());

    @Autowired
    @Qualifier("hotelPurchaseModel")
    private HotelPurchaseModel purchaseModel;

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel model;

    /**
     * Check that vendor grid is presents on car details page
     * when retail car was selected.
     */
    @When("^I switch between retail solutions and car\'s details has updated according to the vendor grid$")
    public void i_switch_between_retail_solutions() {

        assertThat(model.getAvailableVendorEntitiesFromDetails().size())
                .as("List of vendor's offers for retail car is empty on details page")
                .isGreaterThan(0);

        model.verifyVendorChangingOnDetails();
    }

    /**
     * Checking sort order in vendor grid.
     * Offer with lowest price should be first and with higher
     * should be last.
     */
    @Given("^Results in the vendor grid are sorted by total price$")
    public void results_are_sorted_by_total_price() {
        float totalPrice = 0;
        for (VendorGridEntity en : model.getAvailableVendorEntitiesFromDetails()) {
            LOGGER.info("CHECK VENDOR ROW: " + en);
            assertThat(en.getTotalPrice()).as("Compare total price with previous").isGreaterThanOrEqualTo(totalPrice);
            totalPrice = en.getTotalPrice();
        }
    }

    /**
     * Get car features list on Car CCF results/details page
     * @param negation  flag for checking avalability/unavalability of carFeature
     * @param location   Car location to check (Shuttle to car, counter in airport else)
     */
    @Given("^I[\\s]*(?:(don\\'t))? see (.+) in car features list$")
    public void verifyCarFeatureList(String negation, String location) {
        if (StringUtils.isEmpty(negation)) {
            assertThat(model.isResultsContainLocationDescription(location))
                    .as("Feature is in Car Features List");
        }
        else {
            assertThat(!model.isResultsContainLocationDescription(location))
                    .as("Feature is not in Car Features List");
        }
    }

    @Given("^I[\\s]*(?:(don\\'t))? see map in vendor grid$")
    public void verifyCarFeatureList(String negation) {
        model.checkMapAvailability(StringUtils.isEmpty(negation));
    }

    @Given("only one map is displayed$")
    public void verifyVendorMapNum() {
        model.verifyVendorMapNum();
    }


    @Given("I see airport result in vendor grid$")
    public void verifyAirportResultsInVendorGrid() {
        model.verifyAirportResultsInVendorGrid();
    }

    @Given("^I close the map$")
    public void closeVendorMap() {
        model.closeVendorMap();
    }

    @Given("^I click on vendor address link$")
    public void verifyCarFeatureList() {
        model.clickOnVendorAddressLink();
    }

    /**
     * Checks that vendor with lowest price was selected
     * by default in grid for retail car
     */
    @Given("^Result with lowest total price should be checked by default$")
    public void result_with_lowest_total_price_checked_by_default() {

        List<VendorGridEntity> entities = model.getAvailableVendorEntitiesFromDetails();
        Collections.sort(entities);
        VendorGridEntity vendorWithLowestTotalPrice = entities.get(0);

        VendorGridEntity vendorCheckedByDefault = model.getCheckedVendorEntityOnDetails();
        assertThat(vendorCheckedByDefault.getTotalPrice())
                .as("Vendor with lowest price is " + vendorWithLowestTotalPrice + " but was selected " +
                        vendorCheckedByDefault)
                .isEqualTo(vendorWithLowestTotalPrice.getTotalPrice());
    }

    /**
     * Selecting vendor from grid by price type
     */
    @Given("^I select vendor offer with (lowest|higher|random) price$")
    public void i_select_vendor_offer_by_price(String offerPriceType) {

        List<VendorGridEntity> entities = model.getAvailableVendorEntitiesFromDetails();

        if ("random".equals(offerPriceType)) {
            VendorGridEntity en = entities.get(new Random().nextInt(entities.size()));
            LOGGER.info(en + " WAS SELECTED RANDOMLY..");
            model.selectVendorEntityOnDetails(en);
            return;
        }

        Collections.sort(entities);

        if ("lowest".equals(offerPriceType)) {
            VendorGridEntity en = entities.get(0);
            LOGGER.info(en + " VENDOR WITH LOWEST TOTAL PRICE WAS SELECTED..");
            model.selectVendorEntityOnDetails(en);
        }

        if ("higher".equals(offerPriceType)) {
            VendorGridEntity en = entities.get(entities.size() - 1);
            LOGGER.info(en + " VENDOR WITH HIGHER TOTAL PRICE WAS SELECTED..");
            model.selectVendorEntityOnDetails(en);
        }
    }
}
