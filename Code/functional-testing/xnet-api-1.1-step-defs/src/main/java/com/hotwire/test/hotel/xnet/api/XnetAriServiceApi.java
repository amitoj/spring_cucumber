/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet.api;

import static org.fest.assertions.Assertions.assertThat;
import hotwire.xnet.api.AvailRateUpdate;
import hotwire.xnet.api.AvailRateUpdateRQ;
import hotwire.xnet.api.AvailRateUpdateRS;
import hotwire.xnet.api.DateRange;
import hotwire.xnet.api.ErrorInfo;
import hotwire.xnet.api.Hotel;
import hotwire.xnet.api.Inventory;
import hotwire.xnet.api.ObjectFactory;
import hotwire.xnet.api.Rate;
import hotwire.xnet.api.RatePlan;
import hotwire.xnet.api.Restrictions;
import hotwire.xnet.api.RoomType;
import hotwire.xnet.api.XnetApiException;
import hotwire.xnet.api.XnetHotelService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.WebServiceException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hotwire.test.hotel.xnet.XnetAriService;
import com.hotwire.test.hotel.xnet.XnetServiceException;

/**
 * Implements {@link XnetAriService} for xnet api. Uses data gathered from the setter methods to build the request
 * object.
 *
 * @author Renat Zhilkibaev
 */
public class XnetAriServiceApi implements XnetAriService {

    public static final Map<String, String> ERROR_CODES = new HashMap<String, String>() {
        {
            put("0", XnetServiceException.ERROR_CODE_INTERNAL_ERROR);
            put("200", XnetServiceException.ERROR_CODE_INVALID_HOTEL_ID);
            put("301", XnetServiceException.ERROR_CODE_TOTAL_INVENTORY_NOT_IN_RANGE);
            put("700", XnetServiceException.ERROR_CODE_CURRENCY_LENGTH);
            put("701", XnetServiceException.ERROR_CODE_CURRENCY_UNKNOWN);
            put("702", XnetServiceException.ERROR_CODE_PERDAY_RATE_NOT_IN_RANGE);
            put("404", XnetServiceException.ERROR_CODE_END_DATE_NOT_WITHIN_RANGE);
            put("400", XnetServiceException.ERROR_CODE_START_DATE_PAST);
            put("401", XnetServiceException.ERROR_CODE_END_DATE_PAST);
            put("402", XnetServiceException.ERROR_CODE_START_DATE_AFTER_END_DATE);
            put("101", XnetServiceException.ERROR_CODE_DATES_OVERLAP);
            put("502", XnetServiceException.ERROR_CODE_UNKNOWN_ROOMTYPE);

        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(XnetAriServiceApi.class.getSimpleName());
    private static final long APP_MAX_WAIT_MILLIS = 10000; // 10 seconds.
    private static final long INTERVAL_TIME_MILLIS = 500; // .5 seconds.
    private XnetHotelService service;
    private final ObjectFactory objectFactory;
    private final AvailRateUpdateRQ request;

    private String validUserName;
    private String validPassword;
    private String invalidUserName;
    private String invalidPassword;

    private String normalUserName;
    private String normalPassword;
    // This flag checks if there was any web-exceptions
    private boolean success = true;

    @Autowired
    @Qualifier("xnetAuthpolicy")
    private AuthorizationPolicy xnetAuthpolicy;

    @Autowired
    @Qualifier("applicationUrlString")
    private String applicationUrlString;

    public XnetAriServiceApi() {
        request = new AvailRateUpdateRQ();
        objectFactory = new ObjectFactory();
    }

    public void setService(XnetHotelService service) {
        this.service = service;
    }

    @Override
    public void setInvalidSupplier() {
        xnetAuthpolicy.setUserName(invalidUserName);
        xnetAuthpolicy.setPassword(invalidPassword);
    }

    @Override
    public void setValidSupplier(String userType) {
        if (userType.equals("valid")) {
            xnetAuthpolicy.setUserName(validUserName);
            xnetAuthpolicy.setPassword(validPassword);
        }
        else if (userType.equals("normal")) {
            xnetAuthpolicy.setUserName(normalUserName);
            xnetAuthpolicy.setPassword(normalPassword);
        }

    }

    @Override
    public void setXnetServiceHeaders() {
        try {
            XnetApiClientCxfFactory.setXnetUserIdInXnetHotelServiceHttpHeader(service);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isXnetServiceAccessible() throws IOException, TimeoutException {
        long startTime = new Date().getTime();
        boolean streamOpened = false;
        int interval = 0;
        while (new Date().getTime() - startTime <= APP_MAX_WAIT_MILLIS) {
            LOGGER.info("Interval " + interval + ": testing service is accessible.");
            InputStream wsdlStream = null;
            try {
                URL wsdlUrl = new URL(applicationUrlString + "?wsdl");
                wsdlStream = wsdlUrl.openStream();
                wsdlStream.read(new byte[10]);
                streamOpened = true;
            }
            catch (Exception e) {
                LOGGER.info("Xnet service is not accessible. Retrying...\n" + ExceptionUtils.getStackTrace(e));
            }
            finally {
                IOUtils.closeQuietly(wsdlStream);
            }
            // Sleep for specified time.
            if (streamOpened) {
                LOGGER.info("WSDL stream successfully opened.");
                break;
            }
            else {
                LOGGER.info("Failed to open WSDL stream.");
                try {
                    Thread.sleep(INTERVAL_TIME_MILLIS);
                }
                catch (InterruptedException e) {
                    // do nothing.
                }
            }
            interval++;
        }
        if (!streamOpened) {
            throw new TimeoutException("Timeout waiting for xnet application service to be accessible.");
        }
    }

    @Override
    public void updateInventory() throws XnetServiceException {
        try {
            AvailRateUpdateRS rs = service.updateInventory(request);
            if (rs.getSuccess() == null) {
                success = false;
                throw new XnetServiceException("?", "API. No Success object in request.");
            }
        }
        catch (XnetApiException e) {
            success = false;
            ErrorInfo firstError = e.getFaultInfo().getError().get(0);
            String errorCode = ERROR_CODES.containsKey(firstError.getCode()) ?
                ERROR_CODES.get(firstError.getCode()) : "?";
            throw new XnetServiceException(errorCode, "API. " + e.getMessage(), e);
        }
        catch (WebServiceException e) {
            success = false;
            if (e.getCause() instanceof org.apache.cxf.transport.http.HTTPException &&
                ((org.apache.cxf.transport.http.HTTPException) e.getCause()).getResponseCode() == 401) {
                throw new XnetServiceException(XnetServiceException.ERROR_CODE_UNAUTHORIZED, "API. " + e.getMessage(),
                    e);
            }
            else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setHotelId(long hotelId) {
        Hotel hotelInfo = objectFactory.createHotel();
        hotelInfo.setId(hotelId);
        request.setHotel(hotelInfo);
    }

    @Override
    public void addAvailRateUpdate(Date from, Date to, Boolean isSun, Boolean isMon, Boolean isTue, Boolean isWed,
        Boolean isThu, Boolean isFri, Boolean isSat) {
        DateRange dateRange = objectFactory.createDateRange();
        dateRange.setFrom(Utils.dateToXmlGregorianCalendar(from));
        dateRange.setTo(Utils.dateToXmlGregorianCalendar(to));
        dateRange.setSun(isSun);
        dateRange.setMon(isMon);
        dateRange.setTue(isTue);
        dateRange.setWed(isWed);
        dateRange.setThu(isThu);
        dateRange.setFri(isFri);
        dateRange.setSat(isSat);

        // new parent for date range
        AvailRateUpdate availRateUpdate = objectFactory.createAvailRateUpdate();
        availRateUpdate.setDateRange(dateRange);

        List<AvailRateUpdate> availRateUpdateList = request.getAvailRateUpdate();
        availRateUpdateList.add(availRateUpdate);
    }

    @Override
    public void addRoomType(String roomType) {
        RoomType newRoomTypeInfo = objectFactory.createRoomType();
        newRoomTypeInfo.setId(roomType);
        getCurrentAvailRateUpdate().getRoomType().add(newRoomTypeInfo);
    }

    @Override
    public void setTotalInventoryAvailable(int totalInventoryAvailable) {
        Inventory inventory = objectFactory.createInventory();
        inventory.setTotalInventoryAvailable(totalInventoryAvailable);
        getCurrentRoomType().setInventory(inventory);
    }

    @Override
    public void setRatePlan(String ratePlan) {
        RatePlan ratePlanObj = objectFactory.createRatePlan();
        ratePlanObj.setId(ratePlan);
        getCurrentRoomType().setRatePlan(ratePlanObj);
    }

    @Override
    public void setCurrency(String currency) {
        getRate().setCurrency(currency);
    }

    @Override
    public void setPerDayRate(float perDayRate) {
        getRate().setPerDay(perDayRate);
    }

    @Override
    public void setExtraPersonFee(float extraPersonRate) {
        getRate().setExtraPerson(extraPersonRate);
    }

    @Override
    public void setSoldOut(boolean soldOut) {
        getCurrentRoomType().setClosed(soldOut);
    }

    @Override
    public void setClosedToArrival(boolean closedToArrival) {
        getRestrictions().setClosedToArrival(closedToArrival);
    }

    private AvailRateUpdate getCurrentAvailRateUpdate() {
        List<AvailRateUpdate> availRateUpdateList = request.getAvailRateUpdate();
        if (availRateUpdateList.isEmpty()) {
            throw new IllegalStateException("The list of availability updates is empty." +
                " Add at least one availability update first.");
        }
        return availRateUpdateList.get(availRateUpdateList.size() - 1);
    }

    private RoomType getCurrentRoomType() {
        List<RoomType> roomTypeList = getCurrentAvailRateUpdate().getRoomType();
        if (roomTypeList.isEmpty()) {
            throw new IllegalStateException("The list of room types for the current availability update " +
                "is empty. Add at least one room type first.");
        }
        return roomTypeList.get(roomTypeList.size() - 1);
    }

    private RatePlan getRatePlan() {
        RatePlan ratePlan = getCurrentRoomType().getRatePlan();
        if (ratePlan == null) {
            throw new IllegalStateException("No rate plan found. Set rate plan first.");
        }
        return ratePlan;
    }

    private Rate getRate() {
        RatePlan ratePlan = getRatePlan();
        Rate rate = ratePlan.getRate();
        if (rate == null) {
            rate = objectFactory.createRate();
            ratePlan.setRate(rate);
        }
        return rate;
    }

    private Restrictions getRestrictions() {
        RatePlan ratePlan = getRatePlan();
        Restrictions restrictions = ratePlan.getRestrictions();
        if (restrictions == null) {
            restrictions = objectFactory.createRestrictions();
            ratePlan.setRestrictions(restrictions);
        }
        return restrictions;
    }

    public void setValidUserName(String validUserName) {
        this.validUserName = validUserName;
    }

    public void setValidPassword(String validPassword) {
        this.validPassword = validPassword;
    }

    public void setNormalUserName(String normalUserName) {
        this.normalUserName = normalUserName;
    }

    public void setNormalPassword(String normalPassword) {
        this.normalPassword = normalPassword;
    }

    public void setInvalidUserName(String invalidUserName) {
        this.invalidUserName = invalidUserName;
    }

    public void setInvalidPassword(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }

    @Override
    public void verifyUpdateInventory(String successFailure, Exception exception) {
        if ("No".equals(successFailure)) {
            assertThat(success).as("Expected exception to be null").isEqualTo(true);
        }
        else {
            String errorCode = exception instanceof XnetServiceException ? ((XnetServiceException) exception)
                .getErrorCode() : XnetServiceException.ERROR_CODE_UNDEFINED;
            Map<String, String> expectedMessageToErrorCode = new HashMap<String, String>();

            expectedMessageToErrorCode.put("Unknown room type", XnetServiceException.ERROR_CODE_UNKNOWN_ROOMTYPE);
            expectedMessageToErrorCode.put("Invalid hotel id", XnetServiceException.ERROR_CODE_INVALID_HOTEL_ID);
            expectedMessageToErrorCode.put("Internal", XnetServiceException.ERROR_CODE_INTERNAL_ERROR);
            expectedMessageToErrorCode.put("Inventory not in range",
                XnetServiceException.ERROR_CODE_TOTAL_INVENTORY_NOT_IN_RANGE);
            expectedMessageToErrorCode.put("Invalid currency length", XnetServiceException.ERROR_CODE_CURRENCY_LENGTH);
            expectedMessageToErrorCode.put("Unknown currency", XnetServiceException.ERROR_CODE_CURRENCY_UNKNOWN);
            expectedMessageToErrorCode.put("Rate not in range",
                XnetServiceException.ERROR_CODE_PERDAY_RATE_NOT_IN_RANGE);
            expectedMessageToErrorCode.put("End date not within range",
                XnetServiceException.ERROR_CODE_END_DATE_NOT_WITHIN_RANGE);
            expectedMessageToErrorCode.put("Start date in the past", XnetServiceException.ERROR_CODE_START_DATE_PAST);
            expectedMessageToErrorCode.put("End date in the past", XnetServiceException.ERROR_CODE_END_DATE_PAST);
            expectedMessageToErrorCode.put("Dates must not overlap", XnetServiceException.ERROR_CODE_DATES_OVERLAP);
            expectedMessageToErrorCode.put("Start date Not after end date",
                XnetServiceException.ERROR_CODE_START_DATE_AFTER_END_DATE);
            expectedMessageToErrorCode.put("authentication", XnetServiceException.ERROR_CODE_UNAUTHORIZED);

            if (!expectedMessageToErrorCode.get(successFailure).equals(errorCode)) {
                throw new RuntimeException(exception.getMessage(), exception);
            }
        }
    }
}
