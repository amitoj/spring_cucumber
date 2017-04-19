@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


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