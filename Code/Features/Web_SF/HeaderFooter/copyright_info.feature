Feature: Global Footer on uhp

  Background:
  Given default dataset
  Given the application is accessible

    #author Vladimir Yulun
   @US @ACCEPTANCE
  Scenario: Legal disclaimer and copyright info. RTC-661
    Given I see UHP page
    Then I verify hotwire copyright info
    Then I verify hotwire legal disclaimer
