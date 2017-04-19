@ANGULAR @US
Feature: Hotel Search through BEX
     As a user I’d like to be able to search through more options rather than just opaque.

  Background: 


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