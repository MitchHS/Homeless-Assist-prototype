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

    public User ( String email, String password, int userType, String name, String lastName, String streetNumber,
                 String streetName, String suburb, String postcode)
    {
       // this.id = id;
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

    public User ( String email, String password, int userType, String name, String lastName, String suburb, String postcode)
    {
      //  this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name= name;
        this.lastName = lastName;
        this.suburb = suburb;
        this.postcode = postcode;
    }

    public String getEmail() {return this.email.toLowerCase();}
    public String getPassword() {return  this.password;}
    public int getUserType() {return this.userType;}
    public String getName() {return this.name.toLowerCase();}
    public String getLastName() {return this.lastName.toLowerCase();}
    public String getStreetName() {
        if(this.streetName == null){return null;} else {return this.streetName.toLowerCase();}
    }
    public String getStreetNumber() {
        if(this.streetNumber == null){return null;} else {return this.streetNumber;}
        }
    public String getSuburb() {return this.suburb.toLowerCase();}
    public String getPostcode() {return this.postcode;}



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
