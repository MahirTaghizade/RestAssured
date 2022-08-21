package com.cydeo.tests.day04_path_jsonpath;

import com.cydeo.utils.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.xmlbeans.soap.SOAPArrayType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanPathMethodTest extends SpartanTestBase {
    /**
     * Given accept is json
     * And path param id is 13
     * When I send get request to /api/spartans/{id}
     * ----------
     * Then status code is 200
     * And content type is json
     * And id value is 13
     * And name is "Jaimie"
     * And gender is "Female"
     * And phone is "7842554879"
     */
    @DisplayName("GET/spartan/api/{id}/ and path()")
    @Test
    public void readSpartanJsonUsingPathTest() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 13)
                .when().get("/spartans/{id}");

        System.out.println("id = " + response.path("id"));
        System.out.println("name = " + response.path("name"));
        System.out.println("gender = " + response.path("gender"));
        System.out.println("phone = " + response.path("phone"));

        assertEquals(SC_OK, response.statusCode());
        assertEquals("application/json", response.contentType());
        assertEquals("13", response.path("id").toString());
        assertEquals("Jaimie", response.path("name"));
        assertEquals("Female", response.path("gender"));
        assertEquals("7842554879", response.path("phone").toString());

    }

    @DisplayName("GET/spartans and path()")
    @Test
    public void readSpartanJsonArrayUsingPathTest() {
        /**
         Given accept is json
         When I send get request to /api/spartans0
         Then status code is 200
         And content type is json
         And I can navigate json array object
         */
        Response response = given().log().all().accept(ContentType.JSON)
                .when().get("/spartans");

        assertEquals(SC_OK, response.statusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        System.out.println("All ids: "+response.path("id"));
        System.out.println("first spartan id: "+response.path("id[0]"));
        System.out.println("First person name: "+response.path("name[0]"));
        System.out.println("First person name: "+response.path("[0].name"));// same as before

        // print last spartan id (-1) last index
        System.out.println("last spartan id: "+response.path("id[-1]"));
        System.out.println("last person name: "+response.path("name[-1]"));

        // get all id's in to a list
        List<Integer> ids = response.path("id");
        System.out.println("Id s size: "+ids.size());
        System.out.println("All ids; "+ids);
        assertTrue(ids.contains(22)&&ids.size()==100);

        // get all names into a list and say Hi
        List<String> names=response.path("name");
        names.forEach(name-> System.out.println("Hi "+name));




    }
}
