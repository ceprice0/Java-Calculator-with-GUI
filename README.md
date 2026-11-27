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
Jenkinsfile7                             Workshop 9 direct EC2 deployment
Jenkinsfile2                             Workshop 9 Ansible deployment
ansible/deploy.yml                       Ansible playbook
ansible/inventory.ini.example            Inventory format example
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

Interactive mode (stays open for repeated calculations):

```bash
java -jar target/calculator.jar --interactive
```

## Keep the calculator running in Docker

Build the image and start an interactive container. Docker Desktop will show it
as running while the calculator waits for input:

```bash
docker build -t java-calculator-with-gui .
docker run -it --name java-calculator-with-gui java-calculator-with-gui --interactive
```

Enter calculations such as `6 / 3`; type `exit` to stop the container. To start
it in the background and attach later:

```bash
docker run -d -it --name java-calculator-with-gui java-calculator-with-gui --interactive
docker attach java-calculator-with-gui
```

Use `Ctrl+P`, then `Ctrl+Q` to detach without stopping it. Stop it with
`docker stop java-calculator-with-gui`.

## Run one-off Docker commands

The GUI cannot draw inside a container, so the image runs the calculator in its
headless mode.

```bash
docker build -t java-calculator-with-gui .
docker run --rm java-calculator-with-gui            # runs a short demo
docker run --rm java-calculator-with-gui 6 / 3      # does one calculation
```

## CI pipeline (Jenkins)

The existing `Jenkinsfile` checks out the source, builds and tests it with
Maven, and archives `target/calculator.jar`. Configure Maven (`Maven-3.9`) and
JDK (`JDK-17`) in Jenkins global tools. A GitHub webhook (or a pollSCM/cron
trigger) starts the build on each push.

## Workshop 9: deploy the existing image to EC2

`Jenkinsfile7` deploys the existing public image
`calum1/java-calculator-with-gui:latest` directly to webserver2.
`Jenkinsfile2` uses webserver2 as the Ansible control node to deploy the same
image to webserver3 and webserver4. These pipelines do not build or test the
application.

This image is a headless interactive CLI calculator, not an HTTP/PHP service.
The deployment keeps its calculator process running with an interactive TTY;
it does not listen on port 5000 or port 80. Verify it from the Jenkins console
or with `docker ps` and `docker logs` on the EC2 hosts rather than a browser.

### Jenkins prerequisites

- Install the Jenkins Git and SSH Agent plugins. The Jenkins execution agent
  must provide `sh`, `ssh`, and `scp`.
- Add one **SSH Username with private key** credential with username
  `ec2-user` and ID `ec2-ssh-key` (or enter its actual ID in the job parameter).
  Keep the private key in Jenkins; do not put it in this repository.
- Ensure the Jenkins agent trusts webserver2's SSH host key, and webserver2
  trusts the SSH host keys of webserver3 and webserver4. Verify host-key
  fingerprints before adding them to `known_hosts`; these pipelines leave SSH
  host-key checking enabled.
- Allow SSH TCP 22 from the Jenkins agent to webserver2, and from webserver2
  to webserver3/webserver4. The EC2 instances also need outbound HTTPS access
  to pull the public Docker Hub image. No inbound application port is needed.

### Task 1: deploy directly to webserver2

Create a Pipeline job using **Pipeline script from SCM** and set **Script Path**
to `Jenkinsfile7` (leave the existing CI job on `Jenkinsfile`). At build time,
enter webserver2's current public IP/DNS name and the Jenkins SSH credential ID.
The pipeline installs and starts Docker if needed, pulls the existing image,
and replaces the `java-calculator-with-gui` container.

### Task 2: deploy through Ansible on webserver2

Create another Pipeline job from the same SCM repository and set **Script Path**
to `Jenkinsfile2`. At build time, enter webserver2's current public IP/DNS name,
the current private IPv4 addresses of webserver3 and webserver4, and the Jenkins
SSH credential ID. The pipeline generates `ansible/inventory.ini` from those
parameters, copies it and `ansible/deploy.yml` to webserver2, then runs the
playbook with the forwarded Jenkins SSH agent.

`ansible/inventory.ini.example` shows the inventory format. The real inventory
is generated for each Jenkins run, so EC2 addresses are not committed. All
three instances should use Amazon Linux 2023 and the same EC2 key pair. Refresh
public addresses after stopping and starting an instance.

To interact with a deployed calculator, SSH to its host and run
`sudo docker attach java-calculator-with-gui`. Enter calculations such as
`6 / 3`; use **Ctrl+P**, then **Ctrl+Q** to detach without stopping it.

## Screenshots

### Jenkins Pipeline

![Pipeline](img/jenkins-pipeline.png)

### Successful Build

![Successful Build](img/build-success.png)

## Author

Calum Price
