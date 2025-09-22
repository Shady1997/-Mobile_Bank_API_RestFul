import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BankingApiIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

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

        // Get User by ID
        given()
                .when()
                .get("/api/users/" + userId)
                .then()
                .statusCode(200)
                .body("id", equalTo(userId.intValue()))
                .body("username", equalTo("johndoe"));

        // Update User
        String updatedUserJson = """
                {
                    "username": "johndoe",
                    "email": "john.doe@example.com",
                    "password": "password123",
                    "fullName": "John Updated Doe",
                    "phoneNumber": "+1234567890"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(updatedUserJson)
                .when()
                .put("/api/users/" + userId)
                .then()
                .statusCode(200)
                .body("fullName", equalTo("John Updated Doe"))
                .body("email", equalTo("john.doe@example.com"));

        // Delete User
        given()
                .when()
                .delete("/api/users/" + userId)
                .then()
                .statusCode(204);

        // Verify user is deleted
        given()
                .when()
                .get("/api/users/" + userId)
                .then()
                .statusCode(404);
    }

    @Test
    void accountCrudOperations_ShouldWorkCorrectly() {
        // First create a user
        String userJson = """
                {
                    "username": "accountholder",
                    "email": "account@example.com",
                    "password": "password123",
                    "fullName": "Account Holder",
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
                .extract()
                .path("id");

        // Create Account
        String accountJson = String.format("""
                {
                    "userId": %d,
                    "accountType": "SAVINGS",
                    "balance": 1000.00,
                    "creditLimit": 0.00
                }
                """, userId);

        Long accountId = given()
                .contentType(ContentType.JSON)
                .body(accountJson)
                .when()
                .post("/api/accounts")
                .then()
                .statusCode(201)
                .body("accountType", equalTo("SAVINGS"))
                .body("balance", equalTo(1000.0f))
                .extract()
                .path("id");

        // Get Account by ID
        given()
                .when()
                .get("/api/accounts/" + accountId)
                .then()
                .statusCode(200)
                .body("id", equalTo(accountId.intValue()))
                .body("accountType", equalTo("SAVINGS"));

        // Get Accounts by User ID
        given()
                .when()
                .get("/api/accounts/user/" + userId)
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", equalTo(accountId.intValue()));

        // Update Account
        String updatedAccountJson = String.format("""
                {
                    "userId": %d,
                    "accountType": "CHECKING",
                    "balance": 1500.00,
                    "creditLimit": 500.00
                }
                """, userId);

        given()
                .contentType(ContentType.JSON)
                .body(updatedAccountJson)
                .when()
                .put("/api/accounts/" + accountId)
                .then()
                .statusCode(200)
                .body("accountType", equalTo("CHECKING"))
                .body("balance", equalTo(1500.0f));
    }

    @Test
    void transactionOperations_ShouldWorkCorrectly() {
        // Create user and account first
        String userJson = """
                {
                    "username": "transactionuser",
                    "email": "transaction@example.com",
                    "password": "password123",
                    "fullName": "Transaction User",
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
                .extract()
                .path("id");

        String accountJson = String.format("""
                {
                    "userId": %d,
                    "accountType": "CHECKING",
                    "balance": 1000.00
                }
                """, userId);

        Long accountId = given()
                .contentType(ContentType.JSON)
                .body(accountJson)
                .when()
                .post("/api/accounts")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Create Transaction (Deposit)
        String transactionJson = String.format("""
                {
                    "fromAccountId": %d,
                    "amount": 500.00,
                    "transactionType": "DEPOSIT",
                    "description": "Initial deposit"
                }
                """, accountId);

        Long transactionId = given()
                .contentType(ContentType.JSON)
                .body(transactionJson)
                .when()
                .post("/api/transactions")
                .then()
                .statusCode(201)
                .body("amount", equalTo(500.0f))
                .body("transactionType", equalTo("DEPOSIT"))
                .extract()
                .path("id");

        // Get Transaction by ID
        given()
                .when()
                .get("/api/transactions/" + transactionId)
                .then()
                .statusCode(200)
                .body("id", equalTo(transactionId.intValue()))
                .body("status", equalTo("COMPLETED"));

        // Get Transactions by Account ID
        given()
                .when()
                .get("/api/transactions/account/" + accountId)
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", equalTo(transactionId.intValue()));
    }

    @Test
    void errorHandling_ShouldReturnAppropriateErrors() {
        // Test 404 for non-existent user
        given()
                .when()
                .get("/api/users/999")
                .then()
                .statusCode(404)
                .body("message", containsString("User not found"));

        // Test validation error for invalid user data
        String invalidUserJson = """
                {
                    "username": "",
                    "email": "invalid-email",
                    "password": "123"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(invalidUserJson)
                .when()
                .post("/api/users")
                .then()
                .statusCode(400);
    }
}