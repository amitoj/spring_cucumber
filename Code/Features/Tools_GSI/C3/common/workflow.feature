@TOOLS
Feature: Create Workflow access while refunding a Hotel
  Owner:Komarov Igor

  Background:
    Given C3 application is accessible

  @STBL @ACCEPTANCE
  Scenario: Create Workflow access while refunding a Hotel. RTC-3894
   Given customer hotel purchase for refund
   And  I login into C3 with username "csrcroz5"
   Then I search for given customer purchase
   And  I choose service recovery
   And  I choose Test Booking recovery reason and click on create workflow
   Then I create workflow during refund