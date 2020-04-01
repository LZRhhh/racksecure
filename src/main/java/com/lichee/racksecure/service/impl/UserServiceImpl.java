package com.lichee.racksecure.service.impl;

import com.lichee.racksecure.dao.RackDAO;
import com.lichee.racksecure.dao.UserDAO;
import com.lichee.racksecure.pojo.User;
import com.lichee.racksecure.service.UserService;
import com.lichee.racksecure.util.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    RackDAO rackDAO;

    @Override
    public boolean insert(User user) {

        String username = user.getUsername();
        if (exist(username)){
            return false;
        }
        String password = user.getPassword();
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        userDAO.save(user);
        return true;
    }

    @Override
    public void update(User user) {
        userDAO.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User get(String username, String password) {
        try{
            User user = userDAO.getByUsernameAndPassword(username, password);
            return user;
        } catch (Exception e) {
            return null;
        }

    }



    /**
     * 判断用户是否存在
     */
    private boolean exist(String username){
        User user = userDAO.findByUsername(username);
        return (user != null);
    }

}
