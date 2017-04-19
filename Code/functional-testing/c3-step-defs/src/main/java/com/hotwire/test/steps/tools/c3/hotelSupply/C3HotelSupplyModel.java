/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.hotelSupply;

import com.hotwire.selenium.tools.c3.C3IndexPage;
import com.hotwire.selenium.tools.c3.casenotes.C3CaseNotesFrame;
import com.hotwire.selenium.tools.c3.casenotes.C3CaseNotesResultsPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3HotelItineraryPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelActivationForm;
import com.hotwire.selenium.tools.c3.hotel.C3HotelAmenitiesFragment;
import com.hotwire.selenium.tools.c3.hotel.C3HotelCaseHistoryPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelFinanceSearchPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelHomePage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelReportsDeliveryPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelReservationDetailsPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelRetrieveGuestFinanceResults;
import com.hotwire.selenium.tools.c3.hotel.C3HotelRetrieveGuestReservationsPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelSupplierDetailsFragment;
import com.hotwire.selenium.tools.c3.hotel.C3HotelSupplierSearchResults;
import com.hotwire.selenium.tools.c3.hotel.C3HotelWorkflowPage;
import com.hotwire.selenium.tools.c3.hotel.C3OversellWindow;
import com.hotwire.selenium.tools.c3.hotel.C3SearchForHotelPage;
import com.hotwire.selenium.tools.c3.hotel.oversells.C3HotelOversellFragment;
import com.hotwire.selenium.tools.c3.hotel.oversells.C3HotelRetrieveGuestReservationsResults;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.ProductVertical;
import com.hotwire.test.steps.tools.bean.c3.C3HotelSupplyInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3HotelSupplyDao;
import com.hotwire.util.db.c3.C3RefundRecoveryCustomerDao;
import cucumber.api.PendingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.fest.assertions.Assertions;
import org.fest.assertions.Delta;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 5/5/14
 * Time: 1:36 PM
 * The model for hotel supply steps and page objects.
 */
public class C3HotelSupplyModel extends ToolsAbstractModel {
    private static String HOTEL_ID_COLUMN = "hotel_id";
    private String value = null;

    @Autowired
    private C3HotelSupplyInfo c3HotelSupplyInfo;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private SimpleJdbcDaoSupport databaseSupport;

    public void searchByHotelID(String id) {
        new C3IndexPage(getWebdriverInstance()).clickSearchForAHotel();
        new C3SearchForHotelPage(getWebdriverInstance()).searchForHotelID(id);
    }

    public void setUpHotelWithOversells() {
        Map oversells = new C3HotelSupplyDao(getDataBaseConnection()).getHotelWithOversells();
        c3HotelSupplyInfo.setHotelID(oversells.get(HOTEL_ID_COLUMN).toString());
        c3HotelSupplyInfo.setBookingStartDate(new DateTime(oversells.get("start_date")));
        logHotel();
    }

    public void getHotelFromDataBase() {
        Map hotelInfo = new C3HotelSupplyDao(getDataBaseConnection()).getSomeHotel();
        c3HotelSupplyInfo.setHotelID(hotelInfo.get(HOTEL_ID_COLUMN).toString());
        c3HotelSupplyInfo.setFullHotelName(hotelInfo.get("hotel_name").toString());
        c3HotelSupplyInfo.setHotelAddress(hotelInfo.get("address_1").toString());
        c3HotelSupplyInfo.setHotelPhoneNumber(hotelInfo.get("hotel_phone").toString());
        c3HotelSupplyInfo.setHotelFaxNumber(hotelInfo.get("contact_fax").toString());
        c3HotelSupplyInfo.setHotelState(hotelInfo.get("state_name").toString());
        c3HotelSupplyInfo.setHotelCity(hotelInfo.get("city_name").toString());
        c3HotelSupplyInfo.setHotelZipCode(hotelInfo.get("address_zip").toString());
        c3HotelSupplyInfo.setHotelCountry(hotelInfo.get("country_name").toString());
        logHotel();
    }

    public void deleteHotelReportsConfigExceptionFromDB(String hotelID) {
        c3HotelSupplyInfo.setHotelID(hotelID);
        new C3HotelSupplyDao(getDataBaseConnection()).deleteHotelReportsConfigException(hotelID);
    }

    public void getHotelIDFromDataBase() {
        c3HotelSupplyInfo.setHotelID(new C3HotelSupplyDao(getDataBaseConnection()).getSomeHotelID());
        logHotel();
    }

    public void searchForHotelOversells() {
        new C3HotelHomePage(getWebdriverInstance()).clickOnCommonTask("Oversells");
        new C3HotelOversellFragment(getWebdriverInstance())
                .searchOversells(c3HotelSupplyInfo.getStartDate().minusDays(1).toDate(),
                        c3HotelSupplyInfo.getStartDate().plusDays(1).toDate());
    }

    public void searchForHotelOversellsBetweenDates(int shiftDaysIn, int shiftDaysOut) {
        new C3HotelOversellFragment(getWebdriverInstance())
                .searchOversells(new DateTime().plusDays(shiftDaysIn).toDate(),
                        new DateTime().plusDays(shiftDaysOut).toDate());
    }


    public void clickSaveDocument() {
        new C3OversellWindow(getWebdriverInstance()).selectSomeOversell().btnSaveDocumentClick();
    }


    public void clickCreateWorkflow() {
        value = "oversell" + System.currentTimeMillis();
        new C3OversellWindow(getWebdriverInstance()).selectSomeOversell().btnCreateWorkflowClick(value);
    }


    public void verifyOversellErrorMessage(String value) {
        logSession("Confirmation message verification");
        Assertions.assertThat(new C3HotelOversellFragment(getWebdriverInstance())
                .getConfirmationText()).contains(value);
    }


    public void verifyHotelCaseNotes() {
        new C3HotelHomePage(getWebdriverInstance()).clickOnCommonTask("Hotel Case History");
        Assertions.assertThat(new C3HotelCaseHistoryPage(getWebdriverInstance()).getCaseNotesDescription())
                .as("Oversell case notes is not present")
                .contains(value);
    }

    public void verifyAbilityToDeactivateHotel(boolean access) {
        C3HotelHomePage hotelPage = new C3HotelHomePage(getWebdriverInstance());
        if (access) {
            assertThat(hotelPage.isHotelDeactivateLinkDisplayed()).isTrue();
            hotelPage.clickOnDeactivateLink();
            assertThat(hotelPage.isDeactivateHotelBtnDisplayed()).isTrue();
        }
        else {
            assertThat(hotelPage.isHotelDeactivateLinkDisplayed()).isFalse();
            assertThat(hotelPage.isHotelActivationFakeLinkDisplayed()).isTrue();
        }
    }

    public void deactivateHotel() {
        C3HotelHomePage c3HotelCaseHistoryPage = new C3HotelHomePage(getWebdriverInstance());
        c3HotelCaseHistoryPage.clickOnDeactivateLink();
        c3HotelCaseHistoryPage.clickOnDeactivateBtn();
    }

    public void reactivateHotel() {
        C3HotelHomePage c3HotelCaseHistoryPage = new C3HotelHomePage(getWebdriverInstance());
        c3HotelCaseHistoryPage.clickOnReactivateBtn();
    }

    public void selectHotelOnSearchResults() {
        C3HotelSupplierSearchResults hotelSearchResults = new C3HotelSupplierSearchResults(getWebdriverInstance());
        hotelSearchResults.selectHotel(c3HotelSupplyInfo.getFullHotelName());
    }


    public void getHotelWithUniquePhoneNum() {
        Map hotel = new C3HotelSupplyDao(getDataBaseConnection()).getHotelWithUniquePhoneNum();
        c3HotelSupplyInfo.setHotelID(hotel.get(HOTEL_ID_COLUMN).toString());
        c3HotelSupplyInfo.setFullHotelName(hotel.get("hotel_name").toString());
        c3HotelSupplyInfo.setHotelPhoneNumber(hotel.get("hotel_phone").toString());
        c3HotelSupplyInfo.setHotelFaxNumber(hotel.get("contact_fax").toString());
        c3HotelSupplyInfo.setHotelAddress(hotel.get("address_1").toString());
        logHotel();
    }

    public void proceedToAmenitiesPage() {
        new C3HotelHomePage(getWebdriverInstance()).clickAmenities();
    }


    public void operateWithAmenities(String action, String amenityType) {
        c3HotelSupplyInfo.setAction(action);
        if (amenityType.equals("Major")) {
            c3HotelSupplyInfo.setMajorAmenity(true);
        }
        else {
            c3HotelSupplyInfo.setMajorAmenity(false);
        }
        String amenityName = findAmenityInDB();
        new C3HotelAmenitiesFragment(getWebdriverInstance()).clickAmenity(amenityName);
        c3HotelSupplyInfo.setAmenities(amenityName);
    }

    public String findAmenityInDB() {
        final C3HotelSupplyDao c3HotelSupplyDao = new C3HotelSupplyDao(getDataBaseConnection());
        switch (c3HotelSupplyInfo.getAction()) {
            case ADD :
                if (c3HotelSupplyInfo.isMajorAmenity()) {
                    return c3HotelSupplyDao.getMajorAmenityForAdd(c3HotelSupplyInfo.getHotelID());
                }
                else {
                    return c3HotelSupplyDao.getMinorAmenityForAdd(c3HotelSupplyInfo.getHotelID());
                }
            case REMOVE :
                if (c3HotelSupplyInfo.isMajorAmenity()) {
                    return c3HotelSupplyDao.getMajorAmenityForRemoval(c3HotelSupplyInfo.getHotelID());
                }
                else {
                    return c3HotelSupplyDao.getMinorAmenityForRemoval(c3HotelSupplyInfo.getHotelID());
                }
            default:
                throw new UnimplementedTestException("No such action");
        }
    }

    public void saveChangesForAmenities() {
        new C3HotelAmenitiesFragment(getWebdriverInstance()).saveChanges();
    }


    public void checkSaveAmenitiesConfirmation(String message) {
        Assertions.assertThat(new C3HotelAmenitiesFragment(getWebdriverInstance()).getConfirmationMessage()).
                as("Confirmation message isn't present").
                contains(message);
    }


    public void checkCaseNotesText() {
        new C3HotelHomePage(getWebdriverInstance()).clickOnCommonTask("Hotel Case History");
        C3CaseNotesResultsPage resultPage =
                new C3CaseNotesResultsPage(getWebdriverInstance(), By.cssSelector("div.caseHistory"));
        for (String s : c3HotelSupplyInfo.getAmenities()) {
            Assertions.assertThat(resultPage.getCaseNotesDescription())
                    .as("Amenities case notes isn't present")
                    .contains(s);
        }
    }


    public void verifyMessageInCommonTasks(String msg) {
        C3HotelHomePage hotelPage = new C3HotelHomePage(getWebdriverInstance());
        List<WebElement> tasks = hotelPage.getCommonTasksMessages();
        Boolean test = false;
        for (WebElement task : tasks) {
            if (msg.equals(task.getText())) {
                test = true;
            }
        }
        Assertions.assertThat(test).as("Message should be in common tasks").isTrue();
    }


    public void changeStatusBunchOfAmenities() {
        operateWithAmenities("add", "Minor");
        operateWithAmenities("add", "Major");
        operateWithAmenities("remove", "Minor");
        operateWithAmenities("remove", "Major");
    }

    public void verifyHotelSupplierInfoWithDB() {
        C3HotelSupplierDetailsFragment hotelSupDetFragment = new C3HotelSupplierDetailsFragment(getWebdriverInstance());
        Assertions.assertThat(hotelSupDetFragment.getHotelId())
                .contains(c3HotelSupplyInfo.getHotelID());
        Assertions.assertThat(hotelSupDetFragment.getHotelName())
                .contains(c3HotelSupplyInfo.getFullHotelName());
        Assertions.assertThat(hotelSupDetFragment.getHotelPhoneNumber())
                .contains(c3HotelSupplyInfo.getHotelPhoneNumber());
        Assertions.assertThat(hotelSupDetFragment.getHotelFaxNumber())
                .contains(c3HotelSupplyInfo.getHotelFaxNumber());
        Assertions.assertThat(hotelSupDetFragment.getHotelAddress())
                .contains(c3HotelSupplyInfo.getHotelAddress());
    }

    public void verifyLinkInCommonTasks(boolean availability, String link) {
        C3HotelHomePage hotelPage = new C3HotelHomePage(getWebdriverInstance());
        List<WebElement> tasks = hotelPage.getCommonTasksLinks();
        Boolean test = false;
        for (WebElement task : tasks) {
            if (link.equals(task.getText())) {
                test = true;
            }
        }
        if (availability) {
            Assertions.assertThat(test).as("Link should be in common tasks").isTrue();
        }
        else {
            Assertions.assertThat(test).as("Link should NOT be in common tasks").isFalse();
        }
    }

    public void verifyErrorMessage(String message) {
        Assertions.assertThat(new C3SearchForHotelPage(getWebdriverInstance()).getErrorMessage()).isEqualTo(message);
    }

    public void searchHotelByNameAndAddress(String addressPart) {
        new C3IndexPage(getWebdriverInstance()).clickSearchForAHotel();
        C3SearchForHotelPage hotelSearchPage = new C3SearchForHotelPage(getWebdriverInstance());
        hotelSearchPage.enterHotelName(c3HotelSupplyInfo.getFullHotelName());
        if (addressPart.equals("state")) {
            hotelSearchPage.selectHotelState(c3HotelSupplyInfo.getHotelState());
        }
        else if (addressPart.equals("country")) {
            hotelSearchPage.selectCountry(c3HotelSupplyInfo.getHotelCountry());
        }
        else if (addressPart.equals("address")) {
            hotelSearchPage.enterAddress(c3HotelSupplyInfo.getHotelAddress());
        }
        else if (addressPart.equals("zip code")) {
            hotelSearchPage.enterZipCode(c3HotelSupplyInfo.getHotelZipCode());
        }
        hotelSearchPage.clickSearchBtn();
    }

    public void searchByNameOnly() {
        new C3IndexPage(getWebdriverInstance()).clickSearchForAHotel();
        C3SearchForHotelPage hotelSearchPage = new C3SearchForHotelPage(getWebdriverInstance());
        hotelSearchPage.enterHotelName(c3HotelSupplyInfo.getFullHotelName());
        hotelSearchPage.clickSearchBtn();
    }

    public void searchHotelByAddressPart(String addressPart) {
        new C3IndexPage(getWebdriverInstance()).clickSearchForAHotel();
        C3SearchForHotelPage hotelSearchPage = new C3SearchForHotelPage(getWebdriverInstance());
        if (addressPart.equals("state")) {
            hotelSearchPage.selectHotelState(c3HotelSupplyInfo.getHotelState());
        }
        else if (addressPart.equals("country")) {
            hotelSearchPage.selectCountry(c3HotelSupplyInfo.getHotelCountry());
        }
        else if (addressPart.equals("address")) {
            hotelSearchPage.enterAddress(c3HotelSupplyInfo.getHotelAddress());
        }
        else if (addressPart.equals("zip code")) {
            hotelSearchPage.enterZipCode(c3HotelSupplyInfo.getHotelZipCode());
        }
        hotelSearchPage.clickSearchBtn();
    }

    public void getHotelWithIncorrectPhoneNum() {
        c3HotelSupplyInfo.setHotelPhoneNumber("1@3tert345");
    }

    public void searchHotelByPhoneNumber() {
        new C3IndexPage(getWebdriverInstance()).clickSearchForAHotel();
        C3SearchForHotelPage hotelSearchPage = new C3SearchForHotelPage(getWebdriverInstance());
        hotelSearchPage.searchByPhoneNumber(c3HotelSupplyInfo.getHotelPhoneNumber());
    }

    public void searchHotelByPartOfHotelName() {
        new C3IndexPage(getWebdriverInstance()).clickSearchForAHotel();
        C3SearchForHotelPage hotelSearchPage = new C3SearchForHotelPage(getWebdriverInstance());
        hotelSearchPage.enterHotelName(c3HotelSupplyInfo.getFullHotelName().substring(0, 3));
        hotelSearchPage.selectCountry(c3HotelSupplyInfo.getHotelCountry());
        hotelSearchPage.clickSearchBtn();
    }

    public void fillHotelActivationForm() {
        C3HotelActivationForm c3HotelActivationForm = new C3HotelActivationForm(getWebdriverInstance());
        c3HotelActivationForm.selectReason();
        c3HotelActivationForm.setRequestedBy("Test");
        c3HotelActivationForm.setTitle("Test");
        c3HotelActivationForm.setPhoneNumber(customerInfo.getPhoneNumber());

    }

    public void submitHotelActivationForm() {
        new C3HotelActivationForm(getWebdriverInstance()).clickSubmit();
    }

    public void verifyHotelDeactivated() {
        logSession("Verify hotel Deactivated. HotelID:" + c3HotelSupplyInfo.getHotelID());
        assertThat(new C3HotelHomePage(getWebdriverInstance()).getConfirmationMsg())
                .isEqualTo("This hotel has been deactivated.");
    }


    public void verifyHotelReactivated() {
        assertThat(new C3HotelHomePage(getWebdriverInstance()).getConfirmationMsg())
                .isEqualTo("The hotel has been reactivated and will now appear on Hotwire.com.");
    }

    public void verifyValidationMessage() {
        assertThat(new C3HotelActivationForm(getWebdriverInstance()).getValidationMsg())
                .isEqualTo("Please select a Reason to deactivate the hotel.");
    }

    public void cancelHotelActivationForm() {
        new C3HotelActivationForm(getWebdriverInstance()).clickCancel();
    }

    public void verifyHotelIsActive() {
        assertThat(new C3HotelHomePage(getWebdriverInstance()).isDeactivateHotelBtnDisplayed()).isTrue();
    }

    public void selectHotelReportsDelivery() {
        C3HotelHomePage hotelPage = new C3HotelHomePage(getWebdriverInstance());
        hotelPage.getCommonTasksLinks();
        hotelPage.clickHotelReportsDeliveryLink();
    }

    public void tryToSendReportByInvalidFax() {
        new C3HotelReportsDeliveryPage(getWebdriverInstance()).sendFax("aaaaa");
        assertThat(verifyTextOnPageBoolean("Please enter valid fax number."))
                .as("Error text 'Please enter valid fax number.' not found").isTrue();
    }

    public void tryToSendReportByInvalidEmail() {
        new C3HotelReportsDeliveryPage(getWebdriverInstance()).sendEmail("abc123");
        assertThat(verifyTextOnPageBoolean("Please enter valid email address."))
                .as("Error text 'Please enter valid email address.' not found").isTrue();
    }

    public void openLinkInCommonTasks(String link) {
        new C3HotelHomePage(getWebdriverInstance()).clickOnCommonTask(link);
    }

    public void createWorkflowForHotel(String title) {
        String route = "Finance";
        String type = "Other";
        String severity = "Low";
        String name = "tester";
        String phone = "123456";

        new C3HotelWorkflowPage(getWebdriverInstance()).
                createWorkflow(route, type, severity, title, name, phone);

        assertThat(verifyTextOnPageBoolean("Your Workflow entry has been submitted."))
                .as("Your Workflow entry has been submitted. not found").isTrue();
    }

    public void checkWorkflowForHotel(String title) {
        String route = "Finance";
        String type = "Other";
        String severity = "Low";
        String name = "tester";
        String phone = "123456";

        C3CaseNotesFrame notes = new C3CaseNotesFrame(getWebdriverInstance());
        String noteText = notes.getWorkflowInfo();

        assertThat(noteText.contains("Workflow sent to: " + route))
                .as("Workflow sent to: not found or correct").isTrue();

        assertThat(noteText.contains("Reason: " + type))
                .as("Reason: not found or correct").isTrue();

        assertThat(noteText.contains("Severity: " + severity))
                .as("Severity: not found or correct").isTrue();

        assertThat(noteText.contains("Title: " + title))
                .as("Title: not found or correct").isTrue();

        assertThat(noteText.contains("Contact Name: " + name))
                .as("Contact Name: not found or correct").isTrue();

        assertThat(noteText.contains("Contact Phone Number: " + phone))
                .as("Contact Phone Number: not found or correct").isTrue();
    }

    public void selectHotelReservationsNearFuture(int shiftDaysIn, int shiftDaysOut) {
        //params sysdate + 1 and sysdate +10
        new C3HotelRetrieveGuestReservationsPage(getWebdriverInstance())
                .searchByDates(new DateTime().plusDays(shiftDaysIn).toDate(),
                        new DateTime().plusDays(shiftDaysOut).toDate());
    }

    public void checkHotelReservationsNearFuture(int shiftDaysIn, int shiftDaysOut) {
        String in = String.valueOf(shiftDaysIn);
        String out = String.valueOf(shiftDaysOut);
        List<Map<String, Object>> actualResults = new C3HotelRetrieveGuestReservationsResults(getWebdriverInstance())
                .getResultsTable();
        List<Map<String, Object>> dbResults = new C3HotelSupplyDao(getDataBaseConnection()).
                getRetrieveGuestReservationsResults(c3HotelSupplyInfo.getHotelID(), in, out);
        Integer actSize = actualResults.size();
        Integer dbSize = dbResults.size();

        assertThat(actSize.equals(dbSize)).as("actual count - " + actSize + " and db results count " + dbSize).isTrue();

        for (int i = 0; i < actSize; i++) {
            assertThat(actualResults.contains(dbResults.get(i)))
                    .as("i=" + i + " Next row from db not contains in list from UI - " +
                            dbResults.get(i)).isTrue();
        }
    }

    public void checkHotelFinanceNearFuture(int shiftDaysIn, int shiftDaysOut) {
        String in = String.valueOf(shiftDaysIn);
        String out = String.valueOf(shiftDaysOut);
        List<Map<String, Object>> actualResults = new C3HotelRetrieveGuestFinanceResults(getWebdriverInstance())
                .getResultsTable();
        List<Map<String, Object>> dbResults = new C3HotelSupplyDao(getDataBaseConnection()).
                getRetrieveGuestFinancesResults(c3HotelSupplyInfo.getHotelID(), in, out);
        Integer actSize = actualResults.size();
        Integer dbSize = dbResults.size();

        assertThat(actSize.equals(dbSize)).as("actual count - " + actSize + " and db results count " + dbSize).isTrue();

        for (int i = 0; i < actSize; i++) {
            assertThat(actualResults.contains(dbResults.get(i)))
                    .as("i=" + i + " Next row from db not contains in list from UI - " +
                            dbResults.get(i)).isTrue();
        }
    }

    public void getHotelIDWithReservations(int shiftDaysIn, int shiftDaysOut) {
        //params sysdate + 1 and sysdate +10
        String in = String.valueOf(shiftDaysIn);
        String out = String.valueOf(shiftDaysOut);
        String hotel_id = new C3HotelSupplyDao(getDataBaseConnection()).getHotelIDWithReservations(in, out);
        c3HotelSupplyInfo.setHotelID(hotel_id);
    }

    public void getHotelIDWithOversellsBetweenDates(int shiftDaysIn, int shiftDaysOut) {
        //params sysdate + 1 and sysdate +10
        String in = String.valueOf(shiftDaysIn);
        String out = String.valueOf(shiftDaysOut);
        String hotelID = new C3HotelSupplyDao(getDataBaseConnection()).
                getHotelIDWithOversellsBetweenDates(in, out);
        c3HotelSupplyInfo.setHotelID(hotelID);
    }

    public void checkHotelOversellsNearFuture(int shiftDaysIn, int shiftDaysOut) {
        String in = String.valueOf(shiftDaysIn);
        String out = String.valueOf(shiftDaysOut);
        List<Map<String, Object>> actualResults = new C3OversellWindow(getWebdriverInstance())
                .getResultsTable();
        List<Map<String, Object>> dbResults = new C3HotelSupplyDao(getDataBaseConnection()).
                getOversellResults(c3HotelSupplyInfo.getHotelID(), in, out);
        Integer actSize = actualResults.size();
        Integer dbSize = dbResults.size();

        assertThat(actSize.equals(dbSize)).as("actual count - " + actSize + " and db results count " + dbSize).isTrue();

        for (int i = 0; i < actSize; i++) {
            assertThat(actualResults.contains(dbResults.get(i)))
                    .as("i=" + i + " Next row from db not contains in list from UI - " +
                            dbResults.get(i)).isTrue();
        }
    }

    public void selectHotelFinanceNearFuture(int shiftDaysIn, int shiftDaysOut) {
        //params sysdate + 1 and sysdate +10
        new C3HotelFinanceSearchPage(getWebdriverInstance())
                .searchByDates(new DateTime().plusDays(shiftDaysIn).toDate(),
                        new DateTime().plusDays(shiftDaysOut).toDate());
    }

    public void getHotelID_DisplayID_4Purchase() {
        Map results = new C3HotelSupplyDao(getDataBaseConnection()).getHotelID_DisplayID_4Purchase();
        c3HotelSupplyInfo.setHotelID(results.get(HOTEL_ID_COLUMN).toString());
        c3ItineraryInfo.setItineraryNumber(results.get("display_number").toString());
        hotwireProduct.setProductVertical(ProductVertical.HOTEL);
    }

    public void selectHotelFinanceInformationByItinerary() {
        new C3HotelFinanceSearchPage(getWebdriverInstance())
                .searchByConfNum(c3ItineraryInfo.getItineraryNumber());
    }

    public void checkHotelReservationDetails() {
        Map<String, Object> actualResults = new C3HotelReservationDetailsPage(getWebdriverInstance()).
                getHotelReservationDetails();
        Map<String, Object> dbResults = new C3HotelSupplyDao(getDataBaseConnection()).
                getHotelReservationDetails(c3HotelSupplyInfo.getHotelID(), c3ItineraryInfo.getItineraryNumber());

        for (Map.Entry<String, Object> entry: actualResults.entrySet()) {
            if (entry.getKey().equals("Total Supplier Cost") || entry.getKey().equals("Charge to HW Card")) {
                assertThat(Double.valueOf(entry.getValue().toString()))
                        .as("Comparing amounts with rounding issue handling.")
                        .isEqualTo(Double.valueOf(dbResults.get(entry.getKey()).toString()), Delta.delta(0.1));
            }
            else {
                assertThat(dbResults.get(entry.getKey()).toString().toLowerCase()
                        .contains(entry.getValue().toString().toLowerCase()))
                        .as("key " + entry.getKey() + " value " + entry.getValue() +
                                " but DB value " + dbResults.get(entry.getKey())).isTrue();
            }
        }
    }

    public void clickManuallyBillCreditItineraryLink() {
        new C3HotelItineraryPage(getWebdriverInstance()).clickOnManuallyBillCreditItineraryLink();
    }

    public void createManualBilling() {
        String val = Integer.valueOf(RandomStringUtils.randomNumeric(2)).toString(); //catch 01->1 value
        c3HotelSupplyInfo.setManualBillingCreditAmount(val);
        new C3HotelItineraryPage(getWebdriverInstance()).createManualBilling(val);
    }

    public void createManualCredit() {
        String val = Integer.valueOf(RandomStringUtils.randomNumeric(2)).toString(); //catch 01->1 value
        c3HotelSupplyInfo.setManualBillingCreditAmount(val);
        new C3HotelItineraryPage(getWebdriverInstance()).createManualCredit(val);
    }

    public void checkManualBilling() {
        new C3HotelItineraryPage(getWebdriverInstance()).
                checkManualBilling(c3HotelSupplyInfo.getManualBillingCreditAmount());
    }

    public void checkManualCredit() {
        new C3HotelItineraryPage(getWebdriverInstance()).
                checkManualCredit(c3HotelSupplyInfo.getManualBillingCreditAmount());
    }

    public void checkC3AVSPurchase() {
        C3HotelItineraryPage itPage = new C3HotelItineraryPage(getWebdriverInstance());
        assertThat(itPage.isAVSDeclinedPurchase()).as("Purchase not AVS declined. itinerary - " +
                c3ItineraryInfo.getItineraryNumber()).isTrue();
    }

    public void checkResubmitCPVoption() {
        C3HotelItineraryPage itPage = new C3HotelItineraryPage(getWebdriverInstance());
        assertThat(itPage.isCPVResubmitOptionAvailable()).as("Purchase not AVS declined. itinerary - " +
                c3ItineraryInfo.getItineraryNumber()).isFalse();
    }

    public void verifyStatusAndAmountManualCredit() {
        Map map = new C3RefundRecoveryCustomerDao(databaseSupport)
                .getStatusCodeAndAmountForRefundedPurchase(c3ItineraryInfo.getItineraryNumber());

        BigDecimal refundC3 = new BigDecimal(c3HotelSupplyInfo.getManualBillingCreditAmount());
        BigDecimal refundDB = (BigDecimal) map.get("AMOUNT");
        BigDecimal status = new BigDecimal(30500);

        Float refundAmountDif = refundDB.subtract(refundC3).floatValue();

        assertThat(map.get("STATUS_CODE")).as("Status code is not equal to expected " + status).isEqualTo(status);
        assertThat(refundAmountDif).as("Refund amount in C3 and DB is not equal").isLessThanOrEqualTo(new Float(.1));
    }

    public void verifyStatusAndAmountManualBilling() {
        Map map = new C3RefundRecoveryCustomerDao(databaseSupport)
                .getStatusCodeAndAmountForRefundedPurchase(c3ItineraryInfo.getItineraryNumber());

        BigDecimal refundC3 = new BigDecimal(c3HotelSupplyInfo.getManualBillingCreditAmount());
        BigDecimal refundDB = (BigDecimal) map.get("AMOUNT");
        BigDecimal status = new BigDecimal(30400);

        Float refundAmountDif = refundDB.subtract(refundC3).floatValue();

        assertThat(map.get("STATUS_CODE")).as("Status code is not equal to expected " + status).isEqualTo(status);
        assertThat(refundAmountDif).as("Refund amount in C3 and DB is not equal").isLessThanOrEqualTo(new Float(.1));
    }

    public void updateHotelTaxRates() {
        new C3HotelFinanceSearchPage(getWebdriverInstance())
                .updateHotelTaxRates();
        String waitMessage = "The tax rate update request was sent to the hotel team.";
        assertThat(verifyTextOnPageBoolean(waitMessage)).
                as(waitMessage).isTrue();
    }

    public void uncheckHotelReportsDelivery(String reportType) {
        switch (reportType.toUpperCase()) {
            case "DAR":
                new C3HotelReportsDeliveryPage(getWebdriverInstance()).uncheckDARReport();
                break;
            case "DFR":
                new C3HotelReportsDeliveryPage(getWebdriverInstance()).uncheckDFRReport();
                break;
            case "CMR":
                new C3HotelReportsDeliveryPage(getWebdriverInstance()).uncheckCMRReport();
                break;
            case "MEBR":
                new C3HotelReportsDeliveryPage(getWebdriverInstance()).uncheckMEBRReport();
                break;
            default:
                throw new PendingException("Unknow hotel report type");
        }

    }

    public void verifyHotelReportsStatusInDB(String reportType) {
        Map reports = new C3HotelSupplyDao(getDataBaseConnection()).getHotelReports(c3HotelSupplyInfo.getHotelID());
        switch (reportType.toUpperCase()) {
            case "DAR":
                assertThat(reports.get("type")).isEqualTo("hotel");
                assertThat(reports.get("report_id")).isEqualTo("1");
                break;
            case "DFR":
                assertThat(reports.get("type")).isEqualTo("hotel");
                assertThat(reports.get("report_id")).isEqualTo("2");
                break;
            case "CMR":
                assertThat(reports.get("type")).isEqualTo("hotel");
                assertThat(reports.get("report_id")).isEqualTo("3");
                break;
            case "MEBR":
                assertThat(reports.get("type")).isEqualTo("hotel");
                assertThat(reports.get("report_id")).isEqualTo("4");
                break;
            default:
                throw new PendingException("Unknow hotel report type");
        }
    }
}


