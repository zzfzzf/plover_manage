package com.zzy.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2017/3/17.
 */

public class BaseController {
    static Integer SUCCESS = 200;
    static Integer UNKNOW_EXCEPTION = 400;
    static Integer NOT_LOGIN = 302;
    public JSONObject SUCCESS(Object data){
        JSONObject json = new JSONObject();
        json.put("status",SUCCESS);
        if(data != null){
            json.put("data",data);
        }
        return json;
    }
    public JSONObject SUCCESS(){
       return SUCCESS(null);
    }

    public JSONObject FAILD(Integer status,String message){
        JSONObject json = new JSONObject();
        if(status!=null){
            json.put("status",UNKNOW_EXCEPTION);
        }
        json.put("message",message);
        return json;
    }
    public JSONObject FAILD(String message){
        return FAILD(null,message);
    }

}
