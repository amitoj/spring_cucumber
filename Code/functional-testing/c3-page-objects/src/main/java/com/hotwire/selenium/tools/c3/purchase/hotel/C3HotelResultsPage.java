/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.hotel;


import com.hotwire.selenium.tools.ToolsAbstractPage;
import com.hotwire.selenium.tools.c3.purchase.ResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Methods used in C3 for Hotel Results page
 */
public class C3HotelResultsPage extends ToolsAbstractPage implements ResultsPage {
    private static String firstResult = "//a[@class='detailsLink']";
    private static String location = "location";
    private static String startDate = "startDate";
    private static String endDate = "endDate";
    private static String rooms = "numRooms";
    private static String adults = "numAdults";
    private static String children = "numChildren";

    public C3HotelResultsPage(WebDriver webdriver) {
        super(webdriver, By.className("hotelNameLink"));
    }

    public void clickEditSearch() {
        findOne(".editSearchLinkLabel").click();
    }

    public String getLocation() {
        return findOne(By.name(location)).getAttribute("value");
    }

    public String getStartDate() {
        return findOne(By.name(startDate)).getAttribute("value");
    }

    public String getEndDate() {
        return findOne(By.name(endDate)).getAttribute("value");
    }

    public String getRooms() {
        return new Select(findOne(By.name(rooms))).getFirstSelectedOption().getText();
    }

    public String getAdults() {
        return new Select(findOne(By.name(adults))).getFirstSelectedOption().getText();
    }

    public String getChildren() {
        return new Select(findOne(By.name(children))).getFirstSelectedOption().getText();
    }

    public C3HotelResultsPage chooseOpaque() {
        if (!opaqueResultsAreDisplayed()) {
            findOne(".opaqueTab a", DEFAULT_WAIT).click();
        }
        return new C3HotelResultsPage(getWebDriver());
    }

    public C3HotelResultsPage chooseRetail() {
        if (opaqueResultsAreDisplayed()) {
            findOne(".retailTab a", DEFAULT_WAIT).click();
        }
        return new C3HotelResultsPage(getWebDriver());
    }

    public void selectFirstResult() {
        findOne(By.xpath(firstResult), EXTRA_WAIT).click();
    }

    public void verifyResults() {
        findOne(By.xpath(firstResult), DEFAULT_WAIT);
    }

    public String getOpaqueResultsDisplayedLocator() {
        return "opaqueHotel-div";
    }

    public boolean opaqueResultsAreDisplayed() {
        try {
            getWebDriver().findElement(By.id(getOpaqueResultsDisplayedLocator()));
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getReferenceNumber() {
        return findOne("span.customerCareRefNumber", EXTRA_WAIT).getText().replaceFirst("^.*:\\s+", "");
    }

    public List<String> getResults() {
        ArrayList results = new ArrayList<String>();
        new WebDriverWait(getWebDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.retail"))).click();

        List<WebElement> list = getWebDriver().findElements(By.cssSelector("a.hotelNameLink"));
        for (WebElement w : list) {
            System.out.println(w.getText());
            results.add(w.getText());
        }
        return results;
    }

    public void switchToRetail() {
        findOne("a#retail-tab", DEFAULT_WAIT).click();
    }

    public void switchToOpaque() {
        findOne("a#opaque-tab", DEFAULT_WAIT).click();
    }

    @Override
    public String getTypeOfFirstResult() {
        if (findOne(By.xpath(firstResult), DEFAULT_WAIT).findElement(By.xpath(".//..//.."))
                .getAttribute("class").contains("opaque")) {
            return "opaque";
        }
        else {
            return "retail";
        }
    }
}
