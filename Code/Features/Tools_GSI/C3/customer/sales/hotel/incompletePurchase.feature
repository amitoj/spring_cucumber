@TOOLS   @c3Hotel
Feature: Incomplete purchase
  Owner: Oleksandr Zelenov

  Background:
    Given C3 application is accessible

  Scenario Outline: RTC-782.Mouseover Dhtml Pop-up - Auth Card Rejected
    Given I login into C3 with username "csrcroz1"
    And I have invalid credit card
    When I search for hotel
    And I process the results page
    And I save reference number from details page
    And I process the details page
    And I process the interstitial page
    And I fill user information
    And I confirm booking
    Then I see billing validation message <message>
    And I see status "30010" in Database purchase_order table
    And I see status "50003" in Database reservation table
    #Status verification
    Given C3 application is accessible
    And I login into C3 with username "csrcroz1"
    And I search for given customer email
    Then I see Auth Card Rejected status for last hotel purchase

  Examples:
    |message|
    |The credit card number, expiration date, or security code you entered does not match what is on file with your card's issuing bank. Your card has not been charged. Please note this price is not guaranteed until the purchase is complete. We apologize for this Hotwire booking error. Your credit card provider may be blocking specific transactions. To complete your booking, we suggest contacting your credit card provider to remove any restrictions.|

