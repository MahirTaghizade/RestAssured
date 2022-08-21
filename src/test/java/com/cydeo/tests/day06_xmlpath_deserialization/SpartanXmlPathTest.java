package com.cydeo.tests.day06_xmlpath_deserialization;
import com.cydeo.utils.SpartanTestBase;
import groovy.xml.XmlParser;
import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.cydeo.utils.SpartanTestBase;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.XML;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanXmlPathTest extends SpartanTestBase {
    /**
     Given accept type is application/xml
     When i send GET request to /api/spartans
     Then status code is 200
     And content type is XML
     And spartan at index 0 is matching:
     id > 107
     name>Ezio Auditore
     gender >Male
     phone >7224120202

     */
    @DisplayName("GET /spartans -> xml path")
    @Test
    public void spartanXMLPathTest() {
        Response response = given().accept(XML)
                .when().get("/spartans");
        response.prettyPrint();

        assertEquals(SC_OK,response.statusCode());
        assertEquals(XML.toString(),response.contentType());

        // convert XMP response body XMP response obj
        XmlPath xmlPath = response.xmlPath();
        System.out.println(xmlPath.getInt("List.item[0].id"));
        System.out.println(xmlPath.getString("List.item[0].name"));
        System.out.println(xmlPath.getString("List.item[0].gender"));
        System.out.println(xmlPath.getString("List.item[0].phone"));

        List<String > names = xmlPath.getList("List.item.name");
        System.out.println(names);
        System.out.println(names.size());

    }
}
