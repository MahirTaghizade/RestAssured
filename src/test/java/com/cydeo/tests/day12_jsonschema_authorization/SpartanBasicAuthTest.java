package com.cydeo.tests.day12_jsonschema_authorization;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import com.cydeo.utils.SpartanSecureTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpartanBasicAuthTest extends SpartanSecureTestBase {
    /**
     given accept type is json
     and credentials are admin, admin
     When user sends GET request to /spartans
     Then status code is 201
     And content type is json
     And log response
     */
    @DisplayName("GET /spartans with basic auth")
    @Test
    public void getSpartansWithAuthTest() {
        given().accept(JSON)
                .and().auth().basic("admin", "admin")
                .when().get("/spartans")
                .then().statusCode(SC_OK).contentType(JSON)
                .and().log().all();
    }

    /**
     given accept type is json
     When user sends GET request to /spartans
     Then status code is 401
     And content type is json
     And log response
     */

    @DisplayName("GET /spartans with basic auth")
    @Test
    public void getAllSpartansUnAuthorizedTest() {
        given().accept(JSON)
                .when().get("/spartans")
                .then().statusCode(401)
                .and().contentType(JSON)
                .and().body("error",equalTo("Unauthorized"))
                .and().log().all();


    }
}