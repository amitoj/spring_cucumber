/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.common;

import java.util.HashSet;
import java.util.Set;

/**
 * @author v-mzabuga
 * @since 7/17/12
 */
public enum PGoodCode {

    H('H', "Hotel", "Hotels"),
    A('A', "Air", "Flight", "Flights"),
    C('C', "Car", "Cars"),
    R('R', "Cruise", "Cruises"),
    P('P', "Package", "Packages"),
    V('V', "Activities", "Activities"),
    T('T', "TripStarter", "TripStarter");

    private Set<String> humanNameSet;
    private char humanNameChar;

    private PGoodCode(char humanNameChar, String... humanNames) {
        this.humanNameSet = new HashSet<String>(humanNames.length);
        this.humanNameChar = humanNameChar;
        humanNameSet.add(Character.valueOf(humanNameChar).toString());
        for (String humanName : humanNames) {
            humanNameSet.add(humanName.toLowerCase());
        }
    }

    public static PGoodCode toPGoodType(String humanName) {
        for (PGoodCode enumInstance : values()) {
            if (enumInstance.humanNameSet.contains(humanName.toLowerCase())) {
                return enumInstance;
            }
        }
        return null;
    }

    public char toChar() {
        return humanNameChar;
    }
}
