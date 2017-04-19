/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Hotel operations page in C3
 *
 */
public class C3HotelHomePage extends ToolsAbstractPage {
    private String deactivateHotelBtn = "input.formButton[value='Deactivate Hotel']";

    public C3HotelHomePage(WebDriver webDriver) {
        super(webDriver, By.cssSelector("div.commonTasksLayout div.hotelInfo"));
    }

    public void clickHotelCaseHistory() {
        clickOnLink("Hotel Case History", DEFAULT_WAIT);
    }

    public void clickAmenities() {
        clickOnLink("Amenities", DEFAULT_WAIT);
    }

    public String getOverchargeDetailsURL() {
        String onclick = getWebDriver().findElement(By.linkText("Overcharge Details")).getAttribute("onclick");
        return onclick.replaceFirst("^.*\\('", "").replaceFirst("'.*$", "");
    }

    public List<WebElement> getCommonTasksLinks() {
        return findMany("ul.commonTasks li a", EXTRA_WAIT);
    }

    public void clickOnCommonTask(String task) {
        //for stabilization
        freeze(1);
        clickOnLink(task, DEFAULT_WAIT);
    }

    public List<WebElement> getCommonTasksMessages() {
        return getWebDriver().findElements(By.cssSelector("ul.commonTasks li span"));
    }


    public String getConfirmationMsg() {
        return findOne("div.errorMessages.c3Skin ul li", DEFAULT_WAIT).getText();
    }

    public boolean isDeactivateHotelBtnDisplayed() {
        return isElementDisplayed(By.cssSelector(deactivateHotelBtn), EXTRA_WAIT);
    }

    public void clickOnDeactivateLink() {
        findOne("a[href*='hotelActivation.jsp']").click();
    }

    public boolean isHotelDeactivateLinkDisplayed() {
        return isElementDisplayed("a[href*='hotelActivation.jsp']");
    }

    public boolean isHotelActivationFakeLinkDisplayed() {
        return isElementDisplayed("span#hotelActivationLink.link");
    }

    public void clickOnDeactivateBtn() {
        findOne(deactivateHotelBtn, DEFAULT_WAIT).click();
    }

    public void clickOnReactivateBtn() {
        findOne("input.formButton[value='Reactivate Hotel']").click();
    }

    public void clickHotelReportsDeliveryLink() {
        findOne(By.xpath("//a[text()='Hotel Reports Delivery']"), DEFAULT_WAIT).click();
    }
}
