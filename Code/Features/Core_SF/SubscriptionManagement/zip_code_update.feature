@US
Feature: Zip Code Update
  Owner: Cristian Gonzalez Robles

  Background: 
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario Outline: RTC-4239: Zip Code Update
    Given I navigate to external link /email/zip-update.jsp?r=Y&cid=12d9b324251018b8c4d97c05ef62dd85&amp
    When I update the current zip code with "<zipCode>" and save changes
    Then The new zipcode for the customer should be reflected in CUSTOMER table

    Examples: 
      | zipCode |
      | 94111   |
      #| 94114   |
      #| 94115   |

  @ACCEPTANCE
  Scenario: RTC-4240: Zip Code - Error Case
    Given I navigate to external link /email/zip-update.jsp?r=Y&cid=12d9bfghfg324251018b8c4d97c05ef62dd85&amp
    Then The DBM sign up page should be displayed
