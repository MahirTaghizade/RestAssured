package com.cydeo.tests.day10_db_vs_api_put_delete;

import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.DBUtils;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.Configuration;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpartanAPIAndDBValidationTest extends SpartanTestBase {
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
     * When I send database query
     * SELECT name, gender, phone
     * FROM spartans
     * WHERE spartan_id = newIdFrom Post request;
     * Then name, gender , phone values must match with POST request details
     */

    @DisplayName("POST /api/spartan -> then validate in DB")
    @Test
    public void postNewSpartanThenValidateInDBTest() {
        Map<String, Object> postRequestMap = new HashMap<>();
        postRequestMap.put("gender", "Male");
        postRequestMap.put("name", "PostVSDatabase");
        postRequestMap.put("phone", 1234567425);

        Response response = (Response) given().accept(JSON)
                .and().contentType(JSON)
                .and().body(postRequestMap)
                .when().post("/spartans");

        assertThat(response.statusCode(), is(SC_CREATED));
        assertThat(response.contentType(), is(JSON.toString()));
        assertThat(response.jsonPath().getString("success"), is("A Spartan is Born!"));

        JsonPath jsonPath = response.jsonPath();
        int newSpartanId = jsonPath.getInt("data.id");

        String query = "SELECT name, gender, phone FROM spartans WHERE spartan_id=" + newSpartanId;

        String dbUrl = ConfigurationReader.getProperty("spartan.db.url");
        String dbUser = ConfigurationReader.getProperty("spartan.db.userName");
        String dbPwd = ConfigurationReader.getProperty("spartan.db.password");

        DBUtils.createConnection(dbUrl, dbUser, dbPwd);
        Map<String, Object> dbMap = DBUtils.getRowMap(query);

        assertThat(dbMap.get("NAME"),is(postRequestMap.get("name")));
        assertThat(dbMap.get("PHONE"),is(postRequestMap.get("phone").toString()));
        assertThat(dbMap.get("GENDER").toString(),is(postRequestMap.get("gender")));



        //disconnect from database
        DBUtils.destroy();
    }
}