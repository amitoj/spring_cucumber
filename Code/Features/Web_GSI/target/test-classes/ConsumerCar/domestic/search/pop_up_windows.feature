#Doesnt work opn JSLAVE, Other environments are OK.
#BUG53909: 	JSLAVE does not work with vt.SPC01 for cars and vt.SPH01 for hotels.
@BUG  @US
Feature: Cars - Search - Pop-Up Windows
  RTC: 4274, 4275, 4276, 4277, 4278, 4279
  Owner: Vyacheslav Zyryanov

  Background:
    Given default dataset
    Given activate car's version tests


  Scenario Outline: 4276, 4277 Verify that CarRentals.com and Expedia.com pop-under is not triggered when CR checkbox is selected and disambiguous search data is submitted on Hotwire UHP/CLP
    Given set version test "vt.SPC01" to value "2"
    Given the application is accessible
    And I am on car <pageType> page
    And I select next partners "CarRentals.com, Expedia" in fare finder
    And I'm searching for a car in "<location>"
    And I want to travel between <startDateShift> and <endDateShift>
    And I request quotes
    Then I receive immediate "<error>" error message
    Then I should not see "CarRentals.com" page
    Then I should not see "Expedia" page

  Examples:
    | #    | location | pageType  | startDateShift  | endDateShift    | error |
    | 4276 |          | index     | 4 days from now | 7 days from now | Pick up location is blank. |
    | 4277 | DEN      | landing   | 7 days from now | 4 days from now | Drop off date is not valid. The drop off date must be after the pick up date. |

  Scenario Outline: 4274, 4275
  Verify that CarRentals.com, Expedia.com  pop-unders are triggered when
  CR,EX checkboxs are selected and search is successfully submitted on Hotwire UHP

    Given set version test "vt.SPC01" to value "<value>"
    Given the application is accessible
    And I am on car <pageType> page
    And "Compare with CarRentals.com" checkbox is <CR-C>
    And "Compare with Expedia" checkbox is <EX-C>

  Examples:
    | #    | pageType  | value | CR-C       | EX-C       |
    | 4274 | index     | 0     | hidden     | hidden     |
    |      | index     | 1     | hidden     | hidden     |
    |      | index     | 2     | unchecked  | unchecked  |
    #|      | index     | 3     | checked    | checked    |  BUG48889: 	Version testing vt.SPC01 doesn't work in FF 15+
    #|      | index     | 4     | unchecked  | checked    |
    #|      | index     | 5     | checked    | unchecked  |
    #|      | index     | 6     | hidden     | checked    |
    |      | index     | 7     | hidden     | unchecked  |
    #|      | index     | 8     | checked    | hidden     |
    |      | index     | 9     | unchecked  | hidden     |
    | 4275 | landing   | 0     | hidden     | hidden     |
    |      | landing   | 1     | hidden     | hidden     |
    |      | landing   | 2     | unchecked  | unchecked  |
    #|      | landing   | 3     | checked    | checked    |
    #|      | landing   | 4     | unchecked  | checked    |
    #|      | landing   | 5     | checked    | unchecked  |
    #|      | landing   | 6     | hidden     | checked    |
    |      | landing   | 7     | hidden     | unchecked  |
    #|      | landing   | 8     | checked    | hidden     |
    |      | landing   | 9     | unchecked  | hidden     |

  Scenario: 4278
  Verify that partner's popups were opening when checkboxes of
  CarRentals.com and Expedia were selected in fare finder on home page

    Given set version test "vt.SPC01" to value "2"
    Given the application is accessible
    And I am on car index page
    And I select next partners "CarRentals.com, Expedia" in fare finder
    And I'm searching for a car in "LAX"
    And I want to travel in the near future
    And I request quotes
    Then I check search options when I compare with other car's retailers
      | CR              | Los Angeles International Airport - California (LAX)  |
      | EXPEDIA         | Los Angeles, CA (LAX-Los Angeles Intl.)               |

  Scenario: 4279
  Verify that partner's popups were opening when checkboxes of
  CarRentals.com and Expedia were selected in fare finder on landing page

    Given set version test "vt.SPC01" to value "2"
    Given the application is accessible
    And I am on car landing page
    And I select next partners "CarRentals.com, Expedia" in fare finder
    And I'm searching for a car in "MEM"
    And I want to travel in the near future
    And I request quotes
    Then I check search options when I compare with other car's retailers
      | CR              | Memphis International Airport - Tennessee (MEM)  |
      | EXPEDIA         | Memphis, TN (MEM-Memphis Intl.)                  |