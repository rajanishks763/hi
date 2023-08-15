package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.antity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByLoginId(String loginId);
	

}
