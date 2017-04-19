@TOOLS @customerService
Feature: Cancellation, confirmation and resending for hotel and car deals
  Let CSR search for a customer and do refund.
  Owner: Oleksandr Zelenov


  @LIMITED  @criticalPriority
  Scenario Outline: Purchase cancellation RTC-963,RTC-1171,RTC-1777,RTC-1742
    Given C3 application is accessible
    Given customer <vertical> purchase for cancel
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    When  I cancel itinerary
    Then  I see "itinerary has been cancell?ed in the GDS" in confirmation message
    And   I see purchase status is "Cancelled"

  Examples:
    | vertical |
    | hotel    |
    | car      |
#    | air      |

  Scenario Outline: Purchase cancellation with case notes view link verification. RTC-963,RTC-1171,RTC-1777,RTC-1742
    #Sergey Shubey
    Given C3 application is accessible
    Given customer <vertical> purchase for cancel
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer purchase
    When  I cancel itinerary
    Then  I see "itinerary has been cancell?ed in the GDS" in confirmation message
    And   I see purchase status is "Cancelled"
    When  I get <vertical> past booking
    Then  I see View link in "Case attached" column for purchase

  Examples:
    | vertical |
    | air      |
    | hotel    |
    | car      |


  @ACCEPTANCE @STBL
  Scenario Outline: 'View' link in Case column is absent for last domestic purchase RTC-1741
    Given C3 application is accessible
    Given customer <vertical> purchase without Case
    And   I login into C3 with username "csrcroz1"
    And   I search for given customer email
    When  I get <vertical> past booking
    Then  I don't see View link in "Case attached" column for purchase

  Examples:
    | vertical |
    | air      |
    | hotel    |
    | car      |