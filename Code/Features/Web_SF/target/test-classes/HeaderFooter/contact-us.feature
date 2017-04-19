@US @ACCEPTANCE
Feature: Global Footer links on home page

  Background:
  Given default dataset
  Given the application is accessible

    @ACCEPTANCE
  Scenario: Contact us link is accessible to all customers. RTC-659
    Given I'm a guest user
    When I access Contact us
    Then I see send email text
    Then I access SiteMap
    Then I verify hotwire products
    Then I verify my account links