FROM tomcat:11.0.13-jdk21-temurin-jammy

RUN apt-get update && apt-get install -y vim
RUN rm -rf /usr/local/tomcat/webapps
RUN mv /usr/local/tomcat/webapps.dist /usr/local/tomcat/webapps

COPY tomcat-users.xml /usr/local/tomcat/conf/
COPY context.xml /usr/local/tomcat/webapps/manager/META-INF/
COPY web.xml /usr/local/tomcat/conf/
COPY api.war /usr/local/tomcat/webapps/