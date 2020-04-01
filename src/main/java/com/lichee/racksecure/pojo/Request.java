package com.lichee.racksecure.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Table
@Entity
public class Request{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp end;

    private boolean approved;

    private String username;

    private int rackId;

    @Column
    private String comment;
}