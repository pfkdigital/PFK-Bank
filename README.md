# Starling Round-Up Feature

## The Challenge

The task is to develop a "round-up" feature for customers using starling banks public developer API. This feature is available to all customers and partners.

The "round-up" feature works as follows: For a customer, take all the transactions in a given week and round them up to the nearest pound. For example, with spending of £4.35, £5.20, and £0.87, the round-up would be £1.58. This amount should then be transferred into a savings goal, helping the customer save for future adventures.

## Getting Started

This project consists of a Spring Boot API and a Next.js application. To get the app running, you will need Docker installed on your machine. If you don't have Docker, you can run the applications manually following the steps in their respective README files. Make sure to start the Spring Boot application first.

### Prerequisites

- Docker (optional)
- Docker Compose (optional)
- Java 17
- Maven
- Node.js
- npm

### Running the App with Docker

1. Clone the repository to your local machine.
2. Navigate to the root directory of the project.
3. Run the following command to start the application:

```bash
docker-compose up
