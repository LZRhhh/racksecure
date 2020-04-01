package com.lichee.racksecure.service;

import com.lichee.racksecure.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    /**
     * 添加新用户
     *
     * username 唯一， 默认 USER 权限
     */
    boolean insert(User user);

    void update(User user);
    /**
     * 查询用户信息
     * @param username 账号
     * @return UserEntity
     */
    User getByUsername(String username);

    List<User> getAllUsers();

    User get(String username, String password);


}
