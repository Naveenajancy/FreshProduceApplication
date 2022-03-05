# Based off https://www.codeprimers.com/multiple-spring-boot-container-using-docker-compose/
# Start with a base image containing Java runtime
FROM openjdk:11

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=./target/fresh-produce-app-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container - rename the file
ADD ${JAR_FILE} fresh-produce-app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/fresh-produce-app.jar"]