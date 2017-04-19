/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet.api;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Collection of helper methods.
 * @author Renat Zhilkibaev
 */
final class Utils {

    private Utils() {
        // non instantiable class
    }

    /**
     * Converts {@link Date} to {@link XMLGregorianCalendar}.
     * @param date - the date to convert
     * @return converted date
     */
    public static XMLGregorianCalendar dateToXmlGregorianCalendar(Date date) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
        }
        catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
