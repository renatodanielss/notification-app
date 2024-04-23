# Use the official maven/Java 17 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.8.1-openjdk-17-slim AS build
# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build a release artifact.
RUN mvn resources:resources clean install -DskipTests

# Use OpenJDK 17 for base image.
# https://hub.docker.com/_/openjdk
FROM openjdk:17-jdk AS final
# Copy the jar to the production image from the builder stage.
COPY --from=build /app/target/schedule-api.jar /schedule-api.jar
# Run the web service on container startup.
CMD ["java", "-jar", "/schedule-api.jar"]