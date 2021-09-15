package com.hcl.services.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hcl.dto.UserRequestDto;
import com.hcl.entity.User;
import com.hcl.exception.UserDefinedException;
import com.hcl.repositry.IUserDAO;
import com.hcl.services.IUserService;

//@Service
@Component
public class UserSeviceImpl implements IUserService {
	@Autowired
	private IUserDAO userDAO;

	@Override
	public String addUser(UserRequestDto userRequestDto) throws UserDefinedException {
		User user = new User();
		if (userDAO.findByEmail(userRequestDto.getEmail()) == null) {
			BeanUtils.copyProperties(userRequestDto, user);
			userDAO.save(user);
			return "User added successfuly.....";
		} else {
			throw new UserDefinedException("Email already Exists....");
		}
	}

	@Override
	public List<User> displayAllUsers() {
		return userDAO.findAll();
	}

	@Override
	public String login(String email, String password) throws UserDefinedException {
		User user = userDAO.findByEmailAndPassword(email, password);
		if (user != null) {
			return "Login success";
		}
		throw new UserDefinedException("Invalid Credentials..Login failed and Try Again.");

	}
	
	

}
