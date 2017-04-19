@US @JOB
Feature: Supply Jobs

  Scenario Outline: Retail Hotel Catalog Fetch Job
    Given I run the shell script /opt/p4/ops/3ls/retrieveRetailHotelCatalogs.sh
    And I set the following job params:
      | jspringjobtcp                           |
      | hotel/retail/RetailHotelCatalogFetchJob |
    And job logs will be stored in <dir>
    And I want to refer to it as CATJ
    And I run the job on port 2001 and wait for success <success>
    Then the number of active IAN hotels is greater than 160000
    And I get 1 emails from <sender> with subject <subject> since last 5 minutes with following in the body:
      | following hotel IDs |

    Examples: data
      | dir                                                      | sender                | subject                                        | success                                            |
      | hotwire.biz.jobs.hotel.retail.RetailHotelCatalogFetchJob | Hotwire Customer Care | Retail Hotel Catalog Fetch Job Report for date | Finished 'RetailHotelCatalogFetchJob' successfully |

  Scenario Outline: Xnet Inventory Summary Notification Job
    Given I set the following job params:
      | jrunjobtcp                     |
      | hotel.xnet.XnetNotificationJob |
      | -i                             |
    And job logs will be stored in <dir>
    And I want to refer to it as XIS
    And I run the job on port 2002 and wait for success <success>
    Then I get 3 emails from <sender> with subject <subject> since last 5 minutes with following in the body:
      | Your current inventory on |
      | on the calendar below     |

    Examples: data
      | dir                                             | sender                | subject                 | success                                     |
      | hotwire.biz.jobs.hotel.xnet.XnetNotificationJob | hotelhelp@hotwire.com | Hotel Inventory Summary | Finished 'XnetNotificationJob' successfully |

  Scenario Outline: Xnet Create New Hotel Default User Job
    Given I set the xnet default user data
    And I set the following job params:
      | jrunjobtcp                                  |
      | hotel.xnet.XnetCreateNewHotelDefaultUserJob |
    And job logs will be stored in <dir>
    And I want to refer to it as XDU
    And I run the job on port 2003 and wait for success <success>
    Then I validate the xnet default user data
    And I get 1 emails from <sender> with subject <subject> since last 3 minutes with following in the body:
      | from San Francisco        |
      | simply follow these steps |

    Examples: data
      | dir                                                          | sender                | subject                          | success                                                  |
      | hotwire.biz.jobs.hotel.xnet.XnetCreateNewHotelDefaultUserJob | hotelhelp@hotwire.com | Welcome to the Hotwire Extranet! | Finished 'XnetCreateNewHotelDefaultUserJob' successfully |

  Scenario Outline: Hotel Rating Recommendation Job
    Given I set the following job params:
      | jspringjobtcp                                |
      | hotel/benchmark/HotelRatingRecommendationJob |
    And job logs will be stored in <dir>
    And I want to refer to it as HRJ
    Then I run the job on port 2004 and wait for success <success>

    Examples: data
      | dir                                                           | success                                              |
      | hotwire.biz.jobs.hotel.benchmark.HotelRatingRecommendationJob | Finished 'HotelRatingRecommendationJob' successfully |

  Scenario Outline: GSR_Generation Job
    Given I set the following job params:
      | jrunjobtcp                        |
      | hotel.benchmark.GSR_GenerationJob |
    And job logs will be stored in <dir>
    And I want to refer to it as GSR
    Then I run the job on port 2005 and wait for success <success>

    Examples: data
      | dir                                                | success                                   |
      | hotwire.biz.jobs.hotel.benchmark.GSR_GenerationJob | Finished 'GSR_GenerationJob' successfully |

  Scenario Outline: PullStatusNotificationJob
    Given I set the following job params:
      | runSingletonScriptTcp                   |
      | -s                                      |
      | runNotificationJobTcp                   |
      | -o                                      |
      | notifications/PullStatusNotificationJob |
    And job logs will be stored in <dir>
    And I want to refer to it as PSN
    Then I run the job on port 2006 and wait for success <success>

    Examples: data
      | dir                                                      | success                                                                       |
      | hotwire.biz.jobs.notifications.PullStatusNotificationJob | Finishing Spring job, config file:notifications/PullStatusNotificationJob.xml |

  Scenario Outline: FailedNotificationJob
    Given I set the following job params:
      | runSingletonScriptTcp               |
      | -s                                  |
      | jspringjobtcp                       |
      | -o                                  |
      | notifications/FailedNotificationJob |
    And job logs will be stored in <dir>
    And I want to refer to it as FSN
    Then I run the job on port 2007 and wait for success <success>

    Examples: data
      | dir                                                  | success                                       |
      | hotwire.biz.jobs.notifications.FailedNotificationJob | Finished 'FailedNotificationJob' successfully |
