@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


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