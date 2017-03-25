FROM openjdk:8-jre
LABEL maintainer brbingewar@gmail.com
RUN mkdir /app
COPY target/PriceCalculation-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
EXPOSE 8090
CMD [ "java","-jar","PriceCalculation-0.0.1-SNAPSHOT.jar"]
