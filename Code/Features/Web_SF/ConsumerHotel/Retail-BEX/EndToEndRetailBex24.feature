@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 24
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
   And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
    And i use AFF link with siteID <siteID>
    And I use OPM T1 link with BID <BID> and SID <SID>
   And i book on BEX
   And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | SID3       | BID3          |  CMID       |  ACID       |  KID       |  MID       |              siteID                      | BID       | SID       | transactionNumber |
    | Chicago, IL         | 3              | 5            |     S526   | B90016413     |  K3605636	 |   M03       |AC90000	    |CM900020    |   OOTtr9mlaCk-FO60Ohp0gmvfhTagtD1rsg     | S270      | B378436   | 24                |
    | Irvine, CA          | 5              | 6            |     S526   |	B90016427  |	K3661863 |	M03        |AC90000	    |CM900024    |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     | S270	     |B378435    | 24                |
    | Springfield, IL     |  6             | 7            |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     |   S141	 |B377601    | 24                |
    | Houston, TX         |  7             | 8            |     S527   |	B90016417  |	K3662873 |	M03        |AC90000	    |CM900020    |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     |  S174	 |B379716    | 24                |
    | Phoenix, AZ         |  6             | 7            |     S527   |	B90016408  |	K3662830 |	M03        |AC90000	    |CM900019    |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     |   S174	 |B379593    | 24                |
    | Jacksonville, FL    |   5            | 6            |     S527   |	B90016459  |	K3663340 |	M08        |AC90000	    |CM900035    |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     |    S174	 |B379592    | 24                |
    | Miami, FL           |  4             | 5            |     S526   |	B90016406  |	K3605626 |	M03        |AC90000	    |CM900039    |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     |   S3	     |B379816    | 24                |
    | Toledo, OH          |  3             | 4            |     S526   |	B90016430  |	K3663144 |	M03        |AC90000	    |CM900025    |   UO85MF6im/8-2YrhP/uhuf7HvXtdVqiAxQ     |    S3	 |B379815    | 24                |
    | Detroit, MI         | 2              | 3            |     S526   |	B90016419  |	K3662889 |	M03        |AC90000	    |CM900021    |   AysPbYF8vuM-f17m*Tcb*V5htUmQ3g6lMw     |  S3       |B379803    | 24                |
    | Chicago, IL         | 3              | 5            |     S527   |	B90016426  |	K3599862 |	M03        |AC90000	    |CM900054    |   InyfooKypHI-ax9YVHzmSRA0ldsoBZ7gfQ     |    S358	 |B377857    | 24                |
    | Irvine, CA          | 5              | 6            |     S526   |	B20121290  |	K3642284 |	M03        |AC90000	    |CM900051    |   vl0mfKZlvKU-e2eXrIzrNczqfv1EuLNt*g     |     S358  |B377702    | 24                |
    | Springfield, IL     |  6             | 7            |     S11    |	B90016430  |	K3663128 |	M03        |AC90000	    |CM900025    |   HBLvzQS2RdU-o0qkS119P0hw8c9GepJgVQ     |    S445	 |B379795    | 24                |
    | Houston, TX         |  7             | 8            |     S526   |	B90016400  |	K3605571 |	M03        |AC90000	    |CM900063    |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     |   S445	 |B379371    | 24                |
    | Phoenix, AZ         |  6             | 7            |     S526   |	B90016512  |	K3940849 |	M03        |AC90000	    |CM900061    |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     |     S445  |B379265    | 24                |
    | Jacksonville, FL    |   5            | 6            |     S11    |	B90016425  |	K3599861 |	M08        |AC90000	    |CM900029    |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     |    S69	 |B378436    | 24                |
    | Miami, FL           |  4             | 5            |     S526   |	B90016404  |	K3605621 |	M03        |AC90000	    |CM900019    |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     |    S69	 |B378435    | 24                |
    | Toledo, OH          |  3             | 4            |     S526   |	B90016512  |	K3940819 |	M03        |AC90000	    |CM900061    |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     |    S461	 |B379835    | 24                |
    | Detroit, MI         | 2              | 3            |     S526   |	B90016413  |	K3605636 |	M03        |AC90000	    |CM900020    |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     |     S461  |	B379832  | 24                |
    | Chicago, IL         | 3              | 5            |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     |   S461	 |B379827    | 24                |