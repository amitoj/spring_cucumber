/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.cruise;

import com.hotwire.test.steps.search.SearchModelTemplate;
import com.hotwire.testing.UnimplementedTestException;

/**
 * @author akrivin
 * @since 10/16/12
 */
public class CruiseSearchModelRowWebApp extends SearchModelTemplate<CruiseSearchParameters>
    implements CruiseSearchModel {

    @Override
    public void navigateToCruisePage() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickLastMinuteCruiseDeal() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void clickTopCruiseDeal() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyCruiseSearchResultsUrl() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyCruiseDetailsUrl() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void verifyOpinionLink() {
        throw new UnimplementedTestException("Not implemented yet");
    }

    @Override
    public void navigateBackFromCruiseResultsPage() {
        throw new UnimplementedTestException("Not implemented yet");
    }
}
