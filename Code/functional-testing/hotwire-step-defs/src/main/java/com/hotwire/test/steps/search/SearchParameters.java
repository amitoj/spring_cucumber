/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search;

import java.util.Date;

import com.hotwire.test.steps.common.PGoodCode;

/**
 * This interface provides shared parameters to execute a fare finder.
 *
 * @author Alok Gupta
 * @since 2012.04
 *        Modified: LevR 06/05/12 This class should only provide shared parameters.
 *        All vertical specific parameters should be provided from the corresponding classes
 */
public interface SearchParameters {

    PGoodCode getPGoodCode();

    void setPGoodCode(PGoodCode pGoodCode);

    boolean isLandingPage();

    void setLandingPage(boolean landingPage);

    String getDestinationLocation();

    void setDestinationLocation(String destinationLocation);

    Date getStartDate();

    void setStartDate(Date startDate);

    /**
     * Sets startDate in String format something like '2 day from now'
     */
    void setStartDateString(String startDate);

    /**
     * Sets endDate in String format something like '2 day from now'
     */
    void setEndDateString(String endDateString);

    Date getEndDate();

    void setEndDate(Date endDate);

    boolean isRandomDates();

    void setRandomDates(boolean randomDates);

    boolean isRandomLocation();

    void setRandomLocation(boolean randomLocation);

    String getSearchId();

    void setSearchId(String searchId);
}
