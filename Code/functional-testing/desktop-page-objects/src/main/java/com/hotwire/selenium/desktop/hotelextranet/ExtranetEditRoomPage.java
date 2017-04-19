/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.hotelextranet;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Edit room page
 */
public class ExtranetEditRoomPage extends AbstractPageObject {

    @FindBy(name = "numRooms")
    private WebElement bulkInventoryRoomInput;

    @FindBy(name = "rate")
    private WebElement bulkInventoryRateInput;

    @FindBy(name = "fee")
    private WebElement bulkInventoryEPFInput;

    @FindBy(name = "xtraOut")
    private WebElement bulkInventorySoldOutInput;

    @FindBy(name = "soldArrival")
    private WebElement bulkInventoryClosedArrivalInput;

    @FindBy(name = "preview")
    private WebElement previewChangesBtn;

    @FindBy(xpath = "//div[@class='fRt']/button[2]")
    private WebElement saveChangesBtn;

    @FindBy(id = "savedChangesConfirmation")
    private WebElement confirmationMessageElement;

    /**
     * <div class="errorMessages">
     * <h6>Error</h6>
     * <p>Please correct the following field(s):</p>
     * <ul>
     * <li> <b class="bold">Hotwire ID</b> field is blank or invalid.</li>
     * </ul>
     * </div>
     */
    @FindBy(css = "div.errorMessages")
    private WebElement authenticationError;

    public ExtranetEditRoomPage(WebDriver webdriver) {
        super(webdriver, new String[]{"tiles-def.xnet.edit-room-details"});
    }

    /**
     * enter bulk room
     *
     * @param inventory
     */
    public void updateBulkRooms(int inventory) {
        bulkInventoryRoomInput.sendKeys(String.valueOf(inventory));
    }

    /**
     * enter bulk rate
     *
     * @param rate
     */
    public void updateBulkRate(float rate) {
        bulkInventoryRateInput.sendKeys(String.valueOf(rate));
    }

    /**
     * enter bulk extra person fee
     *
     * @param epf
     */
    public void updateBulkEPF(float epf) {
        bulkInventoryEPFInput.sendKeys(String.valueOf(epf));
    }

    /**
     * Preview changes and Save changes
     */
    public void previewAndSaveChanges() {
        previewChangesBtn.click();
        saveChangesBtn.click();
    }

    /**
     * Verify confirmation message is displayed
     */
    public void verifyConfirmationMessage() {
        confirmationMessageElement.isDisplayed();
    }
}
