package com.example.demo1.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Message {
    private String token;
    private String idx;
    private  String phone_num;
    private String name;

    public Message() {}

    public String getToken() {
        return this.token;
    }

    public String getPhone() {
        return this.phone_num;
    }

    public void setPhone_num(String value){
        this.phone_num = value;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}