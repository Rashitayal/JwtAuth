package com.jwt.JwtAuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.JwtAuth.entities.JwtRequest;
import com.jwt.JwtAuth.entities.JwtResponse;
import com.jwt.JwtAuth.service.UserDetailsServiceImpl;
import com.jwt.JwtAuth.service.UserManagement;
import com.jwt.JwtAuth.util.JwtUtil;

@RestController
public class UserHandler {
	
	@Autowired
	private UserManagement userManagement;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/signup")
	public ResponseEntity<?> register(@RequestBody JwtRequest user){
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if(userManagement.saveUserInfo(user) != null) {
			UserDetails userDetails =  this.userDetailsServiceImpl.loadUserByUsername(user.getUsername());
			String token = jwtUtil.generateToken(userDetails);
			return ResponseEntity.ok(new JwtResponse(token));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/user/name")
	public ResponseEntity<?> getUserNameByToken(@RequestHeader (name="Authorization") String token){
		String username = jwtUtil.extractUsername(token.substring(7));
		UserDetails userDetails =  this.userDetailsServiceImpl.loadUserByUsername(username);
		if(jwtUtil.validateToken(token.substring(7), userDetails)) {
			return ResponseEntity.ok(new JwtRequest(username,""));
		}
		return ResponseEntity.badRequest().build();
	}
		
}
