package com.polyattire.ecommerce.service;


import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.polyattire.ecommerce.bean.DetailedProductBean;

@org.springframework.stereotype.Service
public interface Service {

	Long addProduct(Map<String, Object> payload);

	Long addDetailedProduct(Map<String, Object> payload);
	
//	ProductDetailed getDetailedPorduct(Long id, boolean adminAccess);
//	
//	Product getProduct(Long id, boolean adminAccess);
//	
//	ProductBean getProductBean(Long id);
//	
	Map<String, Object> getAllProductsBean(Long id, Pageable pageable);
//	
//	List<ProductBean> getProductsByCategoryId(Long id);
//	
//	List<ProductBean> getAllProductsWithSorting(String field,boolean directionUp);

	DetailedProductBean getProductById(Long id);
}
