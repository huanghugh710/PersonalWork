package com.heng.service.impl;

import com.heng.domain.User;
import com.heng.domain.UserExample;
import com.heng.mapper.UserMapper;
import com.heng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lfy
 * @Description:
 * @date 2020/9/1618:18
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserByLoginName(User user) {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andLoginNameEqualTo(user.getLoginName());

        List<User> users = userMapper.selectByExample(userExample);

        return users;
    }

    @Override
    public void saveUser(User user) {

        userMapper.insertSelective(user);
    }
}
