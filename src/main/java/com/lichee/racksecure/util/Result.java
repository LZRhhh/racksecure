package com.lichee.racksecure.util;

import lombok.Data;

@Data
public class Result {
    private double confidence;
    private String user_id;
    private String face_token;
}
