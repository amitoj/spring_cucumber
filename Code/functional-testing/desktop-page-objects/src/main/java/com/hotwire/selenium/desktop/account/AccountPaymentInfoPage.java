/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 8/20/14
 * Time: 10:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountPaymentInfoPage  extends AbstractAccountPage  {

    @FindBy(xpath = ".//div[@class='creditCard']//div[contains(text(), 'Visa ending')]")
    private WebElement creditCardName;

    @FindBy(xpath = "//div[@class='paymentInfo']//label[contains(text(), 'Card nickname*:')]//../DIV")
    private WebElement creditCardNickName;

    @FindBy(xpath = "//div[@class='paymentInfo']//label[contains(text(), 'Card type*:')]//../DIV")
    private WebElement creditCardType;

    @FindBy(xpath = "//div[@class='paymentInfo']//label[contains(text(), 'Your name*:')]//../DIV")
    private WebElement yourName;

    @FindBy(xpath = "//div[@class='paymentInfo']//label[contains(text(), 'Credit card no.*:')]//../DIV")
    private WebElement creditCardNumber;

    @FindBy(xpath = "//div[@class='paymentInfo']//label[contains(text(), 'Billing address*:')]//../DIV")
    private WebElement billingAddress;

    @FindBy(xpath = "//div[@class='paymentInfo']//label[contains(text(), 'Expiration date*:')]//../DIV")
    private WebElement expirationDate;

    @FindBy(xpath = "//LI[contains(text(), 'You have no available HotDollars')]")
    private List<WebElement> noAvailableHotDollarsMsg;

    @FindBy(xpath = "//LI[contains(text(), 'You have no expired HotDollars')]")
    private WebElement noExpiredHotDollarsMsg;

    @FindBy(css = ".yui3-u.ammount")
    private List<WebElement> allHotDollarsAmount;

    public AccountPaymentInfoPage(WebDriver driver) {
        super(driver, "tile.account.paymentInfo");
    }

    public String getCreditCardName() {
        return creditCardName.getText();
    }

    public String getCreditCardNickName() {
        return creditCardNickName.getText();
    }

    public String getCreditCardType() {
        return creditCardType.getText();
    }

    public String getYourName() {
        return yourName.getText();
    }

    public String getCreditCardNumber() {
        return creditCardNumber.getText();
    }

    public String getBillingAddress() {
        return billingAddress.getText();
    }

    public String getExpirationDate() {
        return expirationDate.getText();
    }

    public boolean isNoAvailableHotDollarsMsgExist() {
        try {
            noAvailableHotDollarsMsg.get(0).getText();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean doesNoExpiredHotDollarsMsgExist() {
        try {
            noExpiredHotDollarsMsg.getText();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean isNoActiveHotDollarsMsgExist() {
        try {
            noAvailableHotDollarsMsg.get(1).getText();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<String> getHotDollarsFromSection(Integer requiredSection) {
        List<String> rows = new ArrayList<String>();
        Integer section = 0;
        Integer size = allHotDollarsAmount.size();
        if (!noAvailableHotDollarsMsg.isEmpty()) {
            section++;
        }
        for (int i = 0; i < size; i++) {
            String value = allHotDollarsAmount.get(i).getText();
            if (value.toLowerCase().contains("amount")) {
                section++;
                continue;
            }
            if (section.equals(requiredSection)) {
                rows.add(value.replace(",", "").replace("$", "").replace(".00", ""));
            }
        }
        return rows;
    }

    public List<String> getExpiredHotDollarsList() {
        return  getHotDollarsFromSection(2);
    }

    public List<String> getAvailableHotDollarsList() {
        return  getHotDollarsFromSection(1);
    }

    public List<String> getUsedHotDollarsList() {
        return  getHotDollarsFromSection(3);
    }

    public String getFirstAvailableHotDollarsAmount() {
        for (WebElement webElement : allHotDollarsAmount) {
            if (!webElement.getText().equalsIgnoreCase("Amount")) {
                return  webElement.getText();
            }
        }
        throw new RuntimeException("Couldn't find any hotdollars on the page");
    }

    public List<Double> getAmountsOfAllHotDollars() {
        List<Double> amount = new ArrayList<Double>();
        for (WebElement webElement : allHotDollarsAmount) {
            if (webElement.getText().contains("$")) {
                amount.add(Double.parseDouble(webElement.getText().replaceAll("[$,]", "")));
            }
        }
        return amount;
    }

}
