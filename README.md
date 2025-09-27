# Mobile Banking REST API

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A comprehensive Spring Boot REST API for mobile banking applications with full CRUD operations, transaction management, and comprehensive testing suite.

## Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Postman Testing Guide](#postman-testing-guide)
- [RestAssured Testing](#restassured-testing)
- [Security](#security)
- [Error Handling](#error-handling)
- [Performance Considerations](#performance-considerations)

## Features

### Core Banking Operations
- **User Management**: Complete CRUD operations for user accounts
- **Account Management**: Support for multiple account types (Savings, Checking, Credit)
- **Transaction Processing**: Deposits, Withdrawals, Transfers, and Payments
- **Real-time Balance Updates**: Automatic balance calculation with transaction processing
- **Transaction History**: Complete audit trail for all financial operations

### Technical Features
- **RESTful API Design**: Following REST principles and best practices
- **Data Validation**: Comprehensive input validation using Bean Validation
- **Exception Handling**: Global exception handling with meaningful error messages
- **API Documentation**: Interactive Swagger/OpenAPI documentation
- **Database Integration**: JPA/Hibernate with H2 in-memory database
- **Security**: Basic Spring Security configuration
- **Comprehensive Testing**: Unit tests and integration tests with RestAssured

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17
- **Database**: H2 (in-memory for development)
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security
- **Documentation**: SpringDoc OpenAPI 3
- **Testing**: JUnit 5, Mockito, RestAssured
- **Build Tool**: Maven
- **Validation**: Bean Validation (Hibernate Validator)

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

### Installation & Running

1. **Clone the repository**
```bash
git https://github.com/Shady1997/-Mobile_Bank_API_RestFul.git
cd -Mobile_Bank_API_RestFul
```

2. **Build the project**
```bash
mvn clean compile
```

3. **Run tests**
```bash
mvn test
```

4. **Start the application**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8083`

### Quick Access Links
- **API Documentation**: http://localhost:8083/swagger-ui/index.html
- **H2 Database Console**: http://localhost:8083/h2-console
    - JDBC URL: `jdbc:h2:mem:testdb`
    - Username: `sa`
    - Password: `password`

## API Documentation

### Base URL
```
http://localhost:8083/api
```

### Authentication
Currently using basic authentication (development only):
- Username: `admin`
- Password: `admin123`

## User Management APIs

### 1. Create User
**POST** `/api/users`

**Request Body:**
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phoneNumber": "+1234567890"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "fullName": "John Doe",
  "phoneNumber": "+1234567890",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 2. Get User by ID
**GET** `/api/users/{id}`

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "fullName": "John Doe",
  "phoneNumber": "+1234567890",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 3. Get All Users
**GET** `/api/users`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "phoneNumber": "+1234567890",
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

### 4. Update User
**PUT** `/api/users/{id}`

**Request Body:**
```json
{
  "username": "johndoe",
  "email": "john.updated@example.com",
  "password": "newpassword123",
  "fullName": "John Updated Doe",
  "phoneNumber": "+1234567890"
}
```

### 5. Delete User
**DELETE** `/api/users/{id}`

**Response:** `204 No Content`

### 6. Get User by Username
**GET** `/api/users/username/{username}`

## Account Management APIs

### 1. Create Account
**POST** `/api/accounts`

**Request Body:**
```json
{
  "userId": 1,
  "accountType": "SAVINGS",
  "balance": 1000.00,
  "creditLimit": 0.00,
  "status": "ACTIVE"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "accountNumber": "123456789012",
  "user": {
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "fullName": "John Doe"
  },
  "accountType": "SAVINGS",
  "balance": 1000.00,
  "creditLimit": 0.00,
  "status": "ACTIVE",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 2. Get Account by ID
**GET** `/api/accounts/{id}`

### 3. Get All Accounts
**GET** `/api/accounts`

### 4. Get Accounts by User ID
**GET** `/api/accounts/user/{userId}`

### 5. Update Account
**PUT** `/api/accounts/{id}`

### 6. Delete Account
**DELETE** `/api/accounts/{id}`

### 7. Get Account by Account Number
**GET** `/api/accounts/number/{accountNumber}`

## Transaction Management APIs

### 1. Create Transaction
**POST** `/api/transactions`

**Deposit Example:**
```json
{
  "fromAccountId": 1,
  "amount": 500.00,
  "transactionType": "DEPOSIT",
  "description": "Salary deposit",
  "fee": 0.00
}
```

**Transfer Example:**
```json
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 200.00,
  "transactionType": "TRANSFER",
  "description": "Transfer to savings",
  "fee": 2.50
}
```

**Withdrawal Example:**
```json
{
  "fromAccountId": 1,
  "amount": 100.00,
  "transactionType": "WITHDRAWAL",
  "description": "ATM withdrawal",
  "fee": 1.50
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "transactionReference": "TXN-ABC12345",
  "fromAccount": {
    "id": 1,
    "accountNumber": "123456789012",
    "balance": 1400.00
  },
  "toAccount": null,
  "amount": 500.00,
  "transactionType": "DEPOSIT",
  "status": "COMPLETED",
  "description": "Salary deposit",
  "fee": 0.00,
  "createdAt": "2024-01-15T10:30:00",
  "processedAt": "2024-01-15T10:30:01"
}
```

### 2. Get Transaction by ID
**GET** `/api/transactions/{id}`

### 3. Get All Transactions
**GET** `/api/transactions`

### 4. Get Transactions by Account ID
**GET** `/api/transactions/account/{accountId}`

### 5. Get Transaction by Reference
**GET** `/api/transactions/reference/{reference}`

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Accounts Table
```sql
CREATE TABLE accounts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    credit_limit DECIMAL(15,2) DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### Transactions Table
```sql
CREATE TABLE transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    transaction_reference VARCHAR(50) UNIQUE NOT NULL,
    from_account_id BIGINT NOT NULL,
    to_account_id BIGINT,
    amount DECIMAL(15,2) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    description VARCHAR(500),
    fee DECIMAL(15,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP,
    FOREIGN KEY (from_account_id) REFERENCES accounts(id),
    FOREIGN KEY (to_account_id) REFERENCES accounts(id)
);
```

## Postman Testing Guide

### Setting up Postman Environment

1. **Create a new environment** in Postman named "Mobile Banking API"

2. **Add environment variables:**
    - `baseUrl`: `http://localhost:8083/api`
    - `userId`: (will be set dynamically)
    - `accountId`: (will be set dynamically)
    - `transactionId`: (will be set dynamically)

### Complete Test Scenarios

#### Scenario 1: User Lifecycle Management

**Test 1: Create User**
```
Method: POST
URL: {{baseUrl}}/users
Headers: Content-Type: application/json

Body:
{
  "username": "testuser2024",
  "email": "test2024@example.com",
  "password": "securepass123",
  "fullName": "Test User 2024",
  "phoneNumber": "+1987654321"
}

Test Script:
pm.test("User created successfully", function () {
    pm.response.to.have.status(201);
    const response = pm.response.json();
    pm.expect(response.username).to.eql("testuser2024");
    pm.environment.set("userId", response.id);
});
```

**Test 2: Get User by ID**
```
Method: GET
URL: {{baseUrl}}/users/{{userId}}

Test Script:
pm.test("User retrieved successfully", function () {
    pm.response.to.have.status(200);
    const response = pm.response.json();
    pm.expect(response.id).to.eql(parseInt(pm.environment.get("userId")));
});
```

**Test 3: Update User**
```
Method: PUT
URL: {{baseUrl}}/users/{{userId}}
Headers: Content-Type: application/json

Body:
{
  "username": "testuser2024",
  "email": "updated2024@example.com",
  "password": "securepass123",
  "fullName": "Updated Test User 2024",
  "phoneNumber": "+1987654321"
}

Test Script:
pm.test("User updated successfully", function () {
    pm.response.to.have.status(200);
    const response = pm.response.json();
    pm.expect(response.email).to.eql("updated2024@example.com");
    pm.expect(response.fullName).to.eql("Updated Test User 2024");
});
```

#### Scenario 2: Account Management

**Test 4: Create Savings Account**
```
Method: POST
URL: {{baseUrl}}/accounts
Headers: Content-Type: application/json

Body:
{
  "userId": {{userId}},
  "accountType": "SAVINGS",
  "balance": 2500.00,
  "creditLimit": 0.00
}

Test Script:
pm.test("Account created successfully", function () {
    pm.response.to.have.status(201);
    const response = pm.response.json();
    pm.expect(response.accountType).to.eql("SAVINGS");
    pm.expect(response.balance).to.eql(2500.00);
    pm.environment.set("accountId", response.id);
    pm.environment.set("accountNumber", response.accountNumber);
});
```

**Test 5: Create Checking Account**
```
Method: POST
URL: {{baseUrl}}/accounts
Headers: Content-Type: application/json

Body:
{
  "userId": {{userId}},
  "accountType": "CHECKING",
  "balance": 1500.00,
  "creditLimit": 500.00
}

Test Script:
pm.test("Checking account created", function () {
    pm.response.to.have.status(201);
    const response = pm.response.json();
    pm.environment.set("checkingAccountId", response.id);
});
```

**Test 6: Get User Accounts**
```
Method: GET
URL: {{baseUrl}}/accounts/user/{{userId}}

Test Script:
pm.test("User accounts retrieved", function () {
    pm.response.to.have.status(200);
    const response = pm.response.json();
    pm.expect(response).to.be.an('array');
    pm.expect(response.length).to.be.at.least(2);
});
```

#### Scenario 3: Transaction Processing

**Test 7: Deposit Transaction**
```
Method: POST
URL: {{baseUrl}}/transactions
Headers: Content-Type: application/json

Body:
{
  "fromAccountId": {{accountId}},
  "amount": 1000.00,
  "transactionType": "DEPOSIT",
  "description": "Monthly salary deposit",
  "fee": 0.00
}

Test Script:
pm.test("Deposit transaction successful", function () {
    pm.response.to.have.status(201);
    const response = pm.response.json();
    pm.expect(response.transactionType).to.eql("DEPOSIT");
    pm.expect(response.status).to.eql("COMPLETED");
    pm.environment.set("transactionId", response.id);
    pm.environment.set("transactionRef", response.transactionReference);
});
```

**Test 8: Transfer Transaction**
```
Method: POST
URL: {{baseUrl}}/transactions
Headers: Content-Type: application/json

Body:
{
  "fromAccountId": {{accountId}},
  "toAccountId": {{checkingAccountId}},
  "amount": 500.00,
  "transactionType": "TRANSFER",
  "description": "Transfer to checking account",
  "fee": 2.50
}

Test Script:
pm.test("Transfer transaction successful", function () {
    pm.response.to.have.status(201);
    const response = pm.response.json();
    pm.expect(response.transactionType).to.eql("TRANSFER");
    pm.expect(response.status).to.eql("COMPLETED");
});
```

**Test 9: Withdrawal Transaction**
```
Method: POST
URL: {{baseUrl}}/transactions
Headers: Content-Type: application/json

Body:
{
  "fromAccountId": {{checkingAccountId}},
  "amount": 200.00,
  "transactionType": "WITHDRAWAL",
  "description": "ATM cash withdrawal",
  "fee": 3.00
}

Test Script:
pm.test("Withdrawal transaction successful", function () {
    pm.response.to.have.status(201);
    const response = pm.response.json();
    pm.expect(response.transactionType).to.eql("WITHDRAWAL");
    pm.expect(response.status).to.eql("COMPLETED");
});
```

**Test 10: Get Account Transaction History**
```
Method: GET
URL: {{baseUrl}}/transactions/account/{{accountId}}

Test Script:
pm.test("Transaction history retrieved", function () {
    pm.response.to.have.status(200);
    const response = pm.response.json();
    pm.expect(response).to.be.an('array');
    pm.expect(response.length).to.be.at.least(1);
});
```

#### Scenario 4: Error Handling Tests

**Test 11: Invalid User Creation**
```
Method: POST
URL: {{baseUrl}}/users
Headers: Content-Type: application/json

Body:
{
  "username": "",
  "email": "invalid-email",
  "password": "123"
}

Test Script:
pm.test("Validation error for invalid user", function () {
    pm.response.to.have.status(400);
});
```

**Test 12: Insufficient Funds Transaction**
```
Method: POST
URL: {{baseUrl}}/transactions
Headers: Content-Type: application/json

Body:
{
  "fromAccountId": {{accountId}},
  "amount": 999999.00,
  "transactionType": "WITHDRAWAL",
  "description": "Large withdrawal test"
}

Test Script:
pm.test("Insufficient funds error", function () {
    pm.response.to.have.status(400);
    const response = pm.response.json();
    pm.expect(response.message).to.include("Insufficient funds");
});
```

**Test 13: Non-existent Resource**
```
Method: GET
URL: {{baseUrl}}/users/999999

Test Script:
pm.test("Resource not found error", function () {
    pm.response.to.have.status(404);
    const response = pm.response.json();
    pm.expect(response.message).to.include("User not found");
});
```

### Running Postman Tests

1. **Import Collection**: Save all tests as a Postman collection
2. **Set Environment**: Select the "Mobile Banking API" environment
3. **Run Collection**: Use Postman's Collection Runner to execute all tests
4. **View Results**: Check test results and response times

## RestAssured Testing

### Running Integration Tests

The project includes comprehensive RestAssured integration tests. Run them with:

```bash
mvn test -Dtest=BankingApiIntegrationTest
```

### Sample RestAssured Test Cases

```java
@Test
void userCrudOperations_ShouldWorkCorrectly() {
    // Create User
    String userJson = """
        {
            "username": "johndoe",
            "email": "john@example.com",
            "password": "password123",
            "fullName": "John Doe",
            "phoneNumber": "+1234567890"
        }
        """;

    Long userId = given()
            .contentType(ContentType.JSON)
            .body(userJson)
            .when()
            .post("/api/users")
            .then()
            .statusCode(201)
            .body("username", equalTo("johndoe"))
            .body("email", equalTo("john@example.com"))
            .extract()
            .path("id");

    // Verify user creation
    given()
            .when()
            .get("/api/users/" + userId)
            .then()
            .statusCode(200)
            .body("id", equalTo(userId.intValue()))
            .body("username", equalTo("johndoe"));
}
```

### Advanced Test Scenarios

**Transaction Flow Test:**
```java
@Test
void completeTransactionFlow_ShouldProcessCorrectly() {
    // Setup: Create user and two accounts
    Long userId = createTestUser();
    Long savingsAccountId = createTestAccount(userId, "SAVINGS", new BigDecimal("1000.00"));
    Long checkingAccountId = createTestAccount(userId, "CHECKING", new BigDecimal("500.00"));
    
    // Test: Perform transfer
    String transferJson = String.format("""
        {
            "fromAccountId": %d,
            "toAccountId": %d,
            "amount": 200.00,
            "transactionType": "TRANSFER",
            "description": "Transfer test",
            "fee": 2.50
        }
        """, savingsAccountId, checkingAccountId);
    
    given()
            .contentType(ContentType.JSON)
            .body(transferJson)
            .when()
            .post("/api/transactions")
            .then()
            .statusCode(201)
            .body("status", equalTo("COMPLETED"));
    
    // Verify: Check account balances
    given()
            .when()
            .get("/api/accounts/" + savingsAccountId)
            .then()
            .statusCode(200)
            .body("balance", equalTo(797.50f)); // 1000 - 200 - 2.50 fee
    
    given()
            .when()
            .get("/api/accounts/" + checkingAccountId)
            .then()
            .statusCode(200)
            .body("balance", equalTo(700.0f)); // 500 + 200
}
```

### Test Data Setup

**Custom Test Data Builders:**
```java
public class TestDataBuilder {
    
    public static String createUserJson(String username, String email) {
        return String.format("""
            {
                "username": "%s",
                "email": "%s",
                "password": "password123",
                "fullName": "Test User",
                "phoneNumber": "+1234567890"
            }
            """, username, email);
    }
    
    public static String createAccountJson(Long userId, String accountType, BigDecimal balance) {
        return String.format("""
            {
                "userId": %d,
                "accountType": "%s",
                "balance": %.2f
            }
            """, userId, accountType, balance);
    }
    
    public static String createTransactionJson(Long fromAccountId, Long toAccountId, 
                                             BigDecimal amount, String type, String description) {
        return String.format("""
            {
                "fromAccountId": %d,
                "toAccountId": %s,
                "amount": %.2f,
                "transactionType": "%s",
                "description": "%s"
            }
            """, fromAccountId, toAccountId != null ? toAccountId.toString() : "null", 
                 amount, type, description);
    }
}
```

## Security

### Current Implementation
- **Basic Authentication**: Development-level security
- **CSRF Protection**: Disabled for API endpoints
- **CORS**: Configured for cross-origin requests
- **Input Validation**: Bean validation on all DTOs
- **SQL Injection Prevention**: JPA/Hibernate parameter binding

### Production Security Recommendations
1. **JWT Authentication**: Implement token-based authentication
2. **Role-Based Access Control**: Add user roles and permissions
3. **Password Encryption**: Use BCrypt for password hashing
4. **Rate Limiting**: Implement API rate limiting
5. **HTTPS**: Force HTTPS in production
6. **Input Sanitization**: Additional input sanitization layers
7. **Audit Logging**: Comprehensive audit trail

### Sample Security Enhancement
```java
@Configuration
@EnableWebSecurity
public class ProductionSecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/transactions/**").hasRole("USER")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .build();
    }
}
```

## Error Handling

### Standard Error Response Format
```json
{
  "status": 404,
  "message": "User not found with id: 123",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Validation Error Response
```json
{
  "username": "Username is required",
  "email": "Email should be valid",
  "password": "Password must be at least 8 characters"
}
```

### Error Codes
- **400 Bad Request**: Validation errors, business rule violations
- **401 Unauthorized**: Authentication required
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **409 Conflict**: Duplicate resource (username, email, account number)
- **500 Internal Server Error**: Unexpected server errors

## Performance Considerations

### Database Optimization
1. **Indexing Strategy**:
   ```sql
   CREATE INDEX idx_users_username ON users(username);
   CREATE INDEX idx_users_email ON users(email);
   CREATE INDEX idx_accounts_user_id ON accounts(user_id);
   CREATE INDEX idx_accounts_account_number ON accounts(account_number);
   CREATE INDEX idx_transactions_from_account ON transactions(from_account_id);
   CREATE INDEX idx_transactions_created_at ON transactions(created_at);
   ```

2. **Connection Pooling**:
   ```yaml
   spring:
     datasource:
       hikari:
         maximum-pool-size: 20
         minimum-idle: 5
         connection-timeout: 20000
   ```

3. **Query Optimization**:
    - Use `@Query` annotations for complex queries
    - Implement pagination for large result sets
    - Use projections for read-only operations

### Caching Strategy
```java
@Service
@Transactional
public class UserService {
    
    @Cacheable("users")
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    @CacheEvict(value = "users", key = "#id")
    public User updateUser(Long id, UserDto userDto) {
        // Update logic
    }
}
```

### Monitoring and Metrics
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
```

## API Testing Checklist

### Functional Testing
- [ ] User CRUD operations work correctly
- [ ] Account creation and management functions properly
- [ ] All transaction types process correctly
- [ ] Balance calculations are accurate
- [ ] Transaction history is maintained

### Security Testing
- [ ] Authentication is required for protected endpoints
- [ ] Input validation prevents malicious data
- [ ] SQL injection attempts are blocked
- [ ] Cross-site scripting (XSS) is prevented

### Performance Testing
- [ ] API responses are under acceptable time limits
- [ ] Concurrent requests are handled properly
- [ ] Database connections are managed efficiently
- [ ] Memory usage is within acceptable bounds

### Error Handling Testing
- [ ] Appropriate HTTP status codes are returned
- [ ] Error messages are informative but not revealing
- [ ] Validation errors are properly formatted
- [ ] Unexpected errors are handled gracefully

## Deployment Considerations

### Environment Configuration
```yaml
# application.yml-prod.yml
spring:
  datasource:
    url: jdbc:postgresql://prod-db-host:5432/banking_db
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

logging:
  level:
    com.banking: INFO
    org.springframework.security: WARN

server:
  port: ${PORT:8083}
  ssl:
    enabled: true
    key-store: classpath:keystore.p12
    key-store-password: ${KEYSTORE_PASSWORD}
```

### Docker Configuration
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/mobile-banking-api-1.0.0.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Health Checks
```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    
    @Autowired
    private DataSource dataSource;
    
    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1)) {
                return Health.up()
                    .withDetail("database", "Available")
                    .build();
            }
        } catch (Exception e) {
            return Health.down()
                .withDetail("database", "Unavailable")
                .withException(e)
                .build();
        }
        return Health.down().build();
    }
}
```

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Commit your changes: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature/new-feature`
5. Submit a pull request

### Code Style Guidelines
- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Maintain test coverage above 80%
- Follow RESTful API design principles

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE) file for details.

## Support

For support and questions:
- Create an issue in the GitHub repository
- Email: support@bankingapi.com
- Documentation: [API Documentation](http://localhost:8083/swagger-ui/index.html)

---

**Version**: 1.0.0  
**Last Updated**: January 2024  
**Maintained by**: Banking API Team (Shady)