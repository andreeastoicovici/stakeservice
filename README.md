# REST API for Placing Bets

## Description

This is a simple REST API for placing bets, using only [com.sun.net.httpserver.HttpServer](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html).

There is no authentication nor session key keep-alive mechanism implemented. Users who want to place bets need a sessionKey. Session keys expire automatically 10 minutes after they are issued.

The available operations are:

* **GET** /{customerId}/session
* **POST** /{betofferid}/stake?sessionkey={sessionkey}
* Request body: {stake}
* **GET** /{betofferid}/highstakes

## Installation and Running the Server

For compiling, building and running tests you need [Maven](https://maven.apache.org/download.cgi).

To compile:
* $ mvn compile

To generate the jar file, which will be named "stakes-service.jar", in the "target" folder:
* $ mvn jar:jar

To run the tests:
* $ mvn test

To launch the application:

* $ java -jar stakes-service


