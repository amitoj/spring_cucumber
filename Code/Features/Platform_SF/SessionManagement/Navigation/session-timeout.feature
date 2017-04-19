@MOBILE @TEST 
Feature: Navigating to pages after session/search has timed out. Time out simulated with invalid searchId or invalid searchTokenIds.
    Given that a user navigates to a page after session has timed out, then the user should land on the homepage with session time out message.

  Background:
    Given the application is accessible

   Scenario Outline: A user navigates to hotel or car pages after the landing page without first launching a search, they should be redirected to the homepage with session timeout message 

    Given I visit <page> page on <vertical> with invalid session parameters
    Then I'm redirected to the homepage with session timeout message
    
  Examples: page and vertical
      | page             | vertical     |
      | results          | hotel        |
      | details          | hotel        |
      | results          | car          |
      | details          | car          |