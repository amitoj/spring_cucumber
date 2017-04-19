/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.blackList;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-sshubey on 9/30/2014.
 */
public class C3BlackListPage extends ToolsAbstractPage {

    public C3BlackListPage(WebDriver webDriver) {
        super(webDriver, By.xpath("//td[contains(text(), 'blacklist')]"));
    }

    public void setEmail(String email) {
        findOne("input#email").clear();
        findOne("input#email").sendKeys(email);
    }

    public void clickAddToBlackList() {
        findOne(By.name("add")).click();
    }

    public void clickRemoveFromBlackList() {
        findOne(By.name("remove")).click();
    }

    public String getConfirmationMessage() {
        return findOne(By.xpath("//td[@id='errorMessaging']")).getText();
    }
}
