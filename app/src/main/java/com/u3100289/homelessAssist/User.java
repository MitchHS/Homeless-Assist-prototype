package com.u3100289.homelessAssist;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable {
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
    String contactNo = "";
    String businessName = "";

    public User ( String email, String password, int userType, String name, String lastName, String contactNo, String streetNumber,
                  String streetName, String suburb, String postcode, String businessName)
    {
        // this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.suburb = suburb;
        this.postcode = postcode;
        this.businessName = businessName;
    }

    public User ( String id, String email, String password, int userType, String name, String lastName,  String contactNo, String streetNumber,
                  String streetName, String suburb, String postcode, String businessName)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.suburb = suburb;
        this.postcode = postcode;
        this.businessName = businessName;
    }

    public User ( String email, String password, int userType, String name, String lastName, String contactNo, String suburb, String postcode)
    {
        //  this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name= name;
        this.lastName = lastName;
        this.contactNo = contactNo;
        this.suburb = suburb;
        this.postcode = postcode;

    }

    public User ( String id, String email, String password, int userType, String name, String lastName,  String contactNo, String suburb, String postcode)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name= name;
        this.lastName = lastName;
        this.contactNo = contactNo;
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
    public String getContactNo() {return this.contactNo;}
    public String getBusinessName() {
        if (this.businessName == null) {
            return "private";
        } else {
            return this.businessName.toLowerCase();
        }
    }
    public String getId() {return this.id;}



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

    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        password = in.readString();
        userType = in.readInt();
        name = in.readString();
        lastName = in.readString();
        streetNumber = in.readString();
        streetName = in.readString();
        suburb = in.readString();
        postcode = in.readString();
        contactNo = in.readString();
        businessName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeInt(userType);
        dest.writeString(name);
        dest.writeString(lastName);
        dest.writeString(streetNumber);
        dest.writeString(streetName);
        dest.writeString(suburb);
        dest.writeString(postcode);
        dest.writeString(contactNo);
        dest.writeString(businessName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}