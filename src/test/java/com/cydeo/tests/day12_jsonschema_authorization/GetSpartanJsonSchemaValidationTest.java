package com.cydeo.tests.day12_jsonschema_authorization;

import com.cydeo.utils.SpartanTestBase;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetSpartanJsonSchemaValidationTest extends SpartanTestBase {
    /**
     * given accept type is json
     * and path param id is 15
     * when I send GEt request to /spartans/{id}
     * then status code is 200
     * And json payload/body matches SingleSpartanSchema.json
     */
    @DisplayName("GET/spartan/{id} and json validation test")
    @Test
    public void singleSpartanSchemaValidationTest() {
        given().accept(JSON)
                .and().pathParam("id", 10)
                .when().get("/spartans/{id}")
                .then().statusCode(SC_OK)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/SingleSpartanSchema.json")))
                .and().log().all();
    }

    /**
     * given accept type is json
     * when I send GEt request to /spartans/{id}
     * then status code is 200
     * And json payload/body matches SingleSpartanSchema.json
     */
    @DisplayName("GET/spartan and json validation test")
    @Test
    public void allSpartanSchemaValidationTest() {
        given().accept(JSON)
                .when().get("/spartans")
                .then().statusCode(SC_OK)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/AllSpartansSchema.json")
                )).log().all();
    }

    /**
     * given accept type is json
     * And query params: nameContains "e" and gender "Female"
     * when I send GET request to /spartans
     * Then status code is 200
     * And json payload/body matches SearchSpartansSchema.json
     */
    @DisplayName("GET/spartan and json validation test")
    @Test
    public void searchSpartanSchemaValidationTest() {
given().accept(JSON)
        .and().queryParams("nameContains","e")
        .and().queryParams("gender","Female")
        .when().get("/spartans/search")
        .then().statusCode(SC_OK)
        .and().body(JsonSchemaValidator.matchesJsonSchema(
                new File("src/test/resources/jsonschemas/SearchSpartansSchema.json")
        )).log().all();
    }






}