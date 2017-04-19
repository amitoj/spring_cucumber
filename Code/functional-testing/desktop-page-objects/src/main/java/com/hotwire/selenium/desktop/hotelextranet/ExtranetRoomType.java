/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.hotelextranet;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.ui.ExtendedSelect;

/**
 * @author adeshmukh
 *
 */
public class ExtranetRoomType extends AbstractUSPage {

    private static List<String> actualRoomTypeSetting = new ArrayList<String>();

    @FindBy(name = "bedTypeName1")
    private WebElement bedTypeName;

    @FindBy(name = "maxRoomsPerBooking1")
    private WebElement maxRoomsPerBooking;

    @FindBy(name = "maxGuestsPerRoom1")
    private WebElement maxGuestsPerRoom;

    @FindBy(name = "maxAdultsPerRoom1")
    private WebElement maxAdultsPerRoom;

    @FindBy(name = "baseOccupancy1")
    private WebElement baseOccupancy;

    @FindBy(name = "isActive1")
    private WebElement isActive;

    @FindBy(xpath = "//select[@name='bedTypeName1']/option[@selected='true']")
    private WebElement selectedBedType;

    @FindBy(xpath = "//select[@name='isActive1']/option[@selected='']")
    private WebElement selectedState;

    @FindBy(css = "button#saveBtn")
    private WebElement saveButton;

    @FindBy(xpath = "//div[contains(@class,'confirmMessages')]/p")
    private WebElement confirmationMessage;

    @FindBy(linkText = "Hotel overview")
    private WebElement hotelOverview;

    /**
     * @param webDriver
     */
    public ExtranetRoomType(WebDriver webDriver) {
        super(webDriver, "tiles-def.xnet.modify-room-types");
    }

     /**
     *
     * @param roomType
     */
    private void selectRoomType(String roomType) {
        ExtendedSelect room = new ExtendedSelect(bedTypeName);
        room.selectByValue(roomType);
    }

    private void setMaxRoomsPerBooking(String maxRooms) {
        maxRoomsPerBooking.clear();
        maxRoomsPerBooking.sendKeys(maxRooms);
    }

    private void setMaxGuestsPerRoom(String maxGuests) {
        maxGuestsPerRoom.clear();
        maxGuestsPerRoom.sendKeys(maxGuests);
    }

    private void setMaxAdultsperRoom(String maxAdults) {
        maxAdultsPerRoom.clear();
        maxAdultsPerRoom.sendKeys(maxAdults);
    }

    private void setBaseOccupany(String base) {
        baseOccupancy.clear();
        baseOccupancy.sendKeys(base);
    }

    private void setActiveInactive(String status) {
        ExtendedSelect setActive = new ExtendedSelect(isActive);
        setActive.selectByValue(status);
    }

    public boolean savedChanges(List<String> expectedSetting) {
        boolean validateSavedChanges = true;
        List<String> actualSetting = actualRoomTypeSetting();
        for (int i = 0; i < expectedSetting.size(); i++) {
            if (!(actualSetting.get(i).equals(expectedSetting.get(i)))) {
                validateSavedChanges = false;
                break;
            }
            if (!successConfirmation()) {
                validateSavedChanges = false;
            }
        }
        return validateSavedChanges;
    }

    private List<String> actualRoomTypeSetting() {
        actualRoomTypeSetting.add(0, selectedBedType.getAttribute("value"));
        actualRoomTypeSetting.add(1, maxRoomsPerBooking.getAttribute("value"));
        actualRoomTypeSetting.add(2, maxGuestsPerRoom.getAttribute("value"));
        actualRoomTypeSetting.add(3, maxAdultsPerRoom.getAttribute("value"));
        actualRoomTypeSetting.add(4, baseOccupancy.getAttribute("value"));
        actualRoomTypeSetting.add(5, selectedState.getAttribute("value"));
        return actualRoomTypeSetting;
    }

    public void changeRoomTypeSetting(List<String> roomTypeSetting) {
        selectRoomType(roomTypeSetting.get(0));
        setMaxRoomsPerBooking(roomTypeSetting.get(1));
        setMaxGuestsPerRoom(roomTypeSetting.get(2));
        setMaxAdultsperRoom(roomTypeSetting.get(3));
        setBaseOccupany(roomTypeSetting.get(4));
        setActiveInactive(roomTypeSetting.get(5));
        saveButton.click();
    }

    public void saveChanges() {
        saveButton.click();
    }

    private boolean successConfirmation() {
        boolean confirmation = true;
        if (!confirmationMessage.getText().contains("Your changes have been saved")) {
            confirmation = false;
        }
        return confirmation;
    }

    public void clickHotelOverview() {
        hotelOverview.click();
    }
}
