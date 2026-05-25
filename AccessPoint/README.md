# 🔐 AccessPoint Authentication System

A secure and scalable full-stack authentication system built using **Spring Boot**, **Spring Security**, **JWT**, **MySQL**, and **React**.

This project demonstrates a modern authentication workflow with secure API access, email verification, OTP-based password recovery, and JWT-based stateless authentication.

---

# ✨ Features

* User Registration
* User Authentication (Login)
* JWT Token Generation & Validation
* Protected APIs
* Email OTP Verification
* Password Reset via OTP
* Secure Password Encryption (BCrypt)
* Role-ready Security Architecture
* RESTful API Design
* React Frontend Integration

---

# 🛠️ Technology Stack

## Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT Authentication
* MySQL
* Maven

## Frontend

* React + Vite
* Bootstrap
* Axios
* React Router DOM
* React Toastify

---

# 📁 Project Structure

```text
AccessPoint/
│
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── security/
│   ├── entity/
│   └── config/
│
├── frontend/
│   ├── components/
│   ├── pages/
│   ├── api/
│   └── context/
```

---

# ⚙️ Backend Configuration

## Database Configuration

Configure MySQL connection inside:

```properties
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/accesspoint
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# ▶️ Running the Application

## Run Backend

```bash
mvn spring-boot:run
```

Backend Server:

```text
http://localhost:8080
```

---

## Run Frontend

```bash
npm install
npm run dev
```

Frontend Server:

```text
http://localhost:5173
```

---

# 🔑 Authentication Workflow

## User Registration

```text
Client Request
      ↓
Validation
      ↓
Password Encryption
      ↓
Store User in Database
```

---

## User Login

```text
User Credentials
      ↓
Spring Security Authentication
      ↓
JWT Token Generation
      ↓
Token Returned to Client
```


---

# 🌐 Base URL

```text
http://localhost:8080
```

---

# 📚 REST API Documentation

# 👤 User APIs

## Register User

### Endpoint

```http
POST /register
```

### Description

Creates a new user account.

### Request Body

```json
{
  "name": "John Doe",
  "email": "john@gmail.com",
  "password": "password123"
}
```

### Response

```json
{
  "message": "User registered successfully"
}
```

---

## Get User Profile

### Endpoint

```http
GET /profile
```

### Description

Returns authenticated user information.

### Headers

```http
Authorization: Bearer JWT_TOKEN
```

### Response

```json
{
  "name": "John Doe",
  "email": "john@gmail.com"
}
```

---

# 🔐 Authentication APIs

## Login User

### Endpoint

```http
POST /login
```

### Description

Authenticates user credentials and returns JWT token.

### Request Body

```json
{
  "email": "john@gmail.com",
  "password": "password123"
}
```

### Response

```json
{
  "token": "JWT_TOKEN",
  "email": "john@gmail.com"
}
```

---

## Check Authentication Status

### Endpoint

```http
GET /is-authenticated
```

### Description

Checks whether the current user session is authenticated.

### Headers

```http
Authorization: Bearer JWT_TOKEN
```

### Response

```json
true
```

---

## Send Password Reset OTP

### Endpoint

```http
POST /send-reset-otp
```

### Description

Sends a password reset OTP to the user's email address.

### Request Body

```json
{
  "email": "john@gmail.com"
}
```

### Response

```json
{
  "message": "OTP sent successfully"
}
```

---

## Reset Password

### Endpoint

```http
POST /reset-password
```

### Description

Resets user password using OTP verification.

### Request Body

```json
{
  "email": "john@gmail.com",
  "otp": "123456",
  "newPassword": "newPassword123"
}
```

### Response

```json
{
  "message": "Password reset successful"
}
```

---

## Send Email Verification OTP

### Endpoint

```http
POST /send-otp
```

### Description

Sends email verification OTP.

### Request Body

```json
{
  "email": "john@gmail.com"
}
```

### Response

```json
{
  "message": "OTP sent successfully"
}
```

---

## Verify Email OTP

### Endpoint

```http
POST /verify-otp
```

### Description

Verifies user email using OTP validation.

### Request Body

```json
{
  "email": "john@gmail.com",
  "otp": "123456"
}
```

### Response

```json
{
  "message": "Email verified successfully"
}
```

---

# 🔒 Security Implementation

* JWT-Based Stateless Authentication
* BCrypt Password Encryption
* Spring Security Filters
* Secure Protected Endpoints
* OTP-Based Email Verification
* Password Reset Security
* Authentication Validation Middleware

---

# 🛡️ Authorization Header

Protected endpoints require JWT token:

```http
Authorization: Bearer JWT_TOKEN
```

Example:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

# 🌍 CORS Configuration

Allow frontend origin:

```java
configuration.setAllowedOrigins(
    List.of("http://localhost:5173")
);
```

---

# 📦 Frontend Dependencies

```bash
npm install axios bootstrap react-router-dom react-toastify
```

---

# 🚀 Future Enhancements

* Refresh Token Authentication
* OAuth2 / Google Login
* Role-Based Authorization
* Docker Deployment
* Redis Session Management
* Email Templates
* Account Verification Expiration


---


# 👨‍💻 Developer


Built with ❤️ by [Md Tarikul Hassan](https://github.com/mdtarikul-hassan)