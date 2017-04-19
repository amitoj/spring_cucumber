/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bexRetail;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author jgonzalez
 */
public class BexSteps extends AbstractSteps {

    @Autowired
    @Qualifier("bexModel")
    BexModel bexModel;

    @And("^I use OPM T1 link with BID (.*) and SID (.*)$")
    public void goToOPMT1(String bid, String sid) {
        bexModel.goToOMPT(bid, sid);
    }

    @And("^i use OPM T2 link$")
    public void goToOPMT2() {
        bexModel.goToOMPT("BID", "SID");
    }

    @And("^i use SEM B link$")
    public void goToSemB() {
        bexModel.goToSem("S526", "B90016413", "K3605636", "M03", "AC90000", "CM900020");
    }

    @And("^i use SEM U link$")
    public void goToSemU() {
        bexModel.goToSem("S11", "B20114428", "K3598361", "M01", "AC20017", "CM200000");
    }


    @And("^i book on BEX$")
    public void finishBooking() {
        bexModel.bookFromResultsPage();
    }

    @And("^i use OPM T2 link with BID (.*) and SID (.*)$")
    public void goToOPMT2(String bid2, String sid2) {
        bexModel.goToOMPT(bid2, sid2);
    }

    @And("^i use SEM B link with SID (.*), BID (.*), CMID (.*), ACID (.*), KID (.*) and MID (.*)$")
    public void semBLink(String sid, String bid, String cmid, String acid, String kid, String mid) {
        bexModel.goToSem(sid, bid, cmid, acid, kid, mid);
    }

    @And("^i use SEM U link with SID (.*), BID (.*), CMID (.*), ACID (.*), KID (.*) and MID (.*)$")
    public void semULink(String sid, String bid, String cmid, String acid, String kid, String mid) {
        bexModel.goToSem(sid, bid, cmid, acid, kid, mid);
    }

    @And("^i use DBM link with NID (.*), VID (.*) and DID (.*)$")
    public void dbmLink(String nid, String vid, String did) {
        bexModel.goToDBM(nid, vid, did);
    }

    @And("^i use transaction email link with NID (.*), VID (.*) and DID (.*)$")
    public void transactionEmailLink(String nid, String vid, String did) {
        bexModel.goToDBM(nid, vid, did);
    }



    @And("^i use AFF link with siteID (.*)$")
    public void affLink(String siteID) {
        bexModel.goToAff(siteID);
    }

    @And("^i use META link with Expedia Hotel ID (.*) and RPE (.*)$")
    public void goToMetaLink(String hotelId, String rep) {
        bexModel.goToMeta(hotelId, rep);
    }

    @And("^i save the search details finishing transaction (.*)$")
    public void saveSearchDetails(String transactionNumber) {
        bexModel.saveSearchDetails(transactionNumber);
    }
}


