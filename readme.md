# Project Title

JUnit and Jira integration to create test execution based on unit tests

## Getting Started
mvn install
mvn -PcustomTestRunner

### Prerequisites

* Maven
* Java


### Installing

1. Add your Jira credentials (password, username) to config file
JiraClient/src/test/resources/config.properties

2. Install Maven dependencies
```
mvn install
```

## Running the tests
````
mmvn -PcustomTestRunner
````

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Apache http client](https://hc.apache.org/httpcomponents-client-ga/quickstart.html)
* [JSON parser](https://github.com/google/gson)
