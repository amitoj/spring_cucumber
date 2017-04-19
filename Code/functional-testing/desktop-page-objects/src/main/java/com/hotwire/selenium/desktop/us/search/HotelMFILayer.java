/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.search;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 *
 */
public class HotelMFILayer extends AbstractUSPage {
    private static final long DEFAULT_WAIT = 10;
    private static final String MFI_LAYER = "tileName-mfiD2C";
    private static final String LAYER_PARAGRAGHS =
            ".//div[@class='HwTilesComp MFIDare2CompareComp']//div[@class='innerContent']/p";
    private static final String PARTNER_CHECKBOXES = " .partners .partner input";
    private static final String BUTTON = " .d2c .footer .btn";

    @FindBy(id = MFI_LAYER)
    private WebElement mfiLayer;

    @FindBy(css = BUTTON)
    private WebElement searchButton;

    public HotelMFILayer(WebDriver webDriver) {
        super(webDriver, By.id(MFI_LAYER));
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(BUTTON), true));
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public List<WebElement> getParagraphs() {
        By by = By.xpath(LAYER_PARAGRAGHS);
        new WebDriverWait(getWebDriver(), 20)
                .until(new IsElementLocationStable(getWebDriver(), by));
        return getWebDriver().findElements(by);
    }

    public List<WebElement> getCheckboxes() {
        By by = By.cssSelector(PARTNER_CHECKBOXES);
        new WebDriverWait(getWebDriver(), 20)
                .until(new IsElementLocationStable(getWebDriver(), by));
        return getWebDriver().findElements(by);
    }

    public HotelMFILayer uncheckAllPartners() {
        By by = By.cssSelector(PARTNER_CHECKBOXES);
        for (int i = 0; i < getCheckboxes().size(); i++) {
            if (getWebDriver().findElements(by).get(i).isSelected()) {
                getWebDriver().findElements(by).get(i).click();
            }
        }
        return this;
    }

    public HotelMFILayer choosePartner(String sPartner) {
        String value;

        if (sPartner.toLowerCase().equals("kayak")) {
            value = "KA";
        }
        else if (sPartner.toLowerCase().equals("expedia")) {
            value = "EX";
        }
        else if (sPartner.toLowerCase().equals("priceline")) {
            value = "PC";
        }
        else if (sPartner.toLowerCase().equals("orbitz")) {
            value = "OB";
        }
        else if (sPartner.toLowerCase().equals("hotels")) {
            value = "HO";
        }
        else {
            throw new UnimplementedTestException("Partner '" + sPartner + "' is NOT recognized");
        }

        By by = By.cssSelector(PARTNER_CHECKBOXES + "[value=" + value + "]");
        getWebDriver().findElement(by).click();
        return this;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

}
