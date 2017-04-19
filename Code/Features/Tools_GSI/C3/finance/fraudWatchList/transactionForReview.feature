@TOOLS   @c3Finance
Feature:  FINANCE / Risk Management Administration (Fraud) / Transactions for Review
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  #Author V Yulun
  @ACCEPTANCE @ARCHIVE
  Scenario: Searches for Fraud Watch List Input / Air. RTC-1547
    Given customer with existing purchases
    Given I login into C3 with username "csrcroz9"
    Given I go to Air transactions for review on admin page
    When I choose 3 air transaction for review
    Then I see "3 purchases were successfully updated." text on page

    #Author V Yulun
  @ACCEPTANCE
  Scenario: Searches for Fraud Watch List Input / Hotel. RTC-1556
    Given customer with existing purchases
    Given I login into C3 with username "csrcroz9"
    Given I go to Hotel transactions for review on admin page
    When I choose 1 hotel transaction for review
    Then I see "1 purchases were successfully updated." text on page

    #Author V Yulun
  @ACCEPTANCE
  Scenario: Searches for Fraud Watch List Input / Car. RTC-1555
    Given customer with existing purchases
    Given I login into C3 with username "csrcroz9"
    Given I go to Car transactions for review on admin page
    When I choose 2 car transaction for review
    Then I see "2 purchases were successfully updated." text on page

    #Author V Yulun
  @ACCEPTANCE
  Scenario: Search notning for Fraud Watch List Input / Air. RTC-1571
    Given customer with existing purchases
    Given I login into C3 with username "csrcroz9"
    Given I go to Air transactions for review on admin page
    When I choose 0 air transaction for review
    Then I see "To pass on reviewing a transaction, please select a transaction first." text on page
