FROM tomcat:9.0-jdk8-openjdk

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set environment variables for JMX
ENV CATALINA_OPTS="-Dcom.sun.management.jmxremote \
  -Dcom.sun.management.jmxremote.port=9999 \
  -Dcom.sun.management.jmxremote.rmi.port=9999 \
  -Dcom.sun.management.jmxremote.ssl=false \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.local.only=false \
  -Djava.rmi.server.hostname=0.0.0.0"

# Install PostgreSQL driver
RUN mkdir -p $CATALINA_HOME/lib && \
    curl -L https://repo1.maven.org/maven2/org/postgresql/postgresql/42.3.1/postgresql-42.3.1.jar \
    -o $CATALINA_HOME/lib/postgresql-42.3.1.jar

# Copy source and build
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -DskipTests

# Copy the built WAR file to the webapps directory
COPY target/Lab3.war $CATALINA_HOME/webapps/

# Update the database configuration for Docker
RUN mkdir -p $CATALINA_HOME/webapps/Lab3/WEB-INF/classes/ && \
    cp src/main/resources/hibernate.cfg.xml $CATALINA_HOME/webapps/Lab3/WEB-INF/classes/ && \
    sed -i 's|jdbc:postgresql://pg:5432/studs|jdbc:postgresql://postgres:5432/studs|g' $CATALINA_HOME/webapps/Lab3/WEB-INF/classes/hibernate.cfg.xml

# Create a volume for logs
VOLUME $CATALINA_HOME/logs

# Expose ports for web access and JMX
EXPOSE 8080 9999

CMD ["catalina.sh", "run"]