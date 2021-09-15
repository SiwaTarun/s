package com.hcl.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcl.entity.Orders;

public interface IOrderDAO extends JpaRepository<Orders, Integer> {
	@Query(value = "select * from orders where user_user_id=?1", nativeQuery = true)
	public List<Orders> findByUserId(int user_id);
}
