package com.lichee.racksecure.service;


import com.lichee.racksecure.pojo.Rack;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RackService {

    Rack getById(int id);
    List<Rack> findAll();
}
