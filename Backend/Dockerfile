# Build stage
FROM maven AS maven
WORKDIR /usr/src/app/backend
COPY . /usr/src/app/backend
RUN mvn package

# Run stage
FROM openjdk:17-jdk-slim
ARG JAR_FILE=/usr/src/app/backend/target/*.jar
WORKDIR /opt/app/backend
COPY --from=maven ${JAR_FILE} /opt/app/backend/app.jar

# Set the entrypoint to run the JAR file with the specified main class
ENTRYPOINT ["java", "-jar", "/opt/app/backend/app.jar"]
