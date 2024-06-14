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

import com.fitdo.model.dto.Goal;
import com.fitdo.model.service.GoalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/goal")
@Tag(name = "GoalRestController", description = "목표 관리")
@CrossOrigin("*")
public class GoalRestController {

	@Autowired
	private GoalService gs;

	// 게시물 별 목표 조회
	@GetMapping("/{goalUserId}/{goalRegDate}")
	@Operation(summary = "게시물 별 목표 조회")
	public ResponseEntity<List<Goal>> getGoals(@PathVariable String goalUserId, @PathVariable String goalRegDate) {
		List<Goal> goals = gs.getGoal(goalUserId, goalRegDate);
		if (!goals.isEmpty()) {
			return new ResponseEntity<>(goals, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	
	// 게시물 별 목표 존재여부
	@GetMapping("/isExist")
	@Operation(summary = "게시물 별 목표 존재 여부")
	public ResponseEntity<?> getExistingGoals(@RequestParam("goalUserId") String goalUserId,
			@RequestParam("goalRegDate") String goalRegDate) {
		if (gs.selectExistingGoal(goalUserId, goalRegDate)) {
			return new ResponseEntity<>("오늘의 목표가 이미 존재!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("오늘의 목표가 아직 없음!", HttpStatus.NOT_FOUND);
		}
	}

	
	// 목표 등록
	@PostMapping("")
	@Operation(summary = "목표 등록")
	public ResponseEntity<String> createGoal(@RequestBody Goal goal) {

		if (goal.getGoalRegDate() == null) {
			System.out.println("목표 등록 날짜가 null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("목표 등록 날짜가 null");
		}

		if (goal.getGoalRegDate().isEmpty()) {
			System.out.println("목표 등록 날짜가 빈 문자열");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("목표 등록 날짜가 빈 문자열");
		}

		if (!gs.isUpdatable(goal.getGoalRegDate())) {
			System.out.println("과거 날짜에는 목표는 등록할 수 없음");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("과거 날짜에는 목표는 등록할 수 없음");
		}

		int result = gs.createGoal(goal);
		if (result > 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body("목표 등록 성공!");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("목표 등록 실패");
		}
	}

	
	// 목표 수정
	@PutMapping("/{goalId}")
	@Operation(summary = "목표 수정")
	public ResponseEntity<String> updateGoal(@PathVariable int goalId, @RequestBody Goal goal) {
		goal.setGoalId(goalId);
		goal.setGoalRegDate(gs.getGoalById(goalId).getGoalRegDate());

		if (!gs.isUpdatable(gs.getGoalById(goalId).getGoalRegDate())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("과거 날짜의 목표는 수정할 수 없습니다.");
		}

		try {
			int result = gs.updateGoal(goal);
			if (result > 0) {
				return ResponseEntity.ok("목표 수정 성공!");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("목표 수정 실패");
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	
	// 목표 삭제
	@DeleteMapping("/{goalId}")
	@Operation(summary = "목표 삭제")
	public ResponseEntity<String> deleteGoal(@PathVariable int goalId) {
		int result = gs.deleteGoal(goalId);
		if (result > 0) {
			return ResponseEntity.ok("목표 삭제 성공!");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("목표 삭제 실패");
		}
	}
}
