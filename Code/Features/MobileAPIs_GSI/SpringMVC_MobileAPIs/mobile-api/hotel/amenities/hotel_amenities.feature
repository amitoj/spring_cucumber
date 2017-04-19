@MobileApi
Feature:  Hotel Mobile API amenities

  Scenario: verify hotel amenities response
    Given I use hotel id 596
    When I execute the hotel amenities request with room type 1Penthouse
    Then I see hotel amenities are present
