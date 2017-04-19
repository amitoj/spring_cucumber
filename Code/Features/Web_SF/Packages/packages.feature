@US
Feature: Packages (F+H+C, H+C, A+C)
  Owner: TBD

  Background:
    Given default dataset
    Given the application is accessible

  @SMOKE @CLUSTER3 @JANKY
  Scenario Outline: Search for a package with Air+Car+Hotel as a guest user.
    #RTC-6824
    Given I'm a guest user
    And I'm searching for "<package>" vacation from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I will travel between "<startAnytime>" session and "<endAnytime>" session in vacation
    And I want <numberOfHotelRooms> room(s) in vacation
    And I will be traveling with <numberOfAdults> adults in vacation
    And I will be traveling with <numberOfSeniors> seniors in vacation
    And I will be traveling with "<numberOfChildren>" children with "<childAge>" years in vacation
    And I request quotes
    Then I should redirect vacation.com with respective OD pair results

   Examples: quotable fares parameters
     |package | fromLocation  | toLocation    | startDateShift | endDateShift  | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime |
     |AHC     | SFO           | JFK           | 22             | 29            |  1                 | 3              | 1                | 1               | 4        | Morning      | Evening    |

   @CLUSTER1 @CLUSTER3 @JANKY
   Scenario Outline: Search for a package of Air+Car+Hotel with add more destinations as a guest user.
     #RTC-6825
    Given I'm a guest user
    And I'm searching for "<package>" vacation from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want to add more destinations    
    Then I should redirect vacation.com with two destination selected

   Examples: quotable fares parameters
     |package | fromLocation  | toLocation    | startDateShift | endDateShift  | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime |
     |AHC     | SFO           | JFK           | 5              | 10            |  1                 | 3              | 1                | 1               | 4        | Morning      | Evening    |

  @CLUSTER3 @JANKY 
  Scenario Outline: Search for a package with Air+Hotel as a guest user.
    #RTC-6826
    Given I'm a guest user
    And I'm searching for "<package>" vacation from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I will travel between "<startAnytime>" session and "<endAnytime>" session in vacation
    And I want <numberOfHotelRooms> room(s) in vacation
    And I will be traveling with <numberOfAdults> adults in vacation
    And I will be traveling with <numberOfSeniors> seniors in vacation
    And I will be traveling with "<numberOfChildren>" children with "<childAge>" years in vacation
    And I request quotes
    Then I should redirect vacation.com with respective OD pair results

    Examples: quotable fares parameters
    |package | fromLocation  | toLocation    | startDateShift | endDateShift  | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime |
    |AH      |  SFO          | JFK           | 22             | 29            |  1                 | 4              | 1                | 1               | 4        | Afternoon    | Evening    |

  @CLUSTER2 @CLUSTER3 @JANKY 
  Scenario Outline: Search for a package of Air+Hotel with add more destinations as a guest user.
    #RTC-6827
    Given I'm a guest user
    And I'm searching for "<package>" vacation from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want to add more destinations    
    Then I should redirect vacation.com with two destination selected

    Examples: quotable fares parameters
    |package | fromLocation  | toLocation    | startDateShift | endDateShift  | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime |
    |AH      | SFO           | JFK           | 5              | 10            |  1                 | 3              | 1                | 1               | 4        | Morning      | Evening    |

  @CLUSTER3 @JANKY 
  Scenario Outline: Search for a package with Car+Hotel as a guest user.
    #6828
    Given I'm a guest user
    And I'm searching for a HC package to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I will travel between "<startAnytime>" session and "<endAnytime>" session in vacation
    And I want <numberOfHotelRooms> room(s) in vacation
    And I will be traveling with <numberOfAdults> adults in vacation
    And I will be traveling with <numberOfSeniors> seniors in vacation
    And I will be traveling with "<numberOfChildren>" children with "<childAge>" years in vacation
    And I request quotes
    Then I should redirect vacation.com with respective OD pair results

   Examples: quotable fares parameters
   | toLocation    | startDateShift | endDateShift  | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime |
   | JFK           | 5              | 10            |  1                 | 3              | 1                | 1               | 4        | 12:00 PM      | 3:00 PM   |

  @CLUSTER1 @CLUSTER3 @JANKY 
  Scenario Outline: Search for a package of Air+Car+Hotel with partial hotel stay as a guest user.
    #RTC-6829
    Given I'm a guest user
    And I'm searching for "<package>" vacation from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want partial hotel stay between <hotelStartdate> days from now and <hotelEnddate> days from now  
    And I will travel between "<startAnytime>" session and "<endAnytime>" session in vacation
    And I want <numberOfHotelRooms> room(s) in vacation
    And I will be traveling with <numberOfAdults> adults in vacation
    And I will be traveling with <numberOfSeniors> seniors in vacation
    And I will be traveling with "<numberOfChildren>" children with "<childAge>" years in vacation
    And I request quotes
    Then I should redirect vacation.com with respective OD pair results

   Examples: quotable fares parameters
   |package | fromLocation  | toLocation    | startDateShift | endDateShift  | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime | hotelStartdate | hotelEnddate |
   |AHC     | SFO           | JFK           | 5              | 10            |  1                 | 3              | 1                | 1               | 4        | Morning      | Evening    | 6              | 8            |
 
  @CLUSTER2 @CLUSTER3  @JANKY 
  Scenario Outline: Search for a package of Air+Hotel with partial hotel stay as a guest user.
    #RTC-6830
    Given I'm a guest user
    And I'm searching for "<package>" vacation from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I want partial hotel stay between <hotelStartdate> days from now and <hotelEnddate> days from now
    And I will travel between "<startAnytime>" session and "<endAnytime>" session in vacation
    And I want <numberOfHotelRooms> room(s) in vacation
    And I will be traveling with <numberOfAdults> adults in vacation
    And I will be traveling with <numberOfSeniors> seniors in vacation
    And I will be traveling with "<numberOfChildren>" children with "<childAge>" years in vacation
    And I request quotes
    Then I should redirect vacation.com with respective OD pair results

  Examples: quotable fares parameters
    |package | fromLocation  | toLocation    | startDateShift | endDateShift  | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime | hotelStartdate | hotelEnddate |
    |AH      |  SFO          | JFK           | 5              | 10            |  1                 | 4              | 1                | 1               | 4        | Afternoon    | Evening    | 6              | 8            |

#RTC-721   
#origCity and packageDestinationId are the validation parameters
#packageDestinationId is mapped to vacation_destination table. 30 = Atlanta, 32 = Seattle and so on
#One can always change the origCity and packageDestinationId in <URI> and then pass the same in <destination> and <origin>
  @JANKY
  Scenario Outline: RTC-721: Validate external link initiates package search.

    Given I'm a guest user
    And I click on an external link "<URI>"
    Then I see vacation deals for "<origin>" and "<destination>" pair

  Examples: URI and validation parameters                                                                                                                         
   | URI |origin|destination|
   | /package/results.jsp?packageDestinationId=32&packagedPGoodTypeCodes=AH&origCity=LAX&noOfAdults=2&noOfChildren=0&noOfRooms=1&startSearchType=N&inputId=index|LAX|Seattle|

  @ACCEPTANCE 
  Scenario Outline: Verify search for a package with AirCarHotel as a registered user - RTC-3949
    Given my name is <username> and my password is <password>
    And I authenticate myself
    And I'm searching for "<package>" vacation from "<fromLocation>" to "<toLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I will travel between "<startAnytime>" session and "<endAnytime>" session in vacation
    And I want <numberOfHotelRooms> room(s) in vacation
    And I will be traveling with <numberOfAdults> adults in vacation
    And I will be traveling with <numberOfSeniors> seniors in vacation
    And I will be traveling with "<numberOfChildren>" children with "<childAge>" years in vacation
    And I request quotes
    Then I should redirect vacation.com with respective OD pair results
    And I see AHC vacation search in DB for <username>

  Examples: quotable fares parameters
    | username                  | password   | package | fromLocation | toLocation | startDateShift | endDateShift | numberOfHotelRooms | numberOfAdults | numberOfChildren | numberOfSeniors | childAge | startAnytime | endAnytime |
    | qa_regression@hotwire.com | hotwire333 | AHC     | SFO          | JFK        | 22             | 29           | 1                  | 3              | 1                | 1               | 4        | Morning      | Evening    |

  @ACCEPTANCE @ARCHIVE
  Scenario: Verify child age defaults to 11 for external AHC search launch - RTC-3956
    Given I launch external AHC search
    Then  I should redirect vacation.com with default child age is 11

  @JANKY
  Scenario: External link to theme page farefinder - RTC-3957
    Given I launch a search with external link to theme page farefinder
    Then  I should see theme page farefinder with origination "San Francisco" and destination "Boston, Massachusetts"
    Given I start package theme search without specifying search parameters
    Then  I should redirect vacation.com with origination "San Francisco" and destination "Boston, Massachusetts"
