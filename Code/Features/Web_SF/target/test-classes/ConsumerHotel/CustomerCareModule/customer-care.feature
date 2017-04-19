@ACCEPTANCE
Feature: Customer Care Module
    Verify customer care module in various Hotwire pages.

    Background:
        Given default dataset
        Given the application is accessible

    @US @ROW
    Scenario Outline: Verify customer care modules displayed with correct information.
        Given I'm searching for a hotel in "<destinationLocation>"
        And I want to travel between <startDateShift> days from now and <endDateShift> days from now
        And I request quotes
        When I choose <page> hotels tab on results
        And I will see <page> results page
        Then All customer care modules will be displayed in the <page> results

    Examples:
        | destinationLocation | startDateShift  | endDateShift  | page           |
        | San Francisco, CA   | 3               | 5             | opaque         |
        | Chicago, Il         | 3               | 5             | retail         |

    @US @ROW
    Scenario Outline: Verify customer care modules displayed with correct information.
        Given I'm searching for a hotel in "<destinationLocation>"
        And I want to travel between <startDateShift> days from now and <endDateShift> days from now
        And I request quotes
        When I choose <page> hotels tab on results
        And I will see <page> results page
        When I choose a hotel result
        Then All customer care modules will be displayed in the <page> details page

    Examples:
        | destinationLocation | startDateShift  | endDateShift  | page           |
        | San Francisco, CA   | 3               | 5             | opaque         |
        | Chicago, Il         | 3               | 5             | retail         |

    @US @ARCHIVE
    Scenario Outline: Verify live chat from customer care module in results
        Given I'm searching for a hotel in "<destinationLocation>"
        And I want to travel between <startDateShift> days from now and <endDateShift> days from now
        And I request quotes
        And I choose <page> hotels tab on results
        And I will see <page> results page
        When I choose live chat from the results customer care module
        Then I will see the live chat prechat form
        When I cancel the live chat
        Then I will not see the live chat prechat form

    Examples:
        | destinationLocation | startDateShift  | endDateShift  | page           |
        | San Francisco, CA   | 3               | 5             | opaque         |
        | New York City, NY   | 3               | 5             | retail         |

    @US @ARCHIVE
    Scenario Outline: Verify live chat from customer care module in details page.
        Given I'm searching for a hotel in "<destinationLocation>"
        And I want to travel between <startDateShift> days from now and <endDateShift> days from now
        And I request quotes
        And I choose <page> hotels tab on results
        And I will see <page> results page
        When I choose a hotel result
        And I choose live chat from the details customer care module
        Then I will see the live chat prechat form
        When I cancel the live chat
        Then I will not see the live chat prechat form

    Examples:
        | destinationLocation | startDateShift  | endDateShift  | page           |
        | San Francisco, CA   | 3               | 5             | opaque         |
        | New York City, NY   | 3               | 5             | retail         |
