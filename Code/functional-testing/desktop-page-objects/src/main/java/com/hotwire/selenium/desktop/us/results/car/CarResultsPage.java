/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results.car;

import com.hotwire.selenium.desktop.us.results.car.fragments.CarSolutionFragment;
import com.hotwire.selenium.desktop.us.results.car.fragments.CarSolutionFragmentsList;
import com.hotwire.selenium.desktop.us.results.car.impl.LegalNotesFragment;

/**
 * User: v-vzyryanov
 * Date: 1/16/13
 * Time: 6:44 AM
 */
public interface CarResultsPage {

    String getDesignId();

    Boolean isDiscountNoteDisplayed();

    boolean isNoResultsMessageDisplayed();

    boolean isWhatIsHotRateModuleDisplayed();

    LegalNotesFragment getLegalNotesFragment();

    boolean isNearbyAirportModuleDisplayed();

    String getStartLocation();

    CarSolutionFragmentsList<CarSolutionFragment> getResult();

    boolean isIntentMediaElementsExisting();

    boolean isMeSoBannersDisplayed();
}





