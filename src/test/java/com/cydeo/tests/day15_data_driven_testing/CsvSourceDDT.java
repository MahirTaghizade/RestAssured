package com.cydeo.tests.day15_data_driven_testing;

import com.cydeo.utils.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CsvSourceDDT {
    @ParameterizedTest
    @CsvSource({"7, 5, 12",
            "3, 99, 102",
            "52, 8, 60",
            "38, 41, 79"})
    public void basicAddTest(int num1, int num2, int expSum) {

        assertThat(num1+num2, is (expSum));
    }

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }
    @ParameterizedTest
    @CsvSource({"New York City,NY",
            "Washington,VA",
            "Boston,MA",
            "San Diego,CA"})
    public void cityAndStateTest(String city,String state){
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("state",state);
        paramsMap.put("city",city);
        given().accept(JSON)
                .and().pathParams(paramsMap)
                .when().get("/us/{state}/{city}")
                .then().assertThat().statusCode(SC_OK)
                .and().contentType(JSON)
                .and().body("'place name'",equalTo(city))
                .log().all();

    }
}
