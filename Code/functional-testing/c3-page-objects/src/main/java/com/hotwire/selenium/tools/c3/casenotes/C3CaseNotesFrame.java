/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.casenotes;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: v-abudyak
 * Date: 5/12/13
 * Time: 4:13 PM
 */
public class C3CaseNotesFrame extends ToolsAbstractPage {
    public static final String ERROR_MESSAGE_CSS = ".errorMessages.c3Skin>ul>li";
    public static final String CASE_NOTES_DESCRIPTION_CSS = ".caseNoteDescriptionItem, .caseNoteDescriptionItemContent";

    public C3CaseNotesFrame(WebDriver webDriver) {
        super(webDriver, By.cssSelector("div.caseEntryLayout"));
    }

    public void setCaseNotesText(String note) {
        setText("textarea#note", note);
    }

    public void selectOutBound() {
        selectValue("select#methodOfContact", "3");
    }

    public void clickSaveNote() {
        freeze(DEFAULT_WAIT);
        findOne("input[value='Save note']", DEFAULT_WAIT).click();
    }

    public String getCaseNotesID() {
        return findOne(By.xpath(".//strong[contains(text() , 'Open Case ID: ')]")).
                    getText().split(":")[1];
    }

    public void clickCloseCase() {
        findOne("input[value='Close case']", DEFAULT_WAIT).click();
        //wait case note saving
        freeze(1);
    }

    public String getErrorMessage() {
        return new WebDriverWait(getWebDriver(), 5)
                .until(ExpectedConditions.
                        presenceOfElementLocated(By.cssSelector(ERROR_MESSAGE_CSS))).getText();
    }

    public Boolean checkCreditCardNumberInDesc(String cardNumber) {
        findOne(CASE_NOTES_DESCRIPTION_CSS, EXTRA_WAIT);
        List<WebElement> weList = getWebDriver().findElements(By.cssSelector(".caseNoteDescriptionItem"));
        Iterator<WebElement> it = weList.iterator();
        while (it.hasNext()) {
            WebElement wl = it.next();
            if (wl.getText().contains(cardNumber)) {
                return true;
            }
        }
        return false;
    }

    public void selectTermOfUse() {
        findOne("select#termsOfUse").sendKeys(Keys.ARROW_DOWN);
    }

    public void chooseTermOfUseValue(String value) {
        selectValueByVisibleText("select#termsOfUse", value, DEFAULT_WAIT);
    }

    public void chooseTermOfUseValue() {
        selectValueByIndex("select#termsOfUse", 1, DEFAULT_WAIT);
    }

    public String getSelectedLanguage() {
        return findOne("select#language option[selected]").getText();
    }

    public Boolean checkCaseNotesCreated(String text) {
        try {
            new WebDriverWait(getWebDriver(), 1)
                    .until(ExpectedConditions.
                            presenceOfElementLocated(By.xpath("//DIV[contains(text(), '" + text + "')]")));
            return true;
        }
        catch (TimeoutException e) {
            return false;
        }
    }

    public String getCaseNotesDescription() {
        freeze(1);
        return findOne("div.caseNoteDescriptionItem").getText();
    }

    public List<?> getLanguages() {
        List<String> languages = new ArrayList<>();
        List<WebElement> options = findMany("select#language option");
        for (WebElement option : options) {
            languages.add(option.getText());
        }
        return languages;
    }

    public String getMethodOfContact() {
        if (isElementDisplayed(".fLft>span")) {
            return findOne(".fLft>span").getText();
        }
        else if (isElementDisplayed("")) {
            return findOne("select#methodOfContact").getText();
        }
        else {
            return "unimplementedStep";
        }
    }

    public boolean isMethodOfContactDropdownDisplayed() {
        return isElementDisplayed("select#methodOfContact");
    }

    public void selectFirstLevelDisposition(String dispositionName) {
        selectValueByVisibleText("select#primaryDispositionReason", dispositionName, DEFAULT_WAIT);
    }

    public void selectFirstLevelDisposition() {
        selectValueByIndex("select#primaryDispositionReason", 4, DEFAULT_WAIT);
    }

    public void selectSecondLevelDisposition(String dispositionName) {
        selectValueByVisibleText("select#secondaryDispositionReason", dispositionName, DEFAULT_WAIT);
    }

    public void selectSecondLevelDisposition() {
        selectValueByIndex("select#secondaryDispositionReason", 1, EXTRA_WAIT);
    }

    public boolean isSecondDispositionDisplayed() {
        return isElementDisplayed(By.cssSelector("select#secondaryDispositionReason"), DEFAULT_WAIT);
    }

    public void selectThirdLevelDisposition(String dispositionName) {
        selectValueByVisibleText("select#tertiaryDispositionReason", dispositionName, DEFAULT_WAIT);
    }

    public void selectThirdLevelDisposition() {
        selectValueByIndex("select#tertiaryDispositionReason", 1, DEFAULT_WAIT);
    }

    public boolean isThirdDispositionDisplayed() {
        return isElementDisplayed(By.cssSelector("select#tertiaryDispositionReason"), EXTRA_WAIT);
    }

    public boolean isOutcomesDisplayed() {
        freeze(EXTRA_WAIT);
        return isElementDisplayed(By.cssSelector("select#outcomeCode"), EXTRA_WAIT);
    }

    public String getFirstLevelDispositionText() {
        return getSelectValue("select#primaryDispositionReason", EXTRA_WAIT);
    }

    public String getSecondDispositionText() {
        return getSelectValue("select#secondaryDispositionReason", EXTRA_WAIT);
    }

    public String getThirdDispositionText() {
        return getSelectValue("select#tertiaryDispositionReason", EXTRA_WAIT);
    }

    public String getOutcomesText() {
        return new Select(findOne("select#outcomeCode")).getFirstSelectedOption().getText();
    }

    public boolean isCloseCaseButtonDisabled() {
        freeze(DEFAULT_WAIT);
        return findOne("input[value='Close case']").getAttribute("class").contains("disabled");
    }

    public boolean isHelpDeskContactedDisplayed() {
        return isElementDisplayed(By.cssSelector("select#isHelpdeskContacted"), DEFAULT_WAIT);
    }

    public void selectHelpDeskContacted(String value) {
        selectValueByVisibleText("select#isHelpdeskContacted", value, DEFAULT_WAIT);
    }

    public String getWorkflowInfo() {
        return findOne(By.xpath("//div[@class='caseNoteDescriptionItem'][2]"), DEFAULT_WAIT).getText();
    }

    public String clickOnLatestResultUrl() {
        findOne("input.resultsButton").click();
        return waitValueLoaded(By.className("resultsField"), DEFAULT_WAIT).getAttribute("value");
    }


    public String clickOnLatestDetailsUrl() {
        findOne("input.detailsButton").click();
        return waitValueLoaded(By.className("detailsField"), DEFAULT_WAIT).getAttribute("value");
    }

    public boolean isSaveNoteButtonDisabled() {
        freeze(DEFAULT_WAIT);
        return findOne("input[value='Save note']").getAttribute("class").contains("disabled");
    }

    public void setNameOfContact(String name) {
        findOne("input#nameOfContact", DEFAULT_WAIT).sendKeys(name);
    }

    public String getNameOfContact() {
        return findOne("input#nameOfContact", DEFAULT_WAIT).getText();
    }

    public void selectOutcome(String outcome) {
        selectValueByVisibleText("select#outcomeCode", outcome, DEFAULT_WAIT);
    }

    public void selectOutcome() {
        selectValueByIndex("select#outcomeCode", 1, DEFAULT_WAIT);
    }
}
