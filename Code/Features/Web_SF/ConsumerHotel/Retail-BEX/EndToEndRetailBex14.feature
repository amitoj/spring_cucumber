@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


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