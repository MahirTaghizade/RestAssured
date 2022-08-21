package com.cydeo.tests.day03_parameters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanApiWithParamsTest {

    String url = "http://54.87.134.20:8000/api/spartans";

    @DisplayName("GET/api/spartans/{id}")
    @Test
    public void getSingleSpartan() {
        /**
         Given accept type is Json
         And Id parameter value is 5
         When user sends GET request to /api/spartans/{id}
         Then response status code should be 200
         And response content-type: application/json
         And "Blythe" should be in response payload(body)
         */

        given().accept(ContentType.JSON)
                .when().get(url + "/5");

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 5)
                .when().get(url + "/{id}");
        response.prettyPrint();

        System.out.println(response.statusCode());
        assertEquals(HttpStatus.SC_OK, response.statusCode());

        System.out.println(response.getContentType());
        assertTrue(response.getHeader("content-type").contains("application/json"));

        assertTrue(response.asString().contains("Blythe"));

    }

    @DisplayName("GET/api/spartans/{id} with missing id")
    @Test
    public void getSingleSpartanNotFound() {
        /**Given accept type is Json
         And Id parameter value is 500
         When user sends GET request to /api/spartans/{id}
         Then response status code should be 404
         And response content-type: application/json
         And "Not Found" message should be in response payload
         */

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .when().get(url + "/{id}");

        assertEquals(404, response.statusCode());
        assertTrue(response.contentType().contains("application/json"));
        assertEquals(ContentType.JSON.toString(), response.contentType());
        assertTrue(response.asString().contains("Not Found"));

        response.prettyPrint();


    }

}