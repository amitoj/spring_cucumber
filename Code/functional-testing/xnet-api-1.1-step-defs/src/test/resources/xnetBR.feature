    @XnetAPIBR
    Feature: Xnet API BR on V1.1 Regression

        Background:
        Given the xnet service is accessible

        Scenario: Verify the empty booking retrieval response
        Given I'm a valid supplier
        And my booking retrieval hotel id is 12323
        And my minutes in the past is 30
        When I retrieve booking
        Then I should get empty booking response

        Scenario: Verify the proper exception is thrown for an invalid hotel id
        Given I'm a valid supplier
        And my booking retrieval hotel id is 999999999
        And my minutes in the past is 30
        When I retrieve booking
        Then I should expect Invalid hotel id error

        Scenario: Verify the proper exception is thrown for an omitted hotel id
        Given I'm a valid supplier
        And my minutes in the past is 30
        When I retrieve booking
        Then I should expect The current API version only supports booking retrieval by hotel id AND minutes in past error

        Scenario: Verify the proper exception is thrown for minutes in past element when hotelId is not passed
        Given I'm a valid supplier
        And my minutes in the past is 600
        When I retrieve booking
        Then I should expect minutes in past error

        Scenario Outline: Verify minsInPast for invalid input with hotel Id
        Given I'm a valid supplier
        And my booking retrieval hotel id is 12323
        And my minutes in the past is <mins>
        When I retrieve booking
        Then I should expect minutes in past error

        Examples: Mins in the past input
                  | mins |
                  | -30  |
                  | 29   |
                  | 361  |
                  | -360 |

        Scenario: Verify minsInPast for valid input with hotel Id
        Given I'm a valid supplier
        And my booking retrieval hotel id is 12323
        And my minutes in the past is 30
        When I retrieve booking
        Then I should get empty booking response

        @JANKY
        Scenario Outline: Verify the validity of a single-night booking response
        Given I'm a valid supplier
        And my booking retrieval hotel id is <hotelId>
        And my minutes in the past is 30
        When I retrieve booking
        Then I should get booking of type is <bookingType>
        Then I should get room stay whose id is <roomTypeId>
        Then I should get room stay whose rate plan id is <ratePlanId>
        Then I should get number of rooms is <numRooms>
        Then I should get number of adults is <adultNum>
        Then I should get number of children is <childrenNum>
        Then I should get rates in currency is <currencyCode>
        Then I should get currency code is <totalAmountCurrencyCode>

        Examples: Valid BR response validation
                        | hotelId | numberOfHotelRooms | bookingType | roomTypeId | ratePlanId | numRooms |  adultNum | childrenNum | currencyCode | totalAmountCurrencyCode |
                        | 8880    |                  1 | Book        | STANDARD   |  XHW       | 1        |  2        |     0       |  USD         |  USD                   |
