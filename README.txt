This is a Demo for a simple Banking Customer Management Application. 

-------Application Description-------

The offered API supplies the user with endpoints that can:
	-get all customers
	-get one customer
	-add new customer
	-update existing customer 
	-delete existing customer
	-deposit a specific amount in a customer's account
	-withdraw a specific amount (less or equal to current account's balance) from a customer's account
	-get one Customer's Info (that has detailed info of customers accounts and transactions)
	
In the Graphical User Interface offered, we can navigate from home page to Customers page where all customers are displayed. Here, 
we can add a new customer, or navigate further to view more details/info of a specific customer. Before navigate deeper to customer's 
accounts and transactions, we can update the basic information of a customer or simply just delete the customer. Diving deeper to 
Customers Info page, we have an overview of total balance of all accounts and a table with all of customers accounts. Also, there is 
a functionality that opens a new current account for an existing customer. Finally, the user can navigate and see the total balance 
for all the accounts of a customer along with their respective transactions (upon pressing "All Transactions" button) or the user can 
choose to see the balance for a specific account along with the transactions made for this specific account (upon pressing "See 
Transactions" button). 


-------Technical Details and Instructions to run the application-------

In order to deploy and run this Springboot application, you need Java 17 and Maven 3.8.5. Also, because 
the back-end stores its data to a mysql database, you need to have a running mysql 8 instance. If you
want to run it in a container using Docker, you need to have install docker, too.

Access to GUI home page: http://localhost:8080/myapp/webapp/home

Included a Postman suite that tests the rest API of the application: myapp.postman_collection.json
This suite can be imported in Postman and execute the API calls described above.

Commands: We have 3 cases depending on what we want to do. Execute one of the three set of commands in the cloned project's path

				1. To run it locally: 	In application.properties file, make sure that the proper spring.datasource.url is set, according to the comment (1st one to run it locally and comment-out the other one).

										- mvn clean install -DskipTests  (-DskipTests flag is to skip tests)

										- mvn spring-boot:run  	(mysql should be up and running)
									
				
				2. When using Docker: 	In application.properties file, make sure that the proper spring.datasource.url is set, according to the comment (2nd one to run it as Docker). If it is not, we need 
					(with the			to proceed with set of commands No 3, because the application needs to be built.
					published image)
										We need to create the network on which we will run this docker-compose.yml script.
				     					- docker network create app-mysql
				    	
										Finally, execute the docker-compose command, so to start both dockers.
										- docker-compose up
				
					
				3. When using Docker:	In application.properties file, make sure that the proper spring.datasource.url is set, according to the comment (2nd one to run it as Docker). 	
					(and we build 
					the app)			Build the application.					
										- mvn clean install -DskipTests
									
										Build locally the Docker image using Dckerfile and the jar files made in previous step.
										- docker build -t kmichos/myproject .
									
										Push this image to DockerHub. (optional, this step is for publishing publicly the image of our docker)
										- docker push kmichos/myproject
									
										Then we need to create the network on which we will run this docker-compose.yml script. (To check if there is such network, do "docker network ls")
										- docker network create app-mysql
									
										Finally, execute the docker-compose command, so to start both dockers.
										- docker-compose up
										
										
										
---End---