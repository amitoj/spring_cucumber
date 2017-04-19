/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet.api;

import hotwire.xnet.api.BookingRetrievalRQ;
import hotwire.xnet.api.ErrorInfo;
import hotwire.xnet.api.Hotel;
import hotwire.xnet.api.ObjectFactory;
import hotwire.xnet.api.RetrieveBookingsRS;
import hotwire.xnet.api.XnetApiException;
import hotwire.xnet.api.XnetHotelService;

import javax.xml.ws.WebServiceException;

import com.hotwire.test.hotel.xnet.XnetBR_Service;
import com.hotwire.test.hotel.xnet.XnetServiceException;

/**
 *
 * @author Scheedhella
 *
 */
public class XnetBR_ServiceImpl implements XnetBR_Service {

    private XnetHotelService service;
    private BookingRetrievalRQ brRequest;
    private ObjectFactory objectFactory;
    private RetrieveBookingsRS rs;

    public XnetBR_ServiceImpl() {
        brRequest = new BookingRetrievalRQ();
        objectFactory = new ObjectFactory();
    }

    /**
     * Retrieve Booking. On success returns with no exceptions.
     *
     * @throws com.hotwire.test.hotel.xnet.XnetServiceException
     *             if any error occurs
     */
    @Override
    public void retrieveBooking() throws XnetServiceException {
        try {
            rs = service.retrieveBooking(brRequest);
        }
        catch (XnetApiException e) {
            ErrorInfo firstError = e.getFaultInfo().getError().get(0);
            if ("0".equals(firstError.getCode())) {
                throw new XnetServiceException(XnetServiceException.ERROR_CODE_INTERNAL_ERROR,
                    "API. " + e.getMessage(), e);
            }
            else if ("200".equals(firstError.getCode())) {
                throw new XnetServiceException(XnetServiceException.ERROR_CODE_INVALID_HOTEL_ID, "API. " +
                    e.getMessage(), e);
            }
            else if ("1000".equals(firstError.getCode())) {
                throw new XnetServiceException(XnetServiceException.ERROR_CODE_UNSUPPORTED_OPERATION, "API. " +
                    e.getMessage(), e);
            }
            else if ("900".equals(firstError.getCode())) {
                throw new XnetServiceException(XnetServiceException.ERROR_CODE_BR_INVALID_MINS_IN_PAST, "API. " +
                    e.getMessage(), e);
            }
            throw new XnetServiceException("?", "API. No response object");
        }
        catch (WebServiceException e) {

            if (e.getCause() instanceof org.apache.cxf.transport.http.HTTPException &&
                    ((org.apache.cxf.transport.http.HTTPException) e.getCause()).getResponseCode() == 401) {
                throw new XnetServiceException(XnetServiceException.ERROR_CODE_UNAUTHORIZED, "API. " + e.getMessage(),
                    e);
            }
        }
    }

    /**
     * Sets hotel id
     *
     * @param hotelId
     *            - the id to set
     */
    @Override
    public void setHotelId(long hotelId) {
        Hotel hotelInfo = objectFactory.createHotel();
        hotelInfo.setId(hotelId);
        brRequest.setHotel(hotelInfo);
    }

    /**
     * Sets minutes in past
     *
     * @param minutes
     */
    @Override
    public void setMinutesInPast(int minutes) {
        brRequest.setMinsInPast(minutes);
    }

    @Override
    public boolean validateBookingResponse() throws XnetServiceException {
        return rs.getBooking().size() == 0 ? true : false;
    }

    @Override
    public boolean validateBookingType(String bookingType) throws XnetServiceException {
        int lastIdx = rs.getBooking().size();
        return rs.getBooking().get(lastIdx - 1).getType().equalsIgnoreCase(bookingType);
    }

    @Override
    public boolean validateCurrencyCode(String currencyCode) throws XnetServiceException {
        int lastIdx = rs.getBooking().size();
        return rs.getBooking().get(lastIdx - 1).getRoomStay().getPerDayRates().getCurrency()
            .equalsIgnoreCase(currencyCode);
    }

    @Override
    public boolean validateNumberOfAdults(int numAdults) throws XnetServiceException {
        int lastIdx = rs.getBooking().size();
        return rs.getBooking().get(lastIdx - 1).getRoomStay().getGuestCount().getAdult() == numAdults ? true : false;
    }

    @Override
    public boolean validateNumberOfChildren(int numChildren) throws XnetServiceException {
        int lastIdx = rs.getBooking().size();
        return rs.getBooking().get(lastIdx - 1).getRoomStay().getGuestCount().getChild() == numChildren ? true : false;
    }

    @Override
    public boolean validateRoomStayId(String roomStayId) throws XnetServiceException {
        int lastIdx = rs.getBooking().size();
        return rs.getBooking().get(lastIdx - 1).getRoomStay().getRoomTypeID().equalsIgnoreCase(roomStayId);
    }

    @Override
    public boolean validateRoomRatePlanId(String roomRatePlanId) throws XnetServiceException {
        int lastIdx = rs.getBooking().size();
        return rs.getBooking().get(lastIdx - 1).getRoomStay().getRatePlanID().equalsIgnoreCase(roomRatePlanId);
    }

    @Override
    public boolean validateNumberOfRooms(int numOfRooms) throws XnetServiceException {
        int lastIdx = rs.getBooking().size();
        return rs.getBooking().get(lastIdx - 1).getRoomStay().getNumberOfRooms() == numOfRooms ? true : false;

    }

    @Override
    public boolean validateTotalAmountCurrencyCode(String currencyCode) throws XnetServiceException {
        int lastIdx = rs.getBooking().size();
        return rs.getBooking().get(lastIdx - 1).getRoomStay().getTotalAmount().getCurrency()
            .equalsIgnoreCase(currencyCode);
    }

    @Override
    public boolean validateNumberOfNights(int numOfNights) throws XnetServiceException {
        int lastIdx = rs.getBooking().size();
        return rs.getBooking().get(lastIdx - 1).getRoomStay().getPerDayRates().getPerDayRate().size() == numOfNights;
    }

    public void setService(XnetHotelService service) {
        this.service = service;
    }

}
