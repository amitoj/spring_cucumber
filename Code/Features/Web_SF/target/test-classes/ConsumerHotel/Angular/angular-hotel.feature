@ANGULAR
Feature: Hotel Search through booking happy path on Angular site.
   As a price hunter, I'd like to be able to search for hotels that are located at my destination so that
  I can book a hotel I choose.

  Background: 
    Given default dataset
    Given the angular application is accessible
    Given the application is accessible

@ANGULAR @ACCEPTANCE
  Scenario Outline: Angular customer care module
    Given I'm a guest user
    And I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    #Given I want to go to the Hotels angular landing page
    # Given I'm searching for a hotel in "<destinationLocation>"
    #And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #And I request angular quotes
   # Then I see customer service in angular hotel results
  #  When I book the first hotel in the angular results
   # Then I will see customer service in the angular details page

    Examples: 
      | destinationLocation | startDateShift | endDateShift |
      | San Jose, CA        | 3              | 5            |
