package me.junbyoung.CommentService.service;

import me.junbyoung.CommentService.client.UserClient;
import me.junbyoung.CommentService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserClient userClient;

    public User getUserInfoByUserId(Long userId) {
        return userClient.getUserInfoByUserId(userId);
    }
}
