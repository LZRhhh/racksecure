package com.lichee.racksecure.dao;


import com.lichee.racksecure.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDAO extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
//    List<Rack> findByIdContaining(int id);
    User findById(int id);
    User findByUsername(String username);
    User findByFaceToken(String face_token);
    User getByUsernameAndPassword(String username, String password);

}
