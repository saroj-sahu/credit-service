package com.cr.request;

import java.io.File;

public class UserProfileRequest {

    private String id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String branch;
    private String rank;
    private Float yearsOfService;
    private String mos;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getBranch() {
        return branch;
    }

    public String getRank() {
        return rank;
    }

    public Float getYearsOfService() {
        return yearsOfService;
    }

    public String getMos() {
        return mos;
    }
}
