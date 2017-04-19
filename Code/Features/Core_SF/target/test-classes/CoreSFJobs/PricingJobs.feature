@US @JOB
Feature: Finance Jobs

  @JANKY
  Scenario Outline: WrightExpressCardActivationJob
    And I set the following job params:
      | jrunjobtcp                                         |
      | hotel.wrightexpress.WrightExpressCardActivationJob |
    And job logs will be stored in <dir>
    And I want to refer to it as WEXact
    And I run the job on port 4001 and wait for success <success>
    Then I validate the WEXActivationData

    Examples: data
      | dir                                                                 | success                                                 |
      | hotwire.biz.jobs.hotel.wrightexpress.WrightExpressCardActivationJob | Finished 'WrightExpressCardActivationJob' successfully. |

  @JANKY
  Scenario Outline: WrightExpressCardModifyDeactivateJob
    And I set the following job params:
      | jrunjobtcp                                               |
      | hotel.wrightexpress.WrightExpressCardModifyDeactivateJob |
    And job logs will be stored in <dir>
    And I want to refer to it as WEXdeact
    And I run the job on port 4002 and wait for success <success>
    Then I validate the WEXDeactivationData

    Examples: data
      | dir                                                                       | success                                                       |
      | hotwire.biz.jobs.hotel.wrightexpress.WrightExpressCardModifyDeactivateJob | Finished 'WrightExpressCardModifyDeactivateJob' successfully. |

  Scenario Outline: WrightExpressCardMonitorJob
    And I set the following job params:
      | jrunjobtcp                                      |
      | hotel.wrightexpress.WrightExpressCardMonitorJob |
    And job logs will be stored in <dir>
    And I want to refer to it as WEXmonitor
    And I run the job on port 4003 and wait for success <success>

    Examples: data
      | dir                                                              | success                                              |
      | hotwire.biz.jobs.hotel.wrightexpress.WrightExpressCardMonitorJob | Finished 'WrightExpressCardMonitorJob' successfully. |

  Scenario Outline: OrderAddOnJob
    And I set the following job params:
      | jspringjobtcp            |
      | orderaddon/OrderAddOnJob |
      | 30                       |
      | 1,5                      |
    And job logs will be stored in <dir>
    And I want to refer to it as OaddOn
    And I run the job on port 4004 and wait for success <success>
    Then I validate the OrderAddOnJob

    Examples: data
      | dir                                       | success                                |
      | hotwire.biz.jobs.orderaddon.OrderAddOnJob | Finished 'OrderAddOnJob' successfully. |

  @JANKY
  Scenario Outline: CurrencyExchangeRateJob
    And I set the following job params:
      | jrunjobtcp                   |
      | bill.CurrencyExchangeRateJob |
    And job logs will be stored in <dir>
    And I want to refer to it as CEx
    And I run the job on port 4005 and wait for success <success>
    Then I validate the CurrencyExchangeJob

    Examples: data
      | dir                                           | success                                          |
      | hotwire.biz.jobs.bill.CurrencyExchangeRateJob | Finished 'CurrencyExchangeRateJob' successfully. |

  @JANKY
  Scenario Outline: AuthReversalJob
    And I set the following job params:
      | jspringjob         |
      | fn/AuthReversalJob |
    And job logs will be stored in <dir>
    And I want to refer to it as AuthR
    And I run the job on port 4006 and wait for success <success>
    Then I validate the AuthReversalJob

    Examples: data
      | dir                                 | success                                  |
      | hotwire.biz.jobs.fn.AuthReversalJob | Finished 'AuthReversalJob' successfully. |

  Scenario Outline: PayPalIncompleteOrdersJob
    And I set the following job params:
      | jrunjobDebug                   |
      | bill.PayPalIncompleteOrdersJob |
      | 0                              |
      | 120                            |
    And job logs will be stored in <dir>
    And I want to refer to it as Paypal
    And I run the job on port 4007 and wait for success <success>

    Examples: data
      | dir                                             | success                                            |
      | hotwire.biz.jobs.bill.PayPalIncompleteOrdersJob | Finished 'PayPalIncompleteOrdersJob' successfully. |
