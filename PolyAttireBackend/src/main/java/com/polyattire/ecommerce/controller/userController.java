package com.polyattire.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.polyattire.ecommerce.entity.EcomRole;
import com.polyattire.ecommerce.entity.EcomUser;
import com.polyattire.ecommerce.service.UserService;

@RestController
@CrossOrigin("http://localhost:4200")
public class userController {

	@Autowired
	UserService service;

	@GetMapping("/api/users")
	public ResponseEntity<List<EcomUser>> getUsers()
	{
		return new ResponseEntity<List<EcomUser>>(service.getUser(),null,200);
	}
	
	@PostMapping(path = "/api/user/save",consumes = "application/json", produces = "application/json")
	public ResponseEntity<EcomUser> saveUser(@RequestBody EcomUser ecomUser)
	{
		return new ResponseEntity<EcomUser>(service.saveUser(ecomUser),null,201);
	}
	
	@PostMapping(path = "/api/role/save",consumes = "application/json", produces = "application/json")
	public ResponseEntity<EcomRole> saveRole(@RequestBody EcomRole ecomRole)
	{
		return new ResponseEntity<EcomRole>(service.saveRole(ecomRole),null,201);
	}
}
