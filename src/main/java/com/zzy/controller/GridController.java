package com.zzy.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzy.mapper.GridMapper;
import com.zzy.util.ImageUtil;
import com.zzy.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/16.
 */

@RestController
public class GridController extends BaseController{
    public static String SIPMLE_IMG_PATH = "D:/plover/img/simple/";
    public static String TOP_IMG_PATH = "D:/plover/img/top/";
    public static String TYPE_PNG = ".png";
    public static String IMAGE_TYPE = "png|jpeg|jpg";
    public static String DEFAULT_SIMPLE_IMAGE = "images/pi1.jpg";
    public static String DEFAULT_TOP_IMAGE = "images/dnf.jpg";

    @Autowired
    private GridMapper gridMapper;

    public String upload(MultipartFile upFile,int width,int height){

        InputStream in=null;
        // 默認普通路径
        String imageName = UUID.randomUUID().toString();
        String filePath=SIPMLE_IMG_PATH+imageName+TYPE_PNG;
        if("topImage".equals(upFile.getName())){
            filePath=TOP_IMG_PATH+imageName+TYPE_PNG;
        }

        try{
            in = upFile.getInputStream();
            ImageUtil.resizeImage(in,filePath,width,height);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

     return filePath;
    }
    @RequestMapping(value="/upload/simple",method = RequestMethod.POST)
    public JSONObject receiveSimpleImage(MultipartFile simpleImage,HttpSession session){
        if(simpleImage.getSize()>= 3*1024*1024){
            return FAILD("图片尺寸不能大于3M");
        }
        String[] suffix =simpleImage.getOriginalFilename().split("\\.");
        String imageType = suffix[1].toLowerCase();
        if(!imageType.matches(IMAGE_TYPE)){
            return FAILD("请上传图片类型的文件");
        }

        String filePath = (String)session.getAttribute("simpleImagePath");
        if(filePath!=null || "".equals(filePath)){
            File file = new File(filePath.replace("http://127.0.0.1","D:"));
            file.delete();
        }
        filePath = upload(simpleImage,270,170).replace("D:","http://127.0.0.1");
        session.setAttribute("simpleImagePath",filePath);
        return SUCCESS(filePath);
    }

    @RequestMapping(value="/upload/top",method = RequestMethod.POST)
    public JSONObject receiveTopImage(MultipartFile topImage,HttpSession session){
        if(topImage.getSize()>= 3*1024*1024){
            return FAILD("图片尺寸不能大于3M");
        }
        String suffix =topImage.getOriginalFilename().split("\\.")[1];
        String imageType = suffix.toLowerCase();
        if(!imageType.matches(IMAGE_TYPE)){
            return FAILD("请上传图片类型的文件");
        }


        String filePath = (String)session.getAttribute("topImagePath");
        if(filePath!=null || "".equals(filePath)){
            File file = new File(filePath.replace("http://127.0.0.1","D:"));
            file.delete();
        }
        filePath = upload(topImage,1140,521).replace("D:","http://127.0.0.1");
        session.setAttribute("topImagePath",filePath);
        return SUCCESS(filePath);
    }


    @RequestMapping(value="/get/grid",method = RequestMethod.POST)
    public JSONObject getGrid(@RequestBody JSONObject json,HttpSession session){
        Map map = gridMapper.getGrid(json);

        // 查看时存入文件路径,修改时判断读取
        session.setAttribute("simpleImagePath",map.get("image"));
        session.setAttribute("topImagePath",map.get("topImage"));
        return SUCCESS(gridMapper.getGrid(json));
    }
    @RequestMapping(value="/add/grid",method = RequestMethod.POST)
    public JSONObject addGrid(@RequestBody JSONObject json,HttpSession session){
        json.put("userId",session.getAttribute("userId"));
        json.put("id",UUID.randomUUID().toString());
        json.put("status",0); // status为0 就是未验证
        json.put("image",session.getAttribute("simpleImagePath"));
        json.put("topImage",session.getAttribute("topImagePath"));
        int resultValue = gridMapper.addGrid(json);
        if(resultValue==1){
            session.setAttribute("simpleImagePath",null);
            session.setAttribute("topImagePath",null);
        }
        return resultValue==1?SUCCESS():FAILD("添加信息失败");
    }
    @RequestMapping(value="/update/grid",method = RequestMethod.POST)
    public JSONObject updateGrid(@RequestBody JSONObject json,HttpSession session){
        json.put("image",session.getAttribute("simpleImagePath"));
        json.put("topImage",session.getAttribute("topImagePath"));
        Map gridMap = gridMapper.getGrid(json);
        deleteImage(gridMap);
        return SUCCESS(gridMapper.updateGrid(json));
    }
    @RequestMapping(value="/delete/grid",method = RequestMethod.POST)
    public JSONObject deleteGrid(@RequestBody JSONObject json,HttpSession session){
        json.put("userId",session.getAttribute("userId"));

        Map gridMap = gridMapper.getGrid(json);
        deleteImage(gridMap);

        gridMapper.deleteGrid(json);
        return SUCCESS();
    }
    private void deleteImage(Map gridMap){
        File file;
        String imagePath = (String)gridMap.get("image");
        String topImagePath = (String)gridMap.get("topImage");
        if(imagePath!=null){
            file= new File( imagePath.replace("http://127.0.0.1","D:"));
            file.delete();
        }
        if(topImagePath!=null){
            file= new File( imagePath.replace("http://127.0.0.1","D:"));
            file.delete();
        }
    }

    @RequestMapping(value="/list/grid",method = RequestMethod.POST)
    public JSONObject livtGrid(@RequestBody JSONObject json,HttpSession session){
        json.put("userId",session.getAttribute("userId"));
        List liveGridList = gridMapper.listGrid(json);
        defaultImage(liveGridList);

        return SUCCESS(liveGridList);
    }
    @RequestMapping(value="/count/grid",method = RequestMethod.POST)
    public JSONObject countGridByUser(@RequestBody JSONObject json,HttpSession session){
        if(session.getAttribute("userId")==null || "".equals(session.getAttribute("userId"))){
            return FAILD(NOT_LOGIN,"用户没登录");
        }
        json.put("userId",session.getAttribute("userId"));
        int gridNum = gridMapper.countGridByUser(json);
        // 暂时只允许发一个链接
        if(gridNum > 0){
            return FAILD(UNKNOW_EXCEPTION,"格子数量大于最大限制");
        }
        return SUCCESS();
    }

    @RequestMapping(value="/get/topImage",method = RequestMethod.POST)
    public JSONObject getTopImage(@RequestBody JSONObject json,HttpSession session){
        List topImageList = gridMapper.getTopImage();
        defaultImage(topImageList);
        return SUCCESS(topImageList);
    }

    @RequestMapping(value="/all/grid",method = RequestMethod.POST)
    public JSONObject listGrid(@RequestBody JSONObject json, HttpSession session){
        int totalNum = gridMapper.countGrid();
        Integer pageSize = json.getInteger("pageSize");
        if(pageSize==null){
            // 默认查询40个
            pageSize = new Integer(40);
        }
        Page page = new Page(json.getInteger("currentPage"),pageSize,totalNum);
        json.put("index",page.getIndex());
        List allGrid = gridMapper.allGridByType(json);
        defaultImage(allGrid);
        // 封装分页信息
        JSONObject jt = SUCCESS(allGrid);
        jt.put("currentPage",page.getCurrentPage());
        jt.put("totalNum",page.getTotalNum());

        jt.put("totalPage",page.getTotalPage());
        return jt;
    }


    private void defaultImage(List gridList){
        Iterator<Map> it = gridList.iterator();
        while(it.hasNext()){
            Map gridMap = it.next();
            String image = (String)gridMap.get("image");
            String topImage = (String)gridMap.get("topImage");
            if(image==null || "".equals(image)){
                gridMap.put("image",DEFAULT_SIMPLE_IMAGE);
            }
            if(topImage==null || "".equals(topImage)){
                gridMap.put("topImage",DEFAULT_TOP_IMAGE);
            }
        }
    }
}
