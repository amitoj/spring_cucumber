@MobileApi @simulator
Feature: Verifications of price check cases on cars with mobile API

  Scenario Outline: verify price change flags in car details
    And pickup location is CHS
    And date and time range is between 6 days from now and 8 days from now
    And deposit method is Credit Card
    And age of drive is 25+
    When I execute car search request
    And car search has been executed
    Then I see search results are present
    And search criteria is present
    And I use the <carType> car type result id in the request for details
    And I execute car details request
    Then car details request has been executed
    And car details response is present
    And price <priceMessage> flag is true

  Examples:
    | carType | priceMessage |
    | ECAR    | up           |
    | SCAR    | down         |
    | FCAR    | same         |