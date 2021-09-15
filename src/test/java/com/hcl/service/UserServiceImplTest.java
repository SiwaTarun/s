package com.hcl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.hcl.dto.UserRequestDto;
import com.hcl.entity.User;
import com.hcl.exception.UserDefinedException;
import com.hcl.repositry.IUserDAO;
import com.hcl.services.Impl.UserSeviceImpl;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceImplTest {

	@Mock
	IUserDAO userDAO;;

	@InjectMocks
	UserSeviceImpl userSeviceImpl;

	static UserRequestDto userRequestDto;
	static User user;
	static List<User> userList;

	@BeforeAll
	public static void setUp() {
		userRequestDto = new UserRequestDto();
		userRequestDto.setEmail("rama@gmail.com");
		userRequestDto.setPassword("rama");
		userRequestDto.setPhoneNumber("9090909090");
		userRequestDto.setUserName("RamaDevi");

		user = new User();
		user.setUserId(1);
		user.setEmail("rama@gmail.com");
		user.setPassword("rama");
		user.setPhoneNumber("9090909090");
		user.setUserName("RamaDevi");

		userList = new ArrayList<>();
		userList.add(user);
	}

	@Test
	@Order(1)
	@DisplayName("Save UserDetails:Positive Scenario")
	public void saveUserDetailsTest() throws UserDefinedException {
		when(userDAO.findByEmail(userRequestDto.getEmail())).thenReturn(null);
		when(userDAO.save(any(User.class))).thenAnswer(i -> {
			user = i.getArgument(0);
			user.setUserId(1);
			return user;
		});

		String result = userSeviceImpl.addUser(userRequestDto);
		verify(userDAO).save(user);
		assertEquals("User added successfuly.....", result);
	}

	@Test
	@Order(2)
	@DisplayName("Save UserDetails: Negative Scenario")
	public void saveuserDetailsTest1() throws UserDefinedException {
		when(userDAO.findByEmail(userRequestDto.getEmail())).thenReturn(user);
		assertThrows(UserDefinedException.class, () -> userSeviceImpl.addUser(userRequestDto));
	}

	@Test
	@Order(3)
	@DisplayName("Get all User Details:Positive Scenario")
	public void getAllUsers() {
		when(userDAO.findAll()).thenReturn(userList);
		List<User> result = userSeviceImpl.displayAllUsers();
		verify(userDAO).findAll();
		assertEquals(userList, result);

	}

	@Test
	@Order(4)
	@DisplayName("Login check:Positive Scenario")
	public void login1() throws UserDefinedException {
		when(userDAO.findByEmailAndPassword("rama@gmail.com", "rama")).thenReturn(user);
		String result = userSeviceImpl.login("rama@gmail.com", "rama");
		verify(userDAO).findByEmailAndPassword("rama@gmail.com", "rama");
		assertEquals("Login success", result);
	}

	@Test
	@Order(5)
	@DisplayName("Login check: Negative Scenario")
	public void login2() throws UserDefinedException {
		when(userDAO.findByEmailAndPassword("rama@gmail.com", "rama")).thenThrow(UserDefinedException.class);
		assertThrows(UserDefinedException.class, () -> userSeviceImpl.login("rama@gmail.com", "rama"));
	}

}
