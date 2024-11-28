package me.junbyoung.CommentService.client.fallback;

import me.junbyoung.CommentService.client.UserClient;
import me.junbyoung.CommentService.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {
    @Override
    public User getUserInfoByUserId(Long userId) {
        return new User();
    }
}
