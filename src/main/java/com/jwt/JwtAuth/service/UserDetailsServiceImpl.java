package com.jwt.JwtAuth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.JwtAuth.dao.UserDao;
import com.jwt.JwtAuth.entities.JwtRequest;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		JwtRequest user = userDao.findByUsername(username);
		if(user==null) { 
			throw new UsernameNotFoundException("User Not Found"); 
			}
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

}
