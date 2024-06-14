package com.fitdo.model.dao;

import java.util.List;
import java.util.Map;

import com.fitdo.model.dto.Post;
import com.fitdo.model.dto.User;

public interface UserDao {
		
	// 회원가입
	public int createUser(User user);
	
	// 로그인
	public  User login(String userid, String password);
	
	// 검색
	public List<User> searchById(String id);
	
	// 검색어 포함된 유저아이디 모두 검색
	public List<User> searchUsersByIdToFollow(String id);
	
	// 추천을 위한 같은 MBTI를 가진 팔로잉 하지 않은 사용자 검색
	public List<User> findUsersWithSameMbtiNotFollowing(Map<String, String> recommendationKey);
	
	// 회원정보 수정
	public int updateUser(User user);
	
	// 회원정보 삭제
	public int deleteUser(String id);

	// 회원중복 유무
	public int isExistingUser(String userid);

}