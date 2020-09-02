package com.rab.medicare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class API_Response
{
    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("FirstName")
    private String FirstName;
    @JsonProperty("DOB")
    private String DOB;
    @JsonProperty("notification")
    private static String notification;
    @JsonProperty("LastName")
    private String LastName;
    @JsonProperty("Gender")
    private String Gender;
    @JsonProperty("Contact")
    private String Contact;
    @JsonProperty("Address")
    private String Address;
    @JsonProperty("EmailVerified")
    private String EmailVerified;

    public String getEmailVerified() {
        return EmailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        EmailVerified = emailVerified;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
}
