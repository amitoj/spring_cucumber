@TOOLS @ACCEPTANCE
Feature: Hotel Star Rating verification. Checking Hotwire, Expedia ratings, last rating change and rating change fields.
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  @highPriority @BUG
  Scenario: Verify Hotel Star rating. Happy Path.
#  https://jira/jira/browse/HWTL-554
    Given customer hotel purchase with star rating
    And I login into C3 with username "csrcroz1"
    When I search for given customer purchase
    Then I see itinerary details page
    And I see hotel star rating link upon confirmation
    When I open hotel star rating
    Then I see new window with correct star rating
    And hotel star rating change is correct
    And hotel survey information is correct