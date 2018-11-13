package com.example.demo1.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class WXUser {

    private int id;
    private String username;
    //private String reg_time;
    //private String last_time;
    private int cha_id;
    private String phonenum;
    private String truename;
    private int birth_year;
    private int zmf;
    private String reg_ip;
    private String phone_type;
    private String open_id;

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public int getCha_id() {
        return cha_id;
    }

    public void setCha_id(int cha_id) {
        this.cha_id = cha_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}