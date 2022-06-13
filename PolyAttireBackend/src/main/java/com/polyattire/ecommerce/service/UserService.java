package com.polyattire.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.polyattire.ecommerce.entity.EcomRole;
import com.polyattire.ecommerce.entity.EcomUser;

@Service
public interface UserService {
	
	EcomUser saveUser(EcomUser user);
	
	EcomRole saveRole(EcomRole role);
	
	void attachRoleToUser(String username, String roleName);
	
	EcomUser getUserByUsername(String username);
	
	List<EcomUser> getUser();//implement pagination
}
