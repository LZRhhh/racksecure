package com.lichee.racksecure.dao;


import com.lichee.racksecure.pojo.Rack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RackDAO extends JpaRepository<Rack, Integer> {
//    List<User> findByNameContaining(String name);
    Rack findById(int id);
    List<Rack> findByLockedFalse();
    List<Rack> findAll();
}
