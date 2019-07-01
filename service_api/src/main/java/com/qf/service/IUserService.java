package com.qf.service;


/*
    @author: LMFeng
    @date: 2019-06-29 17:07
    @desc:
  */


import com.qf.entity.User;

import java.util.List;
import java.util.Map;

public interface IUserService {

     int register(User user);

     List<User> login(Map map);

}
