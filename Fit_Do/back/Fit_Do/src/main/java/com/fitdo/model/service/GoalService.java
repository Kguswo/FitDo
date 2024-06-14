package com.fitdo.model.service;

import java.util.List;
import com.fitdo.model.dto.Goal;

public interface GoalService {
	
	// 게시물 별 목표 전체조회
    public List<Goal> getGoal(String goalUserId, String goalRegDate);
    
	// 목표아이디 별 목표 조회
    public Goal getGoalById(int goalId);
	
	// 게시물 별 목표 존재여부
	public boolean selectExistingGoal(String goalUserId, String goalRegDate);
	
	// 목표 등록
	public int createGoal(Goal goal);
	
	// 목표 수정
	public int updateGoal(Goal goal);
	
	// 목표 삭제
	public int deleteGoal(int goalId);

	// 날짜 비교
	public boolean isUpdatable(String goalRegDate);
}
