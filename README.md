# Java Calculator with GUI

A Java calculator application demonstrating Continuous Integration (CI) using Jenkins, Docker and GitHub.

## Project Overview

This project contains a Java calculator application that is automatically built using a Jenkins Pipeline running in Docker.

## Technologies Used

- Java
- Git
- GitHub
- Jenkins
- Docker
- CI/CD Pipeline

## Pipeline Workflow

```text
GitHub Repository
        ↓
     Jenkins
        ↓
Checkout Source
        ↓
Clean Workspace
        ↓
Compile Java
        ↓
Package Application
        ↓
Verify Build
        ↓
Archive Artifact
        ↓
Build SUCCESS
```

## Jenkins Pipeline Stages

✅ Checkout Source

✅ Clean Workspace

✅ Compile Java Application

✅ Package Application

✅ Verify Build

✅ Archive Build Artifacts

## Build Process

The Jenkins pipeline performs the following actions automatically:

1. Pulls source code from GitHub.
2. Checks out the latest commit.
3. Cleans previous build files.
4. Compiles `Calculator.java`.
5. Creates build artifacts.
6. Verifies generated files.
7. Reports build status.

## Build Output

Successful compilation generates:

```text
Calculator.class
```

and packages the application for deployment.

## Screenshots

### Jenkins Pipeline

![Pipeline](img/jenkins-pipeline.png)

### Successful Build

![Successful Build](img/build-success.png)

## Author

Calum Price

## Latest Build Status

✅ SUCCESS