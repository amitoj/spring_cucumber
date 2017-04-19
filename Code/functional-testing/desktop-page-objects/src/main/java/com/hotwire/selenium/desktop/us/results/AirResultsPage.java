/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.selenium.desktop.us.partners.PartnerIMLFragment;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * Created by IntelliJ IDEA. User: v-abudyak Date: 5/12/12 Time: 7:16 PM To
 * change this template use File | Settings | File Templates.
 */
public class AirResultsPage extends AbstractPageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirResultsPage.class);
    private static final String RESULTS_HEADLINE = ".resultsHeadline";

    @FindBy(xpath = "//IMG[@alt='Show me'][1]//..")
    private WebElement opaqueLink;

    @FindBy(css = ".dollar, .lowPriceModule .price")
    private WebElement opaquePrice;

    @FindBy(css = ".dollars")
    private List<WebElement> retailPriceList;

    @FindBy(css = ".dollars")
    private WebElement retailPrice;

    @FindBy(css = ".price")
    private WebElement topPrice;

    @FindBy(css = ".links")
    private WebElement linkText;

    @FindBy(className = "PackageResultsSpeedBump")
    private WebElement speedBump;

    @FindBy(xpath = "//IMG[@alt='Save with a package']//..")
    private WebElement columnAModule;

    @FindBy(css = "a.changeSearchOpener")
    private WebElement changeYourSearch;

    @FindBy(css = RESULTS_HEADLINE)
    private WebElement flightsRouteInfo;

    @FindBy(name = "crsType")
    private WebElement crsType;

    @FindBy(css = ".freshResultsLayout .isAirResultsRedesign h2")
    private WebElement location;

    @FindBy(css = ".airSolution .priceLookup")
    private List<WebElement> priceLookups;


    @FindBy(css = ".airSolution .departure .origin")
    private List<WebElement> originDepartureTimes;

    @FindBy(css = ".airSolution .departure .destination")
    private List<WebElement> departureDestinationTimes;

    @FindBy(css = ".resultsSortingBar")
    private WebElement sortBar;

    @FindBy(css = ".lowPriceModule")
    private WebElement lowPriceModule;

    @FindBy(xpath = "//div[contains(@data-jsname,'MesoBanner')]")
    private WebElement mesoAds;

    @FindBy(xpath = "//*[@id='pageSelector']/following-sibling::strong")
    private WebElement resultsPageNumber;

    public AirResultsPage(WebDriver webDriver) {
        super(webDriver, new String[]{"tiles-def.air.results"});
    }

    public AirResultsPage(WebDriver webDriver, String locator) {
        super(webDriver, new String[]{locator});
    }

    public AirResultsPage(WebDriver webDriver, boolean skipInnerWait) {
        super(webDriver);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector(".airSolutionWrapper, .airSolution");
    }

    @Override
    protected int getTimeout() {
        return 90;
    }

    public void waitForUpdatingResults() {
        new WebDriverWait(getWebDriver(), 20).until(
            PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(".masked"), false));
    }

    public boolean sortResultsBy(String sortName) {
        WebElement sortElement;
        if (sortName.equalsIgnoreCase("price")) {
            sortElement = sortBar.findElement(By.cssSelector("a.price"));
            sortElement.click();
            waitForUpdatingResults();
            new AirResultsPage(getWebDriver());
            sortElement = sortBar.findElement(By.cssSelector("a.price"));
            return sortElement.getAttribute("class").contains("asc") ? true : false;
        }
        else if (sortName.equalsIgnoreCase("departure")) {
            sortElement = sortBar.findElement(By.cssSelector("a.departure"));
            sortElement.click();
            waitForUpdatingResults();
            new AirResultsPage(getWebDriver());
            sortElement = sortBar.findElement(By.cssSelector("a.departure"));
            return sortElement.getAttribute("class").contains("asc") ? true : false;
        }
        else if (sortName.equalsIgnoreCase("arrival")) {
            sortElement = sortBar.findElement(By.cssSelector("a.arrival"));
            sortElement.click();
            waitForUpdatingResults();
            new AirResultsPage(getWebDriver());
            sortElement = sortBar.findElement(By.cssSelector("a.arrival"));
            return sortElement.getAttribute("class").contains("asc") ? true : false;
        }
        else if (sortName.equalsIgnoreCase("stops")) {
            sortElement = sortBar.findElement(By.cssSelector("a.stops"));
            sortElement.click();
            waitForUpdatingResults();
            new AirResultsPage(getWebDriver());
            sortElement = sortBar.findElement(By.cssSelector("a.stops"));
            return sortElement.getAttribute("class").contains("asc") ? true : false;
        }
        else if (sortName.equalsIgnoreCase("duration")) {
            sortElement = sortBar.findElement(By.cssSelector("a.duration"));
            sortElement.click();
            waitForUpdatingResults();
            new AirResultsPage(getWebDriver());
            sortElement = sortBar.findElement(By.cssSelector("a.duration"));
            return sortElement.getAttribute("class").contains("asc") ? true : false;
        }
        throw new UnimplementedTestException("Unknown Air results sort criteria.");
    }

    public ArrayList<Double> getAllSolutionsPrices() {
        ArrayList<Double> prices = new ArrayList<Double>();
        for (WebElement priceLookup : priceLookups) {
            if (priceLookup.findElements(By.cssSelector(".dollars")).isEmpty()) {
                continue;
            }
            String price = priceLookup.findElement(By.cssSelector(".dollars")).getText().trim() + "." +
                priceLookup.findElement(By.cssSelector(".cents")).getText().trim();
            prices.add(new Double(price.replaceAll("[^0-9.]", "")));
        }
        return prices;
    }

    public ArrayList<LocalTime> getOriginDepartureTimeList() {
        ArrayList<LocalTime> originDepartureTimeList = new ArrayList<>();
        for (WebElement originDepartureTime : originDepartureTimes) {
            if (originDepartureTime.findElements(By.cssSelector(".time")).isEmpty()) {
                continue;
            }
            String text = originDepartureTime.findElement(By.cssSelector(".time")).getText().trim();
            originDepartureTimeList.add(LocalTime.parse(text, DateTimeFormat.shortTime()));
        }
        return originDepartureTimeList;
    }

    public ArrayList<LocalTime> getDepartureDestinationTimesList() {
        ArrayList<LocalTime> destinationDepartureTimeList = new ArrayList<LocalTime>();
        for (WebElement departureDestinationTime : departureDestinationTimes) {
            if (departureDestinationTime.findElements(By.cssSelector(".time")).isEmpty()) {
                continue;
            }
            String text = departureDestinationTime.findElement(By.cssSelector(".time")).getText().trim();
            LocalTime localTime = LocalTime.parse(text, DateTimeFormat.shortTime());
            if (departureDestinationTime.findElements(By.cssSelector(".plusDay")).size() > 0) {
                int plusDays = new Integer(departureDestinationTime.findElement(
                    By.cssSelector(".plusDay")).getText().trim().replaceAll("[^0-9]", "")).intValue();
                localTime = localTime.plusMillis(plusDays);
            }
            destinationDepartureTimeList.add(localTime);
        }
        return destinationDepartureTimeList;
    }

    public ArrayList<Integer> getTotalNumberOfStopsPerSolution() {
        ArrayList<Integer> stops = new ArrayList<Integer>();
        for (WebElement solution : getWebDriver().findElements(By.cssSelector(".airSolution"))) {
            if (solution.findElements(By.cssSelector(".numStops")).isEmpty()) {
                continue;
            }
            String dtime = solution.findElement(By.cssSelector(".departure .numStops")).getText().trim();
            String atime = solution.findElement(By.cssSelector(".arrival .numStops")).getText().trim();
            Integer departureStop = dtime.equals("Nonstop") ? 0 : new Integer(dtime.replaceAll("[^0-9]", ""));
            Integer arrivalStop = atime.equals("Nonstop") ? 0 : new Integer(atime.replaceAll("[^0-9]", ""));
            stops.add(departureStop.intValue() + arrivalStop.intValue());
        }
        return stops;
    }

    public ArrayList<Double> getTotalDurationPerSolution() {
        ArrayList<Double> durations = new ArrayList<Double>();
        for (WebElement solution : getWebDriver().findElements(By.cssSelector(".airSolution"))) {
            if (solution.findElements(By.cssSelector(".duration")).isEmpty()) {
                continue;
            }
            String dduration = solution.findElement(By.cssSelector(".departure .duration")).getText().trim();
            String aduration = solution.findElement(By.cssSelector(".arrival .duration")).getText().trim();
            String[] dparts = dduration.split(" ");
            String[] aparts = aduration.split(" ");
            Double dHour = new Double(dparts[0].replaceAll("[^0-9]", ""));
            Double dMin = new Double(dparts[1].replaceAll("[^0-9]", "")) / 60.0;
            Double aHour = new Double(aparts[0].replaceAll("[^0-9]", ""));
            Double aMin = new Double(aparts[1].replaceAll("[^0-9]", "")) / 60.0;
            durations.add(
                new Double(new DecimalFormat("###.##").format(
                    dHour.doubleValue() + dMin.doubleValue() + aHour.doubleValue() + aMin.doubleValue())));
        }
        return durations;
    }
    public Boolean chooseOpaque() {
        try {
            return opaqueLink.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void select(Integer resultNumberToSelect, String opaqueRetail) {

        if (opaqueRetail.equals("retail")) {
            By locator = By.cssSelector("a[id^=retailSolutionResults]");
            new WebDriverWait(getWebDriver(), getTimeout())
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

            List<WebElement> solutions = getWebDriver().findElements(locator);
            LOGGER.info("Retail results count is {}. Try to select {} item..", solutions.size(), resultNumberToSelect);

            if (solutions.size() > 0 && resultNumberToSelect < solutions.size() + 1) {
                solutions.get(resultNumberToSelect - 1).click();
            }
            else {
                throw new RuntimeException("Number of solution to select is out of range..");
            }
        }
        else {
            getWebDriver().findElement(By.cssSelector("a[id='airOpaqueSolutionResults']")).click();
        }
    }

    public Boolean compareOpaqueWithRetailPrice() {
        try {
            if (opaquePrice.isDisplayed()) {
                for (WebElement retailPriceElement : retailPriceList) {
                    if (!(Integer.parseInt(getOpaquePrice()) < Integer
                            .parseInt(retailPriceElement.getText().replaceAll("[^0-9]", "")))) {
                        return false;
                    }
                }
                return true;
            }
        }
        catch (NoSuchElementException e) {
            LOGGER.info("Non-delta flight (unmasked) deal was not displayed in air results page!");
        }
        return false;
    }

    public String getRetailPrice() {
        // Ignore getting cents.
        return retailPrice.getText().replaceAll("[^0-9]", "");
    }

    public String getTopPrice() {
        // Ignore getting cents.
        return topPrice.getText().replaceAll("[^0-9]", "");
    }

    public String getOpaquePrice() {
        // Ignore getting cents.
        return opaquePrice.getText().replaceAll("[^0-9]", "");
    }

    public Boolean verifySpeedBumpAndSaveText() {
        try {
            if (speedBump.isDisplayed()) {
                return true;
            }
        }
        catch (NoSuchElementException e) {
            LOGGER.info("If Speedbump and save text modules are not avaliable in page");
        }
        return false;
    }

    public Boolean verifyColumnA() {
        try {
            if (columnAModule.isDisplayed()) {
                return true;
            }
        }
        catch (NoSuchElementException e) {
            LOGGER.info("If Column A modules is not avaliable in page");
        }
        return false;
    }

    public void clickChangeSearchLink() {
        try {
            changeYourSearch.click();
        }
        catch (NoSuchElementException e) {
            LOGGER.info("changeYourSearch link is not available");
        }
    }

    public boolean verifyChangeRoutes(String string1, String string2) {
        AirResultsPage airResultPage = new AirResultsPage(getWebDriver());
        String route = airResultPage.flightsRouteInfo.getText();
        boolean routeInfo = true;
        if (!route.contains(string2) || !route.contains(string1)) {
            routeInfo = false;
            LOGGER.info("incorrect route: " + string1 + " & " + string2 + " are not as expected");
        }
        LOGGER.info("CORRECT ROUTE: " + string1 + " & " + string2 + " are as expcted");
        return routeInfo;
    }

    public Integer getAirResultsCount() {
        return retailPriceList.size();
    }

    public String getCRSTypeForBFS() {
        LOGGER.info("CRS Type: " + crsType.getAttribute("value") + " for BFS results");
        return crsType.getAttribute("value");
    }

    public String getCRSTypeForITA() {
        LOGGER.info("CRS Type: " + crsType.getAttribute("value") + " for ITA results");
        return crsType.getAttribute("value");
    }

    public String getLocation() {
        return location.getText();
    }

    public String getFromLocation() {
        return location.getText().replaceFirst(" to \\w+, \\w+ \\([A-Z]{3}\\)", "");
    }

    public String getToLocation() {
        return location.getText().replaceFirst("\\w+, \\w+ \\([A-Z]{3}\\) to ", "");
    }

    public PartnerIMLFragment getPartnerIMLFragment() {
        return new PartnerIMLFragment(
                getWebDriver(),
                By.cssSelector(".IM_outer_container.IM_multiclick, .IM_lightbox_container"));
    }

    public boolean isLowPriceModuleDisplayed() {
        try {
            return lowPriceModule.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public Double getLowPriceModulePrice() {
        WebElement priceElement = lowPriceModule.findElement(By.cssSelector(".priceBody"));
        return new Double(
            priceElement.findElement(By.cssSelector(".price")).getText().trim().replaceAll("[^0-9]", "")) / 100.00;
    }

    public boolean checkMesoAdsIsDisplayed() {
        return mesoAds.isDisplayed();
    }

    public String getResultsPageNumber() {
        String[] pagesNum = resultsPageNumber.getText().split("of ");
        return pagesNum[1];
    }

    public void getFlightAbovePrice(double expectedPrice) {
        boolean flightFound = false;
        boolean istooLowToShowDisplayed;

        int centinel = 0;
        //Declare the element we are looking for
        try {
            WebElement tooLowToShow = getWebDriver().findElement(By.xpath("//img[@alt='TOO LOW TO SHOW']"));
            istooLowToShowDisplayed = tooLowToShow.isDisplayed();
        }
        catch (NoSuchElementException e) {
            istooLowToShowDisplayed = false;
        }


        //Print boolean to standard out
        System.out.println("Too Low to show : " + istooLowToShowDisplayed);
        if (istooLowToShowDisplayed) {
            centinel = 1;
        }
        for (int i = centinel; i < priceLookups.size(); i++) {
            String textPrice = priceLookups.get(i).findElement(By.cssSelector(".dollars")).getText().trim() + "." +
                priceLookups.get(i).findElement(By.cssSelector(".cents")).getText().trim();

            String subString = textPrice.substring(1);
            Double price = Double.parseDouble(subString);

            if (price >= expectedPrice) {
                priceLookups.get(i).click();
                flightFound = true;
                break;
            }
        }
        if (flightFound) {
            System.out.println("Flight has been selected");
        }
        else {
            throw new RuntimeException("No flights meeting criteria were found");
        }
    }
}
