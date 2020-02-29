FROM java:8

WORKDIR /

ADD build/libs/bsuir-student-backend-1.0.jar server.jar

ENTRYPOINT java -jar -Dspring.profiles.active=prod server.jar
