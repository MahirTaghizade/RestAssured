package com.cydeo.tests.day09_post_put_delete;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.cydeo.pojo.Spartan;
import com.cydeo.utils.SpartanRestUtils;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SpartanPostTest extends SpartanTestBase {
    /**
     * given accept is json and content type is json
     * and body is:
     * {
     * "gender": "Male",
     * "name": "TestPost1"
     * "phone": 1234567425
     * }
     * When I send POST request to /spartans
     * Then status code is 201
     * And content type is json
     * And "success" is "A Spartan is Born!"
     * Data name, gender , phone matches my request details
     */
    @DisplayName("POST new spartan using string json")
    @Test
    public void addNewSpartanAsJsonTest() {
        String jsonBody = "{\n" +
                "     \"gender\": \"Male\",\n" +
                "     \"name\": \"TestPost1\",\n" +
                "     \"phone\": 1234567425\n" +
                "     }";

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post("/spartans");

        response.prettyPrint();
        System.out.println("status code = " + response.statusCode());
        assertThat(response.statusCode(), is(201));

        //verify header
        assertThat(response.contentType(), is("application/json"));

        //assign response to jsonpath
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Spartan is Born!"));
        assertThat(jsonPath.getString("data.name"), equalTo("TestPost1"));

        assertThat(jsonPath.getString("data.gender"), equalTo("Male"));
        assertThat(jsonPath.getLong("data.phone"), equalTo(1234567425L));
        System.out.println();

        // delete created spartan
        int spartanId = jsonPath.getInt("data.id");
        SpartanRestUtils.deleteSpartanById(spartanId);


    }

    @DisplayName("POST new spartan using string json")
    @Test
    public void addNewSpartanAsJsonTest1() {
        String jsonBody = "{\n" +
                "     \"gender\": \"Male\",\n" +
                "     \"name\": \"TestPost1\",\n" +
                "     \"phone\": 1234567425\n" +
                "     }";
        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(jsonBody)
                .when().post("/spartans")
                .then().statusCode(SC_CREATED)
                .and().contentType(JSON)
                .and().body("success", equalTo("A Spartan is Born!"),
                        "data.name", is("TestPost1"),
                        "data.gender", is("Male"),
                        "data.phone", is(1234567425));
    }

    @DisplayName("POST /spartans using map - SERIALIZATION")
    @Test
    public void addNewSpartanAsMapTest() {
        Map<String, Object> spartanPostMap = new HashMap<>();
        spartanPostMap.put("gender", "Male");
        spartanPostMap.put("name", "TestPost1");
        spartanPostMap.put("phone", "1234567425");

        Response response = given().accept(JSON)
                .and().contentType(JSON)
                .and().body(spartanPostMap) //-> Map to Json -SERIALIZATION
                .when().post("/spartans");

        response.prettyPrint();
        assertThat(response.statusCode(), is(SC_CREATED));

        //verify header
        assertThat(response.contentType(), is(JSON.toString()));

        //assign response to jsonpath
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Spartan is Born!"));

        assertThat(jsonPath.getString("data.name"), equalTo(spartanPostMap.get("name")));
        assertThat(jsonPath.getString("data.gender"), equalTo(spartanPostMap.get("gender")));
        assertThat(jsonPath.getString("data.phone"), equalTo(spartanPostMap.get("phone").toString()));


        int spartanId = jsonPath.getInt("data.id");
        SpartanRestUtils.deleteSpartanById(spartanId);

    }

    @DisplayName("POST /spartans using POJO - SERIALIZATION")
    @Test
    public void addNewSpartanAsPOJOTest() {
        Spartan newSpartan = new Spartan();
        newSpartan.setGender("Male");
        newSpartan.setName("TestPost1");
        newSpartan.setPhone(1234567425L);

        Response response = given().accept(JSON)
                .and().contentType(JSON)
                .and().body(newSpartan)
                .when().post("/spartans");

        response.prettyPrint();
        assertThat(response.statusCode(), is(SC_CREATED));

        //verify header
        assertThat(response.contentType(), is(JSON.toString()));

        //assign response to jsonpath
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.getString("success"), equalTo("A Spartan is Born!"));

        assertThat(jsonPath.getString("data.name"), equalTo(newSpartan.getName()));
        assertThat(jsonPath.getString("data.gender"), equalTo(newSpartan.getGender()));
        assertThat(jsonPath.getString("data.phone"), equalTo(newSpartan.getPhone()));


        int spartanId = jsonPath.getInt("data.id");
        SpartanRestUtils.deleteSpartanById(spartanId);

    }
}