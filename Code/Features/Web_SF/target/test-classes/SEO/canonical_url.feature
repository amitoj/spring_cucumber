@US
Feature: Canonical URL tests
  Owner: Komarov Igor

  Background:
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @ACCEPTANCE
  Scenario: URL leads to the car landing page. RTC-423
    Given I load URL for car seo page
    And I see car landing page

  @ACCEPTANCE
  Scenario: URL leads to the air landing page. RTC-424
    Given I load URL for flight seo page
    And I see flight landing page

  @ACCEPTANCE
  Scenario: URL leads to the hotel landing page. RTC-425
    Given I load URL for hotel seo page
    And I see hotel landing page

  @ACCEPTANCE
  Scenario: URL leads to the deals landing page. RTC-427
    Given I load URL for deals seo page
    Then  I verify deals page

  #Next tests were added by bshukaylo
  @ACCEPTANCE
  Scenario: Destination city with a SID/BID RTC-444
    Given I am a new user
    Given I create an account
    And I am on car landing page
    When I have load URL with destination city Boston,ma/sid/S007/bid/B007
    And Car farefinder contain "Boston,ma" in location
    And I'm searching for a car in "Boston,ma"
    And I want to travel in the near future
    And I request quotes
    And I see a non-empty list of search results
    Then I receive immediate DB next status for SID/BID search referrals table
      |O            |S007       |B007       |null       |null      |null      |

  @ACCEPTANCE
  Scenario:  Destination city with SID/BID/KID RTC-445
    Given I am a new user
    Given I create an account
    And I am on car landing page
    When I have load URL with destination city Denver,co/sid/S007/bid/B007/kid/K007
    And Car farefinder contain "Denver,co" in location
    And I'm searching for a car in "Denver,co"
    And I want to travel in the near future
    And I request quotes
    And I see a non-empty list of search results
    Then I receive immediate DB next status for SID/BID search referrals table
      |O            |S007    |B007       |null       |K007      |null      |

  @ACCEPTANCE
  Scenario: Valid destination city with SID/BID/KID/MID. RTC-446
  #author VYulun
    Given I am a new user
    Given I create an account
    And I am on car landing page
    When I have load URL with destination city Tucson/sid/S007/bid/B007/kid/K007/mid/M007
    And I'm searching for a car in "Tucson"
    And I want to travel in the near future
    And I request quotes
    And I see a non-empty list of search results
    Then I receive immediate DB next status for SID/BID search referrals table
      |O            |S007       |B007       |null       |K007      |M007      |

  @ACCEPTANCE
  Scenario: NID/VID persistence in DB RTC-447
    Given I am a new user
    Given I create an account
    And I am on car landing page
    When I have load URL with destination city SF/iid/I007/pid/P007
    And Car farefinder contain "SF" in location
    And I'm searching for a car in "SF"
    And I want to travel in the near future
    And I request quotes
    And I see a non-empty list of search results
    Then I receive immediate DB next status for SID/BID search referrals table
      |I            |I007       |P007       |null       |null      |null      |

  @ACCEPTANCE
  Scenario: NID/VID persistence in DB RTC-448
    Given I am a new user
    Given I create an account
    When  I load site with URL path
    """
    /seo/air/d-city/oak/o-city/sea/nid/N-HFA-001/vid/V-HFA-001-V1
    """
    Then  I am redirected to the URL
    """
    /seo/air/d-city/oak/o-city/sea
    """
    And   Air destination location is "oak" on farefinder
    And   Air origination location is "sea" on farefinder
    Given I set air dates between 5 days from now and 14 days from now
    When  I start air search without specifying search parameters
    Then  I should see air search results page
    And   I receive immediate DB next status for SID/BID search referrals table
      |N            |N-HFA-001       |null       |V-HFA-001-V1       |null      |null      |

  @ACCEPTANCE
  Scenario: NID/VID persistence in DB RTC-449
    Given I am a new user
    Given I create an account
    And I am on car landing page
    When I have load URL with destination city sea/nid/N-HFA-001/vid/V-HFA-001-V1/did/D001
    And Car farefinder contain "sea" in location
    And I'm searching for a car in "sea"
    And I want to travel in the near future
    And I request quotes
    And I see a non-empty list of search results
    Then I receive immediate DB next status for SID/BID search referrals table
      |N            |N-HFA-001       |D001       |V-HFA-001-V1       |null      |null      |

 @ACCEPTANCE
  Scenario: NID/VID persistence in DB RTC-429
    Given I am a new user
    Given I create an account
    And I am on car landing page
    When I have load URL with destination city calgary/siteID/0zJZkk7l74E-OhE2gZ
    And Car farefinder contain "calgary" in location
    And I'm searching for a car in "calgary"
    And I want to travel in the near future
    And I request quotes
    And I see a non-empty list of search results
    Then I receive immediate DB next status for SID/BID search referrals table
      |A            |0zJZkk7l74E-OhE2gZ       |null       |null       |null      |null      |

  @ACCEPTANCE
  Scenario Outline: Register a user with different referral type, makes a hotel purchase for the user.405, 408
    Given I load URL for referral type <referralType>
    Given I am a new user
    Given I create an account
    And I'm searching for a hotel in "San Francisco, CA "
    And I want to travel in the near future
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  Examples: referral type
    | referralType  |
    | SB            |
    | N             |

  @ACCEPTANCE
  Scenario: Register a user with S referral type, makes a hotel purchase for the user. RTC-402
    Given I load URL for referral type S
    Given I am a new user
    Given I create an account
    And I'm searching for a hotel in "San Francisco, CA "
    And I want to travel in the near future
    And I request quotes
    When I choose a hotel and purchase as guest a quote
    Then I receive immediate confirmation

  @ACCEPTANCE @STBL
  Scenario Outline: Register a user with different referral type, makes a car purchase for the user. RTC-403, 409
    Given I load URL for referral type <referralType>
    Given I am a new user
    Given I create an account
    And I'm searching for a car from "SFO" to "LAX"
    And I want to travel in the near future
    And I request quotes
    And I see a non-empty list of search results
    And I choose a retail car
    When I fill in all billing information
    And I complete purchase with agreements
    Then I receive immediate confirmation

  Examples: referral type
    | referralType  |
    | S             |
    | N             |

  @ACCEPTANCE @JANKY
  Scenario: Register a user with different referral type, makes a car purchase for the user.RTC-406
    Given I load URL for referral type SB
    Given I am a new user
    Given I create an account
    And I'm searching for a car from "SFO" to "LAX"
    And I want to travel in the near future
    And I request quotes
    And I see a non-empty list of search results
    And I choose a retail car
    When I fill in all billing information
    And I complete purchase with agreements
    Then I receive immediate confirmation



