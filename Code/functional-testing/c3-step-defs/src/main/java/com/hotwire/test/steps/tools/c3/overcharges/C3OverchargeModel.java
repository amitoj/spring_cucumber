/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.overcharges;


import com.hotwire.selenium.tools.c3.hotel.C3HotelHomePage;
import com.hotwire.selenium.tools.c3.overcharges.C3HotelOverchargeInfoPage;
import com.hotwire.selenium.tools.c3.overcharges.C3OverchargeAmountPopUp;
import com.hotwire.selenium.tools.c3.overcharges.C3OverchargesReportPage;
import com.hotwire.selenium.tools.c3.overcharges.C3OverchargesList;
import com.hotwire.selenium.tools.c3.overcharges.C3SearchOverchargesPage;
import com.hotwire.test.steps.tools.bean.c3.C3HotelSupplyInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.test.steps.tools.bean.c3.C3OverchargesInfo;
import com.hotwire.test.steps.tools.ToolsAbstractModel;


import com.hotwire.util.db.c3.C3HotelOverchargesDao;
import org.fest.assertions.Assertions;
import org.fest.assertions.Delta;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cucumber.api.PendingException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-abudyak
 * Date: 9/19/13
 * Time: 8:59 AM
 */
public class C3OverchargeModel extends ToolsAbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3OverchargeModel.class);

    @Autowired
    private C3OverchargesInfo c3OverchargesInfo;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private C3HotelSupplyInfo c3HotelSupplyInfo;

    @Autowired
    private SimpleJdbcDaoSupport databaseSupport;

    public void searchOverchargesWithPeriod(String searchBy, String period, String assigned) {
        String assignee = assigned;
        C3SearchOverchargesPage searchOverchargesPage = new C3SearchOverchargesPage(getWebdriverInstance());

        if ("this HSR person".equals(assigned)) {
            assignee = c3OverchargesInfo.getAssignee();
        }

        searchOverchargesPage.selectSearchByParam(searchBy);

        populateSearchPeriod(period, searchOverchargesPage);
        populateAssignee(assignee, searchOverchargesPage);
        c3OverchargesInfo.setSearchBy(searchBy);

        searchOverchargesPage.clickSearch();

        if (searchOverchargesPage.errorOnPagePresent()) {
            throw new PendingException("No overcharge reports for the given period");
        }
    }

    public void chooseSearchForUnassignedOvercharges() {
        C3SearchOverchargesPage searchOverchargesPage = new C3SearchOverchargesPage(getWebdriverInstance());
        searchOverchargesPage.clickSearchForUnassigned();
    }

    public void startSearch() {
        C3SearchOverchargesPage searchOverchargesPage = new C3SearchOverchargesPage(getWebdriverInstance());
        searchOverchargesPage.clickSearch();
    }

    public void checkOverchargeInList(boolean isOverchargeDisplayedInList) {
        C3OverchargesList overchargesList = new C3OverchargesList(getWebdriverInstance());

        boolean isOverchargeDisplayed = true;
        for (WebElement ovr : overchargesList.getOverchargesList()) {
            if (ovr.getText().contains(c3HotelSupplyInfo.getFullHotelName())) {
                isOverchargeDisplayed = true;
                break;
            }
            else {
                isOverchargeDisplayed = false;
            }
        }
        if (isOverchargeDisplayedInList) {
            assertThat(isOverchargeDisplayed).as("Overcharge should be displayed in the list of unassigned " +
                    "overcharges but it is not displayed there").isTrue();
        }
        else {
            assertThat(isOverchargeDisplayed).as("Overcharge should not be displayed in the list of unassigned " +
                    "overcharges but it is displayed there").isFalse();
        }
    }

    private void populateSearchPeriod(String period, C3SearchOverchargesPage searchOverchargesPage) {
        int daysToSearchFrom;
        if (period.matches(".*days$")) {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(period);
            matcher.find();
            String daysFrom = matcher.group();
            daysToSearchFrom = Integer.parseInt(daysFrom);
            searchOverchargesPage.selectSearchPeriodWithDatepicker(daysToSearchFrom);
            c3OverchargesInfo.setSearchPeriod(daysToSearchFrom);
        }
        else {
            searchOverchargesPage.selectSearchPeriod(period);
            daysToSearchFrom = transformStringDateToDays(period);
            c3OverchargesInfo.setSearchPeriod(daysToSearchFrom);
        }
    }

    private void populateAssignee(String assignee, C3SearchOverchargesPage searchOverchargesPage) {
        if (assignee.isEmpty()) {
            searchOverchargesPage.clickSearchForUnassigned();
        }
        else {
            searchOverchargesPage.typeAssigneeName(assignee);
            c3OverchargesInfo.setAssignee(assignee);
        }
    }


    public void sortFieldByOrder() {
        C3OverchargesList overchargesList = new C3OverchargesList(getWebdriverInstance());
        overchargesList.setSortOrder(c3OverchargesInfo.getOverchargeColumnString());
    }
    public List<Integer> getFieldDigits(List<WebElement> elements) {
        List<Integer> values = new ArrayList<>();
        for (WebElement e : elements) {
            values.add(Integer.valueOf(e.getText()));
        }
        return values;
    }

    public List<String> getFieldStrings(List<WebElement> elements) {
        List<String> values = new ArrayList<>();
        for (WebElement e : elements) {
            values.add(e.getText());
        }
        return values;
    }

    public List<Double> getFieldDollars(List<WebElement> elements) {
        List<Double> values = new ArrayList<>();
        for (WebElement e : elements) {
            values.add(new Double(e.getText().replaceAll("[$,]", "")));
        }
        return values;
    }

    public List getColumnValues(List<WebElement> elements) {
        switch (c3OverchargesInfo.getOverchargeColumn()) {
            case OVERCHARGE_COUNT:
                return getFieldDigits(elements);
            case TOTAL_OVERCHARGES:
                return getFieldDollars(elements);
            default:
                return getFieldStrings(elements);
        }
    }

    public void verifySortingOrder() {
        String columnName = c3OverchargesInfo.getOverchargeColumnString();
        assertThat(getColumnValues(getOverchargeColumn(columnName)))
                .isEqualTo(sortList(getColumnValues(getOverchargeColumn(columnName))));
    }

    public List<WebElement> getOverchargeColumn(String columnName) {
        final C3OverchargesList c3OverchargesList = new C3OverchargesList(getWebdriverInstance());
        switch (c3OverchargesInfo.getOverchargeColumn()) {
            case HOTEL_NAME:
                return c3OverchargesList.getHotelNameColumn();
            case TOTAL_OVERCHARGES:
                return c3OverchargesList.getTotalOverchargesColumn();
            default:
                return c3OverchargesList.getColumn(columnName);
        }
    }

    private List sortList(List list) {
        if (c3OverchargesInfo.isSorting()) {
            Collections.sort(list);
        }
        else {
            Collections.sort(list, Collections.reverseOrder());
        }
        return list;
    }



    public void verifySearchResult() {
        assertThat(new C3OverchargesList(getWebdriverInstance()).getOverchargesList().size())
                .isGreaterThan(0).as("There should be more than one overchages row");
    }


    public void setupOverchargeHotel(Boolean withOvercharges) {
        String hotelId;
        if (withOvercharges) {
            hotelId = new C3HotelOverchargesDao(databaseSupport).getOverchargesHotel();
        }
        else {
            hotelId = new C3HotelOverchargesDao(databaseSupport).getHotelWithoutOvercharges();
        }
        c3HotelSupplyInfo.setHotelID(hotelId);
        LOGGER.info("Hotel ID: " + c3HotelSupplyInfo.getHotelID());
    }


    public void openOverchargeDetails() {
        String url = new C3HotelHomePage(getWebdriverInstance()).getOverchargeDetailsURL();
        assertThat(url).as("Verify overcharges report URL").contains("overchargesReport");
        getWebdriverInstance().get(url);
    }


    public void verifyOverchargesReport() {
        C3OverchargesReportPage report = new C3OverchargesReportPage(getWebdriverInstance());
        report.verifyReport();
        assertThat(report.getSendButtonsNum()).isEqualTo(4);
    }


    public void verifyOverchargeResults() {
        c3OverchargesInfo.setOverchargeColumn("Overcharge Count");
        assertThat(getC3OverchargesResultsResults()).isNotEmpty();
    }

    private List<WebElement> getC3OverchargesResultsResults() {
        C3OverchargesList page = new C3OverchargesList(getWebdriverInstance());
        return page.getColumn(c3OverchargesInfo.getOverchargeColumnString());
    }

    private int transformStringDateToDays(String searchPeriod) {
        int dateFrom;
        if ("last week".equalsIgnoreCase(searchPeriod)) {
            dateFrom = 7;
        }
        else if ("last 2 weeks".equalsIgnoreCase(searchPeriod)) {
            dateFrom = 14;
        }
        else if ("last 6 months".equalsIgnoreCase(searchPeriod)) {
            LocalDate date1 = new LocalDate().minusMonths(6);
            LocalDate date2 = new LocalDate();
            dateFrom = Days.daysBetween(date1, date2).getDays();
        }
        else {
            LocalDate date1 = new LocalDate().minusMonths(12);
            LocalDate date2 = new LocalDate();
            dateFrom = Days.daysBetween(date1, date2).getDays();
        }
        return dateFrom;
    }

    public void updateOvercharges() {
        C3OverchargesList overchargesList = new C3OverchargesList(getWebdriverInstance());
        c3HotelSupplyInfo.setFullHotelName(overchargesList.getFullHotelName());
        c3OverchargesInfo.setAssignee("csrcroz9");
        assignHSR();
    }

    private void assignHSR() {
        C3OverchargesList overchargesList = new C3OverchargesList(getWebdriverInstance());
        overchargesList.chooseHSR();
        overchargesList.clickUpdate();
    }

    public void openAnyOverchargeDetails() {
        C3OverchargesList overchargesList = new C3OverchargesList(getWebdriverInstance());
        overchargesList.navigateToAnyOverchargeDetails();
    }

    public void openHotelWithMinimumOvercharges() {
        C3OverchargesList overchargesList = new C3OverchargesList(getWebdriverInstance());
        overchargesList.openHotelWithMinimumOvercharges();
    }

    public void returnBackToResultsFromDetails() {
        C3HotelOverchargeInfoPage hotelOverchargeInfo = new C3HotelOverchargeInfoPage(getWebdriverInstance());
        hotelOverchargeInfo.clickBackToResultsLink();
    }

    public void unAsssignAll() {
        new C3OverchargesList(getWebdriverInstance()).unAsssignAll();
    }

    public void verifyHotelOverchargeInfoPage() {
        new C3HotelOverchargeInfoPage(getWebdriverInstance());
    }

    public void verifyOverchargesNum(Integer number) {
        Assertions.assertThat(new C3HotelOverchargeInfoPage(getWebdriverInstance()).getOverchargeResultsNum())
                .isLessThanOrEqualTo(number);
    }

    public void clickOnOverchargeAmount() {
        C3HotelOverchargeInfoPage c3HotelOverchargeInfoPage = new C3HotelOverchargeInfoPage(getWebdriverInstance());
        c3OverchargesInfo.setAmountCharged(c3HotelOverchargeInfoPage.getOverchargeAmount());
        c3ItineraryInfo.setItineraryNumber(c3HotelOverchargeInfoPage.getItineraryOfFirstRow());
        c3HotelOverchargeInfoPage.clickOnOverchargeAmount();
    }

    public void verifyOverchargeAmountPopUp() {
        C3OverchargeAmountPopUp c3OverchargeAmountPopUp = new C3OverchargeAmountPopUp(getWebdriverInstance());
        Assertions.assertThat(c3OverchargeAmountPopUp.getCTANumber())
                .isEqualTo("CTA" + new C3HotelOverchargesDao(databaseSupport)
                        .getCTANumber(c3ItineraryInfo.getItineraryNumber()));
//        Assertions.assertThat(c3OverchargeAmountPopUp.isChargesBlockDisplayed()).isTrue();
        Assertions.assertThat(c3OverchargeAmountPopUp.getChargeAmount())
                .isEqualTo(c3OverchargesInfo.getAmountCharged());
    }

    public void changeOverchargeStatus() {
        C3HotelOverchargeInfoPage c3HotelOverchargeInfoPage = new C3HotelOverchargeInfoPage(getWebdriverInstance());
        c3OverchargesInfo.setOverchargeStatus(c3HotelOverchargeInfoPage.changeFirstOverchargeStatus());
        c3ItineraryInfo.setItineraryNumber(c3HotelOverchargeInfoPage.getItineraryOfFirstRow());
    }

    public void submitChangedStatus() {
        logSession("Changing overcharge status");
        new C3HotelOverchargeInfoPage(getWebdriverInstance()).submit();
    }

    public void verifyChangedOverchargeVisibility(boolean visible) {
        boolean visibility = new C3HotelOverchargeInfoPage(getWebdriverInstance())
                .isOverchargeDisplayed(c3ItineraryInfo.getItineraryNumber());
        if (visible) {
            Assertions.assertThat(visibility).isTrue();
        }
        else {
            Assertions.assertThat(visibility).isFalse();
        }
    }

    public void showOverchargeHistory() {
        new C3HotelOverchargeInfoPage(getWebdriverInstance()).clickOnShowHistory();
    }

    public void verifyChangedOverchargeInNotes() {
        Assertions.assertThat(new C3HotelOverchargeInfoPage(getWebdriverInstance()).getNotes())
                .contains(c3ItineraryInfo.getItineraryNumber())
                .contains(c3OverchargesInfo.getOverchargeStatus());
    }

    public void fixSupplierContactInformation() {
        C3HotelOverchargeInfoPage c3HotelOverchargeInfoPage = new C3HotelOverchargeInfoPage(getWebdriverInstance());
        c3HotelOverchargeInfoPage.setTitle("TestManager");
        c3HotelOverchargeInfoPage.setSupplierEmail("test@gmail.com");
        c3HotelOverchargeInfoPage.setSupplierPhone("(415) 333-1111");
        c3HotelOverchargeInfoPage.setSupplierFax("(415) 333-1111");
    }

    public void clickOnUnassigned() {
        new C3SearchOverchargesPage(getWebdriverInstance()).checkUnassigned();
    }

    public void fillHSR(String hsr) {
        new C3SearchOverchargesPage(getWebdriverInstance()).fillHsr(hsr);
    }

    public void setOverchargeHotelNamePart() {
        Map hotelAndState = new C3HotelOverchargesDao(getDataBaseConnection()).getOverchargeHotelWithState();
        c3OverchargesInfo.setHotelName(String.valueOf(hotelAndState.get("HOTEL_NAME")));
        c3OverchargesInfo.setHotelState(String.valueOf(hotelAndState.get("STATE_CODE")));
        String partHotelName = c3OverchargesInfo.getHotelName().substring(0, 3);
        new C3SearchOverchargesPage(getWebdriverInstance()).setHotel(partHotelName);
    }

    public void setState(String state) {
        new C3SearchOverchargesPage(getWebdriverInstance()).setState(state);
    }

    public void verifyHotelName(String hotelName) {
        for (WebElement webElement : new C3OverchargesList(getWebdriverInstance()).getHotelNameColumn()) {
            Assertions.assertThat(webElement.getText().toLowerCase().contains(hotelName.toLowerCase()));
        }
    }

    public void clickOnSearchForOverchargesInfo() {
        new C3OverchargesList(getWebdriverInstance()).clickOnSearchForOverchargesInfo();
    }

    public void checkUnassignedOverchargesFromDB() {
        List<Map<String, Object>>  actualDB = new C3HotelOverchargesDao(databaseSupport).getUnAssignedOvercharges();
        Map<String, Object>  actualUI = new HashMap<>();
        int size = actualDB.size();
        int j = 0; int i = 0;
        C3OverchargesList resultList = new C3OverchargesList(getWebdriverInstance());
        //resultList.sortByOverchargesTotal();
        for (i = 0; i < size; i++) {
            actualUI = resultList.getOverchargesRow(i);
            assertThat(Double.valueOf(actualUI.get("OVERCHARGE_TOTAL").toString().replace(",", "")))
                        .as("Comparing OVERCHARGE_TOTAL with rounding issue handling. i=" + j)
                        .isEqualTo(Double.valueOf(actualDB.get(j)
                                .get("OVERCHARGE_TOTAL").toString()), Delta.delta(0.1));

            assertThat(actualUI.get("HOTEL_NAME"))
                    .as("HOTEL_NAME is incorrect")
                    .isEqualTo(actualDB.get(j).get("HOTEL_NAME").toString().split(" \\(")[0]);


            assertThat(Double.valueOf(actualUI.get("OVERCHARGE_COUNT").toString().replace(",", "")))
                    .as("Comparing OVERCHARGE_COUNT with rounding issue handling. i=" + j)
                    .isEqualTo(Double.valueOf(actualDB.get(j).get("OVERCHARGE_COUNT").toString()), Delta.delta(0.1));

            assertThat(actualUI.get("OVERCHARGE_DATE_MODIFIED"))
                    .as("OVERCHARGE_DATE_MODIFIED is incorrect")
                    .isEqualTo(actualDB.get(j).get("OVERCHARGE_DATE_MODIFIED"));
            assertThat(actualUI.get("LAST_OVERCHARGE_DATE"))
                    .as("LAST_OVERCHARGE_DATE is incorrect")
                    .isEqualTo(actualDB.get(j).get("LAST_OVERCHARGE_DATE"));
            if ((i > 0) && (i % 24 == 0)) {
                resultList.clickNextPage();
                i = -1;
                size -= 25;
            }
            j++;
        }
    }

    public void updateAnyOverchargeItinerary() {
        C3HotelOverchargeInfoPage hotelOverchargeInfo = new C3HotelOverchargeInfoPage(getWebdriverInstance());
        c3HotelSupplyInfo.setFullHotelName(hotelOverchargeInfo.getFullHotelName());
        c3ItineraryInfo.setItineraryNumber(hotelOverchargeInfo.getItineraryOfFirstRow()); //set first
        hotelOverchargeInfo.updateFirstItineraryStatus();
        String expectedMessage  = "Itinerary[" + c3ItineraryInfo.getItineraryNumber() + "]" +
                " Credit Pending -> Write Off - Exchange Rate";
        assertThat(verifyTextOnPageBoolean(expectedMessage)).as(expectedMessage).isTrue();
    }

    public void checkOverchargesItineraryUpdateInDB() {
        String actualDB = new C3HotelOverchargesDao(databaseSupport).
                getOverchargesItineraryUpdateInDB(c3HotelSupplyInfo.getFullHotelName());
        String expectedValue = "Overcharges statuses are changed: <br/>Itinerary[" +
                c3ItineraryInfo.getItineraryNumber() + "] Credit Pending -> Write Off - Exchange Rate<br/> ";
        assertThat(actualDB).isEqualTo(expectedValue);
    }
}



