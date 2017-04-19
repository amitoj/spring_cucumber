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