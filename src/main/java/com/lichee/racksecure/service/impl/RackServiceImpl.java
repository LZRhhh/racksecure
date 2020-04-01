package com.lichee.racksecure.service.impl;


import com.lichee.racksecure.dao.RackDAO;
import com.lichee.racksecure.pojo.Rack;
import com.lichee.racksecure.service.RackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Slf4j
public class RackServiceImpl implements RackService {
    @Autowired
    RackDAO rackDAO;



    @Override
    public Rack getById(int id) {
        return rackDAO.findById(id);
    }

    @Override
    public List<Rack> findAll() {
        return rackDAO.findAll();
    }

}
