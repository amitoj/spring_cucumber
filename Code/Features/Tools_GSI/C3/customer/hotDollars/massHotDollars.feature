@TOOLS @c3Management
Feature: HotDollars operations for multiple customers. View resources script.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @highPriority
  Scenario: Multi-customer HotDollars adding.
    Given I login into C3 with username "csrcroz1"
    Given Hot Dollars of 5 customers
    And I click on View Resources
    And I go to multi-customer Hot Dollars Form
    Then I see multi-customer Hot Dollars Form
    When I enter given emails to multi-customer HotDollars Form
    And I add to each email 5 Hot Dollars with expiration 3 years
    Then I see message that Hot Dollars amount was updated to each customer
    And I verify all customer's HotDollars in database
    And I verify all HotDollars expiration dates in database
  #Case notes creation verification
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I search for given case note email
    Then I see addition of Hot Dollars to multiple customers in case notes

  Scenario: Multi-customer HotDollars subtraction.
    Given I login into C3 with username "csrcroz1"
    Given Hot Dollars of 5 customers
    And I click on View Resources
    And I go to multi-customer Hot Dollars Form
    Then I see multi-customer Hot Dollars Form
    When I enter given emails to multi-customer HotDollars Form
    And I subtract from each email 5 Hot Dollars with expiration 3 years
    Then I see message that Hot Dollars amount was updated to each customer
    And I verify all customer's HotDollars in database
    #Case notes creation verification
    Given C3 application is accessible
    Given I login into C3 with username "csrcroz1"
    And I search for given case note email
    Then I see subtraction of Hot Dollars to multiple customers in case notes

  @STBL @ACCEPTANCE
  Scenario: Multi-customer HotDollars validation report verification. RTC-4120
    Given I login into C3 with username "csrcroz1"
    Given bunch of customer emails "fsjhslcmriown@gmail.com,caps-express@hotwire.com"
    And I click on View Resources
    And I go to multi-customer Hot Dollars Form
    Then I see multi-customer Hot Dollars Form
    When I enter given emails to multi-customer HotDollars Form
    And I add to each email 5 Hot Dollars with expiration 3 years
    Then I see mass HotDollars report
    And I see that email "fsjhslcmriown@gmail.com" is not exist
    And I see that email "caps-express@hotwire.com" is success


