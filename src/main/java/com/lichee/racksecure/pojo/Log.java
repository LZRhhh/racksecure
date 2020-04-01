package com.lichee.racksecure.pojo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String operation;

    @Column(nullable = false)
    private Timestamp createTime;

    private String params;
}
