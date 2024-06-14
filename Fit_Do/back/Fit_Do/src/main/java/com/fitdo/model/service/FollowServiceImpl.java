package com.fitdo.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitdo.model.dao.FollowDao;
import com.fitdo.model.dto.Follow;

@Service
public class FollowServiceImpl implements FollowService{
	
	private final FollowDao followDao;
	
	@Autowired
	public FollowServiceImpl(FollowDao followDao) {
		super();
		this.followDao = followDao;
	}

	@Override
	public void follow(String fromUserId, String toUserId) {
		followDao.follow(fromUserId, toUserId);
	}

	@Override
	public void unfollow(String fromUserId, String toUserId) {
		followDao.unfollow(fromUserId, toUserId);
	}

	@Override
	public boolean followState(String fromUserId, String toUserId) {
	    int count = followDao.followState(fromUserId, toUserId);
	    return count > 0;
	}

	@Override
	public List<Follow> selectFolloweeList(String fromUserId) {
		return followDao.selectFolloweeList(fromUserId);
	}

	@Override
	public int selectFolloweeNum(String fromUserId) {
		return followDao.selectFolloweeNum(fromUserId);
	}

	@Override
	public List<Follow> selectFollowerList(String toUserId) {
		return followDao.selectFollowerList(toUserId);
	}

	@Override
	public int selectFollowerNum(String toUserId) {
		return followDao.selectFollowerNum(toUserId);
	}

//	@Override
//	public void deleteUserAllFollow(String fromUserId) {
//		// TODO Auto-generated method stub
//		
//	}
	
}
