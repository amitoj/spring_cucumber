/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.po.AbstractPageObject;

/**
 * This class provides the methods to access the purchase review panel on us billing page.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public class PurchaseReviewFragment extends AbstractPageObject {

    private static final By CONTAINER = By.id("billingPanelReviewAndPurchaseComp");
    private static final String AGREEMENT_CHECKBOX =
            "div#confirmSection div.agreeAndBookWrapper input[name='billingForm.agreement'], " +
                    "div#termsOfUse input#agreementChecked";
    private static final String CSS_CONFIRM_BUTTON =
            "div#confirmSection div.agreeAndBookWrapper button.btnC, div#termsOfUse input.continueBtn";
    private Boolean agreeWithTerms = true;

    @FindBy(xpath = "//input[@id='insurancePurchaseCheckTrue']/following-sibling::span")
    private WebElement insuranceChecked;

    @FindBy(xpath =
        "//input[contains(@name, 'agreement')]//..//..//a[contains(@href, '.hotwire.com/pop-up/terms-popup.jsp#tab2')]")
    private WebElement termsOfUse;

    @FindBy(xpath =
        "//input[contains(@name, 'agreement')]//..//..//a[contains(@href, '.hotwire.com/pop-up/terms-popup.jsp#tab3')]")
    private WebElement productRulesAndRestrictions;

    @FindBy(xpath =
        "//div[@class='mt15 value finalTotal']")
    private WebElement tripTotal;

    @FindBy(xpath =
        "//div[@class='totalCostDataContainer']//div[@class='value']")
    private WebElement insurance;

    @FindBy(css = AGREEMENT_CHECKBOX)
    private WebElement agreementChecked;

    public PurchaseReviewFragment(WebDriver webDriver) {
        super(webDriver, CONTAINER);
    }

    public void openTermsOfUse() {
        termsOfUse.click();
    }

    public void openProductRulesAndRestrictions() {
        productRulesAndRestrictions.click();
    }

    public void continuePanel() {
        if (agreeWithTerms && !agreementChecked.isSelected()) {
            getWebDriver().findElement(By.cssSelector(AGREEMENT_CHECKBOX)).click();
        }
        submit();
        new SecureCodeVerificationWindow(getWebDriver()).proceed();
    }

    public PurchaseReviewFragment setAgreeWithTerms(Boolean agreeWithTerms) {
        this.agreeWithTerms = agreeWithTerms;
        return this;
    }

    private void submit() {
        JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
        executor.executeScript("document.getElementsByName('btnPurchase')[0].click();");
    }

    public WebElement getTripTotal() {
        return tripTotal;
    }

    public WebElement getInsurance() {
        return insurance;
    }

    public WebElement getInsuranceChecked() {
        return insuranceChecked;
    }
}
