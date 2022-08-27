package com.cydeo.tests.day14_specifications;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import com.cydeo.utils.BookItApiTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class BookItSpecTest extends BookItApiTestBase {
    /**
     Given accept type is json
     And header Authorization is access_token of teacher
     When I send GET request to /api/teachers/me
     Then status code is 200
     And content type is json
     And teacher info is presented in payload
     */
    @Test
    public void test(){
        Map teacherMap = given().spec(teacherReqSpec)
                .when().get("/api/teachers/me")
                .then().spec(teacherResSpec)
                .and().extract().body().as(Map.class);

        Assertions.assertEquals(1816,teacherMap.get("id"));
        Assertions.assertEquals("Barbabas",teacherMap.get("firstName"));
        Assertions.assertEquals("Lyst",teacherMap.get("lastName"));
        Assertions.assertEquals("teacher",teacherMap.get("role"));


    }
}
