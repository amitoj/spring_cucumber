@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 1
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
   And I want to travel between <startDateShift> days from now and <endDateShift> days from now
   And I use OPM T1 link with BID <BID> and SID <SID>
   And i use OPM T2 link with BID <BID2> and SID <SID2>
   And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
   And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
   And i book on BEX
   And i save the search details finishing transaction <transactionNumber>


  Examples:
    | destinationLocation | startDateShift | endDateShift | BID       | SID       | BID2       |  SID2       | SID3       | BID3       |  CMID       |  ACID       |  KID       |  MID       |  SID4       | BID4       |  CMID2       |  ACID2       |  KID2       |  MID2       | transactionNumber |
    | Chicago, IL         | 3              | 5            | S270      | B378436   |   S174	   |  B265180    |     S526	  | B90016413  |  K3605636	 |   M03       |AC90000	    |CM900020    |    S11	   | B20114428  |  K3598361	   |   M01        |AC20017	    |CM200000     | 1                 |
    | Irvine, CA          | 5              | 6            | S270	  |B378435    |   S174	   |  B265180    |     S526   |	B90016427  |	K3661863 |	M03        |AC90000	    |CM900024    |    S526	   |B90016400	|K3605571	   |M03	          |AC90001	    |CM900070     | 1                 |
    | Springfield, IL     |  6             | 7            |   S141	  |B377601    |    S174    |  B265180    |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S11	   |B30140468	|K3658591	   |M03	          |AC30000	    |CM300000     | 1                 |
    | Houston, TX         |  7             | 8            |  S174	  |B379716    |    S322	   |  B322681    |     S527   |	B90016417  |	K3662873 |	M03        |AC90000	    |CM900020    |    S192	   |B30140564	|K3662126	   |M03	          |AC30000	    |CM300013     | 1                 |
    | Phoenix, AZ         |  6             | 7            |   S174	  |B379593    |     S322   |  B378048    |     S527   |	B90016408  |	K3662830 |	M03        |AC90000	    |CM900019    |    S11	   |B30140490	|K3939356	   |M03	          |AC30000	    |CM300000     | 1                 |
    | Jacksonville, FL    |   5            | 6            |    S174	  |B379592    |       S322 |  B378638    |     S527   |	B90016459  |	K3663340 |	M08        |AC90000	    |CM900035    |    S362	   |B20114434	|K3598946	   |M03	          |AC20000	    |CM500024     | 1                 |
    | Miami, FL           |  4             | 5            |   S3	  |B379816    |      S174  |  B379716    |     S526   |	B90016406  |	K3605626 |	M03        |AC90000	    |CM900039    |    S11	   |B20114407	|K3598130	   |M03	          |AC20016	    |CM200000     | 1                 |
    | Toledo, OH          |  3             | 4            |    S3	  |B379815    |       S286 |  B3796102   |     S526   |	B90016430  |	K3663144 |	M03        |AC90000	    |CM900025    |    S11	   |B20114407	|K3598130	   |M02	          |AC20000	    |CM200000     | 1                 |
    | Detroit, MI         | 2              | 3            |  S3       |B379803    |   S174	   |  B265180    |     S526   |	B90016419  |	K3662889 |	M03        |AC90000	    |CM900021    |    S11	   |B90016381	|K3600806	   |M03	          |AC90003	    |CM900010     | 1                 |
    | Chicago, IL         | 3              | 5            |    S358	  |B377857    |    S174    |  B265180    |     S527   |	B90016426  |	K3599862 |	M03        |AC90000	    |CM900054    |    S192	   |B30140568	|K3662168	   |M03	          |AC30000	    |CM300013     | 1                 |
    | Irvine, CA          | 5              | 6            |     S358  |B377702    |    S322	   |  B322681    |     S526   |	B20121290  |	K3642284 |	M03        |AC90000	    |CM900051    |    S11	   |B20121569	|K3665234	   |M03	          |AC20000	    |CM200203     | 1                 |
    | Springfield, IL     |  6             | 7            |    S445	  |B379795    |     S322   |  B378048    |     S11    |	B90016430  |	K3663128 |	M03        |AC90000	    |CM900025    |    S11	   |B20121413	|K3663688	   |M01	          |AC20000	    |CM200000     | 1                 |
    | Houston, TX         |  7             | 8            |   S445	  |B379371    |       S322 |  B378638    |     S526   |	B90016400  |	K3605571 |	M03        |AC90000	    |CM900063    |    S11	   |B20136790	|K3818809	   |M08	          |AC20000	    |CM200258     | 1                 |
    | Phoenix, AZ         |  6             | 7            |     S445  |B379265    |      S174  |  B379716    |     S526   |	B90016512  |	K3940849 |	M03        |AC90000	    |CM900061    |    S11	   |B30140782	|K3666914	   |M03	          |AC30000	    |CM300016     | 1                 |
    | Jacksonville, FL    |   5            | 6            |    S69	  |B378436    |       S286 |  B3796102   |     S11    |	B90016425  |	K3599861 |	M08        |AC90000	    |CM900029    |    S192	   |B20121654	|K3666179	   |M01	          |AC20000	    |CM200000     | 1                 |
    | Miami, FL           |  4             | 5            |    S69	  |B378435    |  S174	   |  B265180    |     S526   |	B90016404  |	K3605621 |	M03        |AC90000	    |CM900019    |    S192	   |B20114433	|K3597506	   |M02	          |AC20000	    |CM200000     | 1                 |
    | Toledo, OH          |  3             | 4            |    S461	  |B379835    |   S174     |  B265180    |     S526   |	B90016512  |	K3940819 |	M03        |AC90000	    |CM900061    |    S527	   |B90016427	|K3661863	   |M03	          |AC90009	    |CM900054     | 1                 |
    | Detroit, MI         | 2              | 3            |     S461  |	B379832   |   S322	   |  B322681    |     S526   |	B90016413  |	K3605636 |	M03        |AC90000	    |CM900020    |    S527	   |B90016423	|K3600561	   |M03	          |AC90009	    |CM900045     | 1                 |
    | Chicago, IL         | 3              | 5            |   S461	  |B379827    |    S322    |  B378048    |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S192	   |B20137247	|K3939064	   |M08	          |AC20000	    |CM200271     | 1                 |

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 2
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use DBM link with NID <NID>, VID <VID> and DID <DID>
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
    And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
    And I use OPM T1 link with BID <BID> and SID <SID>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | NID          |  VID                |DID       |  NID2      |  VID2       |DID2       |BID       | SID       | SID3       | BID3          |  CMID       |  ACID       |  KID       |  MID       |  transactionNumber|
    | Chicago, IL         | 3              | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1476     |  N-HSS	   |V-HSS-V1	 |D1382      |S270      | B378436   |     S526   | B90016413     |  K3605636   |   M03       |AC90000	  |CM900020    |    2                 |
    | Irvine, CA          | 5              | 6            |   N-ISR-C	 |V-ISR-C-F1D		   |D0461     |  N-CON-H   |V-CON-H-V3	 |D0909      |S270	    |B378435    |     S526   |	B90016427    |	K3661863   |	M03      |AC90000	  |CM900024    |    2                 |
    | Springfield, IL     |  6             | 7            |   N-ISRH-RFL |V-ISRH-RFL-V1	       |D1183     |  N-CON-H   |V-CON-H-V3	 |D0916      |  S141    |B377601    |     S526   |	B90016425    |	K3599861   |	M03      |AC90000	  |CM900054    |    2                 |
    | Houston, TX         |  7             | 8            |   N-TW-ISRH	 |V-TW-ISRH-F3A		   |D0463     |  N-HSS	   |V-HSS-V1	 |D1333      | S174	    |B379716    |     S527   |	B90016417    |	K3662873   |	M03      |AC90000	  |CM900020    |    2                 |
    | Phoenix, AZ         |  6             | 7            |   N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1481     |  N-HSS	   |V-HSS-V1	 |D1334      |  S174    |B379593    |     S527   |	B90016408    |	K3662830   |	M03      |AC90000	  |CM900019    |    2                 |
    | Jacksonville, FL    |   5            | 6            |   N-HFA-545	 |V-HFA-545-V1SS-T5B   |D1480     |  N-CON-H   |V-CON-H-V3	 |D0913      |   S174	|B379592    |     S527   |	B90016459    |	K3663340   |	M08      |AC90000	  |CM900035    |    2                 |
    | Miami, FL           |  4             | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T5C   |D1475     |  N-REM-H   |V-REM-H-V2	 |D0913      |  S3	    |B379816    |     S526   |	B90016406    |	K3605626   |	M03      |AC90000	  |CM900039    |    2                 |
    | Toledo, OH          |  3             | 4            |   N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1491     |   N-HSS	   |V-HSS-V1	 |D1382      |   S3	    |B379815    |     S526   |	B90016430    |	K3663144   |	M03      |AC90000	  |CM900025    |    2                 |
    | Detroit, MI         | 2              | 3            |   N-HFA-545	 |V-HFA-545-V1SS-T3A   |D1474     |   N-CON-H   |V-CON-H-V3	 |D0909      | S3       |B379803    |     S526   |	B90016419    |	K3662889   |	M03      |AC90000	  |CM900021    |    2                 |
    | Chicago, IL         | 3              | 5            |   N-ISR-C	 |V-ISR-C-F6D		   |D0461     |   N-CON-H   |V-CON-H-V3	 |D0916      |   S358	|B377857    |     S527   |	B90016426    |	K3599862   |	M03      |AC90000	  |CM900054    |    2                 |
    | Irvine, CA          | 5              | 6            |   N-TW-ISRA	 |V-TW-ISRA-F3		   |D0303     |   N-HSS	   |V-HSS-V1	 |D1333      |    S358  |B377702    |     S526   |	B20121290    |	K3642284   |	M03      |AC90000	  |CM900051    |    2                 |
    | Springfield, IL     |  6             | 7            |   N-ISR-C	 |V-ISR-C-F5A		   |D0460     |   N-HSS	   |V-HSS-V1	 |D1334      |   S445	|B379795    |     S11    |	B90016430    |	K3663128   |	M03      |AC90000	  |CM900025    |    2                 |
    | Houston, TX         |  7             | 8            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1475     |   N-CON-H   |V-CON-H-V3	 |D0913      |  S445	|B379371    |     S526   |	B90016400    |	K3605571   |	M03      |AC90000	  |CM900063    |    2                 |
    | Phoenix, AZ         |  6             | 7            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1485     |   N-REM-H   |V-REM-H-V2	 |D0913      |    S445  |B379265    |     S526   |	B90016512    |	K3940849   |	M03      |AC90000	  |CM900061    |    2                 |
    | Jacksonville, FL    |   5            | 6            |   N-ISR-C	 |V-ISR-C-F2D		   |D0065     |   N-HSS	   |V-HSS-V1	 |D1382      |   S69	|B378436    |     S11    |	B90016425    |	K3599861   |	M08      |AC90000	  |CM900029    |    2                 |
    | Miami, FL           |  4             | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T5D   |D1479     |   N-CON-H   |V-CON-H-V3	 |D0909      |   S69	|B378435    |     S526   |	B90016404    |	K3605621   |	M03      |AC90000	  |CM900019    |    2                 |
    | Toledo, OH          |  3             | 4            |   N-ISR-C	 |V-ISR-C-F7D		   |D0461     |   N-CON-H   |V-CON-H-V3	 |D0916      |   S461	|B379835    |     S526   |	B90016512    |	K3940819   |	M03      |AC90000	  |CM900061    |    2                 |
    | Detroit, MI         | 2              | 3            |   N-ISR-C	 |V-ISR-C-F2D		   |D0206     |   N-HSS	   |V-HSS-V1	 |D1333      |    S461  |B379832    |     S526   |	B90016413    |	K3605636   |	M03      |AC90000	  |CM900020    |    2                 |
    | Chicago, IL         | 3              | 5            |   N-ISR-C	 |V-ISR-C-F6A		   |D0461     |   N-HSS	   |V-HSS-V1	 |D1334      |  S461	|B379827    |     S526   |	B90016425    |	K3599861   |	M03      |AC90000	  |CM900054    |    2                 |

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 3
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
    And I use OPM T1 link with BID <BID> and SID <SID>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift |  NID2      |  VID2       |DID2       |BID       | SID       |  transactionNumber   |
    | Chicago, IL         | 3              | 5            |  N-HSS	   |V-HSS-V1	 |D1382      |S270      | B378436   |    3                 |
    | Irvine, CA          | 5              | 6            |  N-CON-H   |V-CON-H-V3	 |D0909      |S270	    |B378435    |    3                 |
    | Springfield, IL     |  6             | 7            |  N-CON-H   |V-CON-H-V3	 |D0916      |  S141    |B377601    |    3                 |
    | Houston, TX         |  7             | 8            |  N-HSS	   |V-HSS-V1	 |D1333      | S174	    |B379716    |    3                 |
    | Phoenix, AZ         |  6             | 7            |  N-HSS	   |V-HSS-V1	 |D1334      |  S174    |B379593    |    3                 |
    | Jacksonville, FL    |   5            | 6            |  N-CON-H   |V-CON-H-V3	 |D0913      |   S174	|B379592    |    3                 |
    | Miami, FL           |  4             | 5            |  N-REM-H   |V-REM-H-V2	 |D0913      |  S3	    |B379816    |    3                 |
    | Toledo, OH          |  3             | 4            |   N-HSS	   |V-HSS-V1	 |D1382      |   S3	    |B379815    |    3                 |
    | Detroit, MI         | 2              | 3            |   N-CON-H   |V-CON-H-V3	 |D0909      | S3       |B379803    |    3                 |
    | Chicago, IL         | 3              | 5            |   N-CON-H   |V-CON-H-V3	 |D0916      |   S358	|B377857    |    3                 |
    | Irvine, CA          | 5              | 6            |   N-HSS	   |V-HSS-V1	 |D1333      |    S358  |B377702    |    3                 |
    | Springfield, IL     |  6             | 7            |   N-HSS	   |V-HSS-V1	 |D1334      |   S445	|B379795    |    3                 |
    | Houston, TX         |  7             | 8            |   N-CON-H   |V-CON-H-V3	 |D0913      |  S445	|B379371    |    3                 |
    | Phoenix, AZ         |  6             | 7            |   N-REM-H   |V-REM-H-V2	 |D0913      |    S445  |B379265    |    3                 |
    | Jacksonville, FL    |   5            | 6            |   N-HSS	   |V-HSS-V1	 |D1382      |   S69	|B378436    |    3                 |
    | Miami, FL           |  4             | 5            |   N-CON-H   |V-CON-H-V3	 |D0909      |   S69	|B378435    |    3                 |
    | Toledo, OH          |  3             | 4            |   N-CON-H   |V-CON-H-V3	 |D0916      |   S461	|B379835    |    3                 |
    | Detroit, MI         | 2              | 3            |   N-HSS	   |V-HSS-V1	 |D1333      |    S461  |B379832    |    3                 |
    | Chicago, IL         | 3              | 5            |   N-HSS	   |V-HSS-V1	 |D1334      |  S461	|B379827    |    3                 |


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 5
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I use OPM T1 link with BID <BID> and SID <SID>
    And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
    And i use OPM T2 link with BID <BID2> and SID <SID2>
    And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | BID       | SID       | BID2       |  SID2       | SID3       | BID3       |  CMID       |  ACID       |  KID       |  MID       |  SID4       | BID4       |  CMID2       |  ACID2       |  KID2       |  MID2       | transactionNumber |
    | Chicago, IL         | 3              | 5            | S270      | B378436   |   S174	   |  B265180    |     S526	  | B90016413  |  K3605636	 |   M03       |AC90000	    |CM900020    |    S11	   | B20114428  |  K3598361	   |   M01        |AC20017	    |CM200000     | 5                |
    | Irvine, CA          | 5              | 6            | S270	  |B378435    |   S174	   |  B265180    |     S526   |	B90016427  |	K3661863 |	M03        |AC90000	    |CM900024    |    S526	   |B90016400	|K3605571	   |M03	          |AC90001	    |CM900070     | 5                |
    | Springfield, IL     |  6             | 7            |   S141	  |B377601    |    S174    |  B265180    |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S11	   |B30140468	|K3658591	   |M03	          |AC30000	    |CM300000     | 5                |
    | Houston, TX         |  7             | 8            |  S174	  |B379716    |    S322	   |  B322681    |     S527   |	B90016417  |	K3662873 |	M03        |AC90000	    |CM900020    |    S192	   |B30140564	|K3662126	   |M03	          |AC30000	    |CM300013     | 5                |
    | Phoenix, AZ         |  6             | 7            |   S174	  |B379593    |     S322   |  B378048    |     S527   |	B90016408  |	K3662830 |	M03        |AC90000	    |CM900019    |    S11	   |B30140490	|K3939356	   |M03	          |AC30000	    |CM300000     | 5                |
    | Jacksonville, FL    |   5            | 6            |    S174	  |B379592    |       S322 |  B378638    |     S527   |	B90016459  |	K3663340 |	M08        |AC90000	    |CM900035    |    S362	   |B20114434	|K3598946	   |M03	          |AC20000	    |CM500024     | 5                |
    | Miami, FL           |  4             | 5            |   S3	  |B379816    |      S174  |  B379716    |     S526   |	B90016406  |	K3605626 |	M03        |AC90000	    |CM900039    |    S11	   |B20114407	|K3598130	   |M03	          |AC20016	    |CM200000     | 5                |
    | Toledo, OH          |  3             | 4            |    S3	  |B379815    |       S286 |  B3796102   |     S526   |	B90016430  |	K3663144 |	M03        |AC90000	    |CM900025    |    S11	   |B20114407	|K3598130	   |M02	          |AC20000	    |CM200000     | 5                |
    | Detroit, MI         | 2              | 3            |  S3       |B379803    |   S174	   |  B265180    |     S526   |	B90016419  |	K3662889 |	M03        |AC90000	    |CM900021    |    S11	   |B90016381	|K3600806	   |M03	          |AC90003	    |CM900010     | 5                |
    | Chicago, IL         | 3              | 5            |    S358	  |B377857    |    S174    |  B265180    |     S527   |	B90016426  |	K3599862 |	M03        |AC90000	    |CM900054    |    S192	   |B30140568	|K3662168	   |M03	          |AC30000	    |CM300013     | 5                |
    | Irvine, CA          | 5              | 6            |     S358  |B377702    |    S322	   |  B322681    |     S526   |	B20121290  |	K3642284 |	M03        |AC90000	    |CM900051    |    S11	   |B20121569	|K3665234	   |M03	          |AC20000	    |CM200203     | 5                |
    | Springfield, IL     |  6             | 7            |    S445	  |B379795    |     S322   |  B378048    |     S11    |	B90016430  |	K3663128 |	M03        |AC90000	    |CM900025    |    S11	   |B20121413	|K3663688	   |M01	          |AC20000	    |CM200000     | 5                |
    | Houston, TX         |  7             | 8            |   S445	  |B379371    |       S322 |  B378638    |     S526   |	B90016400  |	K3605571 |	M03        |AC90000	    |CM900063    |    S11	   |B20136790	|K3818809	   |M08	          |AC20000	    |CM200258     | 5                |
    | Phoenix, AZ         |  6             | 7            |     S445  |B379265    |      S174  |  B379716    |     S526   |	B90016512  |	K3940849 |	M03        |AC90000	    |CM900061    |    S11	   |B30140782	|K3666914	   |M03	          |AC30000	    |CM300016     | 5                |
    | Jacksonville, FL    |   5            | 6            |    S69	  |B378436    |       S286 |  B3796102   |     S11    |	B90016425  |	K3599861 |	M08        |AC90000	    |CM900029    |    S192	   |B20121654	|K3666179	   |M01	          |AC20000	    |CM200000     | 5                |
    | Miami, FL           |  4             | 5            |    S69	  |B378435    |  S174	   |  B265180    |     S526   |	B90016404  |	K3605621 |	M03        |AC90000	    |CM900019    |    S192	   |B20114433	|K3597506	   |M02	          |AC20000	    |CM200000     | 5                |
    | Toledo, OH          |  3             | 4            |    S461	  |B379835    |   S174     |  B265180    |     S526   |	B90016512  |	K3940819 |	M03        |AC90000	    |CM900061    |    S527	   |B90016427	|K3661863	   |M03	          |AC90009	    |CM900054     | 5                |
    | Detroit, MI         | 2              | 3            |     S461  |	B379832   |   S322	   |  B322681    |     S526   |	B90016413  |	K3605636 |	M03        |AC90000	    |CM900020    |    S527	   |B90016423	|K3600561	   |M03	          |AC90009	    |CM900045     | 5                |
    | Chicago, IL         | 3              | 5            |   S461	  |B379827    |    S322    |  B378048    |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S192	   |B20137247	|K3939064	   |M08	          |AC20000	    |CM200271     | 5                |

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


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 7
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
    And i use OPM T2 link with BID <BID2> and SID <SID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift |  BID2       |  SID2       |   SID4       | BID4       |  CMID2       |  ACID2       |  KID2         |  MID2       | transactionNumber |
    | Chicago, IL         | 3              | 5            |    S174	    |  B265180    |     S11	     | B20114428  |  K3598361	 |   M01        |AC20017	    |CM200000     | 7                 |
    | Irvine, CA          | 5              | 6            |    S174	    |  B265180    |     S526	 |B90016400	  |K3605571	     |M03	        |AC90001	    |CM900070     | 7                 |
    | Springfield, IL     |  6             | 7            |     S174    |  B265180    |      S11	 |B30140468	  |K3658591	     |M03	        |AC30000	    |CM300000     | 7                 |
    | Houston, TX         |  7             | 8            |     S322	|  B322681    |    S192	     |B30140564	  |K3662126	     |M03	        |AC30000	    |CM300013     | 7                 |
    | Phoenix, AZ         |  6             | 7            |      S322   |  B378048    |      S11	 |B30140490	  |K3939356	     |M03	        |AC30000	    |CM300000     | 7                 |
    | Jacksonville, FL    |   5            | 6            |        S322 |  B378638    |      S362	 |B20114434	  |K3598946	     |M03	        |AC20000	    |CM500024     | 7                 |
    | Miami, FL           |  4             | 5            |       S174  |  B379716    |      S11	 |B20114407	  |K3598130	     |M03	        |AC20016	    |CM200000     | 7                 |
    | Toledo, OH          |  3             | 4            |        S286 |  B3796102   |      S11	 |B20114407	  |K3598130	     |M02	        |AC20000	    |CM200000     | 7                 |
    | Detroit, MI         | 2              | 3            |    S174	    |  B265180    |     S11	     |B90016381	  |K3600806	     |M03	        |AC90003	    |CM900010     | 7                 |
    | Chicago, IL         | 3              | 5            |     S174    |  B265180    |       S192	 |B30140568	  |K3662168	     |M03	        |AC30000	    |CM300013     | 7                 |
    | Irvine, CA          | 5              | 6            |     S322	|  B322681    |    S11	     |B20121569	  |K3665234	     |M03	        |AC20000	    |CM200203     | 7                 |
    | Springfield, IL     |  6             | 7            |      S322   |  B378048    |      S11	 |B20121413	  |K3663688	     |M01	        |AC20000	    |CM200000     | 7                 |
    | Houston, TX         |  7             | 8            |        S322 |  B378638    |      S11	 |B20136790	  |K3818809	     |M08	        |AC20000	    |CM200258     | 7                 |
    | Phoenix, AZ         |  6             | 7            |       S174  |  B379716    |      S11	 |B30140782	  |K3666914	     |M03	        |AC30000	    |CM300016     | 7                 |
    | Jacksonville, FL    |   5            | 6            |        S286 |  B3796102   |      S192	 |B20121654	  |K3666179	     |M01	        |AC20000	    |CM200000     | 7                 |
    | Miami, FL           |  4             | 5            |   S174	    |  B265180    |     S192	 |B20114433	  |K3597506	     |M02	        |AC20000	    |CM200000     | 7                 |
    | Toledo, OH          |  3             | 4            |    S174     |  B265180    |       S527	 |B90016427	  |K3661863	     |M03	        |AC90009	    |CM900054     | 7                 |
    | Detroit, MI         | 2              | 3            |    S322	    |  B322681    |     S527	 |B90016423	  |K3600561	     |M03	        |AC90009	    |CM900045     | 7                 |
    | Chicago, IL         | 3              | 5            |     S322    |  B378048    |    S192	     |B20137247	  |K3939064	     |M08	        |AC20000	    |CM200271     | 7                 |

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 10
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I use OPM T1 link with BID <BID> and SID <SID>
    And i use DBM link with NID <NID>, VID <VID> and DID <DID>
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | NID          |  VID                |DID       |  NID2      |  VID2       |DID2       |BID       | SID       | transactionNumber  |
    | Chicago, IL         | 3              | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1476     |  N-HSS	   |V-HSS-V1	 |D1382      |S270      | B378436   |   10               |
    | Irvine, CA          | 5              | 6            |   N-ISR-C	 |V-ISR-C-F1D		   |D0461     |  N-CON-H   |V-CON-H-V3	 |D0909      |S270	    |B378435    |   10               |
    | Springfield, IL     |  6             | 7            |   N-ISRH-RFL |V-ISRH-RFL-V1	       |D1183     |  N-CON-H   |V-CON-H-V3	 |D0916      |  S141    |B377601    |   10               |
    | Houston, TX         |  7             | 8            |   N-TW-ISRH	 |V-TW-ISRH-F3A		   |D0463     |  N-HSS	   |V-HSS-V1	 |D1333      | S174	    |B379716    |   10               |
    | Phoenix, AZ         |  6             | 7            |   N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1481     |  N-HSS	   |V-HSS-V1	 |D1334      |  S174    |B379593    |   10               |
    | Jacksonville, FL    |   5            | 6            |   N-HFA-545	 |V-HFA-545-V1SS-T5B   |D1480     |  N-CON-H   |V-CON-H-V3	 |D0913      |   S174	|B379592    |   10               |
    | Miami, FL           |  4             | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T5C   |D1475     |  N-REM-H   |V-REM-H-V2	 |D0913      |  S3	    |B379816    |   10               |
    | Toledo, OH          |  3             | 4            |   N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1491     |   N-HSS	   |V-HSS-V1	 |D1382      |   S3	    |B379815    |   10               |
    | Detroit, MI         | 2              | 3            |   N-HFA-545	 |V-HFA-545-V1SS-T3A   |D1474     |   N-CON-H   |V-CON-H-V3	 |D0909      | S3       |B379803    |   10               |
    | Chicago, IL         | 3              | 5            |   N-ISR-C	 |V-ISR-C-F6D		   |D0461     |   N-CON-H   |V-CON-H-V3	 |D0916      |   S358	|B377857    |   10               |
    | Irvine, CA          | 5              | 6            |   N-TW-ISRA	 |V-TW-ISRA-F3		   |D0303     |   N-HSS	   |V-HSS-V1	 |D1333      |    S358  |B377702    |   10               |
    | Springfield, IL     |  6             | 7            |   N-ISR-C	 |V-ISR-C-F5A		   |D0460     |   N-HSS	   |V-HSS-V1	 |D1334      |   S445	|B379795    |   10               |
    | Houston, TX         |  7             | 8            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1475     |   N-CON-H   |V-CON-H-V3	 |D0913      |  S445	|B379371    |   10               |
    | Phoenix, AZ         |  6             | 7            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1485     |   N-REM-H   |V-REM-H-V2	 |D0913      |    S445  |B379265    |   10               |
    | Jacksonville, FL    |   5            | 6            |   N-ISR-C	 |V-ISR-C-F2D		   |D0065     |   N-HSS	   |V-HSS-V1	 |D1382      |   S69	|B378436    |   10               |
    | Miami, FL           |  4             | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T5D   |D1479     |   N-CON-H   |V-CON-H-V3	 |D0909      |   S69	|B378435    |   10               |
    | Toledo, OH          |  3             | 4            |   N-ISR-C	 |V-ISR-C-F7D		   |D0461     |   N-CON-H   |V-CON-H-V3	 |D0916      |   S461	|B379835    |   10               |
    | Detroit, MI         | 2              | 3            |   N-ISR-C	 |V-ISR-C-F2D		   |D0206     |   N-HSS	   |V-HSS-V1	 |D1333      |    S461  |B379832    |   10               |
    | Chicago, IL         | 3              | 5            |   N-ISR-C	 |V-ISR-C-F6A		   |D0461     |   N-HSS	   |V-HSS-V1	 |D1334      |  S461	|B379827    |   10               |

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 11
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use DBM link with NID <NID>, VID <VID> and DID <DID>
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | NID          |  VID                |DID       |  NID2      |  VID2       |DID2       | transactionNumber  |
    | Chicago, IL         | 3              | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1476     |  N-HSS	   |V-HSS-V1	 |D1382      |   11               |
    | Irvine, CA          | 5              | 6            |   N-ISR-C	 |V-ISR-C-F1D		   |D0461     |  N-CON-H   |V-CON-H-V3	 |D0909      |   11               |
    | Springfield, IL     |  6             | 7            |   N-ISRH-RFL |V-ISRH-RFL-V1	       |D1183     |  N-CON-H   |V-CON-H-V3	 |D0916      |   11               |
    | Houston, TX         |  7             | 8            |   N-TW-ISRH	 |V-TW-ISRH-F3A		   |D0463     |  N-HSS	   |V-HSS-V1	 |D1333      |   11               |
    | Phoenix, AZ         |  6             | 7            |   N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1481     |  N-HSS	   |V-HSS-V1	 |D1334      |   11               |
    | Jacksonville, FL    |   5            | 6            |   N-HFA-545	 |V-HFA-545-V1SS-T5B   |D1480     |  N-CON-H   |V-CON-H-V3	 |D0913      |   11               |
    | Miami, FL           |  4             | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T5C   |D1475     |  N-REM-H   |V-REM-H-V2	 |D0913      |   11               |
    | Toledo, OH          |  3             | 4            |   N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1491     |   N-HSS	   |V-HSS-V1	 |D1382      |   11               |
    | Detroit, MI         | 2              | 3            |   N-HFA-545	 |V-HFA-545-V1SS-T3A   |D1474     |   N-CON-H   |V-CON-H-V3	 |D0909      |   11               |
    | Chicago, IL         | 3              | 5            |   N-ISR-C	 |V-ISR-C-F6D		   |D0461     |   N-CON-H   |V-CON-H-V3	 |D0916      |   11               |
    | Irvine, CA          | 5              | 6            |   N-TW-ISRA	 |V-TW-ISRA-F3		   |D0303     |   N-HSS	   |V-HSS-V1	 |D1333      |   11               |
    | Springfield, IL     |  6             | 7            |   N-ISR-C	 |V-ISR-C-F5A		   |D0460     |   N-HSS	   |V-HSS-V1	 |D1334      |   11               |
    | Houston, TX         |  7             | 8            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1475     |   N-CON-H   |V-CON-H-V3	 |D0913      |   11               |
    | Phoenix, AZ         |  6             | 7            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1485     |   N-REM-H   |V-REM-H-V2	 |D0913      |   11               |
    | Jacksonville, FL    |   5            | 6            |   N-ISR-C	 |V-ISR-C-F2D		   |D0065     |   N-HSS	   |V-HSS-V1	 |D1382      |   11               |
    | Miami, FL           |  4             | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T5D   |D1479     |   N-CON-H   |V-CON-H-V3	 |D0909      |   11               |
    | Toledo, OH          |  3             | 4            |   N-ISR-C	 |V-ISR-C-F7D		   |D0461     |   N-CON-H   |V-CON-H-V3	 |D0916      |   11               |
    | Detroit, MI         | 2              | 3            |   N-ISR-C	 |V-ISR-C-F2D		   |D0206     |   N-HSS	   |V-HSS-V1	 |D1333      |   11               |
    | Chicago, IL         | 3              | 5            |   N-ISR-C	 |V-ISR-C-F6A		   |D0461     |   N-HSS	   |V-HSS-V1	 |D1334      |   11               |


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 12
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift |   SID4       | BID4       |  CMID2       |  ACID2       |  KID2         |  MID2       | NID2      |  VID2       |DID2       | transactionNumber |
    | Chicago, IL         | 3              | 5            |     S11	     | B20114428  |  K3598361	 |   M01        |AC20017	    |CM200000     | N-HSS	  |V-HSS-V1	    |D1382      | 12                 |
    | Irvine, CA          | 5              | 6            |     S526	 |B90016400	  |K3605571	     |M03	        |AC90001	    |CM900070     | N-CON-H   |V-CON-H-V3	|D0909      | 12                 |
    | Springfield, IL     |  6             | 7            |      S11	 |B30140468	  |K3658591	     |M03	        |AC30000	    |CM300000     | N-CON-H   |V-CON-H-V3	|D0916      | 12                 |
    | Houston, TX         |  7             | 8            |    S192	     |B30140564	  |K3662126	     |M03	        |AC30000	    |CM300013     | N-HSS	  |V-HSS-V1	    |D1333      | 12                 |
    | Phoenix, AZ         |  6             | 7            |      S11	 |B30140490	  |K3939356	     |M03	        |AC30000	    |CM300000     | N-HSS	  |V-HSS-V1	    |D1334      | 12                 |
    | Jacksonville, FL    |   5            | 6            |      S362	 |B20114434	  |K3598946	     |M03	        |AC20000	    |CM500024     | N-CON-H   |V-CON-H-V3	|D0913      | 12                 |
    | Miami, FL           |  4             | 5            |      S11	 |B20114407	  |K3598130	     |M03	        |AC20016	    |CM200000     | N-REM-H   |V-REM-H-V2	|D0913      | 12                 |
    | Toledo, OH          |  3             | 4            |      S11	 |B20114407	  |K3598130	     |M02	        |AC20000	    |CM200000     |  N-HSS	  |V-HSS-V1	    |D1382      | 12                 |
    | Detroit, MI         | 2              | 3            |     S11	     |B90016381	  |K3600806	     |M03	        |AC90003	    |CM900010     |  N-CON-H  |V-CON-H-V3	|D0909      | 12                 |
    | Chicago, IL         | 3              | 5            |       S192	 |B30140568	  |K3662168	     |M03	        |AC30000	    |CM300013     |  N-CON-H  |V-CON-H-V3	|D0916      | 12                 |
    | Irvine, CA          | 5              | 6            |    S11	     |B20121569	  |K3665234	     |M03	        |AC20000	    |CM200203     |  N-HSS	  |V-HSS-V1	    |D1333      | 12                 |
    | Springfield, IL     |  6             | 7            |      S11	 |B20121413	  |K3663688	     |M01	        |AC20000	    |CM200000     |  N-HSS	  |V-HSS-V1	    |D1334      | 12                 |
    | Houston, TX         |  7             | 8            |      S11	 |B20136790	  |K3818809	     |M08	        |AC20000	    |CM200258     |  N-CON-H  |V-CON-H-V3	|D0913      | 12                 |
    | Phoenix, AZ         |  6             | 7            |      S11	 |B30140782	  |K3666914	     |M03	        |AC30000	    |CM300016     |  N-REM-H  |V-REM-H-V2	|D0913      | 12                 |
    | Jacksonville, FL    |   5            | 6            |      S192	 |B20121654	  |K3666179	     |M01	        |AC20000	    |CM200000     |  N-HSS	  |V-HSS-V1	    |D1382      | 12                 |
    | Miami, FL           |  4             | 5            |     S192	 |B20114433	  |K3597506	     |M02	        |AC20000	    |CM200000     |  N-CON-H  |V-CON-H-V3	|D0909      | 12                 |
    | Toledo, OH          |  3             | 4            |       S527	 |B90016427	  |K3661863	     |M03	        |AC90009	    |CM900054     |  N-CON-H  |V-CON-H-V3	|D0916      | 12                 |
    | Detroit, MI         | 2              | 3            |     S527	 |B90016423	  |K3600561	     |M03	        |AC90009	    |CM900045     |  N-HSS	  |V-HSS-V1	    |D1333      | 12                 |
    | Chicago, IL         | 3              | 5            |    S192	     |B20137247	  |K3939064	     |M08	        |AC20000	    |CM200271     |  N-HSS	  |V-HSS-V1	    |D1334      | 12                 |

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 13
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
    And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
    And i use OPM T2 link with BID <BID2> and SID <SID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | BID2       |  SID2       | SID3       | BID3       |  CMID       |  ACID       |  KID       |  MID       |  SID4       | BID4       |  CMID2       |  ACID2       |  KID2       |  MID2       | transactionNumber |
    | Chicago, IL         | 3              | 5            |   S174	   |  B265180    |     S526	  | B90016413  |  K3605636	 |   M03       |AC90000	    |CM900020    |    S11	   | B20114428  |  K3598361	   |   M01        |AC20017	    |CM200000     | 13                |
    | Irvine, CA          | 5              | 6            |   S174	   |  B265180    |     S526   |	B90016427  |	K3661863 |	M03        |AC90000	    |CM900024    |    S526	   |B90016400	|K3605571	   |M03	          |AC90001	    |CM900070     | 13                |
    | Springfield, IL     |  6             | 7            |    S174    |  B265180    |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S11	   |B30140468	|K3658591	   |M03	          |AC30000	    |CM300000     | 13                |
    | Houston, TX         |  7             | 8            |    S322	   |  B322681    |     S527   |	B90016417  |	K3662873 |	M03        |AC90000	    |CM900020    |    S192	   |B30140564	|K3662126	   |M03	          |AC30000	    |CM300013     | 13                |
    | Phoenix, AZ         |  6             | 7            |     S322   |  B378048    |     S527   |	B90016408  |	K3662830 |	M03        |AC90000	    |CM900019    |    S11	   |B30140490	|K3939356	   |M03	          |AC30000	    |CM300000     | 13                |
    | Jacksonville, FL    |   5            | 6            |       S322 |  B378638    |     S527   |	B90016459  |	K3663340 |	M08        |AC90000	    |CM900035    |    S362	   |B20114434	|K3598946	   |M03	          |AC20000	    |CM500024     | 13                |
    | Miami, FL           |  4             | 5            |      S174  |  B379716    |     S526   |	B90016406  |	K3605626 |	M03        |AC90000	    |CM900039    |    S11	   |B20114407	|K3598130	   |M03	          |AC20016	    |CM200000     | 13                |
    | Toledo, OH          |  3             | 4            |       S286 |  B3796102   |     S526   |	B90016430  |	K3663144 |	M03        |AC90000	    |CM900025    |    S11	   |B20114407	|K3598130	   |M02	          |AC20000	    |CM200000     | 13                |
    | Detroit, MI         | 2              | 3            |   S174	   |  B265180    |     S526   |	B90016419  |	K3662889 |	M03        |AC90000	    |CM900021    |    S11	   |B90016381	|K3600806	   |M03	          |AC90003	    |CM900010     | 13                |
    | Chicago, IL         | 3              | 5            |    S174    |  B265180    |     S527   |	B90016426  |	K3599862 |	M03        |AC90000	    |CM900054    |    S192	   |B30140568	|K3662168	   |M03	          |AC30000	    |CM300013     | 13                |
    | Irvine, CA          | 5              | 6            |    S322	   |  B322681    |     S526   |	B20121290  |	K3642284 |	M03        |AC90000	    |CM900051    |    S11	   |B20121569	|K3665234	   |M03	          |AC20000	    |CM200203     | 13                |
    | Springfield, IL     |  6             | 7            |     S322   |  B378048    |     S11    |	B90016430  |	K3663128 |	M03        |AC90000	    |CM900025    |    S11	   |B20121413	|K3663688	   |M01	          |AC20000	    |CM200000     | 13                |
    | Houston, TX         |  7             | 8            |       S322 |  B378638    |     S526   |	B90016400  |	K3605571 |	M03        |AC90000	    |CM900063    |    S11	   |B20136790	|K3818809	   |M08	          |AC20000	    |CM200258     | 13                |
    | Phoenix, AZ         |  6             | 7            |      S174  |  B379716    |     S526   |	B90016512  |	K3940849 |	M03        |AC90000	    |CM900061    |    S11	   |B30140782	|K3666914	   |M03	          |AC30000	    |CM300016     | 13                |
    | Jacksonville, FL    |   5            | 6            |       S286 |  B3796102   |     S11    |	B90016425  |	K3599861 |	M08        |AC90000	    |CM900029    |    S192	   |B20121654	|K3666179	   |M01	          |AC20000	    |CM200000     | 13                |
    | Miami, FL           |  4             | 5            |  S174	   |  B265180    |     S526   |	B90016404  |	K3605621 |	M03        |AC90000	    |CM900019    |    S192	   |B20114433	|K3597506	   |M02	          |AC20000	    |CM200000     | 13                |
    | Toledo, OH          |  3             | 4            |   S174     |  B265180    |     S526   |	B90016512  |	K3940819 |	M03        |AC90000	    |CM900061    |    S527	   |B90016427	|K3661863	   |M03	          |AC90009	    |CM900054     | 13                |
    | Detroit, MI         | 2              | 3            |   S322	   |  B322681    |     S526   |	B90016413  |	K3605636 |	M03        |AC90000	    |CM900020    |    S527	   |B90016423	|K3600561	   |M03	          |AC90009	    |CM900045     | 13                |
    | Chicago, IL         | 3              | 5            |    S322    |  B378048    |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S192	   |B20137247	|K3939064	   |M08	          |AC20000	    |CM200271     | 13                |



  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 14
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use OPM T2 link with BID <BID2> and SID <SID2>
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | BID2       |  SID2       |  NID2      |  VID2       |DID2       | transactionNumber  |
    | Chicago, IL         | 3              | 5            |   S174	   |  B265180    |  N-HSS	  |V-HSS-V1	    |D1382      |   14               |
    | Irvine, CA          | 5              | 6            |   S174	   |  B265180    |  N-CON-H   |V-CON-H-V3	|D0909      |   14               |
    | Springfield, IL     |  6             | 7            |    S174    |  B265180    |  N-CON-H   |V-CON-H-V3	|D0916      |   14               |
    | Houston, TX         |  7             | 8            |    S322	   |  B322681    |  N-HSS	  |V-HSS-V1	    |D1333      |   14               |
    | Phoenix, AZ         |  6             | 7            |     S322   |  B378048    |  N-HSS	  |V-HSS-V1	    |D1334      |   14               |
    | Jacksonville, FL    |   5            | 6            |       S322 |  B378638    |  N-CON-H   |V-CON-H-V3	|D0913      |   14               |
    | Miami, FL           |  4             | 5            |      S174  |  B379716    |  N-REM-H   |V-REM-H-V2	|D0913      |   14               |
    | Toledo, OH          |  3             | 4            |       S286 |  B3796102   |   N-HSS	  |V-HSS-V1	    |D1382      |   14               |
    | Detroit, MI         | 2              | 3            |   S174	   |  B265180    |   N-CON-H  |V-CON-H-V3	|D0909      |   14               |
    | Chicago, IL         | 3              | 5            |    S174    |  B265180    |   N-CON-H  |V-CON-H-V3	|D0916      |   14               |
    | Irvine, CA          | 5              | 6            |    S322	   |  B322681    |   N-HSS	  |V-HSS-V1	    |D1333      |   14               |
    | Springfield, IL     |  6             | 7            |     S322   |  B378048    |   N-HSS	  |V-HSS-V1	    |D1334      |   14               |
    | Houston, TX         |  7             | 8            |       S322 |  B378638    |   N-CON-H  |V-CON-H-V3	|D0913      |   14               |
    | Phoenix, AZ         |  6             | 7            |      S174  |  B379716    |   N-REM-H  |V-REM-H-V2	|D0913      |   14               |
    | Jacksonville, FL    |   5            | 6            |       S286 |  B3796102   |   N-HSS	  |V-HSS-V1	    |D1382      |   14               |
    | Miami, FL           |  4             | 5            |  S174	   |  B265180    |   N-CON-H  |V-CON-H-V3	|D0909      |   14               |
    | Toledo, OH          |  3             | 4            |   S174     |  B265180    |   N-CON-H  |V-CON-H-V3	|D0916      |   14               |
    | Detroit, MI         | 2              | 3            |   S322	   |  B322681    |   N-HSS	  |V-HSS-V1	    |D1333      |   14               |
    | Chicago, IL         | 3              | 5            |    S322    |  B378048    |   N-HSS	  |V-HSS-V1	    |D1334      |   14               |




  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 15
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
    And i use OPM T2 link with BID <BID2> and SID <SID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | BID2       |  SID2       | SID3       | BID3       |  CMID       |  ACID       |  KID       |  MID      | transactionNumber |
    | Chicago, IL         | 3              | 5            |   S174	   |  B265180    |     S526	  | B90016413  |  K3605636	 |   M03       |AC90000	    |CM900020   | 15                |
    | Irvine, CA          | 5              | 6            |   S174	   |  B265180    |     S526   |	B90016427  |	K3661863 |	M03        |AC90000	    |CM900024   | 15                |
    | Springfield, IL     |  6             | 7            |    S174    |  B265180    |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054   | 15                |
    | Houston, TX         |  7             | 8            |    S322	   |  B322681    |     S527   |	B90016417  |	K3662873 |	M03        |AC90000	    |CM900020   | 15                |
    | Phoenix, AZ         |  6             | 7            |     S322   |  B378048    |     S527   |	B90016408  |	K3662830 |	M03        |AC90000	    |CM900019   | 15                |
    | Jacksonville, FL    |   5            | 6            |       S322 |  B378638    |     S527   |	B90016459  |	K3663340 |	M08        |AC90000	    |CM900035   | 15                |
    | Miami, FL           |  4             | 5            |      S174  |  B379716    |     S526   |	B90016406  |	K3605626 |	M03        |AC90000	    |CM900039   | 15                |
    | Toledo, OH          |  3             | 4            |       S286 |  B3796102   |     S526   |	B90016430  |	K3663144 |	M03        |AC90000	    |CM900025   | 15                |
    | Detroit, MI         | 2              | 3            |   S174	   |  B265180    |     S526   |	B90016419  |	K3662889 |	M03        |AC90000	    |CM900021   | 15                |
    | Chicago, IL         | 3              | 5            |    S174    |  B265180    |     S527   |	B90016426  |	K3599862 |	M03        |AC90000	    |CM900054   | 15                |
    | Irvine, CA          | 5              | 6            |    S322	   |  B322681    |     S526   |	B20121290  |	K3642284 |	M03        |AC90000	    |CM900051   | 15                |
    | Springfield, IL     |  6             | 7            |     S322   |  B378048    |     S11    |	B90016430  |	K3663128 |	M03        |AC90000	    |CM900025   | 15                |
    | Houston, TX         |  7             | 8            |       S322 |  B378638    |     S526   |	B90016400  |	K3605571 |	M03        |AC90000	    |CM900063   | 15                |
    | Phoenix, AZ         |  6             | 7            |      S174  |  B379716    |     S526   |	B90016512  |	K3940849 |	M03        |AC90000	    |CM900061   | 15                |
    | Jacksonville, FL    |   5            | 6            |       S286 |  B3796102   |     S11    |	B90016425  |	K3599861 |	M08        |AC90000	    |CM900029   | 15                |
    | Miami, FL           |  4             | 5            |  S174	   |  B265180    |     S526   |	B90016404  |	K3605621 |	M03        |AC90000	    |CM900019   | 15                |
    | Toledo, OH          |  3             | 4            |   S174     |  B265180    |     S526   |	B90016512  |	K3940819 |	M03        |AC90000	    |CM900061   | 15                |
    | Detroit, MI         | 2              | 3            |   S322	   |  B322681    |     S526   |	B90016413  |	K3605636 |	M03        |AC90000	    |CM900020   | 15                |
    | Chicago, IL         | 3              | 5            |    S322    |  B378048    |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054   | 15                |

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 17
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use AFF link with siteID <siteID>
    And i use OPM T2 link with BID <BID2> and SID <SID2>
    And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift |BID2       |  SID2     |  SID4      | BID4       |  CMID2       |  ACID2       |  KID2       |  MID2       |              siteID                      | transactionNumber  |
    | Chicago, IL         | 3              | 5            |  S174	  |  B265180  |    S11	   | B20114428  |  K3598361	   |   M01        |AC20017	    |CM200000     |   OOTtr9mlaCk-FO60Ohp0gmvfhTagtD1rsg     | 17                 |
    | Irvine, CA          | 5              | 6            |  S174	  |  B265180  |    S526	   |B90016400	|K3605571	   |M03	          |AC90001	    |CM900070     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     | 17                 |
    | Springfield, IL     |  6             | 7            |   S174    |  B265180  |    S11	   |B30140468	|K3658591	   |M03	          |AC30000	    |CM300000     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     | 17                 |
    | Houston, TX         |  7             | 8            |   S322	  |  B322681  |    S192	   |B30140564	|K3662126	   |M03	          |AC30000	    |CM300013     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     | 17                 |
    | Phoenix, AZ         |  6             | 7            |    S322   |  B378048  |    S11	   |B30140490	|K3939356	   |M03	          |AC30000	    |CM300000     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     | 17                 |
    | Jacksonville, FL    |   5            | 6            |      S322 |  B378638  |    S362	   |B20114434	|K3598946	   |M03	          |AC20000	    |CM500024     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     | 17                 |
    | Miami, FL           |  4             | 5            |     S174  |  B379716  |    S11	   |B20114407	|K3598130	   |M03	          |AC20016	    |CM200000     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     | 17                 |
    | Toledo, OH          |  3             | 4            |      S286 |  B3796102 |    S11	   |B20114407	|K3598130	   |M02	          |AC20000	    |CM200000     |   UO85MF6im/8-2YrhP/uhuf7HvXtdVqiAxQ     | 17                 |
    | Detroit, MI         | 2              | 3            |  S174	  |  B265180  |    S11	   |B90016381	|K3600806	   |M03	          |AC90003	    |CM900010     |   AysPbYF8vuM-f17m*Tcb*V5htUmQ3g6lMw     | 17                 |
    | Chicago, IL         | 3              | 5            |   S174    |  B265180  |    S192	   |B30140568	|K3662168	   |M03	          |AC30000	    |CM300013     |   InyfooKypHI-ax9YVHzmSRA0ldsoBZ7gfQ     | 17                 |
    | Irvine, CA          | 5              | 6            |   S322	  |  B322681  |    S11	   |B20121569	|K3665234	   |M03	          |AC20000	    |CM200203     |   vl0mfKZlvKU-e2eXrIzrNczqfv1EuLNt*g     | 17                 |
    | Springfield, IL     |  6             | 7            |    S322   |  B378048  |    S11	   |B20121413	|K3663688	   |M01	          |AC20000	    |CM200000     |   HBLvzQS2RdU-o0qkS119P0hw8c9GepJgVQ     | 17                 |
    | Houston, TX         |  7             | 8            |      S322 |  B378638  |    S11	   |B20136790	|K3818809	   |M08	          |AC20000	    |CM200258     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     | 17                 |
    | Phoenix, AZ         |  6             | 7            |     S174  |  B379716  |    S11	   |B30140782	|K3666914	   |M03	          |AC30000	    |CM300016     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     | 17                 |
    | Jacksonville, FL    |   5            | 6            |      S286 |  B3796102 |    S192	   |B20121654	|K3666179	   |M01	          |AC20000	    |CM200000     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     | 17                 |
    | Miami, FL           |  4             | 5            | S174	  |  B265180  |    S192	   |B20114433	|K3597506	   |M02	          |AC20000	    |CM200000     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     | 17                 |
    | Toledo, OH          |  3             | 4            |  S174     |  B265180  |    S527	   |B90016427	|K3661863	   |M03	          |AC90009	    |CM900054     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     | 17                 |
    | Detroit, MI         | 2              | 3            |  S322	  |  B322681  |    S527	   |B90016423	|K3600561	   |M03	          |AC90009	    |CM900045     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     | 17                 |
    | Chicago, IL         | 3              | 5            |   S322    |  B378048  |    S192	   |B20137247	|K3939064	   |M08	          |AC20000	    |CM200271     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     | 17                 |


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 19
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I use OPM T1 link with BID <BID> and SID <SID>
    And i use DBM link with NID <NID>, VID <VID> and DID <DID>
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | NID          |  VID                |DID       |  NID2      |  VID2       |DID2       |BID       | SID       |  transactionNumber |
    | Chicago, IL         | 3              | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1476     |  N-HSS	   |V-HSS-V1	 |D1382      |S270      | B378436   |   19               |
    | Irvine, CA          | 5              | 6            |   N-ISR-C	 |V-ISR-C-F1D		   |D0461     |  N-CON-H   |V-CON-H-V3	 |D0909      |S270	    |B378435    |   19               |
    | Springfield, IL     |  6             | 7            |   N-ISRH-RFL |V-ISRH-RFL-V1	       |D1183     |  N-CON-H   |V-CON-H-V3	 |D0916      |  S141    |B377601    |   19               |
    | Houston, TX         |  7             | 8            |   N-TW-ISRH	 |V-TW-ISRH-F3A		   |D0463     |  N-HSS	   |V-HSS-V1	 |D1333      | S174	    |B379716    |   19               |
    | Phoenix, AZ         |  6             | 7            |   N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1481     |  N-HSS	   |V-HSS-V1	 |D1334      |  S174    |B379593    |   19               |
    | Jacksonville, FL    |   5            | 6            |   N-HFA-545	 |V-HFA-545-V1SS-T5B   |D1480     |  N-CON-H   |V-CON-H-V3	 |D0913      |   S174	|B379592    |   19               |
    | Miami, FL           |  4             | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T5C   |D1475     |  N-REM-H   |V-REM-H-V2	 |D0913      |  S3	    |B379816    |   19               |
    | Toledo, OH          |  3             | 4            |   N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1491     |   N-HSS	   |V-HSS-V1	 |D1382      |   S3	    |B379815    |   19               |
    | Detroit, MI         | 2              | 3            |   N-HFA-545	 |V-HFA-545-V1SS-T3A   |D1474     |   N-CON-H   |V-CON-H-V3	 |D0909      | S3       |B379803    |   19               |
    | Chicago, IL         | 3              | 5            |   N-ISR-C	 |V-ISR-C-F6D		   |D0461     |   N-CON-H   |V-CON-H-V3	 |D0916      |   S358	|B377857    |   19               |
    | Irvine, CA          | 5              | 6            |   N-TW-ISRA	 |V-TW-ISRA-F3		   |D0303     |   N-HSS	   |V-HSS-V1	 |D1333      |    S358  |B377702    |   19               |
    | Springfield, IL     |  6             | 7            |   N-ISR-C	 |V-ISR-C-F5A		   |D0460     |   N-HSS	   |V-HSS-V1	 |D1334      |   S445	|B379795    |   19               |
    | Houston, TX         |  7             | 8            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1475     |   N-CON-H   |V-CON-H-V3	 |D0913      |  S445	|B379371    |   19               |
    | Phoenix, AZ         |  6             | 7            |   N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1485     |   N-REM-H   |V-REM-H-V2	 |D0913      |    S445  |B379265    |   19               |
    | Jacksonville, FL    |   5            | 6            |   N-ISR-C	 |V-ISR-C-F2D		   |D0065     |   N-HSS	   |V-HSS-V1	 |D1382      |   S69	|B378436    |   19               |
    | Miami, FL           |  4             | 5            |   N-HFA-545	 |V-HFA-545-V1SS-T5D   |D1479     |   N-CON-H   |V-CON-H-V3	 |D0909      |   S69	|B378435    |   19               |
    | Toledo, OH          |  3             | 4            |   N-ISR-C	 |V-ISR-C-F7D		   |D0461     |   N-CON-H   |V-CON-H-V3	 |D0916      |   S461	|B379835    |   19               |
    | Detroit, MI         | 2              | 3            |   N-ISR-C	 |V-ISR-C-F2D		   |D0206     |   N-HSS	   |V-HSS-V1	 |D1333      |    S461  |B379832    |   19               |
    | Chicago, IL         | 3              | 5            |   N-ISR-C	 |V-ISR-C-F6A		   |D0461     |   N-HSS	   |V-HSS-V1	 |D1334      |  S461	|B379827    |   19               |


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 20
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I use OPM T1 link with BID <BID> and SID <SID>
    And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
    And i use DBM link with NID <NID>, VID <VID> and DID <DID>
    And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
    And i use AFF link with siteID <siteID>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift |NID           |  VID                |DID       | SID3       | BID3          |  CMID       |  ACID       |  KID       |  MID       |  SID4       | BID4       |  CMID2       |  ACID2       |  KID2       |  MID2       |              siteID                      | BID       | SID       | transactionNumber |
    | Chicago, IL         | 3              | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1476     |     S526   | B90016413     |  K3605636	 |   M03       |AC90000	    |CM900020    |    S11	   | B20114428  |  K3598361	   |   M01        |AC20017	    |CM200000     |   OOTtr9mlaCk-FO60Ohp0gmvfhTagtD1rsg     | S270      | B378436   | 20                |
    | Irvine, CA          | 5              | 6            |  N-ISR-C	 |V-ISR-C-F1D		   |D0461     |     S526   |	B90016427  |	K3661863 |	M03        |AC90000	    |CM900024    |    S526	   |B90016400	|K3605571	   |M03	          |AC90001	    |CM900070     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     | S270	     |B378435    | 20                |
    | Springfield, IL     |  6             | 7            |  N-ISRH-RFL  |V-ISRH-RFL-V1	       |D1183     |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S11	   |B30140468	|K3658591	   |M03	          |AC30000	    |CM300000     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     |   S141	 |B377601    | 20                |
    | Houston, TX         |  7             | 8            |  N-TW-ISRH	 |V-TW-ISRH-F3A		   |D0463     |     S527   |	B90016417  |	K3662873 |	M03        |AC90000	    |CM900020    |    S192	   |B30140564	|K3662126	   |M03	          |AC30000	    |CM300013     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     |  S174	 |B379716    | 20                |
    | Phoenix, AZ         |  6             | 7            |  N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1481     |     S527   |	B90016408  |	K3662830 |	M03        |AC90000	    |CM900019    |    S11	   |B30140490	|K3939356	   |M03	          |AC30000	    |CM300000     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     |   S174	 |B379593    | 20                |
    | Jacksonville, FL    |   5            | 6            |  N-HFA-545	 |V-HFA-545-V1SS-T5B   |D1480     |     S527   |	B90016459  |	K3663340 |	M08        |AC90000	    |CM900035    |    S362	   |B20114434	|K3598946	   |M03	          |AC20000	    |CM500024     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     |    S174	 |B379592    | 20                |
    | Miami, FL           |  4             | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T5C   |D1475     |     S526   |	B90016406  |	K3605626 |	M03        |AC90000	    |CM900039    |    S11	   |B20114407	|K3598130	   |M03	          |AC20016	    |CM200000     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     |   S3	     |B379816    | 20                |
    | Toledo, OH          |  3             | 4            |  N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1491     |     S526   |	B90016430  |	K3663144 |	M03        |AC90000	    |CM900025    |    S11	   |B20114407	|K3598130	   |M02	          |AC20000	    |CM200000     |   UO85MF6im/8-2YrhP/uhuf7HvXtdVqiAxQ     |    S3	 |B379815    | 20                |
    | Detroit, MI         | 2              | 3            |  N-HFA-545	 |V-HFA-545-V1SS-T3A   |D1474     |     S526   |	B90016419  |	K3662889 |	M03        |AC90000	    |CM900021    |    S11	   |B90016381	|K3600806	   |M03	          |AC90003	    |CM900010     |   AysPbYF8vuM-f17m*Tcb*V5htUmQ3g6lMw     |  S3       |B379803    | 20                |
    | Chicago, IL         | 3              | 5            |  N-ISR-C	 |V-ISR-C-F6D		   |D0461     |     S527   |	B90016426  |	K3599862 |	M03        |AC90000	    |CM900054    |    S192	   |B30140568	|K3662168	   |M03	          |AC30000	    |CM300013     |   InyfooKypHI-ax9YVHzmSRA0ldsoBZ7gfQ     |    S358	 |B377857    | 20                |
    | Irvine, CA          | 5              | 6            |  N-TW-ISRA	 |V-TW-ISRA-F3		   |D0303     |     S526   |	B20121290  |	K3642284 |	M03        |AC90000	    |CM900051    |    S11	   |B20121569	|K3665234	   |M03	          |AC20000	    |CM200203     |   vl0mfKZlvKU-e2eXrIzrNczqfv1EuLNt*g     |     S358  |B377702    | 20                |
    | Springfield, IL     |  6             | 7            |  N-ISR-C	 |V-ISR-C-F5A		   |D0460     |     S11    |	B90016430  |	K3663128 |	M03        |AC90000	    |CM900025    |    S11	   |B20121413	|K3663688	   |M01	          |AC20000	    |CM200000     |   HBLvzQS2RdU-o0qkS119P0hw8c9GepJgVQ     |    S445	 |B379795    | 20                |
    | Houston, TX         |  7             | 8            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1475     |     S526   |	B90016400  |	K3605571 |	M03        |AC90000	    |CM900063    |    S11	   |B20136790	|K3818809	   |M08	          |AC20000	    |CM200258     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     |   S445	 |B379371    | 20                |
    | Phoenix, AZ         |  6             | 7            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1485     |     S526   |	B90016512  |	K3940849 |	M03        |AC90000	    |CM900061    |    S11	   |B30140782	|K3666914	   |M03	          |AC30000	    |CM300016     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     |     S445  |B379265    | 20                |
    | Jacksonville, FL    |   5            | 6            |  N-ISR-C	 |V-ISR-C-F2D		   |D0065     |     S11    |	B90016425  |	K3599861 |	M08        |AC90000	    |CM900029    |    S192	   |B20121654	|K3666179	   |M01	          |AC20000	    |CM200000     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     |    S69	 |B378436    | 20                |
    | Miami, FL           |  4             | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T5D   |D1479     |     S526   |	B90016404  |	K3605621 |	M03        |AC90000	    |CM900019    |    S192	   |B20114433	|K3597506	   |M02	          |AC20000	    |CM200000     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     |    S69	 |B378435    | 20                |
    | Toledo, OH          |  3             | 4            |  N-ISR-C	 |V-ISR-C-F7D		   |D0461     |     S526   |	B90016512  |	K3940819 |	M03        |AC90000	    |CM900061    |    S527	   |B90016427	|K3661863	   |M03	          |AC90009	    |CM900054     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     |    S461	 |B379835    | 20                |
    | Detroit, MI         | 2              | 3            |  N-ISR-C	 |V-ISR-C-F2D		   |D0206     |     S526   |	B90016413  |	K3605636 |	M03        |AC90000	    |CM900020    |    S527	   |B90016423	|K3600561	   |M03	          |AC90009	    |CM900045     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     |     S461  |	B379832  | 20                |
    | Chicago, IL         | 3              | 5            |  N-ISR-C	 |V-ISR-C-F6A		   |D0461     |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S192	   |B20137247	|K3939064	   |M08	          |AC20000	    |CM200271     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     |   S461	 |B379827    | 20                |



  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 21
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift | NID2      |  VID2       |DID2       | transactionNumber  |
    | Chicago, IL         | 3              | 5            | N-HSS	  |V-HSS-V1	    |D1382      |   21               |
    | Irvine, CA          | 5              | 6            | N-CON-H   |V-CON-H-V3	|D0909      |   21               |
    | Springfield, IL     |  6             | 7            | N-CON-H   |V-CON-H-V3	|D0916      |   21               |
    | Houston, TX         |  7             | 8            | N-HSS	  |V-HSS-V1	    |D1333      |   21               |
    | Phoenix, AZ         |  6             | 7            | N-HSS	  |V-HSS-V1	    |D1334      |   21               |
    | Jacksonville, FL    |   5            | 6            | N-CON-H   |V-CON-H-V3	|D0913      |   21               |
    | Miami, FL           |  4             | 5            | N-REM-H   |V-REM-H-V2	|D0913      |   21               |
    | Toledo, OH          |  3             | 4            |  N-HSS	  |V-HSS-V1	    |D1382      |   21               |
    | Detroit, MI         | 2              | 3            |  N-CON-H  |V-CON-H-V3	|D0909      |   21               |
    | Chicago, IL         | 3              | 5            |  N-CON-H  |V-CON-H-V3	|D0916      |   21               |
    | Irvine, CA          | 5              | 6            |  N-HSS	  |V-HSS-V1	    |D1333      |   21               |
    | Springfield, IL     |  6             | 7            |  N-HSS	  |V-HSS-V1	    |D1334      |   21               |
    | Houston, TX         |  7             | 8            |  N-CON-H  |V-CON-H-V3	|D0913      |   21               |
    | Phoenix, AZ         |  6             | 7            |  N-REM-H  |V-REM-H-V2	|D0913      |   21               |
    | Jacksonville, FL    |   5            | 6            |  N-HSS	  |V-HSS-V1	    |D1382      |   21               |
    | Miami, FL           |  4             | 5            |  N-CON-H  |V-CON-H-V3	|D0909      |   21               |
    | Toledo, OH          |  3             | 4            |  N-CON-H  |V-CON-H-V3	|D0916      |   21               |
    | Detroit, MI         | 2              | 3            |  N-HSS	  |V-HSS-V1	    |D1333      |   21               |
    | Chicago, IL         | 3              | 5            |  N-HSS	  |V-HSS-V1	    |D1334      |   21               |


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

  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 25
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I use OPM T1 link with BID <BID> and SID <SID>
    And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
    And i use transaction email link with NID <NID2>, VID <VID2> and DID <DID2>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift |SID4        | BID4       |  CMID2     |  ACID2       |  KID2       |  MID2      |  NID2      |  VID2       |DID2       |BID       | SID       | transactionNumber  |
    | Chicago, IL         | 3              | 5            |  S11	   | B20114428  |  K3598361  |   M01        |AC20017	  |CM200000    |  N-HSS	    |V-HSS-V1	  |D1382      |S270      | B378436   |   25               |
    | Irvine, CA          | 5              | 6            |  S526	   |B90016400	|K3605571    |M03	        |AC90001	  |CM900070    |  N-CON-H   |V-CON-H-V3	  |D0909      |S270	     |B378435    |   25               |
    | Springfield, IL     |  6             | 7            |  S11	   |B30140468	|K3658591    |M03	        |AC30000	  |CM300000    |  N-CON-H   |V-CON-H-V3	  |D0916      |  S141    |B377601    |   25               |
    | Houston, TX         |  7             | 8            |  S192	   |B30140564	|K3662126    |M03	        |AC30000	  |CM300013    |  N-HSS	    |V-HSS-V1	  |D1333      | S174	 |B379716    |   25               |
    | Phoenix, AZ         |  6             | 7            |  S11	   |B30140490	|K3939356    |M03	        |AC30000	  |CM300000    |  N-HSS	    |V-HSS-V1	  |D1334      |  S174    |B379593    |   25               |
    | Jacksonville, FL    |   5            | 6            |  S362	   |B20114434	|K3598946    |M03	        |AC20000	  |CM500024    |  N-CON-H   |V-CON-H-V3	  |D0913      |   S174	 |B379592    |   25               |
    | Miami, FL           |  4             | 5            |  S11	   |B20114407	|K3598130    |M03	        |AC20016	  |CM200000    |  N-REM-H   |V-REM-H-V2	  |D0913      |  S3	     |B379816    |   25               |
    | Toledo, OH          |  3             | 4            |  S11	   |B20114407	|K3598130    |M02	        |AC20000	  |CM200000    |   N-HSS    |V-HSS-V1	  |D1382      |   S3	 |B379815    |   25               |
    | Detroit, MI         | 2              | 3            |  S11	   |B90016381	|K3600806    |M03	        |AC90003	  |CM900010    |   N-CON-H  |V-CON-H-V3	  |D0909      | S3       |B379803    |   25               |
    | Chicago, IL         | 3              | 5            |  S192	   |B30140568	|K3662168    |M03	        |AC30000	  |CM300013    |   N-CON-H  |V-CON-H-V3	  |D0916      |   S358	 |B377857    |   25               |
    | Irvine, CA          | 5              | 6            |  S11	   |B20121569	|K3665234    |M03	        |AC20000	  |CM200203    |   N-HSS    |V-HSS-V1	  |D1333      |    S358  |B377702    |   25               |
    | Springfield, IL     |  6             | 7            |  S11	   |B20121413	|K3663688    |M01	        |AC20000	  |CM200000    |   N-HSS    |V-HSS-V1	  |D1334      |   S445	 |B379795    |   25               |
    | Houston, TX         |  7             | 8            |  S11	   |B20136790	|K3818809    |M08	        |AC20000	  |CM200258    |   N-CON-H  |V-CON-H-V3	  |D0913      |  S445	 |B379371    |   25               |
    | Phoenix, AZ         |  6             | 7            |  S11	   |B30140782	|K3666914    |M03	        |AC30000	  |CM300016    |   N-REM-H  |V-REM-H-V2	  |D0913      |    S445  |B379265    |   25               |
    | Jacksonville, FL    |   5            | 6            |  S192	   |B20121654	|K3666179    |M01	        |AC20000	  |CM200000    |   N-HSS    |V-HSS-V1	  |D1382      |   S69	 |B378436    |   25               |
    | Miami, FL           |  4             | 5            |  S192	   |B20114433	|K3597506    |M02	        |AC20000	  |CM200000    |   N-CON-H  |V-CON-H-V3	  |D0909      |   S69	 |B378435    |   25               |
    | Toledo, OH          |  3             | 4            |  S527	   |B90016427	|K3661863    |M03	        |AC90009	  |CM900054    |   N-CON-H  |V-CON-H-V3	  |D0916      |   S461	 |B379835    |   25               |
    | Detroit, MI         | 2              | 3            |  S527	   |B90016423	|K3600561    |M03	        |AC90009	  |CM900045    |   N-HSS    |V-HSS-V1	  |D1333      |    S461  |B379832    |   25               |
    | Chicago, IL         | 3              | 5            |  S192	   |B20137247	|K3939064    |M08	        |AC20000	  |CM200271    |   N-HSS    |V-HSS-V1	  |D1334      |  S461	 |B379827    |   25               |


  @ANGULAR @ACCEPTANCE @US
  Scenario Outline: Transaction 26
    Given we have no cookies
    Given we land on bex bucket
    Given I'm searching for a hotel in "<destinationLocation>"
    And I want to travel between <startDateShift> days from now and <endDateShift> days from now
    And I use OPM T1 link with BID <BID> and SID <SID>
    And i use SEM U link with SID <SID4>, BID <BID4>, CMID <CMID2>, ACID <ACID2>, KID <KID2> and MID <MID2>
    And i use DBM link with NID <NID>, VID <VID> and DID <DID>
    And i use SEM B link with SID <SID3>, BID <BID3>, CMID <CMID>, ACID <ACID>, KID <KID> and MID <MID>
    And i use AFF link with siteID <siteID>
    And i book on BEX
    And i save the search details finishing transaction <transactionNumber>

  Examples:
    | destinationLocation | startDateShift | endDateShift |NID           |  VID                |DID       | SID3       | BID3          |  CMID       |  ACID       |  KID       |  MID       |  SID4       | BID4       |  CMID2       |  ACID2       |  KID2       |  MID2       |              siteID                      | BID       | SID       | transactionNumber |
    | Chicago, IL         | 3              | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1476     |     S526   | B90016413     |  K3605636	 |   M03       |AC90000	    |CM900020    |    S11	   | B20114428  |  K3598361	   |   M01        |AC20017	    |CM200000     |   OOTtr9mlaCk-FO60Ohp0gmvfhTagtD1rsg     | S270      | B378436   | 26              |
    | Irvine, CA          | 5              | 6            |  N-ISR-C	 |V-ISR-C-F1D		   |D0461     |     S526   |	B90016427  |	K3661863 |	M03        |AC90000	    |CM900024    |    S526	   |B90016400	|K3605571	   |M03	          |AC90001	    |CM900070     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     | S270	     |B378435    | 26              |
    | Springfield, IL     |  6             | 7            |  N-ISRH-RFL  |V-ISRH-RFL-V1	       |D1183     |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S11	   |B30140468	|K3658591	   |M03	          |AC30000	    |CM300000     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     |   S141	 |B377601    | 26              |
    | Houston, TX         |  7             | 8            |  N-TW-ISRH	 |V-TW-ISRH-F3A		   |D0463     |     S527   |	B90016417  |	K3662873 |	M03        |AC90000	    |CM900020    |    S192	   |B30140564	|K3662126	   |M03	          |AC30000	    |CM300013     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     |  S174	 |B379716    | 26              |
    | Phoenix, AZ         |  6             | 7            |  N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1481     |     S527   |	B90016408  |	K3662830 |	M03        |AC90000	    |CM900019    |    S11	   |B30140490	|K3939356	   |M03	          |AC30000	    |CM300000     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     |   S174	 |B379593    | 26              |
    | Jacksonville, FL    |   5            | 6            |  N-HFA-545	 |V-HFA-545-V1SS-T5B   |D1480     |     S527   |	B90016459  |	K3663340 |	M08        |AC90000	    |CM900035    |    S362	   |B20114434	|K3598946	   |M03	          |AC20000	    |CM500024     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     |    S174	 |B379592    | 26              |
    | Miami, FL           |  4             | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T5C   |D1475     |     S526   |	B90016406  |	K3605626 |	M03        |AC90000	    |CM900039    |    S11	   |B20114407	|K3598130	   |M03	          |AC20016	    |CM200000     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     |   S3	     |B379816    | 26              |
    | Toledo, OH          |  3             | 4            |  N-HFA-545	 |V-HFA-545-V1SS-T6C   |D1491     |     S526   |	B90016430  |	K3663144 |	M03        |AC90000	    |CM900025    |    S11	   |B20114407	|K3598130	   |M02	          |AC20000	    |CM200000     |   UO85MF6im/8-2YrhP/uhuf7HvXtdVqiAxQ     |    S3	 |B379815    | 26              |
    | Detroit, MI         | 2              | 3            |  N-HFA-545	 |V-HFA-545-V1SS-T3A   |D1474     |     S526   |	B90016419  |	K3662889 |	M03        |AC90000	    |CM900021    |    S11	   |B90016381	|K3600806	   |M03	          |AC90003	    |CM900010     |   AysPbYF8vuM-f17m*Tcb*V5htUmQ3g6lMw     |  S3       |B379803    | 26              |
    | Chicago, IL         | 3              | 5            |  N-ISR-C	 |V-ISR-C-F6D		   |D0461     |     S527   |	B90016426  |	K3599862 |	M03        |AC90000	    |CM900054    |    S192	   |B30140568	|K3662168	   |M03	          |AC30000	    |CM300013     |   InyfooKypHI-ax9YVHzmSRA0ldsoBZ7gfQ     |    S358	 |B377857    | 26              |
    | Irvine, CA          | 5              | 6            |  N-TW-ISRA	 |V-TW-ISRA-F3		   |D0303     |     S526   |	B20121290  |	K3642284 |	M03        |AC90000	    |CM900051    |    S11	   |B20121569	|K3665234	   |M03	          |AC20000	    |CM200203     |   vl0mfKZlvKU-e2eXrIzrNczqfv1EuLNt*g     |     S358  |B377702    | 26              |
    | Springfield, IL     |  6             | 7            |  N-ISR-C	 |V-ISR-C-F5A		   |D0460     |     S11    |	B90016430  |	K3663128 |	M03        |AC90000	    |CM900025    |    S11	   |B20121413	|K3663688	   |M01	          |AC20000	    |CM200000     |   HBLvzQS2RdU-o0qkS119P0hw8c9GepJgVQ     |    S445	 |B379795    | 26              |
    | Houston, TX         |  7             | 8            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1475     |     S526   |	B90016400  |	K3605571 |	M03        |AC90000	    |CM900063    |    S11	   |B20136790	|K3818809	   |M08	          |AC20000	    |CM200258     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     |   S445	 |B379371    | 26              |
    | Phoenix, AZ         |  6             | 7            |  N-HFA-545	 |V-HFA-545-V1SS-T6D   |D1485     |     S526   |	B90016512  |	K3940849 |	M03        |AC90000	    |CM900061    |    S11	   |B30140782	|K3666914	   |M03	          |AC30000	    |CM300016     |   vl0mfKZlvKU-3LopHC4eZL4R2MBr6o5CRw     |     S445  |B379265    | 26              |
    | Jacksonville, FL    |   5            | 6            |  N-ISR-C	 |V-ISR-C-F2D		   |D0065     |     S11    |	B90016425  |	K3599861 |	M08        |AC90000	    |CM900029    |    S192	   |B20121654	|K3666179	   |M01	          |AC20000	    |CM200000     |   UO85MF6im/8-H1p3amufzEM7f0d3iFIq8Q     |    S69	 |B378436    | 26              |
    | Miami, FL           |  4             | 5            |  N-HFA-545	 |V-HFA-545-V1SS-T5D   |D1479     |     S526   |	B90016404  |	K3605621 |	M03        |AC90000	    |CM900019    |    S192	   |B20114433	|K3597506	   |M02	          |AC20000	    |CM200000     |   fKits46xEtc-iNf39XZbwo06HY/mMAZbjQ     |    S69	 |B378435    | 26              |
    | Toledo, OH          |  3             | 4            |  N-ISR-C	 |V-ISR-C-F7D		   |D0461     |     S526   |	B90016512  |	K3940819 |	M03        |AC90000	    |CM900061    |    S527	   |B90016427	|K3661863	   |M03	          |AC90009	    |CM900054     |   GFBeectgAps-z2NivheXdjYvL46f4bn5fQ     |    S461	 |B379835    | 26              |
    | Detroit, MI         | 2              | 3            |  N-ISR-C	 |V-ISR-C-F2D		   |D0206     |     S526   |	B90016413  |	K3605636 |	M03        |AC90000	    |CM900020    |    S527	   |B90016423	|K3600561	   |M03	          |AC90009	    |CM900045     |   UO85MF6im/8-cj1yE6xqmMwEPS55joo4yg     |     S461  |	B379832  | 26              |
    | Chicago, IL         | 3              | 5            |  N-ISR-C	 |V-ISR-C-F6A		   |D0461     |     S526   |	B90016425  |	K3599861 |	M03        |AC90000	    |CM900054    |    S192	   |B20137247	|K3939064	   |M08	          |AC20000	    |CM200271     |   GFBeectgAps-Uo/IiB9U5xi4Dy61A7BwJg     |   S461	 |B379827    | 26              |
