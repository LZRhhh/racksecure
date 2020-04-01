package com.lichee.racksecure.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;

@Data
public class DetectResponse {
    private String request_id;
    private int time_used;
    private List<Face> faces;
    private String image_id;
    private int face_num;

    public static void main(String[] args) {
        String str = "{\"request_id\":\"1582589257,dff3ede4-4db4-483b-92cc-ac9d519b6479\",\"time_used\":984,\"faces\":[{\"face_token\":\"807e802099ccfd6e1267fe8063614ff0\",\"face_rectangle\":{\"top\":1089,\"left\":724,\"width\":982,\"height\":982}}],\"image_id\":\"+Y+xzw4ZbDMlhVSzI08EWQ==\",\"face_num\":1}";
        DetectResponse res = JSON.parseObject(str, DetectResponse.class);
        System.out.println(res.getFaces().get(0).getFace_token());
    }
}
