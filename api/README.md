# Starling Tech Test

This project is a Spring Boot application that provides a set of APIs for managing accounts, rounding up transactions, and handling savings goals. The application is written in Java and uses Maven for dependency management.

## The Challenge

We’d like you to develop a “round-up” feature for Starling customers using our public developer API that is available to all customers and partners. For a customer, take all the transactions in a given week and round them up to the nearest pound. For example with spending of £4.35, £5.20 and £0.87, the round-up would be £1.58. This amount should then be transferred into a savings goal, helping the customer save for future adventures.

## Getting Started

To get started with the project, clone the repository to your local machine:

```bash
git clone https://github.com/yourusername/starling-tech-test.git
```

Navigate into the project directory:

```bash
cd starling-tech-test
```

## Building the Project

The project uses Maven for dependency management. To build the project, run the following command:

```bash
mvn clean install
```

## Running the Application

To start the application, use the following command:

```bash
mvn spring-boot:run
```

The application will start and by default will be accessible at `http://localhost:8080`.

## Docker

The application can also be run using Docker. To build the Docker image, use the following command:

```bash
docker build -t starling-tech-test .
```

To run the Docker container, use the following command:

```bash
docker run -p 8080:8080 starling-tech-test
```

## API Documentation

The project uses Swagger for API documentation. Once the application is running, you can access the Swagger UI at `http://localhost:8080/swagger-ui.html`. This provides a user-friendly interface to interact with the API and understand its capabilities.

## Testing

The project includes a suite of unit tests that can be run using the following command:

```bash
mvn test
```

## Endpoints

The application provides the following endpoints:

- `GET /api/v1/account/{accountUid}/balance`: Fetches the balance of a specific account.
- `PUT /api/v1/roundup`: Rounds up transactions to the nearest whole number.
- `GET /api/v1/savings-goals/account/{accountUid}`: Fetches all savings goals for a specific account.
- `PUT /api/v1/savings-goals/account/{accountUid}`: Creates new savings goals for a specific account.
- `PUT /api/v1/savings-goals/account/{accountUid}/savings-goal/{savingsGoalUid}`: Transfers money to a specific savings goal.