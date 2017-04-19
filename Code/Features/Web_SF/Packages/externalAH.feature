@US
Feature: External AH search launch

  Background: 
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE @BUG
  Scenario: External AH search launch - default dates used RTC-3955
    Given I want to access url "http://www.qa.hotwire.com/package/results.jsp?packageDestinationId=45&packagedPGoodTypeCodes=AH&origCity=OAK&noOfAdults=2"
    When  The page is loaded
    And   I click on Vail option in the disambiguation page
    Then  I should be redirected to results page
    And   departure date must match sysdate plus 30 days
    And   return date must match sysdate plus 34 days
