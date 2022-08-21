package com.cydeo.tests.day06_xmlpath_deserialization;
import com.cydeo.utils.SpartanTestBase;
import groovy.xml.XmlParser;
import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.cydeo.utils.SpartanTestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.XML;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.*;
public class SpartanToMapTest extends SpartanTestBase{
    /**
     Given accept type is application/json
     And Path param id = 10
     When i send GET request to /api/spartans
     Then status code is 200
     And content type is json
     And spartan data matching:
     id > 10
     name>Lorenza
     gender >Female
     phone >3312820936
     */

    @Test
    public void spartanToMapTest(){
        Response response = given().accept(JSON)
                .and().pathParam("id", 10)
                .when().get("/spartans/{id}");

        response.prettyPrint();
        assertEquals(SC_OK,response.statusCode());
        assertEquals(JSON.toString(),response.contentType());

        Map<String ,?> spartanMap = response.as(Map.class);
        System.out.println(spartanMap);

        System.out.println(spartanMap.keySet());
        assertEquals(10,spartanMap.get("id"));
        assertEquals("Lorenza",spartanMap.get("name"));
        assertEquals("Female",spartanMap.get("gender"));
        assertEquals("3312820936",spartanMap.get("phone").toString());









    }




}
