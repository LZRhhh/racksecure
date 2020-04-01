package com.lichee.racksecure.util;


import com.alibaba.fastjson.JSON;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class FaceUtil {



    public static void main(String[] args) throws Exception {

        FaceUtil fu = new FaceUtil();
//        fu.createFaceSet();
        String filepath = "D:\\Pictures\\微信图片_20200217234653.jpg";
        String faceToken = fu.detect(filepath);
        System.out.println(faceToken);
//        fu.addToken(faceToken);
//        fu.searchFace(filepath);
//        fu.removeFace(faceToken);
    }
    private final static String APIKey = "VNCZtFdxdjm_HxsXNrjQpwClTzqLNQdU";
    private final static String APISecret = "nIvoeZySRcHxJm9Q4YYZhNSogg9jNg9r";
    private final static String faceSetToken = "563d2545c76ca1f05feee5c9151ffe17";
    private final static String outerId = "zhuoran";
    private final static String faceToken = "58e72cf214888d32891d5be0b86e10c8";
//    a8dd2b98dca33553ba3d77b79dbfb1a1

    public void createFaceSet(){
        String url = "https://api-us.faceplusplus.com/facepp/v3/faceset/create";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", APIKey);
        map.put("api_secret", APISecret);
        map.put("outer_id", outerId);

        try{
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            System.out.println(str);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String searchFace(String filepath) throws Exception{
        File file = new File(filepath);
        byte[] buff = getBytesFromFile(file);
        String url = "https://api-us.faceplusplus.com/facepp/v3/search";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", APIKey);
        map.put("api_secret", APISecret);
        map.put("outer_id", outerId);
        byteMap.put("image_file", buff);

        try{
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            System.out.println(str);
            SearchResponse res = JSON.parseObject(str, SearchResponse.class);

            double confidence = res.getResults().get(0).getConfidence();
            if(confidence < 90)
                return null;
            else
                return res.getResults().get(0).getFace_token();
        }catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    public void addFace(String filepath) throws Exception {
        String faceToken = detect(filepath);
        if(faceToken!=null)
            addToken(faceToken);
    }

    public static void addToken(String faceToken) throws Exception{

        String url = "https://api-us.faceplusplus.com/facepp/v3/faceset/addface";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", APIKey);
        map.put("api_secret", APISecret);
        map.put("outer_id", outerId);
        map.put("face_tokens", faceToken);

        try{
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            System.out.println("Add:");
            System.out.println(str);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void removeToken(String faceToken) throws Exception{

        String url = "https://api-us.faceplusplus.com/facepp/v3/faceset/removeface";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", APIKey);
        map.put("api_secret", APISecret);
        map.put("outer_id", outerId);
        map.put("face_tokens", faceToken);

        try{
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            System.out.println("Remove:");
            System.out.println(str);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFaceToken(String filepath) throws Exception {
        String str = detect(filepath);
        if(str != null) return str.split("\"")[11];
        else return null;
    }

    public static String detect(String filepath) throws Exception{
        File file = new File(filepath);
        byte[] buff = getBytesFromFile(file);
        String url = "https://api-us.faceplusplus.com/facepp/v3/detect";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", APIKey);
        map.put("api_secret", APISecret);
//        map.put("return_landmark", "1");
//        map.put("return_attributes", "gender,age,smiling,headpose,facequality,blur,eyestatus,emotion,ethnicity,beauty,mouthstatus,eyegaze,skinstatus");
        byteMap.put("image_file", buff);
        try{
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            System.out.println("Detect:");
            DetectResponse res = JSON.parseObject(str, DetectResponse.class);
            return res.getFaces().get(0).getFace_token();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();
    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }
    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    private static String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }

    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

}
