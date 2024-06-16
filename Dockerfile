# Use Ubuntu 22.04 as base image
FROM ubuntu:22.04

# Install prerequisites
RUN apt-get update \
    && apt-get install -y wget gnupg2 openjdk-17-jdk maven vim curl git \
    && rm -rf /var/lib/apt/lists/*

# Import MongoDB public GPG key
RUN wget -qO - https://www.mongodb.org/static/pgp/server-6.0.asc | apt-key add -

# Add MongoDB repository to sources list
RUN echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/6.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-6.0.list

# Disable interactive prompts during tzdata installation
ENV DEBIAN_FRONTEND=noninteractive

# Time zone settings
ENV TZ=Asia/Seoul

# Update package list and install MongoDB
RUN apt-get update \
    && apt-get install -y mongodb-org \
    && rm -rf /var/lib/apt/lists/*

# Create necessary directories
RUN mkdir -p /data/db /data/configdb

# Change ownership of directories
RUN chown -R mongodb:mongodb /data/db /data/configdb

# Set working directory
WORKDIR /root/project

# Add project files
ADD run.sh /root/project/run.sh

RUN chmod +x /root/project/run.sh

EXPOSE 27017

CMD ["/bin/bash", "./run.sh"]

# Set github token for environment variable. Please be careful not to leak the API key.
# ENV GITHUB_TOKEN={OUR_PUBLIC_API_TOKEN}
# In the submission file, the our API code will be present.