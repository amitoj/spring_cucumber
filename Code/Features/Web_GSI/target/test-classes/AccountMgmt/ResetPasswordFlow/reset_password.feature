@US
Feature: resetPassword
  Password Assistance section

  Background: 
    Given default dataset
    Given activate car's version tests
    Given the application is accessible

  @US @ACCEPTANCE
  Scenario: Validate that Forgot Password link will take user to Forgot Password page RTC-336
    Given I access to 'Password Assistance' page
    Then I am on password assistance page

  @US @ACCEPTANCE
  Scenario: On the Forgot Password page enter an invalid email RTC-329
    Given I access to 'Password Assistance' page
    Given I am set username to a@b.abc in password assistance page
    Then I receive immediate error message

  @US @ACCEPTANCE
  Scenario: Validate that valid email will not cause an error. RTC-319
    Given I access to 'Password Assistance' page
    Given I am set username to a@b.com in password assistance page
    Then I receive confirmation restore password message

  @US @ACCEPTANCE
  Scenario: Validate that no email entered will cause an error. RTC-320
    Given I access to 'Password Assistance' page
    Given I am set username to null in password assistance page
    Then I receive immediate error message

  @US @ACCEPTANCE @JANKY
  Scenario: Blacklisted user cannot log into the site. RTC-322
    #author VYulun
    Given I access to 'Password Assistance' page
    Given I am set blacklist username in password assistance page
    Then I receive immediate error message

  @US @ACCEPTANCE
  Scenario: Validate that email is not case sensitive. RTC-324
    Given I access to 'Password Assistance' page
    Given I am set username to Qa_ReGrEsSioN@hotwire.com in password assistance page
    Then I receive confirmation restore password message

  @US @ACCEPTANCE @STBL
  Scenario: Check the message when creating an invalid password on the Reset password page. RTC-4355
    Given I access to 'Password Assistance' page
    Given I am set username to qa_regression@hotwire.com in password assistance page
    Then I receive confirmation restore password message
    And I go to reset password page from received email
    Then I am set password to 12 and confirmation password to 12 in password assistance page
    Then I receive immediate "Enter a valid password that contains 6 to 30 characters." error message

  @US @ACCEPTANCE @STBL
  Scenario: Verify that old URL from Hotwire password assistance email will generate an error. RTC-328
    Given I access to 'Password Assistance' page
    Given I am set username to qa_regression@hotwire.com in password assistance page
    Then I receive confirmation restore password message
    Given I access to 'Password Assistance' page
    Given I am set username to qa_regression@hotwire.com in password assistance page
    And I receive confirmation restore password message
    Then I verify old URL from hotwire password assistance email generate an error

  #Cannot login as qa_regression@hotwire.com
  @US @ACCEPTANCE @BUG
  Scenario: Validate that after password changed, there are no associated credit cards on My Accounts page and DB. RTC-328
    Given my name is qa_regression@hotwire.com and my password is hotwire333
    Then I authenticate myself
    And I am authenticated
    When I'm searching for a hotel in "SFO"
    And I want to travel in the near future
    Then I request quotes
    And I choose a hotel result
    Then I book that hotel
    Then I fill credit card and save billing information for logged user
    And I complete purchase with agreements
    Then I access my account information
    And I manage payment info
    Then I verify logged user has saved credit card on my account page
    Then I verify qa_regression@hotwire.com user has saved credit card in DB
    Then I want to logout
    Then I access to 'Password Assistance' page
    And I am set username to qa_regression@hotwire.com in password assistance page
    Then I receive confirmation restore password message
    And I go to reset password page from received email
    Then I am set password to hotwire333 and confirmation password to hotwire333 in password assistance page
    Then I am on home page
    And I access my account information
    And I manage payment info
    Then I verify logged user has no saved credit card on my account page
    Then I verify qa_regression@hotwire.com user has no saved credit card in DB
