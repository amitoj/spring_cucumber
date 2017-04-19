/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * A class to maintain retail hero links with dates in the future.
 *
 * @author ahobbs
 */
public class RetailHeroLinkFactory {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");
    private static final DateFormat INTL_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final int DEFAULT_START_DAY_OFFSET = 7;
    private static final int DEFAULT_END_DAY_OFFSET = 8;
    private static final String EAN_HOTEL_KEY = "ean";
    private static final String EXPEDIA_HOTEL_KEY = "expedia";
    private static final String HOTWIRE_HOTEL_KEY = "hotwire";

    private static final String RETAIL_HERO_URL = "/hotel/details/direct-retail-details?inputId=hotel-index";
    private static final String RETAIL_HERO_MARKETING_PARAMS = "sid=S109&bid=B378901&rpid=RPH432";
    private static final String EAN_HOTEL_ID = "361750";
    private static final String EXPEDIA_HOTEL_ID = "3870088";
    private static final String HOTWIRE_HOTEL_ID = "65583";

    private final HashMap<String, String> retailHeroLinkMap = new HashMap<String, String>();

    // Today's date
    private final Date date = new Date();

    @Autowired
    private Environment env;

    /**
     * A method to return the the proper link based on the hotelType key
     *
     * @param hotelType type of hotel a retail hero link is needed for
     * @return retail hero link for ean/expeida/hotwire hotel id or null
     */
    public String getRetailHeroLink(String hotelType) {
        String startDate = getDateInFuture(DEFAULT_START_DAY_OFFSET);
        String endDate = getDateInFuture(DEFAULT_END_DAY_OFFSET);
        String mapKey = hotelType.toLowerCase();
        return String.format(this.retailHeroLinkMap.get(mapKey), startDate, endDate);
    }

    /**
     * Initialize the retailHeroLinkMap to contain all links for ean/expedia/hotwire hotel IDs and set all dates into
     * the future
     */
    @SuppressWarnings("unused")
    private void init() {

        this.retailHeroLinkMap.put(EAN_HOTEL_KEY, RETAIL_HERO_URL + "&startDate=%s&endDate=%s&selectedHotelEanId=" +
                                   EAN_HOTEL_ID + "&" + RETAIL_HERO_MARKETING_PARAMS);
        this.retailHeroLinkMap.put(EXPEDIA_HOTEL_KEY, RETAIL_HERO_URL +
                                   "&startDate=%s&endDate=%s&selectedExpediaHotelId=" + EXPEDIA_HOTEL_ID + "&" +
                                   RETAIL_HERO_MARKETING_PARAMS);
        this.retailHeroLinkMap.put(HOTWIRE_HOTEL_KEY, RETAIL_HERO_URL + "&startDate=%s&endDate=%s&selectedHotelId=" +
                                   HOTWIRE_HOTEL_ID + "&" + RETAIL_HERO_MARKETING_PARAMS);
    }

    /**
     * Takes current date and adds the offset
     *
     * @param offset number of days in the future from today
     * @return a String representation of a future date using the DATE_FORMAT
     */
    private String getDateInFuture(int offset) {
        return env.getActiveProfiles()[0].contains("RowWebApp") ?
            INTL_DATE_FORMAT.format(DateUtils.addDays(this.date, offset)) :
            DATE_FORMAT.format(DateUtils.addDays(this.date, offset));
    }
}
