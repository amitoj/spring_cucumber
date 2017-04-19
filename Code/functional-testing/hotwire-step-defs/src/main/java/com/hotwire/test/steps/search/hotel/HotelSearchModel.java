/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.hotel;

import java.util.Date;

import com.hotwire.selenium.desktop.searchsolution.SearchSolution;
import com.hotwire.test.steps.search.SearchModel;

/**
 * Hotel search model declares generic and hotel specific search functionality.
 */
public interface HotelSearchModel extends SearchModel {

    void verifyHotelSearchResultsPage();

    void chooseHotelsResultsByType(String resultsType);

    void chooseResultsTypeCloseTripWatcher(String resultsType);

    void verifyHotelDetailsPage(String pageType);

    void activateRetailExamplesForOpaque(boolean enableExamples);

    void chooseHotelFromResults(String neighborhood, String starRating, String crs);

    void verifyVisibilityOfRetailExamples(boolean examplesShouldBeVisible);

    void sortByCriteria(String sortCriteria);

    void verifyMediaPlannerLayerIsShown();

    void verifyTripWatcherLayer(boolean seeInResults);

    void turnOnTripWatcherLayer();

    void showActivateServicesTab();

    void verifyValidationErrorOnField(String validationError, String field);

    void filterOutTopNeighborhood();

    void clickSeeAllResultsLink();

    void appendQueryParameterToUrl(String queryParameter);

    void verifySearchCriteria();

    @Override
    void verifyActivitiesBanner(String destinationCity, Date startDate);

    @Override
    void verifyActivitiesPage(String destinationCity, Date endDate, Date startDate);

    @Override
    void clickBanner();

    void verifyMFI(HotelSearchParameters hotelSearchParameters);

    void choosePartner(String partner);

    void launchMFIPartnerSearch();

    void verifyPartnersSearch(HotelSearchParameters hotelSearchParameters, String partnerName);

    void verifyPartnersSearchInDB(String partnerName);

    void verifyHotelResultsPageView(String viewType);

    void verifyAllInclusivePriceAtResults(String priceType, String resultsType);

    void verifyAllInclusivePriceAtDetails();

    void chooseCustomerRecommendedSolution();

    void verifyCustomerRating(String rating);

    void selectHotelByName(String hotelName);

    void verifyPrice(String price);

    void verifyRetailHeroIsPresent();

    void verifyCrossSellData(SearchSolution solution);

    void verifySearchWinnerDeal(String dealType, SearchSolution solution);

    void verifyOpaqueHotelsLabel(String label, String resultsType);

    void verifyDiscountBannerOnFF();

    void verifyDiscountBannerOnHotelResults();

    void verifyCustomerCarePhoneNumber(String page, String phoneNumber);

    void verifyDisclaimer();

    void verifySavingsLayer(boolean state);

    void changeHotelSearch();

    void verifyHotelSearchDetails();

    void verifyFareFinderDestination(String topDestination);

    void verifyFacilitiesTermUsage(String term);

    void verifySemiOpaqueRoomPhotos();

    void clickSearch();

    void verifyResultsPageType(String resultsType);

    void displayMapPopup();

    void verifyMapPopup();

    void verifyHotelNeighborhood();

    void verifyNoRoomPhotos();

    void returnToSearchResults(String position);

    void removeSessionCookieAndReloadPage();

    void verifyBackToResultsLink();

    void verifyLowPriceGuaranteeOffer();

    void verifyKnowBeforeYouGoNotice();

    void verifyDetailsFareFinderState(boolean isFareFinderExpanded);

    void expandDetailsPageFareFinder();

    void collapseDetailsPageFareFinder();

    void verifyIntlDetailsPageFromIntlDetailsPageSearch(Date startDate, Date endDate);

    void verifySearchResultsPage(String resultsType);

    void clickOnTheHotelChildrenFiled();

    void setDates(Date startDate, Date endDate);

    void typeDates(Date startDate, Date endDate);

    void compareSearchResultsWithPrevious(boolean isTheSame);

    void rememberSearchSolutionsList();

    void checkFareFinderDefaultAdultsValue(Integer iAdults);

    void checkFareFinderDefaultChildrenValue(Integer iChildren);

    void verifyResultsPageHasSolutions();

    void doSubscribeOnResultPage();

    @Override
    void verifySuggestedLocation(String number, String location, String style, String click);

    void verifyCorrespondenceBetweenAdultsAndRooms();

    void chooseResultByPrice(Integer lessPrice, Integer morePrice);

    void checkResultsDistance();

    void verifyNeighborhoodsInDB();

    void verifyChainNamesInDB();

    void verifyResultsCurrency(String currency);

    void clickCommentCardLink();

    void typeAndSubmitCommentCard();

    void verifyCommentCardGone();

    void verifyCommentCardPopup();

    void getSearchResults();

    void verifyDistanceTextInResults(boolean isSeen);

    void sortResultsBy(String sortBy);

    void verifySortByResults(HotelResultsSortCriteria sortCriteria, HotelResultsSortOrderBy orderBy);

    void goBackToPreviousPage();

    void selectFilteringTab(String filterName);

    void chooseRecommendedFilterValue(HotelResultsFilteringEnum filterEnum);

    void verifyHotelFeatureInResults(String feature);

    void checkHotelFeatureCheckbox(String feature);

    void verifyFreeAmenityInDetailsPage(String feature);

    void verifyRecommendedRangeFromFiltersInResults(HotelResultsFilteringEnum resultsFilterEnum);

    void verifyRecommendedAreaFilterInResults();

    void unselectRecommendedFilterCheckbox(HotelResultsFilteringEnum filterEnum);

    void verifyAllAreasCheckboxSelectedState(boolean isSelected);

    void verifyResetFiltersDisplayed(boolean resetFiltersVisible);

    void clickResetFilters();

    void getFilteredHotelSearchResults();

    void verifyResultsNotFiltered();

    void viewRetailHotelExamples();

    void verifyVisibilityOfResultsRetailExamples(boolean retailExamplesShouldBeVisible);

    void verifyHotelResultsMapRenderedCorrectly();

    void verifyDareToCompareExists();

    void verifyTripWatcherLayerInResults(boolean tripWatcherIsDisplayed, boolean verifyInOpaque);

    void verifyReferencPriceModuleDisplayed();

    void verifyReferencePriceModuleValue();

    void verifyDbmLink();

    void verifyTripAdvisorInResults(boolean isVisible);

    void verifyRecommendedAccessibilityInResults();

    void verifyLPGLayer();

    void verifyCancellationPolicyPanel();

    void verifyTripAdvisorLayer();

    void verifyLocationDesc();

    void verifyFeaturesList();

    void watchThisTrip(String email);

    void verifyDetailsPageFarefinder();

    void verifyTripWatcherModuleInHotelResultsList(boolean isDisplayed);

    void clickResultListTripWatcherModule();

    void verifyTripWatcherLayerFromSpeedbump(boolean isFromSpeedbump);

    void getHotelReferenceNumberList();

    void setHotelSearchResultReferenceNumber();

    void compareSearchResultReferenceNumber(String isTheSame);

    void turnOnOffHotelInDB(boolean isOn);

    void verifyHComPopupWindow(boolean hcomPopupDisplayed);

    void verifyCallAndBookModule(String country);

    void saveHotelSearchId();

    void clickHotelRoomAdultDdl();

    void clickHotelRoomChildrenDdl();

    void verifyAdultNumberLimit(int maxAdults);

    void verifyChildrenNumberLimit(int maxChildren);

    void verifyNoErrorsOnPage();

    void confirmThatUrlContainsSearchTokenId(long tokenId);

    void chooseAmenitiesValue(String amenityName);

    void compareHotelResultsWithPartners(String partners, String numberOfModule);
}
