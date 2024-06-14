package com.fitdo.model.service;

import java.util.List;

import com.fitdo.model.dto.Todo;

public interface TodoService {

	// 목표 별 할 일 전제조회
	public List<Todo> selectAllTodo(int todoGoalId);

	// todoId로 할 일 조회
	public Todo selectTodo(int todoId);
	
	// 할 일 등록
	public int createTodo(Todo todo);

	// 할 일 수정
	public int updateTodo(Todo todo);

	// 할 일 삭제
	public int deleteTodo(int todoId);

	// 할 일 상태 변경
	public void changeState(int todoId);
	
	// 날짜 비교
	public boolean isChangable(String date);
}
