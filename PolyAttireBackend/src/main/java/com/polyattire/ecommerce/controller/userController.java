package com.polyattire.ecommerce.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.polyattire.ecommerce.entity.EcomRole;
import com.polyattire.ecommerce.entity.EcomUser;
import com.polyattire.ecommerce.service.UserService;

import lombok.Data;

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
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
		return ResponseEntity.created(uri).body(service.saveUser(ecomUser));	
	}
	
	@PostMapping(path = "/api/role/save",consumes = "application/json", produces = "application/json")
	public ResponseEntity<EcomRole> saveRole(@RequestBody EcomRole ecomRole)
	{
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
		return ResponseEntity.created(uri).body(service.saveRole(ecomRole));
	}
	
	@PostMapping(path = "/role/addroletouser",consumes = "application/json")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
		service.attachRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
	
	@Data
	class RoleToUserForm{
		private String username;
		private String roleName;
	}
}
