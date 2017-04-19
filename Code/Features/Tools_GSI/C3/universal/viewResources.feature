@TOOLS
Feature:  Foot Prints
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Foot Prints. RTC-642
    Given I login into C3 with username "csrcroz1"
    When I click on View Resources
    And I click on workflow link in most used resources
    Then I could create workflow entry
