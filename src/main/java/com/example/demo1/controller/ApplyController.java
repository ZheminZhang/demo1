package com.example.demo1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.example.demo1.entity.wd_product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.controller.MoblieMessageUtil;

@RestController
@RequestMapping("/apply")


public class ApplyController {
    @RequestMapping(value={"/check_code"}, method=RequestMethod.POST)
    public String return_val_code(@RequestBody Message msg) throws CliException {
        System.out.println(msg.getToken());
        String code = getRandomString();
        MoblieMessageUtil.sendIdentifyingCode(msg.getPhone(), code);
        return "success";
    }

    public static String getRandomString(){
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        int index;   //获取随机chars下标
        for(int i=0;i<4;i++){
            index = random.nextInt(9);  //获取随机chars下标
            buffer.append(index);
        }
        return buffer.toString();
    }
}

class Message {
    //private String code;
    private String token;
    private  String phone_num;
//    private String content;

    public Message() {}

//    public String getCode() {
//        return this.code;
//    }

    String getToken() {
        return this.token;
    }

    String getPhone() {
        return this.phone_num;
    }

//    public void setFrom(String value) {
//        this.from = value;
//    }
//
//    public void setTo(String value) {
//        this.to = value;
//    }
//
//    public void setContent(String value) {
//        this.content = value;
//    }
}
