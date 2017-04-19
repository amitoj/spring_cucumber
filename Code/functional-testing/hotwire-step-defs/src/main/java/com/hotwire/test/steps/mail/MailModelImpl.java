/*
 *  Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.mail;

import com.hotwire.qa.Now;
import com.hotwire.qa.client.mail.CsSearch;
import com.hotwire.qa.client.mail.MailDelete;
import com.hotwire.qa.client.mail.MailFolder;
import com.hotwire.qa.client.mail.MailSearch;
import com.hotwire.qa.client.mail.MailWb;
import com.hotwire.qa.client.mail.Mailbox;
import com.hotwire.qa.data.Period;
import com.hotwire.qa.systems.HwCorp;
import com.hotwire.qa.systems.HwQaTestUser;
import com.hotwire.qa.util.Timeout;
import com.hotwire.selenium.desktop.account.PasswordAssistancePage;
import com.hotwire.selenium.desktop.us.ZipUpdatePage;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;
import com.hotwire.test.steps.search.hotel.HotelSearchParameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.search.SearchTerm;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Mail model implementation
 */
public class MailModelImpl extends WebdriverAwareModel implements MailModel {
    private static final Integer EMAIL_DELAY = 40;

    private static Logger LOGGER = LoggerFactory.getLogger(MailModelImpl.class);
    private String host;
    private String user;
    private String password;
    private String emailAccount;
    private String newZipCode;
    private boolean textIsHtml;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setAuthParameters(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public String getMessageHTML(Message message) {
        Object msgContent;

        try {
            msgContent = message.getContent();
            if (msgContent instanceof Multipart) {
                Multipart mp = (Multipart) msgContent;
                for (int i = 0; i < mp.getCount(); i++) {
                    BodyPart bp = mp.getBodyPart(i);

                    try {
                        setTextIsHtml(false);
                        if (!getText(bp).isEmpty() && isTextHtml()) {
                            return getText(bp);
                        }
                    }
                    catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }

        return "Something went wrong";
    }

    private String getText(Part p) throws
            MessagingException, IOException {

       // boolean textIsHtml = false;
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            setTextIsHtml(p.isMimeType("text/html"));
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null) {
                        text = getText(bp);
                    }
                    continue;
                }
                else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null) {
                        return s;
                    }
                }
                else {
                    return getText(bp);
                }
            }
            return text;
        }
        else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null) {
                    return s;
                }
            }
        }

        return null;
    }

    public Message[] findMessages(String subject, Mailbox box) {
        MailFolder inboxFolder = box.getInbox();
        long last2days = Now.minus(Period.days(2.1));

        SearchTerm serverSideSrch = MailSearch.ssContains(null, subject);

        Message[] msgs = inboxFolder.search(last2days, Now.rightNow(), serverSideSrch, null, 10);
        MailWb.echo(msgs);
        return  msgs;

    }

    public Mailbox connectToMailBox(String userLogin, String userPassword) {
        return  HwCorp.MailMsExchange.openMailbox(userLogin, userPassword);
    }


    @Override
    public boolean assertEmailReceived(String from,
                                       String subject,
                                       Integer startTime,
                                       Integer count,
                                       List<String> body) {
        LOGGER.info("Asserting emails");
        if (subject.contains("date")) {
            subject = replaceWithCurrentDate(subject);
        }
        //parameters in an email
        LOGGER.info("from:" + from);
        LOGGER.info("subject:" + subject);
        LOGGER.info("startTime:" + startTime);
        LOGGER.info("count:" + count);
        LOGGER.info("Body Contains:");
        for (String text:body) {
            LOGGER.info(text);
        }
        HwQaTestUser testUser = HwQaTestUser.TestUser1;
        Mailbox mb = HwCorp.MailMsExchange.openMailbox(testUser);
        MailFolder mf = mb.getInbox();
        SearchTerm passCriteria = MailSearch.ssContains(from, subject);
        CsSearch csContains = MailSearch.and(bodyContains(body));
        LOGGER.info("Looking for emails sent by: " + from + " with subject " + subject);
        Message[] found = mf.waitForMessages(Now.minus(Period.minutes(startTime)),
                                             passCriteria,
                                             csContains,
                                             count,
                                             Timeout.of(Period.minutes(5)));
        mb.close();
        if (found.length >= count) {
            LOGGER.info("emails found");
            deleteMails(from, subject, startTime, count);
            return true;
        }
        else {
            LOGGER.warn("No emails");
            return false;
        }
    }

    @Override
    public boolean assertEmailReceived(String from,
                                       String subject,
                                       Integer startTime,
                                       Integer count) {
        LOGGER.info("Asserting emails");
        if (subject.contains("date")) {
            subject = replaceWithCurrentDate(subject);
        }
        //parameters in an email
        LOGGER.info("from:" + from);
        LOGGER.info("subject:" + subject);
        LOGGER.info("startTime:" + startTime);
        LOGGER.info("count:" + count);
        LOGGER.info("Body Contains:");
        HwQaTestUser testUser = HwQaTestUser.TestUser1;
        Mailbox mb = HwCorp.MailMsExchange.openMailbox(testUser);
        MailFolder mf = mb.getInbox();
        SearchTerm passCriteria = MailSearch.ssContains(from, subject);
        LOGGER.info("Looking for emails sent by: " + from + " with subject " + subject);
        Message[] found = mf.waitForMessages(Now.minus(Period.minutes(startTime)),
                                             passCriteria,
                                             null,
                                             count,
                                             Timeout.of(Period.minutes(5)));
        mb.close();
        if (found.length >= count) {
            LOGGER.info("emails found");
            deleteMails(from, subject, startTime, count);
            return true;
        }
        else {
            LOGGER.warn("No emails");
            return false;
        }
    }


    @Override
    public void checkEmail(String subject) {
        assertThat(findMessages(subject,  connectToMailBox(getUser(), getPassword()))).isNotNull();
    }

    @Override
    public void verifyTextInEmail(String subject, String expectedText) {
        Message[] messages = findMessages(subject,  connectToMailBox(getUser(), getPassword()));
        assertThat(getMessageHTML(messages[0])).contains(expectedText);

    }

    @Override
    public void verifyCarConfirmationLetter(AuthenticationParameters authenticationParameters,
                                            CarSearchParameters carSearchParameters,
                                            PurchaseParameters purchaseParameters) {

        DateFormat formatterDate1 = new SimpleDateFormat("EEE, MMM d, yyyy");
        DateFormat formatterDate2 = new SimpleDateFormat("MM/dd/yy");

        String startDate = formatterDate1.format(carSearchParameters.getGlobalSearchParameters().getStartDate()) +
                " at " + carSearchParameters.getPickupTime();
        String endDate = formatterDate1.format(carSearchParameters.getGlobalSearchParameters().getEndDate()) +
                " at " + carSearchParameters.getDropoffTime();

        String shortLocation = carSearchParameters
                .getGlobalSearchParameters().getDestinationLocation().split(",")[0].trim();

        //for the correct work location should contains name of the city
        // and state after a comma( e.g. San Francisco, CA - (SFO) )
        String subject = "Hotwire Car Purchase Confirmation - B. Nyxvxryegsls " + shortLocation + " " +
                formatterDate2.format(carSearchParameters.getGlobalSearchParameters().getStartDate());


        Message[] messages = findMessages(subject,  connectToMailBox(getUser(), getPassword()));
        String emailText = getMessageHTML(messages[0]);  //take the first message

        assertThat(emailText).containsIgnoringCase(purchaseParameters.getDisplayNumber());

        assertThat(emailText).containsIgnoringCase(authenticationParameters.getUsername());

        assertThat(emailText).containsIgnoringCase(purchaseParameters.getUserInformation().getFirstName() +
                " " + purchaseParameters.getUserInformation().getLastName());


        String carModel  = carSearchParameters.getSelectedSearchSolution().getCarModels().split("or")[0].trim();
        assertThat(emailText).containsIgnoringCase(carSearchParameters.getSelectedSearchSolution().getCarName());
        assertThat(emailText).containsIgnoringCase(carModel);

        assertThat(emailText).containsIgnoringCase(startDate);
        assertThat(emailText).containsIgnoringCase(endDate);
        assertThat(emailText).containsIgnoringCase(shortLocation);

        emailText = emailText.replaceAll(",", "");

        assertThat(emailText).containsIgnoringCase(String.valueOf(carSearchParameters.getSelectedSearchSolution()
                .getTotalPrice()));
        assertThat(emailText).containsIgnoringCase(String.valueOf(carSearchParameters.getSelectedSearchSolution()
                .getRentalDaysCount()));
        assertThat(emailText).containsIgnoringCase(String.valueOf(carSearchParameters.getSelectedSearchSolution()
                .getPerDayPrice()));
        assertThat(emailText).containsIgnoringCase(String.valueOf(carSearchParameters.getSelectedSearchSolution()
                .getTaxesAndFees()));
        assertThat(emailText).containsIgnoringCase(String.valueOf(carSearchParameters.getSelectedSearchSolution()
                .getDamageProtection()));

        LOGGER.info("Car confirmation letter contains correct information");
    }


    @Override
    public void verifyHotelConfirmationLetter(AuthenticationParameters authenticationParameters,
                                              HotelSearchParameters hotelSearchParameters,
                                              PurchaseParameters purchaseParameters) {

        DateFormat formatterDate1 = new SimpleDateFormat("EEE, MMM d, yyyy");

        String startDate = formatterDate1.format(hotelSearchParameters.getGlobalSearchParameters().getStartDate()) +
                " 3:00 PM";
        String endDate = formatterDate1.format(hotelSearchParameters.getGlobalSearchParameters().getEndDate()) +
                " 12:00 PM";

        String subject = "Hotwire Hotel Purchase Confirmation - B. Nyxvxryegsls ";


        Message[] messages = findMessages(subject,  connectToMailBox(getUser(), getPassword()));
        String emailText = null;
        for (int i = 0; i < messages.length; i++) {
            emailText = getMessageHTML(messages[i]);
            if (emailText.contains(purchaseParameters.getItinerary())) {
                //we found email with necessary intinerary number
                break;
            }
            emailText = null;
        }

        if (null == emailText) {
            throw new RuntimeException("Hotel purchase confirmation email with " + purchaseParameters.getItinerary() +
                    " intinerary number,  wasn't found");
        }

        assertThat(emailText).containsIgnoringCase(purchaseParameters.getItinerary());

        assertThat(emailText).containsIgnoringCase(authenticationParameters.getUsername());
        assertThat(emailText).containsIgnoringCase(startDate);
        assertThat(emailText).containsIgnoringCase(endDate);
        emailText = emailText.replaceAll(",", "");
        assertThat(emailText).containsIgnoringCase(hotelSearchParameters.getSelectedSearchSolution()
                .getPrice()); //per day
        assertThat(emailText).containsIgnoringCase(purchaseParameters.getBillingTotal());
        LOGGER.info("Hotel confirmation letter contains correct information");
    }


    public void verifyCarShareItineraryLetter(AuthenticationParameters authenticationParameters,
                                            CarSearchParameters carSearchParameters,
                                            PurchaseParameters purchaseParameters,
                                            String userLogin,
                                            String userPassword) {

        DateFormat formatterDate1 = new SimpleDateFormat("EEE, MMM d, yyyy");

        String startDate = formatterDate1.format(carSearchParameters.getGlobalSearchParameters().getStartDate()) +
                " at " + carSearchParameters.getPickupTime();
        String endDate = formatterDate1.format(carSearchParameters.getGlobalSearchParameters().getEndDate()) +
                " at " + carSearchParameters.getDropoffTime();

        String shortLocation = carSearchParameters
                .getGlobalSearchParameters().getDestinationLocation().split(",")[0].trim();

        //for the correct work location should contains name of the city
        // and state after a comma( e.g. San Francisco, CA - (SFO) )
        String subject = "Test Booking's Rental Car Itinerary from Hotwire";

        Message[] messages = findMessages(subject, connectToMailBox(userLogin, userPassword));
        String emailText = getMessageHTML(messages[0]);  //take the first message

        assertThat(emailText).containsIgnoringCase(purchaseParameters.getDisplayNumber());

        assertThat(emailText).containsIgnoringCase(authenticationParameters.getUsername());

        assertThat(emailText).containsIgnoringCase(purchaseParameters.getUserInformation().getFirstName() +
                " " + purchaseParameters.getUserInformation().getLastName());

        String carModel  = carSearchParameters.getSelectedSearchSolution().getCarModels().split("or")[0].trim();
        assertThat(emailText).containsIgnoringCase(carSearchParameters.getSelectedSearchSolution().getCarName());
        assertThat(emailText).containsIgnoringCase(carModel);

        assertThat(emailText).containsIgnoringCase(startDate);
        assertThat(emailText).containsIgnoringCase(endDate);
        assertThat(emailText).containsIgnoringCase(shortLocation);

        LOGGER.info("Car share itinerary letter contains correct information");
    }


    @Override
    public void goToResetPasswordPageFromEmail(String userLogin, String userPassword) {

        String subject = "Hotwire password assistance";

        Message[] messages = findMessages(subject, connectToMailBox(getUser(), getPassword()));
        String emailText = getMessageHTML(messages[0]);  //take the first message


        getWebdriverInstance().navigate().to("https://" +
                emailText.split("https://")[1].split(new Character((char) 34).toString())[0]);


    }

    @Override
    public void verifyOldFromHotwirePasswordAssistanceEmailGenerateError(String userLogin, String userPassword) {
        String subject = "Hotwire password assistance";

        Message[] messages = findMessages(subject, connectToMailBox(getUser(), getPassword()));
        String textOfTheNewEmail = getMessageHTML(messages[0]);
        String textOfTheOldEmail = getMessageHTML(messages[1]);

        getWebdriverInstance().navigate().to("https://" +
                textOfTheNewEmail.split("https://")[1].split(new Character((char) 34).toString())[0]);

        new PasswordAssistancePage(getWebdriverInstance());

        getWebdriverInstance().navigate().to("https://" +
                textOfTheOldEmail.split("https://")[1].split(new Character((char) 34).toString())[0]);

        assertThat(getWebdriverInstance().getPageSource().contains("We couldn't process your request"))
                .as("The url from old hotwire assistance email generate en error")
                .isTrue();

        assertThat(getWebdriverInstance().getPageSource().contains("Thank you for visiting Hotwire." +
                " We're always working to improve our site. Occasionally, we reorganize our pages," +
                " and as a result, bookmarks you have set for our site may no longer be accurate."))
                .as("Error message for the inexistent page is visible")
                .isTrue();

    }

    @Override
    public void verifyRetailCarCancellationLetter(PurchaseParameters purchaseParameters,
                                                    String userLogin, String userPassword) {
        String subject = "Hotwire cancellation confirmation";

        Message[] messages = findMessages(subject, connectToMailBox(getUser(), getPassword()));
        String emailText = getMessageHTML(messages[0]);

        assertThat(emailText).containsIgnoringCase(purchaseParameters.getDisplayNumber() + " has been cancelled");
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTextHtml() {
        return textIsHtml;
    }

    public void setTextIsHtml(boolean textIsHtml) {
        this.textIsHtml = textIsHtml;
    }


    private String replaceWithCurrentDate(String subject) {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        String newSubject = subject.replaceAll("date", dateFormatter.format(startDate.getTime()));
        return newSubject;

    }

    private CsSearch[] bodyContains(List<String> bodyText) {
        ArrayList<CsSearch> csBody = new ArrayList<>();
        for (int i = 0; i < bodyText.size(); i++) {
            csBody.add(i, MailSearch.csBodyContains(bodyText.get(i)));
        }
        CsSearch[] realArray = csBody.toArray(new CsSearch[csBody.size()]);
        return realArray;
    }

    private void deleteMails(String from, String subject, Integer startTime, Integer count) {
        LOGGER.info("DELETING EMAILS WITH SUBJECT:- " + subject);
        HwQaTestUser testUser = HwQaTestUser.TestUser1;
        Mailbox mb = HwCorp.MailMsExchange.openMailbox(testUser);
        MailFolder mf = mb.getInbox();
        SearchTerm passCriteria = MailSearch.ssContains(from, subject);
        long since = System.currentTimeMillis() - startTime * 60 * 1000;
        if (subject.contains("Your Hotwire Trip") || subject.contains("Hotel Inventory")) {
            count = 50;
        }
        Message[] found = mf.waitForMessages(since, passCriteria, null, count, Timeout.of(Period.minutes(1)));
        LOGGER.info("Deleteing " + found.length + " emails found");
        MailWb.delete(found, MailDelete.class);
    }

    @Override
    public void updateZipCode(String zipCode) {
        this.newZipCode = zipCode;
        ZipUpdatePage updatePage = new ZipUpdatePage(getWebdriverInstance());
        this.emailAccount = updatePage.getEmail();
        updatePage.enterZipCode(zipCode);
        updatePage.clickSaveChanges();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void newZipcodeIsReflectedInCUSTOMERTable() {
        List<String> listResult;
        String sql = "select register_zip from customer where email = '" + emailAccount + "'";
        listResult = jdbcTemplate.queryForList(sql, String.class);
        assertThat(listResult.get(0).contains(newZipCode)).as(
            "the " + newZipCode + " Zipcode for the customer should be reflected in CUSTOMER table").isTrue();
    }

    @Override
    public void dbmSignUpPageShouldBeDisplayed() {
        ZipUpdatePage updatePage = new ZipUpdatePage(getWebdriverInstance());
        assertThat(updatePage.getDBMHeader().equals("Sign up for Hotwire Travel Deals")).as(
            "DBM sign up page should be displayed").isTrue();
    }

    /**
     * @return
     */
    public String getEmailHTML(String subject) {

        //String subject = "Hotwire hotel purchase confirmation";

        System.out.println("getEmailHTML has been accessed");
        String userName = "qa_regression";
        String inboxPassword = "Hotwire655!";

        Message[] messages = findMessages(subject, connectToMailBox(userName, inboxPassword));
        String emailText = getMessageHTML(messages[0]);
        System.out.println("end of method reached");

        return emailText;
    }
}
