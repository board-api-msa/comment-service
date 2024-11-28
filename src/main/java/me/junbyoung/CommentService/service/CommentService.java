package me.junbyoung.CommentService.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import me.junbyoung.CommentService.model.Comment;
import me.junbyoung.CommentService.payload.CommentRequest;
import me.junbyoung.CommentService.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class CommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment createComment(Long userId, Long postId, CommentRequest commentRequest) {
        Comment comment = new Comment(userId,postId,commentRequest.getContent());
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long userId,Long postId, Long commentId, CommentRequest commentRequest) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
        if (!comment.getUserId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to delete this comment.");
        }
        comment.updateComment(commentRequest.getContent());
        return comment;
    }

    public void deleteComment(Long userId,Long postId, Long commentId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
        if (!comment.getUserId().equals(userId)) {
            throw new AccessDeniedException("You do not have permission to delete this comment.");
        }
        commentRepository.delete(comment);
    }
}
