Reward Points Calculator

This Spring Boot application calculates reward points for customers based on their transaction history.  The application defines a reward system where customers earn points for every dollar spent, with a higher rate for amounts over $100.

 Features

 Customer Management:
     Creates new customers.
      
Reward Calculation:
     Calculates reward points for customers based on transaction amounts.
     Points are calculated as follows:
         2 points for every dollar spent over $100 in a transaction.
         1 point for every dollar spent between $50 and $100 in a transaction.
     Retrieves reward data for a specific customer, optionally within a specified date range.
      
API Endpoints:
     Provides Restful API endpoints for:
         Creating customers.
         Add Transactions with customer details
         Retrieving reward points for a customer.
          
Exception Handling:
     Handles potential errors such as customer not found and transaction not found.
      
JUnit Testing:
     Includes JUnit tests for the controller and service layer.


 Technologies Used

1.	Java
2.	Spring Boot
3.	Spring Data JPA
4.	JUnit
5.	Mockito
6.	Maven

Setup Instructions

1.  Clone the Repository:
    git clone https://github.com/Akki304/CustomersRewardsApplication.git

2.  Configure the Database:
Modify the src/main/resources/application.properties file to configure your database connection.  For example, for H2:
        properties
spring.application.name=rewardcalculator
 spring.datasource.url: jdbc:h2:mem:testdb
spring.datasource.driverClassName:org.h2.Driver
spring.datasource.username: xyz
spring.datasource.password: xyz
        

3.  Build the Application
    mvn clean install

4.  Run the Application:
    mvn spring-boot:run
   

    The application will start and be accessible at `http://localhost:8080`.


API Endpoints

Create a Customer and add Transaction

Endpoint: api/retailer/createCustomer
Method:  POST
Request Body:
     {
    
    "name":"Raj",
    "transactions":[
    {
        
        "transactionAmount":120,
        "transactionDate": "2024-01-28"
    }
    ]
}

    
Response:
     Status Code: 200 OK
     Body: "User successfully created"


Add a new Transaction

EndPoint: api/retailer/addTransaction
Method: POST
Request Body:
{
    "transactionAmount": 101,
    "customerId": "cust1",
    "transactionDate": "2024-01-28"
}

Response
  Status Code: 200 OK
  Body: Transaction sucessfully added




Get Customer Rewards

Endpoint: api/retailer/rewardCalculator
Method: GET

    Query Parameters:
        customerId: The ID of the customer.
     	(Optional)
    	 	startDate: The start date for the transaction period (format: `yyyy-MM-dd`).
    		endDate`: The end date for the transaction period (format: `yyyy-MM-dd`).
Response:
              Status Code: 200 OK

Response Body:

     {
    "name": "Raj",
    "customerId": "cust1",
    "transactionDetails": [
        {
            "transactionId": 2,
            "transactionAmount": 120,
            "transactionDate": "2024-02-15"
        },
        {
            "transactionId": 3,
            "transactionAmount": 120,
            "transactionDate": "2024-02-16"
        },
        {
            "transactionId": 4,
            "transactionAmount": 120,
            "transactionDate": "2024-02-20"
        }
    ],
    "monthlyPoints": [
        {
            "monthName": "Feb",
            "year": 2024,
            "rewardPoints": 270
        }
    ],
    "totalRewardPoints": 270
}

  Error conditions with status code
        
         If customer id is not present:
             Status Code: 404
         If date format is wrong
              Status Code: 400
         If end date is earlier than start date
              Status Code: 400


 Testing

To run the JUnit tests:
mvn test
