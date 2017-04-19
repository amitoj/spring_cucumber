@ANGULAR @ACCEPTANCE
Feature: Hotel Search through booking happy path on Angular site.
   As a price hunter, I'd like to be able to search for hotels that are located at my destination so that
   I can book a hotel I choose.

  Background: 
    Given default dataset
    Given the angular application is accessible
    Given activate car's version tests
    Given the application is accessible

  #@US @ROW
 #  Scenario Outline: Navigate back to details page from billing going through non-angular flow.
   #  Given I'm a guest user
   #  And I'm searching for a hotel in "<destinationLocation>"
   #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
  #   And I request quotes
  #   And I choose <resultstype> hotels tab on results
  #   And I choose a <resultstype> hotel
  #   When I navigate back from the billing page
  #   Then I will see <resultstype> details page
  #   When I book that hotel
  #   And I hit the browser back button
  #   Then I will see <resultstype> details page
  # Examples: opaque quotable fares parameters
  #   | state  | destinationLocation | startDateShift | endDateShift | resultstype |
  #   | is     | Paris, France       | 30             | 35           | opaque      |
  #| is     | Paris, France       | 30             | 35           | retail      |

  @ANGULAR @ACCEPTANCE
  Scenario Outline: Navigate back to angular details page from billing page.
    # Given I want to go to the Hotels angular landing page
    # Given I'm searching for a hotel in "<destinationLocation>"
    # And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    And I book the first hotel in the angular results
    And I will see the angular details page
    When I book the hotel in the angular details page
    Then I will see the hotel billing page from the angular details page
    When I navigate back from the billing page
    Then I will see the angular details page
    When I book the hotel in the angular details page
    And I hit the browser back button
    Then I will see the angular details page

    Examples: 
      | destinationLocation | startDateShift | endDateShift |
      | Chicago, IL         | 3              | 5            |

  @ANGULAR @ACCEPTANCE
  Scenario Outline: Search through to billing going back to angular results page.
    #  Given I want to go to the Hotels angular landing page
    #  Given I'm searching for a hotel in "<destinationLocation>"
    #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    And I save the angular results list
    And I book the first hotel in the angular results
    And I will see the angular details page
    When I book the hotel in the angular details page
    Then I will see the hotel billing page from the angular details page
    When I navigate back from the billing page
    Then I will see the angular details page
    When I go back to results from angular details page
    Then I see angular hotel results
    And the angular results list is the same as before

    Examples: 
      | destinationLocation | startDateShift | endDateShift |
      | Irvine, CA          | 3              | 5            |

  #@US @JANKY
  #Scenario Outline: Mixed session search through to billing going back to details page.
  #   Given I want to go to the Hotels angular landing page
  #   Given I'm searching for a hotel in "<destinationLocation>"
  #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
  #  And I request angular quotes
  #  When I sort angular results by Price (low to high)
  #  Then I see angular hotel results
  #  And I book the first hotel in the angular results
  # And I will see the angular details page
  # When I book the hotel in the angular details page
  #  Then I will see the hotel billing page from the angular details page
  #  When I navigate back from the billing page
  #  Then I will see the angular details page
  #  Given I go to the UHP
  #  And I'm searching for a hotel in "<newDestination>"
  #   And I want to travel between <startDateShift> days from now and <endDateShift> days from now
  #   And I request quotes
  #   And I choose <resultstype> results getting rid of tripwatcher
  #   And I choose a <resultstype> hotel
  #   When I navigate back from the billing page
  #   Then I will see <resultstype> details page
  # Examples:
  #  | destinationLocation   | startDateShift | endDateShift | newDestination                                                 | resultstype  |
  #  | Portland, OR          | 7              | 9            | Las Vegas, NV                                                  | opaque       |
  # | Eugene, OR            | 5              | 7            | Las Vegas, NV                                                  | retail       |
  #    | Phoenix, AZ           | 3              | 5            | New York, NY, United States (JFK-John F. Kennedy Intl.) (JFK)  | opaque       |

  #@US
  #Scenario: Mixed session. Troll through different verticals seeing if session/authentication info is kept.
   # Given I have valid credentials
   # When I authenticate myself
   # Then I am authenticated
   # Given I'm searching for a car in "San Jose, CA - (SJC)"
   # And I want to travel between 5 days from now and 7 days from now
   # And I don't request insurance
   # And I request quotes
   # And I see a non-empty list of search results
   # Then I am authenticated
    # Given I want to go to the Hotels angular landing page
    #  Given I'm searching for a hotel in "Portland, OR"
    #  And I want to travel between 3 days from now and 5 days from now
    #  And I request angular quotes
   # Given I'm searching for a hotel in "Portland, OR"
   # And I want to travel between 3 days from now and 5 days from now
   # And I request quotes
   # Then I see angular hotel results
   # And I book the first hotel in the angular results
    #And I will see the angular details page
   # When I book the hotel in the angular details page
   # Then I will see the hotel billing page from the angular details page
   # And I am authenticated
   # When I navigate back from the billing page
    #And I want to go to the Cars landing page from angular page
    #Then I am authenticated
    #And Car farefinder contain "San Jose, CA - (SJC)" in location

  @ANGULAR @ACCEPTANCE
  Scenario Outline: User logs in from old site and is still logged in through angular flow to billing.
    Given I have valid credentials
    When I authenticate myself
    Then I am authenticated
    #  Given I want to go to the Hotels angular landing page
    #  Given I'm searching for a hotel in "<destinationLocation>"
    #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  And I select 2 room(s)
    #  And I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #And I select 2 room(s)
    And I request angular quotes
    Then I see angular hotel results
    And I book the first hotel in the angular results
    And I will see the angular details page
    When I book the hotel in the angular details page
    Then I will see the hotel billing page from the angular details page
    And I am authenticated
    When I complete the booking with saved user account
    Then I receive immediate confirmation

    Examples: 
      | destinationLocation | startDateShift | endDateShift |
      | Boise, ID           | 3              | 5            |

 @ANGULAR @ACCEPTANCE
  Scenario Outline: Going to details and back to results.
    #  Given I want to go to the Hotels angular landing page
    #  Given I'm searching for a hotel in "<destinationLocation>"
    #  And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  When I request angular quotes
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    When I request angular quotes
    Then I see angular hotel results
    And I save the angular results list
    When I book the first hotel in the angular results
    Then I will see the angular details page
    When I go back to results from angular details page
    Then I see angular hotel results
    And the angular results list is the same as before

    Examples: 
      | destinationLocation | startDateShift | endDateShift |
      | Chicago, IL         | 3              | 5            |

 @ANGULAR @ACCEPTANCE
  Scenario Outline: Going to details and back to results via browser navigation.
   #  Given I want to go to the Hotels angular landing page
   #  Given I'm searching for a hotel in "<destinationLocation>"
    # And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    #  When I request angular quotes
   Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I request angular quotes
    Then I see angular hotel results
    And I save the angular results list
    When I book the first hotel in the angular results
    Then I will see the angular details page
    When I hit the browser back button
    Then I see angular hotel results
    And the angular results list is the same as before

    Examples: 
      | destinationLocation | startDateShift | endDateShift |
      | Chicago, IL         | 3              | 5            |