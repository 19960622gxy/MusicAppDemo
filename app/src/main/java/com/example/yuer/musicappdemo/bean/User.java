package com.example.yuer.musicappdemo.bean;

/**
 * Created by Yuer on 2017/4/14.
 */

public class User {
    private int id;
    private String regeistName;
    private String regeistPassword;
    private String regeistCheckPassword;
    private String phone;
    private String sex;
    private String nickName;
    private String content;

    public User(int id, String regeistName, String regeistPassword, String regeistCheckPassword, String phone, String sex, String nickName, String content) {
        this.id = id;
        this.regeistName = regeistName;
        this.regeistPassword = regeistPassword;
        this.regeistCheckPassword = regeistCheckPassword;
        this.phone = phone;
        this.sex = sex;
        this.nickName = nickName;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegeistName() {
        return regeistName;
    }

    public void setRegeistName(String regeistName) {
        this.regeistName = regeistName;
    }

    public String getRegeistPassword() {
        return regeistPassword;
    }

    public void setRegeistPassword(String regeistPassword) {
        this.regeistPassword = regeistPassword;
    }

    public String getRegeistCheckPassword() {
        return regeistCheckPassword;
    }

    public void setRegeistCheckPassword(String regeistCheckPassword) {
        this.regeistCheckPassword = regeistCheckPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
