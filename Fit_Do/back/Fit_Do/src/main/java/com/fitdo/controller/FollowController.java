package com.fitdo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fitdo.model.dto.Follow;
import com.fitdo.model.dto.Post;
import com.fitdo.model.dto.User;
import com.fitdo.model.service.FollowService;
import com.fitdo.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/follow")
@Tag(name = "FollowRestController", description = "팔로우 관리")
@CrossOrigin("*")
public class FollowController {
    
    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);
    
    @Autowired
    private FollowService fs;
    
    @Autowired
    private UserService us;
    
    //팔로우
    @PostMapping("/doFollow")
    @Operation(summary = "팔로우하기")
    public ResponseEntity<?> follow(@RequestParam String fromUserId, @RequestParam String toUserId, HttpSession session) {
        logger.info("/follow/" + toUserId + " : 팔로우할래요");
        
//		  세션에서 로그인한사람 정보 가져옴
//        Object object = session.getAttribute("login");
//        if (object == null || !(object instanceof User)) {
//            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
//        }
        
        // 팔로우할사람 정보 가져옴
        User targetUser = us.searchById(toUserId).get(0);
        if (targetUser == null) {
            return new ResponseEntity<>("팔로우할 유저 없습니다!", HttpStatus.NOT_FOUND);
        }
        
        // 팔로우 여부 먼저 확인하고
        if (fs.followState(fromUserId, toUserId)) {
            return new ResponseEntity<>("해당 유저는 이미 팔로우하고 있습니다.", HttpStatus.BAD_REQUEST);
        }
        
        // 팔로우 실행!
        Follow follow = new Follow();
        follow.setFromUserId(fromUserId);
        follow.setToUserId(toUserId);
        
        fs.follow(fromUserId, toUserId);
        
        return new ResponseEntity<>("팔로우가 성공!", HttpStatus.OK);
    }
    
    
    // 언팔로우
    @PostMapping("/doUnfollow")
    @Operation(summary = "언팔로우 하기")
    public ResponseEntity<?> unfollow(@RequestParam String fromUserId, @RequestParam String toUserId, HttpSession session) {
        logger.info("/follow/unfollow/" + toUserId + " : 언팔할래요");
        
//		  세션에서 로그인한사람 정보 가져옴
//        Object object = session.getAttribute("login");
//        if (object == null || !(object instanceof User)) {
//            return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
//        }
        
        // 언팔할사람 정보 가져옴
        User targetUser = us.searchById(toUserId).get(0);
        if (targetUser == null) {
            return new ResponseEntity<>("언팔로우할 유저 없습니다!", HttpStatus.NOT_FOUND);
        }
        
        // 팔로우 여부 먼저 확인하고
        if (!fs.followState(fromUserId, toUserId)) {
            return new ResponseEntity<>("해당 유저는 팔로우를 하지 않았습니다.", HttpStatus.BAD_REQUEST);
        }
        
        // 언팔!
        Follow follow = new Follow();
        follow.setFromUserId(fromUserId);
        follow.setToUserId(toUserId);
        
        fs.unfollow(fromUserId, toUserId);
        
        return new ResponseEntity<>("언팔로우 성공!", HttpStatus.OK);
    }
    
    
    // 팔로우 여부 -> 팔로우, 팔로잉✔ 표시 위함ㅇ
    @PostMapping("/checkFollow")
    @Operation(summary = "팔로우 여부 체크")
    public ResponseEntity<Boolean> checkFollow(@RequestParam String fromUserId, @RequestParam String toUserId, HttpSession session) {
        logger.info("/follow/checkfollow/" + toUserId + " : 팔로우 여부 확인");
        
// 		  세션에서 로그인한사람 정보 가져옴
//        Object object = session.getAttribute("login");
//        if (object == null || !(object instanceof User)) {
//            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
//        }
        
        // 팔로우할사람 정보 가져옴
        User targetUser = us.searchById(toUserId).get(0);
        if (targetUser == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        
        // 팔로우 여부 먼저 확인하고
        boolean isFollowing = fs.followState(fromUserId, toUserId);
        
        // true면 팔로우중, false면 팔로우 x
        return new ResponseEntity<>(isFollowing, HttpStatus.OK);
    }
    
    
    // 내가 팔로우 한 유저 리스트
    @GetMapping("/followee")
    @Operation(summary = "내가 팔로우 한 유저")
    public ResponseEntity<?> followeeList(@RequestParam String fromUserId) {
    	List<Follow> followee = fs.selectFolloweeList(fromUserId);
		
    	for (Follow follow : followee) {
			User user = us.searchById(follow.getToUserId()).get(0);
			// 사용자 정보가 null이 아닌 경우에만 설정
			if (user != null) {
				follow.setProfileImg(user.getProfileImg());
			}
		}
    	System.out.println(followee);
    	if(!followee.isEmpty()) {
    		return new ResponseEntity<>(followee, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
    
    
    // 팔로잉 수 
    @GetMapping("/followeeNum")
    @Operation(summary = "팔로잉 한 사람 수")
    public ResponseEntity<?> followeeNum(@RequestParam String fromUserId) {
    	int followeeNum = fs.selectFolloweeNum(fromUserId);
    	if(followeeNum != 0) {
    		return new ResponseEntity<>(followeeNum, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    
    // 나를 팔로우 한 유저
    @GetMapping("/follower")
    @Operation(summary = "나를 팔로우 한 유저")
    public ResponseEntity<?> followerList(@RequestParam String toUserId) {
    	List<Follow> follower = fs.selectFollowerList(toUserId);
    	for (Follow follow : follower) {
			User user = us.searchById(follow.getFromUserId()).get(0);
			// 사용자 정보가 null이 아닌 경우에만 설정
			if (user != null) {
				follow.setProfileImg(user.getProfileImg());
			}
		}
    	System.out.println(follower);
    	if(!follower.isEmpty()) {
    		return new ResponseEntity<>(follower, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
    
    
    // 팔로우 수 
    @GetMapping("/followerNum")
    @Operation(summary = "팔로우 받은 사람 수")
    public ResponseEntity<?> followerNum(@RequestParam String toUserId) {
    	int followerNum = fs.selectFollowerNum(toUserId);
    	if(followerNum != 0) {
    		return new ResponseEntity<>(followerNum, HttpStatus.OK);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    } 
}
