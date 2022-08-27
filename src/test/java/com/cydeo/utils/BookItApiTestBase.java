package com.cydeo.utils;

import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;


import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public abstract class BookItApiTestBase {
    protected static RequestSpecification teacherReqSpec;
    protected static ResponseSpecification responseSpec;


    @BeforeAll
    public static void sttUp() {
        baseURI = ConfigurationReader.getProperty("bookit.base.url");

        String teacherToken = getAccessToken(ConfigurationReader.getProperty("teacher_email"),
                ConfigurationReader.getProperty("teacher_password"));

        teacherReqSpec = given().accept(JSON)
                .and().header("Authorization", teacherToken)
                .log().all();

        responseSpec =expect().statusCode(SC_OK)
                .and().contentType(JSON)
                .logDetail(LogDetail.ALL);

    }

    public static String getAccessToken(String email, String password) {
        String accessToken = given().accept(JSON)
                .and().queryParams("email", email)
                .and().queryParam("password", password)
                .when().get("/sign")
                .then().statusCode(200)
                .and().extract().jsonPath().get("accessToken");

        assertThat("accessToken is empty or null", accessToken, not(emptyOrNullString()));

        return "Bearer " + accessToken;
    }
}
