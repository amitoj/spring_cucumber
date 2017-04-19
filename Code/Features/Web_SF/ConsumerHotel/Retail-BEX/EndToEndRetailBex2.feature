@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


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