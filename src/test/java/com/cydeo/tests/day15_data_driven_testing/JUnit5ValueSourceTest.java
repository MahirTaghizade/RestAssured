package com.cydeo.tests.day15_data_driven_testing;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.cydeo.utils.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class JUnit5ValueSourceTest {
    @ParameterizedTest
    @ValueSource(ints = {5,23,90,33,64,10986,456})
    public void numbersTest(int num){
        System.out.println("num = " + num);
        assertThat(num,is(greaterThan(0)));
    }

    @ParameterizedTest
    @ValueSource(strings ={"Vugar","Mahir","Shina","Eda"} )
    public void testNames(String name){
        System.out.println("Hi " + name);
        assertThat(name,not(blankOrNullString()));
    }

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @ValueSource(ints = {22102, 22031, 22034, 11209, 15090, 15237,12345,20879,21224,11223})
    public void zipCodeTest(int zipCode){
        given().accept(JSON)
                .and().pathParam("postal-code",zipCode)
                .when().get("/us/{postal-code}")
                .then().assertThat().statusCode(200).log().all();


    }
}
