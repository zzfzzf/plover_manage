package com.zzy.controller;

import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

/**
 * Created by Administrator on 2017/3/17.
 */
public class BaseController {
    static Integer SUCCESS = 200;
    static Integer UNKNOW_EXCEPTION = 400;
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

    public JSONObject FAILD(String status,String message){
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
