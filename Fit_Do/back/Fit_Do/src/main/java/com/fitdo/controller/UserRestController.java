package com.fitdo.controller;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fitdo.model.dto.User;
import com.fitdo.model.service.S3FileUploadServiceImpl;
import com.fitdo.model.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Tag(name = "UserRestController", description = "유저 관리")
@CrossOrigin("*")
public class UserRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
//	private final ResourceLoader resourceLoader;
//	@Autowired
//    private ResourceLoader resourceLoader;

	@Autowired
	private UserService us;

	@Autowired
	private S3FileUploadServiceImpl ss;

	// 회원가입
	@PostMapping("/signup")
	@Operation(summary = "회원가입하기")
	public ResponseEntity<String> signup(@RequestBody User user) {
		try {
			// 아이디로 중복회원 검사먼저, true 면 중복회원 존재
			if (us.isExistingUser(user.getUserid())) {
				return new ResponseEntity<>("중복 회원 존재! : ", HttpStatus.BAD_REQUEST);
			}
			us.createUser(user);
			return new ResponseEntity<>("회원가입 성공!", HttpStatus.CREATED);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return new ResponseEntity<>("회원가입 실패! : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("회원가입 실패! : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	// 회원정보 + 프로필사진
//	@PostMapping("/singupWithProfileImg")
//    public ResponseEntity<?> singupWithProfileImg(@ModelAttribute User user, @RequestParam("file") MultipartFile file) {
//		try {
//			// 아이디로 중복회원 검사먼저, true 면 중복회원 존재
//			if (us.isExistingUser(user.getUserid())) {
//				return new ResponseEntity<>("중복 회원 존재! : ", HttpStatus.BAD_REQUEST);
//			}
//
//			us.createUserWithProfileImg(user, file);
//
//			return new ResponseEntity<>("회원가입 성공!", HttpStatus.CREATED);
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("회원가입 실패! : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("회원가입 실패! : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}

	// 프로필 이미지 업로드
	@PostMapping(value = "/{userid}/uploadImage", consumes = "multipart/form-data")
	@Operation(summary = "프사 이미지 api")
	public ResponseEntity<?> uploadImage(@PathVariable String userid, @RequestPart MultipartFile file,
			@RequestBody User user) throws IOException {
		try {
			String url = ss.uploadFile(file);
			System.out.println("file : " + file);
			System.out.println("url : " + url);

			return ResponseEntity.ok(url);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일 업로드 중 오류 발생");
		}
	}

	// 로그인
	@PostMapping("/login")
	@Operation(summary = "로그인하기")
	public ResponseEntity<?> login(@RequestParam("userid") String userid, @RequestParam("password") String password,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		User user = us.login(userid, password);
		if (user != null) {
			session.setAttribute("loginedUser", user);
			System.out.println("세션에 저장된 회원정보 " + session.getAttribute("loginedUser"));

			// 유저 아이디 담아다닐 쿠키 생성!
			Cookie cookie = new Cookie("loginedUserId", user.getUserid());
			response.addCookie(cookie);
			System.out.println("쿠키에 저장된 정보 " + cookie.getValue());
			System.out.println("cookie.getValue() : " + cookie.getValue());
			System.out.println("cookie.getName() : " + cookie.getName());
			System.out.println("=======================================");

			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("로그인 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	// 세션 정보읽어오기
//    @GetMapping("/user-info")
//    @ResponseBody
//    public String getUserInfo(HttpSession session) {
//        // 세션에서 사용자 정보 읽기
//        String username = (String) session.getAttribute("username");
//        return "로그인된 회원정보: " + username;
//    }

	// 쿠키 가져오기
//    public String getCookieValue(HttpServletRequest request, String cookieName) {
//        String value = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//            	System.out.println("getValue : "+cookie.getValue());
//            	System.out.println("getName : "+cookie.getName());
//                if (cookie.getName().equals(cookieName)) {
//                    value = cookie.getValue();
//                    break;
//                }
//            }
//        }
//        return value;
//    }
//    // 쿠키값 읽어오기
//    @GetMapping("/get-cookie")
//    public String getCookie(HttpServletRequest request) {
//        String value = getCookieValue(request, "myCookie");
//        return "Value from cookie: " + value;
//    }
//    @GetMapping("/set-cookie")
//    public String setCookie(HttpServletResponse response) {
//        // 쿠키에 담을 정보
//        String userId = "myUserId";
//
//        // 쿠키 생성 및 클라이언트에게 전송
//        CookieUtil.setCookie(response, "userId", userId);
//
//        return "쿠키에 사용자 아이디 저장 : " + userId;
//    }

	// 로그아웃
	@GetMapping("/logout")
	@Operation(summary = "로그아웃하기")
	public ResponseEntity<String> doLogout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("로그아웃 되었습니다.");
	}

	// 회원정보 조회
	@GetMapping("/{userId}")
	@Operation(summary = "회원 정보 조회하기")
	public ResponseEntity<?> getUser(@PathVariable String userId) {
		List<User> user = us.searchById(userId);
		if (!user.isEmpty()) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("해당 회원이 없습니다!", HttpStatus.NOT_FOUND);
		}
	}
	
	// 검색어로 회원 찾기
	@GetMapping("/search/{userId}")
	@Operation(summary = "회원 아이디로 검색하기")
	public ResponseEntity<?> searchUserById(@PathVariable String userId) {
		List<User> user = us.searchUsersByIdToFollow(userId);
		if (!user.isEmpty()) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("해당 회원이 없습니다!", HttpStatus.NOT_FOUND);
		}
	}
	
	// 나와 MBTI같은데 내가 팔로우 안하는 사람들 추천
	@GetMapping("/{userId}/sameMbtiNotFollowing")
    @Operation(summary = "같은 MBTI이면서 팔로잉하지 않은 유저 추천하기")
    public ResponseEntity<?> findUsersWithSameMbtiNotFollowing(@PathVariable String userId, @RequestParam String mbti) {
        List<User> recommendedUsers = us.findUsersWithSameMbtiNotFollowing(userId, mbti);
        if (!recommendedUsers.isEmpty()) {
            return new ResponseEntity<>(recommendedUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("추천할 사용자가 없습니다.", HttpStatus.NOT_FOUND);
        }
    }

	// 회원정보 수정
	@PutMapping("/{userId}")
	@Operation(summary = "회원 정보 수정하기")
	public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody User user) {
		user.setUserid(userId);
		int result = us.updateUser(user);
		if (result > 0) {
			return new ResponseEntity<>("회원정보 수정 성공!", HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body("회원정보 수정 실패!");
		}
	}

	// 회원 프로필 사진 수정
	@PostMapping(value = "/{userid}/updateImage", consumes = "multipart/form-data")
	@Operation(summary = "프사 이미지 수정하기")
	public ResponseEntity<?> updateProfileImage(@PathVariable String userid, @RequestPart MultipartFile file) throws IOException {
		try {
			String url = ss.uploadFile(file);
			System.out.println("url : " + url);
			
            List<User> users = us.searchById(userid);
            if (users.isEmpty()) {
    			return new ResponseEntity<>("해당 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
            User user = users.get(0);
            int updateImg = us.updateProfileImageUrl(userid, url);
            System.out.println("updateImage : " + updateImg);
            
			return new ResponseEntity<>("프사 수정 성공!", HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>("프사 업로드 실패", HttpStatus.BAD_REQUEST);
		}
	}

	// 회원 프로필 사진 삭제
	@PostMapping(value = "/{userid}/deleteImage")
	@Operation(summary = "프사 이미지 삭제하기")
	public ResponseEntity<?> deleteProfileImage(@PathVariable String userid, @RequestBody User user)
			throws IOException {

		try {
			int updateImg = us.updateProfileImageUrl(userid,
					"https://fit-do.s3.ap-southeast-2.amazonaws.com/Nomal_profile.jpg");
			System.out.println(updateImg);
			return new ResponseEntity<>("프사 삭제 성공!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("프사 삭제 중 오류 발생", HttpStatus.BAD_REQUEST);
		}
	}

	// 회원정보 삭제
	@DeleteMapping("/{userId}")
	@Operation(summary = "회원정보 삭제")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
		List<User> user = us.searchById(userId);
		if (!user.isEmpty()) {
			us.deleteUser(userId);
			return new ResponseEntity<>("회원정보 삭제!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		}
	}

}
