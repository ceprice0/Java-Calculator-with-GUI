FROM eclipse-temurin:17-jre

WORKDIR /app

COPY calculator.jar .

CMD ["java", "-jar", "calculator.jar"]