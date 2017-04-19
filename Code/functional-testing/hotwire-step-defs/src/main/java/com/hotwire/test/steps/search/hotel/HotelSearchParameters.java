/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.hotel;

import java.util.List;

import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.test.steps.search.SearchParameters;

/**
 * Hotel search Parameters
 */
public interface HotelSearchParameters {

    SearchParameters getGlobalSearchParameters();

    Integer getNumberOfHotelRooms();

    void setNumberOfHotelRooms(Integer numberOfHotelRooms);

    Integer getNumberOfAdults();

    void setNumberOfAdults(Integer numberOfAdults);

    Integer getNumberOfChildren();

    void setNumberOfChildren(Integer numberOfChildren);

    List<HotelRoomParameters> getHotelRoomParameters();

    Boolean addHotelRoomParameter(HotelRoomParameters parameters);

    Boolean removeHotelRoomParameter(HotelRoomParameters parameters);

    SearchSolution getSelectedSearchSolution();

    void setSelectedSearchSolution(SearchSolution hotwireCustomerRecomended);

    void setSearchSolutionsList(List<SearchSolution> searchSolutionsList);

    List<SearchSolution> getSearchSolutionsList();

    String getDestination();

    void setDestination(String destination);

    void setHotelRefNumbersList(List<String> hotelRefNumbersList);

    List<String> getHotelRefNumbersList();

    void setEnableHComSearch(boolean enableHComSearch);

    Boolean getEnableHComSearch();

    void setHotelSearchId(String searchId);

    String getHotelSearchId();

    void setHotelSearchResultReference(String searchResultReference);

    String getHotelSearchResultReference();
}
