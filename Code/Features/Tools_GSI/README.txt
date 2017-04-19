▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
►Tools GSI Run Configuration
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
Wiki:
	•https://redspace.jiveon.com/docs/DOC-10401
	•https://redspace.jiveon.com/docs/DOC-11616
	•https://redspace.jiveon.com/docs/DOC-12460
Email: SFO - Tools Dev team <SFO-ToolsDevteam@expedia.com>
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
§Features§
	•@TOOLS: Tools Selenium tests (C:\p4\phoenix\main\test\Features\Tools_GSI\Tools)
	•@ToolsAPI: Tools REST tests (C:\p4\phoenix\main\test\Features\Tools_GSI\ToolsAPI)
——————————————————————————————————————————————————————————————————————
§Dependency modules§
@TOOLS:
	• C3StepDefs:
	C:\p4\phoenix\modular\testing\C3StepDefs\2.X.X
	• C3PageObjects:
	C:\p4\phoenix\modular\testing\C3PageObjects\2.X.X
	• DatabaseSupport:
	C:\p4\phoenix\modular\testing\DatabaseSupport\1.X.X
	• DesktopPageObject(optional):
	C:\p4\phoenix\modular\testing\DesktopPageObjects\2.X.X
	• WebDriverSupport(optional):
	C:\p4\phoenix\modular\testing\DesktopPageObjects\2.X.X

@ToolsAPI:
	• ToolsApiSteps:
	C:\p4\phoenix\modular\testing\ToolsApiStepDefs\1.X.X
——————————————————————————————————————————————————————————————————————
§IDEA Cucumber defaults configuration§

@TOOLS:
►MAIN Branch (Dev, jslave)
	• Main class: cucumber.api.cli.Main
	• Glue: com.hotwire.test.steps
	• Working directory: C:\p4\phoenix\modular\testing\C3StepDefs\2.X.X
	• VM options:
	-ea -DtargetAppType=WebApp -Dapplication_url=http://dev10.dev.hotwire.com:7001
	• Use classpath of module: C3StepDefs
►RELEASE Branch	(QA)
	• Main class: cucumber.api.cli.Main
	• Glue: com.hotwire.test.steps
	• Working directory: C:\p4\phoenix\modular\testing\C3StepDefs\2.X.X
	• VM options:
	-ea -DtargetAppType=WebApp -Dapplication_url=http://www.qa.hotwire.com -Denv=qa
	• Use classpath of module: C3StepDefs

@ToolsAPI:
►Any Branch
	• Main class: cucumber.api.cli.Main
	• Glue: com.hotwire.test.steps
	• Working directory: C:\p4\phoenix\modular\testing\ToolsApiStepDefs\1.X.X
	• VM options: -ea -DbrowserType=htmlunit
	• Use classpath of module: ToolsApiSteps
——————————————————————————————————————————————————————————————————————
§Maven run parameters§
@TOOLS:
	►TBD(Currently there is an issue with database connection from feature projects)
	Path: C:\p4\phoenix\main\test\Features\Tools_GSI\Tools

@ToolsAPI:
	►mvn clean install -fae
	Path: C:\p4\phoenix\main\test\Features\Tools_GSI\ToolsAPI
——————————————————————————————————————————————————————————————————————
§CI TAGS§
Examples for smoke tests. For other tags the same.
►IDE:
    Program arguments: --tags=@SMOKE
