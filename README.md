# Overview
Simple command line calculator. Supports the following operations: add, sub, mult, div, and let. The operations can be nested as can be seen in the examples below. The `let` operation allows a value to be assigned to a variable and the variable can then be used in the calculation. All operations can be nested. Some examples below show some use cases.

Examples  | Answer
----------|-------
add(2, 5) | 7.0
sub(1, 9) | -8.0
mult(3, 14)| 42.0
div(47, 2)| 23.5
add(2, mult(3, 4)) | 14.0
let(a, 5, add(a, a)) | 10.0
let(a, 5, let(b, mult(a, 10), add(b, a))) | 55
let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))) | 40
let(a, 5, let(b, mult(a, let(c, 4, add(c,a))), add(c, b))) | 49

## Requirements
* Maven
* Java
* Internet connection (downloading dependencies)

# Building
Step-by-step instructions on how to build the code from source

1.  Clone the repository:
```
    git clone https://github.com/cwagnello/calculator
```
2. Run maven
```
    mvn clean package 
```

# Running
Given an executable jar file. The Calculator can be run as follows:
```
    java -DLOG_LEVEL=INFO -Dlogback.configurationFile=<path/to/config/file>/logback.xml -jar Calculator-0.2.0.jar
```
The logging is done via logback. The log level can be set by passing in the system property `LOG_LEVEL`. Also the configuration file for logback needs to be specified as one is not built into the application. A sample config is included in the project at [logback.xml](https://github.com/cwagnello/calculator/blob/main/src/main/resources/logback.xml)

# Continuous Integration
https://app.circleci.com/pipelines/github/cwagnello/calculator


#TODOs
- Add more error checking on inputs
- Add CircleCI config to store jar artifact
