package com.fitdo.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitdo.model.dao.GoalDao;
import com.fitdo.model.dto.Goal;

@Service
public class GoalServiceImpl implements GoalService {

	private final GoalDao goalDao;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public GoalServiceImpl(GoalDao goalDao) {
		super();
		this.goalDao = goalDao;
	}
	
	@Override
	public List<Goal> getGoal(String goalUserId, String goalRegDate) {
		return goalDao.getGoal(goalUserId, goalRegDate);
	}

	@Override
	public Goal getGoalById(int goalId) {
		return goalDao.getGoalById(goalId);
	}
	
	@Override
	public boolean selectExistingGoal(String goalUserId, String goalRegDate) {
		int goalcount = goalDao.selectExistingGoal(goalUserId, goalRegDate);
		if (goalcount > 0) {
			System.out.println("오늘의 목표 있음");
			return true;
		} else {
			System.out.println("오늘의 목표 없음");
			return false;
		}
	}

	@Override
	public int createGoal(Goal goal) {
		return goalDao.createGoal(goal);
	}

	@Override
	public int updateGoal(Goal goal) {
		if (!isUpdatable(goal.getGoalRegDate())) {
			throw new IllegalArgumentException("과거 날짜의 목표는 수정할 수 없음");
		}
		return goalDao.updateGoal(goal);	}

	@Override
	public int deleteGoal(int goalId) {
		return goalDao.deleteGoal(goalId);
	}
	
	@Override
    public boolean isUpdatable(String goalRegDate) {
		if (goalRegDate == null) {
            throw new IllegalArgumentException("등록 날짜 = null");
        }
        try  {
            Date inputDate = dateFormat.parse(goalRegDate); // 수정할 날짜
            Date currentDate = new Date(); // 오늘 날짜
            return !inputDate.before(dateFormat.parse(dateFormat.format(currentDate)));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


}