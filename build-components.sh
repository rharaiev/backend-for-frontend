#!/bin/bash

mvn -f books/pom.xml clean
mvn -f books/pom.xml package

mvn -f authors/pom.xml clean
mvn -f authors/pom.xml package

mvn -f web-sockets/pom.xml clean
mvn -f web-sockets/pom.xml package

mvn -f mobile-app/pom.xml clean
mvn -f mobile-app/pom.xml package

mvn -f client-app/pom.xml clean
mvn -f client-app/pom.xml package

docker build -t lohika/bff-books-service:1.0 -f books/Dockerfile books
docker build -t lohika/bff-authors-service:1.0 -f authors/Dockerfile authors
docker build -t lohika/bff-web-sockets-service:1.0 -f web-sockets/Dockerfile web-sockets
docker build -t lohika/bff-mobile-app:1.0 -f mobile-app/Dockerfile mobile-app
docker build -t lohika/bff-client-app:1.0 -f client-app/Dockerfile client-app
