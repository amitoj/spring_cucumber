/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car.fragments;

import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import com.hotwire.selenium.desktop.us.results.car.CarResultsPageProvider;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


/**
 * User: achotemskoy
 * Date: 1/12/15
 * Time: 1:20 PM
 * com.hotwire.selenium.desktop.us.results.car.fragments
 * <p/>
 Basically it is list of webElements (wrapped to CarSolutionFragment) with some additional features.
 Represents list of car solutions on car results page.
 Also provides different methods to filter list by different parameters.
 For example you need to select cheapest opaque solution:
 <p></p>
 CarResultsPageProvider.get(driver).getResult()
 .opaque()
 .cheapest()
 .select();
 <p></p>
 You can extend it with any filters that you need. If you don't care about type, price, and other - simply use:
 <p></p>
 CarResultsPageProvider.get(driver).getResult()
 .select();
 <p></p>
 This will choose first result for you.
 *
 * @param <T> - This is CarSolutionFragment object
 */
public class CarSolutionFragmentsList<T> extends ArrayList<CarSolutionFragment> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSolutionFragmentsList.class.getName());
    private WebDriver driver;

    public CarSolutionFragmentsList(WebDriver driver) {
        this.driver = driver;
        //searching for all solutions and filling list with wrapped
        for (WebElement solution : this.driver.findElements(By.cssSelector(".resultDetails"))) {
            this.add(new CarSolutionFragment(solution));
        }
    }

    //START of FILTERS methods.
    // Using Iterators instead for each to avoid - java.util.ConcurrentModificationException

    /**
     * Filters this list to have only cheapest solution.
     * <p></p>
     * Be aware - if you are using this function before others -
     * there will be only one cheapest result (no matter if you applied sorting from dropdown on UI or not.),
     * and future filters may fail selecting because of no matches. Better to use this filter after all other filters.
     * <p></p>
     * In case if you just need any solution - you can use select() function without other functions.
     * @return this list to be able build chains of calls.
     */
    public CarSolutionFragmentsList cheapest() {
        CarSolutionFragment cheapestCar = this.get(0);
        for (Iterator<CarSolutionFragment> iterator = this.listIterator(); iterator.hasNext();) {
            CarSolutionFragment solutionFragment = iterator.next();
            if (solutionFragment.getTotalPrice() <= cheapestCar.getTotalPrice()) {
                cheapestCar = solutionFragment;
            }
            else {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * Filters this list to have only opaque or retail solutions.
     * This will remove solutions from list that is not match.
     * @param opaqueOrRetail string in format "opaque" or "retail" also accepts "" as a parameter.
     * @return this list to be able build chains of calls.
     */
    public CarSolutionFragmentsList opaqueOrRetail(String opaqueOrRetail) {
        //In case of empty parameter - ignoring this method call.
        //You can also decide to call select() directly.
        if (opaqueOrRetail.isEmpty()) {
            return this;
        }

        for (Iterator<CarSolutionFragment> iterator = this.listIterator(); iterator.hasNext();) {
            CarSolutionFragment solutionFragment = iterator.next();
            if (opaqueOrRetail.equalsIgnoreCase("retail") && solutionFragment.isOpaque() ||
                    opaqueOrRetail.equalsIgnoreCase("opaque") && !solutionFragment.isOpaque()) {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * Filters this list to have only opaque solutions.
     * This will remove solutions from list that is not match.
     * @return this list to be able build chains of calls.
     */
    public CarSolutionFragmentsList opaque() {
        for (Iterator<CarSolutionFragment> iterator = this.listIterator(); iterator.hasNext();) {
            CarSolutionFragment solutionFragment = iterator.next();
            if (!solutionFragment.isOpaque()) {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * Filters this list to have only retail solutions.
     * This will remove solutions from list that is not match.
     * @return this list to be able build chains of calls.
     */
    public CarSolutionFragmentsList retail() {
        for (Iterator<CarSolutionFragment> iterator = this.listIterator(); iterator.hasNext();) {
            CarSolutionFragment solutionFragment = iterator.next();
            if (solutionFragment.isOpaque()) {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * Filters this list to have only solutions at airport location.
     * Airport location solutions are exist for local search (search inside city, not airport).
     * This will remove solutions from list that is not match.
     * This filter will do nothing if you launched search for airport, because all solutions will be at airport.
     * @return this list to be able build chains of calls.
     */
    public CarSolutionFragmentsList withAirportLocation() {
        //skipping filter if we have airport search results.
        if (!CarResultsPageProvider.get(driver).isNearbyAirportModuleDisplayed()) {
            return this;
        }
        for (Iterator<CarSolutionFragment> iterator = this.listIterator(); iterator.hasNext();) {
            CarSolutionFragment solutionFragment = iterator.next();
            if (!solutionFragment.isAirportLocation()) {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * Filters this list to have only solutions with specified CarTypeName.
     * Car type name - like "Mini (Manual, no A/C)" , "Standart" or "Standard Wagon (Manual)"
     * But formated with this - .replaceAll("^[a-zA-Z0-9]+:", "");
     * @param carTypeName String, what carTypeName to look for
     * @return this list to be able build chains of calls.
     */
    public CarSolutionFragmentsList withCarTypeName(String carTypeName) {
        for (Iterator<CarSolutionFragment> iterator = this.listIterator(); iterator.hasNext();) {
            CarSolutionFragment solutionFragment = iterator.next();
            if (!solutionFragment.getCarTypeName().equalsIgnoreCase(carTypeName)) {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * Filters this list to have only solutions with specified CarTypeName.
     * Car type code - like SPAR, ECAR, CCAR
     * But formated with this - .replaceAll("^[a-zA-Z0-9]+:", "");
     * @param carTypeCode String, what carTypeCode to look for
     * @return this list to be able build chains of calls.
     */
    public CarSolutionFragmentsList withCarTypeCode(String carTypeCode) {
        for (Iterator<CarSolutionFragment> iterator = this.listIterator(); iterator.hasNext();) {
            CarSolutionFragment solutionFragment = iterator.next();
            if (!solutionFragment.getCarTypeCode().equalsIgnoreCase(carTypeCode)) {
                iterator.remove();
            }
        }
        return this;
    }

    //END of FILTERS methods.

    /**
      Use this to select first result from current list.
      There is 2 ways to use this method.
      First is just call
      CarResultsPage.getResult().select();  - this will proceed to details for first solution.
      Second is - use filtering methods first.
      Methods like withCarTypeCode(), withCarTypeName, withAirportLocation(),
      and other methods from Filters block - they will remove solutions that is not meet filter criteria.
      So you can build chains of calls like -
      CarResultsPageProvider.get(getWebdriverInstance())
        .getResult()
        .withAirportLocation()
        .withCarTypeName(carModels)
        .select();
     <p></p>
     @throws ZeroResultsTestException in case there is no results to select.
     Be careful with this exception. It produces pending exception, not step crash. So test looks "yellow".
     But there actually can be a bug.
     */
    public CarSolutionModel select() {
        //wait for page stabilization after soldout redirect
        new WebDriverWait(driver, 5).until(presenceOfElementLocated(By.xpath("(//*[@class='carDetails'])[1]")));

        CarSolutionModel carModel;
        try {
            carModel = this.get(0).select();
        }
        catch (IndexOutOfBoundsException ignored) {
            LOGGER.error("Cannot find results according to your request.", ignored);
            throw new ZeroResultsTestException("Cannot find results according to your request.");
        }

        //Checking that we are not returned back to results page.
        //This can happen when application cannot find selected solution - for example it is sold out.
        //noinspection ConstantConditions
        if ((new PageName().apply(driver).contains("car.results") &&
                driver.findElement(By.cssSelector(".priceChangeMssg")).isDisplayed()) ||
                (new PageName().apply(driver).contains("car.results") &&
                        driver.findElement(By.cssSelector(".unavailableMessage")).isDisplayed())) {
            LOGGER.info(
                    "Inventory check redirected to results page due to sold out. Selecting the next solution.");
            select();
        }
        return carModel;
    }

    //Verify that no results are present.
    public boolean isResultsEmpty() {
        try {
            super.get(0);
            return false;
        }
        catch (IndexOutOfBoundsException e) {
            return true;
        }
    }

    //Verifying that our results contain at least one opaque or retail solution.
    //In this case additional legal text displayed.
    public boolean isResultsContainRetailSolutions() {
        try {
            this.get(0);
        }
        catch (IndexOutOfBoundsException ignored) {
            LOGGER.debug("List of solutions is empty. Returning false.", ignored);
            return false;
        }
        for (CarSolutionFragment solutionFragment : this) {
            if (!solutionFragment.isOpaque()) {
                return true;
            }
        }
        return false;
    }

    public boolean isResultsContainOpaqueSolutions() {
        try {
            this.get(0);
        }
        catch (IndexOutOfBoundsException ignored) {
            LOGGER.debug("List of solutions is empty. Returning false.", ignored);
            return false;
        }
        for (CarSolutionFragment solutionFragment : this) {
            if (solutionFragment.isOpaque()) {
                return true;
            }
        }
        return false;
    }

    public boolean isResultsContainLocationDescription(String location) {
        for (CarSolutionFragment solutionFragment : this) {
            if (solutionFragment.getDistanceDescription().contains(location)) {
                return true;
            }
        }
        return false;
    }

    /**
    There is special legal texts that appears only if at least one solution has strikethrough price.
    Usually this is opaque solutions, but sometimes we have opaque solutions without strikethrough price.
    */
    public boolean isResultsHasStrikeThroughPrices() {
        boolean strikeThroughPricesInResults = false;
        for (CarSolutionFragment solutionFragment : this) {
            if (solutionFragment.getStrikeThroughPrice() != 0.0) {
                strikeThroughPricesInResults = true;
                break;
            }
        }
        return strikeThroughPricesInResults;
    }

    /**
    Provides direct access to result. You can choose any result by index.
    For example - to get first result, and verify that it is with lowest price -
    <p></p>
    CarResultsPageProvider.get(getWebdriverInstance()
        .getResult()
        .get(0)
        .isLowestPrice();
    //This will return boolean.
    <p></p>
    @return CarSolutionFragment
    @throws ZeroResultsTestException when element with index not exist.
    */
    @Override
    public CarSolutionFragment get(int index) {
        try {
            return super.get(index);
        }
        catch (IndexOutOfBoundsException noResults) {
            throw new ZeroResultsTestException("Cannot find results according to your request.");
        }
    }

}
