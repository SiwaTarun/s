package com.hcl.services.Impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.dto.AddCategoryRequestDto;
import com.hcl.entity.Category;
import com.hcl.repositry.ICategoryDAO;
import com.hcl.services.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ICategoryDAO categoryDAO;

	@Override
	public Category addCategory(AddCategoryRequestDto addCategoryRequestDto) {
		Category category = new Category();
		BeanUtils.copyProperties(addCategoryRequestDto, category);

		return categoryDAO.saveAndFlush(category);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryDAO.findAll();
	}

}
