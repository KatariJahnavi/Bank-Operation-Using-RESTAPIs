# 🏦 Bank Management System — Spring Boot REST API

A fully functional **Bank Management System** built with **Spring Boot**, **JPA/Hibernate**, and **PostgreSQL**. This project provides RESTful APIs to manage Banks, Accounts, and Addresses with complete business logic, custom exception handling, and structured responses.

---

## 📌 Table of Contents

- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Entity Relationships](#entity-relationships)
- [Features](#features)
- [Business Logic & Validations](#business-logic--validations)
- [API Endpoints](#api-endpoints)
  - [Bank APIs](#bank-apis)
  - [Account APIs](#account-apis)
  - [Address APIs](#address-apis)
- [Sample Inputs](#sample-inputs)
- [Exception Handling](#exception-handling)
- [Response Structure](#response-structure)
- [How to Run](#how-to-run)

---

## 🛠 Tech Stack

| Technology | Version |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.0 |
| Spring Data JPA | Latest |
| PostgreSQL | 15.x |
| Maven | Latest |
| REST API | JSON |

---

## 📁 Project Structure

```
src/main/java/jsp/springboot/
├── BankmanagementApplication.java
└── Bankmanagement/
    ├── controller/
    │   ├── BankController.java
    │   ├── AccountController.java
    │   └── AddressController.java
    ├── service/
    │   ├── BankService.java
    │   ├── AccountService.java
    │   └── AddressService.java
    ├── repository/
    │   ├── BankRepository.java
    │   ├── AccountRepository.java
    │   └── AddressRepository.java
    ├── entity/
    │   ├── Bank.java
    │   ├── Account.java
    │   └── Address.java
    ├── dto/
    │   ├── ResponseStructure.java
    │   ├── AccountDto.java
    │   └── TransferDto.java
    └── exception/
        ├── GlobalExceptionHandler.java
        ├── BankNotFoundException.java
        ├── AccountNotFoundException.java
        ├── AddressNotFoundException.java
        ├── AddressMandatoryException.java
        ├── ContactLengthException.java
        ├── PincodeLengthException.java
        ├── IfscCodeAlreadyExistsException.java
        ├── ContactAlreadyExistsException.java
        ├── AccountNumberAlreadyExistsException.java
        ├── InsufficientBalanceException.java
        └── InvalidAmountException.java
```

---

## 🔗 Entity Relationships

```
Bank (1) ──────────── (1) Address
  │
  └──────────────── (Many) Account
```

- **Bank → Address** : OneToOne (CascadeType.ALL)
- **Bank → Account** : OneToMany (CascadeType.ALL)
- **Account → Bank** : ManyToOne

---

## ✅ Features

- Create, Read, Delete Banks with Address
- Create Accounts linked to a Bank
- Deposit, Withdraw, and Transfer amounts between accounts
- Pagination support for Banks and Accounts
- Filter by IFSC code, city, pincode, contact, account type, balance
- Custom exception handling with meaningful error responses
- Unified response structure with statusCode, message, and data

---

## 🔒 Business Logic & Validations

### Bank
| Rule | Detail |
|---|---|
| IFSC Code | Must be unique, exactly 11 characters |
| Contact | Must be unique and exactly 10 digits |
| Address | Mandatory to save a bank |
| Pincode | Must be exactly 6 digits |
| Delete | Bank can only be deleted if it has no accounts |

### Account
| Rule | Detail |
|---|---|
| Account Number | Must be unique |
| Bank | Must exist to create an account |
| Deposit Amount | Must be positive |
| Withdrawal Amount | Must be positive and must not exceed balance |
| Transfer Amount | Must be positive, sender and receiver must exist and must not be the same, must not exceed sender's balance |

---

## 📡 API Endpoints

### Bank APIs

| Method | URL | Description |
|---|---|---|
| POST | `/bank` | Create a new bank |
| GET | `/bank` | Get all banks |
| GET | `/bank/{id}` | Get bank by ID |
| DELETE | `/bank/{id}` | Delete bank by ID |
| GET | `/bank/pagination/{page}/{size}` | Get banks with pagination |
| GET | `/bank/ifsc/{ifscCode}` | Get bank by IFSC code |
| GET | `/bank/address/{addressId}` | Get bank by address ID |
| GET | `/bank/city/{city}` | Get banks by city |
| GET | `/bank/contact/{contact}` | Get bank by contact number |

---

### Account APIs

| Method | URL | Description |
|---|---|---|
| POST | `/account/{bankId}` | Create account under a bank |
| GET | `/account` | Get all accounts |
| GET | `/account/{id}` | Get account by ID |
| DELETE | `/account/{id}` | Delete account by ID |
| PUT | `/account/deposit` | Deposit amount |
| PUT | `/account/withdraw` | Withdraw amount |
| PUT | `/account/transfer` | Transfer amount between accounts |
| GET | `/account/bank/{bankId}` | Get accounts by bank |
| GET | `/account/type/{type}` | Get accounts by type (SAVINGS/CURRENT/FIXED_DEPOSIT) |
| GET | `/account/balance/{value}` | Get accounts with balance greater than value |
| GET | `/account/sorting?page=0&size=3` | Get accounts with pagination |

---

### Address APIs

| Method | URL | Description |
|---|---|---|
| GET | `/address/{id}` | Get address by ID |
| PUT | `/address/{id}` | Update address by ID |
| DELETE | `/address/{id}` | Delete address by ID |
| GET | `/address/bank/{bankId}` | Get address by bank |
| GET | `/address/city/{city}` | Get addresses by city |
| GET | `/address/city/{city}/street/{street}` | Get addresses by city and street |
| GET | `/address/pincode/{pincode}` | Get addresses by pincode |

---

## 📥 Sample Inputs

### Create Bank — POST `/bank`
```json
{
    "bankName": "State Bank of India",
    "ifscCode": "SBIN0001234",
    "branch": "Koramangala",
    "contact": 9876543210,
    "address": {
        "street": "80 Feet Road",
        "city": "Bengaluru",
        "state": "Karnataka",
        "pincode": 560034
    }
}
```

### Create Account — POST `/account/1`
```json
{
    "accountNumber": 1234567890,
    "accountholder": "Rahul Sharma",
    "accountType": "SAVINGS",
    "balance": 50000.00
}
```

### Deposit — PUT `/account/deposit`
```json
{
    "id": 1,
    "amount": 10000.00
}
```

### Withdraw — PUT `/account/withdraw`
```json
{
    "id": 1,
    "amount": 5000.00
}
```

### Transfer — PUT `/account/transfer`
```json
{
    "senderId": 1,
    "receiverId": 2,
    "amount": 2000.00
}
```

### Update Address — PUT `/address/1`
```json
{
    "street": "MG Road",
    "city": "Mysuru",
    "state": "Karnataka",
    "pincode": 570001
}
```

---

## ⚠️ Exception Handling

All exceptions are handled globally via `GlobalExceptionHandler` using `@RestControllerAdvice`.

| Exception | HTTP Status | Scenario |
|---|---|---|
| `BankNotFoundException` | 404 Not Found | Bank ID does not exist |
| `AccountNotFoundException` | 404 Not Found | Account ID does not exist |
| `AddressNotFoundException` | 404 Not Found | Address ID does not exist |
| `AddressMandatoryException` | 400 Bad Request | Address not provided while saving bank |
| `ContactLengthException` | 400 Bad Request | Contact number is not 10 digits |
| `PincodeLengthException` | 400 Bad Request | Pincode is not 6 digits |
| `IfscCodeAlreadyExistsException` | 409 Conflict | IFSC code already exists |
| `ContactAlreadyExistsException` | 409 Conflict | Contact number already exists |
| `AccountNumberAlreadyExistsException` | 409 Conflict | Account number already exists |
| `InsufficientBalanceException` | 400 Bad Request | Balance too low for withdrawal or transfer |
| `InvalidAmountException` | 400 Bad Request | Amount is zero or negative |

---

## 📦 Response Structure

Every API response follows this unified format:

```json
{
    "statusCode": 200,
    "message": "Bank fetched successfully.",
    "data": { }
}
```

```java
public class ResponseStructure<T> {
    private int statusCode;
    private String message;
    private T data;
}
```

---

## ▶️ How to Run

### Prerequisites
- Java 17+
- PostgreSQL 15.x
- Maven

### Step 1 — Configure `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bankdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Step 2 — Create Database
```sql
CREATE DATABASE bankdb;
```

### Step 3 — Add PostgreSQL Dependency in `pom.xml`
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Step 4 — Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

### Step 5 — Test APIs
Use **Postman** or any REST client.
```
Base URL: http://localhost:8080
```

---

## 👤 Author

**Divya Kake**
- GitHub: [@Divyakake03](https://github.com/Divyakake03)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
