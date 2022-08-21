package com.cydeo.tests.day12_jsonschema_authorization;

import com.cydeo.pojo.Spartan;
import com.cydeo.utils.SpartanRestUtils;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpartanPostJsonSchemaValidationTest extends SpartanTestBase {


    /**
     given accept is json and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "TestPost1"
     "phone": 1234567425
     }
     When I send POST request to /spartans
     Then status code is 201
     And body should match SpartanPostSchema.json schema
     */

    @DisplayName("POST/spartan -> json schema test")
    @Test
    public void postSpartanSchemaTest() {
        Map<String ,Object> newSpartan = new HashMap<>();
        newSpartan.put("gender","Male");
        newSpartan.put("name","TestPost1");
        newSpartan.put("phone","1234567425");

        int spartanId= given().accept(JSON)
                .and().contentType(JSON)
                .and().body(newSpartan)
                .when().post("/spartans")
                .then().statusCode(SC_CREATED)
                .and().body(JsonSchemaValidator.matchesJsonSchema(
                        new File("src/test/resources/jsonschemas/SpartanPostSchema.json")
                )).log().all()
                .and().extract().jsonPath().getInt("data.id");

        SpartanRestUtils.deleteSpartanById(spartanId);


    }
}