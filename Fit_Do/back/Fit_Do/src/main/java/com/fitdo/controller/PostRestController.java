package com.fitdo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fitdo.model.dto.Follow;
import com.fitdo.model.dto.Post;
import com.fitdo.model.dto.User;
import com.fitdo.model.service.CommentService;
import com.fitdo.model.service.FollowService;
import com.fitdo.model.service.PostService;
import com.fitdo.model.service.S3FileUploadServiceImpl;
import com.fitdo.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/post")
@Tag(name = "PostRestController", description = "게시물 관리")
@CrossOrigin("*")
public class PostRestController {

	@Autowired
	private PostService ps;

	@Autowired
	private UserService us;

	@Autowired
	private S3FileUploadServiceImpl ss;

	@Autowired
	private FollowService fs;
	
	@Autowired
	private CommentService cs;

	// 게시물 전체 조회
	@GetMapping("/")
	@Operation(summary = "게시물 전체 조회")
	public ResponseEntity<List<Post>> getAllPosts() {
		List<Post> posts = ps.getPost();
		if (posts == null || posts.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	// 게시물 전체 조회 + 프사 + 댓글수
	@GetMapping("/withImg")
	@Operation(summary = "게시물 전체 조회 + 프사 + 댓글수")
	public ResponseEntity<List<Post>> getAllPostsWithProfileImg() {
		List<Post> posts = ps.getPostWithUserProfileImg();
		for (Post post : posts) {
			User user = us.searchById(post.getPostUserId()).get(0);
	        int commentsNum = cs.getCommentNum(post.getPostId());
			// 사용자 정보가 null이 아닌 경우에만 설정
			if (user != null) {
				post.setUser(user);
				post.setCommentNum(commentsNum);
			}
		} 
		System.out.println(posts);
		if (posts == null || posts.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	// 팔로우 하는 유저 게시물 전체 조회 + 본인포함
	@GetMapping("/followee/{userId}")
	@Operation(summary = "팔로우 하는 유저 게시물 전체 조회 + 본인포함")
	public ResponseEntity<List<Post>> getFolloweePosts(@PathVariable("userId") String userId) {

		List<Follow> followee = fs.selectFolloweeList(userId);
		System.out.println("내가 팔로우 하는 사람들 객체 : " + followee);

		List<String> followeeUserId = new LinkedList<>();
		for (Follow follow : followee) {
			followeeUserId.add(follow.getToUserId());
		}
		System.out.println("내가 팔로우 하는 사람들 아이디 목록 : " + followeeUserId);

		// 나도추가
		followeeUserId.add(userId);

		// 내가 볼 팔로우하는 사람들의 게시물 리스트
		List<Post> myFolloweePosts = new ArrayList<>();
		for (String followeeId : followeeUserId) {
			List<Post> userPosts = ps.selectPostsByUserId(followeeId);
			myFolloweePosts.addAll(userPosts);
		}

		List<Post> posts = myFolloweePosts;
		for (Post post : posts) {
			User user = us.searchById(post.getPostUserId()).get(0);
			// 사용자 정보가 null이 아닌 경우에만 설정
			if (user != null) {
				post.setUser(user);
			}
		}

		if (posts == null || posts.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}

	// 게시물 상세 조회
	@GetMapping("/{postId}")
	@Operation(summary = "게시물 상세 조회")
	public ResponseEntity<Post> getOnePost(@PathVariable int postId) {
		Post post = ps.selectOnePost(postId);
		if (post != null) {
			return new ResponseEntity<>(post, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 게시물 상세 조회
	@GetMapping("/getPostByUserId/{postUserId}")
	@Operation(summary = "게시물 유저아이디로 상세 조회 + 유저 정보")
	public ResponseEntity<Post> getOnePostByUserId(@PathVariable String postUserId) {
		Post post = ps.selectPostsByUserId(postUserId).get(0);
		User user = us.searchById(postUserId).get(0);
		if (user != null) {
			post.setUser(user);
		}
		if (post != null) {
			return new ResponseEntity<>(post, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// 게시물들 유저아이디 일치하는 사람으로 조회
	@GetMapping("/getPostsListByUserId/{postUserId}")
	@Operation(summary = "게시물들 리스트 유저아이디로 상세 조회 - 게시물들 여러개 받는 거")
	public ResponseEntity<?> getPostsListByUserId(@PathVariable String postUserId) {
		List<Post> posts = ps.selectPostlistByUserId(postUserId);
		if (posts != null) {
			return new ResponseEntity<>(posts, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// 게시물 등록
	@PostMapping("/addpost/{postUserId}")
	@Operation(summary = "게시물 등록")
	public ResponseEntity<?> createPost(@PathVariable("postUserId") String postUserId, @RequestBody Post post,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//		User user = (User) session.getAttribute("user");
//		System.out.println("user : " + user);
//        Cookie myCookie = new Cookie("myUserId : ", user.getUserid());
//        System.out.println(myCookie.getName());
//        System.out.println(myCookie.getValue());
//		Cookie[] cookies = request.getCookies();
//	    if (cookies != null) {
//	        for (Cookie cookie : cookies) {
//		        System.out.println("cookie.getName() : " + cookie.getName());
//		        System.out.println("cookie.getValue() : " + cookie.getValue());
//	        }
//	    }

		// Swagger통신시 세션저장이 안되는 문제점 해결 할 때 까지 테스트용 임시user "ssafy"생성

//		if (user == null) {
//			// 세션에 사용자 정보가 없을 때 임시로 사용자 객체 생성
//	        user = new User();
//	        user.setUserid("ssafy");
//	        // 임시로 생성한 사용자 객체를 세션에 저장
//	        session.setAttribute("user", user);
////			return new ResponseEntity<>("로그인이 필요합니다!", HttpStatus.UNAUTHORIZED);
//		}

//		post.setPostUserId(user.getUserid());

		// User객체까지 등록할 때 프론트단으로 넘겨줄거임
		User user = us.searchById(postUserId).get(0);
		post.setPostUserId(user.getUserid());
		post.setUser(user);
		System.out.println("postUserId : " + post.getPostUserId());
		System.out.println("user : " + user);
		System.out.println("userId : " + user.getUserid());
		System.out.println("userProfileImg : " + user.getProfileImg());

		int result = 0;
		try {
			result = ps.createPost(post, request);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 등록 실패!");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 등록 실패!");
		}
		if (result > 0) {
			return new ResponseEntity<>("게시물 등록 성공!", HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 등록 실패!");
		}
	}

	// 게시물 등록 및 이미지 업로드
	@PostMapping(value = "/addpostWithImg/{postUserId}", consumes = "multipart/form-data")
	@Operation(summary = "게시물 등록 및 이미지 업로드")
	public ResponseEntity<?> createPostWithImage(@PathVariable("postUserId") String postUserId,
			@RequestPart("post") @Valid Post post, @RequestPart("file") MultipartFile file, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		int result = 0;
		try {
//	    	System.out.println("https://fit-do.s3.ap-southeast-2.amazonaws.com/"+file.getOriginalFilename());
//	        post.setPostImg("https://fit-do.s3.ap-southeast-2.amazonaws.com/"+file.getOriginalFilename());

			String fileUrl = ss.uploadFile(file);
			System.out.println("fileUrl : " + fileUrl);
			post.setPostImg(fileUrl);

			// User객체까지 등록할 때 프론트단으로 넘겨줄거임
			User user = us.searchById(postUserId).get(0);
			post.setPostUserId(user.getUserid());
			post.setUser(user);
			System.out.println("postUserId : " + post.getPostUserId());
			System.out.println("user : " + user);
			System.out.println("userId : " + user.getUserid());
			System.out.println("userProfileImg : " + user.getProfileImg());

			result = ps.createPost(post, request);
			if (result > 0) {
				return new ResponseEntity<>("게시물 등록 성공!", HttpStatus.CREATED);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 등록 실패!");
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 등록 실패!");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 등록 실패!");
		}
	}

	// 게시물 수정
	@PutMapping("/{postId}")
	@Operation(summary = "게시물 수정")
	public ResponseEntity<String> updatePost(@PathVariable int postId, @RequestBody Post post) {
		post.setPostId(postId);
		int result = ps.updatePost(post);
		if (result > 0) {
			return ResponseEntity.ok("게시물 수정 성공!");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 수정 실패");
		}
	}

	// 게시물 사진 수정
	@PutMapping(value = "/{postid}/updateImage", consumes = "multipart/form-data")
	@Operation(summary = "게시물 사진 수정")
	public ResponseEntity<String> updatePostImg(@PathVariable int postid, @RequestPart MultipartFile file)
			throws IOException {
		try {
			String url = ss.uploadFile(file);
			System.out.println("게시물 사진 url : " + url);

			Post post = ps.selectOnePost(postid);

			if (post == null) {
				return new ResponseEntity<>("해당 게시물이 존재하지 않음", HttpStatus.NOT_FOUND);
			}
			int updateImg = ps.updatePostImageUrl(postid, url);
			System.out.println("updateImage : " + updateImg);

			return new ResponseEntity<>("게시물 사진 수정 성공!", HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>("사진 업로드 실패", HttpStatus.BAD_REQUEST);
		}
	}

	// 게시물 삭제
	@DeleteMapping("/{postId}")
	@Operation(summary = "게시물 삭제")
	public ResponseEntity<String> deletePost(@PathVariable int postId) {
		int result = ps.deletePost(postId);
		if (result > 0) {
			return ResponseEntity.ok("게시물 삭제!");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시물 삭제 실패");
		}
	}
}
