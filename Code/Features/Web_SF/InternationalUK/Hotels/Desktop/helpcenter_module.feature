@US
Feature: Help Centre module
  Owner: Cristian Gonzalez Robles

  Background:
    Given default dataset
    Given the application is accessible

  @ACCEPTANCE @STBL
  Scenario Outline: Help Centre Module. RTC-4841 (UK), RTC-4842 (IE), RTC-4843 (AU), RTC-4844 (NZ), RTC-4845 (NO), RTC-4846 (DK), RTC-4847 (SE),
    Given I navigate to external link <countryExternalLink>
    Then I verify the email message within Hotwire Customer Care module for country "<country>"
    Then I verify the hours of availability message within Hotwire Customer Care module for country "<country>"
    Then I verify the phone contacts within Hotwire Customer Care module for country "<country>"

    Examples:
      | countryExternalLink | country        |
      | /uk/help/send-email | United Kingdom |
      | /ie/help/send-email | Ireland        |
      | /se/help/send-email | Sverige        |
      | /no/help/send-email | Norge          |
      | /dk/help/send-email | Danmark        |
      | /au/help/send-email | Australia      |
      | /nz/help/send-email | New Zealand    |
      | /de/help/send-email | Deutschland    |
      | /hk/help/send-email | Hong Kong      |
      | /sg/help/send-email | Singapore      |
      | /mx/help/send-email | México         |
