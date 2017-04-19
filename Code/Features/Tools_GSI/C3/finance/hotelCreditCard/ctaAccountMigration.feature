@TOOLS
Feature:  FINANCE / Hotel Credit Card Admin / CTA Account Information/
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Valid CTA Account Search. RTC-1454
    Given I have valid OrderID
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel Credit card admin page
    When I try to search hotel information by CTA account
    Then I check hotel CTA account information

