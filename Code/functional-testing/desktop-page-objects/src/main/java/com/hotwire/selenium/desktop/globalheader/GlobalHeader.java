/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.globalheader;

import com.hotwire.selenium.desktop.account.AccountOverviewPage;
import com.hotwire.selenium.desktop.account.RegisterNewUserPage;
import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.HomePage;
import com.hotwire.selenium.desktop.us.SignInPage;
import com.hotwire.selenium.desktop.us.helpcenter.HelpCenterPage;
import com.hotwire.selenium.desktop.us.index.HotelIndexPage;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Global header page object for all Hotwire pages.
 *
 * @author vjong
 */
public class GlobalHeader extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalHeader.class);

    private static final String CSS_HOTWIRE_LOGO = ".hotwireLogo a img";
    private static final String MAIN_NAVIGATION_TABS = ".hwMainNavigation";
    private static final String SELECT_CURRENCY_CODE = ".currencySelection";
    private static final String SELECT_CURRENCY_DROPDOWN = SELECT_CURRENCY_CODE + " .currencyDropdown";
    private static final String SELECT_COUNTRY_DROPDOWN = ".countryDropdown";
    private static final String SIGN_IN_MODULE = ".signInModule";
    private static final String CSS_SIGN_IN_DIALOG_OPENER = ".headerSignIn .signInModule .helloContainer";
    private static final String CSS_HF_CUSTOMER_STAR = ".hfCustomer";
    // This link goes to the same page as sign in action link, which has both sign in and sign up frames.

    private static final String ACTIVE_VERTICAL_LOCATOR = "//div[@class='active']//span";
    private static final long DEFAULT_WAIT = 15;
    private static final int DEFAULT_ITERATIONS = 5;

    @FindBy(css = CSS_HOTWIRE_LOGO)
    private WebElement logo;

    @FindBy(css = ".helpCenter a")
    private WebElement helpCenterLink;

    @FindBy(css = MAIN_NAVIGATION_TABS)
    private WebElement verticalNavigationTabs;

    @FindBy(css = SELECT_CURRENCY_DROPDOWN)
    private WebElement selectCurrencyDropdown;

    @FindBy(css = SELECT_COUNTRY_DROPDOWN)
    private WebElement selectCountryDropdown;

    @FindBy(xpath = ACTIVE_VERTICAL_LOCATOR)
    private WebElement activeVertical;

    @FindBy(css = CSS_SIGN_IN_DIALOG_OPENER)
    private WebElement signInDialogOpener;

    @FindBy(css = CSS_HF_CUSTOMER_STAR)
    private WebElement hfCustomer;

    private Map<String, String> verticalLandingPagesMap;
    private Map<String, String[]> verticalPageNames;

    public GlobalHeader(WebDriver driver) {
        super(driver, By.cssSelector(".hwGlobalHeader"));
        initVerticalPageNamesMap();
        initVerticalLandingPagesMap();
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(CSS_HOTWIRE_LOGO);
    }

    @SuppressWarnings("serial")
    private void initVerticalPageNamesMap() {
        verticalPageNames = new HashMap<String, String[]>() { {
                put("Hotels", new String[] {"tiles-def.hotel.index", "tile.hotel.index", "tile.hotwire.hotel"});
                put("Cars", new String[] {"tiles-def.car.index", "tile.hotwire.car"});
                put("Flights", new String[] {"tiles-def.air.index", "tile.hotwire.air"});
                put("Vacations", new String[] {"tiles-def.package.index", "tile.hotwire.package"});
            }
        };
    }

    @SuppressWarnings("serial")
    private void initVerticalLandingPagesMap() {
        verticalLandingPagesMap = new HashMap<String, String>() { {
                put("Hotels", "/hotel");
                put("Cars", "/car/index.jsp");
                put("Flights", "/air/index.jsp");
                put("Vacations", "/package/index.jsp");
            }
        };
    }

    public boolean currentlyOnDomesticSite() {
        return hasVerticalNavigationTabs();
    }

    public void clickSignInAndWaitForAction() {
        signInDialogOpener.click();
    }

    public boolean doesExistSignInDialogOpener() {
        try {
            signInDialogOpener.isDisplayed();
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }

    public RegisterNewUserPage navigateToNewUser() {
        clickSignInAndWaitForAction();
        new UserActionsFragment(getWebDriver()).navigateToNewUser();
        return new RegisterNewUserPage(getWebDriver());
    }

    public SignInPage navigateToSignInPage() {
        clickSignInAndWaitForAction();
        new UserActionsFragment(getWebDriver()).navigateToNewUser();
        return new SignInPage(getWebDriver());
    }

    public AccountOverviewPage navigateToMyAccount() {
        clickSignInAndWaitForAction();
        new UserActionsFragment(getWebDriver()).navigateToMyAccount();
        return new AccountOverviewPage(getWebDriver());
    }

    public SignInPage logoutOfAccount() {
        if (getWebDriver().findElements(By.cssSelector(SIGN_IN_MODULE + " .notAUserLink a")).size() > 0) {
            completeSignoutFromCookiedMode();
        }
        else {
            LOGGER.info("FULLY SIGNED IN. Signing out of full signed in mode to cookied mode.");
            clickSignInAndWaitForAction();
            new UserActionsFragment(getWebDriver()).logout();
            // Get latest state of app and it's global header.
            new GlobalHeader(getWebDriver());
            completeSignoutFromCookiedMode();
        }
        return new SignInPage(getWebDriver());
    }

    public void logoutToCookieMode() {
        if (getWebDriver().findElements(By.cssSelector(SIGN_IN_MODULE + " .notAUserLink a")).size() > 0) {
            // Already in cookied mode.
            return;
        }
        LOGGER.info("FULLY SIGNED IN. Signing out of full signed in mode to cookied mode.");
        clickSignInAndWaitForAction();
        new UserActionsFragment(getWebDriver()).logout();

        // circular reference is removed here..
        if (hasVerticalNavigationTabs()) {
            // Domestic UHP.
            new HomePage(getWebDriver());
        }
        else {
            // Intl. hotel index page.
            new HotelIndexPage(getWebDriver());
        }
    }

    public void completeSignoutFromCookiedMode() {
        LOGGER.info("IN COOKIE MODE. Signing out of cookie mode for complete sign out.");
        getWebDriver().findElement(By.cssSelector(SIGN_IN_MODULE + " .notAUserLink a")).click();
        LOGGER.info("Signed out completely.");
    }

    public boolean isLoggedIn() {
        if (getWebDriver().findElements(By.cssSelector(SIGN_IN_MODULE + " .notAUserLink .customerName")).size() == 0 &&
            getWebDriver().findElements(By.cssSelector(SIGN_IN_MODULE + " .helloContainer .hello")).size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isLoggedInCookieMode() {
        return getWebDriver().findElements(By.cssSelector(SIGN_IN_MODULE + " .notAUserLink .customerName")).size() > 0;
    }

    public String getActiveVertical() {
        return this.activeVertical.getText();
    }

    public AbstractUSPage navigateToVerticalLandingPageFromNavigationTab(final String verticalTabName) {
        return navigateToVerticalLandingPageFromNavigationTab(verticalTabName, false /* return desktop page */);
    }

    public AbstractUSPage navigateToVerticalLandingPageFromNavigationTab(final String verticalTabName,
                                                                         boolean returnAngularPageObject) {

        WebElement link = verticalNavigationTabs.findElement(
                By.xpath("//a[contains(@href, '" + verticalLandingPagesMap.get(verticalTabName) + "')]"));
        /**
         * Fix clicking on landing page link for IE
         */
        link.click();
        //((JavascriptExecutor) getWebDriver()).executeScript("arguments[0].click();", link);

        // Return abstract vertical index page with the proper tiles-def. We could create individual vertical page
        // objects and change the map to return the right vertical page object. For now this will work just fine as
        // we don't really need to worry about elements of each individual page as getting to these pages from clicking
        // the tabs will do a full browser page load.
        if (returnAngularPageObject) {
            return new AbstractUSPage(getWebDriver());
        }
        else {
            return new AbstractUSPage(getWebDriver(), verticalPageNames.get(verticalTabName));
        }
    }

    public void changeCurrency(String currency) {
        openCurrencyDropdown();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT).until(new IsElementLocationStable(getWebDriver(), By
            .cssSelector(SELECT_CURRENCY_DROPDOWN + " ul"), DEFAULT_ITERATIONS));
        List<WebElement> elements = selectCurrencyDropdown.findElements(By.cssSelector(" li a"));
        boolean found = false;
        for (WebElement element : elements) {
            if (element.getAttribute("data-currencycode").toLowerCase().trim().equals(currency.toLowerCase().trim())) {
                found = true;
                element.click();
                break;
            }
        }
        if (!found) {
            throw new RuntimeException(currency + " not found in currency selection list.");
        }
    }

    public String getCurrency() {
        return getSelectedCurrency();
    }

    public Collection<String> getSupportedCurrencies() {
        Collection<String> currencies = new HashSet<String>();
        openCurrencyDropdown();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT).until(new IsElementLocationStable(getWebDriver(), By
            .cssSelector(SELECT_CURRENCY_DROPDOWN + " ul"), DEFAULT_ITERATIONS));
        List<WebElement> elements = selectCurrencyDropdown.findElements(By.cssSelector(" li a"));

        for (WebElement element : elements) {
            currencies.add(element.getAttribute("data-currencycode"));
        }
        return currencies;
    }

    private void openCurrencyDropdown() {
        selectCurrencyDropdown.findElement(By.cssSelector(" dt a")).click();
    }

    /**
     * @return The full selected currency String. For example, USD US Dollar, DKK Danish Krone, etc.
     */
    public String getSelectedCurrency() {
        return selectCurrencyDropdown.findElement(By.cssSelector(" dt a")).getText();
    }

    /**
     * @return The letter currency code of the selected currency. For example, USD, DKK, etc.
     */
    public String getSelectedCurrencyCode() {
        return getSelectedCurrency().split("\\s+")[0];
    }
    public String getSelectedCountry() {
        return selectCountryDropdown.findElement(By.cssSelector(" dt a span")).getAttribute("title");
    }

    public void selectPOS(String country) {
        selectCountryDropdown.findElement(By.cssSelector(" dt a")).click();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT).until(new IsElementLocationStable(getWebDriver(), By
            .cssSelector(SELECT_COUNTRY_DROPDOWN + " ul"), DEFAULT_ITERATIONS));
        List<WebElement> elements = selectCountryDropdown.findElements(By.cssSelector(" li a"));
        boolean found = false;
        for (WebElement element : elements) {
            String listText = element.getText().trim().toLowerCase();
            String modifiedCountry = country.trim().toLowerCase();
            if (listText.equals(modifiedCountry)) {
                found = true;
                element.click();
                break;
            }
            else if ((modifiedCountry.equals("us") || modifiedCountry.equals("united states") ||
                    modifiedCountry.equals("canada")) && listText.equals("us/canada")) {
                found = true;
                element.click();
                break;
            }
        }
        if (!found) {
            throw new RuntimeException(country + " not found in currency selection list.");
        }
    }

    public HelpCenterPage navigateToHelpCenterPage() {
        helpCenterLink.click();
        return new HelpCenterPage(getWebDriver());
    }

    public RegisterNewUserPage goToRegistrationPage() {
        return navigateToNewUser();
    }

    public void logOut() {
        logoutOfAccount();
    }

    public void clickOnHotwireLogo() {
        logo.click();
    }

    public String getCurrentPageURL() {
        return getWebDriver().getCurrentUrl();
    }

    public boolean hasVerticalNavigationTabs() {
        return getWebDriver().findElements(By.cssSelector(MAIN_NAVIGATION_TABS + " a")).size() > 0;
    }

    public SignInPage navigateToMyTrips() {
        clickSignInAndWaitForAction();
        new UserActionsFragment(getWebDriver()).navigateToMyAccount();
        return new SignInPage(getWebDriver());
    }

    public void navigateToMyTripsAsSignedInUser() {
        clickSignInAndWaitForAction();
        new UserActionsFragment(getWebDriver()).navigateToMyTripsAsSignedInUser();
    }

    public boolean areHfcElementsDisplayed() {
        clickSignInAndWaitForAction();
        try {
            return new UserActionsFragment(getWebDriver()).isExpressLinkDisplayed() && hfCustomer.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
