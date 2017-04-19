/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.coupons;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import com.hotwire.test.steps.search.SearchResponseHolder;
import com.jayway.jsonpath.JsonPath;
import hotwire.biz.order.discount.DiscountJdo;
import hotwire.eis.dao.HwGenericDao;
import hotwire.eis.db.DatabaseCodes;
import hotwire.view.jaxb.domain.mobileapi.ClientInfoType;
import hotwire.view.jaxb.domain.mobileapi.Name;
import hotwire.view.jaxb.domain.mobileapi.PersonInfo;
import hotwire.view.jaxb.domain.mobileapi.SearchResult;
import hotwire.view.jaxb.domain.mobileapi.SearchResultReference;
import hotwire.view.jaxb.domain.mobileapi.SummaryOfCharges;
import hotwire.view.jaxb.domain.mobileapi.coupon.CouponDetails;
import hotwire.view.jaxb.domain.mobileapi.coupon.CouponValidationRQ;
import hotwire.view.jaxb.domain.mobileapi.coupon.CouponValidationRS;
import hotwire.view.jaxb.domain.mobileapi.coupon.TripCharges;
import hotwire.view.jaxb.domain.mobileapi.coupon.car.CarCouponValidationRQ;
import hotwire.view.jaxb.domain.mobileapi.coupon.hotel.HotelCouponValidationRQ;
import hotwire.view.jaxb.domain.mobileapi.search.SearchSolution;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarSearchSolution;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.OpaqueHotelSearchSolution;
import org.fest.assertions.Delta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;


/**
 * User: v-dsobko
 * Since: 01/20/15
 */
public class CouponCodesService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponCodesService.class.getName());

    private CouponValidationRS couponValidationResponse;

    private HwGenericDao hwGenericDaoProxy;
    private RequestProperties requestProperties;
    private RequestPaths paths;
    private SearchResponseHolder searchResponseHolder;

    public void setHwGenericDaoProxy(HwGenericDao hwGenericDaoProxy) {
        this.hwGenericDaoProxy = hwGenericDaoProxy;
    }

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setSearchResponseHolder(SearchResponseHolder searchResponseHolder) {
        this.searchResponseHolder = searchResponseHolder;
    }

    /**
     * Type of coupon
     */
    public enum SampleType {
        VALID, EXPIRED, INVALID
    }

    /**
     * Countries for coupons
     */
    public enum CountryCodes {
        USA(1),
        UK(2);
        private int code;

        private CountryCodes(int code) {
            this.code = code;
        }

        private int getCode() {
            return code;
        }

        public static CountryCodes getCodeByName(String name) {
            for (CountryCodes value : values()) {
                if (value.name().equals(name)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("No mapping for this country " + name);
        }

        public static int getCountryCode(CountryCodes country) {
            for (CountryCodes value : values()) {
                if (value == country) {
                    return value.getCode();
                }
            }
            throw new IllegalArgumentException("No mapping for this country " + country);
        }
    }



    private List<OpaqueHotelSearchSolution> getHotelSearchSolutions() {
        return new ArrayList<>(Collections2.transform(searchResponseHolder.getResponse().getSearchResults()
                .getSolution(), new Function<SearchSolution, OpaqueHotelSearchSolution>() {
                    @Nullable
                    @Override
                    public OpaqueHotelSearchSolution apply(@Nullable SearchSolution input) {
                        return (OpaqueHotelSearchSolution) input;
                    }
                }));
    }

    private List<CarSearchSolution> getCarSearchSolutions() {
        return new ArrayList<>(Collections2.transform(searchResponseHolder.getResponse().getSearchResults()
                .getSolution(), new Function<SearchSolution, CarSearchSolution>() {
                    @Nullable
                    @Override
                    public CarSearchSolution apply(@Nullable SearchSolution input) {
                        return (CarSearchSolution) input;
                    }
                }));
    }

    private void extractResultIdForHotelCouponValidation(HotelCouponValidationRQ hotelCouponValidationRQ, int index) {
        String resultId =
                getHotelSearchSolutions().get(index).getResultID();
        String refNumber =
                getHotelSearchSolutions().get(index).getHwRefNumber();
        SearchResultReference searchResultReference = new SearchResultReference();
        searchResultReference.setSearchResult(new SearchResult());
        searchResultReference.getSearchResult().setProductVertical("Hotel");
        searchResultReference.getSearchResult().setResultID(resultId);
        searchResultReference.getSearchResult().setParentHWRefNumber(refNumber);
        hotelCouponValidationRQ.setSearchResultReference(searchResultReference);
    }

    private void extractResultIdForCarCouponValidation(CarCouponValidationRQ carCouponValidationRQ) {
        for (SearchSolution searchSolution : getCarSearchSolutions()) {
            CarSearchSolution carSearchSolution = (CarSearchSolution) searchSolution;
            if (carSearchSolution.getOpacityCode().equals(String.valueOf(DatabaseCodes.OPACITY_CODE_OPAQUE))) {
                String resultId = carSearchSolution.getResultID();
                SearchResultReference searchResultReference = new SearchResultReference();
                searchResultReference.setSearchResult(new SearchResult());
                searchResultReference.getSearchResult().setProductVertical("Car");
                searchResultReference.getSearchResult().setResultID(resultId);
                carCouponValidationRQ.setSearchResultReference(searchResultReference);
                return;
            }
        }
    }

    private HotelCouponValidationRQ createHotelCouponValidationRequest() {
        UserInformation userInfo = requestProperties.getUserInformation();
        HotelCouponValidationRQ hotelCouponValidationRQ = new HotelCouponValidationRQ();
        extractResultIdForHotelCouponValidation(hotelCouponValidationRQ,
                requestProperties.getResultIdIndexForCouponValidation());
        addClientInfoToCouponValidationRequest(hotelCouponValidationRQ);

        HotelCouponValidationRQ.HotelGuests hotelGuests = new HotelCouponValidationRQ.HotelGuests();
        hotelCouponValidationRQ.setHotelGuests(hotelGuests);

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(new Name());
        personInfo.getName().setFirstName(userInfo.getFirstName());
        personInfo.getName().setLastName(userInfo.getLastName());
        personInfo.setPrimary(true);
        personInfo.setPhoneNumber(userInfo.getPrimaryPhoneNumber());
        personInfo.setEmailAddress(userInfo.getEmailId());
        hotelCouponValidationRQ.getHotelGuests().getGuestInfo().add(personInfo);

        return hotelCouponValidationRQ;
    }



    private CarCouponValidationRQ createCarCouponValidationRequest() {
        UserInformation userInfo = requestProperties.getUserInformation();
        CarCouponValidationRQ carCouponValidationRQ = new CarCouponValidationRQ();
        extractResultIdForCarCouponValidation(carCouponValidationRQ);
        addClientInfoToCouponValidationRequest(carCouponValidationRQ);

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(new Name());
        personInfo.getName().setFirstName(userInfo.getDriverFirstName());
        personInfo.getName().setLastName(userInfo.getDriverLastName());
        personInfo.setPrimary(true);
        personInfo.setPhoneNumber(userInfo.getDriverPhoneNumber());
        personInfo.setEmailAddress(userInfo.getDriverEmailAddress());
        personInfo.setAgeUnder25(false);
        carCouponValidationRQ.getDriverInfo().add(personInfo);

        return carCouponValidationRQ;
    }

    private CouponValidationRQ addClientInfoToCouponValidationRequest(CouponValidationRQ couponValidationRQ) {
        couponValidationRQ.setClientInfo(new ClientInfoType());
        couponValidationRQ.getClientInfo().setClientID(requestProperties.getClientId());
        couponValidationRQ.getClientInfo().setCustomerID(requestProperties.getCustomerId());
        couponValidationRQ.getClientInfo().setLatLong(requestProperties.getLatLong());
        couponValidationRQ.getClientInfo().setCountryCode(requestProperties.getCountryCode());
        return couponValidationRQ;
    }

    public void useHotelCouponForValidation(String couponCode) {
        HotelCouponValidationRQ hotelCouponValidationRQ = createHotelCouponValidationRequest();
        hotelCouponValidationRQ.setCouponCode(couponCode);

        try {
            client.reset()
                    .path(paths.getHotelApplyCouponPath())
                    .header("User-Agent", "SomeMobileClient")
                    .accept(MediaType.APPLICATION_XML_TYPE);
            couponValidationResponse = client.post(hotelCouponValidationRQ, CouponValidationRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Coupon validation response returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Coupon validation response has failed with " + message, e);
        }
    }

    public void useCarCouponForValidation(String couponCode) {
        CarCouponValidationRQ carCouponValidationRQ = createCarCouponValidationRequest();
        carCouponValidationRQ.setCouponCode(couponCode);

        try {
            client.reset()
                    .path(paths.getCarApplyCouponPath())
                    .header("User-Agent", "SomeMobileClient")
                    .accept(MediaType.APPLICATION_XML_TYPE);
            couponValidationResponse = client.post(carCouponValidationRQ, CouponValidationRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Coupon validation response returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Coupon validation response has failed with " +  message, e);
        }
    }

    public void executeHotelRequestInvalidCoupon(String couponCode, Integer errorCode) {
        HotelCouponValidationRQ hotelCouponValidationRQ = createHotelCouponValidationRequest();
        hotelCouponValidationRQ.setCouponCode(couponCode);

        try {
            client.reset()
                    .path(paths.getHotelApplyCouponPath())
                    .header("User-Agent", "SomeMobileClient")
                    .accept(MediaType.APPLICATION_JSON_TYPE);
            couponValidationResponse = client.post(hotelCouponValidationRQ, CouponValidationRS.class);
        }
        catch (InternalServerErrorException e) {
            checkErrorResponse(e, errorCode);
        }
    }

    public void executeCarRequestInvalidCoupon(String couponCode, Integer errorCode) {
        CarCouponValidationRQ carCouponValidationRQ = createCarCouponValidationRequest();
        carCouponValidationRQ.setCouponCode(couponCode);

        try {
            client.reset()
                    .path(paths.getCarApplyCouponPath())
                    .header("User-Agent", "SomeMobileClient")
                    .accept(MediaType.APPLICATION_JSON_TYPE);
            couponValidationResponse = client.post(carCouponValidationRQ, CouponValidationRS.class);
        }
        catch (InternalServerErrorException e) {
            checkErrorResponse(e, errorCode);
        }
    }


    public String createDiscount(SampleType sampleType, DiscountJdo discount, String couponName) {

        if (sampleType == SampleType.INVALID) {
            return "BOGUS_DISCOUNT";
        }

        // Insert it into the cache
        LOGGER.info("Before HwGenericDao.insert(DiscountJdo{pgoodCode={}, hash={}, code={}})",
                    discount.getPGoodCode(), discount.getEncryptedDiscountHash(), discount.getCode());
        hwGenericDaoProxy.insert(discount);
        LOGGER.info("After HwGenericDao.insert(DiscountJdo{pgoodCode={}, hash={}, code={}})",
                    discount.getPGoodCode(), discount.getEncryptedDiscountHash(), discount.getCode());
        DiscountJdo discountFromCache = hwGenericDaoProxy.findByKey(DiscountJdo.class, "coupon", couponName);
        LOGGER.info("Discount from cache " + discountFromCache.getCoupon());

        return discountFromCache.getCoupon();
    }

    private TripCharges getTripCharges() {
        return couponValidationResponse.getTripCharges();
    }

    private CouponDetails getCouponDetails() {
        return couponValidationResponse.getCouponDetails();
    }

    private SummaryOfCharges getSummaryOfCharges() {
        return couponValidationResponse.getSummaryOfCharges();
    }

    public void verifyThatResponseIsNull() {
        assertThat(couponValidationResponse).isNull();
    }

    private void checkErrorResponse(InternalServerErrorException e, Integer errorCode) {
        e.getResponse().bufferEntity();
        String exceptionStack = e.getResponse().getEntity().toString();
        Integer err = JsonPath.read(exceptionStack, "$.couponValidationRS.errors.error[0].errorCode");
        assertThat(err).isEqualTo(errorCode);
    }

    public void verifyCouponValidationResponse() {
        assertThat(couponValidationResponse.getErrors()).isNull();
        assertThat(couponValidationResponse.getCouponDetails()).isNotNull();
    }

    public void verifyHotelTotalPriceHasChanged(int solutionIndex) {
        float priceFromSearch = getHotelSearchSolutions().get(solutionIndex).getTripTotal();
        float priceFromValidation =  couponValidationResponse.getTripCharges().getTripTotal();
        float couponAmount =  couponValidationResponse.getTripCharges().getCouponAmountApplied();
        float delta = 0.5f;

        assertThat(priceFromValidation + couponAmount).isEqualTo(priceFromSearch, Delta.delta(delta));
    }

    public void verifyCarTotalPriceHasChanged() {
        float priceFromSearch = 0f;
        for (SearchSolution searchSolution : getCarSearchSolutions()) {
            CarSearchSolution carSearchSolution = (CarSearchSolution) searchSolution;
            if (carSearchSolution.getOpacityCode().equals(String.valueOf(DatabaseCodes.OPACITY_CODE_OPAQUE))) {
                priceFromSearch = getCarSearchSolutions()
                        .get(getCarSearchSolutions().indexOf(carSearchSolution)).getTripTotal();
                break;
            }
        }
        float priceFromValidation = couponValidationResponse.getTripCharges().getTripTotal();
        float couponAmount = couponValidationResponse.getTripCharges().getCouponAmountApplied();
        float delta = 0.5f;

        assertThat(priceFromValidation + couponAmount).isEqualTo(priceFromSearch, Delta.delta(delta));
    }

    public void verifyTripCharges() {
        assertThat(getTripCharges().getCouponAmountApplied()).isNotNull();
        assertThat(getTripCharges().getTripTotal()).isNotNull();
    }

    public void verifyCouponDetails() {
        assertThat(getCouponDetails().getExpiryDate()).isNotNull();
        assertThat(getCouponDetails().getCouponCurrency()).isNotNull();
        assertThat(getCouponDetails().getCouponType()).isNotNull();
        assertThat(getCouponDetails().getCouponValue()).isNotNull();
        assertThat(getCouponDetails().getMaxDTD()).isNotNull();
        assertThat(getCouponDetails().getMinAmount()).isNotNull();
        assertThat(getCouponDetails().getMinStarRating()).isNotNull();
    }

    public void verifySummaryOfCharges() {
        assertThat(getSummaryOfCharges().getTotal()).isNotNull();
        assertThat(getSummaryOfCharges().getSubTotal()).isNotNull();
    }

    public void verifyCouponCurrency(String currency) {
        assertThat(getCouponDetails().getCouponCurrency()).isEqualTo(currency);
    }

}
