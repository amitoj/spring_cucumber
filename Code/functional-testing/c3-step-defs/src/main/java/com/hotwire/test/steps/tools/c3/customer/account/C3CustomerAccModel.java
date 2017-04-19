/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.customer.account;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.tools.c3.C3IndexPage;
import com.hotwire.selenium.tools.c3.C3ViewResources;
import com.hotwire.selenium.tools.c3.blackList.C3BlackListPage;
import com.hotwire.selenium.tools.c3.customer.C3CaseHistoryPage;
import com.hotwire.selenium.tools.c3.customer.C3CreateCustomerPage;
import com.hotwire.selenium.tools.c3.customer.C3CustomerInfoFragment;
import com.hotwire.selenium.tools.c3.customer.C3CustomerMainPage;
import com.hotwire.selenium.tools.c3.customer.editAccountInfo.C3EditAccInfoPage;
import com.hotwire.selenium.tools.c3.customer.C3PastBookingPage;
import com.hotwire.selenium.tools.c3.customer.C3PrinterFriendlyCaseHistoryPage;
import com.hotwire.selenium.tools.c3.customer.editAccountInfo.C3EditPaymentMethodForm;
import com.hotwire.selenium.tools.c3.customer.hotDollars.C3CustomerHotDollarsForm;
import com.hotwire.selenium.tools.c3.customer.hotDollars.C3MassHotDollarsReport;
import com.hotwire.selenium.tools.c3.customer.hotDollars.C3MultiCustomerHotDollarsForm;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3AirItineraryPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3CarItineraryPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3HotelItineraryPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3ItineraryDetailsPage;
import com.hotwire.selenium.tools.c3.hotel.C3HotelCaseHistoryPage;
import com.hotwire.selenium.tools.myAccount.HWGlobalHeader;
import com.hotwire.selenium.tools.myAccount.HWMyTrips;
import com.hotwire.selenium.tools.myAccount.HWRegisterForm;
import com.hotwire.selenium.tools.myAccount.HWThanksForRegister;
import com.hotwire.selenium.tools.myAccount.Last5DigitsSignInFragment;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.ProductVertical;
import com.hotwire.test.steps.tools.bean.c3.C3HotelSupplyInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.util.db.c3.C3CarItineraryInfoDao;
import com.hotwire.util.db.c3.C3CustomerDao;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.fest.assertions.Assertions;
import org.fest.assertions.Delta;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-abudyak
 * Date: 9/24/13
 * Time: 9:59 AM
 */
public class C3CustomerAccModel extends ToolsAbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3CustomerAccModel.class);
    private static final String HD_RECOVERY_REASON = "Agent Error";
    @Autowired
    @Qualifier("nonExpressCustomer")
    private CustomerInfo nonExpressCustomer;

    @Autowired
    @Qualifier("expressCustomer")
    private CustomerInfo expressCustomer;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private C3HotelSupplyInfo c3HotelSupplyInfo;

    @Autowired
    private BillingInfo billingInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Autowired
    private CustomerInfo customerInfo;

    public void clickServiceRecovery() {
        C3ItineraryDetailsPage viewItinDetailsPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        if (ProductVertical.AIR.equals(hotwireProduct.getProductVertical())) {
            viewItinDetailsPage = new C3AirItineraryPage(getWebdriverInstance());
        }
        c3ItineraryInfo.setTotalCost(viewItinDetailsPage.getTotalCost());
        LOGGER.info("Total price refundable deal: " + viewItinDetailsPage.getTotalCost());
        String customerEmail = new C3CustomerInfoFragment(getWebdriverInstance())
                                    .getEmail();
        LOGGER.info("Customer email: " + customerEmail);
        customerInfo.setCustomerTypeByEmail(customerEmail);
        viewItinDetailsPage.serviceRecoveryClick();
    }

    public ProductVertical selectVertical() {
        C3PastBookingPage pastBookingPage = new C3PastBookingPage(getWebdriverInstance());
        //customerPage.choosePastBooking();
        ProductVertical vertical = hotwireProduct.getProductVertical();
        String tabName = pastBookingPage.getActiveTabName();
        LOGGER.info("Current tab opened: " + tabName);
        boolean test = !ProductVertical.validate(tabName).equals(vertical);
        if (test) {
            switch (vertical) {
                case HOTEL:
                    LOGGER.info("Past purchases. Selecting HOTELS tab..");
                    pastBookingPage.chooseHotels();
                    break;
                case CAR:
                    LOGGER.info("Past purchases. Selecting CARS tab..");
                    pastBookingPage.chooseCar();
                    break;
                case AIR:
                    LOGGER.info("Past purchases. Selecting AIR tab..");
                    pastBookingPage.chooseAir();
                    break;
                default:
                    break;
            }
        }
        else {
            LOGGER.info("Past purchases. From current tab.");
        }
        return vertical;
    }

    public void verifyPurchaseNumber(Integer numOfPurchases) {
        logCustomer();
        C3PastBookingPage pastBookingPage = new C3PastBookingPage(getWebdriverInstance());
        Assertions.assertThat(pastBookingPage.getPurchasesNum()).isEqualTo(numOfPurchases);
    }

    public void createRandomCustomer() {
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.NON_EXPRESS);
        customerInfo.setCustomerRandomEmail();
        new C3IndexPage(getWebdriverInstance()).clickCreateCustomer();
        C3CreateCustomerPage createCustomerPage = new C3CreateCustomerPage(getWebdriverInstance());
        createCustomerPage.setFullName(customerInfo.getFirstName(), customerInfo.getLastName());
        createCustomerPage.setEmail(customerInfo.getEmail());
        createCustomerPage.setAddress(customerInfo.getZipCode());
        createCustomerPage.submit();
    }

    public void fillDataForRandomCustomer() {
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.NON_EXPRESS);
        customerInfo.setCustomerRandomEmail();

        C3CreateCustomerPage createCustomerPage = new C3CreateCustomerPage(getWebdriverInstance());
        createCustomerPage.setFullName(customerInfo.getFirstName(), customerInfo.getLastName());
        createCustomerPage.setEmail(customerInfo.getEmail());
        createCustomerPage.setAddress(customerInfo.getZipCode());
        createCustomerPage.submit();
    }

    public void createCustomerWithSubscription(boolean subscription) {
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.NON_EXPRESS);
        customerInfo.setCustomerRandomEmail();
        new C3IndexPage(getWebdriverInstance()).clickCreateCustomer();
        C3CreateCustomerPage createCustomerPage = new C3CreateCustomerPage(getWebdriverInstance());
        createCustomerPage.setFullName(customerInfo.getFirstName(), customerInfo.getLastName());
        createCustomerPage.setEmail(customerInfo.getEmail());
        createCustomerPage.setAddress(customerInfo.getZipCode());
        createCustomerPage.setSubscription(subscription);
        createCustomerPage.submit();
    }

    public void addRandomPhonesToCustomer() {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        Map<String, String> phone1 = getRandomPhone();
        customerInfo.setPhoneNumber(phone1.get("rowPhone"));
        c3EditAccInfoPage.setPrimaryPhone(phone1.get("country"), phone1.get("city"), phone1.get("number"));

        Map<String, String> phone2 = getRandomPhone();
        customerInfo.setAltPhone(phone2.get("rowPhone"));
        c3EditAccInfoPage.setAlternatePhone(phone2.get("country"), phone2.get("city"), phone2.get("number"));

        c3EditAccInfoPage.clickSubmit();
    }

    private Map<String, String> getRandomPhone() {
        Map<String, String> phone = new HashMap<>();
        String rowPhone = Long.valueOf(System.currentTimeMillis()).toString().substring(1, 12);
        phone.put("rowPhone", rowPhone);
        phone.put("country", rowPhone.substring(0, 1));
        phone.put("city", rowPhone.substring(1, 4));
        phone.put("number", rowPhone.substring(4, rowPhone.length()));
        return phone;
    }

    public void editCustomerAccount() {
        new C3CustomerMainPage(getWebdriverInstance()).clickEditAccInfo();
    }

    public void createAccountWithWrongEmailConfirmation() {
        new C3IndexPage(getWebdriverInstance()).clickCreateCustomer();
        C3CreateCustomerPage createCustomerPage = new C3CreateCustomerPage(getWebdriverInstance());
        createCustomerPage.setFullName(customerInfo.getFirstName(), customerInfo.getLastName());
        createCustomerPage.setWrongEmail(customerInfo.getEmail());
        createCustomerPage.setAddress(customerInfo.getZipCode());
        createCustomerPage.submit();
    }

    public void tryCreateCustomerWithBlankFields() {
        new C3IndexPage(getWebdriverInstance()).clickCreateCustomer();
        C3CreateCustomerPage createCustomerPage = new C3CreateCustomerPage(getWebdriverInstance());
        createCustomerPage.submit();
    }

    public void verifyAccStatus(String status) {
        assertThat(new C3EditAccInfoPage(getWebdriverInstance()).getMessage())
            .as("Error message is not presenting")
            .contains(customerInfo.getEmail()).contains(status);
    }

    public void verifyNoMessageDisplayed() {
        assertThat(new C3EditAccInfoPage(getWebdriverInstance()).isMsgDisplayed())
                .as("Verify message no displayed")
                .isFalse();
    }

    public void verifyCreateCustomerErrorMessage(String message) {
        assertThat(new C3CreateCustomerPage(getWebdriverInstance()).getMessage())
                .as("Error message is not presenting")
                .contains(message);
    }

    public void verifyErrorsForBlankFieldsOnC3CustomerCreatingPage() {
        assertThat(new C3CreateCustomerPage(getWebdriverInstance()).getFirstNameError())
                .as("Error message is not presenting for first name")
                .isEqualTo("Enter the first name.");
        assertThat(new C3CreateCustomerPage(getWebdriverInstance()).getLastNameError())
                .as("Error message is not presenting for last name")
                .isEqualTo("Enter the last name.");
        assertThat(new C3CreateCustomerPage(getWebdriverInstance()).getEmailError())
                .as("Error message is not presenting for email address")
                .isEqualTo("Enter a valid email address.");
        assertThat(new C3CreateCustomerPage(getWebdriverInstance()).getConfirmationEmailError())
                .as("Error message is not presenting for confirmation email address")
                .isEqualTo("Enter a valid email address.");
        assertThat(new C3CreateCustomerPage(getWebdriverInstance()).getCountryError())
                .as("Error message is not presenting for country.")
                .isEqualTo("Enter the country.");
    }

    public void verifyCustomerInDB() {
        assertThat(new C3CustomerDao(getDataBaseConnection())
                .doesCustomerInDB(customerInfo.getEmail(),
                        customerInfo.getFirstName(),
                        customerInfo.getLastName()))
                .as("Customer " + customerInfo.getEmail() + "with name " + customerInfo.getFirstName() +
                        customerInfo.getLastName() + "doesn't exist in DB")
                .isTrue();
    }

    public void clickEditAccInfo() {
        new C3CustomerMainPage(getWebdriverInstance()).clickEditAccInfo();
    }

    public void clickAddANewCardLink(String cardName) {
        new C3EditPaymentMethodForm(getWebdriverInstance()).addACardWithName(cardName);
    }

    public void setCreditCardNickName(String newName) {
        new C3EditPaymentMethodForm(getWebdriverInstance()).changeCreditCardName(newName);
    }

    public void setCCBillingAddress(String billingAddress) {
        new C3EditPaymentMethodForm(getWebdriverInstance()).changeBillingAddress(billingAddress);
    }

    public void openEditPaymentMethodForm() {
        new C3EditAccInfoPage(getWebdriverInstance()).clickEditPaymentMethods();
    }

    public void openAddPaymentMethodForm() {
        new C3EditAccInfoPage(getWebdriverInstance()).clickAddANewCardLink();
    }

    public void deleteCreditCardWithSpecialName(String cardName) {
        new C3EditPaymentMethodForm(getWebdriverInstance()).deleteCreditCardByName(cardName);
        acceptAlert();
    }

    public void verifySuccessMessageOnPaymentMethodForm(String message) {
        assertThat(new C3EditPaymentMethodForm(getWebdriverInstance()).returnCardSuccessMessage())
                .isEqualToIgnoringCase(message);
    }

    public void verifyErrorMessageOnPaymentMethodForm(String message) {
        assertThat(new C3EditPaymentMethodForm(getWebdriverInstance()).returnCardErrorMessage())
                .isEqualToIgnoringCase(message);
    }

    public void deactivateAccount() {
        new C3EditAccInfoPage(getWebdriverInstance()).deactivateAccount();
    }

    public void reactivateAccount() {
        new C3EditAccInfoPage(getWebdriverInstance()).reactivateAccount();
    }

    public void cancelDeactivation() {
        new C3EditAccInfoPage(getWebdriverInstance()).cancelDeactivation();
    }

    public void selectPurchase() {
        ProductVertical productVertical = selectVertical();

        C3PastBookingPage pastBookingPage = new C3PastBookingPage(getWebdriverInstance());
        String itinerary = pastBookingPage.clickOnFirstConfirmedBooking(productVertical.getProductCode());
        c3ItineraryInfo.setItineraryNumber(itinerary);
        LOGGER.info("Itinerary number that was selected is " + c3ItineraryInfo.getItineraryNumber());
    }

    public void editHotDollars() {
        new C3EditAccInfoPage(getWebdriverInstance()).editHotDollars();
    }

    public void addHotDollarsToItinerary() {
        hotwireProduct.getItineraryPage(getWebdriverInstance()).addHotDollarsToItinerary();
        customerInfo.setCustomerEmailByItinerary(c3ItineraryInfo.getItineraryNumber());
        c3ItineraryInfo.setTotalCost(hotwireProduct.getItineraryPage(getWebdriverInstance()).getTotalCost());
    }

    public void verifyHotDollarsForm() {
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        customerInfo.setHotDollars(c3CustomerHotDollarsForm.getHotDollarsAmount());
    }

    public void verifyNoHotDollarsAvailable() {
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        assertThat(Double.parseDouble(c3CustomerHotDollarsForm.getHotDollarsAmount()))
                .as("Total hotdollars balance is not an empty")
                .isEqualTo(0);
    }

    public void verifyHotDollarsFormOpenAndClose() {
        new C3EditAccInfoPage(getWebdriverInstance()).editHotDollars();
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        c3CustomerHotDollarsForm.getHotDollarsAmount();
        c3CustomerHotDollarsForm.getHotDollarsReason();
        c3CustomerHotDollarsForm.getSubmitBtn();
        c3CustomerHotDollarsForm.cancel();
        if (c3CustomerHotDollarsForm.isVisible()) {
            throw new RuntimeException("Edit hotdollars form is still visible on the page");
        }
    }

    public void openCaseNotesFromHotDollars() {
        new C3CustomerHotDollarsForm(getWebdriverInstance()).viewCaseNotes();
    }

    public void addHotDollars(String amount) {
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        c3CustomerHotDollarsForm.setHotDollarsAmount(amount);
        c3CustomerHotDollarsForm.setHotDollarsReason(HD_RECOVERY_REASON);
        c3CustomerHotDollarsForm.submit();
        customerInfo.setHotDollarsChange(amount);
        customerInfo.addHotDollars();
    }

    public void setHotDollars(String amount) {
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        String totalHDAmount =  c3CustomerHotDollarsForm.getHotDollarsAmount();
        c3CustomerHotDollarsForm.setHotDollarsAmount("-" + totalHDAmount);
        c3CustomerHotDollarsForm.setHotDollarsReason(HD_RECOVERY_REASON);
        c3CustomerHotDollarsForm.submit();
        customerInfo.setHotDollars("0");

        new WebDriverWait(getWebdriverInstance(), 10)
                .until(new IsElementLocationStable(getWebdriverInstance(),
                        By.cssSelector(C3CustomerHotDollarsForm.HOT_DOLLARS_AMOUNT)));

        c3CustomerHotDollarsForm.setHotDollarsAmount(amount);
        c3CustomerHotDollarsForm.setHotDollarsReason(HD_RECOVERY_REASON);
        c3CustomerHotDollarsForm.submit();
        customerInfo.setHotDollarsChange(amount);
        customerInfo.addHotDollars();
    }

    public void addHotDollarsMoreThanMax() {
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        c3CustomerHotDollarsForm.setHotDollarsAmount(getMoreThanMaxHotDollars());
        c3CustomerHotDollarsForm.setHotDollarsReason(HD_RECOVERY_REASON);
        c3CustomerHotDollarsForm.submit();
        customerInfo.setHotDollarsChange(getMoreThanMaxHotDollars());
    }

    private String getMoreThanMaxHotDollars() {
        Double cost = c3ItineraryInfo.getTotalCost();
        Double moreThanMax = cost + 1;
        return new DecimalFormat("#.00").format(moreThanMax);
    }

    public void overrideHotDollars(String amount) {
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        c3CustomerHotDollarsForm.setHotDollarsAmount(amount);
        c3CustomerHotDollarsForm.setHotDollarsReason(HD_RECOVERY_REASON);
        c3CustomerHotDollarsForm.override();
        c3CustomerHotDollarsForm.setOverrideDescription("Test Description.");
        c3CustomerHotDollarsForm.clickSubmitHDOverride();
        customerInfo.setHotDollarsChange(amount);
        customerInfo.addHotDollars();
    }

    public void minusHotDollarsMoreThanAvailable() {
        String amountString = new C3CustomerDao(getDataBaseConnection()).getHotDollarsAmount(customerInfo.getEmail());
        Double amount = Double.valueOf(amountString) + 5.0;
        amountString = String.valueOf(amount.intValue());
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        c3CustomerHotDollarsForm.setHotDollarsAmount("-" + amountString);
        c3CustomerHotDollarsForm.setHotDollarsReason(HD_RECOVERY_REASON);
        c3CustomerHotDollarsForm.submit();
    }

    public void verifyHotDollarsAdded() {
        C3CustomerHotDollarsForm form = new C3CustomerHotDollarsForm(getWebdriverInstance());
        final String confirmationMsg = form.getConfirmationMsg();
        try {
            assertThat(confirmationMsg)
                    .contains("was credited to the customers balance.")
                    .contains(customerInfo.getHotDollarsChange());
        }
        catch (AssertionError e) {
            assertThat(confirmationMsg)
                    .contains("HotDollars were added to the customers account")
                    .contains(customerInfo.getHotDollarsChange());
        }
        C3CustomerHotDollarsForm updatedForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        assertThat(updatedForm.getHotDollarsAmount()).contains(customerInfo.getHotDollars());
        assertThat(updatedForm.getHotDollarsSummary()).contains(customerInfo.getHotDollars());
    }

    public void verifyHotDollarsErrorMsg() {
        C3CustomerHotDollarsForm form = new C3CustomerHotDollarsForm(getWebdriverInstance());
        assertThat(form.getErrorMsg())
                    .contains("To award a higher Hot Dollar amount use HD Override.");
    }

    public void verifyHotDollarsSubtracted() {
        C3CustomerHotDollarsForm form = new C3CustomerHotDollarsForm(getWebdriverInstance());
        assertThat(form.getConfirmationMsg())
                .contains("was debited from the customers balance.")
                .contains(customerInfo.getHotDollarsChange().replace("-", ""));
        assertThat(form.getHotDollarsSummary()).contains(customerInfo.getHotDollars());
        assertThat(form.getHotDollarsAmount()).contains(customerInfo.getHotDollars());
    }

    public void verifyHDErrorMsg(String msg) {
        C3CustomerHotDollarsForm form = new C3CustomerHotDollarsForm(getWebdriverInstance());
        assertThat(form.getErrorMsg())
                .isEqualTo(msg);
    }

    public void verifyHotDollarsInDB() {
        //Timeout for Database operations.
        String dbAmount = new C3CustomerDao(getDataBaseConnection())
                .getHotDollarsAmount(customerInfo.getEmail());
        assertThat(customerInfo.getHotDollarsAmount())
                .isEqualTo(Double.valueOf(dbAmount), Delta.delta(0.1));

        //for partial hot dollars purchase only
        if (customerInfo.getHotDollarsAmount() == 0.0) {
            verifyPartiallyHotdollarsTransactionInDB();
        }
    }

    public void verifyPartiallyHotdollarsTransactionInDB() {
        List<Map<String, Object>> results = new C3CustomerDao(getDataBaseConnection())
                .getPaymentReceiptAmount(c3ItineraryInfo.getItineraryNumber());

        Boolean totalCostWithOutInsuranceExistInDB = false;
        Boolean oneExistInDB = false;
        BigDecimal expectedTotalAmount = BigDecimal.valueOf(c3ItineraryInfo.getTotalCost())
                .subtract(BigDecimal.valueOf(Double.parseDouble(billingInfo.getInsuranceAmount())));
        for (Map<String, Object> pair : results) {
            if (expectedTotalAmount.compareTo((BigDecimal) pair.get("AMOUNT")) == 0) {
                totalCostWithOutInsuranceExistInDB = true;
            }
            if (BigDecimal.ONE.compareTo((BigDecimal) pair.get("AMOUNT")) == 0) {
                oneExistInDB = true;
            }
        }
        assertThat(results.size()).as("Payment receipt table in DB has an incorrect size").isEqualTo(3);
        assertThat(totalCostWithOutInsuranceExistInDB).as("Total cost doesn't exist in DB").isTrue();
        assertThat(oneExistInDB).as("Amount with 1$ doesn't  exist in DB").isTrue();
    }

    public void verifyHotDollarsTransactionPopUp() {
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        c3CustomerHotDollarsForm.clickOnLastTransaction();
        assertThat(c3CustomerHotDollarsForm.getLastTransactionPopUp())
                .contains(customerInfo.getHotDollarsChange())
                .containsIgnoringCase(customerInfo.getEmail());
    }

    public void goToMultiCustHotDollarsForm() {
        new C3ViewResources(getWebdriverInstance()).clickOnAddHotDollarsToMultipleAcc();
    }

    public void verifyMultiCustomerHDForm() {
        new C3MultiCustomerHotDollarsForm(getWebdriverInstance());
    }

    public void setEmailsForHotDollarsAdding() {
        String emailsString = StringUtils.join(customerInfo.getCustomersHotDollars().keySet(), ",");
        new C3MultiCustomerHotDollarsForm(getWebdriverInstance()).setEmails(emailsString);
    }

    public void addHotDollarsToEachCustomer(String amount, Integer years) {
        C3MultiCustomerHotDollarsForm c3MultiCustomerHotDollarsForm =
                new C3MultiCustomerHotDollarsForm(getWebdriverInstance());
        c3MultiCustomerHotDollarsForm.setExpirationDate(years);
        c3MultiCustomerHotDollarsForm.setHotDollarsAmount(amount);
        c3MultiCustomerHotDollarsForm.selectReason(HD_RECOVERY_REASON);
        c3MultiCustomerHotDollarsForm.setCaseNotes("Testing adding HotDollars to multiple customers");
        c3MultiCustomerHotDollarsForm.unCheckUserNotification();
        c3MultiCustomerHotDollarsForm.submit();
        //setting HotDollars values for future steps
        try {
            customerInfo.addCustomersHotDollars(amount);
            customerInfo.setHotDollarsExpirationDate(
                    new DateTime(c3MultiCustomerHotDollarsForm.getExpirationDate(years)));
        }
        catch (NullPointerException e) {
            logCustomer();
        }
    }

    public void verifyHDAddingtoEachCustomer() {
        assertThat(new C3MultiCustomerHotDollarsForm(getWebdriverInstance()).getConfirmationMessage())
                .isEqualTo("We updated HotDollars to the Hotwire accounts you entered.");
        customerInfo.setEmail(customerInfo.getCustomersHotDollars().keySet().toArray()[0].toString());
    }

    public void verifyAllCustomersHotDollarsInDB() {
        for (String email : customerInfo.getCustomersHotDollars().keySet()) {
            assertThat(customerInfo.getCustomerHotDollars(email))
                    .isEqualTo(new C3CustomerDao(getDataBaseConnection()).getHotDollarsAmount(email));
        }
    }

    public void verifyAllHotDollarsExpirationInDB() {
        //need to be completed when the form will be done
        for (String email : customerInfo.getCustomersHotDollars().keySet()) {
            assertThat(customerInfo.getHotDollarsExpirationDate().getYear())
                    .isEqualTo(new C3CustomerDao(getDataBaseConnection()).getHotDollarsExpirationDate(email).getYear());
            assertThat(customerInfo.getHotDollarsExpirationDate().getMonthOfYear())
                    .isEqualTo(new C3CustomerDao(getDataBaseConnection())
                            .getHotDollarsExpirationDate(email).getMonthOfYear());
        }
    }

    public void setCustomerEmailsAndHotDollars(Integer numberOfCustomers) {
        customerInfo.createCustomerHotDollars(new HashMap<String, Double>(numberOfCustomers));
        for (Map<String, Object> customer : new C3CustomerDao(getDataBaseConnection())
                .getCustomersEmailsWithHotDollars(numberOfCustomers)) {
            customerInfo.setCustomersHotDollars((String) customer.get("EMAIL"),
                    Double.parseDouble(customer.get("HOTDOLLARS").toString()));
        }
    }

    public void verifyStatusOfLastPurchase(String status) {
        selectVertical();
        assertThat(new C3PastBookingPage(getWebdriverInstance()).getStatusOfLastPurchase()).isEqualTo(status);
    }

    public void verifyWasLastPurchaseBookedByACSR(String expected) {
        assertThat(new C3PastBookingPage(getWebdriverInstance())
                .wasLastPurchaseBookedByCSR())
                .isEqualTo(expected);
    }

    public void verifyPhoneChangedMsg() {
        assertThat(new C3EditAccInfoPage(getWebdriverInstance()).getMessage())
                .isEqualTo(String.format("The account for %s was successfully updated.", customerInfo.getEmail()));
    }

    public void activateSubsNewslettersC3(boolean isSubscribe) {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());

        Assertions.assertThat(c3EditAccInfoPage.isCustomerSubscribed()).isEqualTo(!isSubscribe);
        c3EditAccInfoPage.changeSubscription();
        c3EditAccInfoPage.clickSubmit();
    }

    public void confirmSubscriptionsFromDB(String subscription) {
        assertThat(new C3CustomerDao(getDataBaseConnection()).getCustomerSubscriptions(customerInfo.getEmail()))
                .isEqualTo(subscription);
    }

    public void verifyCustomerFullyUnsubscribedDB() {
        assertThat(new C3CustomerDao(getDataBaseConnection()).getCustomerSubscriptions(customerInfo.getEmail()))
                .as("Subscription status")
                .isEqualTo("N");
        assertThat(new C3CustomerDao(getDataBaseConnection()).getCustomerSubscriptionOpted(customerInfo.getEmail()))
                .as("Subscription status opted")
                .isEqualTo("01N02N03N04N05N06N07N08N09N10N11N12N");
    }


    public void verifyHotDollarsCaseNote(String operation) {
        String caseNote = new C3CustomerHotDollarsForm(getWebdriverInstance()).getCaseNoteDescription();
        assertThat(caseNote).contains("HotDollars to multiple customers").contains(operation);
    }

    public void setBunchOfEmails(String emailsBunch) {
        customerInfo.setRawEmails(emailsBunch);
        customerInfo.createCustomerHotDollars(new HashMap<String, Double>());
        for (String email: emailsBunch.split("\\s+,\\s+")) {
            Double amount = null;
            try {
                amount = Double.parseDouble(new C3CustomerDao(getDataBaseConnection()).getHotDollarsAmount(email));
            }
            catch (NullPointerException | NumberFormatException e) {
                logCustomer();
            }
            customerInfo.setCustomersHotDollars(email, amount);
        }
    }

    public void verifyMassHotDollarsReport() {
        new C3MassHotDollarsReport(getWebdriverInstance());
    }

    public void verifyEmailStatusInHDReport(String email, String status) {
        final C3MassHotDollarsReport c3MassHotDollarsReport = new C3MassHotDollarsReport(getWebdriverInstance());
        if (status.equals("success")) {
            assertThat(c3MassHotDollarsReport.getSuccessfulEmails()).contains(email);
        }
        else {
            assertThat(c3MassHotDollarsReport.getNonExistEmails()).contains(email);
        }
    }

    public void clickEditItineraryCaseHistory() {
        new C3HotelItineraryPage(getWebdriverInstance()).clickEditItineraryCaseHistory();
    }

    public void clickEditCaseEntriesForCustomer() {
        new C3HotelItineraryPage(getWebdriverInstance()).clickEditCaseEntriesForCustomer();
    }

    public void createNewCaseNoteForHistory(String textNote) {
        new C3HotelCaseHistoryPage(getWebdriverInstance()).addCaseNote(textNote);
    }

    public void verifyCreatedCaseNoteInHistory(String textNote) {
        C3HotelCaseHistoryPage historyPage = new C3HotelCaseHistoryPage(getWebdriverInstance());
        String actualNote = historyPage.getLastCaseNote();
        assertThat(textNote.contains(actualNote))
                .as("text expected - '" + textNote + "', but actual note - " + actualNote).isTrue();
    }

    public void receiveDataForCarPurchase() {
        Map carInfo = new C3CarItineraryInfoDao(getDataBaseConnection())
                .getCarItineraryInformation(c3ItineraryInfo.getItineraryNumber());
        hotwireProduct.setOpacity(carInfo.get("opacity_code").toString());
        c3ItineraryInfo.setPnrNumber(carInfo.get("pnr_number").toString());
        customerInfo.setCustomerInfoByItinerary(c3ItineraryInfo.getItineraryNumber());
        c3ItineraryInfo.setCompany(carInfo.get("vendor_name").toString());

    }

    public void compareCarData() {
        C3CarItineraryPage c3CarItineraryPage = new C3CarItineraryPage(getWebdriverInstance());
        assertThat(c3CarItineraryPage.getPurchaseType())
                .isEqualToIgnoringCase(hotwireProduct.getOpacity().getRateType());
        assertThat(c3CarItineraryPage.getItineraryNumber().contains(c3ItineraryInfo.getItineraryNumber()));
        assertThat(c3CarItineraryPage.getPNR().equals(c3ItineraryInfo.getPnrNumber()));
        assertThat(c3CarItineraryPage.getDriverName().equals(c3ItineraryInfo.getDriverName()));
        assertThat(c3CarItineraryPage.getVendorName().equals(c3ItineraryInfo.getCompany()));
    }

    public void resetPassword() {
        new C3EditAccInfoPage(getWebdriverInstance()).clickResetPasswordBtn();
    }

    public void cancelPasswordReset() {
        new C3EditAccInfoPage(getWebdriverInstance()).cancelPasswordReset();
    }

    public void confirmPasswordReset() {
        new C3EditAccInfoPage(getWebdriverInstance()).confirmPasswordReset();
    }

    public void verifyMessageAboutPassword(String isMessageDisplayed) {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        if (isMessageDisplayed.contains("don't")) {
            assertThat(c3EditAccInfoPage.isMsgDisplayed()).isFalse();
        }
        else {
            assertThat(c3EditAccInfoPage.getMessage())
                    .isEqualTo("Please inform the customer that we have sent an email to " +
                            customerInfo.getEmail() +
                            ". This email contains a link they can use to reset their password." +
                            " For security purposes, the link contained in the email is active " +
                            "for only 24 hours. Once the link expires, they can get a new email sent to them by " +
                            "going to the Password Assistance page on Hotwire.com.");
        }
    }

    public void verifyCustomerInfoChanged() {
        assertThat(new C3EditAccInfoPage(getWebdriverInstance()).getMessage())
                .isEqualToIgnoringCase(
                        String.format("The account for %s was successfully updated.", customerInfo.getEmail()));
    }

    public void verifyPastPurchaseCount(String expectedCount) {
        Integer actualCount = new C3PastBookingPage(getWebdriverInstance()).getPastPurchaseCount();
        assertThat(expectedCount).as("past purchase count not equals").isEqualTo(actualCount.toString());
    }

    public void verifyPrinterFriendlyPage() {
        String rootWindowHandler = getWebdriverInstance().getWindowHandle();

        C3CaseHistoryPage c3CaseHistoryPage = new C3CaseHistoryPage(getWebdriverInstance());
        List<String> descriptionsFromC3Page = c3CaseHistoryPage.getDescriptionOfCases();
        List<String> timeFromC3Page = c3CaseHistoryPage.getTimeOfCases();

        c3CaseHistoryPage.getPrinterFriendlyBtn().click();

        for (String h : getWebdriverInstance().getWindowHandles()) {
            if (!h.equals(rootWindowHandler) && !h.isEmpty()) {
                WebDriver printerFriendlyPage = getWebdriverInstance().switchTo().window(h);
                C3PrinterFriendlyCaseHistoryPage  printerFriendlyCaseHistoryPage =
                        new C3PrinterFriendlyCaseHistoryPage(printerFriendlyPage);

                List<String> descriptionsPrinterPage = printerFriendlyCaseHistoryPage.getDescriptionContents();
                List<String> timePrinterPage = printerFriendlyCaseHistoryPage.getTimeContents();

                int i = 0;
                for (String caseDescription : descriptionsPrinterPage) {
                    if (!caseDescription.isEmpty()) {
                        assertThat(caseDescription.equals(descriptionsFromC3Page.get(i)))
                                .as("Descriptions on the Printer and on the C3 pages are equal")
                                .isTrue();
                    }
                    i++;
                }

                i = 0;
                for (String time : timePrinterPage) {
                    assertThat(time.equals(timeFromC3Page.get(i)))
                            .as("Time on the Printer and on the C3 pages are equal").isTrue();
                    i++;
                }
                printerFriendlyPage.close();
            }
        }
    }

    public void verifyRefundedByField(String value) {
        assertThat(hotwireProduct.getItineraryPage(getWebdriverInstance()).getRefundedByValue()).isEqualTo(value);
    }

    public void gotoBlackListPage() {
        new C3IndexPage(getWebdriverInstance()).clickBlackList();
    }

    public void addRemoveCustomerBlackList(String addRemove) {
        new C3IndexPage(getWebdriverInstance()).clickBlackList();
        getWebdriverInstance().switchTo().defaultContent();
        getWebdriverInstance().switchTo().frame("c3Frame");
        C3BlackListPage c3BlackListPage = new C3BlackListPage(getWebdriverInstance());
        c3BlackListPage.setEmail(customerInfo.getEmail());
        if ("add".equalsIgnoreCase(addRemove)) {
            c3BlackListPage.clickAddToBlackList();
        }
        else {
            c3BlackListPage.clickRemoveFromBlackList();
        }
    }

    public void doesCustomerInBlackList(boolean isInBlackList) {
        assertThat(new C3CustomerDao(getDataBaseConnection()).doesCustomerInBlackList(customerInfo.getEmail()))
                .as("Customer " + customerInfo.getEmail() + "").isEqualTo(isInBlackList);
    }

    public void verifyMessageAddedToBlackList() {
        assertThat(new C3BlackListPage(getWebdriverInstance()).getConfirmationMessage().equals(
                "The email address " + customerInfo.getEmail() +
                        " was added to the blacklist. They will not be able to make any purchases " +
                        "or access their account information."
        ))
                .as("The email address " + customerInfo.getEmail() +
                        " was added to the blacklist. They will not be able to make any purchases " +
                        "or access their account information.")
                .isTrue();
    }

    public void verifyMessageRemovedFromBlackList() {
        assertThat(new C3BlackListPage(getWebdriverInstance()).getConfirmationMessage()
                        .equals("The email address " + customerInfo.getEmail() + " was removed from the blacklist."))
                .as("The email address " + customerInfo.getEmail() + " was removed from the blacklist.")
                .isTrue();
    }

    public void verifyMessageEmailNotValid() {
        assertThat(new C3BlackListPage(getWebdriverInstance()).getConfirmationMessage()
                .equals("Enter a valid email address.\n" +
                        "Email address is not valid."))
                .as("Enter a valid email address.\n" +
                        "Email address is not valid.")
                .isTrue();
    }

    public void verifyCustomerInformation() {
        final C3CustomerInfoFragment c3CustomerInfoFragment = new C3CustomerInfoFragment(getWebdriverInstance());
        assertThat(c3CustomerInfoFragment.getName()).isEqualTo(customerInfo.getFullName().replaceFirst("\\s+", " "));
        assertThat(c3CustomerInfoFragment.getEmail()).isEqualTo(customerInfo.getEmail());
        //Comparing dates, skipping time
        DateTime accountCreated = DateTimeFormat.forPattern("dd-MMM-yy")
                .parseDateTime(c3CustomerInfoFragment.getAccountCreated().replaceAll(" .*$", ""));
        assertThat(accountCreated).isEqualTo(customerInfo.getAccountCreated());
        //Phones verification
        assertThat(extractPhone(c3CustomerInfoFragment.getPhoneNumber()))
                .isEqualTo(extractPhone(customerInfo.getPhoneNumber()));
        if (!"".equals(customerInfo.getAltPhone())) {
            assertThat(extractPhone(c3CustomerInfoFragment.getAltPhoneNumber()))
                    .isEqualTo(extractPhone(customerInfo.getAltPhone()));
        }
        assertThat(c3CustomerInfoFragment.getZip()).isEqualTo(customerInfo.getZipCode());
//        final String customerCountry = c3CustomerInfoFragment.getCountry();
//        assertThat(customerCountry).isEqualTo(customerInfo.getCountry());
    }

    private String extractPhone(String rawPhone) {
        return rawPhone.replaceAll("[()-| ]", "");
    }

    public void gotoBlockedHotelsPage() {
        new C3CustomerMainPage(getWebdriverInstance()).clickOnBlockedHotels();
    }

    public void checkVerticalTab(String verticalName) {
        String tabName = new C3PastBookingPage(getWebdriverInstance()).getActiveTabName();
        LOGGER.info("Current tab opened: " + tabName);
        assertThat(verticalName.toLowerCase()).as("vertical name").isEqualTo(tabName.toLowerCase());
    }

    public void verifyPartialRefundedPurchase(String status, String amountVerify) {
        C3PastBookingPage pastBookingPage;
        for (int i = 0; i <= 10; i++) {
            pastBookingPage = new C3PastBookingPage(getWebdriverInstance());
            try {
                String refundedValue = pastBookingPage
                        .getRefundedValueOfPurchase(c3ItineraryInfo.getItineraryNumber()).replaceAll("[,$]", "");

                if (null != amountVerify) {
                    assertThat(Double.parseDouble(refundedValue))
                            .as("Partial refund value is correct")
                            .isEqualTo(c3ItineraryInfo.getRefundAmount());
                }
                String statusOfPurchase = pastBookingPage.getStatusOfPurchase(c3ItineraryInfo.getItineraryNumber());
                if ("any".equals(status)) {
                    assertThat(statusOfPurchase.equalsIgnoreCase("Partially refunded") ||
                            statusOfPurchase.equalsIgnoreCase("Partially voided"))
                            .as("Status for partial refund purchase is correct")
                            .isTrue();
                }
                else {
                    assertThat(statusOfPurchase.equalsIgnoreCase(status))
                            .as("Status for partial refund purchase is correct")
                            .isTrue();
                }
                return;
            }
            catch (Exception e) {
                this.scrollPage();
            }
        }
        throw new RuntimeException("Itinerary " + c3ItineraryInfo.getItineraryNumber() + " was not found on the page");
    }

    public void verifyHotDollarsActivityForPurchase() {
        C3CustomerHotDollarsForm c3CustomerHotDollarsForm = new C3CustomerHotDollarsForm(getWebdriverInstance());
        String itineraryNumber = c3ItineraryInfo.getItineraryNumber();
        String dateExpected = c3CustomerHotDollarsForm.getActivityBookingDateForItinerary(itineraryNumber);
        String bookingExpected = c3CustomerHotDollarsForm.getActivityBookingForItinerary(itineraryNumber);
        String amountExpected = c3CustomerHotDollarsForm.getActivityAmountForItinerary(itineraryNumber);

        Calendar cal = new GregorianCalendar();
        String dateActual = new SimpleDateFormat("ddMMMyy").format(cal.getTime()).toUpperCase();
        String bookingActual = itineraryNumber + c3HotelSupplyInfo.getHotelAddress().split(",")[1] + " hotel";
        String amountActual = billingInfo.getHotDollarsAmountText();

        assertThat(dateActual).isEqualTo(dateExpected);
        assertThat(bookingActual).isEqualTo(bookingExpected);
        assertThat(amountActual).isEqualTo(amountExpected);
    }

    public C3PastBookingPage scrollPastBookingPageToItinerary(String itinerary) {
        C3PastBookingPage pastBookingPage = null;

        for (int i = 0; i <= 25; i++) {
            pastBookingPage = new C3PastBookingPage(getWebdriverInstance());
            try {
                if (pastBookingPage.getAllVisibleItineraries().contains(itinerary)) {
                    return pastBookingPage;
                }
                else {
                    throw new Exception();
                }
            }
            catch (Exception e) {
                this.scrollPage();
            }
        }
        throw new RuntimeException("Itinerary " + itinerary + " is not found on the page");
    }

    public void verifyStatusOfPurchase(String status) {
        C3PastBookingPage pastBookingPage = scrollPastBookingPageToItinerary(c3ItineraryInfo.getItineraryNumber());

        assertThat(pastBookingPage.getStatusOfPurchase(c3ItineraryInfo.getItineraryNumber()))
                .as("Status of purchase is incorrect")
                .isEqualToIgnoringCase(status);

    }

    public void saveTotalHotDollarsAmount() {
        C3CustomerInfoFragment infoFragment = new C3CustomerInfoFragment(getWebdriverInstance());
        String totalHDAmount = infoFragment.getHotDollarAmount().replaceAll("[$,]", "");
        if (totalHDAmount.equals("N/A")) {
            totalHDAmount = "0";
        }
        customerInfo.setHotDollars(totalHDAmount);
    }

    public void closeHotDollarsForm() {
        new C3CustomerHotDollarsForm(getWebdriverInstance()).cancel();
    }

    public void clearCustomerContactDetails() {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        c3EditAccInfoPage.clearContactDetails();
        c3EditAccInfoPage.clickSubmit();
    }

    public void verifyErrorForBlankContactDetailsForCustomer() {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        assertThat(c3EditAccInfoPage.getMessage())
                .contains("Enter first and last names of the travelers.")
                .as("First and Last name error message");
        assertThat(c3EditAccInfoPage.getMessage())
                .contains("Enter a valid zip code.")
                .as("Zip code error message");
        assertThat(c3EditAccInfoPage.getMessage())
                .contains("Enter an email address.")
                .as("Email error message");
        assertThat(c3EditAccInfoPage.getMessage())
                .contains("Confirm email address field is blank.")
                .as("Confirm email address field is blank.");
    }

    public void verifyErrorForIncorrectEmailForCustomer() {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        assertThat(c3EditAccInfoPage.getMessage())
                .contains("Email address is not valid.")
                .as("Email error message");
    }

    public void verifyErrorForAlreadyRegisteredEmailForCustomer() {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        assertThat(c3EditAccInfoPage.getMessage())
                .contains("This email address belongs to an existing Hotwire account. " +
                        "Please choose a different email address.")
                .as("Email error message");
    }

    public void updateCustomerContactDetails() {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        c3EditAccInfoPage.setLastName(RandomStringUtils.randomAlphabetic(5));
        c3EditAccInfoPage.setEmail(customerInfo.getEmail());
        c3EditAccInfoPage.setConfirmEmail(customerInfo.getEmail());
        c3EditAccInfoPage.setPrimaryPhone("1", "415", "3333334");
        c3EditAccInfoPage.setDeliveryStAddress("333 Market str " + RandomStringUtils.randomAlphanumeric(3));
        c3EditAccInfoPage.setDeliveryCity("San Francisco");
        c3EditAccInfoPage.setDeliveryState("CA");
        c3EditAccInfoPage.setDeliveryZip("94133");
        c3EditAccInfoPage.clickSubmit();
    }

    public void updateEmailForCustomer(String email, String confirmEmail) {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        c3EditAccInfoPage.setEmail(email);
        c3EditAccInfoPage.setConfirmEmail(confirmEmail);
        c3EditAccInfoPage.clickSubmit();
    }

    public void updateEmailWithAlreadyRegisteredUser() {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        c3EditAccInfoPage.setEmail("mishak.gill@gmail.com");
        c3EditAccInfoPage.setConfirmEmail("mishak.gill@gmail.com");
        c3EditAccInfoPage.clickSubmit();
    }

    public void verifyConfirmationForUpdatedContactDetailsForCustomer() {
        C3EditAccInfoPage c3EditAccInfoPage = new C3EditAccInfoPage(getWebdriverInstance());
        assertThat(c3EditAccInfoPage.getMessage())
                .contains("The account for " + customerInfo.getEmail() + " was successfully updated.")
                .as("Confirmation message");
    }

    public void createAccountOnDomesticSite() {
        customerInfo.setCustomerType("new");
        customerInfo.setCustomerRandomEmail();
        new HWGlobalHeader(getWebdriverInstance()).goToSignInPage();
        HWRegisterForm hwRegisterForm = new HWRegisterForm(getWebdriverInstance());
        hwRegisterForm.setCustomerNames(customerInfo.getFirstName(), customerInfo.getLastName());
        hwRegisterForm.setCountry(customerInfo.getCountryCode());
        hwRegisterForm.setPostalCode(customerInfo.getZipCode());
        hwRegisterForm.setEmail(customerInfo.getEmail());
        hwRegisterForm.setPassword(customerInfo.getPassword());
        hwRegisterForm.submit();
//        verify Thanks for registering message
        HWThanksForRegister hwThanksForRegister = new HWThanksForRegister(getWebdriverInstance());
        assertThat(hwThanksForRegister.getTitle()).isEqualTo("Thanks for registering!");
        assertThat(hwThanksForRegister.getMsg())
                .isEqualTo("Hotwire has brands you know and trust at prices lower than other travel sites.");
        hwThanksForRegister.proceed();
    }

    public void createNewCustomerInC3() {
        customerInfo.setC3CustomerRandomEmail();
        new C3IndexPage(getWebdriverInstance()).clickCreateCustomer();
        C3CreateCustomerPage createCustomerPage = new C3CreateCustomerPage(getWebdriverInstance());
        createCustomerPage.setFullName(customerInfo.getFirstName(), customerInfo.getLastName());
        createCustomerPage.setEmail(customerInfo.getEmail());
        createCustomerPage.setAddress(customerInfo.getZipCode());
        createCustomerPage.submit();
    }

    public void loginAsCustomerWithKnownCredentials() {
        C3CustomerDao c3CustomerDao = new C3CustomerDao(getDataBaseConnection());
        if (!c3CustomerDao.isTestCustomerExist()) {
            createAccountOnDomesticSite();
        }
        else {
            Map customer = c3CustomerDao.getTestCustomer();
            customerInfo.setEmail(customer.get("EMAIL").toString());
            customerInfo.setCustomerName(customer);
            attemptToAuthenticateUser(customerInfo.getEmail(), customerInfo.getPassword());
            customerInfo.setCustomerTypeByEmail(customerInfo.getEmail());
        }
    }

    public void attemptToAuthenticateUser(String user, String password) {
        AbstractUSPage page = new AbstractUSPage(getWebdriverInstance());
        page.getGlobalHeader()
                .navigateToSignInPage()
                .withUserName(user)
                .withPassword(password)
                .signIn();
    }

    public void loginOnSiteWithExpressCustomer(boolean isExpress) {
        CustomerInfo user = nonExpressCustomer;
        if (isExpress) {
            user = expressCustomer;
            customerInfo.setCustomerType(CustomerInfo.CustomerTypes.EXPRESS);
        }
        else {
            customerInfo.setCustomerType(CustomerInfo.CustomerTypes.NON_EXPRESS);
        }
        customerInfo.setEmail(user.getEmail());
        attemptToAuthenticateUser(user.getEmail(), user.getPassword());
    }

    public void verifyUserExistsAndIsAuthenticated(String nullOrNot) {
        boolean expectedState = "not".equalsIgnoreCase(nullOrNot) ? false : true;
        GlobalHeader header = new GlobalHeader(getWebdriverInstance());
        boolean actualState =  header.isLoggedIn();
        assertThat(actualState)
                .as("Expected user authentication to be " + expectedState + " but was " + actualState)
                .isEqualTo(expectedState);
    }

    public void goToMyTrips() {
        new HWGlobalHeader(getWebdriverInstance()).goToMyTrips();
    }

    public void openMyLastBookedTrip() {
        new HWMyTrips(getWebdriverInstance()).selectLastBooking();
    }

    public void authenticateWith4LastDigits() {
        new HWGlobalHeader(getWebdriverInstance()).goToSignInPage();
        new Last5DigitsSignInFragment(getWebdriverInstance()).signIn(customerInfo.getEmail(), "11111");
        new HWMyTrips(getWebdriverInstance());
    }

}


