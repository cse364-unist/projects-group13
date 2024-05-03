#!/bin/bash

mongod --bind_ip_all &

git clone https://$GITHUB_TOKEN@github.com/cse364-unist/projects-group13.git

cd projects-group13/backend

git checkout milestone2

mvn jacoco:report

mvn package

java -jar ./target/cse364-project-1.0-SNAPSHOT.jar