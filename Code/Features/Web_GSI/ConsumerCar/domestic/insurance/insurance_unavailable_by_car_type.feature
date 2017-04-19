@US @simulator @SingleThreaded @BUG
Feature: For some of car types insurance should be unavailable to use in any pickup location
  Owner:Vyacheslav Zyryanov


#  As a pragmatic customer
#  I want to see if insurance for car is unavailable
#  So that I don't buy insurance for car if it can't be provided

#  car type	    Regions where car insurance is unavailable
#  SPAR	        US, Canada
#  FDAR, FRAR   US, Canada, Caribbean, Europe, Europe-Paris, Mexico, Africa, Asia, Australia, South Africa
#  FRMR	        Caribbean, Europe, Europe-Paris, Mexico, Africa, Asia, Australia, South Africa


  Scenario Outline: RTC-6682 For some of car types insurance should be unavailable to use in any pickup location
   #Mocking return no SPAR, FDAR, FRAR, FRMR results. Mocking needs to be updated to make this test green
    Given default dataset
    Given activate car's version tests
    Given the application is accessible
    And I'm searching for a car in "<location>"
    And I want to travel in the near future
    And I request quotes
    And I select a car by cd code <cdCode>
    Then Rental car damage protection was unavailable

  Examples:
    | location | cdCode |
    | SFO      | ICAR  |
    | YEG      | SPAR   |
