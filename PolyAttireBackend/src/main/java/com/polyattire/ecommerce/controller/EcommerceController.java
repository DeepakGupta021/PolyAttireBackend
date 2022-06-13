package com.polyattire.ecommerce.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polyattire.ecommerce.bean.DetailedProductBean;
import com.polyattire.ecommerce.service.Service;

@RestController
@CrossOrigin("http://localhost:4200")
public class EcommerceController {

	@Autowired
	Service service;
	
	@RequestMapping("/")    
	public String index(){    
		return "index";    
	}  
	
	@PostMapping(path = "api/addproduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Object> addproduct(@RequestBody Map<String, Object> payload)
	{
		//add validation
		Map<String, Object> response = new HashMap<>();
		Long id;
		try {
			
			id = service.addProduct(payload);
			response.put("statusDescription", "Added Succesfully");
			response.put("Id", id);
			
		} catch (Exception e) {
			//add logs
			System.out.println(e.getMessage());
		}
		
		
		return new ResponseEntity<Object>(response, null, 200);
	}
	
	@PostMapping(path = "api/adddetailedproduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Object> addDetailedproduct(@RequestBody Map<String, Object> payload)
	{
		//add validation
		Map<String, Object> response = new HashMap<>();
		Long id;
		try {
			
			id = service.addDetailedProduct(payload);
			response.put("statusDescription", "Added Succesfully");
			response.put("Id", id);
			
		} catch (Exception e) {
			//add logs
			System.out.println(e.getMessage());
		}
		
		
		return new ResponseEntity<Object>(response, null, 200);
	}
	
	@GetMapping(path = {"api/getAllProducts","api/getAllProducts/{catId}"}, produces = "application/json" )
	public ResponseEntity<Object> getAllProducts(@PathVariable(required = false) Long catId,Pageable pageable)
	{
		Map<String,Object> response = service.getAllProductsBean(catId,pageable);
	
		return new ResponseEntity<Object>(response,null,200);
		
	}
	
	@GetMapping(path = "api/getproduct/{id}", produces = "application/json")
	public ResponseEntity<Object> getProductById(@PathVariable Long id)
	{
		DetailedProductBean bean = service.getProductById(id);
		
		return new ResponseEntity<Object>(bean,null,200);
	}
	
//	@GetMapping(path = "api/getproductbyid/{id}", produces = "application/json")
//	public ResponseEntity<Object> getProductById(@PathVariable Long id){
//		ProductBean product= service.getProductBean(id);
//		return new ResponseEntity<Object>(product, null, 200);
//	}
//	
//	@GetMapping(path = "api/getdetailedproductbyid/{id}", produces = "application/json")
//	public ResponseEntity<Object> getDetailedProductById(@PathVariable Long id){
//		ProductDetailed detailedProduct= service.getDetailedPorduct(id, true);
//		return new ResponseEntity<Object>(detailedProduct, null, 200);
//	}
//	
//	@GetMapping(path = "api/getAllProducts", produces = "application/json" )
//	public ResponseEntity<Object> getAllProducts()
//	{
//		List<ProductBean> products = service.getAllProductsBean();
//		Map<String, Object> response = new HashMap<String, Object>();
//		
//		response.put("products", products);
//		return new ResponseEntity<Object>(response,null,200);
//		
//	}
//	
//	@GetMapping(path = "api/getproductsbycategory/{catId}", produces = "application/json")
//	public ResponseEntity<Object> getProductsByCategory(@PathVariable Long catId){
//		List<ProductBean> products = service.getProductsByCategoryId(catId);
//		Map<String, Object> response = new HashMap<String, Object>();
//		
//		response.put("products", products);
//		return new ResponseEntity<Object>(response,null,200);
//		
//	}
	
	
}
