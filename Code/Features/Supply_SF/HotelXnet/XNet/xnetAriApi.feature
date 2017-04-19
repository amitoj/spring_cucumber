@API @V1.0 @V1.1
Feature: Xnet Ari API regression
  This feature file executes entire Xnet Ari API regression cases

  Background:
    Given the xnet service is accessible

  @LIMITED
  Scenario: Update Xnet Inventory using valid username and password every day
    Given the xnet service is accessible
    Given I'm a valid supplier
    And my hotel id is 8880
    And date range is between 1 days from now and 5 days from now every day
    And room type is STANDARD
    And total inventory available is 10
    And rate plan is XHW
    And currency is USD
    And rate per day is 25
    And extra person fee is 0
    When I update Inventory
    Then I should get No error

  @SMOKE
  Scenario: Update Xnet Inventory using valid username and password every day
    Given the xnet service is accessible
    Given I'm a valid supplier
    And my hotel id is 7485
    And date range is between 1 days from now and 5 days from now every day
    And room type is STANDARD
    And total inventory available is 10
    And rate plan is XHW
    And currency is USD
    And rate per day is 25
    And extra person fee is 0
    When I update Inventory
    Then I should get No error

  @ACCEPTANCE
  Scenario Outline: Update Xnet Inventory for valid and invalid Currency
    Given I'm a valid supplier
    And my hotel id is <hotelId>
    And date range is between <startDateShift> days from now and <endDateShift> days from now every day
    And room type is <roomTypeId>
    And total inventory available is <totalInventoryAvailable>
    And rate plan is <ratePlanId>
    And currency is <currency>
    And rate per day is <perDay>
    And extra person fee is <extraPerson>
    When I update Inventory
    Then I should get <response> error

  Examples: Currency validation
    | hotelId | startDateShift | endDateShift | roomTypeId | totalInventoryAvailable | ratePlanId | currency | perDay | extraPerson | response                |
    | 7485    | 4              | 5            | STANDARD   | 1                       | XHW        | usd      | 100    | 0           | No                      |
    | 7485    | 3              | 3            | STANDARD   | 1                       | XHW        | USDA     | 100    | 0           | Invalid currency length |
    | 7485    | 3              | 3            | STANDARD   | 1                       | XHW        | AUD      | 100    | 0           | Unknown currency        |
    | 7485    | 3              | 3            | STANDARD   | 1                       | XHW        | US       | 100    | 0           | Invalid currency length |

  @ACCEPTANCE
  Scenario Outline: Update Xnet Inventory for valid and invalid Date Range
    Given I'm a valid supplier
    And my hotel id is <hotelId>
    And date range is between <startDateShift> days from now and <endDateShift> days from now every day
    And room type is <roomTypeId>
    And total inventory available is <totalInventoryAvailable>
    And rate plan is <ratePlanId>
    And currency is <currency>
    And rate per day is <perDay>
    And extra person fee is <extraPerson>
    When I update Inventory
    Then I should get <response> error

  Examples: Date Range validation
    | hotelId | startDateShift | endDateShift | roomTypeId | totalInventoryAvailable | ratePlanId | currency | perDay | extraPerson | response                      |
    | 7485    | 1              | 1            | STANDARD   | 1                       | XHW        | usd      | 50     | 0           | No                            |
    | 7485    | 400            | 450          | STANDARD   | 1                       | XHW        | usd      | 50     | 0           | No                            |
    | 7485    | 400            | 440          | STANDARD   | 1                       | XHW        | usd      | 50     | 0           | No                            |
    | 7485    | 2              | 62           | STANDARD   | 1                       | XHW        | usd      | 50     | 0           | End date not within range     |
    | 7485    | 2              | 3            | STANDARD   | 1                       | XHW        | usd      | 50     | 0           | No                            |
    | 7485    | 5              | 3            | STANDARD   | 1                       | XHW        | usd      | 50     | 0           | Start date Not after end date |
    | 7485    | 1              | 58           | STANDARD   | 1                       | XHW        | usd      | 50     | 0           | No                            |

  @ACCEPTANCE
  Scenario Outline: Update Xnet Inventory for valid and invalid Hotel Id
    Given I'm a valid supplier
    And my hotel id is <hotelId>
    And date range is between <startDateShift> days from now and <endDateShift> days from now every day
    And room type is <roomTypeId>
    And total inventory available is <totalInventoryAvailable>
    And rate plan is <ratePlanId>
    And currency is <currency>
    And rate per day is <perDay>
    And extra person fee is <extraPerson>
    When I update Inventory
    Then I should get <response> error

  Examples: Hotel Id validation
    | hotelId | startDateShift | endDateShift | roomTypeId | totalInventoryAvailable | ratePlanId | currency | perDay | extraPerson | response         |
    | 7485    | 1              | 2            | STANDARD   | 10                      | XHW        | USD      | 100    | 0           | No               |
    | 99999   | 1              | 2            | STANDARD   | 10                      | XHW        | USD      | 100    | 0           | Unknown hotel id |

  @ACCEPTANCE
  Scenario: Update Inventory for all days of the week no overlapping date ranges using specific day
    Given I'm a valid supplier
    And my hotel id is 7485
    And date range is between 5 days from now and 10 days from now every Monday, Thursday
    And room type is STANDARD
    And total inventory available is 10
    And date range is between 11 days from now and 16 days from now every Tuesday, Wednesday
    And room type is STANDARD
    And total inventory available is 10
    When I update Inventory
    Then I should get No error

  @ACCEPTANCE
  Scenario: Update Inventory for all days of the week within 2 overlapping date ranges
    Given I'm a valid supplier
    And my hotel id is 7485
    And date range is between 5 days from now and 10 days from now every day
    And room type is STANDARD
    And total inventory available is 10
    And date range is between 7 days from now and 12 days from now every day
    And room type is STANDARD
    And total inventory available is 10
    And rate plan is XHW
    And currency is USD
    And rate per day is 25
    And extra person fee is 10
    When I update Inventory
    Then I should get Dates must not overlap error

  @ACCEPTANCE
  Scenario: Update Inventory within 2 overlapping date ranges using specific day
    Given I'm a valid supplier
    And my hotel id is 7485
    And date range is between 5 days from now and 10 days from now every Monday, Wednesday, Thursday
    And room type is STANDARD
    And total inventory available is 10
    And date range is between 7 days from now and 12 days from now every day
    And room type is STANDARD
    And total inventory available is 10
    When I update Inventory
    Then I should get Dates must not overlap error

  @ACCEPTANCE
  Scenario: Update Inventory within 2 overlapping date ranges using specific day
    Given I'm a valid supplier
    And my hotel id is 7485
    And date range is between 5 days from now and 10 days from now every Monday, Thursday
    And room type is STANDARD
    And total inventory available is 11
    And date range is between 7 days from now and 12 days from now every Tuesday, Wednesday
    And room type is STANDARD
    And total inventory available is 12
    When I update Inventory
    Then I should get Dates must not overlap error

  @ACCEPTANCE
  Scenario: Update Inventory for all days of the week with 1 overlapping date ranges
    Given I'm a valid supplier
    And my hotel id is 7485
    And date range is between 11 days from now and 15 days from now every day
    And room type is STANDARD
    And total inventory available is 11
    And date range is between 15 days from now and 20 days from now every day
    And room type is STANDARD
    And total inventory available is 12
    When I update Inventory
    Then I should get Dates must not overlap error

  @ACCEPTANCE
  Scenario Outline: Update Xnet Inventory for valid and invalid Rate per day
    Given I'm a valid supplier
    And my hotel id is <hotelId>
    And date range is between <startDateShift> days from now and <endDateShift> days from now every day
    And room type is <roomTypeId>
    And total inventory available is <totalInventoryAvailable>
    And rate plan is <ratePlanId>
    And currency is <currency>
    And rate per day is <perDay>
    And extra person fee is <extraPerson>
    When I update Inventory
    Then I should get <response> error

  Examples: Rate-Per day validation
    | hotelId | startDateShift | endDateShift | roomTypeId | totalInventoryAvailable | ratePlanId | currency | perDay   | extraPerson | response          |
    | 7485    | 4              | 5            | STANDARD   | 1                       | XHW        | usd      | 50       | 0           | No                |
    | 7485    | 5              | 5            | STANDARD   | 1                       | XHW        | usd      | 50.99    | 0           | No                |
    | 7485    | 6              | 6            | STANDARD   | 1                       | XHW        | usd      | 0        | 0           | No                |
    | 7485    | 7              | 7            | STANDARD   | 1                       | XHW        | usd      | 0.00     | 0           | No                |
    | 7485    | 8              | 8            | STANDARD   | 1                       | XHW        | usd      | 9999.111 | 0           | No                |
    | 7485    | 8              | 8            | STANDARD   | 1                       | XHW        | usd      | 1000000  | 0           | Rate not in range |

  @ACCEPTANCE
  Scenario Outline: Update Xnet Inventory for different Restrictions
    Given I'm a valid supplier
    And my hotel id is <hotelId>
    And date range is between <startDateShift> days from now and <endDateShift> days from now every day
    And room type is <roomTypeId>
    And total inventory available is <totalInventoryAvailable>
    And rate plan is <ratePlanId>
    And currency is <currency>
    And rate per day is <perDay>
    And extra person fee is <extraPerson>
    And sold out is <soldOut>
    And closed to arrival is <closedToArrival>
    When I update Inventory
    Then I should get <response> error

  Examples: Hotel Id validation
    | hotelId | startDateShift | endDateShift | roomTypeId | totalInventoryAvailable | ratePlanId | currency | perDay | extraPerson | response | soldOut  | closedToArrival |
    | 7485    | 1              | 2            | STANDARD   | 10                      | XHW        | USD      | 100    | 0           | No       | Active   | Inactive        |
    | 7485    | 1              | 2            | STANDARD   | 10                      | XHW        | USD      | 100    | 0           | No       | Inactive | Inactive        |
    | 7485    | 1              | 2            | STANDARD   | 10                      | XHW        | USD      | 100    | 0           | No       | Active   | Active          |
    | 7485    | 1              | 2            | STANDARD   | 10                      | XHW        | USD      | 100    | 0           | No       | Inactive | Active          |

  @ACCEPTANCE
  Scenario Outline: Update Xnet Inventory for valid and invalid room type
    Given I'm a valid supplier
    And my hotel id is <hotelId>
    And date range is between <startDateShift> days from now and <endDateShift> days from now every day
    And room type is <roomTypeId>
    And total inventory available is <totalInventoryAvailable>
    And rate plan is <ratePlanId>
    And currency is <currency>
    And rate per day is <perDay>
    And extra person fee is <extraPerson>
    When I update Inventory
    Then I should get <response> error

  Examples: Room type validation
    | hotelId | startDateShift | endDateShift | roomTypeId | totalInventoryAvailable | ratePlanId | currency | perDay | extraPerson | response          |
    | 7485    | 4              | 5            | STANDARD   | 12                      | XHW        | USD      | 20     | 10          | No                |
    | 7485    | 1              | 2            | standard   | 10                      | XHW        | USD      | 100    | 0           | Unknown room type |

  @ACCEPTANCE
  Scenario Outline: Update Xnet Inventory for valid and invalid Total Inventory
    Given I'm a valid supplier
    And my hotel id is <hotelId>
    And date range is between <startDateShift> days from now and <endDateShift> days from now every day
    And room type is <roomTypeId>
    And total inventory available is <totalInventoryAvailable>
    And rate plan is <ratePlanId>
    And currency is <currency>
    And rate per day is <perDay>
    And extra person fee is <extraPerson>
    When I update Inventory
    Then I should get <response> error

  Examples: Total Inventory validation
    | hotelId | startDateShift | endDateShift | roomTypeId | totalInventoryAvailable | ratePlanId | currency | perDay | extraPerson | response               |
    | 7485    | 1              | 2            | STANDARD   | 5000                    | XHW        | USD      | 100    | 0           | Inventory not in range |
    | 7485    | 1              | 2            | STANDARD   | -20                     | XHW        | USD      | 100    | 0           | Inventory not in range |
    | 7485    | 3              | 3            | STANDARD   | 4999                    | XHW        | USD      | 100    | 0           | No                     |