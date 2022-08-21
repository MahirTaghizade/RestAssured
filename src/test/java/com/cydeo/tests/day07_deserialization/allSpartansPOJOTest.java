package com.cydeo.tests.day07_deserialization;

import com.cydeo.pojo.Spartan;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import javax.print.DocFlavor;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.*;

@Data
public class allSpartansPOJOTest extends SpartanTestBase {
    /**
     * Given accept type is json
     * when I send GET request to /spartans
     * Then status code is 200
     * And content type is json
     * And I can convert json to list of spartan pojos
     */
    @Test
    public void allSpartansToPojoTest() {
        Response response = given().accept(JSON)
                .when().get("/spartans");

        assertEquals(SC_OK, response.statusCode());
        assertEquals(JSON.toString(), response.contentType());

        // convert response to JsonPath
        JsonPath jsonPath = response.jsonPath();

        //using jsonPath list of spartans deserialization
        List<Spartan> allSpartans = jsonPath.getList("", Spartan.class);

        System.out.println("count: " + allSpartans.size());

        System.out.println("first: " + allSpartans.get(0));

        //using streams: filter and store female spartans into a different list
        //INTERWIEW TASK

        List<Spartan> female = allSpartans.stream().filter(spartan -> spartan.getGender().equals("Female"))
                .collect(Collectors.toList());

        System.out.println("Female spartans: "+female);

        List<Spartan> maleSpartans = allSpartans.stream().filter(spartan -> spartan.getGender().equals("male"))
                .collect(Collectors.toList());
        Long count = allSpartans.stream().filter(spartan -> spartan.getGender().equals("male")).count();
        System.out.println(count);


    }

}
