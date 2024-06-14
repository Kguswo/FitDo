package com.fitdo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitdo.model.dto.Comment;
import com.fitdo.model.dto.Post;
import com.fitdo.model.dto.User;
import com.fitdo.model.service.CommentService;
import com.fitdo.model.service.PostService;
import com.fitdo.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/comment")
@Tag(name = "CommentRestController", description = "댓글 관리")
@CrossOrigin("*")
public class CommentRestController {

    @Autowired
    private CommentService cs;

    @Autowired
	private UserService us;
    
    @Autowired
	private PostService ps;

    // 게시물 별 댓글 전체 조회
    @GetMapping("/{postId}")
    @Operation(summary = "게시물 별 댓글 전체 조회 + 유저 정보")
    public ResponseEntity<?> getAllComments(@PathVariable int postId) {
        List<Comment> comments = cs.selectAllComment(postId);
        for(Comment comment : comments) {
        	User user = us.searchById(comment.getCommentUserId()).get(0);
        	if(user != null) {
        		comment.setUser(user);
        	}
        }
        if (comments.isEmpty()) {
            return new ResponseEntity<>("해당 게시물 없음!", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
    }
    
    
    // 게시물 별 댓글 조회 + 유저 정보
    @GetMapping("/{postId}/WithUserInfo")
    @Operation(summary = "게시물 별 댓글 조회 + 유저 정보")
    public ResponseEntity<List<Comment>> getAllPostsWithProfileImg(@PathVariable int postId) {

        List<Comment> comments = cs.selectAllComment(postId);
	    for (Comment comment : comments) {
	        User user = us.searchById(comment.getCommentUserId()).get(0);

	        if (user != null) {
	            comment.setUser(user);
	        }
	    }
		
		if (comments == null || comments.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

    // 댓글 등록
    @PostMapping("/addcomment")
    @Operation(summary = "댓글 등록")
    public ResponseEntity<String> createComment(@RequestBody Comment comment) {
    	cs.createComment(comment);
		return new ResponseEntity<String>("댓글 등록성공!", HttpStatus.OK);
    }

    
    // 게시물 별 댓글 수
    @GetMapping("/{postId}/commentNum")
    @Operation(summary = "게시물 별 댓글 수")
    public ResponseEntity<?> getCommentsNum(@PathVariable int postId) {
        int commentsNum = cs.getCommentNum(postId);
		Post post = ps.selectOnePost(postId);
		if (post == null) {
			return new ResponseEntity<>("해당 게시물 존재하지 않음", HttpStatus.OK);
		}
        return new ResponseEntity<>(commentsNum, HttpStatus.OK);
    }
    
    
    // 댓글 수정
    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<String> updateComment(@PathVariable int commentId, @RequestBody Comment comment) {
        comment.setCommentId(commentId);
        int result = cs.updateComment(comment);
        if (result > 0) {
            return ResponseEntity.ok("댓글 수정성공!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정실패");
        }
    }
    

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<String> deleteComment(@PathVariable int commentId) {
        int result = cs.deleteComment(commentId);
        if (result > 0) {
            return ResponseEntity.ok("댓글 삭제성공!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제실패");
        }
    }
}
