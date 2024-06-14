package com.fitdo.model.service;

import java.io.IOException;
import java.util.List;
import com.fitdo.model.dto.User;

public interface UserService {
	
	// 회원가입
	public int createUser(User user) throws IllegalStateException, IOException;
	
//	// 프사 포함 회원가입
//    int createUserWithProfileImg(User user, MultipartFile file) throws IllegalStateException, IOException;
	
	// 로그인
	public User login(String userid, String password);

	// 검색
	public List<User> searchById(String id);
	
	// 검색어 포함된 유저아이디 모두 검색
	public List<User> searchUsersByIdToFollow(String id);
	
	// 추천을 위한 같은 MBTI를 가진 팔로잉 하지 않은 사용자 검색
	public List<User> findUsersWithSameMbtiNotFollowing(String fromUserId, String mbti);
	
	// 회원 프사 검색
	public int updateProfileImageUrl(String userid, String userProfileImg);
	
	// 회원정보 수정
	public int updateUser(User user);
	
	// 회원정보 삭제
	public int deleteUser(String userid);
	
	// 회원중복 유무
	public boolean isExistingUser(String userid);

//	// 이미지 업로드
//    UserResponseDto.ImageRet updateImage(String userid, MultipartFile image) throws IOException;

}
