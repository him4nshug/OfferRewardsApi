# Spring Boot "Microservice" Example Project

This is a sample Java / Gradle / Spring Boot (version 1.5.6) application that can be used as a starter for creating a microservice complete with built-in health check, metrics and much more. I hope it helps you.

## How to Run 

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.8 and Gradle 
* You can build the project and run the tests by running ```./gradlew clean build```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test build/lib/OfferRewardsApi-0.0.1-SNAPSHOT.jar

```
* Check the stdout or boot_example.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2017-08-29 17:31:23.091  INFO 19387 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-08-29 17:31:23.097  INFO 19387 --- [           main] com.khoubyari.example.Application        : Started Application in 22.285 seconds (JVM running for 23.032)
```

## About the Service

The service is just a OfferRewardsApi REST service. 

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar
spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
Given a record of every transaction during a three month period, calculate the reward points earned for each
customer per month and total.

Here are some endpoints you can call:

```
http://localhost:8080/offer/rewards

```

### calculate reward points for each purchase

```
POST /offer/rewards
Accept: application/json
Content-Type: application/json

Request body :
{
   
"transactions": [
   { "customerId":"1",
    "amount":101,
    "transactionDate":"2022/10/09"
   },
   { "customerId":"1",
    "amount":102,
    "transactionDate":"2022/10/08"
   },
   { "customerId":"1",
    "amount":101,
    "transactionDate":"2022/09/08"
   },
   { "customerId":"2",
    "amount":101,
    "transactionDate":"2022/09/09"
   },
   { "customerId":"2",
    "amount":102,
    "transactionDate":"2022/09/08"
   },{ "customerId":"2",
    "amount":101,
    "transactionDate":"2022/08/08"
   },
    { "customerId":"3",
    "amount":101,
    "transactionDate":"2022/10/09"
   },
   { "customerId":"3",
    "amount":102,
    "transactionDate":"2022/07/08"
   },{ "customerId":"3",
    "amount":101,
    "transactionDate":"2022/08/08"
   },
   { "customerId":"4",
    "amount":101,
    "transactionDate":"2022/03/09"
   },
   { "customerId":"4",
    "amount":102,
    "transactionDate":"2022/04/08"
   },
   { "customerId":"4",
    "amount":101,
    "transactionDate":"2022/05/08"
   }
]


}

RESPONSE: HTTP 200 (Created)
sample response :
{
    "customerRewardPointsVO": [
        {
            "customerID": "1",
            "aggregateRewardPoints": 158,
            "rewardPointsPerMonth": {
                "OCTOBER": 106,
                "SEPTEMBER": 52
            }
        },
        {
            "customerID": "2",
            "aggregateRewardPoints": 158,
            "rewardPointsPerMonth": {
                "AUGUST": 52,
                "SEPTEMBER": 106
            }
        },
        {
            "customerID": "3",
            "aggregateRewardPoints": 158,
            "rewardPointsPerMonth": {
                "OCTOBER": 52,
                "AUGUST": 52,
                "JULY": 54
            }
        },
        {
            "customerID": "4",
            "aggregateRewardPoints": 158,
            "rewardPointsPerMonth": {
                "MAY": 52,
                "MARCH": 52,
                "APRIL": 54
            }
        }
    ]
}
```


