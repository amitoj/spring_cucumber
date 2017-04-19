@TOOLS     @salesForce
Feature: Smart contact modules for Sales Force admin site
  Check ability to change availability contact modules from Sales Force admin site
  Owner: Oleksandr Zelenov

  @JANKY
  Scenario Outline: Check smart contact modules changing in Sales Force Admin Site.
    #There is some downtime for LiveChat in the night
    Given I login into Sales Force admin site
    And I click on smart contacts
    And I turn <state> contact modules
    And I go to category link
    When I click any article
    Then I see contact modules correspond to SF Admin settings
    Then I <negation> see Live Chat on article page
    And I go to category link as express
    When I click any article
    Then I see contact modules correspond to SF Admin settings
    Then I <negation> see Live Chat on article page


  Examples:
    | state | negation|
    | ON    |         |
    | OFF   | don't   |


