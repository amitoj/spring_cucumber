/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.bean;

/**
* Contains all the info for travel advisories manipulation.
 */
public class TravelAdvisoryInfo extends ToolsAbstractBean {
    private String title;
    private String dateUpdated;
    private boolean displayOnDomestic;
    private boolean displayOnIntl;
    private String message;
    private String dateExpired;
    private int index;

    private TravelAdvisoryIssues travelAdvisoryIssue;

    /**
     * Travel advisory issues type. In fact profiles.
     */
    public enum TravelAdvisoryIssues { FIRST, SECOND, EMPTY, WRONG }

    public TravelAdvisoryIssues getTravelAdvisoryIssue() {
        return travelAdvisoryIssue;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setTravelAdvisoryIssue(String travelAdvisoryIssue) {
        this.travelAdvisoryIssue = TravelAdvisoryIssues.valueOf(travelAdvisoryIssue.toUpperCase());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDisplayOnDomestic() {
        return displayOnDomestic;
    }

    public void setDisplayOnDomestic(boolean displayOnDomestic) {
        this.displayOnDomestic = displayOnDomestic;
    }

    public boolean isDisplayOnIntl() {
        return displayOnIntl;
    }

    public void setDisplayOnIntl(boolean displayOnIntl) {
        this.displayOnIntl = displayOnIntl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(String dateExpired) {
        this.dateExpired = dateExpired;
    }

//    public DateTime parseDateTime(String dirtyDate) {
//        String trimmedDate = dirtyDate.replaceAll("[^a-zA-Z0-9,:]", "");
//        String format = (trimmedDate.contains(":")) ? ROW_ALERT_DATE_FORMAT : US_ALERT_DATE_FORMAT;
//        return DateTimeFormat.forPattern(format).parseDateTime(trimmedDate);
//    }

}
