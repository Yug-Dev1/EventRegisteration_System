# 🚀 Event Registration System (Spring Boot)

A production-style backend system to manage events and user registrations with validation, seat tracking, and clean API design.

---

## 🔥 Features

- User management (create & fetch users)
- Event management with age restrictions
- Register users for events
- Cancel registration (seat restoration)
- Real-time seat tracking
- Prevent duplicate registrations
- Age validation for events
- Event date validation (no past events)
- Global exception handling with clean responses
- DTO-based API responses (secure & clean)
- Input validation using `@Valid`

---

## 🛠 Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Hibernate
- Postman
- Git & GitHub

---

## 🧱 Project Architecture

```
Controller → Service → Repository → Database
```

- Controller → Handles API requests  
- Service → Contains business logic  
- Repository → Handles database operations  

---

## 📌 API Endpoints

### 👤 User APIs
- POST /users → Create user  
- GET /users/{id} → Get user by ID  

---

### 🎟 Event APIs
- POST /events → Create event  
- GET /events → Get all events  
- GET /events/{id} → Get event by ID  

---

### 📝 Registration APIs
- POST /register?userId=1&eventId=6 → Register user  
- DELETE /register?userId=1&eventId=6 → Cancel registration  

---

## ⚙️ How to Run

1. Clone the repository  
2. Configure MySQL in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_registration
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

3. Run the Spring Boot application  
4. Test APIs using Postman  

---

## 🧪 Sample Flow

```
Create User → Create Event → Register → Seat Decreases → Cancel → Seat Increases
```

---

## 🧠 Key Concepts Implemented

- Layered architecture (Controller-Service-Repository)
- DTO pattern for API responses
- Global exception handling (`@RestControllerAdvice`)
- Validation using Jakarta Bean Validation
- JPA relationships and entity management
- Business logic separation
- Soft delete using status (ACTIVE/CANCELLED)
- Git-based version control

---

## 📸 Example Response

```json
{
  "message": "User not found",
  "status": 404,
  "timestamp": "2026-03-30T12:00:00"
}
```

---

## 🚀 Future Improvements

- JWT Authentication & Authorization
- Pagination & Sorting
- Search & filtering APIs
- Swagger API documentation
- Logging & monitoring

---

## 💡 Author

Built as a backend learning project to demonstrate real-world API development using Spring Boot.- GET /events/{id}

### Registration
- POST /register?userId=1&eventId=2

---

## How to Run
1. Clone repo
2. Configure MySQL in application.properties
3. Run application
4. Use Postman to test APIs
