package com.fitdo.model.service;

import java.util.List;

import com.fitdo.model.dto.Comment;

public interface CommentService {
	
	// 게시물 별 댓글 전제조회
	public List<Comment> selectAllComment(int PostId);
	
	// 게시물 별 댓글 조회 + 유저 정보
	public List<Comment> selectAllCommentWithUserInfo(String commentUserId);
	
	// 게시물 별 댓글 수
	public int getCommentNum(int PostId);
	
	// 댓글 등록
	public int createComment(Comment comment);
	
	// 댓글 수정
	public int updateComment(Comment comment);
	
	// 댓글 삭제
	public int deleteComment(int commentId);
	
}
