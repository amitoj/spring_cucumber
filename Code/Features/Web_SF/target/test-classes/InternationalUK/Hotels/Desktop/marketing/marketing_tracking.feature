@US
Feature: Marketing Tracking

  Background: 
    Given default dataset
    Given the application is accessible

  #Author Cristian Gonzalez Robles
  @ACCEPTANCE @JANKY
  Scenario Outline: RTC-4776:Online Marketing for US site, RTC-4777:Online Marketing for UK site, RTC-4778:Online Marketing for IE site
    Given I navigate to external link <link>
    Given I am a new user
    When I create an account
    Then I verify the values in REGISTRATION_REFERRAL table for "<country>" and <marketingType>
    Given I'm searching for a hotel in "1201 Mason St, San Francisco, CA 94108"
    And I request quotes
    Then I verify the values in SEARCH_REFERRAL table for "<country>" and <marketingType>
    Given I choose a hotel result
    And I book selected hotel
    And I complete a hotel as user a quote
    Then I verify the values in PURCHASE_REFERRAL table for "<country>" and <marketingType>

    Examples: 
      | link                            | country | marketingType |
      | /index.jsp?sid=S1&bid=B1&fid=F1 | US      | ONLINE        |
      | /UK?sid=S2&bid=B2&fid=F2        | UK      | ONLINE        |
      #| /IE?sid=S3&bid=B3&fid=F3        | IE      | ONLINE        |

  #Author Juan Hernandez Iniguez
  @ACCEPTANCE @JANKY
  Scenario Outline: RTC-4779:DB Marketing for US site, RTC-4780:DB Marketing for UK site, RTC-4781:DB Marketing for IE site
    Given I navigate to external link <link>
    Given I am a new user
    When I create an account
    Then I verify the values in REGISTRATION_REFERRAL table for "<country>" and <marketingType>
    Given I'm searching for a hotel in "1201 Mason St, San Francisco, CA 94108"
    And I request quotes
    Then I verify the values in SEARCH_REFERRAL table for "<country>" and <marketingType>
    Given I choose a hotel result
    And I book selected hotel
    And I complete a hotel as user a quote
    Then I verify the values in PURCHASE_REFERRAL table for "<country>" and <marketingType>

    Examples: 
      | link                                      | country | marketingType |
      | /index.jsp?nid=N-1&vid=V-1&did=D-1&fid=F1 | US      | DB            |
      | /UK?nid=N-2&vid=V-2&did=D-2&fid=F2        | UK      | DB            |
      #| /IE?nid=N-3&vid=V-3&did=D-3&fid=F3        | IE      | DB            |

  #Author Cristian Gonzalez Robles
  @ACCEPTANCE @JANKY
  Scenario Outline: RTC-4782:Offline Marketing for US site, RTC-4783:Offline Marketing for UK site, RTC-4784:Offline Marketing for IE site
    Given I navigate to external link <link>
    Given I am a new user
    When I create an account
    Then I verify the values in REGISTRATION_REFERRAL table for "<country>" and <marketingType>
    Given I'm searching for a hotel in "1201 Mason St, San Francisco, CA 94108"
    And I request quotes
    Then I verify the values in SEARCH_REFERRAL table for "<country>" and <marketingType>
    Given I choose a hotel result
    And I book selected hotel
    And I complete a hotel as user a quote
    Then I verify the values in PURCHASE_REFERRAL table for "<country>" and <marketingType>

    Examples: 
      | link                            | country | marketingType |
      | /index.jsp?iid=I1&pid=P1&fid=F1 | US      | OFFLINE       |
      | /UK?iid=I2&pid=P2&fid=F2        | UK      | OFFLINE       |
      #| /IE?iid=I3&pid=P3&fid=F3        | IE      | OFFLINE       |

  #Author Juan Hernandez Iniguez
  @ACCEPTANCE @JANKY
  Scenario Outline: RTC-4785:Affiliate Marketing for US site, RTC-4786:Affiliate Marketing for UK site, RTC-4787:Affiliate Marketing for IE site
    Given I navigate to external link <link>
    Given I am a new user
    When I create an account
    Then I verify the values in REGISTRATION_REFERRAL table for "<country>" and <marketingType>
    Given I'm searching for a hotel in "1201 Mason St, San Francisco, CA 94108"
    And I request quotes
    Then I verify the values in SEARCH_REFERRAL table for "<country>" and <marketingType>
    Given I choose a hotel result
    And I book selected hotel
    And I complete a hotel as user a quote
    Then I verify the values in PURCHASE_REFERRAL table for "<country>" and <marketingType>

    Examples: 
      | link                                                               | country | marketingType |
      | /index.jsp?siteID=g4Ikhy632oI-567fghrty567iuyg761111&bid=B1&fid=F1 | US      | AFFILIATE     |
      | /UK?siteID=g4Ikhy632oI-567fghrty567iuyg761112&bid=B2&fid=F2        | UK      | AFFILIATE     |
      #| /IE?siteID=g4Ikhy632oI-567fghrty567iuyg761113&bid=B3&fid=F3        | IE      | AFFILIATE     |
