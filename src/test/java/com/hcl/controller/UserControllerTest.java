package com.hcl.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.dto.LoginRequestDto;
import com.hcl.dto.UserRequestDto;
import com.hcl.entity.User;
import com.hcl.exception.UserDefinedException;
import com.hcl.services.IUserService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest {

	@Mock
	IUserService userService;

	@InjectMocks
	UserController userController;

	static UserRequestDto userRequestDto;
	static LoginRequestDto loginRequestDto;
	static List<User> userList;
	static User user;

	@BeforeAll
	public static void setUp() {
		userRequestDto = new UserRequestDto();
		userRequestDto.setEmail("rama@gmail.com");
		userRequestDto.setPassword("rama");
		userRequestDto.setUserName("Ramadevi");
		userRequestDto.setPhoneNumber("9090909090");

		loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmail("rama@gmail.com");
		loginRequestDto.setPassword("rama");

		user = new User();
		user.setUserId(1);
		user.setUserName("Teja");
		user.setPhoneNumber("8989898989");
		user.setEmail("teja@gmail.com");
		user.setPassword("teja");

		userList = new ArrayList<User>();
		userList.add(user);

	}

	@Test
	@Order(1)
	@DisplayName("User Registration:Positive Scenario")
	public void userRegistrationTest1() throws UserDefinedException {

		when(userService.addUser(userRequestDto)).thenReturn("Registered Successfully");

		ResponseEntity<String> result = userController.saveUser(userRequestDto);

		verify(userService).addUser(userRequestDto);
		assertEquals("Registered Successfully", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@Order(2)
	@DisplayName("user Registration:Negative Scenario")
	public void userRegistrationTest2() throws UserDefinedException {

		when(userService.addUser(userRequestDto)).thenThrow(UserDefinedException.class);
		assertThrows(UserDefinedException.class, () -> userController.saveUser(userRequestDto));

	}

	@Test
	@DisplayName("Login Function: Positive Scenario")
	public void loginTest() throws UserDefinedException {

		when(userService.login("rama@gmail.com", "rama")).thenReturn("Login success");
		ResponseEntity<String> result = userController.login(loginRequestDto);
		assertEquals("Login success", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@DisplayName("Login Function: Negative Scenario")
	public void loginTest2() throws UserDefinedException {

		when(userService.login("rama@gmail.com", "rama")).thenThrow(UserDefinedException.class);
		assertThrows(UserDefinedException.class, () -> userController.login(loginRequestDto));
	}

	@Test
	@Order(3)
	@DisplayName("All  User details:Positive Scenario")
	public void getUserDetailsTest1() {

		when(userService.displayAllUsers()).thenReturn(userList);
		List<User> result = userController.displayUsers();
		verify(userService).displayAllUsers();
		assertEquals(userList, result);
	}
}
