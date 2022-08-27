package com.cydeo.tests.day13_access_tokens;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.cydeo.utils.BookItApiTestBase;
import com.cydeo.utils.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class BookItApiTest extends BookItApiTestBase {
    /**
     * Given accept type is json
     * And header Authorization is access_token of teacher
     * When I send GET request to /api/campuses
     * Then status code is 200
     * And content type is json
     * And campus location info is presented in payload
     */
    @Test
    public void getAllCampusesTest() {
        String accessToken = getAccessToken(ConfigurationReader.getProperty("teacher_email"),
                ConfigurationReader.getProperty("teacher_password"));
        System.out.println(accessToken);
        Response response = given().accept(JSON)
                .and().header("Authorization", accessToken)
                .when().get("/api/campuses");
        response.prettyPrint();

        assertThat(response.statusCode(), is(200));
        assertThat(response.contentType(), is(JSON.toString()));
        assertThat(response.path("location"), hasItems("VA", "IL"));


    }

    @Test
    public void ilCampusTest() {
        /**
         * Given accept type is Json
         * And header Authorization is access token of team leader
         * And path param "campus_location" is "IL"
         * When I send GET request to "/api/campuses/{campus_location}
         * Then status code is 200
         * And content type is json
         * And location is "IL"
         * And rooms names has items "google" , "apple", "microsoft", "tesla"
         */

        String accessToken = getAccessToken(ConfigurationReader.getProperty("team_leader_email"),
                ConfigurationReader.getProperty("team_leader_password"));

        System.out.println("accessToken = " + accessToken);

        given().accept(ContentType.JSON)
                .and().header("Authorization", accessToken)
                .and().pathParam("campus_location", "IL")
                .when().get("/api/campuses/{campus_location}")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().body("location", equalTo("IL"))
                .and().body("clusters[0].rooms.name", hasItems("google", "apple", "microsoft", "tesla"));
    }

    @Test
    public void roomInfoTest() {
        /**
         * Given accept type is Json
         * And header Authorization is access token of team leader
         * And path param "room_name" is "mit"
         * When I send GET request to "/api/rooms/{room_name}
         * Then status code is 200
         * And content type is json
         body matches data:
         {
         "id": 111,
         "name": "mit",
         "description": "mens et manus",
         "capacity": 6,
         "withTV": true,
         "withWhiteBoard": true
         }
         */
        String accessToken = getAccessToken(ConfigurationReader.getProperty("team_member_email"),
                ConfigurationReader.getProperty("team_member_password"));
        System.out.println("accessToken = " + accessToken);

        Map<String, ?>roomInfoMap=given().accept(ContentType.JSON)
                .and().pathParam("room_name", "mit")
                .and().header("Authorization", accessToken)
                .when().get("/api/rooms/{room_name}")
                .then().assertThat().statusCode(200)
                .and().contentType(JSON.toString())
                .and().extract().body().as(Map.class);

        System.out.println(roomInfoMap);
        assertThat(roomInfoMap.get("id"),is(111));
        assertThat(roomInfoMap.get("name"),is("mit"));
        assertThat(roomInfoMap.get("description"),is("mens et manus"));
        assertThat(roomInfoMap.get("capacity"),is(6));
        assertThat(roomInfoMap.get("withTV"),is(true));
        assertThat(roomInfoMap.get("withWhiteBoard"),is(true));


    }

}