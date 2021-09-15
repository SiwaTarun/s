package com.hcl.services;

import java.util.List;

import com.hcl.dto.AddCategoryRequestDto;
import com.hcl.entity.Category;

public interface ICategoryService {
	Category addCategory(AddCategoryRequestDto addCategoryRequestDto);

	List<Category> getAllCategories();
}
