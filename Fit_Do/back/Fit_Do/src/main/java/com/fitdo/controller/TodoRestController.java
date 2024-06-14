package com.fitdo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fitdo.model.dto.Todo;
import com.fitdo.model.service.GoalService;
import com.fitdo.model.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/todo")
@Tag(name = "TodoRestController", description = "할 일 관리")
@CrossOrigin("*")
public class TodoRestController {

	@Autowired
	private TodoService ts;
	
	@Autowired
	private GoalService gs;

	// 목표별 할 일 전체 조회
	@GetMapping("")
	@Operation(summary = "목표 별 할 일 전체 조회")
	public ResponseEntity<List<Todo>> selectAllTodosByGoalId(@RequestParam("todoGoalId") int todoGoalId) {
		List<Todo> todos = ts.selectAllTodo(todoGoalId);
		if (!todos.isEmpty()) {
			return new ResponseEntity<>(todos, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// 할 일 등록
	@PostMapping("")
	@Operation(summary = "할 일 등록")
	public ResponseEntity<String> createTodo(@RequestBody Todo todo) {
		int result = ts.createTodo(todo);
		if (result > 0) {
			return new ResponseEntity<>("새로운 할 일 추가!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("할 일 등록 실패", HttpStatus.BAD_REQUEST);
		}
	}

	// 할 일 수정
	@PutMapping("/{todoId}")
	@Operation(summary = "할 일 수정")
	public ResponseEntity<String> updateTodo(@PathVariable int todoId, @RequestBody Todo todo) {
		todo.setTodoId(todoId);
		Todo existingTodo = ts.selectTodo(todoId);
		
	    if (existingTodo == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정할 할 일 없음");
	    }
	    
	    int goalId = existingTodo.getTodoGoalId();
	    String goalRegDate = gs.getGoalById(goalId).getGoalRegDate();

	    if (!gs.isUpdatable(goalRegDate)) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("과거 날짜의 할 일은 수정할 수 없음");
	    }

	    int result = ts.updateTodo(todo);
	    
	    if (result > 0) {
	        return new ResponseEntity<>("할 일 수정!", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("할 일 수정 실패", HttpStatus.BAD_REQUEST);
	    }
	}

	// 할 일 삭제
	@DeleteMapping("/{todoId}")
	@Operation(summary = "할 일 삭제")
	public ResponseEntity<String> deleteTodo(@PathVariable int todoId) {
		Todo todo = ts.selectTodo(todoId);

	    if (todo == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 할 일 없음");
	    }
		
		String regDate = gs.getGoalById(ts.selectTodo(todoId).getTodoGoalId()).getGoalRegDate();
		
		if (!gs.isUpdatable(gs.getGoalById(ts.selectTodo(todoId).getTodoGoalId()).getGoalRegDate())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("과거 날짜의 할 일은 삭제할 수 없음");
		}
		
		int result = ts.deleteTodo(todoId);
		if (result > 0) {
			return new ResponseEntity<>("할 일이 삭제!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("할 일 삭제 실패", HttpStatus.BAD_REQUEST);
		}
	}

	// 할 일 상태 변경, true : 1, false : 0
	@PutMapping("/{todoId}/changeState")
	@Operation(summary = "할 일 상태변경, 1 : true/ 0 : false")
	public ResponseEntity<?> changeTodoState(@PathVariable int todoId) {
		try {
			ts.changeState(todoId);
			return new ResponseEntity<>("할 일 상태가 변경!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("할 일 상태 변경 실패", HttpStatus.BAD_REQUEST);
		}
	}
}
