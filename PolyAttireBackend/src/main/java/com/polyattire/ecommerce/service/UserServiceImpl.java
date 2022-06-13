package com.polyattire.ecommerce.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polyattire.ecommerce.dao.EcomRoleRepo;
import com.polyattire.ecommerce.dao.EcomUserRepo;
import com.polyattire.ecommerce.entity.EcomRole;
import com.polyattire.ecommerce.entity.EcomUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
	
	@Autowired
	private EcomUserRepo userRepo;
	
	@Autowired
	private EcomRoleRepo roleRepo;
	
	@Override
	public EcomUser saveUser(EcomUser user) {
		log.info("saving new user {} to the database",user.getEmail());
		return userRepo.save(user);
	}

	@Override
	public EcomRole saveRole(EcomRole role) {
		return roleRepo.save(role);
	}

	@Override
	public void attachRoleToUser(String username, String roleName) {
		EcomUser user = userRepo.findByEmail(username);
		user.getRoles().add(roleRepo.findByName(roleName));
		
	}

	@Override
	public EcomUser getUserByUsername(String username) {
		return userRepo.findByEmail(username);
	}

	@Override
	public List<EcomUser> getUser() {
		return userRepo.findAll();//implement paging
	}

}
