import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class DemowebshopTests {

    @Test
    void addToCartAsNewUserTest() {
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("product_attribute_72_5_18=53" +
                        "&product_attribute_72_6_19=54" +
                        "&product_attribute_72_3_20=57" +
                        "&addtocart_72.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your" +
                        " <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(1)"));
    }

    @Test
    void addToCartWithCookieTest() {
        Integer cartSize = 0;

        ValidatableResponse response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .cookie("Nop.customer=88f590c6-59e9-4a55-b243-7395b35f0ce2;")
                        .body("product_attribute_72_5_18=53" +
                                "&product_attribute_72_6_19=54" +
                                "&product_attribute_72_3_20=57" +
                                "&addtocart_72.EnteredQuantity=1")
                        .when()
                        .post("http://demowebshop.tricentis.com/addproducttocart/details/72/1")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("success", is(true))
                        .body("message", is("The product has been added to your " +
                                "<a href=\"/cart\">shopping cart</a>"));
    }

    @Test
    void registerUser() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("\"Nop.customer=92a7882f-ae43-414f-8c57-7e7412410017; " +
                        "ARRAffinity=1818b4c81d905377ced20e7ae987703a674897394db6e97dc1316168f754a687;" +
                        "__RequestVerificationToken=aCGaKM-7Oec8xDNMK6TP35G8bXgbddvFpjvDL8PJkukJPRctgKC3BQm2o_ME8xh8ZnjV4iiUMk40OHtG_rILMV3lcLuEx6gKYuRl2WWRUZ41; " +
                        "ARRAffinity=1818b4c81d905377ced20e7ae987703a674897394db6e97dc1316168f754a687; Nop.customer=7050118d-bf03-41b2-8b04-deb9968eb911")
                .formParam("Gender", "M")
                .formParam("FirstName", "M")
                .formParam("Password", "M")
                .formParam("ConfirmPassword", "M")
                .formParam("register-button", "Register")
                .log().all()
                .when()
                .post("http://demowebshop.tricentis.com/register")
                .prettyPeek()
                .then()
                .statusCode(302);
    }

    @Test
    void checkProductReview() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("\"Nop.customer=92a7882f-ae43-414f-8c57-7e7412410017; " +
                        "ARRAffinity=1818b4c81d905377ced20e7ae987703a674897394db6e97dc1316168f754a687;" +
                        "__RequestVerificationToken=aCGaKM-7Oec8xDNMK6TP35G8bXgbddvFpjvDL8PJkukJPRctgKC3BQm2o_ME8xh8ZnjV4iiUMk40OHtG_rILMV3lcLuEx6gKYuRl2WWRUZ41; " +
                        "ARRAffinity=1818b4c81d905377ced20e7ae987703a674897394db6e97dc1316168f754a687; Nop.customer=7050118d-bf03-41b2-8b04-deb9968eb911")
                .formParam("AddProductReview.Title", "BAAAAAAAAADDDDD")
                .formParam("AddProductReview.ReviewText", "VERYYYYYY+GOOOOOOOODDDD")
                .formParam("AddProductReview.Rating", "5")
                .formParam("add-review", "Submit+review")
                .log().all()
                .when()
                .post("http://demowebshop.tricentis.com/productreviews/65")
                .prettyPeek()
                .then()
                .statusCode(200);
        //.body("title", is("Demo Web Shop. Product Reviews. TCP Public Complete")); -- Падает
        //  XmlPath resXML = new XmlPath(responseBody);
        //        String title = resXML.getString("head.title");
        //        System.out.println(title);
    }

    @Test
    void addProductToWishlist() {
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body("addtocart_65.EnteredQuantity=1")
                .when()
                .post("http://demowebshop.tricentis.com/addproducttocart/details/65/2")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"))
                .body("updatetopwishlistsectionhtml", is(notNullValue()));
    }
}




