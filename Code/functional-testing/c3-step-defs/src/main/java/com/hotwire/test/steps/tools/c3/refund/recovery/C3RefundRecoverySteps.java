/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.refund.recovery;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.test.steps.tools.c3.refund.C3RefundModel;
import com.hotwire.util.db.c3.C3RefundRecoveryCustomerDao;
import cucumber.api.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * User: v-vzyryanov
 * Date: 10/10/13
 * Time: 5:38 AM
 */
public class C3RefundRecoverySteps extends ToolsAbstractSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3RefundRecoverySteps.class);

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private C3RefundModel c3RefundModel;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Autowired
    private SimpleJdbcDaoSupport databaseSupport;


    @Given("^customer purchase with allowed recovery status \"(YES|NO|NOT DISPLAYED)\"$")
    public void searchForUserWithAllowedRecoveryDisplayed(String status) {
        String itineraryNum = null;

        if ("YES".equals(status)) {
            String email = new C3RefundRecoveryCustomerDao(databaseSupport).getCustomerEmailWithAllowedRecoveryYes();
            customerInfo.setEmail(email);
            customerInfo.setFirstName("Anastasiia");
            LOGGER.info("Searching for a customer with email " + email);
        }
        else if ("NO".equals(status)) {
            itineraryNum = new C3RefundRecoveryCustomerDao(databaseSupport).getItineraryWithAllowedRecoveryNo();
            LOGGER.info("Searching for a itinerary " + itineraryNum + " to verify allowed recovery status " + status);
            c3ItineraryInfo.setItineraryNumber(itineraryNum);
        }
        else {
            itineraryNum = new C3RefundRecoveryCustomerDao(databaseSupport)
                    .getItinararyWithAllowedRecoveryNotDisplayed();
            LOGGER.info("Searching for a itinerary " + itineraryNum + " to verify allowed recovery status " + status);
            c3ItineraryInfo.setItineraryNumber(itineraryNum);
        }
        hotwireProduct.setProductVerticalByItinerary(c3ItineraryInfo.getItineraryNumber());

    }

    @Given("^Allowed Recovery status is (YES|Not allowed|INVESTIGATE|NOT DISPLAYED)$")
    public void verifyStatusOfAllowedRecoveryField(String status) {
        c3RefundModel.verifyPurchaseAllowedRecoveryStatus(status);
    }
}
