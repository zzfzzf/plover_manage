package com.zzy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/16.
 */

@RestController
@RequestMapping(value = "/plover")
public class PloverController extends BaseController{
    @Autowired
    private UserMapper mapper;
    @RequestMapping(value="/live",method = RequestMethod.POST)
    public JSONObject addLiveUser(@RequestBody JSONObject json){
        json.put("id",UUID.randomUUID().toString().replace("-",""));
        json.put("status",0); // status为0 就是未验证
        int resultValue = mapper.addLiveUser(json);
        return resultValue==1?SUCCESS():FAILD("添加信息失败");
    }
}
