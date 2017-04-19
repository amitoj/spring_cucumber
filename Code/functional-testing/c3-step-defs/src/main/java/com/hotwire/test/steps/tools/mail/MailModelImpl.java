/*
 *  Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.mail;

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
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.TripInfo;
import com.hotwire.test.steps.tools.bean.c3.C3CsrAccount;
import com.hotwire.test.steps.tools.bean.c3.C3HotelSupplyInfo;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Mail model implementation
 */
public class MailModelImpl extends ToolsAbstractModel implements MailModel {
    private static final Integer EMAIL_DELAY = 40;
    private static final Integer AMOUNT_OF_LAST_MESSAGES = 10;

    private static Logger LOGGER = LoggerFactory.getLogger(MailModelImpl.class);
    private String host;
    private String user;
    private String password;
    private boolean textIsHtml;

    @Autowired
    private C3CsrAccount c3CsrAccount;

    @Autowired
    private TripInfo tripInfo;

    @Autowired
    private C3HotelSupplyInfo c3HotelSupplyInfo;

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

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

    public String getMessageTXT(Message message) {
        String msgText;
        try {
            msgText = message.getContent().toString();
            return msgText;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Email text wasn't found";
        }
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
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props);

        MailFolder inboxFolder = box.getInbox();
        long last2days = Now.minus(Period.days(2.1));

        SearchTerm serverSideSrch = MailSearch.ssContains(null, subject);

        Message[] msgs = inboxFolder.search(last2days, Now.rightNow(), serverSideSrch, null, AMOUNT_OF_LAST_MESSAGES);
        MailWb.echo(msgs);
        return  msgs;
    }

    public Message[] findMessages(Mailbox box) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props);

        MailFolder inboxFolder = box.getInbox();
        long last2days = Now.minus(Period.days(2.1));

        SearchTerm serverSideSrch = MailSearch.ssContains(null, null);

        Message[] msgs = inboxFolder.search(last2days, Now.rightNow(), serverSideSrch, null, AMOUNT_OF_LAST_MESSAGES);
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
        HwQaTestUser testUser = HwQaTestUser.QaRegression;
        Mailbox mb = HwCorp.MailMsExchange.openMailbox(testUser);
        MailFolder mf = mb.getInbox();
        SearchTerm passCriteria = MailSearch.ssContains(from, subject);
        LOGGER.info("Looking for emails sent by: " + from + " with subject " + subject);
        Message[] found = mf.waitForMessages(Now.minus(Period.minutes(startTime)),
                                             passCriteria,
                                             null,
                                             count,
                                             Timeout.of(Period.minutes(1)));
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
    public void checkEmail(String subject) {
        assertThat(findMessages(subject,  connectToMailBox(getUser(), getPassword()))).isNotNull();
    }

    @Override
    public void verifyTextInEmail(String subject, String expectedText) {
        Message[] messages = findMessages(subject,  connectToMailBox(getUser(), getPassword()));
        assertThat(getMessageHTML(messages[0])).contains(expectedText);
    }

    @Override
    public void verifyTextInEmail(String expectedText) throws Exception {
        Thread.sleep(10000);
        getMessagesWithText(expectedText);
    }

    private List<Message> getMessagesWithText(String expectedText) throws Exception {
        Message[] messages = findMessages(connectToMailBox(getUser(), getPassword()));
        List<Message> result = new ArrayList<>();
        for (int i = 0; i < messages.length; i++) {
            if (getMessageHTML(messages[i]).contains(expectedText)) {
                result.add(messages[i]);
            }
        }
        if (result.size() == 0) {
            throw new Exception(expectedText + " not found in last emails");
        }
        return result;
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

        PasswordAssistancePage assistancePage = new PasswordAssistancePage(getWebdriverInstance());

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
    public void verifyTemporaryPasswordEmailForCSRAccount() {
        String subject = "Hotwire Customer Care Central login information";
        Message[] messages = findMessages(subject, connectToMailBox(getUser(), getPassword()));
        String textOfEmail = getMessageTXT(messages[0]);
        assertThat(textOfEmail).as("Text of email is correct")
                .contains("At your request, your Customer Care site password has been reset.");
        assertThat(textOfEmail).as("Text of email contains temporary password").contains("Temporary Password:");
        assertThat(textOfEmail).as("Text of email contains user name")
                .contains("Username: " + c3CsrAccount.getAgentName());
    }

    @Override
    public void verifyPasswordAssistanceEmail() {
        String subject = "Hotwire password assistance";
        Message[] messages = findMessages(subject, connectToMailBox(getUser(), getPassword()));
        String textOfEmail = getMessageHTML(messages[0]);
        assertThat(textOfEmail).as("Text of email is correct")
                .contains("We received your request for password assistance. " +
                        "To create a new password using our secure server, " +
                        "simply click on the link or copy it into your browser's address bar:");
        assertThat(textOfEmail).as("Text of email is correct")
                .contains("To ensure your security, this link will be active for only 24 hours.  " +
                        "If this link does not take you to a page prompting you to create a new password, " +
                        "please make sure you are using the latest version of your browser. " +
                        "If you continue to experience problems with the link, " +
                        "check your Internet browser help information. Then click on the link again.");
        assertThat(textOfEmail).as("Text of email is correct")
                .contains("At Hotwire, we are committed to protecting the privacy of your information. " +
                        "We've taken the additional precaution of removing any stored credit card information " +
                        "from your account. You'll have the option to enter your new credit card information " +
                        "the next time you book.");
        assertThat(textOfEmail).as("Text of email is correct")
                .contains("If you have any questions, we'll be happy to assist you. We can be reached at the email " +
                        "and phone number listed in the contact information.");
        assertThat(textOfEmail).as("Text of email is correct")
                .contains("We hope to see you back on Hotwire soon.");
        assertThat(textOfEmail).as("Text of email is correct")
                .contains("Sincerely, <br>Hotwire Customer Care");
    }

    @Override
    public void verifyEmailForRecentPartialRefund(String itineraryNumber, String refundAmount) {
        String subject = "Hotwire refund confirmation";
        Message[] messages = findMessages(subject, connectToMailBox(getUser(), getPassword()));
        String textOfEmail = getMessageHTML(messages[0]);
        assertThat(textOfEmail).as("Itinerary number is correct").contains(itineraryNumber);
        assertThat(textOfEmail).as("Amount of refund is correct").contains(refundAmount);
    }

    private String removeAdvertisement(String email) {
        Integer firstIndex  = email.indexOf("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"" +
                " align=\"left\" class=\"x_templateColumnLeftMainContent\"");
        Integer lastIndex  = email.indexOf("</table>", firstIndex) + "</table>".length();
        return email.substring(firstIndex, lastIndex);
    }

    @Override
    public void verifyWeHaveTwoConfirmationHotelEmailsWithSameInformation() {
        DateFormat formatterDateConfPg = new SimpleDateFormat("EEE, MMM dd, yyyy");
        DateFormat formatterDateEmailPg = new SimpleDateFormat("EEE, MMM d, yyyy");
        Date startDate_d;
        Date endDate_d;

        try {
            startDate_d =  formatterDateConfPg.parse(c3HotelSupplyInfo.getCheckInDate());
            endDate_d =  formatterDateConfPg.parse(c3HotelSupplyInfo.getCheckOutDate());
        }
        catch (ParseException e) {
            throw new RuntimeException("Wrong format of check-in check-out dates on confirmation page");
        }

        String startDate = formatterDateEmailPg.format(startDate_d) + " 3:00 PM";
        String endDate = formatterDateEmailPg.format(endDate_d) + " 12:00 PM";

        List<Message> messages;
        try {
            messages = getMessagesWithText(c3ItineraryInfo.getItineraryNumber());
        }
        catch (Exception e) {
            throw new RuntimeException("Couldn't find email with itinerary " + c3ItineraryInfo.getItineraryNumber());
        }
        String emailText1 = getMessageHTML(messages.get(0));
        String emailText2 = getMessageHTML(messages.get(1));

        assertThat(emailText1).as("Original mail doesn't contain check-in date").containsIgnoringCase(startDate);
        assertThat(emailText1).as("Original mail doesn't contain check-out date").containsIgnoringCase(endDate);
        emailText1 = emailText1.replaceAll(",", "");
        assertThat(emailText1).as("Original mail doesn't contain total cost amount")
                .containsIgnoringCase(c3ItineraryInfo.getTotalCost().toString());

        assertThat(emailText2).as("Re-sended confirmation mail doesn't contain check-in date")
                .containsIgnoringCase(startDate);
        assertThat(emailText2).as("Re-sended confirmation mail doesn't contain check-out date")
                .containsIgnoringCase(endDate);
        emailText2 = emailText2.replaceAll(",", "");
        assertThat(emailText2).as("Re-sended confirmation mail doesn't contain total cost amount")
                .containsIgnoringCase(c3ItineraryInfo.getTotalCost().toString());
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
}
