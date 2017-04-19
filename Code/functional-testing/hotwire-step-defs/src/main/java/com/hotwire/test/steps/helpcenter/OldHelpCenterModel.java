/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.helpcenter;

import com.hotwire.selenium.desktop.helpCenter.HCContactUsConfirmation;
import com.hotwire.selenium.desktop.helpCenter.HCContactUsForm;
import com.hotwire.selenium.desktop.helpCenter.HCIndexPage;
import com.hotwire.selenium.desktop.helpCenter.HCSubcategoryPage;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * User: v-abudyak
 * Date: 7/22/13
 */
public class OldHelpCenterModel extends HelpCenterModel {

    @Override
    public void clickSendUsEmailLink() {
        new HCIndexPage(getWebdriverInstance()).clickSendUsEmailLink();
    }

    public void checkSendUsEmailAction(String customerType) {
        if (customerType.equals("non-express")) {
            Assertions.assertThat(getWebdriverInstance().getCurrentUrl()).
                    as("Help Center contact form isn't present").
                    contains("/helpcenter/contact.jsp");
        }
        else {
            verifyMailTo("hotwireexpress@hotwire.com");
        }
    }

    public void verifyMailTo(String email) {
        String template = "a[href*='mailto:%s']";
        String emailLink = String.format(template, email);
        getWebdriverInstance().findElement(By.cssSelector(emailLink));
    }

    public void clickLearnMoreLink() {
        new HCIndexPage(getWebdriverInstance()).clickLearnMoreLink();
    }


    public void checkHCContactUsText(String text) {
        Assertions.assertThat(new HCIndexPage(getWebdriverInstance()).getContactUsText()).
                as("Contact us text doesn't correct").
                contains(text);
    }

    @Override
    public void fillContactUs() {
        HCContactUsForm contactUsForm = new HCContactUsForm(getWebdriverInstance());
        if (contactUsForm.getFormElementValueByName("phoneNumber").isEmpty()) {
            contactUsForm.getFormElementByName("phoneNumber").sendKeys("(123) 123-4567");
        }
        new Select(contactUsForm.getFormElementByName("contactSubject")).selectByVisibleText("Other");
        contactUsForm.getFormElementByName("messageToCustomerCare").sendKeys("text help center");
    }


    @Override
    public void clickSendButton() {
        new HCContactUsForm(getWebdriverInstance()).clickSendButton();
    }

    public void checkErrorMessage() {
        Assertions.assertThat(new HCContactUsForm(getWebdriverInstance()).isErrorMessagePresent()).
                as("Error message isn't presenting").
                isTrue();
    }

    @Override
    public void fillPhoneNumber(String number) {
        new HCContactUsForm(getWebdriverInstance()).getFormElementByName("phoneNumber").sendKeys(number);
    }


    public void checkSuccessSendContactUsFormMessage() {
        Assertions.assertThat(new HCContactUsConfirmation(getWebdriverInstance()).isConfirmationMessagePresent()).
                as("Success send contact us message isn't presenting").
                isTrue();
    }


    public void checkElementPreFilled(String elementName) {
        Assertions.assertThat(new HCContactUsForm(getWebdriverInstance()).getFormElementValueByName(elementName)).
                as("Element " + elementName + " is empty").
                isNotEmpty();
    }


    public void checkElementHighlighted(String elementName) {
        Assertions.assertThat(new HCContactUsForm(getWebdriverInstance()).isHighlightedFormElement(elementName)).
                as("Element " + elementName + " is not highlighted").
                isTrue();
    }

    public void verifyPageURL(String url) {
        Assertions.assertThat(getWebdriverInstance().getCurrentUrl().contains(url));
    }

    @Override
    public void setupHC() {
        getWebdriverInstance().navigate().to(SalesforceHelpCenterModel.SF_HELPCENTER_URL);
    }

    @Override
    public void clickAnyArticle() {
        new HCSubcategoryPage(getWebdriverInstance()).clickOnAnyArticle();
    }

    public void verifyLiveChart() {
        Assertions.assertThat(new WebDriverWait(getWebdriverInstance(), 5)
                .until(ExpectedConditions.presenceOfElementLocated(By.className("rightCol"))).isEnabled())
                .as("LiveChart module doesn't present")
                .isTrue();
    }

    public void verifyArticlePage() {
        new HCIndexPage(getWebdriverInstance());
    }
}
