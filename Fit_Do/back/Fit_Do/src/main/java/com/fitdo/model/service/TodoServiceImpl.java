package com.fitdo.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitdo.model.dao.TodoDao;
import com.fitdo.model.dto.Todo;

@Service
public class TodoServiceImpl implements TodoService{

	private final TodoDao todoDao;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public TodoServiceImpl(TodoDao todoDao) {
		super();
		this.todoDao = todoDao;
	}

	@Override
	public List<Todo> selectAllTodo(int todoGoalId) {
		return todoDao.selectAllTodo(todoGoalId);
	}
	
	@Override
	public Todo selectTodo(int todoId) {
		return todoDao.selectTodo(todoId);
	}

	@Override
	public int createTodo(Todo todo) {
		return todoDao.createTodo(todo);
	}

	@Override
	public int updateTodo(Todo todo) {
		return todoDao.updateTodo(todo);
	}

	@Override
	public int deleteTodo(int todoId) {
		return todoDao.deleteTodo(todoId);
	}

	@Override
	public void changeState(int todoId) {
		Todo todo = todoDao.selectTodo(todoId);
		if(todo!=null) {
	        todo.setTodoState(!todo.isTodoState());
	        todoDao.updateTodo(todo);
		}else {
			System.out.println("해당 할 일이 존재하지 않습니다!");
		}
	}

	@Override
	public boolean isChangable(String date) {
		try {
            Date inputDate = dateFormat.parse(date); // 수정할 날짜
            Date currentDate = new Date(); // 오늘 날짜
            return !inputDate.before(dateFormat.parse(dateFormat.format(currentDate)));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
	}

}
