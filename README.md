# Kafka 

This maven-based project contains a microservices ecosystem over a kafka infrastructure.

0) To run the project one have the following options:

- mvn test. Compiles and runs the tests.
- mvn verify. Compiles, runs the test and generates the following reports: jacoco coverage

1) This project is a multi-module maven project with 3 modules:

- clientApp
* SpringBoot web application that offers 2 endpoints to create a retrevie orders. 
* Spring-jpa with H2 repository to persist the orders.
* Swagger available at: http://localhost:8080/swagger-ui/
* When an order is created, it drops a message into the topic 'order'

- palmettoApp
* SpringBoot application that listens to 'order' topic and writes in 'notification' topic.
* When a message in the 'order' topic arrives, the order is prepared (by waiting 5 seconds) and a message is sent in the 'notification' topic

- courierApp
* SpringBoot application that listens to 'notification' topic and writes in 'notification' topic.
* When a message in the 'notification' (with status 'READY') arrives, the order is delivered (by waiting 5 seconds) and a message is sent in the 'notification' topic (with status 'DELIVERED')

2) When running the project locally it is necessary to launch the kafka docker image. This project already includes a docker-compose.yaml. Just run: docker-compose up -d

3) Code covered with Unit test + coverage via JaCoCo plugin. Those tests are the ones with suffix '_Test'

4) Code covered with Integration tests using test containers with Kafka docker images. Those tests are the ones with suffix '_IT'