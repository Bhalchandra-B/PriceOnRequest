#!/bin/bash
mvn clean install
docker build -t pricerq .
# to run just docker run pricerq
