package com.fitdo.model.dao;

import java.util.List;

import com.fitdo.model.dto.Post;

public interface PostDao {
	
	// 게시물 전체 조회
	public List<Post> getPost();
	
	// 게시물 전체 조회 + 프사
	public List<Post> getPostWithUserProfileImg();
	
	// 게시물 상세 조회
	public Post selectOnePost(int postUserId);
	
	// 게시물 날 팔로우하는 유저 아이디로 상세 조회 + 본인 포함
	public List<Post> selectPostsByUserId(String followeeId);
	
	// 게시물 등록
	public int createPost(Post post);
	
	// 게시물 수정
	public int updatePost(Post post);
	
	// 게시물 삭제
	public int deletePost(int postUserId);
	
	// 유저아이디를통해 일치하는 게시물들 리스트
	public List<Post> selectPostlistByUserId(String postUserId);

}
