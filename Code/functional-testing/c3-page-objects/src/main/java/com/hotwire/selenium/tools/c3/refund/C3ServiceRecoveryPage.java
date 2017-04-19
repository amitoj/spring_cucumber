/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.refund;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Service recovery page in C3
 */
public class C3ServiceRecoveryPage extends ToolsAbstractPage {

    public C3ServiceRecoveryPage(WebDriver webDriver) {
        super(webDriver, By.id("srgQuestionsPrimaryReasonLayer"));
    }

    public void chooseRecoveryReason(String reason) {
        selectValue("select#srgSecondaryReasonName", reason, EXTRA_WAIT);
    }

    public C3RefundDeterminationPage continueRefund() {
        findOne("input[name='recoverServices']").click();
        return new C3RefundDeterminationPage(getWebDriver());
    }

    public void clickContinueRefund() {
        findOne("input[name='recoverServices']").click();
    }

    public void answerTestBookingQuestions(String answer) {
        findOne("div#questions input[value='" + answer + "']").click();
    }

    public void answerMedicalOrDeathQuestions(String answer) {
        findOne("div#questions select option:nth-child(2)").click();
        findOne("div#questions input[type='text']").sendKeys("Test");
        try {
            answerQuestion(4, answer);
        }
        catch (NoSuchElementException e) {
            answerQuestion(2, answer);
        }
    }

    public void answerDeathQuestionsWithPossessionCertificateOfDeath() {
        findOne("div#questions select option:nth-child(7)").click();
        findOne("div#questions input[type='text']").sendKeys("Test");
        answerQuestion(2, "Yes");
    }


    public void answerMinorBookingQuestions() {
        List<WebElement> questions = findMany("div#questions input");
        for (WebElement question : questions) {
            question.sendKeys("Test");
        }
        answerQuestion(3, "No");
    }

    public void answerMilitaryQuestions() {
        findOne("div#questions select option:nth-child(2)").click();
        answerQuestion(2, "Yes");
        findOne("div#questions input[type='text']").sendKeys("Man");
        answerQuestion(5, "No");
    }

    public void answerCourtSummonsQuestions(boolean documents) {
        findOne("div#questions select option:nth-child(2)").click();
        answerQuestion(1, "Yes");
        findOne("div#questions input[type='text']").sendKeys("Man");
        answerQuestion(5, documents ? "Yes" : "No");
    }

    public void answerWrongNameQuestions() {
        try {
            getWebDriver().findElement(By.cssSelector("div#questions input[value='No']")).click();
        }
        catch (NoSuchElementException e) {
            logger.info("No additional questions displayed");
        }
    }

    /**
     *  Answering on questions before refund
     * @param orderNumber - selected category num in html
     * @param value - Yes or No (case sensitive)
     */
    private void answerQuestion(Integer orderNumber, String value) {
        String template = "input[name='selectedSecondaryRecoveryReason.selectedCategoryQnAs[%s].answer'][value='%s']";
        findOne(String.format(template, orderNumber, value)).click();
    }

    public String getTalkingPoints() {
        return findOne("div#talkingPoints", DEFAULT_WAIT).getText().replace("\n", "");
    }




}
