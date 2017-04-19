/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel;

import com.hotwire.testing.MethodInvocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mghosh
 */
public class HotelSearchPurchase {

    private static final String CRS_INPUT_SEPERATOR = "~";
    private static final String CRS_SUB_INPUT_SEPERATOR = ",";
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelSearchPurchase.class);

    /**
     * Remote service that this test uses to invoke methods
     */
    private final List<MethodInvocationService> remoteServiceInterfaces;

    public HotelSearchPurchase(List<MethodInvocationService> remoteServiceInterfaces) {
        this.remoteServiceInterfaces = remoteServiceInterfaces;
    }

    public void injectCRS_Rate(String crsTypes, String hotelIds, String rates) {

        if (crsTypes == null || hotelIds == null || rates == null) {
            throw new IllegalArgumentException("CRS Type Input or CRS HotelId or rates input is empty");
        }

        String[] crsTypesArray = crsTypes.split(CRS_INPUT_SEPERATOR);
        String[] hotelIdsArray = hotelIds.split(CRS_INPUT_SEPERATOR);
        String[] ratesArray = rates.split(CRS_INPUT_SEPERATOR);

        if (!areSameLength(crsTypesArray, hotelIdsArray, ratesArray)) {
            throw new IllegalArgumentException("CRS Type Input and CRS HotelId input mismatch");
        }

        Map<Character, Map<String, Float>> crsToHotel = new HashMap<Character, Map<String, Float>>();

        for (int i = 0; i < crsTypesArray.length; i++) {
            Map<String, Float> hotelIDToRate = new HashMap<String, Float>();
            String[] crsHotelId = hotelIdsArray[i].split(CRS_SUB_INPUT_SEPERATOR);
            String[] crsRates = ratesArray[i].split(CRS_SUB_INPUT_SEPERATOR);

            for (int j = 0; j < crsHotelId.length; j++) {
                if (crsRates.length == 1) {
                    hotelIDToRate.put(crsHotelId[j].trim(), Float.parseFloat(crsRates[0].trim()));
                }
                else {
                    hotelIDToRate.put(crsHotelId[j].trim(), Float.parseFloat(crsRates[j].trim()));
                }
            }

            crsToHotel.put(crsTypesArray[i].trim().charAt(0), hotelIDToRate);
            LOGGER.info("Rate Injection (Crs={hotel_prop_id=rate}}) : " + crsToHotel.toString());
        }

        for (MethodInvocationService remoteServiceInterface : remoteServiceInterfaces) {
            LOGGER.info("Remote Url Injection Server: " + remoteServiceInterface.toString());
            remoteServiceInterface.invokeBeanMethod("hotelRateManipulator", "injectCRS_Rate", crsToHotel);
        }


    }

    private boolean areSameLength(Object[]... arrays) {

        int len = arrays[0].length;
        if (len < 1) {
            return false;
        }

        for (Object[] objects : arrays) {
            if (objects.length != len) {
                return false;
            }
        }
        return true;
    }

    public void removeCRS_Rate(String crsTypes, String crsHotelIds) {

        if (crsTypes == null || crsHotelIds == null) {
            throw new IllegalArgumentException("CRS Type Input or CRS HotelId input is empty");
        }
        String[] crsTypeArray = crsTypes.split(CRS_INPUT_SEPERATOR);
        String[] crsHotelIdArray = crsHotelIds.split(CRS_INPUT_SEPERATOR);

        if (!areSameLength(crsTypeArray, crsHotelIdArray)) {
            throw new IllegalArgumentException("CRS Type Input and CRS HotelId input mismatch");
        }

        HashMap<Character, ArrayList<String>> removeCrsType2CrsHotelIdMap = new HashMap<Character, ArrayList<String>>();

        for (int i = 0; i < crsTypeArray.length; i++) {

            String[] hotelIdArray = crsHotelIdArray[i].split(CRS_SUB_INPUT_SEPERATOR);
            ArrayList<String> hotelIdList = new ArrayList<String>();
            for (String hotelId : hotelIdArray) {
                hotelIdList.add(hotelId.trim());
            }
            removeCrsType2CrsHotelIdMap.put(crsTypeArray[i].trim().charAt(0), hotelIdList);
            LOGGER.info("Removing Rates (Crs={hotel_prop_id}}) : " + removeCrsType2CrsHotelIdMap.toString());
        }

        for (MethodInvocationService remoteServiceInterface : remoteServiceInterfaces) {
            LOGGER.info("Remote Url Injection Server: " + remoteServiceInterface.toString());
            remoteServiceInterface.invokeBeanMethod("hotelRateManipulator",
                    "removeCRS_Rate", removeCrsType2CrsHotelIdMap);
        }

    }

}
