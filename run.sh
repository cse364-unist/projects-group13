#!/bin/bash

mongod --bind_ip_all &

cd ./backend

mvn package

java -jar ./target/cse364-project-1.0-SNAPSHOT.jar