package com.fitdo.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitdo.model.dao.CommentDao;
import com.fitdo.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentDao commentDao;

	@Autowired
	public CommentServiceImpl(CommentDao commentDao) {
		super();
		this.commentDao = commentDao;
	}

	@Override
	public List<Comment> selectAllComment(int PostId) {
		return commentDao.selectAllComment(PostId);
	}
	
	@Override
	public List<Comment> selectAllCommentWithUserInfo(String commentUserId) {
		return commentDao.selectAllCommentWithUserInfo(commentUserId);
	}

	@Override
	public int getCommentNum(int PostId) {
		return commentDao.getCommentNum(PostId);
	}
	
	@Override
	public int createComment(Comment comment) {
		return commentDao.createComment(comment);
	}

	@Override
	public int updateComment(Comment comment) {
		return commentDao.updateComment(comment);
	}

	@Override
	public int deleteComment(int commentId) {
		return commentDao.deleteComment(commentId);
	}

}
