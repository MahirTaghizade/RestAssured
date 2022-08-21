package com.cydeo.tests.day08_hamcrest;

import org.junit.jupiter.api.Test;

import com.cydeo.utils.SpartanTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.fromContentType;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class SpartanHamcrestTest extends SpartanTestBase {
    /*
    given accept type is json
    and path id is 24
    When i send get request to /spartans/{id}
    then status code is 200
    and content type is application/json
    and id" is 24,
    "name" is "Julio",
    "gender" is "Male",
    "phone" is 9393139934
     */
    @DisplayName("GET /spartans/{id} -> hamcrest assertions")
    @Test
    public void singleSpartanTest() {
        given().accept(JSON)
                .and().pathParam("id", 24)
                .when().get("/spartans/{id}")
                .then().statusCode(SC_OK)
                .and().contentType(JSON)
                .and().assertThat().body("id", equalTo(24),
                        "name", equalTo("Julio"),
                        "gender", is(equalToIgnoringCase("male")),
                        "phone", equalTo(9393139934L));
    }

    /**
     * Given accept type is json
     * And query param nameContains value is "e"
     * And query param gender value is "Female"
     * When I send get request to /spartans/search
     * Then status code is 200
     * And content type is Json
     * and header date contains 2022
     * And json response data is as expected
     * totalElement is 34
     * has ids: 94, 98,91, 81
     * has names: Jocelin, Georgianne, Catie, Marylee,Elita
     * every gender is Female
     * every name contains e
     */
    @DisplayName("GET /spartans/{id} -> hamcrest assertions")
    @Test
    public void searchTest() {
        given().accept(JSON)
                .and().queryParams("nameContains", "e")
                .and().queryParams("gender", "Female")
                .when().get("/spartans/search")
                .then().statusCode(SC_OK)
                .and().contentType(JSON)
                .and().header("Date", containsString("2022"))
                .and().header("Date", containsString(LocalDate.now().getYear() + ""))
                .and().body("totalElement", is(equalTo(34)),
                        "content.id", hasItems(94, 98, 81, 46),
                        "content.name", hasItems("Jocelin", "Georgianne", "Catie", "Marylee", "Elita"),
                        "content.gender", everyItem(equalTo("Female")),
                        "content.name", everyItem(containsStringIgnoringCase("e")))
                .log().all();



    }
}