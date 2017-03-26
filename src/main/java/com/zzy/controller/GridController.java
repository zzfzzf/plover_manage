package com.zzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzy.mapper.GridMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/16.
 */

@RestController
@RequestMapping(value = "/live")
public class LiveController extends BaseController{
    public static String SIPMLE_IMG_PATH = "D:/plover/img/simple/";
    public static String TOP_IMG_PATH = "D:/plover/img/top/";
    public static String TYPE_PNG = ".png";
    @Autowired
    private GridMapper liveMapper;

    public String upload(MultipartFile upFile){

        InputStream in=null;
        FileOutputStream fos=null;
        byte[] buffer=new byte[512];
        int numberRead;
        // 默認普通路径
        JSONObject json = new JSONObject();
        String imageName = UUID.randomUUID().toString();
        String filePath=SIPMLE_IMG_PATH+imageName+TYPE_PNG;
        if("topImage".equals(upFile.getName())){
            filePath=TOP_IMG_PATH+imageName+TYPE_PNG;
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

     return filePath;
    }
    @RequestMapping(value="/upload/simple",method = RequestMethod.POST)
    public JSONObject receiveSimpleImage(MultipartFile simpleImage,HttpSession session){
        String filePath = (String)session.getAttribute("simpleImagePath");
        if(filePath!=null || "".equals(filePath)){
            File file = new File(filePath.replace("http://127.0.0.1","D:"));
            file.delete();
        }
        filePath = upload(simpleImage).replace("D:","http://127.0.0.1");
        session.setAttribute("simpleImagePath",filePath);
        return SUCCESS(filePath);
    }

    @RequestMapping(value="/upload/top",method = RequestMethod.POST)
    public JSONObject receiveTopImage(MultipartFile topImage,HttpSession session){
        String filePath = (String)session.getAttribute("topImagePath");
        if(filePath!=null || "".equals(filePath)){
            File file = new File(filePath.replace("http://127.0.0.1","D:"));
            file.delete();
        }
        filePath = upload(topImage).replace("D:","http://127.0.0.1");
        session.setAttribute("topImagePath",filePath);
        return SUCCESS(filePath);
    }


    @RequestMapping(value="/get/grid",method = RequestMethod.POST)
    public JSONObject getGrid(@RequestBody JSONObject json,HttpSession session){
        Map map = liveMapper.getGrid(json);

        // 查看时存入文件路径,修改时判断读取
        session.setAttribute("simpleImagePath",map.get("image"));
        session.setAttribute("topImagePath",map.get("topImage"));
        return SUCCESS(liveMapper.getGrid(json));
    }
    @RequestMapping(value="/add/grid",method = RequestMethod.POST)
    public JSONObject addGrid(@RequestBody JSONObject json,HttpSession session){
        json.put("userId",session.getAttribute("userId"));
        json.put("id",UUID.randomUUID().toString());
        json.put("status",0); // status为0 就是未验证
        json.put("image",session.getAttribute("simpleImagePath"));
        json.put("topImage",session.getAttribute("topImagePath"));
        int resultValue = liveMapper.addGrid(json);
        return resultValue==1?SUCCESS():FAILD("添加信息失败");
    }
    @RequestMapping(value="/update/grid",method = RequestMethod.POST)
    public JSONObject updateGrid(@RequestBody JSONObject json,HttpSession session){
        json.put("image",session.getAttribute("simpleImagePath"));
        json.put("topImage",session.getAttribute("topImagePath"));
        return SUCCESS(liveMapper.updateGrid(json));
    }
    @RequestMapping(value="/delete/grid",method = RequestMethod.POST)
    public JSONObject deleteGrid(@RequestBody JSONObject json,HttpSession session){
        json.put("userId",session.getAttribute("userId"));
        liveMapper.deleteGrid(json);
        return SUCCESS();
    }


    @RequestMapping(value="/list/grid",method = RequestMethod.POST)
    public JSONObject livtGrid(@RequestBody JSONObject json,HttpSession session){
        json.put("userId",session.getAttribute("userId"));
        List liveGridList = liveMapper.listGrid(json);
        return SUCCESS(liveGridList);
    }
}
