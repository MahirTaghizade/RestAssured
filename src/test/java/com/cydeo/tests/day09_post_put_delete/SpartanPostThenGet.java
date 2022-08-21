package com.cydeo.tests.day09_post_put_delete;

import com.cydeo.pojo.Spartan;
import com.cydeo.utils.SpartanRestUtils;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class SpartanPostThenGet extends SpartanTestBase {

    Spartan newSpartan = SpartanRestUtils.getNewSpartan();

    @DisplayName("POST /spartan ->get with id and compare ")
    @Test
    public void postNewSpartanThenGetTest() {
        System.out.println("newSpartan = " + newSpartan);

        Response response = given().accept(JSON)
                .and().contentType(JSON)
                .and().body(newSpartan)
                .when().post("/spartans");

        response.prettyPrint();
        assertThat(response.statusCode(), is(SC_CREATED));
        assertThat(response.contentType(), is(JSON.toString()));

        int newSpartanId = response.jsonPath().getInt("data.id");
        System.out.println("newSpartanId = " + newSpartanId);

        // send get request with newSpartanId and compare
        Response response1 = given().accept(JSON)
                .and().pathParam("id", newSpartanId)
                .when().get("/spartans/{id}");

        System.out.println("Get request body :");
        response1.prettyPrint();

        //convert get request JsonBody to another spartan obj deserialization
        Spartan spartanFromGet = response1.as(Spartan.class);// actual data

        // compare Posted neSpartan with GET request spartan
        assertThat(spartanFromGet.getName(),is(newSpartan.getName()));
        assertThat(spartanFromGet.getGender(),is(newSpartan.getGender()));
        assertThat(spartanFromGet.getPhone(),is(newSpartan.getPhone()));

        // delete using id
        SpartanRestUtils.deleteSpartanById(newSpartanId);
    }
}