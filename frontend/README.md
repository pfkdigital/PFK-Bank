# README

## Project Overview

This project is a web application built with TypeScript, JavaScript, React, and npm. The application is designed to manage saving goals. It allows users to select a saving goal from a list, view the details of the selected saving goal, and round up transactions to the nearest dollar, with the difference being transferred to the selected saving goal.

## Code Structure

The codebase is structured into several components, each responsible for a specific part of the application's functionality. Here are some key components:

- `SavingGoal.tsx`: This is the main component of the application. It fetches the list of saving goals, handles the selection of a saving goal, and displays the selected saving goal's details.

- `SavingsGoalDetails.tsx`: This component displays the details of a selected saving goal.

- `RoundUpModal.tsx`: This component handles the rounding up of transactions and the transfer of the rounded-up amount to the selected saving goal.

- `NoTransactionsFound.tsx`: This component is displayed when no transactions are found.

## Running the Project

Before running the project, make sure you have Node.js and npm installed on your machine.

1. Clone the repository to your local machine.

2. Navigate to the project directory in your terminal.

3. Install the project dependencies by running the following command:

```bash
npm install
```

4. Start the development server by running the following command:

```bash
npm start
```

The application should now be running on `http://localhost:3000`.

## Docker

The project also includes a `Dockerfile` for building a Docker image of the application. To build the Docker image, navigate to the project directory in your terminal and run the following command:

```bash
docker build -t my-app .
```

To run the Docker image, use the following command:

```bash
docker run -p 3000:3000 my-app
```

The application should now be running on `http://localhost:3000`.

Please note that the Docker setup is basic and might need to be adjusted based on your specific application needs.