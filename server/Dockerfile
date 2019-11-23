FROM tomcat:9-jre8-alpine

ADD target/server-1.3.war /usr/local/tomcat/webapps/bsuir.war

EXPOSE 8080

CMD ["catalina.sh", "run"]