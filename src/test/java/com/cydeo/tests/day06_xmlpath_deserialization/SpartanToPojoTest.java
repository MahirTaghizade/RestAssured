package com.cydeo.tests.day06_xmlpath_deserialization;

import com.cydeo.pojo.Spartan;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.XML;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanToPojoTest extends SpartanTestBase {

    @DisplayName("GET /spartan/{id} -> pojo object")
    @Test
    public void spartanToPojoTest() {
        Response response = given().accept(JSON)
                .and().pathParam("id", 10)
                .when().get("/spartans/{id}");

        response.prettyPrint();
        assertEquals(SC_OK, response.statusCode());
        assertEquals(JSON.toString(), response.contentType());

        // DE-SERIALIZATION JSON->POJO OBJECT
        Spartan spartan = response.as(Spartan.class);
        System.out.println(spartan.getGender());
        System.out.println(spartan.getId());
        System.out.println(spartan.getName());
        System.out.println(spartan.getPhone());
        System.out.println(spartan);



    }
}