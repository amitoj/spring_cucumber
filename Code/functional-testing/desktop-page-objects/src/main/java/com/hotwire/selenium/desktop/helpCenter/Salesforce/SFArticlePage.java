/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.helpCenter.Salesforce;

import com.hotwire.selenium.desktop.helpCenter.HelpCenterAbstractPage;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * User: v-asnitko
 * Date: 9/3/13
 */
public class SFArticlePage extends HelpCenterAbstractPage {

    private static final String CSS_FEEDBACK_AREA = "#feedbackContainer #feedback";

    @FindBy(id = "votingContainer")
    private WebElement divVotingContainer;

    @FindBy(className = "star")
    private List<WebElement> stars;

    @FindBy(xpath = "//ul[@class='breadcrumbs']/li//a")
    private List<WebElement> bclink;

    @FindBy(className = "starsDisabler")
    private WebElement divStarsDisabler;

    @FindBy(xpath = ".//*[@id='article']/div[1]/h1")
    private WebElement articleHeader;

    @FindBy(css = "a[href*='http://hw.qa.cs7.force.com/knowledgebase/articles/']")
    private List<WebElement> articlesList;

    @FindBy(css = CSS_FEEDBACK_AREA)
    private WebElement feedbackArea;

    @FindBy(css = "#feedbackContainer button.submit")
    private WebElement feedbackSubmitBtn;

    @FindBy(css = "#feedbackContainer button.cancel")
    private WebElement feedbackCancelBtn;


    public SFArticlePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector("ul.breadcrumbs li");
    }

    public boolean isChatIconOnPage() {
        return isElementDisplayed("div.sidebarChatHolder a.chatTextLink");
    }

    public boolean isVotingContainerOnPage() {
        return divVotingContainer.isDisplayed() && stars.size() == 5;
    }

    public int starsCount() {
        return stars.size();
    }

    public void clickLiveChatIcon() {
        findOne("a.chatTextLink").click();
    }

    public boolean areStarsHighlighted() {
        String attribute = stars.get(0).getAttribute("style");
        return !attribute.equals("");
    }

    public boolean areStarsDisabled() {
        String attribute = divStarsDisabler.getAttribute("style");
        return !attribute.equals("");
    }

    public void clickAnyStar() {
        int starNumber = getRandomStarNumber(starsCount());
        stars.get(starNumber).click();
        new WebDriverWait(getWebDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(CSS_FEEDBACK_AREA)));
    }

    private int getRandomStarNumber(int max) {
        return (int) (Math.random() * max);
    }

    public void verifyBreadCrumbs(String topLink) {
        List<WebElement> breadcrumbs = findMany("ul.breadcrumbs li");
        Assertions.assertThat(breadcrumbs.get(0).getText()).isEqualTo(topLink);
        breadcrumbs.remove(0);
        for (WebElement breadcrumb : breadcrumbs) {
            Assertions.assertThat(breadcrumb.getText()).startsWith(":");
        }
    }

    public boolean verifyRelatedArcticles() {
        return articlesList.size() > 1;
    }

    public boolean isFeedbackFormVisible() {
        return feedbackArea.isDisplayed() && feedbackSubmitBtn.isDisplayed() && feedbackCancelBtn.isDisplayed();
    }

    public String getFeedbackMessageAfterFormClosed() {
        return findOne("#feedbackContainer .response", DEFAULT_WAIT).getText();
    }

    public void cancelFeedbackForm() {
        findOne("#feedbackContainer button.cancel", DEFAULT_WAIT).click();
    }

    public void submitFeedbackForm() {
        findOne("#feedbackContainer button.submit", DEFAULT_WAIT).click();
    }

    public SFArticlePage typeFeedbackMessage(String message) {
        findOne("#feedbackContainer #feedback").sendKeys(message);
        return this;
    }

    public List<String> getPhones() {
        List<String> results = new ArrayList<>();
        try {
            new WebDriverWait(getWebDriver(), 2)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.contactUsBlock")));
            List<WebElement> phones = getWebDriver().findElements(By.cssSelector("div.phoneRow"));
            String[] usPhones = phones.get(0).getText().split("\n");
            results.add(usPhones[2]);
            results.add(usPhones[3]);
            results.add(phones.get(1).getText().split("\n")[1]);
            return results;
        }
        catch (TimeoutException e) {
            return null;
        }
    }

    public boolean getEmail() {
        try {
            getWebDriver().findElement(By.linkText("Send us an email"));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<String> getExpressPhones() {
        List<String> results = new ArrayList<>();
        try {
            new WebDriverWait(getWebDriver(), 2)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.contactUsBlock")));
            List<WebElement> phones = getWebDriver().findElements(By.cssSelector("div.phoneRow"));
            String[] usPhones = phones.get(0).getText().split("\n");
            results.add(usPhones[2]);
            results.add(phones.get(1).getText().split("\n")[1]);
            return results;
        }
        catch (TimeoutException e) {
            return null;
        }
    }

    public String getExpressEmail() {
        String email = null;
        try {
            email = getWebDriver().findElement(By.cssSelector("a.contactEmail")).getText();
        }
        catch (NoSuchElementException e) {
            System.out.println("No email in contact module.");
        }
        return email;
    }
}
