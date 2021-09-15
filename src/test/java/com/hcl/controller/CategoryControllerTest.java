package com.hcl.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.hcl.dto.AddCategoryRequestDto;
import com.hcl.entity.Category;
import com.hcl.exception.UserDefinedException;
import com.hcl.services.ICategoryService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryControllerTest {

	@Mock
	ICategoryService categoryServices;

	@InjectMocks
	CategoryController categoryController;

	static AddCategoryRequestDto addCategoryRequestDto;
	static Category category;
	static List<Category> categoriesList;

	@BeforeAll
	public static void setUp() {
		addCategoryRequestDto = new AddCategoryRequestDto();
		addCategoryRequestDto.setDescription("All electronic gadgets");
		addCategoryRequestDto.setName("Electonics");

		category = new Category();
		category.setCategoryId(1);
		category.setDescription("All electronic gadgets");
		category.setName("Electronics");

		categoriesList = new ArrayList<Category>();
		categoriesList.add(category);
	}

	@Test
	@Order(1)
	@DisplayName("Category Addition:Positive Scenario")
	public void categoryRegistration() throws UserDefinedException {

		when(categoryServices.addCategory(addCategoryRequestDto)).thenReturn(category);

		ResponseEntity<Category> result = categoryController.saveCategory(addCategoryRequestDto);

		verify(categoryServices).addCategory(addCategoryRequestDto);
		assertEquals(category, result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@Order(3)
	@DisplayName("All  Category details:Positive Scenario")
	public void getCategoryDetailsTest1() {

		when(categoryServices.getAllCategories()).thenReturn(categoriesList);
		List<Category> result = categoryController.displayCategories();
		verify(categoryServices).getAllCategories();
		assertEquals(categoriesList, result);
	}

}
