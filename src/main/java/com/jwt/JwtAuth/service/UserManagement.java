package com.jwt.JwtAuth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.JwtAuth.dao.UserDao;
import com.jwt.JwtAuth.entities.JwtRequest;


@Service
public class UserManagement {

	@Autowired
	private UserDao userDao;
	
	public JwtRequest saveUserInfo(JwtRequest u) {
		userDao.save(u);
		return u;
	}
	
}
