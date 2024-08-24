# Use a Gradle image with Java 17
FROM public.ecr.aws/docker/library/gradle:7.5.1-jdk17 AS build

# Set the working directory in Docker
WORKDIR /home/gradle/project

# Copy the Gradle configuration files
COPY build.gradle settings.gradle gradle.lockfile /home/gradle/project/

# Define a volume for dependency lock
VOLUME /home/gradle/project/gradle.lockfile

# Copy the rest of the project
COPY src /home/gradle/project/src

# Build the project without tests
RUN gradle build --no-daemon -x test

# Use OpenJDK 17 slim for the runtime stage
FROM public.ecr.aws/docker/library/openjdk:17-slim

# Expose the port the app runs on
EXPOSE 8081

# Copy the built artifact from the build stage
COPY --from=build /home/gradle/project/build/libs/*.jar /app/app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

