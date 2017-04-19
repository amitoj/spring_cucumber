@US
Feature: Payment authorization
  Testsuite in Testlink  Tools_Marketing - Payment - Payment_aut
  Owner: Boris Shukaylo

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

 @BUG
 Scenario: BUG 52857 Securecode not enrolled, auth attempted RTC-51
   Given I have valid credentials
   Given I have a valid LiveProcessor MasterCard credit card
   And I'm searching for a car in "YOW"
   And I want to travel in the near future
   And I request quotes
   Then I see a non-empty list of search results
   And I choose a opaque car
   When I fill in all billing information
   When I complete purchase with agreements
   Then I receive immediate confirmation
   And I verify that STATUS_CODE's count in DB is equal to 3

   @STBL @ACCEPTANCE
 Scenario: Intl diners card purchase RTC-1686
   Given I have valid credentials
   Given I have a valid Intl Diners Card credit card
   And I'm searching for a car in "AIRPORT"
   And I want to travel in the near future
   And I request quotes
   Then I see a non-empty list of search results
   And I choose a opaque car
   When I fill in all billing information
   When I complete purchase with agreements
   Then I receive immediate confirmation

  @STBL @ACCEPTANCE
  Scenario: Billing address is equals to intl depart check in date less than 3 days from purchase RTC-1337
    Given I have valid credentials
    And I'm from "Australia" POS
    Given I have a valid Australian Visa credit card
    Given I'm searching for a hotel in "SFO"
    And I want to travel between 1 days from now and 2 days from now
    And I request quotes
    And I choose opaque hotels tab on results
    And I choose a hotel result
    And I book that hotel
    And  I purchase as guest a quote
    Then I receive immediate confirmation

  @STBL @ACCEPTANCE
  Scenario: Purchase with trip protection RTC-982
    Given I'm a guest user
    And I'm searching for a hotel in "SFO"
    And I want to travel in the near future
    And I request quotes
    And I choose opaque hotels tab on results
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation
    Then I should be able to see one dollar auth in db
