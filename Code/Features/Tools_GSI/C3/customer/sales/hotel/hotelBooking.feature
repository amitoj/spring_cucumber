@TOOLS     @c3Hotel
Feature: Hotel purchase for registered user in C3.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Hotel purchase for registered user in C3. RTC-1632
    Given I login into C3 with username "csrcroz1"
    And I search for customer with "caps-express@hotwire.com" email
    And I click Site search for customer
    And I search for hotel
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation

  @ACCEPTANCE @STBL
  Scenario: CSR Books Hotel with VPAS Credit Card. RTC-485
    Given I login into C3 with username "csrcroz1"
    And I have valid VPAS 4..0002 credit card
    And I search for customer with "caps-express@hotwire.com" email
    And I click Site search for customer
    And I search for hotel
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation