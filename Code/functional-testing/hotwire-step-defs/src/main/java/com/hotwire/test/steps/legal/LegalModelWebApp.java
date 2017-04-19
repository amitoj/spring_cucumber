/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.legal;

import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import com.hotwire.selenium.desktop.us.results.car.CarResultsPage;
import com.hotwire.selenium.desktop.us.results.car.CarResultsPageProvider;
import com.hotwire.selenium.desktop.us.results.car.impl.LegalNotesFragment;
import com.hotwire.util.webdriver.functions.PageName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-ngolodiuk
 * Since: 4/22/14
 */
public class LegalModelWebApp extends LegalModelTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(LegalModelWebApp.class.getName());

    @Override
    public void verifyText(String text, boolean textPresent) {
        super.verifyText(text, textPresent);
    }

    @Override
    public void verifyCopiesOnSearchResults() {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        LegalNotesFragment legalNotesFragment = carResultsPage.getLegalNotesFragment();

        boolean resultsContainsOpaqueSolutions = carResultsPage.getResult().isResultsContainOpaqueSolutions();
        boolean resultsContainsRetailSolutions = carResultsPage.getResult().isResultsContainRetailSolutions();

        //No disclaimers and footers when no results
        if (!resultsContainsOpaqueSolutions && !resultsContainsRetailSolutions) {
            assertThat(legalNotesFragment.isCopiesBlockIsDisplayed())
                .as("Rates disclaimer displays when should not.").isFalse();
        }
        else {
            // If opaque and retail cars are displayed
            if (resultsContainsOpaqueSolutions && resultsContainsRetailSolutions) {
                assertThat(carResultsPage.isWhatIsHotRateModuleDisplayed())
                    .as("<What is Hotwire Hot Rate?> doesn't appear").isTrue();

                assertThat(legalNotesFragment.isMixedRatesDisclaimerDisplayed())
                    .as("Rates disclaimer are not in place").isTrue();
            }
            //If retail results only
            else if (!resultsContainsOpaqueSolutions) {
                assertThat(legalNotesFragment.isRetailRatesDisclaimerDisplayed())
                    .as("retail rates disclaimer doesn't appear").isTrue();
            }

            //If opaque results only
            else {
                assertThat(legalNotesFragment.isOpaqueRatesDisclaimerDisplayed())
                    .as("Opaque rates disclaimer doesn't appear").isTrue();
            }

            assertThat(legalNotesFragment.isFootNotesDisplayed()).as("Footnotes don't appear").isTrue();

            if (carResultsPage.getResult().isResultsHasStrikeThroughPrices()) {
                assertThat(legalNotesFragment.isFootSavingsNotesDisplayed())
                    .as("Savings footnote doesn't appear").isTrue();
            }
        }
    }

    @Override
    public void verifyCopiesOnDetails() {
        if (CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage()
                .getVendorGridEntities().size() > 1) {
            List<String> footNoteList = CarBillingPageProvider.get(getWebdriverInstance())
                .getCarDetailsPage().getFootNoteList();

            String pageName = new PageName().apply(getWebdriverInstance());

        //if local search then two vendor grids  on details/billing and two disclaimers
            if (footNoteList.size() > 1) {
                assertThat(RETAIL_RATES_DISCLAIMER_TEXT).as("Retail rates disclaimer doesn't appear")
                        .isIn(footNoteList);
                assertThat(OPAQUE_RATES_DISCLAIMER_TEXT).as("Opaque rates disclaimer doesn't appear")
                        .isIn(footNoteList);
            }
            else {
                if ("tile.car-details.retail.billing".equalsIgnoreCase(pageName)) {
                    assertThat(RETAIL_RATES_DISCLAIMER_TEXT).
                        as("Retail rates disclaimer doesn't appear").isIn(footNoteList);
                }
                else if ("tile.car-details.opaque.billing".equalsIgnoreCase(pageName)) {
                    assertThat(footNoteList.isEmpty()).
                        as("Rates disclaimer displays when should not.").isTrue();
                }
            }
        }
        else {
            LOGGER.info("There is only 1 vendor being displayed, no vendor grid is displayed");
        }
    }
}
