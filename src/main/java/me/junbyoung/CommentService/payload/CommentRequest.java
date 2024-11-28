package me.junbyoung.CommentService.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    @NotBlank(message = "댓글의 내용을 입력해주세요.")
    @Size(max = 100, message = "댓글의 내용은 100자 이내로 입력해주세요.")
    private String content;
}
