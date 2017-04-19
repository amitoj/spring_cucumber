@MOBILE
Feature: Netscalar routing of SEO optimized URLs (part 1 of 2)
   As a user of the mobile site, I want to be able to land on the mobile optimized pages when I access
   different informational pages

    Background:
        Given the application is accessible
        
	Scenario Outline: Access sites as a domestic and international users that are remapped to mobile home page
        Given I visit Hotwire from <countryCode> on a mobile device
        Then I should be on mobile optimized home page

    Examples:Location parameters
        | countryCode  |
        | us           |
        | uk           |
        | ie           |
        | se           |
        | no           |
        | dk           |
        | au           |
        | nz           |
        | hk           |
        | de           |
        | sg           |

        
	Scenario Outline: Access travel protection information pages that lands on mobile help center pages
        Given I visit Hotwire from <countryCode> on a mobile device
        And I go to trip protection <category> information page
        Then I should be on mobile optimized help center page
        
    Examples:Trip protection page parameters
        | countryCode   | category  |
        | us			| claim  	|
        | us 			| insurance |
        | us            | purchase 	| 
        | uk 			| claim  	|
        | uk 			| insurance |
        | uk 			| purchase 	|	
        | mx 			| purchase 	|	
	
		
	Scenario Outline: Access information pages that lands on mobile optimized car search page
        Given I visit Hotwire from <countryCode> on a mobile device
        And I go to car <category> information page 
        Then I should be on <landingURL>
        
    Examples:Location parameters
        | countryCode  	| category	| landingURL 						         |
        | us 			| suppliers | mobile optimized car search page 		  	 |
		| uk 			| suppliers | Hotwire branded 3rd party mobile home page |
		| ie 			| suppliers | Hotwire branded 3rd party mobile home page |
		| se 			| suppliers | Hotwire branded 3rd party mobile home page |
		| no 			| suppliers | Hotwire branded 3rd party mobile home page |
		| dk 			| suppliers | Hotwire branded 3rd party mobile home page |
		| au 			| suppliers | Hotwire branded 3rd party mobile home page |
		| nz 			| suppliers | Hotwire branded 3rd party mobile home page |
		| us 			| types 	| mobile optimized car search page 			 |
		| uk 			| types 	| Hotwire branded 3rd party mobile home page |
		| ie 			| types 	| Hotwire branded 3rd party mobile home page |
		| se 			| types 	| Hotwire branded 3rd party mobile home page |
		| no 			| types 	| Hotwire branded 3rd party mobile home page |
		| dk 			| types 	| Hotwire branded 3rd party mobile home page |
		| au 			| types 	| Hotwire branded 3rd party mobile home page |
		| nz 			| types 	| Hotwire branded 3rd party mobile home page |
		| hk 			| types 	| Hotwire branded 3rd party mobile home page |
		| de 			| types 	| Hotwire branded 3rd party mobile home page |
		| mx 			| types     | Hotwire branded 3rd party mobile home page |
		| sg 			| types     | Hotwire branded 3rd party mobile home page |

		
	
	Scenario: Access destination links that lands on mobile optimized pages
        Given I visit Hotwire from us on a mobile device
        And I visit cheap page  
        Then I should be on mobile optimized home page  
    
	
	Scenario Outline: Access destination links that lands on mobile optimized pages
        Given I visit Hotwire from <countryCode> on a mobile device
        And I visit <seoLink> <destinationName> page  
        Then I should be on <landingPage>
        
    Examples:Location parameters 
        | countryCode       | seoLink            | landingPage                           |   destinationName 	|              
        | us                | destination        | mobile optimized home page            |   las-vegas       	|
        | us                | destination        | mobile optimized home page            |   new-york        	|
        | us                | destination        | mobile optimized home page            |   sun    	     	|
        | us                | vacation-travel    | mobile optimized hotel search page    |   atlanta-georgia 	|
        | us                | vacation-travel    | mobile optimized hotel search page    |   austin-texas       |
        | us                | vacation-travel    | mobile optimized hotel search page    |   baltimore-maryland |
        | us                | hotel-rooms        | mobile optimized hotel search page    |   amsterdam      	|
        | uk                | hotel-rooms        | mobile optimized hotel search page    |   amsterdam          |
        | ie                | hotel-rooms        | mobile optimized hotel search page    |   amsterdam       	|
        | us                | hotel-rooms        | mobile optimized hotel search page    |   rome       		|
        | uk                | hotel-rooms        | mobile optimized hotel search page    |   rome       		|
        | ie                | hotel-rooms        | mobile optimized hotel search page    |   rome       		|
        | us                | hotel-rooms        | mobile optimized hotel search page    |   barcelona       	|
        | us                | hotel-rooms        | mobile optimized hotel search page    |   london			 	|

		
