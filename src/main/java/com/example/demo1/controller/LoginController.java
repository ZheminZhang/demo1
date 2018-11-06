package com.example.demo1.controller;

import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


import com.example.demo1.dao.LoginMapper;
import com.google.gson.Gson;

import jdk.nashorn.internal.objects.NativeJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")

public class LoginController {
    @Autowired
    LoginMapper loginMapper;

    @RequestMapping(value={"/user_login"}, method=RequestMethod.GET)
    public Map<String, String> login_hander(String code){
        Map<String, String> res_map = new HashMap<String, String>();//返回结果

        String url = "https://api.weixin.qq.com/sns/jscode2session";//获取session_key的url
        String appid = "wxe9f348c2692ffe5c";//APPID
        String secret = "4461a9c9bedbf8b0cae7f1549575e9d4";
        String param = "appid=" +appid+"&secret="+secret+"&js_code=" + (String)code +"&grant_type=authorization_code";
        String resu =  GetRequest.sendGet(url, param);//获取的数据包

        //转为字典，包含openid和session_key
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map = gson.fromJson(resu, map.getClass());
        String openid = map.get("openid");
        String Session_key = map.get("session_key");
        //TODO：openid作为id查询用户，如果不存在则写入(新用户)

        Integer hashed =  resu.hashCode();


        //下面几行为unionid获取的测试，目前暂不能获取，权限不够
        //Access_token access_token = new Access_token();
        //String access = access_token.get(appid, secret);//需要获取access token,类修改后可以增加判断
        //String user_info = GetRequest.sendGet()

        res_map.put("token", hashed.toString());
        return res_map;
    }
}

class GetRequest {

    static String sendGet(String url, String param) {
        String result = "";
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}

class Access_token{//获取access_token
    // https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140183
    // 1.需要增加判断是否有效函数
    // 2.将类设置成单例模式
    private static boolean live = false;

    public static String get(String appid, String secret){
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String params = "grant_type=client_credential&appid=" + "&secret=" + secret;
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        String reus = GetRequest.sendGet(url, params);
        //从reus到map的映射
        live = true;
        return map.get("access_token").get(0);
    }

    public static boolean living(){
        return live;
    }
}

class User
{//https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140839
    // 绑定公众号后唯一
    Map<String, List<String>> info = new HashMap<String, List<String>>();
    public void get(String acccess_token, String openid){
        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
        String params = "access_token=" + acccess_token + "&openid=" + openid;
        String info_string = GetRequest.sendGet(url, params);
        //从string到map
    }

    public String union_id(){
        return info.get("unionid").get(0);
    }
}
