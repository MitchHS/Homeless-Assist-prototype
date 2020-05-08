package com.u3100289.homelessAssist;

import java.util.Date;

public class Event {
    public String id = "id";
    public String title =  "title";
    public String date;
    public String description = "description";
    public int streetNo;
    public String streetName = "streetName";
    public String suburb = "suburb";
    public int postcode;

    public Event(String id, String title, String date, String description, int streetNo, String streetName, String suburb, int postcode )
    {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.suburb = suburb;
        this.postcode = postcode;
    }

    public Event(String title, String date, String description, int streetNo, String streetName, String suburb, int postcode )
    {

        this.title = title;
        this.date = date;
        this.description = description;
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.suburb = suburb;
        this.postcode = postcode;
    }

    public String getTitle(){return this.title;}
    public String getDate(){return this.date;}
    public String getDescription() { return this.description; }
    public int getStreetNo() {return this.streetNo; }
    public String getId() {return this.id;}
    public String getStreetName() {return this.streetName;}
    public String getSuburb() {return this.suburb.toLowerCase();}
    public int getPostcode() {return this.postcode;}

    @Override
    public String toString() {
        return "Title: " + getTitle() + "\n" + "Date: " + getDate() + "\n" +  "Description: " + getDescription() + "\n" + "Address: " + getStreetNo() + " " +
                getStreetName() + " "  + getSuburb() + " " + getPostcode();
    }


}
