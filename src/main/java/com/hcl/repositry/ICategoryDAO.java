package com.hcl.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.entity.Category;

@Repository
public interface ICategoryDAO extends JpaRepository<Category, Integer> {
}
