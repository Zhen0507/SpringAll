package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;

@Tag(name = "用户Controller")
@Controller
@RequestMapping("user")
public class UserController {

	@Hidden
	@GetMapping("hello")
	public @ResponseBody String hello() {
		return "hello";
	}

	@Operation(summary = "获取用户信息", description = "根据用户id获取用户信息")
	@GetMapping("/{id}")
	public @ResponseBody User getUserById(@Parameter(description = "用户id") @PathVariable(value = "id") Long id) {
		User user = new User();
		user.setId(id);
		user.setName("mrbird");
		user.setAge(25);
		return user;
	}

	@Operation(summary = "获取用户列表", description = "获取用户列表")
	@GetMapping("/list")
	public @ResponseBody List<User> getUserList() {
		List<User> list = new ArrayList<>();
		User user1 = new User();
		user1.setId(1l);
		user1.setName("mrbird");
		user1.setAge(25);
		list.add(user1);
		User user2 = new User();
		user2.setId(2l);
		user2.setName("scott");
		user2.setAge(29);
		list.add(user2);
		return list;
	}

	@Operation(summary = "新增用户", description = "根据用户实体创建用户")
	@PostMapping("/add")
	public @ResponseBody Map<String, Object> addUser(@Parameter(description = "用户实体") @RequestBody User user) {
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}

	@Operation(summary = "删除用户", description = "根据用户id删除用户")
	@DeleteMapping("/{id}")
	public @ResponseBody Map<String, Object> deleteUser(@Parameter(description = "用户id") @PathVariable(value = "id") Long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}

	@Operation(summary = "更新用户", description = "根据用户id更新用户")
	@PutMapping("/{id}")
	public @ResponseBody Map<String, Object> updateUser(
			@Parameter(description = "用户id") @PathVariable(value = "id") Long id, 
			@Parameter(description = "用户实体") @RequestBody User user) {
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
}
