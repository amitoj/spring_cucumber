@MOBILE @CLUSTER3 @CLUSTERALL @NO_PREPROD @NO_PROD_INTERNAL
Feature: Netscaler is a tool to redirect customers to appropriate Hotwire sites.

  Scenario: RNT-75 www.hotwire.com/uk on a mobile device should open mobile INTL site
    When I open the URL on a mobile device
    """
    http://www.hotwire.com/uk
    """
    Then I am redirected to the URL
    """
    http://www.hotwire.com/mobile/uk
    """

  Scenario: RNT-76 www.hotwire.com on a mobile device should open mobile site
    When I open the URL on a mobile device
    """
    http://www.hotwire.com
    """
    Then I am redirected to the URL
    """
    http://www.hotwire.com/mobile
    """

  Scenario: RNT-77 SEO link to hotel search results
    When I open the URL on a mobile device
    """
    http://www.hotwire.com/seo/hotel/sid/S11/bid/B30074641/kid/K3307665/mid/M02/d-city/San%20Francisco,%20CA
    """
    Then I see San Francisco, CA content

  Scenario: RNT-78 Hotel search URL opens mobile hotel search
    When I open the URL on a mobile device
    """
    http://www.hotwire.com/hotel/index.jsp
    """
    Then I am redirected to the URL
    """
    http://www.hotwire.com/mobile/hotel/index.jsp
    """

  Scenario: RNT-79 Car search URL opens mobile car search
    When I open the URL on a mobile device
    """
    http://www.hotwire.com/car/index.jsp
    """
    Then I am redirected to the URL
    """
    http://www.hotwire.com/mobile/car/index.jsp
    """

  Scenario: RNT-80 Air search URL opens forced desktop air search
    When I open the URL on a mobile device
    """
    http://www.hotwire.com/air/index.jsp
    """
    Then I am redirected to the URL
    """
    http://www.hotwire.com/air/index.jsp?mwr=1&isMobileFullSite=true
    """

  Scenario: RNT-81 SEO link to a hotel deal
    When I open the URL on a mobile device
    """
    http://www.hotwire.com/hotel/superPage.jsp?inputId=hotel-index&destCity=Burbank&nid=N-OTH-263&vid=V-OTH-263-V12&did=D0173&cid=334950309da944fdfbec37baa9156178&encDealHash=MTAwOjE3MzAyNjo4OTcxOTo0LjA6NDkuMDAwMDA4Olk6WTpZ&rid=r-166742600126&xid=x-103&wid=w-3&rs=20501&r=Y
    """
    Then I see hotel result page content

  Scenario: RNT-82 SEO link to a car deal
    When I open the URL on a mobile device
    """
    http://www.hotwire.com/car/search-options.jsp?inputId=car-index&startLocation=LAS&userInputType=1&startDay=14&startMonth=9&endDay=16&endMonth=9&xid=x-112&rid=r-149317206409&wid=w-3&rs=20501&referringDealId=149317206409
    """
    Then I see car result page content

  Scenario: RNT-83 Marketing link to home page
    When I open the URL on a mobile device
    """
    http://www.hotwire.com/seo/sid/S11/bid/B90000059/kid/K0000001/mid/M03&rct=j&q=hotwire
    """
    Then I see home page content