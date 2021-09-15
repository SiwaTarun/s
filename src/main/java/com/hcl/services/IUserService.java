package com.hcl.services;

import java.util.List;

import com.hcl.dto.UserRequestDto;
import com.hcl.entity.User;
import com.hcl.exception.UserDefinedException;

public interface IUserService {
	String addUser(UserRequestDto userRequestDto) throws UserDefinedException;

	List<User> displayAllUsers();

	String login(String email, String password) throws UserDefinedException;

}
