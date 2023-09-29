package com.polyattire.ecommerce.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.polyattire.ecommerce.dao.EcomRoleRepo;
import com.polyattire.ecommerce.dao.EcomUserRepo;
import com.polyattire.ecommerce.entity.EcomRole;
import com.polyattire.ecommerce.entity.EcomUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService{
	
	@Autowired
	private EcomUserRepo userRepo;
	
	@Autowired
	private EcomRoleRepo roleRepo;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		EcomUser user =  userRepo.findByEmail(email);
		
		if(user==null) {
			log.error("User not found in the database");
			throw new UsernameNotFoundException("User not found in the database");
		}else {
			log.info("User found in the database {}",email);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		user.getRoles().forEach(role->{
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		
		return new User(user.getEmail(),user.getPassword(),authorities);
	}
	
	@Override
	public EcomUser saveUser(EcomUser user) {
		log.info("saving new user {} to the database",user.getEmail());
		
		if(userRepo.findByEmail(user.getEmail())!=null) {
			log.info("user {} with given email already exists",user.getEmail());
			return null;
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public EcomRole saveRole(EcomRole role) {
		
		if(roleRepo.findByName(role.getName())!=null) {
			log.info("role {} with given role name already exists",role.getName());
			return null;
		}
		
		return roleRepo.save(role);
	}

	@Override
	public void attachRoleToUser(String username, String roleName) {
		EcomUser user = userRepo.findByEmail(username);
		
		if(!user.getRoles().contains(roleRepo.findByName(roleName)))
		{
			user.getRoles().add(roleRepo.findByName(roleName));
		}
		
		
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
