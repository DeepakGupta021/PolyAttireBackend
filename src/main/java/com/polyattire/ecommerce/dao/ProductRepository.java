package com.polyattire.ecommerce.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polyattire.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

//	@Query(value = "select * from product pd where category_id = ?1)", nativeQuery = true)
//	List<Product> findAllByCategoryId(long categoryId);
	
	Page<Product> findAllByCategory_Id(long categoryId, Pageable page);
}
