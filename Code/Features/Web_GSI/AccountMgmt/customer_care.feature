@MOBILE @ACCEPTANCE
Feature: Contact Customer Care Functionality
  As a domestic Price Hunter, I want to use the local phone number so that I can get the appropriate assistance.

  Background:
    Given the application is accessible

  Scenario Outline: Access customer service as a mobile user
    Given I visit Hotwire from <countryCode> on a mobile device
    Then I should reach <countryCode> customer service phone number
    
  @CLUSTER2 @CLUSTER3
  Examples: contact feature parameters
    | countryCode |
    | us          |
    | uk          |
    | de          |
    
  Examples: contact feature parameters
    | countryCode |   
    | ie          |
    | no          |
    | se          |
    | dk          |
    | au          |
    | nz          |
    | hk          |
    | mx          |
    | sg          |
