package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import models.Credentionals;
import models.GenerateTokenResponse;
import models.lombok.CredentionalsLombok;
import models.lombok.GenerateTokenResponseLombok;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static listeners.CustomAllureListener.withCustomTemplates;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BookStoreTests extends BaseTest {

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "https://demoqa.com";
    }
    @Test
    void getBooksTest() {
        get(" /BookStore/v1/Books")
                .then()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void getBooksWithAllLogsTest() {
        given()
                .log().all()
                .get(" /BookStore/v1/Books")
                .then()
                .log().all()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void getBooksWithSomeLogsTest() {
        given()
                .log().uri()
                .log().body()
                .get(" /BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void generateTokenTest() {
        String data = "{ \"userName\": \"Testerovshik_QA\", " +
                "\"password\": \"Qwerty_12345!\" }";


        given()
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", (greaterThan(10)));
    }

    @Test
    void generateTokenWithAllureListenerTest() {
        String data = "{ \"userName\": \"Testerovshik_QA\", " +
                "\"password\": \"Qwerty_12345!\" }";

        //    RestAssured.filters(new AllureRestAssured()); move to @BeforeAll

        given()
                .filter(new AllureRestAssured())
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", (greaterThan(10)));
    }

    @Test
    void generateTokenWithCustomAllureListenerTest() {
        String data = "{ \"userName\": \"Testerovshik_QA\", " +
                "\"password\": \"Qwerty_12345!\" }";

        //    RestAssured.filters(new AllureRestAssured()); move to @BeforeAll

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", (greaterThan(10)));
    }

    @Test
    void getTokenTest() {
        String data = "{ \"userName\": \"Testerovshik_QA\", " +
                "\"password\": \"Qwerty_12345!\" }";

        String token =
                given()
                        .contentType(JSON)
                        .body(data)
                        .log().uri()
                        .log().body()
                        .post("/Account/v1/GenerateToken")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .body("status", is("Success"))
                        .body("result", is("User authorized successfully."))
                        .extract().path("token");

        System.out.println("Token: " + token);
    }

    @Test
    void generateTokenJsonSchemeCheckTest() {
        Credentionals credentionals = new Credentionals();
        credentionals.setUserName("Testerovshik_QA");
        credentionals.setPassword("Qwerty_12345!");

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(credentionals)
                .log().uri()
                .log().body()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/GenerateToken_response_scheme.json"))
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", (greaterThan(10)));
    }

    @Test
    void generateTokenWithModelTest() {
        Credentionals credentionals = new Credentionals();
        credentionals.setUserName("Testerovshik_QA");
        credentionals.setPassword("Qwerty_12345!");


        GenerateTokenResponse tokenResponse =
                given()
                        .filter(withCustomTemplates())
                        .body(credentionals)
                        .contentType(JSON)
                        .log().uri()
                        .log().body()
                        .when()
                        .post("/Account/v1/GenerateToken")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("schemas/GenerateToken_response_scheme.json"))
                        .extract().as(GenerateTokenResponse.class);

        assertThat(tokenResponse.getStatus()).isEqualTo("Success");
        assertThat(tokenResponse.getResult()).isEqualTo("User authorized successfully.");
        assertThat(tokenResponse.getExpires()).hasSizeGreaterThan(10);
        assertThat(tokenResponse.getToken()).hasSizeGreaterThan(10).startsWith("eyJ");
    }

    @Test
    void generateTokenWithLombokTest() {
        CredentionalsLombok credentionals = new CredentionalsLombok();
        credentionals.setUserName("Testerovshik_QA");
        credentionals.setPassword("Qwerty_12345!");

        GenerateTokenResponseLombok tokenResponse =
                given()
                        .filter(withCustomTemplates())
                        .contentType(JSON)
                        .body(credentionals)
                        .log().uri()
                        .log().body()
                        .when()
                        .post("/Account/v1/GenerateToken")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("schemas/GenerateToken_response_scheme.json"))
                        .extract().as(GenerateTokenResponseLombok.class);

        assertThat(tokenResponse.getStatus()).isEqualTo("Success");
        assertThat(tokenResponse.getResult()).isEqualTo("User authorized successfully.");
        assertThat(tokenResponse.getExpires()).hasSizeGreaterThan(10);
        assertThat(tokenResponse.getToken()).hasSizeGreaterThan(10).startsWith("eyJ");
    }
}
