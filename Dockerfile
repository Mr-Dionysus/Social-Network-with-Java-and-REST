FROM tomcat:10.1-jdk21-temurin-jammy

# Set the working directory inside the container
WORKDIR /usr/local/tomcat/webapps/

# Copy the WAR file from the target directory into Tomcat's webapps directory
COPY target/ROOT.war /usr/local/tomcat/webapps/

# Expose port 8080 for Tomcat
EXPOSE 8080

# Default command to run Tomcat
CMD ["catalina.sh", "run"]

