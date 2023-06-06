# Travel App

The Travel App is a web application that allows users to manage cities, users, tickets, races, and book/buy tickets. The app is secured using JWT token authentication and logs are saved in a separate microservice by sending data using Kafka.

## Prerequisites

- Docker
- Docker Compose

## Getting Started

To start the project, follow these steps:

1. Clone the repository:
```bash
git clone https://github.com/kreker1207/travelapp.git
cd travel
````
2. Build and run the project using Docker Compose:
```bash
docker-compose up
```
3. Connect to db in your IDE 
 
4. Once the containers are up and running, you can access the app at `http://localhost:8000`.

## Usage

### Postman Collection

To test the various routes and perform CRUD operations, import the provided Postman collection located at `./postman/TravelApp.postman_collection.json`. This collection contains pre-configured requests for different API endpoints.

### Authentication

The Travel App uses JWT token authentication to secure the routes. To access restricted routes, you need to include the JWT token in the `Authorization` header of the requests. You can obtain the token by making a `POST` request to the `/login` endpoint with valid credentials.

### Route Access

- Some routes are restricted and can only be accessed by logged-in users.
- Certain routes can only be accessed by admins.
