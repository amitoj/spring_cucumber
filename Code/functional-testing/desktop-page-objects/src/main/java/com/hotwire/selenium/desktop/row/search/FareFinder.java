/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.search;

import java.util.Date;
import java.util.List;

public interface FareFinder {

    void findFare();

    String getDestinationLocation();

    FareFinder setDestinationLocation(String destinationLocation);

    FareFinder setStartDate(Date startDate);

    FareFinder setEndDate(Date endDate);

    FareFinder setRoomCount(Integer roomCount);

    List<String> getSearchSuggestions(String destinationLocation);
}
