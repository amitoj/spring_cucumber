/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.post.purchase.car;

import com.hotwire.selenium.desktop.us.confirm.CarConfirmationPage;
import com.hotwire.selenium.desktop.us.confirm.PrintTripDetailsPage;
import com.hotwire.selenium.desktop.us.confirm.PrintVersionConfirmationPage;
import com.hotwire.selenium.desktop.us.confirm.ShareItineraryPage;
import com.hotwire.selenium.desktop.widget.ErrorMessenger;
import com.hotwire.test.steps.post.purchase.PostPurchaseModelTemplate;
import org.openqa.selenium.WebDriver;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-ngolodiuk
 * Since: 8/21/14
 */

public class CarPostPurchaseModelWebApp extends PostPurchaseModelTemplate {

    @Override
    public void clickToSeePrintVersion() {
        CarConfirmationPage carConfirmationPage = new CarConfirmationPage(getWebdriverInstance());
        carConfirmationPage.getPrintReceipt().click();
    }

    @Override
    public void verifyPrintVersionPage() {
        freeze(5);   /*Wait while all popup windows were loaded... Doesn't work without it.*/
        String rootWindowHandler = getWebdriverInstance().getWindowHandle();

        // Get handler of partner's popup windows and wait while inner content was appear
        for (String h : getWebdriverInstance().getWindowHandles()) {
            if (!h.equals(rootWindowHandler) && !h.isEmpty()) {
                WebDriver printVersionPage = getWebdriverInstance().switchTo().window(h);
                PrintVersionConfirmationPage printVersionConfirmationPage =
                        new PrintVersionConfirmationPage(getWebdriverInstance());
                assertThat(printVersionConfirmationPage.getURL()
                        .contains("/car/confirm-receipt-print.jsp"))
                        .as("Popup page with print version of car confirmation contains: " +
                                "/car/confirm-receipt-print.jsp")
                        .isTrue();
                // If we try to verify params in "for" statement when case will be failed then all other popups
                // will not be closed.
                printVersionPage.close();
            }
        }
    }

    @Override
    public void verifyCancelButtonShareItineraryWindow() {
        CarConfirmationPage confirmationPage = new CarConfirmationPage(getWebdriverInstance());
        confirmationPage.getShareItinerary().click();
        ShareItineraryPage shareItineraryPage = new ShareItineraryPage(getWebdriverInstance());
        assertThat(shareItineraryPage.isVisible()).as("Share itinerary pop-up window is visible").isTrue();
        shareItineraryPage.getCancelButton().click();
        assertThat(shareItineraryPage.isVisible()).as("Share itinerary pop-up window is visible").isFalse();
    }

    @Override
    public void shareCarItineraryViaEmail(String email) {
        CarConfirmationPage confirmationPage = new CarConfirmationPage(getWebdriverInstance());
        confirmationPage.getShareItinerary().click();
        ShareItineraryPage shareItineraryPage = new ShareItineraryPage(getWebdriverInstance());
        shareItineraryPage.shareItinerary(email);
        assertThat(shareItineraryPage.isSharedSuccessfully()).as("Itinerary was successfully shared").isTrue();
        shareItineraryPage.getContinueButton().click();
    }

    @Override
    public void shareItineraryWithBadAndGoodEmails() {
        String err = "We're sorry but one of the email addresses you entered was invalid: [DYNAMIC][WRONG_EMAIL]" +
                "[/DYNAMIC]. Be sure that email addresses contain no spaces, one '@' sign and are seperated by commas.";
        CarConfirmationPage confirmationPage = new CarConfirmationPage(getWebdriverInstance());
        confirmationPage.getShareItinerary().click();
        ShareItineraryPage shareItineraryPage = new ShareItineraryPage(getWebdriverInstance());
        shareItineraryPage.shareItinerary("bad_email");
        applicationModel.verifyMessageOnPage(err, ErrorMessenger.MessageType.valueOf("error"));
        shareItineraryPage.shareItinerary("goog_email@hotwire.com");
        assertThat(shareItineraryPage.isSharedSuccessfully()).as("Itinerary was successfully shared").isTrue();
        shareItineraryPage.getContinueButton().click();
    }

    @Override
    public void shareItineraryForMultiplicityEmails(String firstEmail, String secondEmail) {
        CarConfirmationPage confirmationPage = new CarConfirmationPage(getWebdriverInstance());
        confirmationPage.getShareItinerary().click();
        ShareItineraryPage shareItineraryPage = new ShareItineraryPage(getWebdriverInstance());
        shareItineraryPage.shareItinerary(firstEmail + ", " + secondEmail);
        assertThat(shareItineraryPage.isSharedSuccessfully()).as("Itinerary was successfully shared").isTrue();
        shareItineraryPage.getContinueButton().click();
    }

    @Override
    public void verifyPrintTripDetailsPage() {
        CarConfirmationPage confirmationPage = new CarConfirmationPage(getWebdriverInstance());
        confirmationPage.getPrintTripDetails().click();
        freeze(5);   /*Wait while all popup windows were loaded... Doesn't work without it.*/
        String rootWindowHandler = getWebdriverInstance().getWindowHandle();
        for (String h : getWebdriverInstance().getWindowHandles()) {
            if (!h.equals(rootWindowHandler) && !h.isEmpty()) {
                WebDriver printTripDetailsWindow = getWebdriverInstance().switchTo().window(h);
                PrintTripDetailsPage printTripDetailsPage = new PrintTripDetailsPage(printTripDetailsWindow);
                assertThat(printTripDetailsPage.getHotwireItinerary().equals(confirmationPage.getHotwireItinerary()))
                        .isTrue();
                assertThat(printTripDetailsPage.getConfirmationCode().equals(confirmationPage.getConfirmationCode()))
                        .isTrue();
                assertThat(printTripDetailsPage.getPickupDate().equals(confirmationPage.getPickupDate())).isTrue();
                assertThat(printTripDetailsPage.getDropoffDate().equals(confirmationPage.getDropoffDate())).isTrue();
                assertThat(printTripDetailsPage.getDriverName().equals(confirmationPage.getDriverName())).isTrue();
                assertThat(printTripDetailsPage.getCarModels().equals(confirmationPage.getCarModels())).isTrue();
                assertThat(printTripDetailsPage.getTotalPrice() == confirmationPage.getTotalPrice()).isTrue();
                assertThat(printTripDetailsPage.getContactEmail().equals(confirmationPage.getContactEmail())).isTrue();
                assertThat(printTripDetailsPage.getContactPhone().equals(confirmationPage.getContactPhone())).isTrue();
                assertThat(printTripDetailsPage.getCardNumber().equals(confirmationPage.getCardNumber())).isTrue();
                assertThat(printTripDetailsPage.getPurchaseDate().equals(confirmationPage.getPurchaseDate())).isTrue();
                assertThat(printTripDetailsPage.getPrintButtonTop().isDisplayed()).isTrue();
                assertThat(printTripDetailsPage.getURL().endsWith("/confirm-print.jsp")).isTrue();
                printTripDetailsWindow.close();
            }
        }
    }

}
