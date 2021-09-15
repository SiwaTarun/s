package com.hcl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.hcl.dto.AddCategoryRequestDto;
import com.hcl.entity.Category;
import com.hcl.repositry.ICategoryDAO;
import com.hcl.services.Impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryServiceImplTest {
	@Mock
	ICategoryDAO categoryDAO;

	@InjectMocks
	CategoryServiceImpl categoryServiceImpl;

	static AddCategoryRequestDto addCategoryRequestDto;
	static Category category;
	static List<Category> categoryList;

	@BeforeAll
	public static void setUp() {
		addCategoryRequestDto = new AddCategoryRequestDto();
		addCategoryRequestDto.setName("Furniture");
		addCategoryRequestDto.setDescription("All wooden,plastic materials");

		category = new Category();
		category.setCategoryId(3);
		category.setDescription("All wooden,plastic materials");
		category.setName("Furniture");

		categoryList = new ArrayList<Category>();
		categoryList.add(category);

	}

	@Test
	@Order(1)
	@DisplayName("Category Adding: Positive Scenario")
	public void addCategoryTest() {
		when(categoryDAO.saveAndFlush(any(Category.class))).thenAnswer(i -> {
			category = i.getArgument(0);
			category.setCategoryId(3);
			return category;
		});

		Category result = categoryServiceImpl.addCategory(addCategoryRequestDto);
		verify(categoryDAO).saveAndFlush(category);
		assertEquals(category, result);
	}

	@Test
	@Order(2)
	@DisplayName("All Category List: Positive Scenario")
	public void getAllCategoriesTest1() {
		when(categoryDAO.findAll()).thenReturn(categoryList);
		List<Category> result = categoryServiceImpl.getAllCategories();
		verify(categoryDAO).findAll();
		assertEquals(categoryList, result);

	}
}
