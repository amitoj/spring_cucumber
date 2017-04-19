@TOOLS
Feature: Add a credit card to an existing account.
  Delete credit card.
  Owner: Boris Shukaylo

  Background:
    Given C3 application is accessible

  @ACCEPTANCE @highPriority
  Scenario: Happy path: Add / Remove customer credit card RTC-801,RTC-800,RTC-1038
    #add a card
    Given I login into C3 with username "csrcroz1"
    Given customer without saved payment methods
    And I want to search customer by email
    And I go to the customer account info
    And I open Add a new credit card form
    When I add a new credit card named "New test card"
    Then card was added successfully
    #delete this card
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I want to search customer by email
    And I go to the customer account info
    And I open Edit Payment Methods form
    When I delete a credit card named "New test card"
    Then card was deleted successfully

  @ACCEPTANCE @STBL
  Scenario: Case notes verification about customer credit card add/update/remove
    Given I login into C3 with username "csrcroz1"
    Given customer without saved payment methods
    And I want to search customer by email
    And I go to the customer account info
    And I open Edit Payment Methods form
    When I add a new credit card named "New test card"
    Then card was added successfully
    And I see "Added Payment Method Credit Card for" in the notes
    When I switch to c3 frame
    And I open Edit Payment Methods form
    And I set billing address to "14th avenue 45" and save
    And I see "Modified Payment Method Credit Card for" in the notes
    When I switch to c3 frame
    And I delete a credit card named "New test card"
    And I see "Deleted Payment Method Credit Card for" in the notes

  @ACCEPTANCE
  Scenario: Credit card editing, fields validation. RTC-812, 767, 766
    Given customer without saved payment methods
    Then I login into C3 with username "csrcroz1"
    And  I want to search customer by email
    And  I go to the customer account info
    And I open Edit Payment Methods form
    And I receive "There are no Payment Methods associated with this customer." error message
    When I add a new credit card named "New test card"
    Then card was added successfully
    And I open Edit Payment Methods form
    When I set credit card nick name to "" and save
    Then I receive "To save this credit card to your account, please give this card a nickname." error message
    When I set credit card nick name to "New 1234" and save
    Then I receive "Payment method has been updated" successful message
    When I set billing address to "" and save
    Then I receive "Enter a billing address." error message
    When I set billing address to "333 market street" and save
    Then I receive "Payment method has been updated" successful message
