package com.cydeo.tests.day07_deserialization;

import com.cydeo.pojo.Spartan;
import com.cydeo.pojo.SpartanSearch;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanSearchPOJOTest extends SpartanTestBase {

    @Test
    public void SpartanSearchToPOJOTest() {
        /**
         * Given accept type is json
         * And query param nameContains value is "e"
         * And query param gender value is "Female"
         * When I send get request to /spartans/search
         * Then status code is 200
         * And content type is Json
         * And json response data is as expected
         */
        Map<String ,String >mapQuery= new HashMap<>();
        mapQuery.put("nameContains","e");
        mapQuery.put("gender","Female");

        Response response = given().accept(JSON)
                .and().queryParams(mapQuery)
                .when().get("/spartans/search");

        response.prettyPrint();

        assertEquals(SC_OK, response.statusCode());
        assertEquals(JSON.toString(), response.contentType());

        // deserialize

        SpartanSearch spartanSearch = response.body().as(SpartanSearch.class);

        // total elements
        System.out.println("total: "+spartanSearch.getTotalElement());
        System.out.println("All spartans: "+spartanSearch.getContent());
        System.out.println("First spartan :"+spartanSearch.getContent().get(0));

        Spartan secondSpartan = spartanSearch.getContent().get(1);
        System.out.println(secondSpartan);

        Integer secondSpartanId = secondSpartan.getId();
        String secondSpartanName = secondSpartan.getName();
        System.out.println(secondSpartanName);


        List<Spartan> spartanData= spartanSearch.getContent();
        System.out.println("second  sparta: "+spartanData.get(1));

        // read all names as a list
        List<String > names = new ArrayList<>();
        for (Spartan spartan:spartanData) {
            names.add(spartan.getName());
        }
        System.out.println(names);

        List<String> allNames = spartanData.stream().map(Spartan::getName).collect(Collectors.toList());
        System.out.println(allNames);

    }
}