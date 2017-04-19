@US 
Feature: Fax notification test. This tests assert the processing statuses for a given Itinerary number in notifications_hw table.
   		
 		Scenario: Update Xnet Inventory for this neighbourhood
        Given I'm a valid supplier
        And my hotel id is 6591
        And date range is between 0 days from now and 5 days from now every day
        And room type is STANDARD
        And total inventory available is 5
        And rate plan is XHW
        And xnet currency is USD
        And rate per day is 25
        And extra person fee is 0
        When I update Inventory
        Then I should get No error
       	
       Scenario: Changing properties.
        Given I set the following properties:
		|hotwire.eis.notifications.pgc.emailAddressesForTesting=adeshmukh@hotwire.com|
		|hotwire.eis.notifications.pgc.faxNumbersForTesting=1-415-814-0794|
		|hotwire.eis.notifications.isDeliveryEnabled=true|
		|hotwire.eis.notifications.pgc.targetEndPointURL.I=https://mes**saging.easylink.com/soap/sync|
		|hotwire.eis.notifications.pgc.targetEndPointURL.F=https://mess**aging.easylink.com/soap/sync| 

       Scenario Outline: Asserting DB for all bookings.    
    	Given the application is accessible
 		And I have valid credentials
 		And I authenticate myself
        And I am authenticated
 		And I'm searching for a hotel in "<destinationLocation>"
 		And I want to travel between <startDateShift> days from now and <endDateShift> days from now
 		And I request quotes
 		And I choose <resultstype> hotels tab on results
 		When I select the <neighborhoodName> neighborhood with <starRating> star rating and CRS <CRS Ref>
 		And I book that hotel
 		And I complete the booking with saved user account
 		Then the fax processing status is <status>	
 		 		
 		Examples: opaque quotable fares parameters
    	| destinationLocation | startDateShift | endDateShift | resultstype | neighborhoodName            | starRating | CRS Ref  | crs  | status    |
    	| Modesto, CA         |      0         |      1       | opaque      | Salida - Ripon area hotel   |     1      |  XMN     | X    | uploading |
    	| Modesto, CA         |      1         |      2       | opaque      | Salida - Ripon area hotel   |     1      |  XMN     | X    | uploading |
        | Modesto, CA         |      2         |      3       | opaque      | Salida - Ripon area hotel   |     1      |  XMN     | X    | uploading |
        | Morro Bay, CA       |      0         |      1       | opaque      | Paso Robles area hotel      |     3      |  PMN     | P    | uploading |
        | Morro Bay, CA       |      1         |      2       | opaque      | Paso Robles area hotel      |     3      |  PMN     | P    | not created|
		
	   Scenario: Resetting props..
		Given I reset the following properties:
		|hotwire.eis.notifications.pgc.emailAddressesForTesting|
		|hotwire.eis.notifications.pgc.faxNumbersForTesting|
		|hotwire.eis.notifications.isDeliveryEnabled|
		|hotwire.eis.notifications.pgc.targetEndPointURL.I|
		|hotwire.eis.notifications.pgc.targetEndPointURL.F|