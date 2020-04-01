package com.lichee.racksecure.service;


import com.lichee.racksecure.pojo.Rack;
import com.lichee.racksecure.pojo.Request;
import com.lichee.racksecure.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RequestService {
    boolean add(Request request);
    List<Request> findByUsername(String username);
    List<Request> findByRackId(int rackId);

}
