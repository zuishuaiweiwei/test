package com.wei.service;

import com.wei.dao.UserDao;
import com.wei.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 为为
 * @create 11/23
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;



    public User getUserById(int id){
        return userDao.getUserById(id);
    }
}
