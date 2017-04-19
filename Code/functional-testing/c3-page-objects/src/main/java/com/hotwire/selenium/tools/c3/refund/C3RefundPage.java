/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.refund;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import com.hotwire.testing.UnimplementedTestException;
import org.fest.assertions.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-abudyak
 * Date: 11/1/12
 * Time: 7:43 AM
 * Refund PageObject, contains all the methods for refunding.
 */
public class C3RefundPage extends ToolsAbstractPage {

    private static final String ISSUE_REFUND_BUTTON = "input.displayButton[name='issueRefund']";
    private static final String FULL_REFUND_CHECKBOX = "input[name='checkAll']";

    public C3RefundPage(WebDriver webDriver) {
        super(webDriver, By.name("issueRefund"));
    }

    public void clickRefund() {
        findOne(ISSUE_REFUND_BUTTON, EXTRA_WAIT).click();
    }

    public void verifyConfirmationPoints(String talkingPoints) {
        Assertions.assertThat(findOne("#refundResponse", DEFAULT_WAIT).getText()).isEqualTo(talkingPoints);
    }

    public C3RefundConfirmationPage continueOperation() {
        findOne("input#continueId, input[name='continue']", EXTRA_WAIT).click();
        return new C3RefundConfirmationPage(getWebDriver());
    }

    public void clickFullRefundCheckbox() {
        findOne(FULL_REFUND_CHECKBOX).click();
    }

    public String getRefundAmount() {
        freeze(1);
        return findOne("div#poTotalRefundAmtTop", DEFAULT_WAIT).getText();
    }

    public String getRefundedHotDollarsAmount() {
        return findOne("#fullHotDollars>strong").getText()
                .replaceAll("[^0-9.$€]", "");
    }

    public void clickHotDollarsYes() {
        findOne("#hotdollarsYes").click();
    }

    public void clickHotDollarsNo() {
        findOne("#hotdollarsNo").click();
    }

    public String getCustomerEmail() {
        return getWebDriver().findElement(By.id("silkId_accountEmail")).getText();
    }

    public String getCustomerPhone() {
        return getWebDriver().findElement(By.id("silkId_primaryPhone")).getText().replaceAll("[ ()-]", "");
    }


    public void fillPartialRefundValues(Integer numberOfSegment) {
        throw new UnimplementedTestException("not implemented");
    }

    public void fillPartialRefundValues() {
        throw new UnimplementedTestException("not implemented");
    }

    public void proceedPartialRefund() {
        findOne(ISSUE_REFUND_BUTTON, EXTRA_WAIT).click();
        findOne("input.displayButton#continueId", EXTRA_WAIT).click();
    }

    public boolean isFullRefundCheckBoxChecked() {
        return findOne(FULL_REFUND_CHECKBOX, DEFAULT_WAIT).isSelected();
    }
}
