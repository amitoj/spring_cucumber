@ANGULAR @US
Feature: Angular calendar issues

  Background: 
    Given default dataset
    Given the angular application is accessible
    Given the application is accessible
      
@ANGULAR @ACCEPTANCE @US
  Scenario Outline: Angular update calendar check-in and check-out dates if changed from current dates
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    When I change the check-in date to a value after check-out date
    Then check-in date should be updated immediately
    Then check-out date should be updated immediately   
    
  Examples: 
      | destinationLocation      | startDateShift | endDateShift |
      | San Francisco, CA        | 3              | 29           |
      
      
@ANGULAR @ACCEPTANCE @US
  Scenario Outline: Angular update calendar check-in date if entering date before current check-in date
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    When I change the check-in date to a date between today and before current check-in date
    Then check-in date should be updated immediately
    Then check-out date should be updated immediately   
    
  Examples: 
      | destinationLocation      | startDateShift | endDateShift |
      | San Francisco, CA        | 3              | 29           |
      
@ANGULAR @ACCEPTANCE @US
  Scenario Outline: Angular calendar range overlay renders properly
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    When I change the check-out date
    Then check-out date should be updated immediately   
    
  Examples: 
      | destinationLocation      | startDateShift | endDateShift |
      | San Francisco, CA        | 3              | 29           |
      
      
@ANGULAR @ACCEPTANCE @US
  Scenario Outline: Angular calendar dropdown verification for IE9
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    When I change the check-out date to a date more than 30 days
    Then I should receive an fare finder <errorMessage> error
    When I change the check-out date to a value less than 30 days 
    Then check-out date should be updated immediately   
    
  Examples: 
      | destinationLocation      | startDateShift | endDateShift | errorMessage            |
      | San Francisco, CA        | 3              | 29           | Trip exceeds 30 days |
     