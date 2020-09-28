package com.heng.service;

import com.heng.domain.User;
import java.util.List;

/**
 * @author lfy
 * @Description:
 * @date 2020/9/1618:16
 */

public interface UserService {

    /**
     * //根据用户名查询用户
     * @param user
     * @return
     */
    List<User> getUserByLoginName(User user);

    /**
     * 注册用户
     */
    void saveUser(User user);
}
