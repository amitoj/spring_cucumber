/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: v-abudyak
 * Date: 4/13/12
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public final class DateTool {

    public static String DATEFORMAT_FULL_0 = "yyyyMMddHHmmssSSS";
    public static String DATEFORMAT_FULL_1 = "yyyy/MM/dd HH:mm:ss:SSS";
    public static String DATEFORMAT_NORMAL = "MM/dd/yyyy";

    private DateTool() {

    }

    /**
     * Generate today date as string
     * @return date as string with format DATEFORMAT_NORMAL
     */
    public static String dateToString() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_NORMAL);
        Calendar hwCal = Calendar.getInstance();
        return sdf.format(hwCal.getTime());
    }

    /**
     * Generate date as string with shift from today date
     * negative shift mean shift to past
     * @param shift
     * @return
     */
    public static String shiftDateToString(int shift) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT_NORMAL);
        Calendar hwCal = Calendar.getInstance();
        hwCal.add(Calendar.DAY_OF_YEAR, shift);
        return sdf.format(hwCal.getTime());
    }

    public static String dateToStringWithFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar hwCal = Calendar.getInstance();
        return sdf.format(hwCal.getTime());
    }

    public static String shiftDateToStringWithFormat(String format, int shift) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar hwCal = Calendar.getInstance();
        hwCal.add(Calendar.DAY_OF_YEAR, shift);
        return sdf.format(hwCal.getTime());
    }

    public static String shiftCurrentYear(int shift) {
        Calendar hwCal = Calendar.getInstance();
        hwCal.add(Calendar.YEAR, shift);
        return String.valueOf(hwCal.get(Calendar.YEAR));
    }
}
