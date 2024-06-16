#!/bin/bash

mongod --bind_ip_all &

git clone https://github.com/cse364-unist/projects-group13.git

cd projects-group13/backend

git checkout milestone3

mvn package

java -jar ./target/cse364-project-1.0-SNAPSHOT.jar