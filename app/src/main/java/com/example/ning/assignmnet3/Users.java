package com.example.ning.assignmnet3;

import java.util.Date;

public class Users {
    private Integer userId;
    private String userName;
    private String userSurname;
    private String email;
    private Date dob;
    private int height;
    private int weight;
    private String gender;
    private String address;
    private String postcode;
    private int loact;
    private int spmile;

    public Users(Integer userId, String userName, String userSurname,
                 String email, Date dob, int height, int weight, String gender,
                 String address, String postcode, int loact, int spmile) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.email = email;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.loact = loact;
        this.spmile = spmile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLoact() {
        return loact;
    }

    public void setLoact(int loact) {
        this.loact = loact;
    }

    public int getSpmile() {
        return spmile;
    }

    public void setSpmile(int spmile) {
        this.spmile = spmile;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
