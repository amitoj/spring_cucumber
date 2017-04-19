/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.partners;

import java.util.Date;

/**
 * User: v-vyulun
 * Date: 04/10/13
 * Time: 4:52 AM
 */
public interface HotelPartnersPage {

    /**
     * Checks that search options from partner's site are the same as Hotwire's.
     * @param loc String - location from partner's site
     * @param pickUp Date - pickUpDate
     * @param dropOff Date - dropOffDate
     * @return TRUE if options are the same
     */
    boolean checkSearchOptions(String loc, Date pickUp, Date dropOff);
}
