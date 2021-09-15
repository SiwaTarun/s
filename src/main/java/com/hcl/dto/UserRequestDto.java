package com.hcl.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

	@NotEmpty(message = "UserName cannot be Empty")
	private String userName;

	@NotEmpty(message = "phoneNumber cannot be Empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Provide valid Phone Number")
	private String phoneNumber;

	@Email(message = "Email should be valid")
	@NotEmpty(message = "Email cannot be null")
	private String email;

	@NotEmpty(message = "Password cannot be Empty")
	private String password;
}
