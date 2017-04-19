@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


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