package com.zzy;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/3/17.
 */
public class Test
{
    public static void  main(String args[]){
        JSONObject json = new JSONObject();
        json.put("status",200);
        json.clear();
        System.out.print(json);
}
}
