FROM openjdk:11.0.11-jdk-slim

# Define a work directory
WORKDIR /contacts-api

# Add all files to work directory
ADD . /contacts-api

# Update libraries
RUN apt-get update -y

# -B = Run in non-interactive (batch)
# -T = Thread count = 4
RUN ./mvnw -B -T 4 package -DskipTests=true

# Expose port for rest interface
EXPOSE 8080

# Provide entry-point
CMD ["/bin/sh", "./bin/entry-point.sh"]