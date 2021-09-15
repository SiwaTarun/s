package com.hcl.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.entity.User;

@Repository
public interface IUserDAO extends JpaRepository<User, Integer> {
	User findByEmailAndPassword(String email, String password);

	User findByEmail(String email);

}
