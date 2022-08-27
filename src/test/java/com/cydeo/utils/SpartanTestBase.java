package com.cydeo.utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

public abstract class SpartanTestBase {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeAll// @BeforeClass in junit 4
    public static void setUp() {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.api.url");

        requestSpec = given().accept(JSON)
                .and().auth().basic("admin", "admin");
        responseSpec = expect().statusCode(200)
                .and().contentType(JSON);
    }
}
