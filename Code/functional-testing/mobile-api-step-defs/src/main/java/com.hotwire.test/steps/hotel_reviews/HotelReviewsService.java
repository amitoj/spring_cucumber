/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.hotel_reviews;

import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.search.SearchResponseHolder;
import hotwire.view.jaxb.domain.mobileapi.ClientInfoType;
import hotwire.view.jaxb.domain.mobileapi.HotelReviewsRQ;
import hotwire.view.jaxb.domain.mobileapi.HotelReviewsRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * User: v-dsobko
 * Since: 02/07/15
 */
public class HotelReviewsService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelReviewsService.class.getName());

    private RequestPaths paths;
    private SearchResponseHolder searchResponseHolder;
    private RequestProperties requestProperties;

    private HotelReviewsRS hotelReviewsResponse;

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setSearchResponseHolder(SearchResponseHolder searchResponseHolder) {
        this.searchResponseHolder = searchResponseHolder;
    }

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void executeHotelReviewsRequest() {
        HotelReviewsRQ hotelReviewsRequest = createHotelReviewsRequest();
        try {
            client.reset()
                    .path(paths.getHotelReviewsPath())
                    .header("User-Agent", "SomeMobileClient")
                    .accept(MediaType.APPLICATION_XML_TYPE);

            hotelReviewsResponse = client.post(hotelReviewsRequest, HotelReviewsRS.class);

        }
        catch (WebApplicationException e) {
            LOGGER.error("Hotel reviews request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Hotel reviews request has failed with  " + message, e);
        }

    }

    public void verifyHotelReviewsResponse() {
        assertThat(hotelReviewsResponse.getErrors()).isNull();
        assertThat(hotelReviewsResponse.getHotelReviews()).isNotNull();
    }

    private HotelReviewsRQ createHotelReviewsRequest() {
        HotelReviewsRQ hotelReviewsRequest = new HotelReviewsRQ();

        hotelReviewsRequest.setResultID(searchResponseHolder.getResponse().getSearchResults()
                .getSolution().get(0).getResultID());

        hotelReviewsRequest.setClientInfo(new ClientInfoType());
        hotelReviewsRequest.getClientInfo().setClientID(requestProperties.getClientId());
        hotelReviewsRequest.getClientInfo().setCountryCode(requestProperties.getCountryCode());
        hotelReviewsRequest.getClientInfo().setCurrencyCode(requestProperties.getCurrencyCode());
        hotelReviewsRequest.getClientInfo().setCustomerID(requestProperties.getCustomerId());
        hotelReviewsRequest.getClientInfo().setLatLong(requestProperties.getLatLong());

        hotelReviewsRequest.setIndex(0);
        hotelReviewsRequest.setLength(10);

        return hotelReviewsRequest;
    }


}
