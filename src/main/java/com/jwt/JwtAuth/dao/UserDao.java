package com.jwt.JwtAuth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.JwtAuth.entities.JwtRequest;

public interface UserDao extends JpaRepository<JwtRequest, Integer> {

	JwtRequest findByUsername(String userName);
	
}
