/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.deals;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Data object for OPM test links.
 */
public class DealLinkFactory {
    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yy");
    private static final int DEFAULT_DAYS_TO_ADD = 3;
    @SuppressWarnings("unused")
    private static final int DEFAULT_DAYS_TO_ADD_FOR_START_DATE = 3;
    @SuppressWarnings("unused")
    private static final int DEFAULT_DAYS_TO_ADD_FOR_END_DATE = 5;

    private HashMap<String, HashMap<String, String>> linkTypeToUrlMap;
    private HashMap<String, String> providerUrlMapHasInventory;
    private HashMap<String, String> providerUrlMapNoInventory;
    private HashMap<String, String> vertical_type2marketingUrlMap;
    private HashMap<String, String> launchsearchUrlMap;
    private final Date date;

    public DealLinkFactory() {
        date = new Date();
        initMap();
    }

    private void initMap() {
        providerUrlMapHasInventory = new HashMap<>();
        providerUrlMapNoInventory = new HashMap<>();
        vertical_type2marketingUrlMap = new HashMap<>();
        launchsearchUrlMap = new HashMap<>();

        providerUrlMapHasInventory.put(
            "expedia",
            "/hotel/details/direct-retail-details?inputId=hotel-index&startDate=%s&endDate=%s&" +
            "selectedExpediaHotelId=3870088");
        providerUrlMapHasInventory.put(
            "hotwire",
            "/hotel/details/direct-retail-details?inputId=hotel-index&startDate=%s&endDate=%s&selectedHotelId=65583");
        providerUrlMapHasInventory.put(
            "ean",
            "/hotel/details/direct-retail-details?inputId=hotel-index&startDate=%s&endDate=%s&" +
            "selectedHotelEanId=361750");

        providerUrlMapNoInventory.put(
            "expedia",
            "/hotel/details/direct-retail-details?inputId=hotel-index&startDate=%s&endDate=%s&" +
            "selectedExpediaHotelId=14163");
        providerUrlMapNoInventory.put(
            "hotwire",
            "/hotel/details/direct-retail-details?inputId=hotel-index&startDate=%s&endDate=%s&selectedHotelId=20550");
        providerUrlMapNoInventory.put(
            "ean",
            "/hotel/details/direct-retail-details?inputId=hotel-index&startDate=%s&endDate=%s&" +
            "selectedHotelEanId=-99233402");

        // Marketing links for hotel
        vertical_type2marketingUrlMap.put(
            "hotel_dbm_sandbox",
            "/hotel/dbm-link.jsp?inputId=hotel-index&nid=N-HFA-381&vid=V-HFA-381-V1SS-T1&did=D01010604&" +
            "cid=179995ee4177584654705763304be4b5&encDealHash=MTAwOjE0NjU3Ojg0NTAwOjUuMDoyMzMuMDpZOlk6WQ==&" +
            "rid=r-144206721985&xid=x-103&wid=w-3&rs=20500&r=Y&" +
            "startDate=" + getDateInFuture(12) +
            "&endDate=" + getDateInFuture(19));
        vertical_type2marketingUrlMap.put(
            "hotel_dbm",
            "/hotel/dbm-link.jsp?inputId=hotel-index&nid=N-HFA-380&" +
            "vid=V-HFA-380-V1SS-T1&did=D01010101&" +
            "encDealHash=MTAwOjIwNDE0Ojg3NjQzOjQuMDo2Mi4wMDAwMDg6WTpZOlk-&rid=r-143334508260&" +
            "xid=x-103&wid=w-3&rs=20500&r=Y");
        vertical_type2marketingUrlMap.put(
            "hotel_superPage",
            "/hotel/superPage.jsp?inputId=hotel-index&destCity=Burbank&nid=N-OTH-263&vid=V-OTH-263-V12&" +
            "did=D0173&cid=334950309da944fdfbec37baa9156178&" +
            "encDealHash=MTAwOjE3MzAyNjo4OTcxOTo0LjA6NDkuMDAwMDA4Olk6WTpZ&rid=r-166742600126&xid=x-103&" +
            "wid=w-3&rs=20501&r=Y");
        vertical_type2marketingUrlMap.put(
            "hotel_search_option",
            "/hotel/search-options.jsp?inputId=hotel-index&destCity=San Leandro, CA&" +
            "startDay=5" +
            "&startMonth=" + getMonthOfYearInFuture() +
            "&endDay=8" +
            "&endMonth=" + getMonthOfYearInFuture() +
            "&xid=x-103&rid=r-149316101445&wid=w-3&rs=20501&referringDealId=149316101445");
        //Hotel marketing link with startDate & endDate parameters
        vertical_type2marketingUrlMap.put(
            "hotel_cross_sell",
            "/hotel/search-options.jsp?inputId=hotel-index&destCity=San Leandro, CA&" +
            "startDate=" + getDateInFuture(1) +
            "&endDate=" + getDateInFuture(3) +
            "&xid=x-103&rid=r-149316101445&wid=w-3&rs=20501&referringDealId=149316101445");
        //International hotel marketing links corresponding to super pages
        vertical_type2marketingUrlMap.put(
            "hotel_intl_superPage",
            "/hotel/launchsearch?nid=N-INT-OTH-19&vid=V-INT-OTH-19-V1&fid=F2&did=D0131" +
            "&cid=93a42da804e4bd3c4cc0cac134f00585&naeDealHash=MTAwOjMwMDI4Ojg5NjMyOjQuMDo1MzpZOlk6WQ**&rid=r-0" +
            "&xid=x-103&wid=w-3&rs=20501&utm_source=email&utm_campaign=INT_OTH&r=Y");

        // Marketing links for car
        vertical_type2marketingUrlMap.put(
            "car_dbm",
            "/car/dbm-link.jsp?nid=N-HFA-380&vid=V-HFA-380-V1SS-T1&" +
            "cid=8020b975407376082cf650177bfe7715&rid=r-143225133015&xid=x-112&wid=w-3&rs=20500&" +
            "did=D02011003&encDealHash=MTA1OjE5NjYxOkE6RkNBUjozMi45NTpZOk46Tg--&r=Y");
        vertical_type2marketingUrlMap.put(
            "car_superPage",
            "/car/superPage.jsp?inputId=car-index&startLocation=SFO&nid=N-OTH-262&vid=V-OTH-262-V2&did=D0172" +
            "&cid=993c8025423617c0ade37a1a68a9d40e&encDealHash=MTA1OjE0NjU3OkE6SUNBUjoxNi45NTpZOk46Tg--&" +
            "rid=r-149317206409&xid=x-103&wid=w-3&rs=20501&r=Y");
        vertical_type2marketingUrlMap.put(
            "car_search_option",
            "/car/search-options.jsp?inputId=car-index&startLocation=LAS&userInputType=1&" +
            "startDay=5" +
            "&startMonth=" + getMonthOfYearInFuture() +
            "&endDay=8" +
            "&endMonth=" + getMonthOfYearInFuture() +
            "&xid=x-112&rid=r-149317206409&wid=w-3&rs=20501&referringDealId=149317206409");
        //Car marketing link with startDate & endDate parameters
        vertical_type2marketingUrlMap.put(
            "car_cross_sell",
            "/car/search-options.jsp?inputId=car-index&startLocation=LAS&userInputType=1&" +
            "startDate=" + getDateInFuture(1) +
            "&endDate=" + getDateInFuture(3) +
            "&xid=x-112&rid=r-149317206409&wid=w-3&rs=20501&referringDealId=149317206409");


        // Marketing links for air
        vertical_type2marketingUrlMap.put(
            "air_search_option",
            "/air/search-options.jsp?sid=S320&bid=B296413&inputId=index&&origCity=SFO&destinationCity=ORD&" +
            "startDay=5" +
            "&startMonth=" + getMonthOfYearInFuture() +
            "&endDay=8" +
            "&endMonth=" + getMonthOfYearInFuture() +
            "&noOfTickets=2&sid=S320&bid=B311111");
        //Air marketing link with startDate & endDate parameters
        vertical_type2marketingUrlMap.put(
            "air_cross_sell",
            "/air/search-options.jsp?sid=S320&bid=B296413&inputId=index&&origCity=SFO&destinationCity=ORD&" +
            "startDate=" + getDateInFuture(1) +
            "&endDate=" + getDateInFuture(3) +
            "&noOfTickets=2&sid=S320&bid=B311111");


        linkTypeToUrlMap = new HashMap<>();
        linkTypeToUrlMap.put("inventory", providerUrlMapHasInventory);
        linkTypeToUrlMap.put("no inventory", providerUrlMapNoInventory);

        launchsearchUrlMap.put(
            "hotel",
            "/hotel/launchsearch?&naeDealHash=MTAwOjMwMTA1Ojk2NzkyOjQuMDo3Ny4wOlk6WTpZ&" +
            "startDate=" + getDateInFuture(3) +
            "&endDate=" + getDateInFuture(5) +
            "&nid=N-HFA-520&vid=V-HFA-520-V1SS-T1&did=D1488");
        launchsearchUrlMap.put(
            "angular hotel",
            "/hotel/launchsearch?&naeDealHash=MTAwOjMwMTA1Ojk2NzkyOjQuMDo3Ny4wOlk6WTpZ&" +
            "startDate=" + getDateInFuture(3) +
            "&endDate=" + getDateInFuture(5) +
            "&nid=N-HFA-520&vid=V-HFA-520-V1SS-T1&did=D1488&vt.AWA14=2&vt.ALS14=2");
    }

    /**
     * TODO: clean up notes from Rex: this looks like it should be available from a step "Given a deal with a 3 day
     * range"
     */
    public String getLinkUrl(String id, String provider) {
        String startDate = DATE_FORMATTER.format(DateUtils.addDays(date, DEFAULT_DAYS_TO_ADD));
        String endDate = DATE_FORMATTER.format(DateUtils.addDays(date, DEFAULT_DAYS_TO_ADD * 2));
        return String.format(linkTypeToUrlMap.get(id).get(provider), startDate, endDate);
    }

    private String getMonthOfYearInFuture() {
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.MONTH, 1);
        return Integer.toString(futureDate.get(Calendar.MONTH) + 1); // Add one because Jan is month 0
    }

    /**
     * Function adds number of days to current date
     */
    private String getDateInFuture(int numOfDays) {
        return DATE_FORMATTER.format(DateUtils.addDays(date, DEFAULT_DAYS_TO_ADD * numOfDays));
    }

    public String getMarketingLinkUrl(String vertical, String marketingCateogory) {
        return vertical_type2marketingUrlMap.get(vertical + "_" + marketingCateogory.replaceAll(" ", "_"));
    }

    public String getLaunchSearchUrl(String key) {
        return launchsearchUrlMap.get(key);
    }
}
