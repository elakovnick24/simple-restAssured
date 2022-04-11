import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

public class ReqresinTests {

    @BeforeAll
    static void setUpAll() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @AfterAll
    static void tearDownAll() {
    }


    @Test
    void successfulLogin() {
        String authorizedData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

        given()
                .body(authorizedData)
                .contentType(ContentType.JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void missingPasswordLogin() {
        String authorizedData = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .body(authorizedData)
                .contentType(ContentType.JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
//######################################################################################################################
    @Test
    void getUser() {
        when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2));
    }

    @Test
    void updateUser() {
        String updateNewData = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";

        given()
                .body(updateNewData)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/users/2")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    @Test
    void deleteUser() {
        when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void createUser() {
        String userData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .body(userData)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("id", is(4));
    }

    @Test
    void getUsersWithDelay() {
        when()
                .get("/api/users?delay=5")
                .then()
                .statusCode(200)
                .body("total", is(12));
    }

}
