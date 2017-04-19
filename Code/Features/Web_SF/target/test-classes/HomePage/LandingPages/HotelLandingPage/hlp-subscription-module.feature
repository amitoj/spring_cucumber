Feature: Converged Hotel Landing Subscription module.
  Let customer search for hotel rooms from the hotel landing page.

  Background:
    Given default dataset
    Given the application is accessible

  Scenario Outline: Subscription module for domestic converged hotel landing page for non-signed in, non-hotwire user.
    Given  I want to go to the Hotels landing page
    Then I will see the subscription module on the <pos> hotel landing page

  @US @ARCHIVE
  Examples:
    | pos            |
    | domestic       |

  @ROW @ARCHIVE
  Examples:
    | pos            |
    | international  |

  Scenario Outline: Non-signed in user subscribes with new email address.
    Given I want to go to the Hotels landing page
    And I want to subscribe with a <emailType> email and my <zipCode> zipcode
    Then the subscription <state> be successful.

  @US @JANKY
  Examples:
    | emailType | state     | zipCode     |
    | new       | will      | 94111       |

  @US @JANKY
  Examples:
    | emailType | state     | zipCode     |
    | new       | will not  | EC1A 1AA    |
    | saved     | will not  | 94111       |
    | invalid   | will not  | 94111       |

  @ROW
  Examples:
    | emailType | state     | zipCode     |
    | new       | will      | EC1A 1AA    |

  @ROW
  Examples:
    | emailType | state     | zipCode     |
    | new       | will not  | ABCDEFGHIJK |
    | invalid   | will not  | EC1A 1AA    |
    # This is broken on ROW.
    #| saved     | will not  | EC1A 1AA    |
