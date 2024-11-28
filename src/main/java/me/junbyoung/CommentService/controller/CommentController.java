package me.junbyoung.CommentService.controller;

import jakarta.validation.Valid;
import me.junbyoung.CommentService.model.Comment;
import me.junbyoung.CommentService.model.User;
import me.junbyoung.CommentService.payload.CommentRequest;
import me.junbyoung.CommentService.payload.CommentResponse;
import me.junbyoung.CommentService.service.CommentService;
import me.junbyoung.CommentService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments") // 공통 경로 지정
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = new ArrayList<>();
        for (Comment comment : commentService.getAllComments(postId)) {
            User user = userService.getUserInfoByUserId(comment.getUserId());
            comments.add(new CommentResponse(comment, user.getName()));
        }
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestHeader("X-User-Id") Long userId,
                                              @PathVariable Long postId,
                                              @Valid @RequestBody CommentRequest commentRequest) {
        Comment comment = commentService.createComment(userId, postId, commentRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/posts/{postId}/comments/{commentId}")
                .buildAndExpand(postId, comment.getId())
                .toUri();
        return ResponseEntity.created(location).body(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@RequestHeader("X-User-Id") Long userId,
                                                 @PathVariable Long postId,
                                                 @PathVariable Long commentId,
                                                 @Valid @RequestBody CommentRequest commentRequest) throws AccessDeniedException {
        Comment comment = commentService.updateComment(userId, postId, commentId, commentRequest);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@RequestHeader("X-User-Id") Long userId,
                                              @PathVariable Long postId,
                                              @PathVariable Long commentId) throws AccessDeniedException {
        commentService.deleteComment(userId, postId, commentId);
        return ResponseEntity.noContent().build();
    }
}
