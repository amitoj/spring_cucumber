#@ACCEPTANCE @ANGULAR
#Feature: Angular error handling.

 # Background:
 #   Given default dataset
 #   Given the angular application is accessible
 #   Given the application is accessible
    
 # @US
 # Scenario Outline: Previous day checkin, same day checkout, and checkout day before on angular hotel results page.
  #Given I want to go to the Hotels angular landing page
  # And I'm searching for a hotel in "<destinationLocation>"
  # And I want to travel between 3 days from now and 5 days from now
  #  And I request angular quotes
  #  Given I'm searching for a hotel in "<destinationLocation>"
  #  And I want to travel between between 3 days from now and 5 days from now
   #  And I request quotes
   #   And I'm searching for a hotel in "<destinationLocation>"
   #   And I want to travel between <startDateShift> and <endDateShift>
   #   When I request quotes
   #   Then I will see fare finder <errorType> error

  #Examples:
    #  | destinationLocation        | startDateShift    | endDateShift      | errorType     |
    #  | New York City, NY          | 1 days before now | 2 days from now   | start date    |
   #   | New York City, NY          | 3 days from now   | 3 days from now   | end date      |
   #   | New York City, NY          | 3 days from now   | 1 days before now | end date      |
