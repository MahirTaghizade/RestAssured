package com.cydeo.tests.day16_methodsource_mocks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class MethodSourceTest {

    public static List<String> getCountries() {
        List<String> countries = Arrays.asList("Canada", "USA", "Turkey", "Azerbaijan");
        return countries;
    }

    @ParameterizedTest
    @MethodSource("getCountries")
    public void countriesTest(String countryName) {

        System.out.println("countryName = " + countryName+" : "+countryName.length());



    }
}
