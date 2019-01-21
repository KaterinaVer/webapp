FROM tomcat:9.0.14-jre8
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY /target/ROOT.war /usr/local/tomcat/webapps/