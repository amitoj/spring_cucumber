@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 6
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
   And I want to travel between <startDateShift> days from now and <endDateShift> days from now
   And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
   And i use DBM link with NID <NID>, VID <VID> and DID <DID>
   And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
   And i use AFF link with siteID <siteID>
   And i book on BEX
   And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift |NID           |  VID                |DID       | SID3       | BID3          |  CMID       |  ACID       |  KID       |  MID       |  SID4       | BID4       |  CMID2       |  ACID2       |  KID2       |  MID2       |              siteID                      | transactionNumber |
    | Chicago, IL         | 3              | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1476     |     S526   | B90016413     |  K3605636	 |   M03       |AC90000	    |CM900020    |    S11	   | B20114428  |  K3598361	   |   M01        |AC20017	    |CM200000     |   OOTtr9mlaCk-FO60Ohp0gmvfhTagtD1rsg     | 6                 |
    | Irvine, CA          | 5              | 6            |  N-ISR-C	 |V-ISR-C-F1D		   |D0461     |     S526   |	B90016427  |	K3661863 |	M03        |AC90000	    |CM900024    |    S526	   |B90016400	|K3605571	   |M03	          |AC90001	    |CM900070     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     | 6                 |
    | Springfield, IL     |  6             | 7            |  N-ISRH-RFL  |V-ISRH-RFL-V1	       |D1183     |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S11	   |B30140468	|K3658591	   |M03	          |AC30000	    |CM300000     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     | 6                 |
    | Houston, TX         |  7             | 8            |  N-TW-ISRH	 |V-TW-ISRH-F3A		   |D0463     |     S527   |	B90016417  |	K3662873 |	M03        |AC90000	    |CM900020    |    S192	   |B30140564	|K3662126	   |M03	          |AC30000	    |CM300013     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     | 6                 |
    | Phoenix, AZ         |  6             | 7            |  N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1481     |     S527   |	B90016408  |	K3662830 |	M03        |AC90000	    |CM900019    |    S11	   |B30140490	|K3939356	   |M03	          |AC30000	    |CM300000     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     | 6                 |
    | Jacksonville, FL    |   5            | 6            |  N-HFA-545	 |V-HFA-545-V1SS-T5B   |D1480     |     S527   |	B90016459  |	K3663340 |	M08        |AC90000	    |CM900035    |    S362	   |B20114434	|K3598946	   |M03	          |AC20000	    |CM500024     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     | 6                 |
    | Miami, FL           |  4             | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T5C   |D1475     |     S526   |	B90016406  |	K3605626 |	M03        |AC90000	    |CM900039    |    S11	   |B20114407	|K3598130	   |M03	          |AC20016	    |CM200000     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     | 6                 |
    | Toledo, OH          |  3             | 4            |  N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1491     |     S526   |	B90016430  |	K3663144 |	M03        |AC90000	    |CM900025    |    S11	   |B20114407	|K3598130	   |M02	          |AC20000	    |CM200000     |   UO85MF6im/8-2YrhP/uhuf7HvXtdVqiAxQ     | 6                 |
    | Detroit, MI         | 2              | 3            |  N-HFA-545	 |V-HFA-545-V1SS-T3A   |D1474     |     S526   |	B90016419  |	K3662889 |	M03        |AC90000	    |CM900021    |    S11	   |B90016381	|K3600806	   |M03	          |AC90003	    |CM900010     |   AysPbYF8vuM-f17m*Tcb*V5htUmQ3g6lMw     | 6                 |
    | Chicago, IL         | 3              | 5            |  N-ISR-C	 |V-ISR-C-F6D		   |D0461     |     S527   |	B90016426  |	K3599862 |	M03        |AC90000	    |CM900054    |    S192	   |B30140568	|K3662168	   |M03	          |AC30000	    |CM300013     |   InyfooKypHI-ax9YVHzmSRA0ldsoBZ7gfQ     | 6                 |
    | Irvine, CA          | 5              | 6            |  N-TW-ISRA	 |V-TW-ISRA-F3		   |D0303     |     S526   |	B20121290  |	K3642284 |	M03        |AC90000	    |CM900051    |    S11	   |B20121569	|K3665234	   |M03	          |AC20000	    |CM200203     |   vl0mfKZlvKU-e2eXrIzrNczqfv1EuLNt*g     | 6                 |
    | Springfield, IL     |  6             | 7            |  N-ISR-C	 |V-ISR-C-F5A		   |D0460     |     S11    |	B90016430  |	K3663128 |	M03        |AC90000	    |CM900025    |    S11	   |B20121413	|K3663688	   |M01	          |AC20000	    |CM200000     |   HBLvzQS2RdU-o0qkS119P0hw8c9GepJgVQ     | 6                 |
    | Houston, TX         |  7             | 8            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1475     |     S526   |	B90016400  |	K3605571 |	M03        |AC90000	    |CM900063    |    S11	   |B20136790	|K3818809	   |M08	          |AC20000	    |CM200258     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     | 6                 |
    | Phoenix, AZ         |  6             | 7            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1485     |     S526   |	B90016512  |	K3940849 |	M03        |AC90000	    |CM900061    |    S11	   |B30140782	|K3666914	   |M03	          |AC30000	    |CM300016     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     | 6                 |
    | Jacksonville, FL    |   5            | 6            |  N-ISR-C	 |V-ISR-C-F2D		   |D0065     |     S11    |	B90016425  |	K3599861 |	M08        |AC90000	    |CM900029    |    S192	   |B20121654	|K3666179	   |M01	          |AC20000	    |CM200000     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     | 6                 |
    | Miami, FL           |  4             | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T5D   |D1479     |     S526   |	B90016404  |	K3605621 |	M03        |AC90000	    |CM900019    |    S192	   |B20114433	|K3597506	   |M02	          |AC20000	    |CM200000     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     | 6                 |
    | Toledo, OH          |  3             | 4            |  N-ISR-C	 |V-ISR-C-F7D		   |D0461     |     S526   |	B90016512  |	K3940819 |	M03        |AC90000	    |CM900061    |    S527	   |B90016427	|K3661863	   |M03	          |AC90009	    |CM900054     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     | 6                 |
    | Detroit, MI         | 2              | 3            |  N-ISR-C	 |V-ISR-C-F2D		   |D0206     |     S526   |	B90016413  |	K3605636 |	M03        |AC90000	    |CM900020    |    S527	   |B90016423	|K3600561	   |M03	          |AC90009	    |CM900045     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     | 6                 |
    | Chicago, IL         | 3              | 5            |  N-ISR-C	 |V-ISR-C-F6A		   |D0461     |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S192	   |B20137247	|K3939064	   |M08	          |AC20000	    |CM200271     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     | 6                 |