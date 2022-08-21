package com.cydeo.tests.day08_hamcrest;

import com.cydeo.utils.HRApiTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class ORDSHamcrestTest extends HRApiTestBase {
    /**
     * given accept type is json
     * when I send get request to /countries
     * Then status code is 200
     * and content type is json
     * and count should be 25
     * and country ids should contain "AR,AU,BE,BR,CA"
     * and country names should have "Argentina,Australia,Belgium,Brazil,Canada"
     * <p>
     * items[0].country_id ==> AR
     * items[1].country_id ==> AU
     */

    @DisplayName("GET /countries -> hamcrest assertions")
    @Test
    public void countriesTest() {
       String countryId = given().accept(JSON)
                .when().get("/countries")
                .then().statusCode(SC_OK)
                .and().contentType(JSON)
                .and().body("count", is(25),
                "items.country_id", hasItems("AR", "AU", "BE", "BR", "CA"),
                "items.country_name", hasItems("Argentina","Australia","Belgium","Brazil","Canada"),
                        "items[0].country_id",is("AR"),
                        "items[1].country_id",is("AU"))
                .and().extract().body().path("items[0].country_id");

        System.out.println(countryId);





        given().accept(JSON)
                .and().pathParam("country_id",countryId)
                .when().get("/countries/{country_id}")
                .then().statusCode(SC_OK)
                .and().contentType(JSON)
                .and().body("country_name",is(equalTo("Argentina")),
                "country_id", is("AR"),
                "region_id",is(2))
                .log().all();
                //.and().extract().jsonPath();
    }
}