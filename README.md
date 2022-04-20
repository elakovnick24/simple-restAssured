![LOGO](https://fs-thb03.getcourse.ru/fileservice/file/thumbnail/h/b635b6cb9478bb87c77e9c070ee6e122.png/s/x50/a/159627/sc/207)

## Homework QA_GURU

___

### Rest Assured tests

```java
@Test
    void generateTokenWithLombokTest(){
            CredentionalsLombok credentionals=new CredentionalsLombok();
            credentionals.setUserName(username);
            credentionals.setPassword(pass);

            GenerateTokenResponseLombok tokenResponse=
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
 ```
