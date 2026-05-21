# ConnectOs Exam - Cucumber Java Selenium

This is my implementation of the SEIT25 test automation task. I used the provided Cucumber-Java-Selenium framework and built on top of it with my own Base utility class, dynamic locators, and externalized test data.

## What I did

- Implemented all 3 scenarios in the feature file
- Created a reusable `Base` class with common Selenium methods (click, navigate, verify, etc.)
- Used dynamic XPath locators so I don't have to hardcode every element
- Stored test data in `.properties` files to keep it separate from the code
- Followed the Page Object Model approach with constants and locator classes

## Prerequisites

- Java 21
- Google Chrome (latest)
- Maven
- Eclipse IDE (or any IDE with Maven support)

## How to run

1. Open a terminal in the `cucumber-java-selenium` folder
2. Run `mvn test`

Or in Eclipse:
- Right-click `RunCucumberTest.java` > Run As > JUnit Test

The HTML report will be generated at `target/cucumber-report/cucumber.html` after a test run.

## Project structure

```
cucumber-java-selenium/src/test/
├── java/io/cucumber/
│   ├── RunCucumberTest.java        - Test runner
│   ├── base/Base.java              - Reusable Selenium methods
│   ├── constants/                  - String constants and element types
│   ├── locators/                   - Dynamic XPath templates
│   ├── utils/PropertyFile.java     - Reads .properties files
│   ├── core/                       - Hooks, Manager, Context (driver lifecycle)
│   └── glue/                       - Step definitions for each scenario
└── resources/
    ├── io/cucumber/features/       - Feature file (Gherkin scenarios)
    ├── homepagelists/              - Expected link list data
    ├── basicauth/                  - Auth credentials
    └── datatables/                 - Expected table data
```

## Notes

- Scenario 1 (Homepage links) is expected to fail because the actual site has more links than what the requirement specifies. The test correctly reports the mismatch.
- Scenario 2 (Basic Auth) uses embedded credentials in the URL to bypass the browser auth dialog.
- Scenario 3 (Data Tables) validates each cell in the table using a dynamic XPath that matches row position and column header.
