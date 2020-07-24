package com.example.ning.assignmnet3;

import java.util.Date;

public class Credentials {

    private String userName;
    private String pwdHash;
    private Date signupDate;
    private Users userId;

    public Credentials(String userName, String pwdHash, Date signUpDate, Users userId){
        this.userId = userId;
        this.userName = userName;
        this.pwdHash = pwdHash;
        this.signupDate = signUpDate;

    }
    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getPwdHash(){
        return this.pwdHash;
    }

    public void setPwdHash(String pwdHash){
        this.pwdHash = pwdHash;
    }

    public Date getSignUpDate(){
        return signupDate;
    }

    public void setSignUpDate(Date signUpDate){
        this.signupDate = signUpDate;
    }

    public Users getUserId(){
        return userId;
    }

    public void setUserId(Users userId)
    {
        this.userId = userId;
    }


}
