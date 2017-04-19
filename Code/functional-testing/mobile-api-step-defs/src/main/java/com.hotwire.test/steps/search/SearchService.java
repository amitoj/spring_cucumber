/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.search;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.testing.UnimplementedTestException;
import cucumber.api.PendingException;
import hotwire.eis.db.DatabaseCodes;
import hotwire.util.StringUtils;
import hotwire.view.jaxb.domain.mobileapi.Address;
import hotwire.view.jaxb.domain.mobileapi.ClientInfoType;
import hotwire.view.jaxb.domain.mobileapi.HotelAmenities;
import hotwire.view.jaxb.domain.mobileapi.Location;
import hotwire.view.jaxb.domain.mobileapi.search.DealStatus;
import hotwire.view.jaxb.domain.mobileapi.search.ImageUrls;
import hotwire.view.jaxb.domain.mobileapi.search.NeighborhoodsType;
import hotwire.view.jaxb.domain.mobileapi.search.RequestResultType;
import hotwire.view.jaxb.domain.mobileapi.search.SearchRQ;
import hotwire.view.jaxb.domain.mobileapi.search.SearchRS;
import hotwire.view.jaxb.domain.mobileapi.search.SearchSolution;
import hotwire.view.jaxb.domain.mobileapi.search.StartAndEndDate;
import hotwire.view.jaxb.domain.mobileapi.search.car.AgeOfDriver;
import hotwire.view.jaxb.domain.mobileapi.search.car.AirportCarLocation;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarInfo;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarMetaData;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarSearchCriteria;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarSearchRQ;
import hotwire.view.jaxb.domain.mobileapi.search.car.CarSearchSolution;
import hotwire.view.jaxb.domain.mobileapi.search.car.DepositMethod;
import hotwire.view.jaxb.domain.mobileapi.search.car.LocalCarLocation;
import hotwire.view.jaxb.domain.mobileapi.search.car.RentalAgency;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.GuestCount;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.HotelMetaData;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.HotelSearchCriteria;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.HotelSearchRQ;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.HotelSearchSolution;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.LevelOfOpacity;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.OpaqueHotelSearchSolution;
import hotwire.view.jaxb.domain.mobileapi.search.hotel.RetailHotelSearchSolution;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.testng.Assert.assertTrue;

/**
 * User: v-ngolodiuk
 * Since: 1/5/15
 */
public class SearchService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class.getName());

    private SearchRS searchResponse;

    private RequestProperties requestProperties;
    private RequestPaths paths;
    private SearchResponseHolder searchResponseHolder;

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setSearchResponseHolder(SearchResponseHolder searchResponseHolder) {
        this.searchResponseHolder = searchResponseHolder;
    }

    public void executeHotelSearch(String searchScope) throws DatatypeConfigurationException {
        try {
            if (searchScope.equalsIgnoreCase("opaque")) {
                client.reset().path(paths.getHotelSearchOpaquePath());
            }
            if (searchScope.equalsIgnoreCase("retail")) {
                client.reset().path(paths.getHotelSearchRetailPath());
            }
            client.header("X-Channel-ID", requestProperties.getChannelID());
            client.accept(MediaType.APPLICATION_XML_TYPE);
            SearchRQ hotelSearchRequest = createHotelSearchRequest();
            searchResponse = client.post(hotelSearchRequest, SearchRS.class);
            searchResponseHolder.setResponse(searchResponse);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Hotel search request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Hotel search request has failed with   " +  message, e);
        }
    }

    public void executeHotelDealSearch() throws DatatypeConfigurationException {
        HotelSearchRQ hotelSearchRequest = (HotelSearchRQ) createHotelSearchRequest();
        hotelSearchRequest.getSearchCriteria().setDealHash(requestProperties.getHotelSearchDealHash());
        hotelSearchRequest.setRequestResultType(RequestResultType.DEAL);

        try {
            client.reset()
                  .path(paths.getHotelSearchOpaquePath())
                  .accept(MediaType.APPLICATION_XML_TYPE);

            searchResponse = client.post(hotelSearchRequest, SearchRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Hotel deal search request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Hotel deal search request has failed with   " + message, e);
        }
    }

    public void verifySearchResultsArePresent() {
        List<SearchSolution> searchSolutions = searchResponse.getSearchResults().getSolution();
        if (searchSolutions.isEmpty()) {
            LOGGER.error("There are no search results after current search");
            throw new PendingException("There are no search results after current search");
        }
        assertThat(searchSolutions.size()).isGreaterThanOrEqualTo(1);
    }

    private List<OpaqueHotelSearchSolution> getOpaqueHotelSearchSolutions() {
        return new ArrayList<>(Collections2.transform(getSolution(), new Function<SearchSolution,
                OpaqueHotelSearchSolution>() {
            @Nullable
            @Override
            public OpaqueHotelSearchSolution apply(@Nullable SearchSolution input) {
                return (OpaqueHotelSearchSolution) input;
            }
        }));
    }

    private List<RetailHotelSearchSolution> getRetailHotelSearchSolutions() {
        return new ArrayList<>(Collections2.transform(getSolution(), new Function<SearchSolution,
                RetailHotelSearchSolution>() {
            @Nullable
            @Override
            public RetailHotelSearchSolution apply(@Nullable SearchSolution input) {
                return (RetailHotelSearchSolution) input;
            }
        }));
    }

    public void executeCarSearch() throws DatatypeConfigurationException {
        try {
            SearchRQ searchRequest = createCarSearchRequest();
            client.reset()
                .path(paths.getCarSearchPath())
                .accept(MediaType.APPLICATION_XML_TYPE);
            searchResponse = client.post(searchRequest, SearchRS.class);
            searchResponseHolder.setResponse(searchResponse);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Car search request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Car search request has failed with   " +  message, e);
        }
    }

    public SearchRQ createCarSearchRequest() {
        CarSearchRQ request = new CarSearchRQ();

        setClientInfo(request);
        CarSearchCriteria searchCriteria = new CarSearchCriteria();
        request.setSearchCriteria(searchCriteria);

        request.getSearchCriteria().setOneWay(requestProperties.getIsOneWayTrip());
        Location location = new Location();
        request.getSearchCriteria().setLocation(location);
        request.getSearchCriteria().getLocation().setOriginalLocation(requestProperties.getPickupLocation());

        if (requestProperties.getIsOneWayTrip()) {
            request.getSearchCriteria().getLocation().setDestinationLocation(requestProperties.getDropOffLocation());
        }

        XMLGregorianCalendar startDate = requestProperties.getStartDate();
        XMLGregorianCalendar endDate = requestProperties.getEndDate();
        request.getSearchCriteria().setStartAndEndDate(new StartAndEndDate(startDate, endDate));

        DepositMethod depositMethod = DepositMethod.fromValue(requestProperties.getDepositMethod());
        request.getSearchCriteria().setDepositMethod(depositMethod);

        AgeOfDriver ageOfDriver = AgeOfDriver.fromValue(requestProperties.getAgeOfDriver());
        request.getSearchCriteria().setAgeOfDriver(ageOfDriver);

        return request;
    }

    private SearchRQ setClientInfo(SearchRQ request) {
        request.setClientInfo(new ClientInfoType());
        request.getClientInfo().setClientID(requestProperties.getClientId());
        request.getClientInfo().setCountryCode(requestProperties.getCountryCode());
        request.getClientInfo().setCurrencyCode(requestProperties.getCurrencyCode());
        request.getClientInfo().setCustomerID(requestProperties.getCustomerId());
        request.getClientInfo().setLatLong(requestProperties.getLatLong());
        return request;
    }

    public SearchRQ createHotelSearchRequest() throws DatatypeConfigurationException {
        HotelSearchRQ request = new HotelSearchRQ();
        setClientInfo(request);
        request.setSearchCriteria(new HotelSearchCriteria());
        request.getSearchCriteria().setLocation(new Location());
        request.getSearchCriteria().getLocation().setDestinationLocation(requestProperties.getDestinationLocation());
        request.getSearchCriteria().setGuestCount(new GuestCount());
        request.getSearchCriteria().getGuestCount().setAdults(requestProperties.getNumberOfAdults());
        request.getSearchCriteria().getGuestCount().setNumberOfRooms(requestProperties.getNumberOfRooms());

        setStartAndEndDates(request);

        return request;
    }

    private void setStartAndEndDates(HotelSearchRQ searchRequest) throws DatatypeConfigurationException {
        XMLGregorianCalendar startDate = requestProperties.getStartDate();
        XMLGregorianCalendar endDate = requestProperties.getEndDate();
        searchRequest.getSearchCriteria().setStartAndEndDate(new StartAndEndDate(startDate, endDate));
    }

    public void checkSearchResponseExist() {
        assertThat(searchResponse).isNotNull();
    }

    public void extractResultId(int i) {
        requestProperties.setResultId(searchResponse.getSearchResults().getSolution().get(i).getResultID());
    }

    public void extractOpaqueHotelResultIdWithRating(float rating) {
        List<SearchSolution> solutionList = getSolution();
        for (SearchSolution solution : solutionList) {
            OpaqueHotelSearchSolution opaqueHotelSearchSolution = (OpaqueHotelSearchSolution) solution;
            if (opaqueHotelSearchSolution.getStarRating() == rating) {
                requestProperties.setResultId(opaqueHotelSearchSolution.getResultID());
                return;
            }
        }
    }

    public void chooseResultFromNeighborhoodForDetailsWithStarRating(float rating, int neighborhoodId) {
        List<SearchSolution> solutionList = getSolution();
        for (SearchSolution solution : solutionList) {
            OpaqueHotelSearchSolution opaqueHotelSearchSolution = (OpaqueHotelSearchSolution) solution;
            if ((opaqueHotelSearchSolution.getNeighborhoodId() == neighborhoodId) &&
                (opaqueHotelSearchSolution.getStarRating() == rating)) {
                requestProperties.setResultId(opaqueHotelSearchSolution.getResultID());
                return;
            }
        }
    }

    public boolean verifyResultsOpacity(boolean isOpaque) {
        char expectedCode = isOpaque ? DatabaseCodes.OPACITY_CODE_OPAQUE : DatabaseCodes.OPACITY_CODE_RETAIL;
        for (SearchSolution searchSolution : getSolution()) {
            CarSearchSolution carSearchSolution = (CarSearchSolution) searchSolution;
            if (carSearchSolution.getOpacityCode().equalsIgnoreCase(String.valueOf(expectedCode))) {
                return true;
            }
        }
        return false;
    }

    private List<SearchSolution> getSolution() {
        return searchResponse.getSearchResults().getSolution();
    }

    public void extractResultIdOfSpecificOpacity(String solutionType, int index) {
        boolean isOpaque = true;
        boolean isPrepaid = false;
        switch (solutionType) {
            case "opaque":
                isOpaque = true;
                isPrepaid = false;
                break;
            case "retail":
                isOpaque = false;
                isPrepaid = false;
                break;
            case "prepaid":
                isOpaque = false;
                isPrepaid = true;
                break;
            default :
                throw new UnimplementedTestException("Please implement case for " + solutionType + " solution type.");
        }

        CarSearchSolution solution = getQualifiedSolution(isOpaque, isPrepaid, index);
        requestProperties.setResultId(solution.getResultID());
    }

    private CarSearchSolution getQualifiedSolution(boolean isOpaque, boolean isPrepaid, int index) {
        String expectedOpacityCode = "N";
        if (isOpaque) {
            expectedOpacityCode = "O";
        }
        List<CarSearchSolution> solutionList = new ArrayList<>(getSolution().size());
        for (SearchSolution searchSolution : getSolution()) {
            CarSearchSolution solution = (CarSearchSolution) searchSolution;
            if ((solution.isPrepaidRetail() == isPrepaid) &&
                    (solution.getOpacityCode().equalsIgnoreCase(expectedOpacityCode))) {
                solutionList.add(solution);
            }
        }
        if (solutionList.isEmpty()) {
            throw new PendingException("The requested car type was not found in the given results set");
        }
        else {
            return solutionList.get(index);
        }
    }

    public void verifyLongitudeAndLatitudePresenceInSearch() {
        for (SearchSolution searchSolution : getSolution()) {
            CarSearchSolution carSearch = (CarSearchSolution) searchSolution;
            if (carSearch.getPickupLocation() instanceof AirportCarLocation)  {
                AirportCarLocation carPickupLocation = (AirportCarLocation) carSearch.getPickupLocation();
                assertThat(carPickupLocation.getLatLong().getLatitude()).isNotNull();
                assertThat(carPickupLocation.getLatLong().getLongitude()).isNotNull();
            }
            if (carSearch.getDropoffLocation() instanceof AirportCarLocation)  {
                AirportCarLocation carDropOffLocation = (AirportCarLocation) carSearch.getDropoffLocation();
                assertThat(carDropOffLocation.getLatLong().getLatitude()).isNotNull();
                assertThat(carDropOffLocation.getLatLong().getLongitude()).isNotNull();
            }
            if (carSearch.getPickupLocation() instanceof LocalCarLocation)  {
                LocalCarLocation carPickupLocation = (LocalCarLocation) carSearch.getPickupLocation();
                assertThat(carPickupLocation.getLatLong().getLatitude()).isNotNull();
                assertThat(carPickupLocation.getLatLong().getLongitude()).isNotNull();
            }
            if (carSearch.getDropoffLocation() instanceof LocalCarLocation)  {
                LocalCarLocation carDropOffLocation = (LocalCarLocation) carSearch.getDropoffLocation();
                assertThat(carDropOffLocation.getLatLong().getLatitude()).isNotNull();
                assertThat(carDropOffLocation.getLatLong().getLongitude()).isNotNull();
            }
        }
    }


    public void checkSearchCriteria() {
        assertThat(searchResponse.getSearchResults().getSearchCriteria()).isNotNull();
    }

    public void checkHotelSearchMetadata(boolean shouldExist) {
        if (shouldExist) {
            assertThat(searchResponse.getMetaData()).isNotNull();
        }
        else {
            assertThat(searchResponse.getMetaData()).isNull();
        }
    }

    public void checkNumberOfNeighborhoods(int numberOfNeighborhoods) {
        NeighborhoodsType neighborhoods = ((HotelMetaData) searchResponse.getMetaData()).getNeighborhoods();
        assertThat(neighborhoods.getNeighborhood().size()).isGreaterThan(numberOfNeighborhoods);
    }

    public void checkNumberOfSearchResults(int arg1) {
        if (getSolution().isEmpty()) {
            throw new PendingException("There are no search results in current search");
        }
        assertThat(getSolution().size()).isGreaterThanOrEqualTo(arg1);
    }

    public void checkNoErrorsAndWarningsAfterSearch() {
        assertThat(searchResponse.getErrors()).isNull();
        assertThat(searchResponse.getWarnings()).isNull();
    }

    public void verifyRetailSolutionDetailsOnSearch() {
        for (RetailHotelSearchSolution solution : getRetailHotelSearchSolutions()) {
            assertThat(solution.getStarRating()).isNotNull();
            assertThat(solution.getTripAdvisorRating()).isNotNull();
            assertThat(solution.getSolutionName()).isNotEmpty();
            assertThat(solution.getResortFee()).isNotNull();
            assertThat(solution.getDistanceInfo()).isNotEmpty();
            assertThat(solution.getDistanceFromSearchLocation()).isNotNull();
            assertThat(solution.getBestValue()).isNotNull();
            assertThat(solution.getTripTotal()).isNotNull();
            assertThat(solution.getResultID()).isNotNull();
            assertThat(solution.getHwRefNumber()).isNotNull();
            assertThat(solution.getHotelLatLong()).isNotNull();
            assertThat(solution.getCharges()).isNotNull();
        }
    }

    public void verifyOpaqueSolutionDetailsOnSearch() {
        for (OpaqueHotelSearchSolution solution : getOpaqueHotelSearchSolutions()) {
            assertThat(solution.getStarRating()).isNotNull();
            assertThat(solution.getSolutionName()).isNotEmpty();
            assertThat(solution.getResortFee()).isNotNull();
            assertThat(solution.getDistanceInfo()).isNotEmpty();
            assertThat(solution.getDistanceFromSearchLocation()).isNotNull();
            assertThat(solution.getBestValue()).isNotNull();
            assertThat(solution.getTripTotal()).isNotNull();
            assertThat(solution.getResultID()).isNotNull();
            assertThat(solution.getHwRefNumber()).isNotNull();
            assertThat(solution.getCharges()).isNotNull();
            assertThat(solution.getNeighborhoodId()).isNotNull();
            assertThat(solution.getSummaryOfCharges()).isNotNull();
        }
    }

    public void verifyAmenitiesOnResults(String resultType) {
        if (resultType.equalsIgnoreCase("opaque")) {
            for (OpaqueHotelSearchSolution solution : getOpaqueHotelSearchSolutions()) {
                checkAmenities(solution);
            }
        }
        else {
            for (RetailHotelSearchSolution solution : getRetailHotelSearchSolutions()) {
                checkAmenities(solution);
            }
        }

    }

    private void checkAmenities(HotelSearchSolution solution) {
        if (solution.getHotelAmenities() != null) {
            HotelAmenities firstAmenity = solution.getHotelAmenities().getAmenity().get(0);
            assertThat(firstAmenity.getName()).isNotEmpty();
            assertThat(firstAmenity.getDescription()).isNotEmpty();
            assertThat(firstAmenity.getCode()).isNotEmpty();
        }
    }

    public void verifyNeighborhoodsContainCityId() {
        NeighborhoodsType neighborhoods = ((HotelMetaData) searchResponse.getMetaData()).getNeighborhoods();
        assertThat(neighborhoods.getNeighborhood().get(0).getCityId()).isNotNull();
    }

    public void verifyCostSummaryInformation(String resultType) {
        if (resultType.equalsIgnoreCase("opaque")) {
            for (OpaqueHotelSearchSolution solution : getOpaqueHotelSearchSolutions()) {
                assertThat(solution.getSummaryOfCharges().getSubTotal()).isNotNull();
                assertThat(solution.getSummaryOfCharges().getTotal()).isNotNull();
                assertThat(solution.getSummaryOfCharges().getDailyRate()).isNotNull();
                assertThat(solution.getSummaryOfCharges().getNumberOfNights()).isNotNull();
                assertThat(solution.getSummaryOfCharges().getTaxesAndFees()).isNotNull();
                assertThat(solution.getSummaryOfCharges().getExtraPersonFees()).isNotNull();
            }
        }
        else {
            for (RetailHotelSearchSolution solution : getRetailHotelSearchSolutions()) {
                assertThat(solution.getTripTotal()).isNotNull();
                assertThat(solution.getCharges().getDisplayPrice()).isNotNull();
                assertThat(solution.getCharges().getAveragePricePerNight()).isNotNull();
            }
        }

    }

    public void verifyNeighborhoodTagLine() {
        NeighborhoodsType neighborhoods = ((HotelMetaData) searchResponse.getMetaData()).getNeighborhoods();
        assertThat(neighborhoods.getNeighborhood().get(0).getDescription()).isNotNull();
    }

    public void verifyNumberOfStarRatingHotels(int minHotelsNumber) {
        for (OpaqueHotelSearchSolution solution : getOpaqueHotelSearchSolutions()) {
            assertThat(solution.getStarRatingHotels().getSampleHotelChain().size()).isGreaterThan(minHotelsNumber);
        }
    }

    public void verifyProperFieldsAreRevealed() {
        List<OpaqueHotelSearchSolution> moloSolutions = new ArrayList<>(getOpaqueHotelSearchSolutions().size());
        for (OpaqueHotelSearchSolution solution : getOpaqueHotelSearchSolutions()) {
            if (solution.getLevelOfOpacity() == LevelOfOpacity.FULLY_DISCLOSED.value()) {
                moloSolutions.add(solution);
                checkRevealFieldsForSolution(solution);
            }
        }
        if (moloSolutions.isEmpty()) {
            throw new PendingException("There are no MOLO solutions after current search");
        }
    }

    public void verifyMOLOSolutionsAreAbsent() {
        for (OpaqueHotelSearchSolution solution : getOpaqueHotelSearchSolutions()) {
            assertThat(solution.getLevelOfOpacity()).isNotEqualTo(LevelOfOpacity.FULLY_DISCLOSED.value());
        }
    }

    private void checkRevealFieldsForSolution(OpaqueHotelSearchSolution opaqueHotelSearchSolution) {
        assertThat(opaqueHotelSearchSolution.getHotelName()).isNotEmpty();
        Address hotelAddress = opaqueHotelSearchSolution.getHotelAddress();
        assertThat(hotelAddress).isNotNull();
        assertThat(hotelAddress.getAddressLine1()).isNotEmpty();
        assertThat(hotelAddress.getCity()).isNotEmpty();
        assertThat(hotelAddress.getCountry()).isNotEmpty();
        ImageUrls hotelImageUrls = opaqueHotelSearchSolution.getHotelImageUrls();
        assertThat(hotelImageUrls).isNotNull();
        assertThat(opaqueHotelSearchSolution.getHotelLatLong()).isNotNull();
    }

    private String createXmlExploitRequest(String requestFilePath) throws Throwable {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(requestFilePath);
        return IOUtils.toString(inputStream, "UTF-8");
    }

    public void searchAndVerifyXmlExploit(String requestFilePath) throws Throwable {
        try {
            searchResponse = client.post(createXmlExploitRequest(requestFilePath), SearchRS.class);
            fail("Test failed since search with XML entity was processed with no error");
        }
        catch (InternalServerErrorException e) {
            e.getResponse().bufferEntity();
            LOGGER.error("Search returned status " + e.getResponse().getStatus());
            LOGGER.error("Response body:\n" + e.getResponse().getEntity());
            String response = e.getResponse().readEntity(String.class);
            assertTrue(response.matches(
                    ".*?javax.xml.bind.UnmarshalException.*?[\\n].*?[\\n].*?was referenced, but not declared.*?"));
        }
    }

    public void chooseMOLOResultForDetails() {
        extractHotelResultIdForDetails(LevelOfOpacity.FULLY_DISCLOSED);
    }

    public void chooseOPAQUEResultForDetails() {
        extractHotelResultIdForDetails(LevelOfOpacity.FULLY_OPAQUE);
    }

    private void extractHotelResultIdForDetails(LevelOfOpacity levelOfOpacity) {
        List<SearchSolution> solutionList = searchResponse.getSearchResults().getSolution();
        String resultsIdToSet = "";
        for (SearchSolution solution : solutionList) {
            OpaqueHotelSearchSolution opaqueHotelSearchSolution = (OpaqueHotelSearchSolution) solution;
            if (opaqueHotelSearchSolution.getLevelOfOpacity() == levelOfOpacity.value()) {
                resultsIdToSet = opaqueHotelSearchSolution.getResultID();
                requestProperties.setResultId(resultsIdToSet);
                break;
            }
        }
        if (resultsIdToSet.isEmpty()) {
            throw new PendingException("There are no results with opacity code " + levelOfOpacity.value());
        }
    }

    public void setDealHash(String dealHash) {
        requestProperties.setHotelSearchDealHash(dealHash);
    }

    public void verifyDealStatus(String stDealStatus) {
        OpaqueHotelSearchSolution searchSolution = getOpaqueHotelSearchSolutions().get(0);
        LOGGER.info("The lowest deal price in search response was: " +
                searchSolution.getCharges().getDisplayPrice());
        assertThat(searchResponse.getSearchResults().getDealStatus())
                .isEqualTo(DealStatus.valueOf(stDealStatus.toUpperCase()));
    }

    public void verifyCarSearchMetadata() {
        assertThat(((CarMetaData) searchResponse.getMetaData()).getCarInfoMetaData()).isNotNull();
    }

    public void verifyNumberOfCarTypes(int arg1) {
        Set<CarInfo> carInfo = ((CarMetaData) searchResponse.getMetaData()).getCarInfoMetaData().getCarInfo();
        assertThat(carInfo.size()).isGreaterThanOrEqualTo(arg1);
    }

    public void verifyNumberOfCarAgencies(int arg1) {
        Set<RentalAgency> rentalAgencies =
                ((CarMetaData) searchResponse.getMetaData()).getRentalAgencyMetaData().getRentalAgencies();
        if (rentalAgencies.isEmpty()) {
            throw new PendingException("There are no rental agencies in car search result");
        }
        assertThat(rentalAgencies.size()).isGreaterThanOrEqualTo(arg1);
    }

    public void verifyCarTypeImageUrls() {
        Set<CarInfo> carInfo = ((CarMetaData) searchResponse.getMetaData()).getCarInfoMetaData().getCarInfo();
        String regex = "^http:\\/\\/.+png$";
        for (CarInfo car : carInfo) {
            String smallImageUrl = car.getCarTypeImageUrls().getSmallImageUrl();
            assertThat(smallImageUrl).isNotEmpty().contains("181x82");
            assertThat(smallImageUrl).matches(regex);

            String largeImageUrl = car.getCarTypeImageUrls().getLargeImageUrl();
            assertThat(largeImageUrl).isNotEmpty().contains("289x137");
            assertThat(largeImageUrl).matches(regex);
        }
    }

    public boolean verifyLocalDestination() {
        for (SearchSolution searchSolution : getSolution()) {
            CarSearchSolution carSearchSolution = (CarSearchSolution) searchSolution;
            if (carSearchSolution.isLocalRentalFlag()) {
                return true;
            }
        }
        return false;
    }

    public void verifyDistanceFromAddress(String negateString) {
        //the negate string tells you if the distanceFromAddress needs to be present or not
        //example steps: "for airport car I do (not) have distance from my address information"
        //"not" in the above step is the negateString.
        boolean negativeScenario = !StringUtils.isEmpty(negateString) && negateString.equalsIgnoreCase("not");
        for (SearchSolution searchSolution : getSolution()) {
            CarSearchSolution carSearchSolution = (CarSearchSolution) searchSolution;
            if (!carSearchSolution.isLocalRentalFlag()) {
                AirportCarLocation pickUpLocation = (AirportCarLocation) carSearchSolution.getPickupLocation();
                AirportCarLocation dropOffLocation = (AirportCarLocation) carSearchSolution.getDropoffLocation();
                //if negateString is false then it means distanceFromAddress should not be null
                if (!negativeScenario) {
                    assertThat(pickUpLocation.getDistanceFromAddress())
                            .as("Pick up location distanceFromAddress should not null for non-Airport search " +
                                    "with airport solution")
                            .isNotNull();
                    assertThat(dropOffLocation.getDistanceFromAddress())
                            .as("drop off location distanceFromAddress should not null for non-Airport search " +
                                    "with airport solution")
                            .isNotNull();
                    return;
                }
            }
        }
        //If code reaches here, then distanceFromAddress is null. That is valid when the negateString is "not"
        assertThat(negativeScenario)
                .as("distanceFromAddress is not present when it is supposed to be")
                .isTrue();
    }

    public void verifyDisambiguationResponse() {
        assertThat(searchResponse.getOriginLocationMatches().getLocationMatch().get(0)).
                as("Disambiguation response does not contain matching locations list.").isNotEmpty();
    }

    public void verifyStrikeThroughPrice() {
        for (SearchSolution searchSolution : getSolution()) {
            CarSearchSolution carSearchSolution = (CarSearchSolution) searchSolution;
            if (carSearchSolution.getOpacityCode().equals(String.valueOf(DatabaseCodes.OPACITY_CODE_OPAQUE))) {
                assertThat(carSearchSolution.getCarSummaryOfCharges().getStrikeThruPrice()).isNotNull();
            }
        }
    }

    public void extractCarTypeResultIdForDetails(String carTYpe) {
        for (SearchSolution searchSolution : getSolution()) {
            CarSearchSolution car = (CarSearchSolution) searchSolution;
            if (car.getCarTypeCode().equalsIgnoreCase(carTYpe)) {
                requestProperties.setResultId(car.getResultID());
                return;
            }
        }
    }

    public void verifyDebitUnfriendlyFlagOnSearch() {
        CarSearchSolution solution = (CarSearchSolution) getSolution().get(0);
        assertThat(solution.isDebitUnfriendly()).isFalse();
    }

    public void verifyCityCentroidInSearchResponse() {
        assertThat(searchResponse.getSearchResults().getSearchCriteria().getLocation().getOrigin()).isNotNull();
        assertThat(searchResponse.getSearchResults().getSearchCriteria().getLocation().getOrigin().getLatLong())
                .isNotNull();
        assertThat(searchResponse.getSearchResults().getSearchCriteria().getLocation().getDestination()).isNotNull();
        assertThat(searchResponse.getSearchResults().getSearchCriteria().getLocation().getDestination().getLatLong())
                .isNotNull();

        if (!requestProperties.getIsOneWayTrip()) {
            assertThat(searchResponse.getSearchResults().getSearchCriteria().getLocation().getOrigin()
                    .getLatLong().getLatitude())
                    .isEqualTo(searchResponse.getSearchResults().getSearchCriteria().getLocation().getDestination()
                            .getLatLong().getLatitude());
            assertThat(searchResponse.getSearchResults().getSearchCriteria().getLocation().getOrigin()
                    .getLatLong().getLongitude())
                    .isEqualTo(searchResponse.getSearchResults().getSearchCriteria().getLocation().getDestination()
                            .getLatLong().getLongitude());
        }
    }

    public void verifyAcceptedCardTypeFieldOnSearch() {
        CarSearchSolution solution = (CarSearchSolution) getSolution().get(0);
        assertThat(solution.getAcceptedCardType()).isNotNull();
    }

    public void verifyPrepaidRetailFieldOnSearch() {
        CarSearchSolution solution = (CarSearchSolution) getSolution().get(0);
        assertThat(solution.isPrepaidRetail()).isNotNull();
    }

    public void checkTripAdvisorRatingForMOLOSolutions() {
        for (OpaqueHotelSearchSolution solution : getOpaqueHotelSearchSolutions()) {
            if (solution.getLevelOfOpacity() == LevelOfOpacity.FULLY_DISCLOSED.value()) {
                if  (solution.getRatingInfo().getTripAdvisorRating() != null) {
                    assertThat(solution.getRatingInfo().getTripAdvisorNumOfReview()).isNotNull();
                    assertThat(solution.getRatingInfo().getTripAdvisorNumOfReviewMessage()).isNotNull();
                }
            }
        }
    }

}

