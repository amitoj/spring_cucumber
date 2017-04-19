@MOBILE @JANKY
Feature: Netscalar routing of SEO optimized URLs (part 2 of 2)
   As a user of the mobile site, I want to be able to land on the mobile optimized pages when I access
   different informational pages

    Background:
        Given the application is accessible
        	
	Scenario Outline: Access information pages that lands on mobile optimized home page
        Given I visit Hotwire from <countryCode> on a mobile device
        And I go to <category> information page 
        Then I should be on mobile optimized home page
        
    Examples:Location parameters
        | countryCode   | category 			   |
        | us 			| new to Hotwire 	   |
		| uk 			| new to Hotwire 	   |
		| ie 			| new to Hotwire 	   |
		| se 			| new to Hotwire 	   |
		| no 			| new to Hotwire 	   |
		| dk 			| new to Hotwire 	   |
		| au 			| new to Hotwire 	   |
		| nz 			| new to Hotwire 	   |
		| hk 			| new to Hotwire 	   |
		| us 			| last minute vacation |
		| uk 			| last minute vacation |
		| ie 			| last minute vacation |
		| se 			| last minute vacation |
		| no 			| last minute vacation |
		| dk 			| last minute vacation |
		| au 			| last minute vacation |
		| nz 			| last minute vacation |
		| de 			| last minute vacation |
		| us 			| weekend getaway 	   |
		| uk 			| weekend getaway      |
		| ie 			| weekend getaway      |
		| se 			| weekend getaway      |
		| no 			| weekend getaway      |
		| dk 			| weekend getaway      |
		| au 			| weekend getaway      |
		| nz 			| weekend getaway      |
		| mx            | weekend getaway      |
		| sg            | weekend getaway      |



	Scenario Outline: Access information pages that lands on mobile optimized home page
        Given I visit Hotwire from <countryCode> on a mobile device
        And I go to <category> information page 
        Then I should be on mobile optimized hotel search page
        
    Examples:Location parameters
        | countryCode 	| category 		 |
        | us 			| Chicago travel |
		| uk 			| Chicago travel |
		| ie 			| Chicago travel |
		| se 			| Chicago travel |
		| no 			| Chicago travel |
		| dk 			| Chicago travel |
		| au 			| Chicago travel |
		| nz 			| Chicago travel |
		| hk 			| Chicago travel |
		| de 			| Chicago travel |
		| sg 			| Chicago travel |

		
	
	Scenario Outline: Access information pages that lands on mobile optimized hotel search page
        Given I visit Hotwire from <countryCode> on a mobile device
        And I go to hotel <category> information page 
        Then I should be on mobile optimized hotel search page
        
	Examples:Location parameters    
    | countryCode	| category	  	|
    | us 			| rate report 	|
    | uk 			| rate report 	|
    | ie 			| rate report 	|
    | se 			| rate report 	|
    | no 			| rate report 	|
    | dk 			| rate report 	|
    | au 			| rate report 	|
    | nz 			| rate report 	|
    | hk 			| rate report 	|
    | us 			| ratings 		|
    | uk 			| ratings 		|
    | ie 			| ratings 		|
    | se 			| ratings 		|
    | no 			| ratings 		|
    | dk 			| ratings 		|
    | au 			| ratings 		|
    | nz 			| ratings 		|
    | de 			| ratings 		|
    | us 			| destinations	|
    | uk 			| destinations	|
    | ie 			| destinations	|
    | se 			| destinations	|
    | no 			| destinations	|
    | dk 			| destinations 	|
    | au 			| destinations 	|
    | nz			| destinations 	|
    | hk 			| destinations 	|
    | mx 			| destinations 	|
    | sg 			| destinations 	|
    | us 			| suppliers 	|
    | uk 			| suppliers 	|
    | ie 			| suppliers 	|
    | se 			| suppliers 	|
    | no 			| suppliers 	|
    | dk 			| suppliers 	|
    | au 			| suppliers 	|
    | nz 			| suppliers 	|

		
	
	Scenario Outline: Access vertical information links that lands on mobile optimized pages
        Given I visit Hotwire from <countryCode> on a mobile device
        And I go to a <vertical> information page 
        Then I should be on <landingPage>
        
    Examples:Location parameters
        | countryCode	| vertical	|landingPage									|
        | us        	| hotel 	| mobile optimized hotel search page			|
        | uk        	| hotel		| mobile optimized hotel search page			|
        | ie        	| hotel		| mobile optimized hotel search page			|
		| se        	| hotel		| mobile optimized hotel search page			|
        | no        	| hotel		| mobile optimized hotel search page			|
		| dk        	| hotel		| mobile optimized hotel search page			|
        | au        	| hotel		| mobile optimized hotel search page			|
		| nz        	| hotel		| mobile optimized hotel search page			|
		| hk        	| hotel 	| mobile optimized hotel search page			|
		| sg        	| hotel 	| mobile optimized hotel search page			|
        | us        	| car		| mobile optimized car search page				|
        | uk        	| car		| Hotwire branded 3rd party mobile home page	|
        | ie        	| car		| Hotwire branded 3rd party mobile home page	|
		| se        	| car		| Hotwire branded 3rd party mobile home page	|
        | no        	| car		| Hotwire branded 3rd party mobile home page	|
		| dk        	| car		| Hotwire branded 3rd party mobile home page	|
		| au        	| car		| Hotwire branded 3rd party mobile home page	|
        | nz        	| car		| Hotwire branded 3rd party mobile home page	|
        | de        	| car   	| Hotwire branded 3rd party mobile home page    |
        | mx        	| car   	| Hotwire branded 3rd party mobile home page    |
        | sg        	| car   	| Hotwire branded 3rd party mobile home page    |

        
    Scenario Outline: Access deal links that lands on mobile optimized pages
        Given I visit Hotwire from <countryCode> on a mobile device
        And I go to a deals page for <vertical>
        Then I should be on <landing page>
        
    Examples:Location parameters
        | countryCode	| vertical	| landing page									|
        | us        	| hotel		| mobile optimized hotel search page 			|
        | uk        	| hotel		| mobile optimized hotel search page 			|
        | ie        	| hotel		| mobile optimized hotel search page 			|
        | se        	| hotel		| mobile optimized hotel search page 			|
        | no        	| hotel		| mobile optimized hotel search page 			|
        | dk        	| hotel		| mobile optimized hotel search page 			|
        | au        	| hotel		| mobile optimized hotel search page 			|
        | nz        	| hotel		| mobile optimized hotel search page 			|
        | hk        	| hotel     | mobile optimized hotel search page 			|
        | sg        	| hotel     | mobile optimized hotel search page 			|
        | us        	| car		| mobile optimized car search page				|
        | uk        	| car		| Hotwire branded 3rd party mobile home page	|
        | ie        	| car		| Hotwire branded 3rd party mobile home page 	|
        | se        	| car		| Hotwire branded 3rd party mobile home page 	|
        | no        	| car		| Hotwire branded 3rd party mobile home page 	|
        | dk        	| car		| Hotwire branded 3rd party mobile home page 	|
        | au        	| car		| Hotwire branded 3rd party mobile home page	|
        | nz        	| car		| Hotwire branded 3rd party mobile home page	|
        | hk        	| car       | Hotwire branded 3rd party mobile home page	|
        | mx        	| car       | Hotwire branded 3rd party mobile home page 	|
        | sg        	| car       | Hotwire branded 3rd party mobile home page 	|

    
	Scenario Outline: Access deal links that lands on mobile optimized pages
        Given I visit Hotwire from <countryCode> on a mobile device
        And I go to a deals page
        Then I should be on mobile optimized home page
        
    Examples:Location parameters
        | countryCode	|
        | us    		|
        | uk        	|
        | ie        	|
        | se        	|
        | no        	|
        | dk        	|
        | au        	|
        | nz        	|
        | hk        	|
	 	| mx			|
	 	| sg			|

	Scenario Outline: Access links from a search engine (such as Google) that are remapped to mobile search page
        Given I visit Hotwire <product> from a search engine on a mobile device
        Then I should be on mobile optimized <landingVertical> search page
    Examples: SEO link options
        | product	| landingVertical 	|
        | hotels	| hotel				|
        | cars 		| car				|

	
	Scenario Outline: Access links with parameters from a search engine (such as Ask.com, if they still exist)
                        that are remapped to mobile optimized search page or home page
        Given I visit Hotwire <product> from a search engine with marketing parameters on a mobile device
        Then I should be on mobile optimized <landingVertical> page
    Examples: SEO with markeing params link options
        | product | landingVertical |
        |		  | home			|
        | hotels  | hotel search 	|
        | cars 	  | car search		|
        
    
    Scenario Outline: Access product landing links that are remapped to mobile hotel search page
        Given I visit Hotwire <product> on a mobile device
        Then I should be on mobile optimized <landingVertical> search page
    Examples: Product landing page options
        | product	| landingVertical	|
        | hotels	| hotel				|				
        | cars		| car				|