@MOBILE
Feature: Netscalar routing of URLs related to account login, registration and password reset
   As a user of the mobile site, I want to be able to land on the right pages when I access account related links.
   This takes about 1 min and 5 seconds to run

    Background:
        Given the application is accessible
        
	Scenario Outline: Access the login from a mobile optimized sign in page
        Given I visit Hotwire from <countryCode> on a mobile device
        And I want to login
        Then I should be on mobile optimized sign in page
    Examples:Location parameters
        | countryCode  	|
    	| us		|
    	| uk		|
    	| ie		|
		| se        |
        | no        |
        | dk        |
        | au        |
        | nz        |
        | sg        |

	Scenario Outline: New account registration page that lands on mobile optimized registration page
        Given I visit Hotwire from <countryCode> on a mobile device
        And I want to register as a new user
        Then I should be on mobile optimized registration page
    Examples:Location parameters
        | countryCode  	| 
    	| us		|
    	| uk		|
		| ie		|
		| se        |
        | no        |
        | dk        |
        | au        |
        | nz        |
        | sg        |

	Scenario Outline: Access user's trip information that lands on mobile sign in page because the user is not authenticated yet
        Given I visit Hotwire from <countryCode> on a mobile device
        And I want to access my trip information
        Then I should be on mobile optimized sign in page
    Examples:Location parameters
        | countryCode  	| 
    	| us			|
    	| uk			|
		| ie			|
		| se      	    |
        | no            |
        | dk            |
        | au            |
        | nz            |
        | hk            |
        | sg            |

	Scenario Outline: Password assistance page that lands on mobile optimized password assistance page
        Given I visit Hotwire from <countryCode> on a mobile device
        And I want assistance as I forgot my password
        Then I should be on mobile optimized password assistance page
    Examples:Location parameters
        | countryCode  	| 
    	| us			|
    	| uk			|
		| ie			|
		| se        	|
        | no        	|
        | dk        	|
        | au        	|
        | nz        	|
        | de			|
        | sg			|

	Scenario Outline: Password reset link from am email should land on the mobile application (Password reset
    	or a system error page if the link has expired)
        Given I visit Hotwire from <countryCode> on a mobile device
        And I want to reset my password via the password assistance email
        Then I should be on mobile optimized page
    Examples:Location parameters
        | countryCode  	| 
    	| us			|
    	| uk			|
		| ie			|
		| se      	    |
        | no        	|
        | dk        	|
        | au        	|
        | nz        	|
        | mx			|
        | sg			|
