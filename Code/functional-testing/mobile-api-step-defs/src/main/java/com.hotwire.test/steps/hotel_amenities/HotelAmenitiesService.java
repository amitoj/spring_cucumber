/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.hotel_amenities;

import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import cucumber.api.PendingException;
import hotwire.view.jaxb.domain.mobileapi.HotelAmenities;
import hotwire.view.jaxb.domain.mobileapi.booking.hotel.AmenitiesRQ;
import hotwire.view.jaxb.domain.mobileapi.booking.hotel.AmenitiesRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * User: v-dpuchkov
 * Since: 03/17/15
 */
public class HotelAmenitiesService  extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelAmenitiesService.class.getName());

    private AmenitiesRS hotelAmenitiesResponse;

    private RequestProperties requestProperties;
    private RequestPaths paths;

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void executeHotelAmenitiesRequest(String roomType) {
        AmenitiesRQ amenitiesRQ = new AmenitiesRQ();

        amenitiesRQ.setHotelID(requestProperties.getHotelId());
        amenitiesRQ.setRoomTypeID(roomType);

        try {
            client.reset()
                    .path(paths.getHotelAmenitiesPath())
                    .header("User-Agent", "SomeMobileClient")
                    .accept(MediaType.APPLICATION_XML_TYPE);

            hotelAmenitiesResponse = client.post(amenitiesRQ, AmenitiesRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Hotel Amenities request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Hotel Amenities  request has failed with  " +  message, e);
        }
    }

    public void verifyHotelAmenities() {
        assertThat(hotelAmenitiesResponse).isNotNull();
        assertThat(hotelAmenitiesResponse.getErrors()).isNull();
        if (!getHotelAmenitiesList().isEmpty()) {
            assertThat(getHotelAmenitiesList().get(0).getCode()).isNotNull();
            assertThat(getHotelAmenitiesList().get(0).getName()).isNotNull();
            assertThat(getHotelAmenitiesList().get(0).getDescription()).isNotNull();
        }
        else {
            LOGGER.error("Hotel Amenities response returned empty amenities list");
            throw new PendingException("Hotel Amenities response returned empty amenities list");
        }
    }

    private List<HotelAmenities> getHotelAmenitiesList() {
        return hotelAmenitiesResponse.getHotelAmenities().getHotelAmenity();
    }
}


