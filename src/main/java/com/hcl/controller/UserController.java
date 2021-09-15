package com.hcl.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.LoginRequestDto;
import com.hcl.dto.UserRequestDto;
import com.hcl.entity.User;
import com.hcl.exception.UserDefinedException;
import com.hcl.services.IUserService;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	IUserService userService;

	@PostMapping
	public ResponseEntity<String> saveUser(@Valid @RequestBody UserRequestDto userRequestDto)
			throws UserDefinedException {
		String response=userService.addUser(userRequestDto);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto ) 
			throws UserDefinedException {
		String response=userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());	
		 return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	@GetMapping
	public List<User> displayUsers() {
		List<User> ls = new ArrayList<User>();
		ls = userService.displayAllUsers();
		return ls;
	}
}
