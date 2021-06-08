package com.jetshine.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class HttpUtils {

    public static int TIME_OUT = 10000;

    public static final String CHAR_SET = "UTF-8";

    /**
     * 	发送get请求获取数据信息
     * 	采用的默认的UTF-8编码
     * @param url
     * @return
     * @throws Exception
     */
    public static String get(String url){
        String ret = null;
        try {
            ret = HttpUtil.createGet(url).charset(CHAR_SET).timeout(TIME_OUT).execute().body();
        } catch (Exception e) {
            ret = null;
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 	发送get请求获取数据信息
     * 	采用的默认的UTF-8编码
     * @param url
     * @return
     * @throws Exception
     */
    public static String get(String url,Map<String,Object> paramsMap){
        String ret = null;
        try {
            ret = HttpUtil.createGet(url).charset(CHAR_SET).form(paramsMap).timeout(TIME_OUT).execute().body();
        } catch (Exception e) {
            ret = null;
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 	发送get请求获取数据信息
     * 	采用的默认的UTF-8编码
     * @param url
     * @param headers
     * @param paramsMap
     * @return
     * @throws Exception
     */
    public static String get(String url,Map<String,String> headers,Map<String,Object> paramsMap){
        String ret = null;
        try {
            HttpRequest request = HttpUtil.createGet(url).charset(CHAR_SET);
            if(headers!=null) {
                request.addHeaders(headers);
            }
            if(paramsMap!=null) {
                request.form(paramsMap);
            }
            ret = request.timeout(TIME_OUT).execute().body();
        } catch (Exception e) {
            ret = null;
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 	发送post请求
     * 	采用的默认的UTF-8编码
     * @param url
     * @return
     */
    public static String post(String url,Map<String,Object> paramsMap){
        String ret = null;
        try {
            HttpRequest request = HttpUtil.createPost(url).charset(CHAR_SET).timeout(TIME_OUT);
            if(paramsMap!=null) {
                request.form(paramsMap);
            }
            ret = request.execute().body();
        } catch (Exception e) {
            ret = null;
            e.printStackTrace();
        }
        return ret;
    };

    /**
     * 	发送post请求
     * 	采用的默认的UTF-8编码
     * @param url
     * @param headers
     * @return
     */
    public static String post(String url,Map<String,String> headers,Map<String,Object> paramsMap){
        String ret = null;
        try {
            HttpRequest request = HttpUtil.createPost(url).charset(CHAR_SET).timeout(TIME_OUT);
            if(headers!=null) {
                request.addHeaders(headers);
            }
            if(paramsMap!=null) {
                request.form(paramsMap);
            }
            ret = request.execute().body();
        } catch (Exception e) {
            ret = null;
            e.printStackTrace();
        }
        return ret;
    };

    /**
     * 	发送post 请求，请求类型为 application/json
     * 	采用的默认的UTF-8编码
     * @param url
     * @param data
     * @return
     */
    public static String postJson(String url,Object data){
        String ret = null;
        try {
            HttpRequest request = HttpUtil.createPost(url).header("Accept", "application/json").contentType("application/json").charset(CHAR_SET).timeout(TIME_OUT);
            if(data!=null) {
                request.body(JSONUtil.toJsonStr(data));
            }
            ret = request.execute().body();
        }catch (Exception e) {
            ret = null;
            e.printStackTrace();
        }
        return ret;
    }

    public static String post(String url,String accessToken,String contentType,Object data){
        String ret = null;
        try {
            url += "?access_token="+accessToken;
            HttpRequest request = HttpUtil.createPost(url).header("Accept", "application/json").contentType(contentType).charset(CHAR_SET).timeout(TIME_OUT);
            if(data!=null) {
                request.body(JSONUtil.toJsonStr(data));
            }
            ret = request.execute().body();
        }catch (Exception e) {
            ret = null;
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 	发送post 请求，请求类型为 application/json
     * 	采用的默认的UTF-8编码
     * @param url
     * @param headers
     * @param data
     * @return
     */
    public static String postJson(String url,Map<String,String> headers,Object data){
        String ret = null;
        try {
            HttpRequest request = HttpUtil.createPost(url).header("Accept", "application/json").contentType("application/json").charset(CHAR_SET).timeout(TIME_OUT);
            if(headers!=null) {
                request.addHeaders(headers);
            }
            if(data!=null) {
                request.body(JSONUtil.toJsonStr(data));
            }
            ret = request.execute().body();
        }catch (Exception e) {
            ret = null;
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 	将JSon字符串转换为实体对象
     * @param <T>
     * @param data
     * @param clazz
     * @return
     */
    public static <T> T convertToEntity(String data,Class<T> clazz) {
        if(StrUtil.isNotBlank(data)) {
            try {
                return JSONUtil.toBean(data, clazz);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 	将JSon字符串转换为实体对象集合
     * @param <T>
     * @param data
     * @param clazz
     * @return
     */
    public static <T> List<T> convertToEntityForList(String data,Class<T> clazz) {
        if(StrUtil.isNotBlank(data)) {
            List<T> list = new ArrayList<>();
            try {
                JSONArray arrays =  JSONUtil.parseArray(data);
                if(arrays!=null&&arrays.size()>0) {
                    for(int i=0;i<arrays.size();i++) {
                        list.add(JSONUtil.toBean(arrays.getJSONObject(i), clazz));
                    }
                }
            } catch (Exception e) {
                T entity = convertToEntity(data, clazz);
                if(entity!=null) {
                    list.add(entity);
                }
            }
            return list;
        }
        return null;
    }

    /**
     * 	将JSon字符串转换为Map
     * @param data
     * @return
     */
    public static Map<String,Object> convertToMap(String data) {
        if(StrUtil.isNotBlank(data)) {
            Map<String,Object> dataMap = new HashMap<String, Object>();
            try {
                JSONObject obj = JSONUtil.parseObj(data);
                jsonObjToMapData(obj, dataMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dataMap;
        }
        return null;
    }

    /**
     * 	将JSon字符串转换为Map集合
     * @param data
     * @return
     */
    public static List<Map<String,Object>> convertToMapForList(String data) {
        if(StrUtil.isNotBlank(data)) {
            List<Map<String,Object>> list = new ArrayList<>();
            try {
                JSONArray arrays =  JSONUtil.parseArray(data);
                if(arrays!=null&&arrays.size()>0) {
                    Map<String,Object> dataMap = null;
                    for(int i=0;i<arrays.size();i++) {
                        dataMap = new HashMap<String, Object>();
                        jsonObjToMapData(arrays.getJSONObject(i), dataMap);
                        list.add(dataMap);
                    }
                }
            } catch (Exception e) {
                Map<String,Object> dataMap = convertToMap(data);
                if(dataMap!=null) {
                    list.add(dataMap);
                }
            }
            return list;
        }
        return null;
    }

    /**
     * 	将jsonObj数据转换为Map
     * @param map
     * @param dataMap
     */
    private static void jsonObjToMapData(JSONObject map,Map<String,Object> dataMap){
        Set<String> set = map.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            dataMap.put(key, map.get(key));
        }
    }

    /**
     * 将 MultipartFile 文件对象转换为java.io.File对象
     * @param file
     * @return
     * @throws Exception
     */
    public static File convertMulFToFile(MultipartFile file)throws Exception {
        return MultipartFileToFile.multipartFileToFile(file);
    }

    public static void delteTempFile(File file)throws Exception {
        MultipartFileToFile.delteTempFile(file);
    }


    public static File convertBase64StrToFile(String imgstr)throws Exception{
        String dataPrix = ""; //base64格式前头
        String data = "";//实体部分数据
        try {
            if(imgstr==null||"".equals(imgstr)){
                throw new Exception("上传失败，上传图片数据为空");
            }else {
                String [] d = imgstr.split("base64,");//将字符串分成数组
                if(d != null && d.length == 2){
                    dataPrix = d[0];
                    data = d[1];
                }else {
                    throw new Exception("上传失败，数据不合法");
                }
            }
            String suffix = "";//图片后缀，用以识别哪种格式数据
            //data:image/jpeg;base64,base64编码的jpeg图片数据
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){
                suffix = ".jpg";
            }else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){
                //data:image/x-icon;base64,base64编码的icon图片数据
                suffix = ".ico";
            }else if("data:image/gif;".equalsIgnoreCase(dataPrix)){
                //data:image/gif;base64,base64编码的gif图片数据
                suffix = ".gif";
            }else if("data:image/png;".equalsIgnoreCase(dataPrix)){
                //data:image/png;base64,base64编码的png图片数据
                suffix = ".png";
            }else {
                throw new Exception("上传图片格式不合法");
            }
            String uuid = IdUtil.fastSimpleUUID();
            String tempFileName=uuid+suffix;
            Base64 decoder = new Base64();
            //Base64解码
            byte[] bytes = decoder.decode(data);
            for(int i=0;i<bytes.length;++i) {
                if(bytes[i]<0) {
                    //调整异常数据
                    bytes[i]+=256;
                }
            }
            return MultipartFileToFile.byteArraysToFile(bytes, tempFileName);

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("上传图片失败");
        }
    }

    public static byte[] convertBase64StrToBytes(String imgstr)throws Exception{
//		String dataPrix = ""; //base64格式前头
        String data = "";//实体部分数据
        try {
            if(imgstr==null||"".equals(imgstr)){
                throw new Exception("上传失败，上传图片数据为空");
            }else {
                String [] d = imgstr.split("base64,");//将字符串分成数组
                if(d != null && d.length == 2){
                    //dataPrix = d[0];
                    data = d[1];
                }else {
                    throw new Exception("上传失败，数据不合法");
                }
            }
//              String suffix = "";//图片后缀，用以识别哪种格式数据
//              //data:image/jpeg;base64,base64编码的jpeg图片数据
//              if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){
//                  suffix = ".jpg";
//              }else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){
//                  //data:image/x-icon;base64,base64编码的icon图片数据
//                  suffix = ".ico";
//              }else if("data:image/gif;".equalsIgnoreCase(dataPrix)){
//                  //data:image/gif;base64,base64编码的gif图片数据
//                  suffix = ".gif";
//              }else if("data:image/png;".equalsIgnoreCase(dataPrix)){
//                  //data:image/png;base64,base64编码的png图片数据
//                  suffix = ".png";
//              }else {
//              	throw new Exception("上传图片格式不合法");
//              }
            Base64 decoder = new Base64();
            //Base64解码
            byte[] bytes = decoder.decode(data);
            for(int i=0;i<bytes.length;++i) {
                if(bytes[i]<0) {
                    //调整异常数据
                    bytes[i]+=256;
                }
            }
            return bytes;

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("上传图片失败");
        }
    }


    public static byte[] getUrlImage(String url) {
        try {
            URL urlConet = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlConet.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(4 * 1000);
            InputStream inStream = con.getInputStream(); // 通过输入流获取图片数据
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            inStream.close();
            byte[] data = outStream.toByteArray();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getUrlInputStream(String url) {
        try {
            URL urlConet = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlConet.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(60 * 1000);
            return con.getInputStream(); // 通过输入流获取图片数据
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过身份证号码获取出生日期、性别、年龄
     * @param certificateNo
     * @return 返回的出生日期格式：1990-01-01   性别格式：F,0-女，M,1-男
     */
    public static Map<String, String> getBirAgeSex(String certificateNo) {
        String birthday = "";
        String age = "";
        String sexCode = "";
        int year = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = certificateNo.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && certificateNo.length() == 15) {
            birthday = "19" + certificateNo.substring(6, 8) + "-"
                    + certificateNo.substring(8, 10) + "-"
                    + certificateNo.substring(10, 12);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "0" : "1";
            age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
        } else if (flag && certificateNo.length() == 18) {
            birthday = certificateNo.substring(6, 10) + "-"
                    + certificateNo.substring(10, 12) + "-"
                    + certificateNo.substring(12, 14);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "0" : "1";
            age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("birthday", birthday);
        map.put("age", age);
        map.put("sex", sexCode);
        map.put("nation", "无");
        map.put("address", "无");
        return map;
    }


}
