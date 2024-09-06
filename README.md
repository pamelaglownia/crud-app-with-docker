# CRUD Company Application v.1

This project is a Spring Boot application that manages companies, departments, projects, and teams. It uses a PostgreSQL database and is containerized with Docker for easy setup and deployment.

## Technologies Used

- **Java 21**: The application is built using Java.
- **Spring Boot**: Provides the backend framework with RESTful APIs.
- **PostgreSQL**: The relational database used for storing data.
- **Docker**: Containerizes the application and database for easy deployment.
- **Maven**: Manages project dependencies and builds the application.

## Project Structure

- **`src/main/java`**: Contains the Java source code for the application.
    - **`controller`**: REST controllers for handling HTTP requests.
    - **`service`**: Business logic and service classes.
    - **`dto`**: Data transfer objects.
    - **`repository`**: Spring Data JPA repositories.

- **`data.sql`**: Database initialization script containing SQL statements for creating tables and inserting initial data.

- **`Dockerfile`**: Defines the steps to build the application Docker image.

- **`docker-compose.yml`**: Orchestrates the application and database containers.

## Database Schema

The database consists of the following tables:

1. **`company`**: Stores company data.
2. **`department`**: Stores departments and is linked to companies.
3. **`project`**: Stores projects and includes manager details.
4. **`team`**: Stores teams, linked to both departments and projects.

## Prerequisites

- Docker installed on your machine
- Docker Compose installed on your machine

## Getting Started

Follow these steps to set up and run the application.

### 1. Clone the Repository

```
git clone https://github.com/pamelaglownia/crud-app-with-docker.git
```

### 2. Build and Run the Application
Use Docker Compose to build and start the application along with the PostgreSQL database:
```
docker-compose up --build
```
### 3. Access the Application
API Base URL: The application runs on http://localhost:8080.

### 4. Available Endpoints
- **`GET /companies`**: Retrieve a list of all companies.
- **`GET /companies/{id}`**: Retrieve a company by its ID.
- **`POST /companies:`** Create a new company.
- **`PUT /companies/{id}`**: Update an existing company.
- **`DELETE /companies/{id}`**: Delete a company by its ID.
