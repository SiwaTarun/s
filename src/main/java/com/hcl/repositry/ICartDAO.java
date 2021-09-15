package com.hcl.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcl.entity.Cart;
import com.hcl.entity.User;

public interface ICartDAO extends JpaRepository<Cart, Integer> {

	@Query(value = "select * from cart where user_id=?1", nativeQuery = true)
	public List<Cart> findByUserId(int user_id);

	public void deleteByUser(User user);

	@Query(value = "select * from cart where product_id=?1 and user_id=?2", nativeQuery = true)
	public List<Cart> findByProductIdAndUserId(int productId, int user_id);

}
