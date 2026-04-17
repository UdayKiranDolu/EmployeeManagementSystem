# Employee Management System

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)
![JWT](https://img.shields.io/badge/JWT-Security-blue)
![MySQL](https://img.shields.io/badge/MySQL-Database-orange)
![Maven](https://img.shields.io/badge/Build-Maven-red)

A **Spring Boot-based Employee Management System** implementing **Role-Based Access Control (RBAC)** with secure authentication using **JWT (JJwt)** and MySQL.

---

## 🚀 Features

### 🛠 Admin
- Assign duties to Managers and Employees  
- View all users and system-wide tasks  
- Full system control  

### 👨‍💼 Manager
- Assign duties to Employees  
- View tasks assigned by Admin  
- View tasks assigned to Employees  

### 👨‍🔧 Employee
- Self registration  
- View assigned duties  
- View and update personal profile  

---

## ⚙️ Tech Stack

- **Backend:** Spring Boot  
- **Security:** Spring Security + JWT (JJwt)  
- **Database:** MySQL  
- **ORM:** Spring Data JPA / Hibernate  
- **Build Tool:** Maven  

---

## 🔐 Security

- JWT-based stateless authentication  
- Role-based authorization (Admin / Manager / Employee)  
- Secure API endpoints with Spring Security filters  

---

## 🏗️ System Architecture


Client (Postman / Frontend)
        ↓
Controller Layer
        ↓
DTO Layer (Request / Response Objects)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (Database Access)
        ↓
MySQL Database
        ↑
JWT Security Filter (Authentication & Authorization)
````

---

## 📁 Project Structure

```text id="proj_struct_ems"
controller/        → REST APIs (Request handling)
dto/               → Data Transfer Objects
entity/            → Database Entities (Tables)
repository/        → JPA Repositories
security/          → JWT & Spring Security configuration
services/
   └── serviceImpl → Business logic implementation
```

---

## 🛠️ Setup Instructions

### 1. Clone Repository

```bash id="clone_ems"
git clone https://github.com/your-username/employee-management-system.git
cd employee-management-system
```

---

### 2. Create MySQL Database

```sql id="db_ems"
CREATE DATABASE employeemanagementsystem;
```

---

### 3. Configure `application.properties`

```properties id="config_ems"
spring.datasource.url=jdbc:mysql://localhost:3306/employeemanagementsystem
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=yourSecretKey
jwt.expiration=86400000
```

---

### 4. Run Application

```bash id="run_ems"
mvn spring-boot:run
```

---

## 📌 API Endpoints

### 🔐 Auth

* `POST /auth/register` → Register user
* `POST /auth/login` → Login & get JWT token

### 🛠 Admin

* `POST /admin/assign-task`
* `GET /admin/users`

### 👨‍💼 Manager

* `POST /manager/assign-task`
* `GET /manager/tasks`

### 👨‍🔧 Employee

* `GET /employee/tasks`
* `GET /employee/profile`

---

## 📷 Future Enhancements

* Frontend UI (React / Angular)
* Email notifications for task updates
* Task deadlines & priority system
* Admin dashboard analytics

---

## 👨‍💻 Author

Developed by DOLU UDAY KIRAN


