@MOBILE
Feature: Netscalar routing of URLs that take the user to the desktop full site
   As a user of the mobile site, I want to be able to land on the desktop full site when I access
   links such as surveys and air marketing links
    

#    Background:
#        Given the application is accessible
#    
#    Scenario Outline: Access survey pages that lands on desktop full site survey pages
#        Given I visit Hotwire from us on a mobile device
#        And I want to fill out a <vertical> survey
#        Then I should be on desktop domestic <vertical> survey page
#    Examples:Location parameters
#        | vertical  |
#        | air 		|
#        | hotel 	|
#        | car		|
#		
#	Scenario Outline: Access air search options marketing links that lands on desktop domestic air results page
#        Given I visit Hotwire from <countryCode> on a mobile device
#        And I want to find air deals at a destination for given dates from a marketing email
#        Then I should be on desktop domestic air results page
#    Examples:Location parameters
#        | countryCode  |
#        | us           |
#        | uk           |
#        | ie           |
#        | se           |
#        | no           |
#        | dk           |
#        | au           |
#        | nz           |
#        | hk           |
#        | de           |
#        | sg           |
#        
#        
#    Scenario Outline: Access air search options marketing links from confirmation email that lands on desktop domestic air results page
#        Given I visit Hotwire from <countryCode> on a mobile device
#        And I want to find air cross sell deals at a destination for given dates from a confirmation email
#        Then I should be on desktop domestic air results page
#    Examples:Location parameters
#        | countryCode  |
#        | us           |
#        | uk           |
#        | ie           |
#        | se           |
#        | no           |
#        | dk           |
#        | au           |
#        | nz           |
#        | hk           |
#        | de           |
#        | sg           |
#     
#        
#     
#	Scenario Outline: Access air index page links that lands on desktop full site air index page
#        Given I visit Hotwire from <countryCode> on a mobile device
#        And I go to the air index page
#        Then I should be on desktop domestic air index page
#        
#    Examples:Location parameters
#        | countryCode  |
#        | us           |
#        | uk           |
#        | ie           |
#        | se           |
#        | no           |
#        | dk           |
#        | au           |
#        | nz           |
#        | hk           |
#        | de           |     
#        | mx           |
#        | sg           |
#
#
#	Scenario Outline: Access full site via the mobile site for domestic and international users
#        Given I visit Hotwire from <countryCode> on a mobile device
#        And I choose to see the full site
#        Then I should be on desktop <target url>
#
#    Examples: Application redirect to full site rules parameters
#      | countryCode | target url				|
#      | us 			| domestic home page		|
#      | uk 			| international home page	|
#      | ie 			| international home page	|
#      | au 			| international home page 	|
#      | nz 			| international home page 	|
#      | se 			| international home page 	|
#      | dk 			| international home page 	|
#      | no 			| international home page 	|
#      | hk 			| international home page 	|
#      | de 			| international home page 	|
#      | mx 			| international home page 	|
#      | sg 			| international home page 	|
#
#	Scenario: Access air links from a search engine (such as Google) that go to desktop air index page
#        Given I visit Hotwire air from a search engine on a mobile device
#        Then I should be on desktop domestic air index page
#
#    Scenario: Access links with parameters from a search engine (such as Ask.com, if they still exist)
#                        that are remapped to mobile search page
#        Given I visit Hotwire air from a search engine with marketing parameters on a mobile device
#        Then I should be on desktop domestic air index page 
#        
#	
#	Scenario Outline: Access vertical information links that lands on desktop full site
#        Given I visit Hotwire from <countryCode> on a mobile device
#        And I go to a air information page 
#        Then I should be on desktop domestic air information page
#        
#    Examples:Location parameters
#        | countryCode  |
#        | us           |
#        | uk           |
#        | ie           |
#        | se           |
#        | no           |
#        | dk           |
#        | au           |
#        | nz           |
#        | hk           |
#        | de           |
#        | sg           |

        	