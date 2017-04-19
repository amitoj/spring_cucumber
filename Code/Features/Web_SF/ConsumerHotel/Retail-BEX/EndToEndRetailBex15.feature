@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


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