package com.fitdo.model.dto;

public class Todo {

	private int todoId;
	private int todoGoalId; //외래키
	private String todoContent;
	private boolean todoState;
	
	public Todo() {
	}

	public Todo(int todoId, int todoGoalId, String todoContent, boolean todoState) {
		super();
		this.todoId = todoId;
		this.todoGoalId = todoGoalId;
		this.todoContent = todoContent;
		this.todoState = todoState;
	}

	public int getTodoId() {
		return todoId;
	}

	public void setTodoId(int todoId) {
		this.todoId = todoId;
	}

	public int getTodoGoalId() {
		return todoGoalId;
	}

	public void setTodoGoalId(int todoGoalId) {
		this.todoGoalId = todoGoalId;
	}

	public String getTodoContent() {
		return todoContent;
	}

	public void setTodoContent(String todoContent) {
		this.todoContent = todoContent;
	}

	public boolean isTodoState() {
		return todoState;
	}

	public void setTodoState(boolean todoState) {
		this.todoState = todoState;
	}

	@Override
	public String toString() {
		return "Todo [todoId=" + todoId + ", todoContent=" + todoContent + ", todoState=" + todoState + "]";
	}

}
