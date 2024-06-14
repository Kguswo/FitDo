package com.fitdo.model.dto;

public class Goal {

	private int goalId;
	private String goalUserId;
	private String goalRegDate;
	private String goalContent;

	public Goal() {
	}

	public Goal(int goalId, String goalUserId, String goalRegDate, String goalContent) {
		super();
		this.goalId = goalId;
		this.goalUserId = goalUserId;
		this.goalRegDate = goalRegDate;
		this.goalContent = goalContent;
	}

	public int getGoalId() {
		return goalId;
	}

	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}

	public String getGoalUserId() {
		return goalUserId;
	}

	public void setGoalUserId(String goalUserId) {
		this.goalUserId = goalUserId;
	}

	public String getGoalRegDate() {
		return goalRegDate;
	}

	public void setGoalRegDate(String goalRegDate) {
		this.goalRegDate = goalRegDate;
	}

	public String getGoalContent() {
		return goalContent;
	}

	public void setGoalContent(String goalContent) {
		this.goalContent = goalContent;
	}

	@Override
	public String toString() {
		return "Goal [goalId=" + goalId + ", goalUserId=" + goalUserId + ", goalRegDate=" + goalRegDate
				+ ", goalContent=" + goalContent + "]";
	}
	
}
