@TOOLS @c3Customer
Feature: Verification of "Sign In\Register" module on C3 page
  Owner: Komarov Igor

  Background:
    Given C3 application is accessible

  @ACCEPTANCE @STBL
  Scenario: Sign In module shouldn't be present on C3 page. RTC-1633
    Given I login into C3 with username "csrcroz1"
    And  I search for customer with "qa_regression@hotwire.com" email
    Then I click Site search for customer
    And  I see hotwire home page in C3 frame
    And  I don't see SignIn module
    Then I search for hotel
    And  I don't see SignIn module
