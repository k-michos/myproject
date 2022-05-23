
# My Project

### Demo for a simple Banking Customer Management Application. 

In the Graphical User Interface offered, we can navigate from home page to Customers page where all customers are displayed. Here, 
we can add a new customer, or navigate further to view more details/info of a specific customer. Before navigate deeper to customer's 
accounts and transactions, we can update the basic information of a customer or simply just delete the customer. Diving deeper to 
Customers Info page, we have an overview of total balance of all accounts and a table with all of customers accounts. Also, there is 
a functionality that opens a new current account for an existing customer. Finally, the user can navigate and see the total balance 
for all the accounts of a customer along with their respective transactions (upon pressing "All Transactions" button) or the user can 
choose to see the balance for a specific account along with the transactions made for this specific account (upon pressing "See 
Transactions" button).
## API Reference

#### Get all customers
```http
  GET /customers/all
```

#### Get customer
```http
  GET /customers/${id}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long` | **Required**. Id of customer to fetch |


#### Add customer
```http
  POST /customers/add
```

#### Update customer
```http
  PUT /customers/update
```

#### Delete customer
```http
  DELETE /customers/delete
```

#### New current account
```http
  PUT /customers/newCurrentAccount
```

#### Get customer info
```http
  GET /customers/getCustomerInfo/${customerID}
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `customerID`      | `Long` | **Required**. Id of customer to fetch |

#### Deposit
```http
  PUT /customers/deposit
```

#### Update customer
```http
  PUT /customers/withdraw
```






## Requirements

In order to deploy and run this Springboot application, you need:
- Java 17 
- Maven 3.8.5

Also, because the back-end stores its data to a mysql database, you need:
- a running mysql 8 instance

Finally, if you want to run it in a container using Docker, you need to have:
- docker
## Instructions

Below you may find the 3 cases of running the application, depending on what we want to do. Execute one of the three set of commands in the cloned project's path.
### 1) Run Locally

1. Clone the project

2. In application.properties file, make sure that the proper spring.datasource.url is set, according to the comment (1st one to run it locally and comment-out the other one).

3. Build the project (-DskipTests flag is to skip tests)

```bash
  mvn clean install -DskipTests
```

4. Run the Spring Boot application (mysql should be up and running)

```bash
  mvn spring-boot:run
```
* Execute commands in the cloned project's path


### 2) Using Docker with the published image

1. In application.properties file, make sure that the proper spring.datasource.url is set, according to the comment (2nd one to run it as Docker). If it is not, we need to proceed with set of commands No 3, because the application needs to be built.

2. We need to create the network on which we will run this docker-compose.yml script.

```bash
 docker network create app-mysql
```

3. Finally, execute the docker-compose command, so to start both dockers.
```bash
 docker-compose up
```
### 3) Using Docker with our build

1. In application.properties file, make sure that the proper spring.datasource.url is set, according to the comment (2nd one to run it as Docker). 

2. Build the application.
```bash
 mvn clean install -DskipTests
```

3. Build locally the Docker image using Dockerfile and the jar files made in previous step.
```bash
 docker build -t kmichos/myproject .
```

4. Push this image to DockerHub. (optional, this step is for publishing publicly the image of our docker)
```bash
 docker push kmichos/myproject
```

5. Then we need to create the network on which we will run this docker-compose.yml script. (To check if there is such network, do "docker network ls")
```bash
  docker network create app-mysql
```

6. Finally, execute the docker-compose command, so to start both dockers.
```bash
 docker-compose up
```
## GUI access

Home page: http://localhost:8080/myapp/webapp/home

## Postman

There is included a Postman suite that tests the rest API of the application: 
- myapp.postman_collection.json

This suite can be imported in Postman and execute the API calls described in API reference.