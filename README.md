# Event Registration System (Spring Boot)

A backend system to manage events and user registrations with validation and seat tracking.

---

## Features
- User creation
- Event creation with age restrictions
- Register users for events
- Seat management (real-time)
- Prevent duplicate registrations
- Age validation
- Event date validation

---

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Postman

---

## API Endpoints

### User
- POST /users
- GET /users/{id}

### Event
- POST /events
- GET /events
- GET /events/{id}

### Registration
- POST /register?userId=1&eventId=2

---

## How to Run
1. Clone repo
2. Configure MySQL in application.properties
3. Run application
4. Use Postman to test APIs

---

## Sample Flow
User → Event → Registration → Seat Update

---

## Future Improvements
- JWT Authentication
- DTO Layer
- Global Exception Handling
- Pagination
