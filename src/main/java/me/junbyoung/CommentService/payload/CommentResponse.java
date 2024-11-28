package me.junbyoung.CommentService.payload;

import lombok.Getter;
import me.junbyoung.CommentService.model.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private final Long id;
    private final Long postId;
    private final String userName;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponse(Comment comment , String userName){
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.userName = userName;
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
