@US
Feature: Hotel Results Comment Card
    Sending comment from the results pages.

    Background:
      Given default dataset
      Given the application is accessible

    @ACCEPTANCE
    Scenario Outline: Verify comment popup.
      Given I'm from "<country>" POS
      And I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I request quotes
      And I choose <resultstype> hotels tab on results
      When I click the comment card link
      Then the comment card will appear

    Examples:
      | destinationLocation        | startDateShift | endDateShift   | resultstype | country        |
      | San Francisco, CA - (SFO)  | 3              |  5             | opaque      | Ireland        |
      | New York, NY               | 3              |  5             | retail      | Ireland        |
      | San Francisco, CA - (SFO)  | 3              |  5             | opaque      | United States  |
      | Chicago, IL                | 3              |  5             | retail      | United States  |

    @JANKY
    Scenario Outline: Sending comment from the results link.
      Given I'm from "<country>" POS
      And I'm searching for a hotel in "<destinationLocation>"
      And I want to travel between <startDateShift> days from now and <endDateShift> days from now
      And I request quotes
      And I choose <resultstype> hotels tab on results
      When I click the Feedback link
      And I submit my comment
      Then the comment card will disappear
      And I will see <page> results page

    Examples:
      | destinationLocation        | startDateShift | endDateShift   | resultstype | page           | country        |
      | San Francisco, CA - (SFO)  | 3              |  5             | opaque      | opaque         | Ireland        |
      | New York, NY               | 3              |  5             | retail      | retail         | Ireland        |
      | San Francisco, CA - (SFO)  | 3              |  5             | opaque      | opaque         | United States  |
      | Chicago, IL                | 3              |  5             | retail      | retail         | United States  |

