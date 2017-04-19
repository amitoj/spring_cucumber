@TOOLS   @c3Finance
Feature:  FINANCE / Risk Management Administration (Fraud) / Clear Old Transactions
  Owner: Vladimir Yulun

  Background:
    Given C3 application is accessible

  @ACCEPTANCE
  Scenario: Try to clear old transactions without items. RTC-1570
    Given I login into C3 with username "csrcroz9"
    Given I go to Clear Old Transactions admin page
    When I choose Clear Old Transactions without parameters
    Then I see "To clear out old transactions, please enter a transaction amount in the format $123.45." text on page
    Then I see "To clear out old transactions, please enter a before date in the format MM/DD/YY." text on page


  @ACCEPTANCE
  Scenario: Try to Clear old transactions by date only. RTC-1565
    Given I login into C3 with username "csrcroz9"
    Given I go to Clear Old Transactions admin page
    When I clear all transactions before 10 days and under 0 amount
    Then I see "To clear out old transactions, please enter a transaction amount in the format $123.45." text on page

  @ACCEPTANCE
  Scenario: Try to Clear old transactions by date and amount. RTC-1593
    Given I login into C3 with username "csrcroz9"
    Given I go to Clear Old Transactions admin page
    When I clear all transactions before 180 days and under 20 amount
    Then I see "purchases were successfully updated." text on page


