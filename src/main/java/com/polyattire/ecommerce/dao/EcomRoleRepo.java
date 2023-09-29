package com.polyattire.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polyattire.ecommerce.entity.EcomRole;

@Repository
public interface EcomRoleRepo extends JpaRepository<EcomRole, Long>{
	
	EcomRole findByName(String name);
}
