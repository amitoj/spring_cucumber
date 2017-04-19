/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 3/6/14
 * Time: 11:23 PM
 * C3CaseNotesDao - aggregates methods and SQL connected with case notes
 */
public class C3CaseNotesDao extends C3SearchDao {
    private static final String CASE_NOTE_ID = "select case_id from c3_case where rownum=1";

    private static final String CHECK_INIQUE_CASE_ID = "select case_id from c3_case where case_id = ?";

    private static final String CASE_NOTE_EMAIL =
        "select c.email from customer c\n" +
        "join c3_case cc on cc.customer_id = c.customer_id";

    private static final String CASE_NOTE_ITINERARY =
        "select po.display_number from purchase_order po\n" +
        "join case_action ca on ca.purchase_order_id = po.purchase_order_id\n" +
        "where ca.display_number is not NULL\n" +
        "AND po.status_code > 30025\n" +
        "AND po.PARTNER_ID is NULL";

    private static final String REFERRAL_SOURCE_FOR_CASE_ID =
            "select REFERRAL_SOURCE from c3_case_detail where case_id =?";

    private static final String WORKFLOW_ENTRY_BY_ITINERARY =
            "select CORRELATION_ID from CASE_ACTION where CORRELATION_ID = ?";

    private static final String CASE_ID =
            "select case_id from c3_case cs JOIN customer c on cs.customer_id = c.customer_id where c.is_active='Y'";

    private static final String CUSTOMER_EMAIL_FROM_CASE_ID =
            "select email from customer c join c3_case cs on cs.customer_id = c.customer_id where case_id=?";


    public C3CaseNotesDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public String getCaseNoteId() {
        return getValueFromDB(CASE_NOTE_ID);
    }

    public String getCaseNotesEmail() {
        return getValueFromDB(CASE_NOTE_EMAIL);
    }

    public String getCaseNotesItinerary() {
        return getValueFromDB(CASE_NOTE_ITINERARY);
    }

    public boolean checkCaseIDinDB(String caseID) {
        return checkResultInDB(CHECK_INIQUE_CASE_ID, caseID);
    }

    public String getReferralSource4CaseNoteInDB(String caseID) {
        return getValueFromDB(REFERRAL_SOURCE_FOR_CASE_ID, caseID);
    }

    public boolean isWorkflowEntryInDB(String entry) {
        return checkResultInDB(WORKFLOW_ENTRY_BY_ITINERARY, entry);
    }

    public String getRandomCaseId() {
        return getRandomValueFromDB(CASE_ID);
    }

    public String getEmailByCaseId(String caseId) {
        return getValueFromDB(CUSTOMER_EMAIL_FROM_CASE_ID, caseId);
    }

}
