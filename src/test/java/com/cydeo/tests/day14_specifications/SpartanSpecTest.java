package com.cydeo.tests.day14_specifications;

import com.cydeo.utils.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.*;

public class SpartanSpecTest extends SpartanTestBase {

//    RequestSpecification requestSpec = given().accept(JSON)
//            .and().auth().basic("admin", "admin");

//    ResponseSpecification responseSpec = expect().statusCode(200)
//            .and().contentType(JSON);

    @Test
    public void allSpartansTest() {
        given().spec(requestSpec)
                .when().get("/spartans")
                .then().spec(responseSpec)
                .log().all();

    }

    @Test
    public void allSpartansTest2() {
        given().spec(requestSpec)
                .and().pathParam("id",10)
                .when().get("/spartans/{id}")
                .then().spec(responseSpec)
                .and().body("name",equalTo("Lorenza"))
                .log().all();

    }

}

