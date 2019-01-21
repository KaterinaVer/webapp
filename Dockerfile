FROM tomcat:9.0.14-jre8
COPY /target/*.war /usr/local/tomcat/webapps/