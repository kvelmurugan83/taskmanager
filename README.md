# Task Manager
Task Manager Application - Single Page application build using Anuglar 5 as UI and Spring boot MicroServices is back-end services

Running Spring boot Micro service using Maven.
# mvnw package && java -jar target/taskManagerApp-0.0.1-SNAPSHOT.jar

Building docker image for TaskManager API

# mvnw install dockerfile:build

After Building the docker image, To execute the below command to run the task manager API Application in docker 
# docker run -p 8080:8080 -t kvel/taskmanagerapi

Current version of Task manager application implemented using only the H2 database (in-memory database), it will be converted to MySql

Reference : https://spring.io/guides/gs/spring-boot-docker/