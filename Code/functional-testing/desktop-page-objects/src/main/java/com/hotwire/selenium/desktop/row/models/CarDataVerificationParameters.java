/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.models;

import com.hotwire.selenium.desktop.us.models.CarSolutionModel;

/**
 * @author v-ypuchkova
 * @since 2012.14
 */
public class CarDataVerificationParameters {

    /**
     * The set of values for verification search parameters with results parameters
     */
    private CarIntlSolutionModel optionSetForSearch;

    /**
     * The set of values from results page for verification parameters
     */
    private CarIntlSolutionModel optionSetOnResult;

    /**
     * The set of values from details page for verification parameters
     */
    private CarIntlSolutionModel optionSetOnDetails;

    /**
     * The set of values from confirmation page for verification parameters
     */
    private CarIntlSolutionModel optionSetOnConfirmation;

    /**
     * The set of values for particular solution from result set
     */
    private CarSolutionModel optionSetForParticularSolution;

    private boolean shouldSaveDataForVerification;

    public CarIntlSolutionModel getOptionSetForSearch() {
        return optionSetForSearch;
    }

    public void setOptionSetForSearch(CarIntlSolutionModel optionSetForSearch) {
        this.optionSetForSearch = optionSetForSearch;
    }

    public CarSolutionModel getOptionSetForParticularSolution() {
        return optionSetForParticularSolution;
    }

    public void setOptionSetForParticularSolution(CarSolutionModel optionSetForParticularSolution) {
        this.optionSetForParticularSolution = optionSetForParticularSolution;
    }

    public CarIntlSolutionModel getOptionSetOnResult() {
        return optionSetOnResult;
    }

    public void setOptionSetOnResult(CarIntlSolutionModel optionSetOnResult) {
        this.optionSetOnResult = optionSetOnResult;
    }

    public CarIntlSolutionModel getOptionSetOnDetails() {
        return optionSetOnDetails;
    }

    public void setOptionSetOnDetails(CarIntlSolutionModel optionSetOnDetails) {
        this.optionSetOnDetails = optionSetOnDetails;
    }

    public CarIntlSolutionModel getOptionSetOnConfirmation() {
        return optionSetOnConfirmation;
    }

    public void setOptionSetOnConfirmation(CarIntlSolutionModel optionSetOnConfirmation) {
        this.optionSetOnConfirmation = optionSetOnConfirmation;
    }

    public boolean isShouldSaveDataForVerification() {
        return shouldSaveDataForVerification;
    }

    public void setShouldSaveDataForVerification(boolean shouldSaveDataForVerification) {
        this.shouldSaveDataForVerification = shouldSaveDataForVerification;
    }
}
