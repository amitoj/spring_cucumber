/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search.hotel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.test.steps.common.PGoodCode;
import com.hotwire.test.steps.search.BaseSearchParameters;

/**
 * Hotel search parameters impl.
 */
public class HotelSearchParametersImpl extends BaseSearchParameters implements HotelSearchParameters {

    private final List<HotelRoomParameters> hotelRoomParameters = new ArrayList<HotelRoomParameters>();

    private Integer numberOfHotelRooms;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private SearchSolution selectedSearchSolution;
    private List<SearchSolution> searchSolutionsList;
    private String destination;
    private List<String> hotelReferenceNumbersList;
    private boolean enableHComSearch;
    private String searchId;
    private String searchResultReference;

    @Override
    public Integer getNumberOfHotelRooms() {
        return numberOfHotelRooms;
    }

    @Override
    public void setNumberOfHotelRooms(Integer numberOfHotelRooms) {
        this.numberOfHotelRooms = numberOfHotelRooms;
    }

    @Override
    public List<HotelRoomParameters> getHotelRoomParameters() {
        return Collections.unmodifiableList(hotelRoomParameters);
    }

    @Override
    public Boolean addHotelRoomParameter(HotelRoomParameters parameters) {
        return hotelRoomParameters.add(parameters);
    }

    @Override
    public Boolean removeHotelRoomParameter(HotelRoomParameters parameters) {
        return hotelRoomParameters.remove(parameters);
    }

    @Override
    public Integer getNumberOfAdults() {
        return numberOfAdults;
    }

    @Override
    public void setNumberOfAdults(Integer numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    @Override
    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    @Override
    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    @Override
    protected PGoodCode getPGoodCode() {
        return PGoodCode.H;
    }

    @Override
    public SearchSolution getSelectedSearchSolution() {
        return selectedSearchSolution;
    }

    @Override
    public void setSelectedSearchSolution(SearchSolution selectedSearchSolution) {
        this.selectedSearchSolution = selectedSearchSolution;
    }

    @Override
    public void setSearchSolutionsList(List<SearchSolution> searchSolutionsList) {
        this.searchSolutionsList = searchSolutionsList;
    }

    @Override
    public List<SearchSolution> getSearchSolutionsList() {
        return this.searchSolutionsList;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public void setHotelRefNumbersList(List<String> hotelRefNumbersList) {
        this.hotelReferenceNumbersList = hotelRefNumbersList;
    }

    @Override
    public List<String> getHotelRefNumbersList() {
        return this.hotelReferenceNumbersList;
    }

    @Override
    public void setEnableHComSearch(boolean enableHComSearch) {
        this.enableHComSearch = enableHComSearch;
    }

    @Override
    public Boolean getEnableHComSearch() {
        return enableHComSearch;
    }

    @Override
    public void setHotelSearchId(String searchId) {
        this.searchId = searchId;
    }

    @Override
    public String getHotelSearchId() {
        return searchId;
    }

    public void setHotelSearchResultReference(String searchResultReference) {
        this.searchResultReference = searchResultReference;
    }

    public String getHotelSearchResultReference() {
        return this.searchResultReference;
    }
}
