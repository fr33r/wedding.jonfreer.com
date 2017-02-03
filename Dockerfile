FROM tomcat:8-jre8
MAINTAINER Jon Freer <freerjm@miamioh.edu>

#installs nano to allow for viewing and altering of files.
RUN alias ll="ls -alF"
RUN apt-get update
RUN apt-get install nano

#copies the .war file into the tomcat /webapps/ folder.
COPY *.war /usr/local/tomcat/webapps/

#starts up tomcat.
RUN $CATALINA_HOME/bin/startup.sh
#CMD ["catalina.sh", "run"]
