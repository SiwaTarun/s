package com.hcl.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryRequestDto {
	@NotNull(message = "Name cannot be null")
	private String name;

	@NotNull(message = "Description cannot be null")
	private String description;
}
