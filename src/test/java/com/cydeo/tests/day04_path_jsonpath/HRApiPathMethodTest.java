package com.cydeo.tests.day04_path_jsonpath;
import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import com.cydeo.utils.HRApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HRApiPathMethodTest extends HRApiTestBase {

    @Test
    public void readCountriesUsingPathTest() {

        Response response = given().log().all().accept(ContentType.JSON)
                .when().get("/countries");
        assertEquals(SC_OK,response.statusCode());

        System.out.println(" first country id: "+response.path("items[0].country_id"));
        System.out.println(" first country name: "+response.path("items[0].country_name"));

        assertEquals("AR",response.path("items[0].country_id"));

        // store all country names to a list
        List<String >countryNames = response.path("items.country_name");
        System.out.println(countryNames);


    }
}
