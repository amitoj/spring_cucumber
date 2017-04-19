@US
Feature: Partner redirection to Hotwire
    When partner customers click on a Hotwire result from partner site, they will be taken to Hotwire and
    see either the partner's badge on Hotwire or the deal they looking for on the partner's site.

    Background:
      Given default dataset
      Given the application is accessible

    Scenario Outline: ShopAtHome partner image is visible in all verticals
      Given I am directed by ShopAtHome to Hotwire
      When I want to go to the <vertical> landing page
      Then I will see the ShopAtHome banner

    Examples:
      | vertical   |
      #| Hotels     |
      | Cars       |
      | Flights    |
      | Vacations  |
    @JANKY
    Scenario:  User directed from Kayak to Hotwire gets the closest deal from Hotwire search
      Given I am directed by Kayak to Hotwire
      Then I will see "Here's your deal" in the results
