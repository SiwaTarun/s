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

import com.hcl.dto.AddCategoryRequestDto;
import com.hcl.entity.Category;
import com.hcl.services.ICategoryService;

@RestController
@RequestMapping("categories")
public class CategoryController {
	@Autowired
	private ICategoryService categoryServices;

	@PostMapping
	public ResponseEntity<Category> saveCategory(@Valid @RequestBody AddCategoryRequestDto addCategoryRequestDto) {
		return new ResponseEntity<Category>(categoryServices.addCategory(addCategoryRequestDto), HttpStatus.OK);
	}

	@GetMapping
	public List<Category> displayCategories() {
		List<Category> ls = new ArrayList<Category>();
		ls = categoryServices.getAllCategories();
		return ls;
	}
}
