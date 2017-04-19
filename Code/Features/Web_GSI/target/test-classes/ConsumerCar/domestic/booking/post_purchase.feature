@US
Feature:Car post purchase scenario

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE @JANKY
  Scenario: Verifying the printer friendly version of the car confirmation page. RTC-955
    Given I'm a guest user
    And   I'm searching for a car in "LAX"
    And   I want to travel between 10 days from now and 17 days from now
    And   I don't request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And   I choose a retail car
    And   I fill in all billing information
    When  I complete purchase with agreements
    Then  I receive immediate confirmation
    And   I want see print receipt
    And   I should see print version page

  @ACCEPTANCE  @STBL
  Scenario: Verifying information in the opaque car confirmation email. RTC-975
    Given my name is qa_regression@hotwire.com and my password is hotwire333
    And  I authenticate myself
    And  I'm searching for a car in "San Francisco, CA - (SFO)"
    And  I want to travel in the near future
    And   I want to pick up at 1:30pm and drop off at 3:30pm
    And   I request insurance
    And   I request quotes
    And   I see a non-empty list of search results
    And   I choose a opaque car
    And   I fill in all billing information
    When  I complete purchase with agreements
    Then  I receive immediate confirmation
    Then  I save car confirmation information
    Then  I should see correct information in the car confirmation letter
#  java.lang.ArrayIndexOutOfBoundsException: 0


  @ACCEPTANCE @STBL
  Scenario: Share your itinerary via email. RTC-971, 970, 973
    Given I am a new user
    And   I create an account
    Given  I'm searching for a car in "SFO"
    And   I want to travel in the near future
    And   I want to pick up at 5:30pm and drop off at 2:30pm
    And   I request insurance
    Then  I request quotes
    And   I see a non-empty list of search results
    Then  I choose a opaque car
    And   I fill in all billing information
    Then  I complete purchase with agreements
    And   I receive immediate confirmation
    Then  I save car confirmation information
    #rtc-970 verify how Cancel button works
    When  I verify Cancel button on Share itinerary window
    When  I want to access my trips
    Then  my booked trip summaries are available
    And   I select trip details from the first car trip summary
    #rtc-973 share intinerary with bad and good emails
    When  I share intinerary with bad and good emails
    When  I share intinerary via email qa_regression@hotwire.com
    #rtc-971 verify the information in the car share itinerary letter
    Then  I should see correct information in the car share itinerary letter

  @ACCEPTANCE @STBL
  Scenario: Share your itinerary for the multiplicity emails . RTC-972
    Given I am a new user
    And   I create an account
    Given  I'm searching for a car in "SFO"
    And   I want to travel in the near future
    And   I want to pick up at 5:30pm and drop off at 2:30pm
    And   I request insurance
    Then  I request quotes
    And   I see a non-empty list of search results
    Then  I choose a opaque car
    And   I fill in all billing information
    Then  I complete purchase with agreements
    And   I receive immediate confirmation
    Then  I save car confirmation information
    When  I share intinerary for the multiplicity emails qa_regression@hotwire.com v-ikomarov@hotwire.com
    Then  I should see correct information in the car share itinerary letter for the several recipients

  @ACCEPTANCE @STBL
  Scenario: Verify Print Trip Details Page . RTC-1006
    Given  I'm searching for a car in "SFO"
    And   I want to travel in the near future
    And   I request insurance
    Then  I request quotes
    And   I see a non-empty list of search results
    Then  I choose a opaque car
    And   I fill in all billing information
    Then  I complete purchase with agreements
    And   I receive immediate confirmation
    Then  I verify Print Trip Details page
