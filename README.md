# Spring Boot MVC CRUD Application with Soft Delete (Learning Project)

This project is a **Learning Project** built to understand how to implement:

- Spring Boot MVC Architecture
- CRUD Operations
- Hibernate Soft Delete
- MySQL Integration
- Audit Fields
- Optimistic Locking
- JSP View Resolver
- Layered Architecture (Controller → Service → Repository → Entity)

The goal of this project is to practice real-world backend development concepts using Spring Boot.

---

## What I Learned from This Project

- How Spring Boot auto-configuration works
- How to build MVC applications using JSP
- How to integrate MySQL with Spring Data JPA
- How Hibernate handles Soft Delete using `@SQLDelete`
- How to automatically exclude deleted records using `@SQLRestriction`
- How to implement audit fields using `@CreationTimestamp` and `@UpdateTimestamp`
- How Optimistic Locking works using `@Version`
- How layered architecture improves maintainability

---

# Project Architecture

**Controller → Service → Repository → Entity → Database**

This layered architecture helps in maintaining clean code, separation of concerns, and scalability.

---

# Package Structure

```
src/main/java/com/boot/mvc/
├── controller/
│   └── DoctorOperationsController.java
│
├── dto/
│   └── DoctorDto.java
│
├── entity/
│   └── DoctorEntity.java
│
├── exception/
│   └── DoctorNotFoundException.java
│
├── repository/
│   └── IDoctorRepository.java
│
├── service/
│   ├── IDoctorMgmtService.java
│   └── DoctorMgmtServiceImpl.java
│
├── FullStackProjectBootMvcCrudApplicationSoftDelete.java
└── ServletInitializer.java
```

---

# View Layer (JSP)
```properties
src/main/webapp/WEB-INF/pages/
├── welcome.jsp
├── doctor_register_form.jsp
├── doctor_edit_form.jsp
└── show_report.jsp
```

## View Resolver Configuration
```properties
- spring.mvc.view.prefix=/WEB-INF/pages/
- spring.mvc.view.suffix=.jsp
```

---

# Database Configuration (MySQL)
- Database Name
```
mvcprojectsd
```
- application.properties
```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql:///mvcprojectsd
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=6161
```
---

# Soft Delete Implementation (Hibernate Level)

This project uses Hibernate-based Soft Delete instead of manually writing delete logic.

Inside `DoctorEntity.java`:
```java
@SQLDelete(sql = "UPDATE DOCTORS_INFO SET STATUS='INACTIVE' WHERE ID=? AND UPDATE_COUNT=?")
@SQLRestriction("STATUS <> 'INACTIVE'")
```
## How It Works
- When delete() is called, Hibernate executes an UPDATE query instead of DELETE.
- The STATUS column is changed to 'INACTIVE'.
- @SQLRestriction automatically filters out inactive records.
- No manual filtering is required in repository methods.

---

# Entity Features

## Business Fields

- id  
- name  
- specialization  
- fee  
- phone  
- status  

---

## Audit Fields

- createdOn (`@CreationTimestamp`)  
- updatedOn (`@UpdateTimestamp`)  
- createdBy  
- updatedBy  

---

## JPA Lifecycle Callbacks

```java
@PrePersist
@PreUpdate
```
Used to automatically set:
- createdBy
- updatedBy

---

# Optimistic Locking
```java
@Version
private Integer updateCount;
```
Prevents lost updates during concurrent transactions.

---

# Key Concepts Practiced

-  Boot MVC
- DTO Pattern
- Service Layer Pattern
- Repository Pattern
- Hibernate Annotations
- Soft Delete Strategy
- Audit Tracking
- Optimistic Locking
- JSP Integration with Spring Boot
- MySQL Database Connectivity

---

# Note
This is a **learning project** created to understand Spring Boot MVC and advanced Hibernate concepts.
It is not intended for production use.

---
Developed as part of my Spring Boot learning journey.
