@TOOLS
Feature: SID/BID referrals Database verification.
  Owner: Oleksandr Zelenov

  Background:
    Given Domestic application is accessible


  Scenario Outline: User returns and searches via a siteID referral. RTC-441, RTC-442
    Given referral parameter siteID is "<site_id>"
    Given I open referral link
    And I search for hotel
    And I see results page
    And I save reference number from results page
    And I see in search_referral table that REFERRAL_TYPE is "A"
    And I see in search_referral table that REFERRER_ID is "<site_id>"

  Examples:
    | site_id                            |
    | mtYSsY4-eoEQh6JmY                  |
    | mtYSsY4OLOQ-eoEQh6JmYquqN62lw7yEMA |


  Scenario Outline: Referral link database verification. RTC-442
    Given referral parameter <parameter1> is "<value1>"
    Given referral parameter <parameter2> is "<value2>"
    Given referral parameter <parameter3> is "<value3>"
    Given referral parameter <parameter4> is "<value4>"
    Given I open referral link
    And I search for hotel
    And I see results page
    And I save reference number from results page
    And I see in search_referral table that REFERRAL_TYPE is "<type>"
    And I see in search_referral table that REFERRER_ID is "<referrer>"
    And I see in search_referral table that LINK_ID is "<link>"
    And I see in search_referral table that KEYWORD_ID is "<keyword>"
    And I see in search_referral table that MATCH_TYPE_ID is "<match_type>"
    And I see in search_referral table that VERSION_ID is "<version>"

  Examples:
    | parameter1 | value1 | parameter2 | value2 | parameter3 | value3 | parameter4 | value4 | type | referrer | link | keyword | match_type | version |
    | sid        | S11    | bid        | B11    | kid        | K11    | mid        | M1     | O    | S11      | B11  | K11     | M1         |         |
    | nid        | N-000  | vid        | v-000  | did        | D1     |            |        | N    | N-000    | D1   |         |            | v-000   |
    | iid        | I000   | pid        | P000   |            |        |            |        |      | I000     | P000 |         |            |         |

  Scenario Outline: Referral purchase. Happy path. RTC-442, RTC-463
    Given referral parameter <parameter1> is "<value1>"
    Given referral parameter <parameter2> is "<value2>"
    Given I open referral link
    And I search for hotel
    And I see results page
    And I save reference number from results page
    And I process the results page
    And I process the details page
    And I process the billing page
    Then I receive immediate confirmation
    And I see in search_referral table that REFERRAL_TYPE is "<type>"
    And I see in search_referral table that REFERRER_ID is "<value1>"
    And I see in search_referral table that LINK_ID is "<value2>"

  Examples:
    | parameter1 | value1 | parameter2 | value2 | type |
    | sid        | S000   | bid        | b000   | O    |