/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.hotel;

import com.hotwire.test.hotel.HotelSearchPurchase;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author mghosh
 */
@ContextConfiguration("classpath:cucumber.xml")
@DirtiesContext
public class HotelSearchPurchaseSteps {

    @Autowired
    @Qualifier("hotelSearchPurchase")
    private HotelSearchPurchase hotelSearchPurchase;

    @Given("^I inject rates for CRS type (.*) CRS hotel IDs (.*) and rate (.*)$")
    public void injectCRS_Rate(String crsTypes, String crsHotelIds, String rates) {
        hotelSearchPurchase.injectCRS_Rate(crsTypes, crsHotelIds, rates);
    }

    @Given("^I rollback injected rates for CRS type (.*) CRS hotel IDs (.*)$")
    public void removeCRS_Rate(String crsTypes, String crsHotelIds) {
        hotelSearchPurchase.removeCRS_Rate(crsTypes, crsHotelIds);
    }

}
