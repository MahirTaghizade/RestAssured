package com.cydeo.pojo;

import lombok.Data;

/**
 Given accept type is application/json
 And Path param id = 10
 When i send GET request to /api/spartans
 Then status code is 200
 And content type is json
 And spartan data matching:
 id > 10
 name>Lorenza
 gender >Female
 phone >3312820936
 */

@Data // this will add getter and setter for each variable
public class Spartan {
    private int id;
    private String name;
    private String gender;
    private Long phone;

}

