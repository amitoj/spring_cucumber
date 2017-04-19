@TOOLS @c3Management
Feature: Different customer types verification
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

#Matches RTC-783 RTC-1747, 1748 and RTC-1706 from silktest suite
#discussed with A.Churikov

  @criticalPriority
  Scenario Outline: Customer info verification. RTC-1644, 1654, 4544, 4545
    Given <customerType> customer purchase
    Given I login into C3 with username "csrcroz1"
    And I search for given customer purchase
    Then customer type, watermark, breadcrumbs are correct

  @ACCEPTANCE
  Examples:
    | customerType  |
    | guest         |
    | non-express   |
    | ex-express    |
    | express       |
    | express-elite |
    | partner       |
