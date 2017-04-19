/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car.fragments.depositFilter;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vzyryanov
 * Date: 6/27/13
 * Time: 4:10 AM
 */
public class CarDepositFilter extends AbstractUSPage {

    @FindBy(css = ".debitCard")
    private WebElement selectDebitCard;

    @FindBy(css = ".creditCard")
    private WebElement selectCreditCard;

    @FindBy(css = ".depositTypeFilter .bdd-traveling")
    private WebElement depositFilterTravellerRadioButton;

    @FindBy(css = ".depositTypeFilter .bdd-local")
    private WebElement depositFilterLocalRadioButton;

    @FindBy(css = ".depositTypeFilter .show .opener")
    private WebElement moreInformation;

    @FindBy(css = ".depositTypeFilter .container-close")
    private WebElement closePopup;

    @FindBy(css = ".depositTypeFilter #debitMoreInfo-bd")
    private WebElement popupExplanation;

    @FindBy(css = "#debitMoreInfo-panel")
    private WebElement moreInfoPopup;

    @FindBy(css = ".depositTypeFilter .desc")
    private WebElement depositFilterText;

    public CarDepositFilter(WebDriver webDriver) {
        super(webDriver, By.className("depositTypeFilter"));
    }

    public void selectDepositByType(DepositTypes cardType) {
        switch (cardType) {
            case DEBIT:
                selectDebitCard.click();
                // verify additional debit card options
                depositFilterLocalRadioButton.isDisplayed();
                depositFilterTravellerRadioButton.isDisplayed();
                if (!depositFilterTravellerRadioButton.isSelected()) {
                    throw new RuntimeException(
                        "\"I am traveling\" option isn't selected by default for debit deposit" +
                        " type");
                }
                break;
            default:
                selectCreditCard.click();
        }
    }

    public void selectDebitOptionByTravelType(DepositTypes cardType) {
        selectDebitCard.click();
        new WebDriverWait(getWebDriver(), 10).until(new IsAjaxDone());
        switch (cardType) {
            case I_AM_LOCAL:
                depositFilterLocalRadioButton.click();
                break;
            case I_AM_TRAVELING:
                depositFilterTravellerRadioButton.click();
                break;
            default:
                break;
        }
    }

    public DepositTypes getSelectedDepositType() {
        if (selectCreditCard.isSelected()) {
            return DepositTypes.CREDIT;
        }
        if (selectDebitCard.isSelected()) {
            if (depositFilterLocalRadioButton.isSelected()) {
                return DepositTypes.I_AM_LOCAL;
            }
            if (depositFilterTravellerRadioButton.isSelected()) {
                return DepositTypes.I_AM_TRAVELING;
            }
        }
        return null;
    }

    public void clickMoreInformationLink() {
        moreInformation.click();
    }

    public void closeExplanationPopup() {
        closePopup.click();
    }

    public String getPopupExplanation() {
        return popupExplanation.getText();
    }

    public String getFilterText() {
        return depositFilterText.getText();
    }

    public boolean isDebitDepositExplanationOpen() {
        try {
            return moreInfoPopup.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isRefineYourSearchModuleDisplayed() {
        return selectDebitCard.isDisplayed() && selectCreditCard.isDisplayed();
    }

    /**
     * Deposit types
     */
    public enum DepositTypes {

        CREDIT("credit"),
        DEBIT("debit"),
        I_AM_TRAVELING("I am traveling"),
        I_AM_LOCAL("I am local");

        private String text;

        DepositTypes(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static DepositTypes fromString(String text) {
            if (text != null) {
                for (DepositTypes b : DepositTypes.values()) {
                    if (text.equalsIgnoreCase(b.text)) {
                        return b;
                    }
                }
            }
            return null;
        }
    }
}
