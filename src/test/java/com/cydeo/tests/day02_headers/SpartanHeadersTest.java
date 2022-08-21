package com.cydeo.tests.day02_headers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanHeadersTest {
    String url = "http://54.87.134.20:8000/api/spartans";

    /**
     * • When I send a GET request to
     * • spartan_base_url/api/spartans
     * • Then Response STATUS CODE must be 200
     * • And I Should see all Spartans data in JSON format
     */
    @DisplayName("GET /api/spartans and expect Json response")
    @Test
    public void getAllSpartansHeaderTest() {

        when().get(url)
                .then().statusCode(200) // i can add also assert.that()
                //.and().contentType("application/json");
                .and().contentType(ContentType.JSON);
    }

    /**
     * Given Accept type is application/xml
     * • When I send a GET request to
     * • spartan_base_url/api/spartans
     * • Then Response STATUS CODE must be 200
     * • And I Should see all Spartans data in xml format
     */
    @Test
    @DisplayName("GET /api/spartans with req header")
    public void acceptTypeHeaderTest() {
        given().accept(ContentType.XML)
                .when().get(url)
                .then().statusCode(200)
                .and().contentType(ContentType.XML);
    }
    /**
     * Given Accept type is application/json
     • When I send a GET request to
     -----------------------------
     • spartan_base_url/api/spartans
     • Then Response STATUS CODE must be 200
     • And read headers
     */
    @Test
    @DisplayName("GET /api/spartans with req header")
    public void responseHeaders() {
        Response response=given().accept(ContentType.JSON)
                .when().get(url);
        System.out.println(response.getHeader("Date"));
        System.out.println(response.getHeader("Content-Type"));
        System.out.println(response.getHeader("Connection"));

        // read all headers
        System.out.println(response.getHeaders());

        // ensure header keep-alive is present
        assertNotNull(response.getHeader("keep-alive"));
    }
}