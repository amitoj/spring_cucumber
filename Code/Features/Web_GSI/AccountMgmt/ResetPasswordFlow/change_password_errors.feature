@US
Feature: Account change password section

  Background:
    Given default dataset
    Given the application is accessible

  @US @ACCEPTANCE
  Scenario: Validate when confirm password does not match new password an error will occur. RTC-311
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    And I am change my password to 'newqwerty123' and confirm it to 'qwerty123'
    Then I receive immediate "The two passwords need to match." error message


  @ACCEPTANCE
  Scenario: Validate when no confirm password was entered an error will occur. RTC-301
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    And I am change my password to 'newqwerty123' and confirm it to 'null'
    Then I receive immediate "Confirm your password." error message

  @ACCEPTANCE
  Scenario: Validate no password was entered an error will occur. RTC-302
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    And I am change my password to 'null' and confirm it to 'qwerty123'
    Then I receive immediate "Password must contain 6-30 characters and no spaces. It is case-sensitive." error message

  @ACCEPTANCE
  Scenario: Validate that password should not contain spaces. RTC-303
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    And I am change my password to 'qwerty 123' and confirm it to 'qwerty123'
    Then I receive immediate "Password must contain 6-30 characters and no spaces. It is case-sensitive." error message

  @ACCEPTANCE
  Scenario: Validate that password should not be < then 4 characters. RTC-304
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    And I am change my password to '123' and confirm it to '123'
    Then I receive immediate "Password must contain 6-30 characters and no spaces. It is case-sensitive." error message

  @ACCEPTANCE
  Scenario: Validate that password should not be > than 30 characters. RTC-305
    Given I am a new user
    Given I create an account
    And I access my account information
    And I manage my account info
    And I am change my password to 'qwertyuiop123456789QWERTYUIOP11' and confirm it to 'qwertyuiop123456789QWERTYUIOP11'
    Then I receive immediate "Password must contain 6-30 characters and no spaces. It is case-sensitive." error message

  @ACCEPTANCE
  Scenario: Validate that email will be pre-populated on Forgot Password Page if unsuccessful login attempt took place. RTC-294
    Given my name is tkachaeva@hotwire.com and my password is wrongpassword
    And I authenticate myself
    Then I am not authenticated
    When I access to 'Password Assistance' page
    Then Prepopulated email field should be tkachaeva@hotwire.com
