FROM tomcat:8-jre8

MAINTAINER Jaroslav Kuchar - https://github.com/jaroslav-kuchar

RUN apt-get update && apt-get install -y \
	git \
	maven

RUN git clone https://github.com/KIZI/EasyMiner-Scorer.git
WORKDIR EasyMiner-Scorer
RUN mvn clean package

RUN cp target/easyminer-scorer.war  /user/local/tomcat/webapps/
RUN service tomcat restart

EXPOSE 8080