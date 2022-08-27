package com.cydeo.tests.day16_methodsource_mocks;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.cydeo.utils.BookItApiTestBase;
import com.cydeo.utils.ExcelUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;

public class BookItApiMethodSourceExcelTest extends BookItApiTestBase {
    @DisplayName("Get/sign/-> data from BookItQa3.xlsx")
    @ParameterizedTest
    @MethodSource("getUserCredentials")
    public void bookItAuthDDTTest(Map<String,String> userInfo){
        System.out.println("userInfo = " + userInfo);
        given().accept(JSON)
                .and().queryParams(userInfo)
                .when().get("/sign")
                .then().spec(responseSpec)// status code/ content type
                .and().body("accessToken", not(blankOrNullString()));
    }

    public static List<Map<String,String>> getUserCredentials(){
        String filePath = "src/test/resources/BookItQa3.xlsx";
        ExcelUtil excelReader = new ExcelUtil(filePath,"QA3");
        List<Map<String ,String >>data = excelReader.getDataList();
        return data;
    }

    @DisplayName("GET /sign -> using a for loop")
    @Test
    public void loginTestUsingLoop() {
        List<Map<String, String>> allCredentials = getUserCredentials();

        for (Map<String, String> userInfo: allCredentials) {
            System.out.println("userInfo = " + userInfo);//can be used as QueryParams in API call.
            //API call/request to /sign and verify status code and access code is there
            try {
                given().accept(ContentType.JSON)
                        .and().queryParams(userInfo)
                        .when().get("/sign")
                        .then().spec(responseSpec)//status code, contentType
                        .and().body("accessToken", not(blankOrNullString()));
            }catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
