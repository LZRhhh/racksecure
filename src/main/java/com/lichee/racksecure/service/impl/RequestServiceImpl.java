package com.lichee.racksecure.service.impl;

import com.lichee.racksecure.dao.RequestDAO;
import com.lichee.racksecure.pojo.Request;
import com.lichee.racksecure.service.RackService;
import com.lichee.racksecure.service.RequestService;
import com.lichee.racksecure.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Slf4j
public class RequestServiceImpl implements RequestService {

    @Autowired RackService rackService;

    @Autowired UserService userService;

    @Autowired RequestDAO requestDAO;


    @Override
    public boolean add(Request newReq) {
        if(isOverlapped(newReq))
            return false;
        requestDAO.save(newReq);
        return true;
    }


    @Override
    public List<Request> findByUsername(String username) {
        return requestDAO.findByUsername(username);
    }

    @Override
    public List<Request> findByRackId(int rackId) {
        return requestDAO.findByRackId(rackId);
    }


    private boolean isOverlapped(Request newReq) {
        List<Request> reqs = requestDAO.findByStartBetweenAndApprovedTrue(newReq.getStart(), newReq.getEnd());
        for(Request req : reqs){
            if(req.getEnd().after(newReq.getEnd())
            || req.getEnd().before(newReq.getEnd()))
                continue;
            else return true;
        }
        return false;
    }

}
