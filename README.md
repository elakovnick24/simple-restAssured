![LOGO](https://fs-thb03.getcourse.ru/fileservice/file/thumbnail/h/b635b6cb9478bb87c77e9c070ee6e122.png/s/x50/a/159627/sc/207)

## Homework QA_GURU
___

### Rest Assured tests 

``` java
   void getUser() {
        when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2));
    }
```
``` java
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
```
``` java
    @Test
    void deleteUser() {
        when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }
```
```java
    @Test
    void deleteUser() {
        when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }

```
```java
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
```
```java
    @Test
    void getUsersWithDelay() {
        when()
                .get("/api/users?delay=5")
                .then()
                .statusCode(200)
                .body("total", is(12));
    }

```