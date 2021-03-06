package com.u3100289.homelessAssist;

import android.os.Parcel;
import android.os.Parcelable;

public class Resource implements Parcelable {

    String id = "-1";
    String type;
    String description;
    String placeID;
    String suburb;
    int quanity;
    String userID; //FK
    String businessName;

    public Resource (String id, String type, String description, String placeID, String suburb, int quantity, String businessName, String userID)
    {
        this.id = id;
        this.type = type;
        this.description = description;
        this.placeID = placeID;
        this.suburb = suburb;
        this.quanity = quantity;
        this.businessName = businessName;
        this.userID = userID;
    }

    public Resource (String type, String description, String placeID, String suburb, int quantity, String businessName, String userID)
    {

        this.type = type;
        this.description = description;
        this.placeID = placeID;
        this.suburb = suburb;
        this.quanity = quantity;
        this.businessName = businessName;
        this.userID = userID;
    }



    @Override
    public String toString()
    {
        String ret = "Type: " + getType() + " " + "\nDescription: " + getDescription() + "\n" + "Suburb Location: " + getSuburb() + "\nProvided by: "
                + getBusinessName() + "\nQuantity available: " + getQuantity();
        return ret;
    }




    public String getId() {return this.id;}
    public String getType() {return this.type;}
    public String getPlaceID() {return this.placeID;}
    public String getSuburb() {return this.suburb;}
    public int getQuantity() {return this.quanity;}
    public String getUserID() {return this.userID;}
    public String getDescription() {return this.description;}
    public String getBusinessName() {return this.businessName;}


    protected Resource(Parcel in) {
        id = in.readString();
        type = in.readString();
        description = in.readString();
        placeID = in.readString();
        suburb = in.readString();
        quanity = in.readInt();
        userID = in.readString();
        businessName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(placeID);
        dest.writeString(suburb);
        dest.writeInt(quanity);
        dest.writeString(userID);
        dest.writeString(businessName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Resource> CREATOR = new Parcelable.Creator<Resource>() {
        @Override
        public Resource createFromParcel(Parcel in) {
            return new Resource(in);
        }

        @Override
        public Resource[] newArray(int size) {
            return new Resource[size];
        }
    };
}