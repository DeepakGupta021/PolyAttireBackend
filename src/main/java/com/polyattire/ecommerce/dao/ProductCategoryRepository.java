package com.polyattire.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polyattire.ecommerce.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{

}
