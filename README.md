# Java Calculator with GUI

A Java Swing desktop calculator used as a DevOps case study. It demonstrates
the full lifecycle (Plan, Code, Build, Test, Release, Deploy, Operate, Monitor)
with Git/GitHub, Maven, JUnit, Jenkins and Docker.

## What it does

A simple calculator with a Swing graphical interface for addition, subtraction,
multiplication and division. The arithmetic lives in a separate
`CalculatorEngine` class so it can be unit tested on its own, and the same jar
can also run in a headless (text) mode so it works inside a Docker container.

## Tech stack

- **Java 17**, **Swing** for the GUI
- **Maven** build; **JUnit 5** tests
- **Docker** multi-stage build
- **Jenkins** declarative pipeline (`Jenkinsfile`)

## Project layout

```text
src/main/java/cal/Calculator.java        Swing GUI (dispatches to CLI when headless)
src/main/java/cal/CalculatorEngine.java  Pure arithmetic (unit tested)
src/main/java/cal/CalculatorCli.java     Headless / command-line mode
src/test/java/cal/CalculatorEngineTest.java  JUnit 5 tests
pom.xml                                  Maven build
Dockerfile                               Multi-stage build + headless runtime
Jenkinsfile                              CI/CD pipeline
```

## Build and test

```bash
mvn clean verify
```

This compiles the code, runs the JUnit tests and packages `target/calculator.jar`.

## Run it

Graphical mode (on a desktop with a display):

```bash
java -jar target/calculator.jar
```

Headless / command-line mode (one calculation, then exit):

```bash
java -jar target/calculator.jar 6 / 3      # prints 6.0 / 3.0 = 2.0
```

## Run in Docker

The GUI cannot draw inside a container, so the image runs the calculator in its
headless mode.

```bash
docker build -t calculator .
docker run --rm calculator            # runs a short demo
docker run --rm calculator 6 / 3      # does one calculation
```

## CI pipeline (Jenkins)

The `Jenkinsfile` defines these stages: Checkout -> Build -> Unit Tests ->
Package -> Docker Build -> Deploy (headless run). Point a Jenkins job at this
repo and configure Maven (`Maven-3.9`) and JDK (`JDK-17`) in the Jenkins global
tools. A GitHub webhook (or a pollSCM/cron trigger) starts the build on each
push.

## Screenshots

### Jenkins Pipeline

![Pipeline](img/jenkins-pipeline.png)

### Successful Build

![Successful Build](img/build-success.png)

## Author

Calum Price
