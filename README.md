# rewards Application

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

This application has below functionality :
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction

(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.shop.rewards.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Testing the application

 Open any Rest client such as Postman :
 1. create record : POST : http://localhost:8080/records/
 
 request json :
 {
	"skuId": "101",
	"amount": 300
}

response json :
{
    "id": 1,
    "skuId": "101",
    "amount": 300,
    "purchaseDate": "2021-10-06T21:40:30.983+00:00",
    "last3MonthRewards": {
        "total": 450,
        "OCTOBER": 450
    },
    "rewardPoints": 450
}

2. get all records : GET : http://localhost:8080/records/
3. get single record by Id GET : http://localhost:8080/records/1
4. find by skuid : http://localhost:8080/records/search/findBySkuId?skuId=101