@US  @JANKY
Feature: Help center - send email to user
  Express or non-Express customer go to Help Center and operate with it.
  Owner: Oleksandr Zelenov

  Scenario: Email is sent
  #Looks like Old help center send US email is turned OFF
    Given default dataset
    And set version test "vt.SFHS1" to value "2"
    Given the application is accessible
    And I am logged as non-express customer
    When I access Help Center
    Then I see Old Help center
    And I click on "Send us an email"
    And I fill in all the fields
    When click Send button
    Then I see successful message

  Scenario: Send us an email error message for non-express customer
    Given default dataset
    And set version test "vt.SFHS1" to value "2"
    And I am logged as non-express customer
    When I access Help Center
    Then I see Old Help center
    And I click on "Send us an email"
    And I see firstName field is pre-filled
    And I see lastName field is pre-filled
    And I see _NAE_email field is pre-filled
    And I see phoneNumber field is pre-filled
    When I click Send button
    Then I receive an error message
    And I see contactSubject field is highlighted
    And I see messageToCustomerCare field is highlighted

  Scenario: Verification of "Send us email" functionality for express customer
    Given default dataset
    And set version test "vt.SFHS1" to value "2"    And I am logged as express customer
    When I access Help Center
    Then I see Old Help center
    And I see "hotwireexpress@hotwire.com" email link

  Scenario: Send us an email error message for guest user
    Given default dataset
    And set version test "vt.SFHS1" to value "2"
    When I access Help Center
    And I click on "Send us an email"
    When I click Send button
    Then I receive an error message
    And I see firstName field is highlighted
    And I see lastName field is highlighted
    And I see _NAE_email field is highlighted
    And I see phoneNumber field is highlighted
    And I see contactSubject field is highlighted
    And I see messageToCustomerCare field is highlighted