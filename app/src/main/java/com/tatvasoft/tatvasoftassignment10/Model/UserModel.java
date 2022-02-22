package com.tatvasoft.tatvasoftassignment10.Model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private int userId;
    private String userName;
    private String contactNo;
    private String birthDate;
    private String bloodGroup;
    private String country;
    private String gender;
    private String languages;

    public UserModel() {
    }

    public UserModel(int userId,
                     String userName,
                     String contactNo,
                     String birthDate,
                     String bloodGroup,
                     String country,
                     String gender,
                     String languages) {
        this.userId = userId;
        this.userName = userName;
        this.contactNo = contactNo;
        this.birthDate = birthDate;
        this.bloodGroup = bloodGroup;
        this.country = country;
        this.gender = gender;
        this.languages = languages;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
}
