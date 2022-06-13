package com.polyattire.ecommerce.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.polyattire.ecommerce.entity.ProductDetailed;

@Repository
public interface ProductDetailedRepository extends JpaRepository<ProductDetailed, Long>{

	@Query(value = "select * from product_detailed pd where ( pd.product_id in(select id from product where category_id = ?1))", nativeQuery = true)
	List<ProductDetailed> findAllByCategoryId(long categoryId,Pageable pageable);
	
}
