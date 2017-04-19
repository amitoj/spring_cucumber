/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search;

import java.util.Date;

import com.hotwire.test.steps.common.PGoodCode;
import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.Options;
import com.mdimension.jchronic.utils.Span;

/**
 * This class provides parameters to execute a fare finder.
 *
 * @author Alok Gupta
 * @since 2012.04
 *        Modified: LevR 06/05/12 This class should only provide shared parameters.
 *        All vertical specific parameters should be provided from the corresponding classes
 */
public class SearchParametersImpl implements SearchParameters {

    private PGoodCode pgoodCode;
    private boolean isLandingPage;

    private String destinationLocation;
    private Date startDate;
    private Date endDate;
    /**
     * Will set to TRUE if we are using a random
     * valid dates for search (For ex. in step "I want to travel in the near future")
     */
    private boolean randomDates;
    private boolean randomLocation;

    private String searchId;

    public PGoodCode getPGoodCode() {
        return pgoodCode;
    }

    public void setPGoodCode(PGoodCode pGoodCode) {
        this.pgoodCode = pGoodCode;
    }

    public boolean isLandingPage() {
        return isLandingPage;
    }

    public void setLandingPage(boolean landingPage) {
        isLandingPage = landingPage;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDateString(String startDate) {
        Span span = Chronic.parse(startDate, new Options());
        this.startDate = span.getEndCalendar().getTime();
    }

    public void setEndDateString(String endDate) {
        Span span = Chronic.parse(endDate, new Options());
        this.endDate = span.getEndCalendar().getTime();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isRandomDates() {
        return randomDates;
    }

    public void setRandomDates(boolean randomDates) {
        this.randomDates = randomDates;
    }

    public boolean isRandomLocation() {
        return randomLocation;
    }

    public void setRandomLocation(boolean randomLocation) {
        this.randomLocation = randomLocation;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}
