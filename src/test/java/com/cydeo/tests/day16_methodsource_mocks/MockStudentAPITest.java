package com.cydeo.tests.day16_methodsource_mocks;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.cydeo.utils.BookItApiTestBase;
import com.cydeo.utils.ExcelUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
public class MockStudentAPITest {

    @BeforeAll
    public static void setUp(){
        baseURI = "https://0361961f-9015-4c73-bbdc-0e33907107d8.mock.pstmn.io";
    }
    @DisplayName("GET/student/{id}")
    @Test
    public void testStudent(){
        given().accept(JSON)
                .when().get("/students/1")
                .then().assertThat().statusCode(200)
                .and().contentType(JSON)
                .log().all();

    }








}
