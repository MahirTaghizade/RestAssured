package com.cydeo.tests.day05_path_jsonpath;

import com.cydeo.utils.HRApiTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.print.DocFlavor;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.*;

public class HREmployeesJsonPathTest extends HRApiTestBase {
    /**
     Given accept type is Json
     And query param limit is 200
     when I send GET request to /employees
     Then I can use jsonPath to query and read values from json body
     */
    @DisplayName("GET /employees?limit=200 => jsonPath filters")
    @Test
    public void jsonPathEmployeesTest() {
        Response response = given().accept(JSON)
                .and().queryParam("limit", 200)
                .and().get("/employees");

        assertEquals(SC_OK,response.statusCode());
        assertEquals(JSON.toString(), response.contentType());

        JsonPath jsonPath = response.jsonPath();
        assertEquals("Steven",jsonPath.getString("items[0].first_name"));
        System.out.println(jsonPath.getString("items[0].first_name"));


        // working with JsonPath lists


        List<String >allNames = jsonPath.getList("items.first_name");
        System.out.println(allNames);
        System.out.println();

        List<String >allEmails = jsonPath.getList("items.email");
        System.out.println(allEmails);
        System.out.println();

        // check given email exist in list
        assertTrue(allEmails.contains("TGATES"));

        // get all employee first names for department 90
        List<String >namesAt90 = jsonPath.getList("items.findAll{it.department_id==90}.first_name");
        System.out.println(namesAt90);
        System.out.println("namesAt90.size() = " + namesAt90.size());
        System.out.println();


        //get all employee first names who work as IT_PROG
        List<String >itProgs = jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.first_name");
        System.out.println(itProgs);
        System.out.println();

        //get all employee first names whose salary is more than or equal 5000
        List<String >salaryMore5000 = jsonPath.getList("items.findAll{it.salary>=5000}.first_name");
        System.out.println(salaryMore5000);
        System.out.println();

        //get emp first name who has max salary
        String firstNameMax = jsonPath.getString("items.max{it.salary}.first_name");
        System.out.println("Max salary: " +firstNameMax);
        System.out.println();

        //get emp first name who has min salary
        String firstNameMin = jsonPath.getString("items.min{it.salary}.first_name");
        System.out.println("Min salary: " +firstNameMin);
        System.out.println();

        // it will print all related info matched
        String firstNameAll = jsonPath.getString("items.min{it.salary}");
        System.out.println(firstNameAll);


    }
}
