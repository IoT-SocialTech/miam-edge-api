#build stage
FROM maven:3.8.4-openjdk-17 AS build
#set the working directory in the container
WORKDIR /app
#copy the pom.xml and source code
COPY pom.xml .
COPY src ./src
#package the application
RUN mvn clean package

#run stage
FROM openjdk:17.0.2-slim-buster
#set the working directory in the container
WORKDIR /app
#copy the JAR from the build stage to the run stage
COPY --from=build /app/target/edgeApi-0.0.1-SNAPSHOT.jar .
#expose the port the app runs on
EXPOSE 8081
#run the JAR file
ENTRYPOINT ["java", "-jar", "edgeApi-0.0.1-SNAPSHOT.jar"]