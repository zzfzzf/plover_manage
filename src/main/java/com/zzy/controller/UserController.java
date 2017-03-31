package com.zzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzy.mapper.UserMapper;
import com.zzy.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static javafx.scene.input.KeyCode.R;

/**
 * Created by Administrator on 2017/3/25.
 */
@RestController
public class UserController extends BaseController{

    @Autowired
    UserMapper userMapper;
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public JSONObject login(@RequestBody JSONObject json, HttpSession session){
        // 如果已经登录直接返回
        if(session.getAttribute("username")!=null && !"".equals(session.getAttribute("username"))){
            json.put("username",session.getAttribute("username"));
            return SUCCESS(json);
        }
        Map map=userMapper.getUser(json);

        if(map==null || "".equals(map)){
            return FAILD(SUCCESS,"登录失败，请检查用户名或密码");
        }
        session.setAttribute("userId",map.get("id"));
        session.setAttribute("username",map.get("username"));
        session.setAttribute("password",map.get("password"));
        return SUCCESS(map);
    }

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public JSONObject register(@RequestBody JSONObject json,HttpSession session){
        Map userMap = userMapper.getByUsername(json);
        if(userMap!=null&&!"".equals(userMap)){
            return FAILD(UNKNOW_EXCEPTION,"用户已存在!");
        }

        json.put("id", UUID.randomUUID().toString());
        int resultValue = userMapper.register(json);
        return resultValue==1?SUCCESS():FAILD("注册失败");
    }

    @RequestMapping(value="/reset/password",method = RequestMethod.POST)
    public JSONObject resetPassword(@RequestBody JSONObject json, HttpSession session){
        System.out.println(json);
        String key = json.getString("key");
        if(!key.equals(session.getAttribute("resetKey"))){
            return FAILD("当前链接已经失效,请重试");
        }
        json.put("email",json.get("email"));
        json.put("password","123456");
        session.setAttribute("resetKey",null);
        session.setAttribute("userId",null);
        userMapper.updateUserByEmail(json);
        return SUCCESS();
    }
    /**
     * 用户登出
     */
    @RequestMapping(value="/logout",method = RequestMethod.POST)
    public JSONObject logout(@RequestBody JSONObject json,HttpSession session){
        session.setAttribute("userId",null);
        session.setAttribute("username",null);
        session.setAttribute("password",null);
        System.out.println(session.getAttribute("username"));
        return SUCCESS();
    }
    @RequestMapping(value="/forget/password",method = RequestMethod.POST)
    public JSONObject forgetPassword(@RequestBody JSONObject json,HttpSession session){
        String subject = "忘记密码";


        String theParam = json.getString("email")+System.currentTimeMillis();
        try { MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(theParam.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String param=new BigInteger(1, md.digest()).toString(16);
            StringBuilder content = new StringBuilder();
            content.append("请点击以下链接重置密码</br><a href=\"http://127.0.0.1/static/reset.html?email=");
            content.append(json.get("email"));
            content.append("&key=");
            content.append(param);
            content.append("\">重置密码</a>");
            session.setAttribute("resetKey",param);


            MailUtil.sendEmail(json.getString("email"),subject,content.toString(),new Date());

        } catch (Exception e) {}

        return SUCCESS();
    }



}
