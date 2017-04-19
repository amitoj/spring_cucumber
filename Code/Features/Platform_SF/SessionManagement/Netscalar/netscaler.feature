@CLUSTERALL @NO_PREPROD @NO_PROD_INTERNAL
  Feature: Netscaler
    Partner redirection to Hotwire

  Background:
    Given default dataset
    Given the application is accessible

  @US @CLUSTER1 @CLUSTER3
  Scenario Outline:  RNT-59, 60:Netscaler Redirect - Denmark Site
    Given I have load international <country> hotwire site
    Then I should redirect www.hotwire.com/<country> page

  Examples: quotable fares parameters
    | country |
    | DK      |
    | SE      |
#DK - 59
#SE - 60

  @MOBILE @CLUSTER2 @CLUSTER3
  Scenario Outline: Access vertical information links that lands on desktop full site
    Given I visit Hotwire from <countryCode> on a mobile device
    And I go to a air information page
    Then I should be on desktop domestic air information page

  Examples:Location parameters
    | countryCode |
    | us          |

  @MOBILE @CLUSTER3 @CLUSTER4
  Scenario Outline: Access full site via the mobile site for domestic and international users
    Given I visit Hotwire from <countryCode> on a mobile device
    And I choose to see the full site
    Then I should be on desktop <target url>

  Examples: Application redirect to full site rules parameters
    | countryCode | target url              |
    | us          | domestic home page      |
    | uk          | international home page |