# ---- Stage 1: Build and test with Maven ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# Cache dependencies first for faster rebuilds.
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

# Compile, run the unit tests, and package the jar.
COPY src ./src
RUN mvn -B clean verify

# ---- Stage 2: Small runtime image ----
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/target/calculator.jar calculator.jar

# There is no display inside the container, so run the calculator in its
# headless (text) mode. Pass three arguments to do a single calculation,
# e.g. docker run calculator 6 / 3
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "calculator.jar"]
