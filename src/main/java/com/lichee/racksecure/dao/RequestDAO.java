package com.lichee.racksecure.dao;


import com.lichee.racksecure.pojo.Rack;
import com.lichee.racksecure.pojo.Request;
import com.lichee.racksecure.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface RequestDAO extends JpaRepository<Request, Integer> {
    List<Request> findByUsername(String username);
    List<Request> findByRackId(int rackId);
    List<Request> findByEndAfter(Timestamp start);
    List<Request> findByStartBefore(Timestamp end);
    List<Request> findByStartBetweenAndApprovedTrue(Timestamp start, Timestamp end);
}
