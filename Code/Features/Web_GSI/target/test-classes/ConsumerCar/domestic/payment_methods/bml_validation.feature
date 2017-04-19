@US @ACCEPTANCE
Feature: Bill Me Later payment method validations.
  Owner: Oleksandr Zelenov
#
#  BML is disabled on frontend (but NOT removed from the code) in 2015.02 branch.
#  TODO: Commenting this cases until they will be removed from the code.
#
#  Background:
#    Given default dataset
#    Given activate car's version tests
#    Given the application is accessible
#
#  Scenario: BML empty fields validation. RTC-6872
#    Given I'm searching for a car in "SFO"
#    And I want to travel in the near future
#    And I don't request insurance
#    And I request quotes
#    Then I see a non-empty list of search results
#    And I choose a opaque car
#    And I select BML payment option
#    And I complete purchase with agreements
#    Then I receive immediate "Enter missing information in the blank fields indicated below." error message
#
#  Scenario Outline: BML payment section validation. RTC-6872
#    Given I'm searching for a car in "SFO"
#    And I want to travel in the near future
#    And I don't request insurance
#    And I request quotes
#    Then I see a non-empty list of search results
#    And I choose a opaque car
#    And I fill in all BML billing information
#    And I write to <fieldType> BML field <text>
#    And I complete purchase with agreements
#    Then I receive immediate "<message>" error message
#
#  Examples:
#    | fieldType   | text              | message                                                                                              |
#    | firstName   | Jo?hn             | The first and last name you entered can only contain letters, dashes, and periods.                   |
#    | lastName    | Smith0            | The first and last name you entered can only contain letters, dashes, and periods.                   |
#    | lastName    | A                 | Enter a first and last name. Names need to be at least 2 characters.                                 |
#    | city        | assaut13          | Enter a valid city.                                                                                  |
#    | zip         | 1000              | Enter a valid zip code.                                                                              |
#    | zip         | 123456            | Enter a valid zip code.                                                                              |
#    | phoneRegion | 12                | We only accept residential phone numbers with US area codes. We don't accept toll-free numbers.      |
#    | phoneRegion | 1                 | We only accept residential phone numbers with US area codes. We don't accept toll-free numbers.      |
#    | phoneNumber | 92119001          | We only accept residential phone numbers with US area codes. We don't accept toll-free numbers.      |
#    | phoneNumber | 921190            | We only accept residential phone numbers with US area codes. We don't accept toll-free numbers.      |
#
#    @STBL
#  Scenario Outline: BML booking after validation error. RTC-6872
#    Given I'm searching for a car in "SFO"
#    And I want to travel in the near future
#    And I don't request insurance
#    And I request quotes
#    Then I see a non-empty list of search results
#    And I choose a opaque car
#    And I fill in all BML billing information
#    And I write to <fieldType> BML field <text>
#    And I complete purchase with agreements
#    Then I receive immediate "<message>" error message
#    When I fill in all BML billing information
#    And I complete purchase with agreements
#    And I confirm booking on BML website
#    Then I receive immediate confirmation
#
#  Examples:
#    | fieldType | text  | message                                                                            |
#    | firstName | John4 | The first and last name you entered can only contain letters, dashes, and periods. |
#
#    @STBL
#  Scenario: Checking clicking on cancel on BML website. RTC-6872
#    Given I'm searching for a car in "SFO"
#    And I want to travel in the near future
#    And I don't request insurance
#    And I request quotes
#    Then I see a non-empty list of search results
#    And I choose a opaque car
#    And I fill in all BML billing information
#    And I complete purchase with agreements
#    And I cancel booking on BML website
#    Then I see car billing page
#    And I receive immediate "Please select another payment method" error message
#
#    @STBL
#  Scenario: BML booking with invalid data. RTC-6872
#    Given I'm searching for a car in "SFO"
#    And I want to travel in the near future
#    And I don't request insurance
#    And I request quotes
#    Then I see a non-empty list of search results
#    And I choose a opaque car
#    And I fill in all BML billing information
#    And I write to firstName BML field Jonny
#    And I complete purchase with agreements
#    And I confirm booking on BML website
#    Then I see car billing page
#    And I receive immediate "We were unable to authorize this purchase using Bill Me Later. Please try using a credit card to complete this purchase." error message
