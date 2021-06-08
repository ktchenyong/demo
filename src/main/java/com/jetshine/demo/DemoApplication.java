package com.jetshine.demo;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.jetshine.util.MD5Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages= {"com.jetshine"})
public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
//        uploadFileTest();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
               return builder.sources(DemoApplication.class);
    }


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

        map2.put("files",new File("D:\\jetshine\\lpsjtb\\123.jpg"));
//        map2.put("files",file);
        try{
            String result=HttpUtil.post("http://www.cqfygzfw.com/tytbpt/uploadFileController/uploadFiles",map2);
            System.out.println("111"+result);
        }catch (Exception e){

            e.printStackTrace();

        }

    }

}
