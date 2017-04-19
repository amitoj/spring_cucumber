/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.thirdparty;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * Created by IntelliJ IDEA.
 * User: vjong
 * Date: Jul 19, 2012
 * Time: 10:13:43 AM
 */
public class CommentCardPage {
    private static final String COMMENT_TEXT_AREA = "comments";
    private static final String SUBMIT_BUTTON = ".submitButton, #submitImage";
    private static final String CONTENT_RADIO_GROUP = "content";
    private static final String DESIGN_RADIO_GROUP = "design";
    private static final String USABILITY_RADIO_GROUP = "usability";
    private static final String OVERALL_RADIO_GROUP = "overall";
    private static final String COMMENT_BEVEL = "//td[contains(@class, 'commentBevel')]";
    private static final String CDU_BEVEL = "//td[contains(@class, 'cduBevel')]";
    private static final long DEFAULT_WAIT = 10;

    @FindBy(xpath = COMMENT_BEVEL)
    private WebElement commentSection;

    @FindBy(xpath = CDU_BEVEL)
    private WebElement ratingsSection;

    @FindBy(name = CONTENT_RADIO_GROUP)
    private List<WebElement> contentRadioButtons;

    @FindBy(name = DESIGN_RADIO_GROUP)
    private List<WebElement> designRadioButtons;

    @FindBy(name = USABILITY_RADIO_GROUP)
    private List<WebElement> usabilityRadioButtons;

    @FindBy(name = OVERALL_RADIO_GROUP)
    private List<WebElement> overallRadioButtons;

    @FindBy(name = COMMENT_TEXT_AREA)
    private WebElement commentText;

    @FindBy(css = SUBMIT_BUTTON)
    private WebElement submitButton;

    @FindBy(xpath = ".//*[@id='questions']//input")
    private List<WebElement> questionRadioButtons;

    public CommentCardPage(WebDriver webdriver) {
        PageFactory.initElements(webdriver, this);
        // assert the containers for the comment text box, radio buttons and submit button render.
        assertPageRender(webdriver);
    }

    private void assertPageRender(WebDriver webdriver) {
        new WebDriverWait(webdriver, DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(commentSection, true));
        new WebDriverWait(webdriver, DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(ratingsSection, true));
        new WebDriverWait(webdriver, DEFAULT_WAIT)
                .until(
                        PageObjectUtils.webElementVisibleTestFunction(
                                webdriver.findElement(By.cssSelector(SUBMIT_BUTTON)),
                                true));
    }

    public void typeComment(String comment) {
        commentText.sendKeys(comment);
    }

    public void rateContent(int ratingIndex) {
        contentRadioButtons.get(ratingIndex).click();
    }

    public void rateDesign(int ratingIndex) {
        designRadioButtons.get(ratingIndex).click();
    }

    public void rateUsability(int ratingIndex) {
        usabilityRadioButtons.get(ratingIndex).click();
    }

    public void rateOverall(int ratingIndex) {
        overallRadioButtons.get(ratingIndex).click();
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public void unswereQuestions() {
        questionRadioButtons.get(0).click();
        questionRadioButtons.get(2).click();
    }

    public void fillAndSubmitCommentCard(String comment, int contentRatingIndex, int designRatingIndex,
                                         int usabilityRatingIndex, int overallRatingIndex) {
        typeComment(comment);
        rateOverall(overallRatingIndex);
        try {
            rateContent(contentRatingIndex);
            rateDesign(designRatingIndex);
            rateUsability(usabilityRatingIndex);
        }
        catch (IndexOutOfBoundsException e) {
            try {
                unswereQuestions();
            }
            catch (IndexOutOfBoundsException e2) { /* do nothing */ }
        }
        clickSubmitButton();
    }
}
