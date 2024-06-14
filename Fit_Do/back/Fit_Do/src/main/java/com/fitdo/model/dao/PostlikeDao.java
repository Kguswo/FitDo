package com.fitdo.model.dao;

import java.util.List;

import com.fitdo.model.dto.Postlike;

public interface PostlikeDao {

	// 게시물 좋아요 누르기
	public int addPostlike(String postlikeUserId, int postlikePostId);
	
	// 게시물 좋아요 취소
	public int cancelPostlike(String postlikeUserId, int postlikePostId);
	
	// 게시물 좋아요 여부
	public int isPostlike(String postlikeUserId, int postlikePostId);

//	// 게시물 좋아요 수 1 증가
//	public void increasePostlike(int PostlikePostId, Post post);
//
//	// 게시물 좋아요 수 1 감소
//	public void decreasePostlike(int PostlikePostIdd, Post post);

	// 게시물 좋아요 한 유저 목록
	public List<Postlike> selectPostlikeUserList(int postlikePostId);

	// 게시물 좋아요 숫자
	public int selectPostlikeNum(int postlikePostId);

}
