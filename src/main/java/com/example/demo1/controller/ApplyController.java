package com.example.demo1.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.example.demo1.dao.ApplyMapper;
import com.example.demo1.dao.LoginMapper;
import com.example.demo1.entity.WXUser;
import com.example.demo1.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/apply")


public class ApplyController {

    @Autowired
    ApplyMapper applyMapper;

    @RequestMapping(value={"/check_code"}, method=RequestMethod.POST)
    public Map<String, String> return_val_code(@RequestBody Message msg) throws CliException {
        String code = getRandomString();
        //System.out.println(respon);
        SendSmsResponse respon = MoblieMessageUtil.sendIdentifyingCode(msg.getPhone(), code);
//        while(respon.getCode() != "OK"){//请求直到得到‘OK’
//            //System.out.println(respon.getCode());
//            respon = MoblieMessageUtil.sendIdentifyingCode(msg.getPhone(), code);
//        }
        Map<String, String> res_map = new HashMap<String, String>();
        res_map.put("data", code);
        return res_map;
    }

    @RequestMapping(value={"/verified_code"}, method = RequestMethod.POST)
    public Map<String, String> store_phone(@RequestBody Message msg) {
        //TODO: 用idx查询用户，然后添加手机，如果用户手机已经存在？？？
        Map<String, String> res_map = new HashMap<String, String>();
        String openid = msg.getIdx();
        WXUser find_user = applyMapper.selectUserByOpenId(openid);
        if(find_user == null){
            //没找到用户 说明客户端逻辑有错，或者是对端口的直接申请(攻击)
            System.out.println("without this user");
            return res_map;
        }
        WXUser new_user = new WXUser();
        new_user.setUsername(msg.getName());
        new_user.setOpen_id(msg.getIdx());
        new_user.setPhonenum(msg.getPhone());
        applyMapper.updateWXUser(new_user);//updata的条件判断，什么时候updata
        //TODO: 不同类型的用户，有的用户第一次填写相关信息
        res_map.put("state", "OK");
        return res_map;
    }

    private  String getRandomString(){
        StringBuilder buffer = new StringBuilder();
        Random random = new Random();
        int index;   //获取随机chars下标
        for(int i=0;i<4;i++){
            index = random.nextInt(9);  //获取随机chars下标
            buffer.append(index);
        }
        return buffer.toString();
    }
}


