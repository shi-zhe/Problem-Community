package cn.shizhe.community.service.Impl;

import cn.shizhe.community.dao.UserDao;
import cn.shizhe.community.entity.User;
import cn.shizhe.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public User findUserBytoken(String token) {
        return userDao.findUserBytoken(token);
    }
}
