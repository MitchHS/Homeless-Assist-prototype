package com.u3100289.homelessAssist;

public class User {
    String id = "-1";
    String email = "email@example.com";
    String password  = "password";
    int userType = 0;
    String name = "name";
    String lastName = "lastName";
    String streetNumber = "0";
    String streetName = "streetName";
    String suburb = "suburb";
    String postcode = "0000";

    public User (String id, String email, String password, int userType, String name, String lastName, String streetNumber,
                 String streetName, String suburb, String postcode)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.lastName = lastName;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.suburb = suburb;
        this.postcode = postcode;
    }


//    User
//            ID
//    Email
//            Password
//    userType = 1 or 2, individual or business
//            Name
//    lastName
//    Street Number
//    Streed Name
//    Suburb
//            Postcode
}
