package com.fitdo.model.dao;

import java.util.List;

import com.fitdo.model.dto.Follow;

public interface FollowDao {

	// 팔로우 하기
	public void follow(String fromUserId, String toUserId);

	// 언팔로우 하기
	public void unfollow(String fromUserId, String toUserId);

	// 팔로우 유무
	public int followState(String fromUserId, String toUserId);

	// 팔로잉 리스트 조회
	public List<Follow> selectFolloweeList(String fromUserId);

	// 팔로잉 수
	public int selectFolloweeNum(String fromUserId);

	// 팔로워 리스트 조회
	public List<Follow> selectFollowerList(String toUserId);

	// 팔로워 수
	public int selectFollowerNum(String toUserId);
	
//	// 탈퇴시 팔로우삭제
//	public void deleteUserAllFollow(String fromUserId);

}
