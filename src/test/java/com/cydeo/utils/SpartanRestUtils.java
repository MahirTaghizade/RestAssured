package com.cydeo.utils;

import com.cydeo.pojo.Spartan;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

public class SpartanRestUtils {
    private static String baseURL = ConfigurationReader.getProperty("spartan.api.url");

    public static void deleteSpartanById(int spartanID) {
        System.out.println("DELETE spartan with id {" + spartanID + "}");
        given().pathParam("id", spartanID)
                .when().delete(baseURL + "/spartans/{id}")
                .then().log().all();
    }
    public static Spartan getNewSpartan(){
        Faker random = new Faker();
        Spartan spartan = new Spartan();
        spartan.setName(random.name().firstName());
        int num = random.number().numberBetween(1,3);
        if (num == 1) {
            spartan.setGender("Female");
        } else {
            spartan.setGender("Male");
        }
        spartan.setPhone(random.number().numberBetween(1000000000L,9999999999L));
        return spartan;
    }
    /**
     * method accepts spartan id and sends get request
     *
     * @param spartanId
     * @return map obj containing response json data
     */
    public static Map<String, Object> getSpartan(int spartanId) {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", spartanId)
                .when().get(baseURL + "/spartans/{id}");

        Map<String, Object> spartanMap = response.as(Map.class);
        return spartanMap;
    }
        //return response.as(Map.class);

}
