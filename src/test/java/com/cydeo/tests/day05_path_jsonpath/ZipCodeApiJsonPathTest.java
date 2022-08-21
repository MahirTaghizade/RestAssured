package com.cydeo.tests.day05_path_jsonpath;

import com.cydeo.utils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public class ZipCodeApiJsonPathTest {
    /**
     * Given accept type is json
     * and country path param value is "us"
     * and postal code path param value is 22102
     * When I send get request to  href=http://api.zippopotam.us/{country}/{postal-code}
     * Then status code is 200
     * Then "post code" is "22102"
     * And  "country" is "United States"
     * And "place name" is "Mc Lean"
     * And  "state" is "Virginia"
     */
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @DisplayName("GET US/ZipCode - JsonPath")
    @Test
    public void ZipCodeJsonPathTest() {

        Response response = given().log().all().accept(ContentType.JSON)
                .and().pathParam("country", "us")
                .and().pathParam("postal-code", "22102")
                .when().get("/{country}/{postal-code}");
        response.prettyPrint();

        // assign response body/payload to jsonPath
        JsonPath jasonPath = response.jsonPath();
        assertEquals(SC_OK, response.statusCode());
        System.out.println("Country name: " + jasonPath.get("country"));
        assertEquals("United States", jasonPath.get("country"));

        System.out.println("Post code: " + jasonPath.get("'post code'"));
        assertEquals("22102", jasonPath.get("'post code'"));


        verifyZipCode(jasonPath,"22102");


        assertEquals("Mc Lean", jasonPath.get("places[0].'place name'"));
        assertEquals("Virginia", jasonPath.get("places[0].state"));

    }

    public void verifyZipCode(JsonPath jsonPath, String expZipCode) {
        assertEquals(expZipCode, jsonPath.get("'post code'"));
    }
}