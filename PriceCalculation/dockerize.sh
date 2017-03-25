#!/bin/bash
mvn clean install
docker build -t pricerq .
echo to run just docker run -p 8090:8090 pricerq
