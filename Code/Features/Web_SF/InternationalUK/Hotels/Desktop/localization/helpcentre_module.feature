Feature: Help Centre module

  Background: 
    Given default dataset
    Given the application is accessible

  #Owner: Intl team
  @ROW @ACCEPTANCE
  Scenario Outline: Verify Call and book module on hotel results page. RTC-4855, RTC-4856, RTC-4857, RTC-4858, RTC-4859, RTC-4860, RTC-4861.
    Given I'm from "<country>" POS
    And I'm searching for a hotel in "London, United Kingdom"
    And I want to travel between 20 days from now and 22 days from now
    When I request quotes
    Then I should see hotel search results page
    And I should see Call and book module for "<country>" on results page

    Examples: For non-city searches
      | country        |
      | United Kingdom |
      #| Ireland        |
      #| Sverige        |
      #| Norge          |
      #| Danmark        |
      #| Australia      |
      #| New Zealand    |
      #| Deutschland    |
      #| Hong Kong      |
      #| Singapore      |
      #| México         |

  #Owner: Cristian Gonzalez Robles
  #OWR16685
  @US @ACCEPTANCE @BUG
  Scenario Outline: Help Centre Module. RTC-4841 (UK), RTC-4842 (IE), RTC-4843 (AU), RTC-4844 (NZ), RTC-4845 (NO), RTC-4846 (DK), RTC-4847 (SE),
    Given I navigate to external link <countryExternalLink>
    Then I verify the email message within Hotwire Customer Care module for country "<country>"
    Then I verify the hours of availability message within Hotwire Customer Care module for country "<country>"
    Then I verify the phone contacts within Hotwire Customer Care module for country "<country>"

    Examples: 
      | countryExternalLink | country        |
      | /uk/help/send-email | United Kingdom |
      #| /ie/help/send-email | Ireland        |
      #| /se/help/send-email | Sverige        |
      #| /no/help/send-email | Norge          |
      #| /dk/help/send-email | Danmark        |
      #| /au/help/send-email | Australia      |
      #| /nz/help/send-email | New Zealand    |
      #| /de/help/send-email | Deutschland    |
      #| /hk/help/send-email | Hong Kong      |
      #| /sg/help/send-email | Singapore      |
      #| /mx/help/send-email | México         |
