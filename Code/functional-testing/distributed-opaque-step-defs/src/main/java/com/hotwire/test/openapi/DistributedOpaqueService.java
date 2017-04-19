/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.openapi;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Response;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.fest.assertions.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Implementation of bdd tests for distributed opaque apis
 *
 * @author pgleyzer
 */
public abstract class DistributedOpaqueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedOpaqueService.class);
    private static final String ANY_SYMBOLS = ".+?";
    private static final String PRICE_REGEX = "\\d{0,5}\\.\\d\\d";
    private static final String DATE_REGEX = "\\d\\d/\\d\\d/\\d{4}";

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private SearchProperties searchProperties;

    @Autowired
    private BookingProperties bookingProperties;

    @Autowired
    private  RefundProperties refundProperties;

    private XmlPath getResponse() {
        return apiProperties.getResponse();
    }

    public void sendSearchRequest() {
        XmlPath resp;
        switch (apiProperties.getHotwireProduct()) {
            case CAR:
                resp = given()
                    .queryParam("dest", searchProperties.getDestinationLocation())
                    .queryParam("pickuptime", searchProperties.getPickupTime())
                    .queryParam("dropofftime", searchProperties.getDropoffTime())
                    .queryParam("startdate", calculateStartDate())
                    .queryParam("enddate", calculateEndDate())
                    .and().log().all()
                    .when().get(searchProperties.getCarSearchPath())
                    .then().log().all().statusCode(200).extract().response().xmlPath();
                break;
            case HOTEL:
                resp = given()
                    .queryParam("dest", searchProperties.getDestinationLocation())
                    .queryParam("rooms", searchProperties.getRooms())
                    .queryParam("adults", searchProperties.getAdults())
                    .queryParam("children", searchProperties.getChildren())
                    .queryParam("startdate", calculateStartDate())
                    .queryParam("enddate", calculateEndDate())
                    .and().log().all()
                    .when().get(searchProperties.getHotelSearchPath())
                    .then().log().all().statusCode(200).extract().response().xmlPath();
                break;
            default:
                throw new RuntimeException("No such product!");
        }
        assertThat(resp.getInt("Result.size()"), greaterThan(0));
        apiProperties.setResponse(resp);
        verifyResponseStatus();
    }


    public void verifySearchResults() {
        switch (apiProperties.getHotwireProduct()) {
            case HOTEL:
                verifyHotelSolution();
                break;
            case CAR:
                verifyCarSolution();
                break;
            default:
                throw new RuntimeException("No such product!");
        }
        if (ApiType.PRIVATE_API.equals(apiProperties.getApiType())) {
            Assertions.assertThat(getResponse().getString("PartnerHotelIds.PartnerHotelMapping.PartnerHotelId"))
                    .matches("\\d*");
            Assertions.assertThat(getResponse().getString("PartnerHotelIds.PartnerHotelMapping.PartnerName"))
                    .isEqualTo("Expedia");
        }
    }

    private void verifyCarSolution() {
        getResponse().setRoot("Hotwire.Result.CarResult[0]");
        Assertions.assertThat(getResponse().getString("")).matches(ANY_SYMBOLS);
        Assertions.assertThat(getResponse().getString("DeepLink")).matches(".+/car/.+");
        Assertions.assertThat(getResponse().getString("SubTotal")).matches(PRICE_REGEX);
        Assertions.assertThat(getResponse().getString("TaxesAndFees")).matches(PRICE_REGEX);
        Assertions.assertThat(getResponse().getString("TotalPrice")).matches(PRICE_REGEX);
        Assertions.assertThat(getResponse().getString("DailyRate")).matches(PRICE_REGEX);
        Assertions.assertThat(getResponse().getString("CarTypeCode")).matches(ANY_SYMBOLS);
        Assertions.assertThat(getResponse().getString("DropoffTime")).matches("\\d\\d:\\d\\d");
        Assertions.assertThat(getResponse().getString("PickupTime")).matches("\\d\\d:\\d\\d");
        Assertions.assertThat(getResponse().getString("PickupDay")).matches(DATE_REGEX);
        Assertions.assertThat(getResponse().getString("DropoffDay")).matches(DATE_REGEX);
        Assertions.assertThat(getResponse().getString("PickupAirport")).matches(ANY_SYMBOLS);
    }

    private void verifyHotelSolution() {
        getResponse().setRoot("Hotwire.Result.HotelResult[0]");
        Assertions.assertThat(getResponse().getString("")).matches(ANY_SYMBOLS);
        Assertions.assertThat(getResponse().getString("DeepLink")).matches(".+/hotel/.+");
        Assertions.assertThat(getResponse().getString("SubTotal")).matches(PRICE_REGEX);
        Assertions.assertThat(getResponse().getString("TaxesAndFees")).matches(PRICE_REGEX);
        Assertions.assertThat(getResponse().getString("TotalPrice")).matches(PRICE_REGEX);
        Assertions.assertThat(getResponse().getString("CheckInDate")).matches(DATE_REGEX);
        Assertions.assertThat(getResponse().getString("CheckOutDate")).matches(DATE_REGEX);
        Assertions.assertThat(getResponse().getString("AveragePricePerNight")).matches(PRICE_REGEX);
    }

    private void verifyResponseStatus() {
        getResponse().setRoot("Hotwire");
        Assertions.assertThat(getResponse().getString("StatusDesc")).as("Status check failed!").isEqualTo("success");
        Assertions.assertThat(getResponse().getInt("StatusCode")).isEqualTo(0);
        apiProperties.setResponse(getResponse());
    }

    public void openDeepLink() {
        URI link = checkDeepLink(getResponse().getString("DeepLink"));
        Response deeplinkResponse = given()
                .relaxedHTTPSValidation()
                .accept(ContentType.HTML)
                .when().get(convertToSecureLink(link))
                .then()
                .log().ifError()
                .statusCode(200).extract().response();
        apiProperties.setDeeplinkResponse(deeplinkResponse);
    }

    private String convertToSecureLink(URI link) {
        URIBuilder secureLink = new URIBuilder(link).setScheme("https");
        if (7001 == secureLink.getPort())  {
            secureLink.setPort(7002);
        }
        return String.valueOf(secureLink);
    }

    public void validateDetailsPage() {
        switch (apiProperties.getHotwireProduct()) {
            case CAR:
                assertThat(apiProperties.getDeeplinkResponse().asString(), containsString("tile.car-details"));
                break;
            case HOTEL:
                assertThat(apiProperties.getDeeplinkResponse().asString(), containsString("tile.hotel.details"));
                break;
            default:
                throw new RuntimeException("No such product!");
        }
    }

    /**
     * Calculates date 7 days from today
     *
     * @return date
     */
    private String calculateStartDate() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 7);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = dateFormatter.format(startDate.getTime());
        searchProperties.setPickupDay(formattedDate);
        return formattedDate;
    }

    /**
     * Calculates date 9 days from today
     *
     * @return date
     */
    private String calculateEndDate() {
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE, 9);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = dateFormatter.format(endDate.getTime());
        searchProperties.setDropoffDay(formattedDate);
        return formattedDate;
    }


    private URI checkDeepLink(String url) {
        LOGGER.info("Deeplink from resutls:" + url);
        try {
            assertThat(url, containsString("deeplink-details.jsp"));
            URI link = new URI(url);
            List<NameValuePair> params = URLEncodedUtils.parse(link, "UTF-8");
            Map<String, String> queryParams = new HashMap<>();
            for (NameValuePair param : params) {
                queryParams.put(param.getName(), param.getValue());
            }
            assertThat(queryParams.get("resultId"), notNullValue());
            assertThat(queryParams.get("sid"), notNullValue());
            assertThat(queryParams.get("bid"), notNullValue());
            assertThat(queryParams.get("inputId"), equalTo("api-results"));
            assertThat(queryParams.get("actionType"), equalTo("303"));
            return link;
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("Invalid deeplink!");
        }
    }


    public void storeSearchSolutionDetails() {
        switch (apiProperties.getHotwireProduct()) {
            case CAR:
                getResponse().setRoot("Hotwire.Result.CarResult[0]");
                break;
            case HOTEL:
                getResponse().setRoot("Hotwire.Result.HotelResult[0]");
                break;
            default:
                throw new RuntimeException("No such product!");
        }
        searchProperties.setResultId(getResponse().getString("ResultId"));
        searchProperties.setTotalPrice(getResponse().getString("TotalPrice"));
        searchProperties.setHwRefNumber(getResponse().getString("HWRefNumber"));
    }


    public void requestSolutionsAvailability() {
        String url;
        switch (apiProperties.getHotwireProduct()) {
            case CAR:
                url = searchProperties.getCarDetailsPath();
                break;
            case HOTEL:
                url = searchProperties.getHotelDetailsPath();
                break;
            default:
                throw new RuntimeException("No such product!");
        }
        XmlPath resp = given()
            .accept(ContentType.XML)
            .queryParam("resultid", searchProperties.getResultId())
            .when().get(url)
            .then().log().all().statusCode(200).extract().response().xmlPath();
        apiProperties.setResponse(resp);
        verifyResponseStatus();
    }


    public void verifySolutionsDetails() {
        switch (apiProperties.getHotwireProduct()) {
            case HOTEL:
                getResponse().setRoot("Hotwire.Result.HotelResult[0]");
                Assertions.assertThat(getResponse().getString("")).matches(ANY_SYMBOLS);
                Assertions.assertThat(getResponse().getString("TotalPrice"))
                    .isEqualTo(searchProperties.getTotalPrice());

                break;
            case CAR:
                getResponse().setRoot("Hotwire.Result.CarResult[0]");
                Assertions.assertThat(getResponse().getString("")).matches(ANY_SYMBOLS);
                Assertions.assertThat(getResponse().getString("PickupDay"))
                    .isEqualTo(searchProperties.getPickupDay());
                Assertions.assertThat(getResponse().getString("PickupTime"))
                    .isEqualTo(searchProperties.getPickupTime());
                Assertions.assertThat(getResponse().getString("DropoffDay"))
                    .isEqualTo(searchProperties.getDropoffDay());
                Assertions.assertThat(getResponse().getString("DropoffTime"))
                    .isEqualTo(searchProperties.getDropoffTime());
                break;
            default:
                throw new RuntimeException("No such product!");
        }
    }


    public void bookSolution() {
        XmlPath resp;
        switch (apiProperties.getHotwireProduct()) {
            case HOTEL:
                resp = given().contentType(ContentType.XML)
                        .queryParam("resultid", searchProperties.getResultId())
                        .queryParam("pricecharged", searchProperties.getTotalPrice())
                        .and()
                        .queryParam("firstname", bookingProperties.getFirstName())
                        .queryParam("lastname", bookingProperties.getLastName())
                        .queryParam("email", bookingProperties.getEmail())
                        .queryParam("phone", bookingProperties.getPhone())
                        .and()
                        .queryParam("currencycode", bookingProperties.getCurrencyCode())
                        .and().log().all()
                        .when()
                        .get(bookingProperties.getHotelBookingPath())
                        .then()
                        .statusCode(200)
                        .extract().xmlPath();
                apiProperties.setResponse(resp);
                break;
            default:
                throw new RuntimeException("No such product!");
        }
        apiProperties.setResponse(resp);
        verifyResponseStatus();
    }


    public void verifyConfirmation() {
        switch (apiProperties.getHotwireProduct()) {
            case HOTEL:
                getResponse().setRoot("Hotwire.Result.PurchaseOrder");
                Assertions.assertThat(getResponse().getString("TotalTaxes")).matches(PRICE_REGEX);
                Assertions.assertThat(getResponse().getString("TotalFees")).matches(PRICE_REGEX);
                Assertions.assertThat(getResponse().getString("HWRefNumber"))
                        .isEqualToIgnoringCase(searchProperties.getHwRefNumber());
                Assertions.assertThat(getResponse().getString("TotalPrice"))
                        .isEqualToIgnoringCase(searchProperties.getTotalPrice());
                break;
            default:
                throw new RuntimeException("No such product!");
        }
    }


    public void checkPurchaseStatus() {
        XmlPath resp = given().accept(ContentType.XML)
                .queryParam("hwrefnumber", searchProperties.getHwRefNumber())
                .when()
                .get(bookingProperties.getHotelStatusPath())
                .then()
                .statusCode(200).extract().xmlPath();
        apiProperties.setResponse(resp);
    }


    public void verifyBooking() {
        getResponse().setRoot("Hotwire.Result.PurchaseOrder.BookingStatus");
        Assertions.assertThat(getResponse().getString("StatusCode")).isEqualTo("601");
        Assertions.assertThat(getResponse().getString("StatusDesc")).isEqualTo("purchase was booked successfully");
    }


    public void requestNeighborhoodsInfo() {
        XmlPath resp = given()
            .accept(ContentType.XML)
            .queryParam("dest", searchProperties.getDestinationLocation())
            .when().get(searchProperties.getNeighborhoodPath())
            .then().log().all().statusCode(200).extract().response().xmlPath();
        apiProperties.setResponse(resp);
        verifyResponseStatus();
    }


    public void verifyNeighborhoodsInfo() {
        getResponse().setRoot("Hotwire.Result.Neighborhood[0]");
        Assertions.assertThat(getResponse().getString("Centroid"))
            .matches("(?=.*?\\d.*?,.*?\\d.*)-?\\d{1,3}(\\.\\d{1,16})?\\s*\\,\\s*-?\\d{1,3}(\\.\\d{1,16})?");
        Assertions.assertThat(getResponse().getString("City")).matches("(\\w+ ?)*");
        Assertions.assertThat(getResponse().getString("Country")).matches("\\w{2}");
        Assertions.assertThat(getResponse().getString("Id")).matches("\\d*");
        Assertions.assertThat(getResponse().getString("Name")).matches(".+?");
        Assertions.assertThat(getResponse().getString("Shape")).matches(ANY_SYMBOLS);
        Assertions.assertThat(getResponse().getString("State")).matches("\\w{2}");
    }


    public void requestHotelDeals() {
        XmlPath resp = given()
            .accept(ContentType.XML)
            .queryParam("starrating", searchProperties.getStarRating())
            .when().get(searchProperties.getHotelDealsPath())
            .then().log().all().statusCode(200).extract().response().xmlPath();
        apiProperties.setResponse(resp);
    }


    public void verifyHotelDeals() {
        getResponse().setRoot("Hotwire.Result.HotelDeal");
        Assertions.assertThat(getResponse().getInt("size()")).
            as("No deals found!").isGreaterThan(0);
        Assertions.assertThat(getResponse().getString("Url")).matches(ANY_SYMBOLS);
    }


    public void requestAmenities() {
        XmlPath resp = given()
            .accept(ContentType.XML)
            .when().get(searchProperties.getAmenitiesPath())
            .then().log().all().statusCode(200).extract().response().xmlPath();
        apiProperties.setResponse(resp);
    }


    public void verifyAmenities() {
        Assertions.assertThat(getResponse().getInt("Hotwire.Result.Amenity.size()")).
            as("No amenities found!").isGreaterThan(0);
        getResponse().setRoot("Hotwire.Result.Amenity[0]");
        Assertions.assertThat(getResponse().getString("Code")).matches("\\w{2}");
        Assertions.assertThat(getResponse().getString("Description")).matches(ANY_SYMBOLS);
        Assertions.assertThat(getResponse().getString("Name")).matches(ANY_SYMBOLS);
    }


    public void requestHotelTrt() {
        XmlPath resp = given()
            .accept(ContentType.XML)
            .queryParam("dest", searchProperties.getDestinationLocation())
            .queryParam("limit", 1)
            .when().get(searchProperties.getHotelTrtPath())
            .then().log().all().statusCode(200).extract().response().xmlPath();
        apiProperties.setResponse(resp);
    }


    public void verifyHotelTrt() {
        getResponse().setRoot("Hotwire.Result.HotelPricing");
        Assertions.assertThat(getResponse().getString("")).matches(ANY_SYMBOLS);
        Assertions.assertThat(getResponse().getString("DestinationAirportCode")).matches("\\w{3}");
        Assertions.assertThat(getResponse().getString("DestinationCity")).matches("(\\w+ ?)*");
    }


    public void refundPurchase() {
        XmlPath resp = given().accept(ContentType.XML)
                .queryParam("hwrefnumber", searchProperties.getHwRefNumber())
                .queryParam("refundtype", refundProperties.getRefundType())
                .queryParam("shouldcancel", refundProperties.isShouldCancel())
                .queryParam("refundreason", refundProperties.getRefundReason())
                .queryParam("refundcurrencycode", refundProperties.getRefundCurrencyCode())
                .queryParam("isrebooked", refundProperties.isRebooked())
                .queryParam("comments", refundProperties.getComments())
                .and()
                .queryParam("refundamount", searchProperties.getTotalPrice())
                .when()
                .get(refundProperties.getRefundPath())
                .then().statusCode(200).extract().xmlPath();
        apiProperties.setResponse(resp);
        verifyResponseStatus();
    }


    public void verifyRefund() {
        getResponse().setRoot("Hotwire.Result.PurchaseOrder.BookingStatus");
        Assertions.assertThat(getResponse().getString("StatusDesc"))
                .isEqualTo("purchase was cancelled and refunded successfully");
        Assertions.assertThat(getResponse().getString("StatusCode")).isEqualTo("604");

    }

}
