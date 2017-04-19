Feature: Hotel details cross sells

    Background:
        Given default dataset

    @ACCEPTANCE
    Scenario Outline: deal data is consistent when going from details to cross-selled hotel page
        Given the details cross sell list <state> active
        And the application is accessible
        And I'm searching for a hotel in "<place>"
        And I want to travel between 5 days from now and 7 days from now
        And I request quotes
        And I choose <type> hotels tab on results
        When I choose a hotel result
        And I select <dealNumber> cross-sell deal on details
        And I see expected cross-sell deal

    @US @ACK
    Examples:
        # We don't run this on ROW because the banner always uses the number of 59 while the retail results for intl are always 142. Simulator thing.
        # XSD13=1 single cross sell banner on opaque details.
        | state  | place           | type   | dealNumber  |
        | is not | Las Vegas, NV   | opaque | first       |

    @US @ACK
    Examples:
        | state  | place           | type   | dealNumber  |
        # XSD13=2 table list of cross sells on opaque details.
        | is     | Las Vegas, NV   | opaque | first       |
        # State doesn't matter for retail details page. There's only the left/right scrollable cross sell listing.
        #| is     | Los Angeles, CA | retail | first       |
        | is not | Los Angeles, CA | retail | second      |

    @ROW
    Examples:
        | state  | place           | type   | dealNumber  |
        # XSD13=2 table list of cross sells on opaque details.
        #| is     | Las Vegas, NV   | opaque | first       |
        # State doesn't matter for retail details page. There's only the left/right scrollable cross sell listing.
        #| is     | Los Angeles, CA | retail | first       |
        | is not | Los Angeles, CA | retail | second      |


    @ROW
    Examples:
        | state  | place           | type   | dealNumber  |
        # State doesn't matter for retail details page. There's only the left/right scrollable cross sell listing.
        | is     | Los Angeles, CA | retail | first       |
