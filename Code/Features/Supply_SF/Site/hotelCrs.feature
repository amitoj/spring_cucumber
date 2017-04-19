        @US
        Feature: Search and purchase specific CRS(Pegasus, Xnet, Tourico, IAN PRO & Retail) Hotel. It uses winning logic by injecting rates to Cache.
        This feature file should search and purchase a specific CRS Hotel. By verifying the winning logic.
    	List of CRS are Pegasus, Xnet, IAN, Tourico
        It also verifies rates displayed in the Summary of pages.
        
       Scenario: Update Xnet Inventory for this neighbourhood
        Given I'm a valid supplier
        And my hotel id is 67698
        And date range is between 1 days from now and 10 days from now every day
        And room type is STANDARD
        And total inventory available is 1
        And rate plan is XHW
        And xnet currency is USD
        And rate per day is 35
        And extra person fee is 0
        When I update Inventory
        Then I should get No error

        Scenario Outline: Search and purchase a CRS(Pegasus) hotel as a guest user.
        Given I inject rates for CRS type <crs> CRS hotel IDs <crsHotelIds> and rate <rate>
        Given the application is accessible
 		Given I have valid credentials
 		And I authenticate myself
        And I am authenticated
 		And I'm searching for a hotel in "<destinationLocation>"
 		And I want to travel between <startDateShift> days from now and <endDateShift> days from now
 		And I want <numberOfHotelRooms> room(s)
 		And I request quotes
 		And I choose <resultstype> hotels tab on results
 		When I select the <neighborhoodName> neighborhood with <starRating> star rating and CRS <CRS Ref>
 		And I book that hotel
 		And I complete the booking with saved user account
 		Then Summary of charges on new billing page is valid
 	 		
 		Examples: opaque quotable fares parameters
    	| destinationLocation | numberOfHotelRooms |startDateShift | endDateShift | resultstype | neighborhoodName         | starRating | CRS Ref | crs   | crsHotelIds 	| rate 	|
    	| Morro Bay, CA       |         1          |    1          |      2       | opaque      | Morro Bay area hotel     | 2.5        |PMN      | P     | BW;05232        | 25    |
 		
        
        Scenario Outline: Rollback injected rates.
        This will make sure injected rates are always revoked even if above scenario fails.

        Given I rollback injected rates for CRS type <crs> CRS hotel IDs <crsHotelIds>
        Examples: quotable fares parameters & injected rates
                  | crs     | crsHotelIds |
                  | P       | BW;05232    |

	    
        Scenario Outline: Search and purchase a CRS(Xnet) hotel as a guest user.
    	Given the application is accessible
 		Given I have valid credentials
 		And I authenticate myself
        And I am authenticated
 		And I'm searching for a hotel in "<destinationLocation>"
 		And I want to travel between <startDateShift> days from now and <endDateShift> days from now
 		And I want <numberOfHotelRooms> room(s)
 		And I request quotes
 		And I choose <resultstype> hotels tab on results
 		When I select the <neighborhoodName> neighborhood with <starRating> star rating and CRS <CRS Ref>
 		And I book that hotel
 		And I complete the booking with saved user account
 		Then Summary of charges on new billing page is valid
 		 		
 		Examples: opaque quotable fares parameters
    	| destinationLocation | numberOfHotelRooms |startDateShift | endDateShift | resultstype | neighborhoodName    | starRating | CRS Ref | crs   | 
    	| Ojai, CA            |         1          |     3         |      4       | opaque      | Ojai area hotel     |     4      |   XMN   | X     | 
                  


        
        Scenario Outline: Search and purchase a CRS(IAN Retail) hotel as a guest user.
        Given the application is accessible
 		Given I have valid credentials
 		And I authenticate myself
        And I am authenticated
 		And I'm searching for a hotel in "<destinationLocation>"
 		And I want to travel between <startDateShift> days from now and <endDateShift> days from now
 		And I want <numberOfHotelRooms> room(s)
 		And I request quotes
 		And I choose <resultstype> hotels tab on results
 		When I choose a hotel result
 		And I book that hotel
 		And I complete the booking with saved user account
 		Then Summary of charges on new billing page is valid
 		       
       Examples: opaque quotable fares parameters
    	| destinationLocation | numberOfHotelRooms |startDateShift | endDateShift | resultstype | 
    	| San Diego, CA       |         1          |     1         |     2        | retail      | 
                  
        Scenario Outline: Search and purchase a CRS(Tourico) hotel as a guest user.
    	Given I inject rates for CRS type <crs> CRS hotel IDs <crsHotelIds> and rate <rate>
        Given the application is accessible
 		Given I have valid credentials
 		And I authenticate myself
        And I am authenticated
 		And I'm searching for a hotel in "<destinationLocation>"
 		And I want to travel between <startDateShift> days from now and <endDateShift> days from now
 		And I want <numberOfHotelRooms> room(s)
 		And I request quotes
 		And I choose <resultstype> hotels tab on results
 		When I select the <neighborhoodName> neighborhood with <starRating> star rating and CRS <CRS Ref>
 		And I book that hotel
 		And I complete the booking with saved user account
 		Then Summary of charges on new billing page is valid
 		
 		Examples: opaque quotable fares parameters
    	| destinationLocation | numberOfHotelRooms |startDateShift | endDateShift | resultstype | neighborhoodName      | starRating | CRS Ref | crs   | crsHotelIds | rate |
    	| Curacao, Curacao    |         1          |     3         |     4        | opaque      | Curacao area hotel    | 4          |TMN      | T     | 17012       | 25   |
 		
       @ACCEPTANCE
        Scenario Outline: Rollback injected rates.
        This will make sure injected rates are always revoked even if above scenario fails.

        Given I rollback injected rates for CRS type <crs> CRS hotel IDs <crsHotelIds>
        Examples: quotable fares parameters & injected rates
                  | crs | crsHotelIds |
                  | T   | 17012       |

        
       Scenario Outline: Search and purchase a CRS(IAN PRO using version test) hotel as a guest user.
        Given I inject rates for CRS type <crs> CRS hotel IDs <crsHotelIds> and rate <rate>
        Given the application is accessible
 		Given I have valid credentials
 		And I authenticate myself
        And I am authenticated
 		And I'm searching for a hotel in "<destinationLocation>"
 		And I want to travel between <startDateShift> days from now and <endDateShift> days from now
 		And I want <numberOfHotelRooms> room(s)
 		And I request quotes
 		And I choose <resultstype> hotels tab on results
 		When I select the <neighborhoodName> neighborhood with <starRating> star rating and CRS <CRS Ref>
 		And I book that hotel
 		And I complete the booking with saved user account
 		Then Summary of charges on new billing page is valid
 		
 		Examples: opaque quotable fares parameters
    	| destinationLocation   | numberOfHotelRooms |startDateShift | endDateShift | resultstype | neighborhoodName | starRating | CRS Ref | crs   | crsHotelIds | rate |
    	| Hilo, Big Island, HI  |         1          |    20         |  21          | opaque      | Hilo area hotel  | 2.5        |IMP      | I     | pkg_218386  | 35   |
 		
        
        Scenario Outline: Rollback injected rates.
        This will make sure injected rates are always revoked even if above scenario fails.

        Given I rollback injected rates for CRS type <crs> CRS hotel IDs <crsHotelIds>
        Examples: quotable fares parameters & injected rates
                  | crs   | crsHotelIds  |
                  | I     | pkg_218386   |
