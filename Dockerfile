FROM tomcat:10.1-jdk21-temurin-jammy

WORKDIR /usr/local/tomcat/webapps/

COPY target/Social-Network-with-Java-and-REST-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]

