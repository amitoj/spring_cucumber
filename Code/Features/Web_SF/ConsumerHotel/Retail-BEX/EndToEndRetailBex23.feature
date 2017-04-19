@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 23
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
   And I want to travel between <startDateShift> days from now and <endDateShift> days from now
   And i use DBM link with NID <NID>, VID <VID> and DID <DID>
    And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
    And i use AFF link with siteID <siteID>
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
   And i book on BEX
   And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift |NID           |  VID                |DID       | SID3       | BID3          |  CMID       |  ACID       |  KID       |  MID        |              siteID                   | transactionNumber |   NID2      |  VID2       |DID2       |
    | Chicago, IL         | 3              | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1476     |     S526   | B90016413     |  K3605636	 |   M03       |AC90000	    |CM900020     |   OOTtr9mlaCk-FO60Ohp0gmvfhTagtD1rsg  | 23                |   N-HSS	   |V-HSS-V1	 |D1382      |
    | Irvine, CA          | 5              | 6            |  N-ISR-C	 |V-ISR-C-F1D		   |D0461     |     S526   |	B90016427  |	K3661863 |	M03        |AC90000	    |CM900024     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ  | 23                |   N-CON-H   |V-CON-H-V3	 |D0909      |
    | Springfield, IL     |  6             | 7            |  N-ISRH-RFL  |V-ISRH-RFL-V1	       |D1183     |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg  | 23                |   N-CON-H   |V-CON-H-V3	 |D0916      |
    | Houston, TX         |  7             | 8            |  N-TW-ISRH	 |V-TW-ISRH-F3A		   |D0463     |     S527   |	B90016417  |	K3662873 |	M03        |AC90000	    |CM900020     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg  | 23                |   N-HSS	   |V-HSS-V1	 |D1333      |
    | Phoenix, AZ         |  6             | 7            |  N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1481     |     S527   |	B90016408  |	K3662830 |	M03        |AC90000	    |CM900019     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw  | 23                |   N-HSS	   |V-HSS-V1	 |D1334      |
    | Jacksonville, FL    |   5            | 6            |  N-HFA-545	 |V-HFA-545-V1SS-T5B   |D1480     |     S527   |	B90016459  |	K3663340 |	M08        |AC90000	    |CM900035     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q  | 23                |   N-CON-H   |V-CON-H-V3	 |D0913      |
    | Miami, FL           |  4             | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T5C   |D1475     |     S526   |	B90016406  |	K3605626 |	M03        |AC90000	    |CM900039     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ  | 23                |   N-REM-H   |V-REM-H-V2	 |D0913      |
    | Toledo, OH          |  3             | 4            |  N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1491     |     S526   |	B90016430  |	K3663144 |	M03        |AC90000	    |CM900025     |   UO85MF6im/8-2YrhP/uhuf7HvXtdVqiAxQ  | 23                |    N-HSS	   |V-HSS-V1	 |D1382      |
    | Detroit, MI         | 2              | 3            |  N-HFA-545	 |V-HFA-545-V1SS-T3A   |D1474     |     S526   |	B90016419  |	K3662889 |	M03        |AC90000	    |CM900021     |   AysPbYF8vuM-f17m*Tcb*V5htUmQ3g6lMw  | 23                |    N-CON-H   |V-CON-H-V3	 |D0909      |
    | Chicago, IL         | 3              | 5            |  N-ISR-C	 |V-ISR-C-F6D		   |D0461     |     S527   |	B90016426  |	K3599862 |	M03        |AC90000	    |CM900054     |   InyfooKypHI-ax9YVHzmSRA0ldsoBZ7gfQ  | 23                |    N-CON-H   |V-CON-H-V3	 |D0916      |
    | Irvine, CA          | 5              | 6            |  N-TW-ISRA	 |V-TW-ISRA-F3		   |D0303     |     S526   |	B20121290  |	K3642284 |	M03        |AC90000	    |CM900051     |   vl0mfKZlvKU-e2eXrIzrNczqfv1EuLNt*g  | 23                |    N-HSS	   |V-HSS-V1	 |D1333      |
    | Springfield, IL     |  6             | 7            |  N-ISR-C	 |V-ISR-C-F5A		   |D0460     |     S11    |	B90016430  |	K3663128 |	M03        |AC90000	    |CM900025     |   HBLvzQS2RdU-o0qkS119P0hw8c9GepJgVQ  | 23                |    N-HSS	   |V-HSS-V1	 |D1334      |
    | Houston, TX         |  7             | 8            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1475     |     S526   |	B90016400  |	K3605571 |	M03        |AC90000	    |CM900063     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg  | 23                |    N-CON-H   |V-CON-H-V3	 |D0913      |
    | Phoenix, AZ         |  6             | 7            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1485     |     S526   |	B90016512  |	K3940849 |	M03        |AC90000	    |CM900061     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw  | 23                |    N-REM-H   |V-REM-H-V2	 |D0913      |
    | Jacksonville, FL    |   5            | 6            |  N-ISR-C	 |V-ISR-C-F2D		   |D0065     |     S11    |	B90016425  |	K3599861 |	M08        |AC90000	    |CM900029     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q  | 23                |    N-HSS	   |V-HSS-V1	 |D1382      |
    | Miami, FL           |  4             | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T5D   |D1479     |     S526   |	B90016404  |	K3605621 |	M03        |AC90000	    |CM900019     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ  | 23                |    N-CON-H   |V-CON-H-V3	 |D0909      |
    | Toledo, OH          |  3             | 4            |  N-ISR-C	 |V-ISR-C-F7D		   |D0461     |     S526   |	B90016512  |	K3940819 |	M03        |AC90000	    |CM900061     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ  | 23                |    N-CON-H   |V-CON-H-V3	 |D0916      |
    | Detroit, MI         | 2              | 3            |  N-ISR-C	 |V-ISR-C-F2D		   |D0206     |     S526   |	B90016413  |	K3605636 |	M03        |AC90000	    |CM900020     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg  | 23                |    N-HSS	   |V-HSS-V1	 |D1333      |
    | Chicago, IL         | 3              | 5            |  N-ISR-C	 |V-ISR-C-F6A		   |D0461     |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg  | 23                |    N-HSS	   |V-HSS-V1	 |D1334      |