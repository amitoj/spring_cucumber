/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.refund;

import com.hotwire.selenium.tools.c3.customer.C3CustomerInfoFragment;
import com.hotwire.selenium.tools.c3.customer.C3PastBookingPage;
import com.hotwire.selenium.tools.c3.customer.C3ShareItineraryForm;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3HotelItineraryPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3ItineraryDetailsPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelWorkflowPage;
import com.hotwire.selenium.tools.c3.refund.C3AirRefundPage;
import com.hotwire.selenium.tools.c3.refund.C3CarRefundPage;
import com.hotwire.selenium.tools.c3.refund.C3HotelRefundPage;
import com.hotwire.selenium.tools.c3.refund.C3RefundConfirmationPage;
import com.hotwire.selenium.tools.c3.refund.C3RefundDeterminationPage;
import com.hotwire.selenium.tools.c3.refund.C3RefundPage;
import com.hotwire.selenium.tools.c3.refund.C3ResponseWindow;
import com.hotwire.selenium.tools.c3.refund.C3ServiceRecoveryPage;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.ProductVertical;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3RefundRecoveryCustomerDao;
import cucumber.api.PendingException;
import org.fest.assertions.Assertions;
import org.fest.assertions.Delta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 */

public class C3RefundModel extends ToolsAbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3RefundModel.class);

    private static final String TEST_BOOKING = "Test Booking";
    private static final String MEDICAL = "Medical";
    private static final String DEATH = "Death";
    private static final String COURT_SUMMONS = "Court Summons or Jury Duty";
    private static final String MILITARY = "Military";
    private static final String MINOR_BOOKING = "Booking Made By Minor";
    private static final String WRONG_NAME = "Wrong Name";
    private boolean haveDocumentsForCourtSummons;
    private boolean isPurchaseInPending;
    private String currencyCode;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Autowired
    private SimpleJdbcDaoSupport databaseSupport;

    @Autowired
    private BillingInfo billingInfo;

    public void fillRecoveryReasons() {
        C3ServiceRecoveryPage serviceRecoveryPage = new C3ServiceRecoveryPage(getWebdriverInstance());
        String reason = c3ItineraryInfo.getRefundReason();

        if (this.isPurchaseInPending()) {
            checkPurchaseStatus("Pending Review");
        }
        else {
            serviceRecoveryPage.chooseRecoveryReason(reason);
            checkPurchaseStatus("Purchase Confirmed");
        }

        switch (reason) {
            case TEST_BOOKING:
                serviceRecoveryPage.answerTestBookingQuestions("Yes");
                break;
            case MEDICAL:
                serviceRecoveryPage.answerMedicalOrDeathQuestions("No");
                verifyTalkingPoints();
                break;
            case DEATH:
                serviceRecoveryPage.answerMedicalOrDeathQuestions("No");
                verifyTalkingPoints();
                break;
            case COURT_SUMMONS:
                serviceRecoveryPage.answerCourtSummonsQuestions(haveDocumentsForCourtSummons());
                verifyTalkingPoints();
                break;
            case MILITARY:
                serviceRecoveryPage.answerMilitaryQuestions();
                verifyTalkingPoints();
                break;
            case MINOR_BOOKING:
                serviceRecoveryPage.answerMinorBookingQuestions();
                verifyTalkingPoints();
                break;
            case WRONG_NAME:
                serviceRecoveryPage.answerWrongNameQuestions();
                break;
            default:
                throw new UnimplementedTestException("Test not implemented for this reason");
        }
    }

    public void fillTestBookingRecoveryMadeNotByHotwire() {
        C3ServiceRecoveryPage serviceRecoveryPage = new C3ServiceRecoveryPage(getWebdriverInstance());
        checkPurchaseStatus("Purchase Confirmed");
        serviceRecoveryPage.chooseRecoveryReason("Test Booking");
        serviceRecoveryPage.answerTestBookingQuestions("No");
        serviceRecoveryPage.clickContinueRefund();
    }

    public void chooseRecoveryReason() {
        C3ServiceRecoveryPage serviceRecoveryPage = new C3ServiceRecoveryPage(getWebdriverInstance());
        fillRecoveryReasons();
        serviceRecoveryPage.continueRefund();
        new C3RefundDeterminationPage(getWebdriverInstance()).clickRefund();
    }

    public void chooseDeathRecoveryReasonWithPossessionCertificateOfDeath() {
        C3ServiceRecoveryPage serviceRecoveryPage = new C3ServiceRecoveryPage(getWebdriverInstance());
        checkPurchaseStatus("Purchase Confirmed");
        String reason = c3ItineraryInfo.getRefundReason();
        serviceRecoveryPage.chooseRecoveryReason(reason);

        serviceRecoveryPage.answerDeathQuestionsWithPossessionCertificateOfDeath();
        verifyTalkingPoints();

        serviceRecoveryPage.continueRefund();
        new C3RefundDeterminationPage(getWebdriverInstance()).clickRefund();
    }

    public void chooseCreateWorkFlow() {
        C3ServiceRecoveryPage serviceRecoveryPage = new C3ServiceRecoveryPage(getWebdriverInstance());
        fillRecoveryReasons();
        serviceRecoveryPage.continueRefund();
        new C3RefundDeterminationPage(getWebdriverInstance()).clickCreateWorkFlow();
    }

    public void verifyTalkingPoints() {
        if (ProductVertical.HOTEL.equals(hotwireProduct.getProductVertical())) {
            Assertions.assertThat(new C3ServiceRecoveryPage(getWebdriverInstance()).getTalkingPoints())
                    .isEqualTo(c3ItineraryInfo.getTalkingPoints().get(c3ItineraryInfo.getRefundReason()));
        }
    }

    public void setRefundAmount() {
        String refundResponse = new C3RefundPage(getWebdriverInstance()).getRefundAmount();
        LOGGER.info("Refund amount: " + refundResponse);
        c3ItineraryInfo.setRefundAmount(refundResponse);
    }

    public void verifyFullRefundCheckboxUnchecked() {
        Assertions.assertThat(new C3RefundPage(getWebdriverInstance()).isFullRefundCheckBoxChecked())
                .as("Full refund checkbox must be unchecked by default!")
                .isFalse();
    }
    private void processingRefundSecondaryReason(String secondaryReason) {
        C3ResponseWindow responseWindow = new C3ResponseWindow(getWebdriverInstance());
        C3RefundPage refundPage = new C3RefundPage(getWebdriverInstance());
        switch (secondaryReason) {
            case TEST_BOOKING:
                responseWindow.confirmRefundPopUp(c3ItineraryInfo.getRefundAmount(), c3ItineraryInfo.getCurrencyCode());
                break;
            case WRONG_NAME:
                responseWindow.confirmRefundPopUp(c3ItineraryInfo.getRefundAmount(), c3ItineraryInfo.getCurrencyCode());
                break;
            default:
                if (hotwireProduct.getProductVertical().equals(ProductVertical.HOTEL)) {
                    String confirmationPoints = c3ItineraryInfo.getConfirmationPoints().get(secondaryReason);
                    refundPage.verifyConfirmationPoints(confirmationPoints);
                }
                refundPage.continueOperation();
        }
    }

    public void doFullRefundWithHotDollars(boolean withHotDollars) {
        C3RefundPage refundPage = new C3RefundPage(getWebdriverInstance());

        verifyFullRefundCheckboxUnchecked();
        refundPage.clickFullRefundCheckbox();
        if (withHotDollars) {
            refundPage.clickHotDollarsYes();
        }
        else {
            refundPage.clickHotDollarsNo();
        }

        c3ItineraryInfo.setRefundHotDollarAmount(
                Double.parseDouble(refundPage.getRefundedHotDollarsAmount().replaceAll("[$€]", "")));

        setRefundAmount();
        customerInfo.setEmail(new C3CustomerInfoFragment(getWebdriverInstance()).getEmail());
        refundPage.clickRefund();
        processingRefundSecondaryReason(c3ItineraryInfo.getRefundReason());
    }

    public void doFullRefund() {
        C3RefundPage refundPage = new C3RefundPage(getWebdriverInstance());

        if (!this.isPurchaseInPending()) {
            verifyFullRefundCheckboxUnchecked();
            refundPage.clickFullRefundCheckbox();
        }

        setRefundAmount();
        assertThat(c3ItineraryInfo.getRefundAmount()).as("Refund amount couldn't be a zero").isNotEqualTo(0.0);
        customerInfo.setEmail(new C3CustomerInfoFragment(getWebdriverInstance()).getEmail());
        refundPage.clickRefund();
        processingRefundSecondaryReason(c3ItineraryInfo.getRefundReason());
    }

    public void doPartialRefund() {
        C3RefundPage refundPage = new C3RefundPage(getWebdriverInstance());
        switch (hotwireProduct.getProductVertical()) {
            case AIR:
                refundPage = new C3AirRefundPage(getWebdriverInstance());
                break;
            case CAR:
                refundPage = new C3CarRefundPage(getWebdriverInstance());
                break;
            case HOTEL:
                refundPage = new C3HotelRefundPage(getWebdriverInstance());
                break;
            default:
                LOGGER.info("No such vertical!");
        }
        verifyFullRefundCheckboxUnchecked();
        refundPage.fillPartialRefundValues();
        setRefundAmount();
        refundPage.proceedPartialRefund();
    }


    public void verifyRefundConfirmation(Double expectedAmount) {
        String confirmationText = new C3RefundConfirmationPage(getWebdriverInstance()).getRefundConfirmationText();
//        assertThat(confirmationText).contains(customerInfo.getEmail());

        Double actualAmount = getRefundAmount(confirmationText);
        if (null == expectedAmount) {
            expectedAmount = c3ItineraryInfo.getRefundAmount();
        }

        assertThat(actualAmount)
                    .as("Comparing amounts with rounding issue handling.")
                    .isEqualTo(expectedAmount, Delta.delta(0.1));
        assertThat(currencyCode)
                .as("Amount currency verification")
                .isEqualTo(c3ItineraryInfo.getCurrencyCode());
    }

    public Double getRefundAmount(String inputText) {
        String amount = inputText.split("\n")[0]
                .replaceAll("^The customer has been refunded ", "")
                .replaceAll("^The partial refund of ", "")
                .replaceAll(" for Hotwire Itinerary.*$", "")
                .replace(",", "");
        currencyCode = c3ItineraryInfo.extractCurrency(amount);
        return c3ItineraryInfo.extractPrice(amount);
    }

    public void verifyPendingReviewConfirmation() {
        C3RefundConfirmationPage refundConfirmationPage = new C3RefundConfirmationPage(getWebdriverInstance());
        assertThat(refundConfirmationPage.getRefundConfirmationText())
                .as("Confirmation text should be ")
                .contains("An email was successfully sent to the customer telling them which documents to send us.");
    }

    public void openItineraryDetailsByClickOnLinkInBreadcrumbs() {
        getWebdriverInstance().switchTo().defaultContent().switchTo().frame("c3Frame");
        new C3RefundConfirmationPage(getWebdriverInstance()).openItineraryDetailsViaBreadcrumb();
    }

    public void doCancellation() {
        logItinerary();
        hotwireProduct.getItineraryPage(getWebdriverInstance()).doCancellation();
    }

    public void doResendBookingConfirmation() {
        C3ItineraryDetailsPage viewItineraryDetailsPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        viewItineraryDetailsPage.doResendBookingConfirmation();
    }

    public void verifyC3Message(String message) {
        C3ItineraryDetailsPage viewItineraryDetailsPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        assertThat(viewItineraryDetailsPage.getMessage())
                .as("Confirmation message doesn't match").matches("^.*" + message.replace("+", "\\+") + ".*$");
    }

    public void verifyPurchaseStatus(String status) {
        C3ItineraryDetailsPage viewItineraryDetailsPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        assertThat(viewItineraryDetailsPage.getStatus()).as("Verify Itinerary Status").startsWith(status);
    }

    public void viewLinkForCancelledItinerary() {
        assertThat(new C3PastBookingPage(getWebdriverInstance())
                                .isViewLinkExistsByItinerary(c3ItineraryInfo.getItineraryNumber()))
                .as("\"View\" link didn't appear for cancelled purchase " + c3ItineraryInfo.getItineraryNumber())
                .isTrue();
    }

    public void viewLinkIsAbsentForItinerary() {
        assertThat(new C3PastBookingPage(getWebdriverInstance())
                .isViewLinkExistsByItinerary(c3ItineraryInfo.getItineraryNumber()))
                .as("'View' link is visible for itinerary " + c3ItineraryInfo.getItineraryNumber())
                .isFalse();
    }

    /**
     * This method is needed to avoid concurrency conflicts.
     * @param status  - the status of purchase
     */
    public void checkPurchaseStatus(String status) {
        C3ItineraryDetailsPage viewItineraryDetailsPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        try {
            assertThat(viewItineraryDetailsPage.getStatus()).as("Verify Itinerary Status").startsWith(status);
            logItinerary();
        }
        catch (AssertionError e) {
            throw new PendingException("Concurrency conflict. Incorrect itinerary status.");
        }
    }

    public void verifyPurchaseAllowedRecoveryStatus(String status) {
        LOGGER.info("Allowed recovery status should be " + status);
        C3ItineraryDetailsPage viewItineraryDetailsPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        assertThat(viewItineraryDetailsPage.getAllowedRecoveryStatus())
                .as("Allowed recovery status").isEqualToIgnoringCase(status);
    }

    public void verifyShareItinerary(String text) {
        assertThat(text).as("Confirmation message doesn't match").
                contains(new C3ShareItineraryForm(getWebdriverInstance()).getConfirmationMessage());
    }

    public void chooseShareItinerary() {
        C3ItineraryDetailsPage viewItineraryDetailsPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        viewItineraryDetailsPage.clickOnShareItinerary();
    }

    public void cancelShareItinerary() {
        new C3ShareItineraryForm(getWebdriverInstance()).cancelShareItinerary();
    }

    public void clickConfirmShareItinerary() {
        logItinerary();
        new C3ShareItineraryForm(getWebdriverInstance()).clickConfirmShareItinerary();
    }

    public void checkShareItineraryWindowClosed(boolean state) {
        C3ItineraryDetailsPage viewItineraryDetailsPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        if (state) {
            assertThat(viewItineraryDetailsPage.isShareItineraryClosed()).
                    as("Share itinerary module is opened but should be closed").isTrue();
        }
        else {
            assertThat(viewItineraryDetailsPage.isShareItineraryClosed()).
                    as("Share itinerary module is closed but should be opened").isFalse();
        }
    }

    public void enterEmailForShareItinerary(boolean condition) {
        C3ShareItineraryForm form = new C3ShareItineraryForm(getWebdriverInstance());
        if (condition) {
            form.setEmail("test@hotwire.com,");
        }
        else {
            form.setEmail("test@@@hotwire.com,");
        }
    }

    public void verifyShareItineraryErrorMsg(String msg) {
        Assertions.assertThat(new C3ShareItineraryForm(getWebdriverInstance()).getMessage())
                .contains(msg);
    }

    public void fillShareItineraryForm() {
        C3ShareItineraryForm form = new C3ShareItineraryForm(getWebdriverInstance());
        form.setEmail("test@hotwire.com");
        form.setCustomerMessage();
    }

    public void verifyStatusCodeAndAmountForRefundedHotelPurchase() {
        Map map = new C3RefundRecoveryCustomerDao(databaseSupport)
                .getStatusCodeAndAmountForRefundedPurchase(c3ItineraryInfo.getItineraryNumber());

        BigDecimal refundC3 = new BigDecimal(c3ItineraryInfo.getRefundAmount());
        BigDecimal refundDB = (BigDecimal) map.get("AMOUNT");
        BigDecimal status = new BigDecimal(30500);

        Float refundAmountDif = refundDB.subtract(refundC3).floatValue();

        assertThat(map.get("STATUS_CODE")).as("Status code is not equal to expected " + status).isEqualTo(status);
        assertThat(refundAmountDif).as("Refund amount in C3 and DB is not equal").isLessThanOrEqualTo(new Float(.1));
    }

    public void verifyStatusCodeForVoidFullRefundedAirPurchase() {
        assertThat(new C3RefundRecoveryCustomerDao(databaseSupport).getStatusCode(c3ItineraryInfo.getItineraryNumber()))
                .as("Status code should be 30070 for itinerary: " + c3ItineraryInfo.getItineraryNumber())
                .isEqualTo("30070");
    }

    public void verifyStatusCodeAndAmountForPartiallyRefundedAirPurchase() {
        this.verifyStatusCodeAndAmountForRefundedHotelPurchase();
    }

    public void verifyStatusCodeForFullRefundedPurchase() {
        assertThat(new C3RefundRecoveryCustomerDao(databaseSupport).getStatusCode(c3ItineraryInfo.getItineraryNumber()))
                .as("Status code should be 30060 for itinerary: " + c3ItineraryInfo.getItineraryNumber())
                .isEqualTo("30060");
    }

    public void createWorkFlowDuringRefund() {
        String type = "Bug Report";
        String category = "Admin Site";
        String severity = "2 Bug";
        String testTitle = "Test_title_" + System.currentTimeMillis();

        C3HotelWorkflowPage c3HotelWorkflowPage = new C3HotelWorkflowPage(getWebdriverInstance());
        c3HotelWorkflowPage.createWorkflowBugReport(type, category, severity, testTitle);

        String expected = "Follow-up entry \\d{6,} was successfully submitted.";
        assertThat(Pattern.matches(expected, c3HotelWorkflowPage.getSuccessfulMsg()))
                .as("Actual - " + c3HotelWorkflowPage.getSuccessfulMsg() + ", but expected - " + expected).isTrue();
    }

    public void doPartialRefund(String segment, String passengers) {
        C3AirRefundPage refundPage = new C3AirRefundPage(getWebdriverInstance());
        //verifyFullRefundCheckboxUnchecked(); // Not necessary for part.refund. Block RTC-316
        refundPage.fillPartialRefundValues(segment, passengers);
        setRefundAmount();
        refundPage.clickRefund();
        refundPage.proceedPartialRefund();
    }

    public void confirmRefundResponsePopUpText(String text) {
        C3ResponseWindow responseWindow = new C3ResponseWindow(getWebdriverInstance());
        assertThat(responseWindow.getTextOfConfirmRefundPopUp())
                .as("Refund pop-up window does not contain expected text")
                .contains(text);
    }

    public void closeRefundResponsePopUp() {
        C3ResponseWindow responseWindow = new C3ResponseWindow(getWebdriverInstance());
        responseWindow.closeWindow();
    }

    public boolean haveDocumentsForCourtSummons() {
        return haveDocumentsForCourtSummons;
    }

    public void setDocumentsForCourtSummons(boolean haveDocumentsForCourtSummons) {
        this.haveDocumentsForCourtSummons = haveDocumentsForCourtSummons;
    }

    public boolean isPurchaseInPending() {
        return isPurchaseInPending;
    }

    public void setPendingStatusOfPurchase(boolean purchaseInPending) {
        isPurchaseInPending = purchaseInPending;
    }

    public void makeFullAirRefundFromPartialWithMedicalReason() {
        C3ServiceRecoveryPage serviceRecoveryPage = new C3ServiceRecoveryPage(getWebdriverInstance());
        serviceRecoveryPage.chooseRecoveryReason("Medical");
        serviceRecoveryPage.answerMedicalOrDeathQuestions("Yes");
        verifyTalkingPoints();
        serviceRecoveryPage.continueRefund();
        new C3RefundDeterminationPage(getWebdriverInstance()).clickRefund();
        C3AirRefundPage refundPage = new C3AirRefundPage(getWebdriverInstance());
        refundPage.fillPartialRefundValues(2);
        setRefundAmount();
        refundPage.clickRefund();
        refundPage.continueOperation();
    }


    public void verifyRefundedHotDollarsInTotalHotDollarsAmount() {
        C3CustomerInfoFragment customerInfoFragment  = new C3CustomerInfoFragment(getWebdriverInstance());
        assertThat(customerInfo.getHotDollarsAmount() + c3ItineraryInfo.getRefundHotDollarAmount())
                .as("Incorrect total HotDollars amount after hotdollars refund")
                .isEqualTo(Double.parseDouble(customerInfoFragment.getHotDollarAmount().replaceAll("[^0-9.]", "")),
                        Delta.delta(0.1));

    }

    public void doAvsOverride() {
        String securityCode = billingInfo.getSecurityCode();
        C3HotelItineraryPage c3HotelItineraryPage = new C3HotelItineraryPage(getWebdriverInstance());
        String errorMsg = c3HotelItineraryPage.doAvsOverride(securityCode);
        assertThat(errorMsg).as("Override message is wrong")
                .containsIgnoringCase("This reservation is confirmed, and the customer has been charged");
    }

    public void doCpvOverride() {
        String msg = new C3HotelItineraryPage(getWebdriverInstance()).doCpvOverride();
        assertThat(msg.replaceAll("[,]", "")).as("Confirmation message is wrong")
                .contains("This reservation is confirmed and the customer has been charged " +
                        billingInfo.getTotalAmountText() + ". A confirmation message has been sent to " +
                        customerInfo.getEmail());

    }

    public void verifyStatusCodeForCpvOverriddenPurchase() {
        Map map = new C3RefundRecoveryCustomerDao(databaseSupport)
                .getStatusCodeAmountAndCpvStatusCodeForOverriddenPurchase(c3ItineraryInfo.getItineraryNumber());

        assertThat(map.get("STATUS_CODE")).as("Status code is not equal to expected")
                .isEqualTo(new BigDecimal(30200));
        assertThat(map.get("AMOUNT")).as("Amount in C3 and DB is not equal")
                .isEqualTo(new BigDecimal(1));
        assertThat(map.get("HOTWIRE_CPV_STATUS_CODE")).as("CPV status code is not equal to expected")
                .isEqualTo(new BigDecimal(30325));
    }

    public void verifyStatusCodeForAvsOverriddenPurchase() {
        String amount  = billingInfo.getTotalAmountText().replaceAll("[€$,]", "");
        Map map = new C3RefundRecoveryCustomerDao(databaseSupport)
                .getStatusCodeAmountAndAvsStatusCodeForOverriddenPurchase(c3ItineraryInfo.getItineraryNumber());

        assertThat(map.get("STATUS_CODE")).as("Status code is not equal to expected")
                .isEqualTo(new BigDecimal(30200));
        assertThat(map.get("AMOUNT")).as("Amount in C3 and DB is not equal")
                .isEqualTo(new BigDecimal(1));
        assertThat(map.get("HOTWIRE_AVS_STATUS_CODE")).as("AVS status code is not equal to expected")
                .isEqualTo(new BigDecimal(30260));


        map = new C3RefundRecoveryCustomerDao(databaseSupport)
                .getStatusCodeAndAmountForOverriddenPurchase(c3ItineraryInfo.getItineraryNumber(), amount);
        assertThat(map.get("STATUS_CODE")).as("Status code is not equal to expected")
                .isEqualTo(new BigDecimal(30400));
        assertThat(new BigDecimal(amount).compareTo((BigDecimal) map.get("AMOUNT")) == 0)
                .as("Amount in C3 and DB is not equal").isTrue();



    }
}
