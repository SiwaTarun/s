package com.hcl.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.entity.Product;

@Repository
public interface IProductDAO extends JpaRepository<Product, Integer> {

	public List<Product> findByProductNameStartingWith(String productName);

	public List<Product> findByProductCategoryStartingWith(String productCategory);

	@Query(value = "select product_price from product where product_id=?1", nativeQuery = true)
	public double findByProductId(int productId);

}
