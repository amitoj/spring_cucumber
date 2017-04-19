@US @SingleThreaded
Feature: Rental Car Damage Protection module. Booking through static module
  Owner:Vyacheslav Zyryanov
  Owner: Nataliya Golodiuk

  #  - Insurance Module should correspond to comps
  #  - Insurance static Module should appear in case if dynamic insurance is unavaiable (after connection timeout)
  #  - User should be able to book car with insurance
  #  - For retail car payment section should appear if user wants to book car with insurance

  Scenario Outline:RTC-6455

    Given set property "hotwire.biz.order.AccessAmerica.quote.timeout" to value "1"
    # Background
    Given default dataset
    Given activate car's version tests
    Given the application is accessible
    # Scenario
    Given I'm searching for a car in "AIRPORT"
    Given I want to travel in the near future
    Given I <negation> request insurance
    Given I request quotes
    When I choose a <carType> car
    Then Rental car damage protection was presented as static module
    Then I fill in all billing information
    Then I complete purchase with agreements
    Then I receive immediate confirmation

  Examples:
    | negation | carType |
    |          | retail  |
    | don't    | opaque  |

  Scenario:
    # Roll back changes..
    Given set property "hotwire.biz.order.AccessAmerica.quote.timeout" to value "5000"
    Given the application is accessible