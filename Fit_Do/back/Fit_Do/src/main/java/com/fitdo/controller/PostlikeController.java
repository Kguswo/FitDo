package com.fitdo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fitdo.model.dto.Comment;
import com.fitdo.model.dto.Post;
import com.fitdo.model.dto.Postlike;
import com.fitdo.model.dto.User;
import com.fitdo.model.service.PostService;
import com.fitdo.model.service.PostlikeService;
import com.fitdo.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/postlike")
@Tag(name = "PostlikeController", description = "게시물 좋아요 관리")
@CrossOrigin("*")
public class PostlikeController {
    
    private static final Logger logger = LoggerFactory.getLogger(PostlikeController.class);
    
    @Autowired
    private PostlikeService pls;
    
    @Autowired
    private UserService us;
    
    @Autowired
    private PostService ps;
    
    @PostMapping("/addPostlike")
    @Operation(summary = "게시물 좋아요 누르기")
    public ResponseEntity<?> addPostlike(@RequestParam("userId") String userId, @RequestParam("postId") int postId, HttpSession session) {
    	try {
            // Check if the user exists
            if (!us.isExistingUser(ps.getPost().get(0).getPostUserId())) {
                return new ResponseEntity<>("존재하지 않는 회원입니다", HttpStatus.BAD_REQUEST);
            }

            // Check if the user has already liked the post
            if (pls.isPostlike(userId, postId)) {
                return new ResponseEntity<>("이미 좋아요 누른 게시물입니다.", HttpStatus.BAD_REQUEST);
            }

            int result = pls.addPostlike(userId, postId);

            if (result > 0) {
            	//post의 좋아요 수 1증가
                Post post = ps.selectOnePost(postId);
                post.setPostLike(post.getPostLike() + 1);
                ps.updatePost(post);
            	
                return new ResponseEntity<>(userId + "님이 좋아요 눌렀습니다!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("게시물 좋아요 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("게시물 좋아요 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 좋아요 실패 오류: " + e.getMessage());
        }
    }
    
    
    @DeleteMapping("/cancelPostlike")
    @Operation(summary = "게시물 좋아요 취소")
    public ResponseEntity<?> cancelPostlike(@RequestParam("userId") String userId, @RequestParam("postId") int postId, HttpSession session) {
    	
        int result = pls.cancelPostlike(userId, postId);

        if (result > 0) {
        	//post의 좋아요 수 1감소
        	Post post = ps.selectOnePost(postId);
            post.setPostLike(post.getPostLike() - 1);
            ps.updatePost(post);
            
            return new ResponseEntity<>(userId + "님이 좋아요를 취소했습니다!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("게시물 좋아요 취소 실패", HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @GetMapping("/postlikeState")
    @Operation(summary = "게시물 좋아요 여부 확인")
    public ResponseEntity<Boolean> isPostlike(@RequestParam("userId") String userId, @RequestParam("postId") int postId) {
        boolean isPostLiked = pls.isPostlike(userId, postId);
        return new ResponseEntity<>(isPostLiked, HttpStatus.OK);
    }
    
    
    @GetMapping("/PostlikeUsers")
    @Operation(summary = "게시물 좋아요 한 유저 목록 조회 + 프사")
    public ResponseEntity<List<?>> getPostlikeUsers(@RequestParam("postId") int postId) {
        List<Postlike> postlikeUsers = pls.selectPostlikeUserList(postId);
        for(Postlike postlike : postlikeUsers) {
        	String postlikeuserImg = us.searchById(postlike.getPostlikeUserId()).get(0).getProfileImg();
        	if(postlikeuserImg != null) {
        		postlike.setPostlikeUserImg(postlikeuserImg);
        	}
        }
        System.out.println(postlikeUsers);
        return new ResponseEntity<>(postlikeUsers, HttpStatus.OK);
    }
    

    @GetMapping("/countPostlikes")
    @Operation(summary = "게시물 좋아요 숫자 조회")
    public ResponseEntity<Integer> getPostlikeCount(@RequestParam("postId") int postId) {
        int postlikeCount = pls.selectPostlikeNum(postId);
        return new ResponseEntity<>(postlikeCount, HttpStatus.OK);
    }
}
