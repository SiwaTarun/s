package com.hcl.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
	@Email(message = "Email should be valid")
	@NotEmpty(message = "Email cannot be Empty")
	private String email;

	@NotEmpty(message = "Password cannot be empty")
	private String password;
}
