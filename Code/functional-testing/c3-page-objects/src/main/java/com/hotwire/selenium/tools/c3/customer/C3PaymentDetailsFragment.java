/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 10/29/14
 * Time: 11:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class C3PaymentDetailsFragment extends ToolsAbstractPage {
    private static String fullAuthorizationTbl = "//*[text()[contains(.,'Full Authorization')]]//..";
    private static String billTbl = "//*[text()[contains(.,'Bill')]]//..";
    private static String viewDetailsLnk =  "//a[contains(text(), 'View details')]";
    private static String billingName =  "//*[text()[contains(.,'Billing Name')]]//../div[2]";
    private static String account =  "//*[text()[contains(.,'Account')]]//../div[2]";
    private static String amount =  "//*[text()[contains(.,'Amount')]]//../div[2]";
    private static String status =  "//*[text()[contains(.,'Status')]]//../div[2]";
    private static String getaway =  "//*[text()[contains(.,'Getaway')]]//../div[2]";
    private static String authby =  "//*[text()[contains(.,'Auth By')]]//../div[2]";
    private static String isReserved =  "//*[text()[contains(.,'Is Reserved')]]//../div[2]";
    private static String reversalDate =  "//*[text()[contains(.,'Reversal Date')]]//../div[2]";

    public C3PaymentDetailsFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector(".paymentDetails"));
    }

    public Table getFullAuthorizationTable() {
        return new Table(By.xpath(fullAuthorizationTbl));
    }

    public Table getBillTable() {
        return new Table(By.xpath(billTbl));
    }
    /**
     * Table class
     * */
    public class Table {
        private By tableLocator;

        public Table(By tableLocator) {
            this.tableLocator = tableLocator;
        }

        private By getTableLocator() {
            return tableLocator;
        }

        private void setTableLocator(By tableLocator) {
            this.tableLocator = tableLocator;
        }

        public void clickDetailsLink() {
            findOne(getTableLocator()).findElement(By.xpath(viewDetailsLnk)).click();
        }

        public void getBillingName() {
            findOne(getTableLocator()).findElement(By.xpath(billingName)).getText();
        }

        public void getAccount() {
            findOne(getTableLocator()).findElement(By.xpath(account)).getText();
        }

        public void getAmount() {
            findOne(getTableLocator()).findElement(By.xpath(amount)).getText();
        }

        public void getStatus() {
            findOne(getTableLocator()).findElement(By.xpath(status)).getText();
        }

        public void getGetaway() {
            findOne(getTableLocator()).findElement(By.xpath(getaway)).getText();
        }

        public void getAuthBy() {
            findOne(getTableLocator()).findElement(By.xpath(authby)).getText();
        }

        public void getIsReserved() {
            findOne(getTableLocator()).findElement(By.xpath(isReserved)).getText();
        }

        public void getReversalDate() {
            findOne(getTableLocator()).findElement(By.xpath(reversalDate)).getText();
        }
    }


}
