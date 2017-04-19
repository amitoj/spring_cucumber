/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing;

import com.hotwire.selenium.desktop.us.billing.onepage.HotelPayPalFragment;
import com.hotwire.selenium.desktop.us.billing.onepage.HotelVmeFragment;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jreichenberg
 * Date: 7/17/12
 * Time: 12:03 PM
 */
public abstract class AbstractBillingPage extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBillingPage.class);

    private static final long VISIBLE_WAIT = 20;

    private static final String[] BILLING_PAGE_TILES = new String[]{"*.billing", "tile.hotel.billing.onePageBilling"};
    private static final String CONTINUE_AS_GUEST_BUTTON = "btnContinueAsGuest";

    @FindBy(name = CONTINUE_AS_GUEST_BUTTON)
    private WebElement continueAsGuestButton;

    @FindBy(css = "div.layoutColumnRight div.priceChangeMessage")
    private WebElement priceHasChanged;

    public AbstractBillingPage(WebDriver webdriver) {
        super(webdriver, BILLING_PAGE_TILES);
    }

    public void submitPanel(WebElement panelContinueBtn) {

        try {
            LOGGER.info(priceHasChanged.getText() + "\n");
        }
        catch (NoSuchElementException e) {
            // no action
        }

        //grab the panel container that contains the button for checks later
        WebElement panel = panelContinueBtn.findElement(
                By.xpath("./ancestor::*[contains(@class, 'yui-accordion-content')]"));
        new WebDriverWait(getWebDriver(), VISIBLE_WAIT).until(ExpectedConditions.visibilityOf(panelContinueBtn));
        panelContinueBtn.click();
    }

    public AdditionalFeatures fillAdditionalFeatures() {
        return new AdditionalFeaturesFragment(getWebDriver());
    }

    /**
     * For now, to bypass Activities panel if it exists
     * <p/>
     */
    public void selectActivities() {
        if (getWebDriver().findElements(By.id("billingPanelActivitiesComp")).size() > 0) {
            //has destination services / activities....for now, just click-through on our way to additional features
            new ActivitiesFragment(getWebDriver()).continuePanel();
        }
    }

    /**
     * This is fast solve. Will be fixed in the near feature!
     * checks that selected solution is prepaid
     */
    public boolean isPrepaidSolution() {
        try {
            WebElement reviewDetailsFragment =
                    getWebDriver().findElement(By.xpath("//div[contains(@class, 'reviewCarDetails')]"));
            return "true".equals(reviewDetailsFragment.getAttribute("data-prepaid"));
        }
        catch (NoSuchElementException e) {
            // no action
        }
        return false;
    }

    public void book() {
        new PurchaseReviewFragment(getWebDriver()).continuePanel();
    }

    public void selectAsGuest() {
        List<WebElement> continueAsGuest = getWebDriver().findElements(By.name(CONTINUE_AS_GUEST_BUTTON));
        boolean continueAsGuestButtonSeen = false;
        if (continueAsGuest.size() > 0) {
            continueAsGuestButtonSeen = true;
            continueAsGuestButton.click();
        }
        LOGGER.info("Continue as guest button rendered: " + continueAsGuestButtonSeen);
    }

    public void selectAsUser(String sEmail, String sPassword) {
        WebElement signIn = getWebDriver().findElement(By.xpath("//img[@alt='Sign in']"));
        if (signIn != null) {
            WebElement email = getWebDriver().findElement(By.name("loginForm._NAE_email"));
            WebElement password = getWebDriver().findElement(By.name("loginForm._NAE_password"));
            email.sendKeys(sEmail);
            password.sendKeys(sPassword);
            signIn.click();
            //...wait until Ajax is silent
            new Wait<>(new IsAjaxDone()).maxWait(15).apply(getWebDriver());
        }
    }

    public HotelPayPalFragment fillPayPalPaymentMethod() {
        throw new UnimplementedTestException("Not implemented");
    }

    public HotelVmeFragment getVmePaymentFragment() {
        throw new UnimplementedTestException("Implement me in a subclass");
    }
}
