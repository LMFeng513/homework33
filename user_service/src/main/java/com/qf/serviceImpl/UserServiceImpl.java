package com.qf.serviceImpl;

  /*
    @author: LMFeng
    @date: 2019-07-01 10:52
    @desc:
  */

/**/
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
   @Autowired
   private UserMapper userMapper;

    @Override
    public int register(User user) {

        return userMapper.insert(user);
    }

    @Override
    public List<User> login(Map map) {


        return userMapper.selectByMap(map);
    }
}
