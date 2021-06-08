package com.jetshine.controller;


import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.jetshine.util.FileUploadUtils;
import com.jetshine.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/recive1")
    public String recive(MultipartFile file){

        try{
            uploadFileTest();//外网上传
//            uploadFileTest1();//内网上传
        }catch (Exception e){
            e.printStackTrace();
        }
        if(file==null){
            System.out.println("kong");
        }
        String path="";
        try {
            path= FileUploadUtils.upload(Thread.currentThread().getContextClassLoader().getResource("").getPath(),file);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @PostMapping("/recive")
    @ResponseBody
    public String recive1(@RequestParam(value="file") MultipartFile file){
        System.out.println("come in ");
//        System.out.println("come in type:"+type);
        Map map=new HashMap();
//        File file=new File("C:\\Users\\cypro\\Desktop\\test\\123.jpg");
        List list=new ArrayList<>();
        InputStream is =null;
        if(file==null){
            System.out.println("kong1");
        }
        try{

            FileUploadUtils.upload("D:\\test1\\",file);
//
//            is=new FileInputStream(file);
//            OutputStream os = new FileOutputStream(new File("D:\\test1\\test.jpg"));
//            int bytesRead = 0;
//            byte[] buffer = new byte[10240];
//            while ((bytesRead = is.read(buffer, 0, 10240)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            os.close();
//            is.close();
        }catch (Exception e) {

            map.put("data", "error");
            map.put("msg", "接收失败");

            return JSONObject.toJSONString(map);
        }
        map.put("data","success");
        map.put("msg","接收成功");
        return JSONObject.toJSONString(map);
    }


    //外网上传
    public static void uploadFileTest(){
        Map map1 = new HashMap();
        String time=System.currentTimeMillis()+"";
        String sec="3997e12f-bd2d-11eb-ae82-50af732921daLP_ZSPT"+time+"1234";
        map1.put("appid","3997e12f-bd2d-11eb-ae82-50af732921da");
        map1.put("time",time);
        map1.put("type","LP_ZSPT");
        map1.put("salt","1234");
        map1.put("sec", MD5Util.encode("null"+sec));

        Map map2 = new HashMap();
        map2.put("params",JSONObject.toJSONString(map1));

//        map2.put("files",new File("D:\\jetshine\\lpsjtb\\123.jpg"));
        map2.put("files",new File("C:\\Users\\cypro\\Desktop\\test\\123.jpg"));
        try{
            String result=HttpUtil.post("http://www.cqfygzfw.com/tytbpt/uploadFileController/uploadFiles",map2);
            System.out.println("111"+result);
        }catch (Exception e){

            e.printStackTrace();

        }

    }


    //内网上传
    public static void uploadFileTest1(){
        Map map1 = new HashMap();
        String time=System.currentTimeMillis()+"";
        String sec="3997e12f-bd2d-11eb-ae82-50af732921daLP_ZSPT"+time+"1234";
        map1.put("appid","3997e12f-bd2d-11eb-ae82-50af732921da");
        map1.put("time",time);
        map1.put("type","LP_ZSPT");
        map1.put("salt","1234");
        map1.put("sec", MD5Util.encode("null"+sec));

        Map map2 = new HashMap();
        map2.put("params",JSONObject.toJSONString(map1));

//        map2.put("files",new File("D:\\jetshine\\lpsjtb\\123.jpg"));
        map2.put("files",new File("C:\\Users\\cypro\\Desktop\\test\\123.jpg"));
        try{
            String result=HttpUtil.post("http://147.0.0.29:9555/tytbpt/uploadFileController/uploadFiles",map2);
            System.out.println("111"+result);
        }catch (Exception e){

            e.printStackTrace();

        }

    }
}
