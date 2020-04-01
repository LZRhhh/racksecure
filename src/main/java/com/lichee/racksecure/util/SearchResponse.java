package com.lichee.racksecure.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    private String image_id;
    private String faces;
    private String time_used;
    private String threshold;
    private String request_id;
    private List<Result> results;

    public static void main(String[] args) {
        String str = "{\"image_id\": \"+Y+xzw4ZbDMlhVSzI08EWQ==\", \"faces\": [{\"face_rectangle\": {\"width\": 982, \"top\": 1088, \"left\": 724, \"height\": 982}, \"face_token\": \"ca028c652bbbe31410b6c18b1bd89dba\"}], \"time_used\": 1284, \"thresholds\": {\"1e-3\": 62.327, \"1e-5\": 73.975, \"1e-4\": 69.101}, \"request_id\": \"1582581332,ba076b2c-598c-4891-a127-c3cf14a1978d\", \"results\": [{\"confidence\": 92.188, \"user_id\": \"\", \"face_token\": \"58e72cf214888d32891d5be0b86e10c8\"}]}";
        SearchResponse res = JSON.parseObject(str, SearchResponse.class);

        System.out.println(res.results.get(0).getConfidence());
        System.out.println(res.results.get(0).getFace_token());
    }
}
