/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.currency_list;

import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.util.db.mobileapi.MobileApiDao;
import hotwire.view.jaxb.domain.mobileapi.currency.CurrencyListRQ;
import hotwire.view.jaxb.domain.mobileapi.currency.CurrencyListRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * User: v-dsobko
 * Since: 02/05/15
 */
public class CurrencyListService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyListService.class.getName());
    private static final String USD = "USD";
    private static final String US = "US";

    private RequestPaths paths;
    private CurrencyListRS currencyListResponse;
    private SimpleJdbcDaoSupport databaseSupport;


    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setDatabaseSupport(SimpleJdbcDaoSupport databaseSupport) {
        this.databaseSupport = databaseSupport;
    }

    public void executeCurrencyListRequest(String country) {
        CurrencyListRQ currencyListRequest = new CurrencyListRQ();
        currencyListRequest.setCountryCode(country);
        try {
            client.reset()
                    .path(paths.getCurrencyListPath())
                    .header("User-Agent", "SomeMobileClient")
                    .accept(MediaType.APPLICATION_XML_TYPE);

            currencyListResponse = client.post(currencyListRequest, CurrencyListRS.class);

        }
        catch (WebApplicationException e) {
            LOGGER.error("Currency list request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Currency list request has failed with  " +  message, e);
        }
    }

    public void verifyCurrencyListResponse() {
        assertThat(currencyListResponse.getErrors()).isNull();
        assertThat(currencyListResponse.getSupportedCurrencyList()).isNotNull();
    }

    public void verifyCurrenciesInResponse(String country) {
        List<String> actualCurrencies = currencyListResponse.getSupportedCurrencyList().getCurrencyCode();
        Collections.sort(actualCurrencies);
        List<String> expectedCurrencies;

        if (country.equalsIgnoreCase(US)) {
            expectedCurrencies = getCurrencyListFromDBForDomestic();
            assertThat(currencyListResponse.getDefaultCurrencyCode()).isEqualTo(USD);
        }
        else {
            expectedCurrencies = getCurrencyListFromDBForInternational();
            assertThat(currencyListResponse.getDefaultCurrencyCode()).isNotEqualTo(USD);
        }
        Collections.sort(expectedCurrencies);
        assertThat(actualCurrencies).isEqualTo(expectedCurrencies);
    }

    private List<String> getCurrencyListFromDBForDomestic() {
        return new MobileApiDao(databaseSupport).getCurrencyCodesForDomesticPos();
    }

    private List<String> getCurrencyListFromDBForInternational() {
        return new MobileApiDao(databaseSupport).getCurrencyCodesForInternationalPos();
    }

}
