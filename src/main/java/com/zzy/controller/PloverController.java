package com.zzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/16.
 */

@RestController
@RequestMapping(value = "/plover")
public class PloverController extends BaseController{
    public static String SIPMLE_IMG_PATH = "D:/plover/img/simple/";
    public static String TOP_IMG_PATH = "D:/plover/img/top/";
    public static String TYPE_PNG = ".png";
    @Autowired
    private UserMapper mapper;
    @RequestMapping(value="/live",method = RequestMethod.POST)
    public JSONObject addLiveUser(@RequestBody JSONObject json){
        json.put("id",UUID.randomUUID().toString().replace("-",""));
        json.put("status",0); // status为0 就是未验证
        int resultValue = mapper.addLiveUser(json);
        return resultValue==1?SUCCESS():FAILD("添加信息失败");
    }

    public JSONObject upload(MultipartFile upFile){
        InputStream in=null;
        FileOutputStream fos=null;
        byte[] buffer=new byte[512];
        int numberRead;
        // 默認普通路径
        String filePath=SIPMLE_IMG_PATH+UUID.randomUUID()+TYPE_PNG;
        if("topImage".equals(upFile.getName())){
            filePath=TOP_IMG_PATH+UUID.randomUUID()+TYPE_PNG;
        }
        try{
            in = upFile.getInputStream();
            fos = new FileOutputStream(filePath);

            while ((numberRead=in.read(buffer))!=-1) {  //numberRead的目的在于防止最后一次读取的字节小于buffer长度，
                fos.write(buffer, 0, numberRead);       //否则会自动被填充0
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fos.flush();
                in.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     return SUCCESS(filePath.replace("D:","127.0.0.1"));
    }
    @RequestMapping(value="/upload/simple",method = RequestMethod.POST)
    public JSONObject receiveSimpleImage(MultipartFile simpleImage){

        return upload(simpleImage);
    }

    @RequestMapping(value="/upload/top",method = RequestMethod.POST)
    public JSONObject receiveTopImage(MultipartFile topImage){
        return upload(topImage);
    }
}
