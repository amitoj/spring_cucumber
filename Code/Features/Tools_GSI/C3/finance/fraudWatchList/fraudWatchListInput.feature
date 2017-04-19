@TOOLS   @c3Finance
Feature:  FINANCE Risk Management Administration Fraud Watch List Input.
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  #Author V Yulun
  @ACCEPTANCE @JANKY
  Scenario: Add to fraud list. RTC-1358
    Given customer with existing purchases
    Given I login into C3 with username "csrcroz9"
    Given I go to Fraud Watch List admin page
    When Add record to fraud watch list
    Then I see "The fraud watch list was successfully updated with the specified criteria." text on page

  #Author V Yulun
  @ACCEPTANCE  @JANKY
  Scenario: Search from fraud list. RTC-1357
    Given active fraud customer
    Given I login into C3 with username "csrcroz9"
    Given I go to Fraud Watch List admin page
    When Search email in fraud watch list
    Then I see "The following are the results of the search on the fraud watchlist." text on page

  #Author V Yulun
  @ACCEPTANCE @JANKY
  Scenario: Take off user from fraud list. RTC-1356
    Given active fraud customer
    Given I login into C3 with username "csrcroz9"
    Given I go to Fraud Watch List admin page
    When Search email in fraud watch list
    And I deactivate fraud account
    Then I see fraud account deactivated


