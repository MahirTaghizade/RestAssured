package com.cydeo.tests.day01_intro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apiguardian.api.API;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import javax.annotation.meta.When;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

        /*
    When User sends GET Request to
    https://reqres.in/api/users
    Then Response status code should be 200
    And Response body should contain "George"
    And Header Content type should be json
         */

public class ReqResApiTest {
    @DisplayName("Get all users")
    @Test
    public void usersGetTest() {

        //When User sends GET Request to
        Response response = when().get("https://reqres.in/api/users");
        //Then Response status code should be 200
        assertEquals(200, response.statusCode());
        // BBD Syntax
        response.then().statusCode(200);
        response.then().assertThat().statusCode(200);

        //And Response body should contain "George"
        assertTrue(response.asString().contains("George"));
        //And Header Content type should be json
        assertTrue(response.contentType().contains("json"));

    }
    /*
    When User Sends get request to API Endpoint:
            "https://reqres.in/api/users/5"
    Then Response status code should be 200
    And Response body should contain user info "Charles"
    */
    @DisplayName("Get single user")
    @Test
    public void getSingleUserApiTest() {
        Response response = when().get("https://reqres.in/api/users/5");
        assertEquals(200,response.statusCode());
        response.then().statusCode(200);
        response.prettyPrint();
        assertTrue(response.asString().contains("Charles"));

    }
    /*
    When Send get request to API Endpoint:
            "https://reqres.in/api/users/50"
    Then Response status code should be 404
    And Response body should contain "{}"
    */
    @DisplayName("Find singl user")
    @Test
    public void getSingleUserNegativeApiTest() {
        String url = "https://reqres.in/api/users";
        Response response = when().get(url+"/50");
        response.then().statusCode(404);
        assertEquals("{}",response.asString());

    }
}
