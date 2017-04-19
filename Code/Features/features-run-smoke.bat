SET modulePath="C:\p4\phoenix\main\test\Features"
::SET URL=-Dapplication_url=http://dev10.dev.hotwire.com:7001

:: run cucumber tests
:: profiles: smoke, limited, acceptance, regression, mt, localhost
:: By default tests will start against dev01, to use other environment add follow property
:: -Dapplication_url=http://dev10.dev.hotwire.com:7001

call mvn -f "%modulePath%\pom.xml" clean install -Psmoke,localhost --fail-at-end -X -Dapplication_url=http://www.qa.hotwire.com > bdd.features.log


:: generate report
mvn cucumber-aggregation:aggregate