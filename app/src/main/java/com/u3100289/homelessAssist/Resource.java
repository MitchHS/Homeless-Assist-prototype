package com.u3100289.homelessAssist;

public class Resource {

    String id = "-1";
    String type;
    String description;
    String placeID;
    String address;
    int quanity;
    String userID; //FK
    String businessName;

    public Resource (String id, String type, String description, String placeID, String address, int quantity, String businessName, String userID)
    {
        this.id = id;
        this.type = type;
        this.description = description;
        this.placeID = placeID;
        this.address = address;
        this.quanity = quantity;
        this.businessName = businessName;
        this.userID = userID;
    }

    public Resource (String type, String description, String placeID, String address, int quantity, String businessName, String userID)
    {

        this.type = type;
        this.description = description;
        this.placeID = placeID;
        this.address = address;
        this.quanity = quantity;
        this.businessName = businessName;
        this.userID = userID;
    }




    public String getId() {return this.id;}
    public String getType() {return this.type;}
    public String getPlaceID() {return this.placeID;}
    public String getAddress() {return this.address;}
    public int getQuantity() {return this.quanity;}
    public String getUserID() {return this.userID;}
    public String getDescription() {return this.description;}
    public String getBusinessName() {return this.businessName;}

}
