/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.angular;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;

/**
 * @author vjong
 *
 */
public class AngularGlobalHeader extends AbstractUSPage {

    @FindBy(css = "div.navbar-header a.navbar-brand img")
    private WebElement hwLogo;

    @FindBy(css = "ul.navbar-left")
    private WebElement verticalNavBar;

    private HashMap<String, String> verticalLandingPagesMap;

    public AngularGlobalHeader(WebDriver webdriver) {
        super(webdriver, new Wait<Boolean>(new VisibilityOf(By.id("header"))).maxWait(5));
        initVerticalLandingPagesMap();
    }

    public void clickHotwireLogo() {
        hwLogo.click();
    }

    public String getSelectedCountry() {
        return getWebDriver().findElement(By.cssSelector(".country-dropdown a.dropdown-toggle")).getText();
    }

    public void selectCountry(String country) {
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(
            ".country-dropdown a.dropdown-toggle")));
        getWebDriver().findElement(By.cssSelector(".country-dropdown a.dropdown-toggle")).click();
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(
            ".country-dropdown ul.dropdown-menu li a")));
        boolean found = false;
        for (WebElement countryListItem : getWebDriver().findElements(
                By.cssSelector(".country-dropdown ul.dropdown-menu li a"))) {
            if (countryListItem.getText().trim().toLowerCase().equals(country.toLowerCase())) {
                found = true;
                countryListItem.click();
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("\"" + country + "\" is not a valid country selector.");
        }
    }

    public void selectCurrency(String currency) {
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(
            ".currency-dropdown a.dropdown-toggle")));
        getWebDriver().findElement(By.cssSelector(".currency-dropdown a.dropdown-toggle")).click();
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(
            ".currency-dropdown ul.dropdown-menu li a")));
        boolean found = false;
        for (WebElement currencyListItem : getWebDriver().findElements(
                By.cssSelector(".currency-dropdown ul.dropdown-menu li a"))) {
            if (currencyListItem.getText().trim().startsWith(currency)) {
                found = true;
                currencyListItem.click();
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("\"" + currency + "\" is not a valid currency selector.");
        }
    }

    public String getSelectedCurrency() {
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(
            ".currency-dropdown a.dropdown-toggle")));
        return getWebDriver().findElement(By.cssSelector(".currency-dropdown a.dropdown-toggle")).getText();
    }

    public void selectMyAccountItem(String menuSelection) {
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(
            ".sign-in-dropdown a.dropdown-toggle")));
        getWebDriver().findElement(By.cssSelector(".sign-in-dropdown a.dropdown-toggle")).click();
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(
            ".sign-in-dropdown ul.dropdown-menu li a")));
        boolean found = false;
        for (WebElement element : getWebDriver().findElements(
                By.cssSelector(".sign-in-dropdown ul.dropdown-menu li a"))) {
            if (element.getText().trim().equals(menuSelection)) {
                found = true;
                element.click();
                break;
            }
        }
        if (!found) {
            throw new RuntimeException(menuSelection + " My Account selector is not available.");
        }
    }

    public void selectSupportItem(String supportType) {
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(
            ".support-dropdown a.dropdown-toggle")));
        getWebDriver().findElement(By.cssSelector(".support-dropdown a.dropdown-toggle")).click();
        new WebDriverWait(getWebDriver(), 2).until(new VisibilityOf(By.cssSelector(
            ".support-dropdown ul.dropdown-menu li a")));
        boolean found = false;
        for (WebElement element : getWebDriver().findElements(
                By.cssSelector(".support-dropdown ul.dropdown-menu li a"))) {
            if (element.getText().trim().equals(supportType)) {
                found = true;
                element.click();
                break;
            }
        }
        if (!found) {
            throw new RuntimeException(supportType + " Support selector is not available.");
        }
    }

    public void navigateToVerticalLandingPage(String vertical) {
        verticalNavBar.findElement(By.xpath(
            "li/a[contains(@href, '" + verticalLandingPagesMap.get(vertical) + "')]")).click();
    }

    @SuppressWarnings("serial")
    private void initVerticalLandingPagesMap() {
        verticalLandingPagesMap = new HashMap<String, String>() { {
                put("Hotels", "#!/");
                put("Cars", "/car/index.jsp");
                put("Flights", "/air/index.jsp");
                put("Vacations", "/package/index.jsp");
            }
        };
    }
}
