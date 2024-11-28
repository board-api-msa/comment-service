package me.junbyoung.CommentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.junbyoung.CommentService.model.Comment;
import me.junbyoung.CommentService.payload.CommentRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommentServiceApplicationTests {

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;

	private Comment newComment;

	//Id가 1인 post가 저장되어있다는 가정하에 테스트진행.
	@Test
	@Order(1)
	void addComment() throws Exception {
		CommentRequest CommentRequest = new CommentRequest("content test");
		String jsonContent = objectMapper.writeValueAsString(CommentRequest);

		newComment = objectMapper.readValue(
				mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/1/comments")
								.header("X-User-Id", "1")
								.contentType(MediaType.APPLICATION_JSON)
								.content(jsonContent))
						.andExpect(status().isCreated())
						.andReturn()
						.getResponse()
						.getContentAsString(),
				Comment.class
		);
	}

	@Test
	@Order(2)
	void getComment() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/1/comments"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].id").value(newComment.getId()))
				.andExpect(jsonPath("$[0].content").value(newComment.getContent()));
	}

	@Test
	@Order(3)
	void updateComment() throws Exception {
		CommentRequest CommentRequest = new CommentRequest("content update");
		newComment.updateComment(CommentRequest.getContent());
		String jsonContent = objectMapper.writeValueAsString(CommentRequest);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/1/comments/{commentId}",newComment.getId())
						.header("X-User-Id", "1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonContent))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value(newComment.getContent()));
	}

	@Test
	@Order(4) //댓글 소유자가 아닌 사용자가 댓글을 수정하려고할때의 테스트
	void updateCommentByUnauthorizedUser() throws Exception {
		CommentRequest CommentRequest = new CommentRequest("content update");
		String jsonContent = objectMapper.writeValueAsString(CommentRequest);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/1/comments/{commentId}",newComment.getId())
						.header("X-User-Id", "2")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonContent))
				.andExpect(status().isForbidden());
	}

	@Test
	@Order(5) //댓글 소유자가 아닌 사용자가 댓글을 삭제하려고할때의 테스트
	void deleteCommentByUnauthorizedUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/1/comments/{commentId}",newComment.getId())
						.header("X-User-Id", "2"))
				.andExpect(status().isForbidden());
	}

	@Test
	@Order(6)
	void deleteComment() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/1/comments/{commentId}",newComment.getId())
						.header("X-User-Id", "1"))
				.andExpect(status().isNoContent());
	}
}