@US
Feature: Validate data Consistency on confirmation email
  Owner: Juan Hernandez

  Background: 
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE
  Scenario Outline: Validate information consistency in confirmation email
    Given I have load international <country> hotwire site
    Given my name is qa_regression@hotwire.com and my password is hotwire333
    When I authenticate myself
    Then I am authenticated
    Given I'm searching for a hotel in "San Francisco"
    And I request quotes
    Given I choose a hotel result
    And I book selected hotel
    And I complete a hotel as user a quote
    And I extract details from db and validate against email

    Examples: 
      | country | #     |
      | NZ      | 4851  |
      | UK      | 4848  |
      | AU      | 4850  |
      | IE      | 4849  |
      | NO      | 4852  |
      | SE      | 4854  |
      | DK      | 4853  |
