/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */


package com.hotwire.test.steps.currency_list;

import com.hotwire.test.steps.AbstractSteps;
import cucumber.api.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: v-dsobko
 * Since: 02/05/15
 */
public class CurrencyListSteps extends AbstractSteps {

    @Autowired
    private CurrencyListService currencyListService;

    @And("^I execute currency list request for (UK|US|MX|AU)$")
    public void executeCurrencyListRequest(String country) {
        currencyListService.executeCurrencyListRequest(country);
    }

    @And("^currency list response is present$")
    public void verifyCurrencyListResponse() {
        currencyListService.verifyCurrencyListResponse();
    }

    @And("^proper currencies are present for (US|UK|MX|AU)$")
    public void verifyCurrenciesInResponse(String country) {
        currencyListService.verifyCurrenciesInResponse(country);
    }


}
