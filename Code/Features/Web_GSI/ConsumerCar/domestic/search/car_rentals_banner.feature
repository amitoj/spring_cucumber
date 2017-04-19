@US @simulator @SingleThreaded
Feature: Checking carRentals banner on car results page
  Owner: Vyacheslav Zyryanov

  # @BUG Banner is not appearing on JSLAVE env.
  # Scenario Outline:
  #  Given set property "hotwire.biz.search.car.crDynamicBannerEnabled" to value "false"
  #  Given default dataset
  #  Given activate car's version tests
  #  Given set version test "vt.CRB12" to value "<vt>"
  #  Given the application is accessible
  #  And I choose <currency> currency
  #  And I'm searching for a car in "SFO"
  #  And I want to travel in the near future
  #  And I request quotes
  #  Then I will see carRentals.com banner with static price

 # Examples: vt.CRB12 values
 #   | currency | vt  |
 #   | USD      | 2   |
 #   | CAD      | 3   |
 #   | EUR      | 2   |
 #   | CHF      | 3   |

 # Scenario:
 #   Then set property "hotwire.biz.search.car.crDynamicBannerEnabled" to value "true"