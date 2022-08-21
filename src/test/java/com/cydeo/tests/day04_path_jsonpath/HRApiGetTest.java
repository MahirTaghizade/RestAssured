package com.cydeo.tests.day04_path_jsonpath;
import com.cydeo.utils.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class HRApiGetTest {
    @BeforeEach
    public void setUp() {
        baseURI = ConfigurationReader.getProperty("hr.api.url");
    }

    @DisplayName("GET/ Regions")
    @Test
    public void getRegionsTest() {
        /**
         * Given accept type is json
         * When user send get request to /ords/hr/regions
         * Status code should be 200
         * Content type should be "application/json"
         * And body should contain "Europe"
         */
        Response response = given().accept(ContentType.JSON)
                .when().get("/regions");
        response.prettyPrint();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("application/json", response.contentType());
        Assertions.assertTrue(response.body().asString().contains("Europe"), "Europe is not in Json body");
    }

    @DisplayName("GET/regions/{region_id} path param")
    @Test
    public void getSinglePathParamTest() {
        /**
         * Given accept type is json
         And Path param "region_id" value is 1
         When user send get request to /ords/hr/regions/{region_id}
         Status code should be 200
         Content type should be "application/json"
         And body should contain "Europe"
         */
        Response response = given().log().all().accept(ContentType.JSON)
                .and().pathParam("region_id", 1)
                .when().get("/regions/{region_id}");
        response.prettyPrint();

        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        Assertions.assertEquals("application/json",response.contentType());
        assertTrue(response.asString().contains("Europe"),"Europe is not in Json body");

    }
    /**
     * Given accept type is json
     * And query param q={"region_name": "Americas"}
     * When user send get request to /ords/hr/regions
     * Status code should be 200
     * Content type should be "application/json"
     * And region name should be "Americas"
     * And region id should be "2"
     */
    @DisplayName("GET /regions?q={\"region_name\": \"Americas\"}")
    @Test
    public void getRegionWithQueryParamTest() {
        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_name\": \"Americas\"}")
                .when().get("/regions");

        response.prettyPrint();

        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        Assertions.assertEquals("application/json",response.contentType());
        assertTrue(response.asString().contains("Europe"),"Europe is not in Json body");
        assertTrue(response.asString().contains("Europe"),"Region is is incorrect");



    }
}
