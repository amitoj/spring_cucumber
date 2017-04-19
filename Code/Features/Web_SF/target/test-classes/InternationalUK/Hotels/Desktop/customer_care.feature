@US
Feature: Customer care module contains phone number and reference number.
  Owner: Intl team

  Background:
    Given default dataset
    And the application is accessible

  # Help center page is now on drupal.
  @JANKY
  Scenario Outline: Help Center customer support hours are localized
    Given I'm from "<POS>" POS
    When I access Help Center
    Then I see "<POS>" support is available "<time>"

  Examples:
    | POS            | time       |
    | United Kingdom | 24 / 7     |
    | Ireland        | 24 / 7     |
    | Sverige        | 9am - 5pm  |
    | Norge          | 9am - 5pm  |
    | Danmark        | 9am - 5pm  |
    | Australia      | 24 / 7     |
    | New Zealand    | 24 / 7     |
    | Deutschland    | 9am - 11pm |
    | Hong Kong      | 24 / 7     |
    | Singapore      | 24 / 7     |
    | México         | 2am - 10pm |

  @MAP
  Scenario Outline: Customer care module on mapped results
    Given I'm from "<site>" POS
    When I'm searching for a hotel in "<location>"
    And I want to travel between 5 days from now and 7 days from now
    And I request quotes
    Then I see customer care phone number above the map is "<phoneNumber>"
    When I choose a hotel result
    Then I see customer care phone number above the details is "<phoneNumber>"

  Examples:
    | site           | location                                   | phoneNumber    |
    | United Kingdom | London, England, United Kingdom            | 0808 234 5903  |
    | Ireland        | Dublin, Ireland                            | 1 800 760738   |
    | Sverige        | Stockholm, Sverige                         | 020 797 237    |
    | Norge          | Oslo, Norge                                | 80 01 72 28    |
    | Danmark        | København, Denmark                         | 80 25 00 72    |
    | Australia      | Sydney, Australia                          | 1 800 306 217  |
    | New Zealand    | Auckland, New Zealand                      | 0800 452 690   |
    | Deutschland    | Berlin, Deutschland                        | 0800 723 5637  |
    | Hong Kong      | New York, United States of America         | 800 933 475    |
    | Singapore      | Singapore, Singapore                       | 800 120 6272   |
    | México         | Ciudad de México, Distrito Federal, México | 001-8554643751 |
