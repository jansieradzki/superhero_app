# Superheroes Battle Arena

## Table of Contents
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Technology Stack](#technology-stack)
4. [Setup Instructions](#setup-instructions)
5. [API Documentation](#api-documentation)
6. [Frontend Features](#frontend-features)
7. [Testing](#testing)
8. [Enhancements and Professional Practices](#enhancements-and-professional-practices)
9. [Future Plans](#future-plans)
10. [Acknowledgments](#acknowledgments)

---

## Project Overview
The **Superheroes Battle Arena** is a web application designed to simulate dynamic battles between superheroes and supervillains. Built with a professional approach, it adheres to modern software development practices, including CQRS (Command Query Responsibility Segregation), SOLID principles, and robust error handling. The application is fully containerized, making it easy to deploy and scale.

---

## Features

### Backend
1. **Battle API**: Determines the winner between two characters based on their stats.
2. **Validation**: Ensures only valid battles occur (superhero vs supervillain) and prevents invalid inputs.
3. **Battle History API**: Provides a paginated history of battles.
4. **Real-Time Updates**: Utilizes WebSockets for live updates of battle results.
5. **CQRS Architecture**: The backend services are split into command and query responsibilities, enhancing scalability and maintainability.
6. **Service Interfaces**: All services implement interfaces, promoting testability and adhering to the Dependency Inversion Principle.

### Frontend
1. **Battle Form**: Allows users to input characters and initiate battles.
2. **Battle History View**: Displays the history of battles in real-time, including additional metadata for each battle.
3. **Error Handling**: Provides clear and user-friendly error messages for invalid inputs or server errors.
4. **Responsive Design**: Built with Tailwind CSS for seamless performance across devices.

---

## Technology Stack

### Backend
- **Language**: Java 17
- **Framework**: Spring Boot 3.3
- **Database**: PostgreSQL
- **Testing**: JUnit, Mockito, Testcontainers
- **Others**: MapStruct, Lombok, WebSocket, Docker

### Frontend
- **Language**: TypeScript
- **Framework**: React with Vite
- **Styling**: Tailwind CSS

### Deployment
- **Containerization**: Docker, Docker Compose

---

## Setup Instructions

### Prerequisites
- Docker and Docker Compose installed
- Node.js (v18 or higher)

### Steps
1. Clone the repository:
   ```bash
   cd superheroes-app
   ```

2. Build and start the application:
   ```bash
   docker-compose up --build
   ```

3. Access the application:
    - **Frontend**: [http://localhost:3000](http://localhost:3000)
    - **Backend**: [http://localhost:8080](http://localhost:8080)
    - **Swagger**: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

---

## API Documentation

### Base URL
```
http://localhost:8080/api
```

### Endpoints

#### 1. Battle API
**Endpoint**: `GET /battle`

**Description**: Determines the winner between two characters.

**Request**:
```http
GET /api/battle?character=Batman&rival=Joker
```

**Response**:
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "character": "Batman",
  "rival": "Joker",
  "winner": "Batman",
  "timestamp": "2025-01-13T18:30:00"
}
```

**Error Example**:
```json
{
  "message": "Characters of the same type cannot fight.",
  "status": 400
}
```

#### 2. Battle History API
**Endpoint**: `GET /battle_history`

**Description**: Retrieves a paginated history of battles.

**Request**:
```http
GET /api/battle_history?page=0&size=5
```

**Response**:
```json
{
  "content": [
    {
      "id": "123e4567-e89b-12d3-a456-426614174001",
      "character": "Thor",
      "rival": "Thanos",
      "winner": "Thanos",
      "timestamp": "2025-01-13T18:00:00"
    }
  ],
  "totalPages": 1,
  "totalElements": 1
}
```

---

## Frontend Features

### 1. Battle Form
- Input two characters and initiate a battle.
- Displays real-time error messages for invalid input or backend errors.

### 2. Battle History View
- Displays a table of battle history, including winner and timestamp.
- Updates dynamically using WebSockets.

### 3. Responsive Design
- Tailored to provide a seamless experience on mobile and desktop.

---

## Testing

### Backend
1. Run unit and integration tests:
   ```bash
   mvn clean install
   ```
2. **Test Coverage**:
    - Battle API logic
    - Validation
    - WebSocket integration

---

## Enhancements and Professional Practices
1. **CQRS Pattern**: The backend employs Command Query Responsibility Segregation to separate read and write operations, ensuring scalability and clarity.
2. **Service Interfaces**: All services implement interfaces, promoting testability and maintainability.
3. **Environment Variables**: Sensitive data and configurations are managed using `.env` files for security and flexibility.
4. **WebSocket Integration**: Ensures real-time updates for battle results.
5. **Scalability**: Pagination and optimized queries enhance database and application performance.
6. **Error Handling**: Integrated error handling ensures robust user and developer feedback.
7. **Security**: CORS protection and restricted environment variable access enhance security.

---

## Future Plans

### 1. Deployment Enhancements
**Description**: The project will be deployed on platforms such as Render or Railway for hosting both the frontend and backend services along with the PostgreSQL database. These platforms offer modern, scalable hosting solutions that align with the professional standards of the project.

#### Planned Features:
- **Render**:
    - Automatic deployment from GitHub repositories.
    - Free PostgreSQL database hosting with SSL support.
    - Suitable for low-cost, lightweight hosting needs.
- **Railway**:
    - Flexible and easy-to-configure deployments.
    - Larger database storage limits (e.g., 500 MB for PostgreSQL).
    - Scalable hosting with support for various frameworks.

### 2. Generalizing CQRS
- **Objective**: Create a generic handler for query and command operations to further streamline the CQRS pattern.
- **Benefit**: Enhance maintainability and reduce repetitive code by implementing reusable logic across services.

### 3. Adding New Features
- **New Characters**: Allow administrators to add new superheroes and supervillains through the frontend interface.
- **Image Storage**: Integrate AWS S3 for secure and scalable storage of character images.

---

## Acknowledgments
This project was developed to demonstrate expertise in full-stack development with modern practices in Java and React. It showcases scalability, maintainability, and an excellent user experience. The professional application of design patterns, such as CQRS, and the robust testing and error-handling mechanisms make this a standout solution.

