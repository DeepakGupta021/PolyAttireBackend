package com.polyattire.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polyattire.ecommerce.entity.EcomUser;

@Repository
public interface EcomUserRepo extends JpaRepository<EcomUser, Long>{
	
	EcomUser findByEmail(String email);
	
}
