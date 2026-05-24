# Event Registration System (Spring Boot + MySQL + MongoDB)

A full-featured backend Event Registration System built with Spring Boot that supports secure authentication, dynamic event configuration, ticket booking, seat management, and hybrid SQL + NoSQL persistence.

---

## Overview

This system allows users to browse and register for events while admins can create highly customizable event pages with ticket tiers, dynamic forms, FAQs, schedules, themes, and media content.

The architecture uses:

- **MySQL** for transactional/core business data
- **MongoDB** for flexible dynamic event configuration
- **JWT + OAuth2 Authentication** for secure access

---

## Features

### Authentication & Security
- User registration
- JWT-based authentication
- OAuth2 login support
- Role-based access control (Admin / User)
- Secure endpoint protection using Spring Security

---

### Event Management
- Create events
- Update events
- Delete events
- Fetch all events
- Fetch event by ID
- Age restrictions (min/max age)
- Event date validation
- Real-time seat tracking
- Multiple ticket policy per event

---

### Dynamic Event Page Configuration (MongoDB)
Each event supports customizable event pages:

- Event type
- Media content
  - Banner image
  - Thumbnail
  - Gallery
  - Promo video
- Theme customization
  - Primary/secondary colors
  - Font family
  - Button styles
  - Background styles
- Event schedule
- Participants / speakers / artists
- FAQ section
- Ticket tiers
  - VIP / General / Backstage etc.
- Custom event attributes
- Dynamic registration form fields

---

### Registration System
- Secure booking using JWT-authenticated user
- One registration per user per event
- Duplicate registration prevention
- Seat availability validation
- Ticket tier validation
- Ticket quantity validation
- Age eligibility validation
- Event expiry validation
- Required custom form validation
- Automatic seat deduction
- Ticket tier quantity deduction
- Registration cancellation
- Seat restoration on cancellation
- Ticket inventory restoration on cancellation

---

### Hybrid Persistence Design
#### MySQL Stores
- Users
- Events
- Registrations
- Authentication data

#### MongoDB Stores
- Dynamic event page configurations
- Registration custom answers

---

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Spring Data MongoDB
- Hibernate
- JWT
- OAuth2

### Database
- MySQL
- MongoDB

### Tools
- Postman
- Git
- GitHub
- Maven

---

## Project Architecture

```text
Client
   ↓
Spring Security
   ↓
JWT / OAuth2 Authentication
   ↓
Controllers
   ↓
Services
   ↓
MySQL (Transactional Data)
MongoDB (Dynamic Flexible Data)
```

---

## API Endpoints

# Authentication
```http
POST /auth/register
POST /auth/login
GET /oauth2/authorization/google
```

---

# Events
```http
POST   /events
GET    /events
GET    /events/{id}
PATCH  /events/{id}
DELETE /events/{id}
```

---

# Registrations
```http
POST   /registrations
DELETE /registrations/cancel/{eventId}
```

---

## Sample Registration Flow

```text
User Login
   ↓
JWT Generated
   ↓
User Selects Event
   ↓
Backend Validates:
   - Duplicate registration
   - Age eligibility
   - Event active
   - Seat availability
   - Ticket tier availability
   - Required custom fields
   ↓
MySQL:
   - Save registration
   - Deduct available seats
   ↓
MongoDB:
   - Reduce ticket tier inventory
   - Save custom form answers
   ↓
Booking Confirmed
```

---

## Example Features in Action

### Concert Event
Supports:
- VIP / General / Backstage tickets
- Artist lineup
- Event schedule
- FAQs
- Dynamic custom questions
- Media galleries

Example custom form fields:
- Emergency Contact Number
- T-Shirt Size
- Wheelchair Assistance

---

## Validation Rules
- Event date must be in future
- Age must match event restrictions
- Duplicate registrations blocked
- Seat availability checked
- Ticket inventory checked
- Required custom fields enforced
- One registration per user per event

---

## Security Highlights
- JWT authentication
- OAuth2 login
- Role-based authorization
- User identity derived from SecurityContext
- No client-side user ID trust

---

## Future Improvements
### Payments
- Razorpay / Stripe integration
- Payment verification
- Webhooks
- Refund workflow

### Notifications
- Email confirmations
- Cancellation emails
- Event reminders
- SMS / WhatsApp notifications

### Frontend
- React / Next.js frontend
- Dynamic event page renderer
- Admin dashboard
- Booking management UI
- Payment UI

### Advanced Features
- QR ticket generation
- Event check-in scanner
- Booking history
- Admin analytics dashboard
- Waitlist system
- Discount coupons
- Referral system
- Seat locking
- Redis caching
- Swagger API docs
- Docker deployment
- CI/CD pipelines
- Unit + integration testing
- Load testing

---

## How to Run

### Prerequisites
Install:
- Java 17+
- Maven
- MySQL
- MongoDB
- Git

---

### Clone Repository
```bash
git clone https://github.com/Yug-Dev1/EventRegisteration_System.git
cd EventRegisteration_System
```

---

### Configure application.properties
Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/event_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.data.mongodb.uri=mongodb://localhost:27017/event_db

jwt.secret=your_secret_key
```

---

### Run Application
```bash
mvn spring-boot:run
```

---

### Test APIs
Use Postman for testing.

---

## Author
Built by Yug
