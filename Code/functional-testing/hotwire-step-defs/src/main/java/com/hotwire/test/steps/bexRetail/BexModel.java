/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bexRetail;

/**
 * @author jgonzalez
 *
 */
public interface BexModel {


    void goToOMPT(String bid, String sid);

    void goToDBM(String nid, String vid, String did);

    void goToSem(String sid, String bid, String cmid, String acid, String kid, String mid);

    void bookFromResultsPage();

    void saveSearchDetails(String transactionNumber);

    void goToAff(String siteID);

    void goToMeta(String hotelId, String rpe);
}
