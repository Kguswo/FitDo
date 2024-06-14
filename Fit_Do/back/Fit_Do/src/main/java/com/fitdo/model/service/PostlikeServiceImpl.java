package com.fitdo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitdo.model.dao.PostlikeDao;
import com.fitdo.model.dto.Postlike;

@Service
public class PostlikeServiceImpl implements PostlikeService{

	PostlikeDao postlikeDao;
	
	@Autowired
	public PostlikeServiceImpl(PostlikeDao postlikeDao) {
		super();
		this.postlikeDao = postlikeDao;
	}

	@Override
	public int addPostlike(String postlikeUserId, int postlikePostId) {
		return postlikeDao.addPostlike(postlikeUserId, postlikePostId);
	}

	@Override
	public int cancelPostlike(String postlikeUserId, int postlikePostId) {
		return postlikeDao.cancelPostlike(postlikeUserId, postlikePostId);

	}
	
	@Override
	public boolean isPostlike(String postlikeUserId, int postlikePostId) {
		int count = postlikeDao.isPostlike(postlikeUserId, postlikePostId);
		return count > 0 ;
	}

//	@Override
//	public void increasePostlike(int PostlikePostId, Post post) {
//		return;
//	}
//
//	@Override
//	public void decreasePostlike(int PostlikePostIdd, Post post) {
//		return;
//	}

	@Override
	public List<Postlike> selectPostlikeUserList(int postlikePostId) {
		return postlikeDao.selectPostlikeUserList(postlikePostId);
	}

	@Override
	public int selectPostlikeNum(int postlikePostId) {
		return postlikeDao.selectPostlikeNum(postlikePostId);
	}
	
}
