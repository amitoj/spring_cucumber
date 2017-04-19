/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.car;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Contains different location data for car searches.
 */
final class CarSearchLocationMap {

    private static List<String> LOCATIONS = new ArrayList<String>() {
        {
            add("ADDRESS");
            add("AIRPORT");
            add("CITY");
            add("POSTAL");
        }
    };
    private static List<String> ADDRESS = new ArrayList<String>() {
        {
            add("1201 Mason St, San Francisco, CA 94108");
            add("3601 Lyon St San Francisco, CA");
            add("2001 Pan American Plaza San Diego");
            add("5700 South Lake Shore Drive Chicago");
            add("2001 N Clark St Chicago");
            add("155 West Hampton Avenue Mesa");
            add("5401 Woodward Avenue");
            add("333 S Valley View Blvd");
            add("1401 North Rainbow Boulevard");
        }
    };
    private static List<String> AIRPORT = new ArrayList<String>() {
        {
            add("San Francisco, CA - (SFO)");
            add("Baltimore, MD - (BWI)");
            add("Tucson, AZ - (TUS)");
            add("Cleveland, OH - (CLE)");
            add("Denver, CO - (DEN)");
            add("Las Vegas, NV - (LAS)");
            add("Columbia, SC - (CAE)");
            add("San Diego, CA - (SAN)");
            add("San Antonio, TX - (SAT)");
            add("Boston, MA - (BOS)");
            add("Memphis, TN - (MEM)");
        }
    };
    private static List<String> CITY = new ArrayList<String>() {
        {
            add("Sacramento, CA");
            add("Las Vegas, NV");
            add("Santa Barbara, CA");
            add("Orlando, FL");
            add("Concord, CA");
            add("Sanford, FL ");
            add("Chicago, IL");
            add("Houston, TX");
            add("Phoenix, AZ");
            add("San Antonio, TX");
            add("San Diego, CA");
            add("Dallas, TX");
            add("Detroit, MI");
        }
    };
    private static List<String> POSTAL = new ArrayList<String>() {
        {
            add("11238");
            add("11201");
            add("94605");
            add("94612");
            add("23223");
            add("55403");
            add("97204");
            add("60612");
            add("30315");
        }
    };

    private CarSearchLocationMap() {
        // This class should not be instantiated.
    }

    private static String getAirport() {
        return AIRPORT.get(new Random().nextInt(AIRPORT.size()));
    }

    private static String getCity() {
        return CITY.get(new Random().nextInt(CITY.size()));
    }

    private static String getAddress() {
        return ADDRESS.get(new Random().nextInt(ADDRESS.size()));
    }

    private static String getPostalCode() {
        return POSTAL.get(new Random().nextInt(POSTAL.size()));
    }

    public static String getByType(String locationType) {
        switch (locationType) {
            case "ADDRESS":
                return getAddress();
            case "AIRPORT":
                return getAirport();
            case "CITY":
                return getCity();
            case "POSTAL":
                return getPostalCode();
            default:
                return locationType;
        }
    }

    public static boolean supports(String locationType) {
        return LOCATIONS.contains(locationType);
    }
}
