package com.cydeo.tests.day13_access_tokens;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
public class BookItAccessTokenTest {
    /**
     * Given accept type is Json
     * And Query params: email = blyst6@si.edu & password = barbabaslyst
     * When I send GET request to
     * Url: https://cybertek-reservation-api-qa3.herokuapp.com/sign
     * Then status code is 200
     * And accessCode should be present
     */
    @Test
    public void bookItLoginTest(){

        Response response = given().accept(JSON)
                .and().queryParams("email", "blyst6@si.edu")
                .and().queryParam("password", "barbabaslyst")
                .when().get("https://cybertek-reservation-api-qa3.herokuapp.com/sign");
        response.prettyPrint();
        Assertions.assertEquals(200,response.statusCode());

        String  accessToken = response.path("accessToken");
        Assertions.assertTrue(accessToken!=null&& !accessToken.isEmpty());



    }
}
