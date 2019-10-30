package cn.shizhe.community.service;

import cn.shizhe.community.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    void insertUser(User user);
    
    User findUserBytoken(String token);
}
