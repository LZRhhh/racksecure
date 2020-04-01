package com.lichee.racksecure.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table
public class User {

    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String faceToken;

    @Column(nullable = true)
    private String email;



}
