package com.polyattire.ecommerce.service;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.polyattire.ecommerce.bean.DetailedProductBean;
import com.polyattire.ecommerce.bean.PageBean;
import com.polyattire.ecommerce.bean.ProductBean;
import com.polyattire.ecommerce.dao.ProductCategoryRepository;
import com.polyattire.ecommerce.dao.ProductDetailedRepository;
import com.polyattire.ecommerce.dao.ProductRepository;
import com.polyattire.ecommerce.entity.Product;
import com.polyattire.ecommerce.entity.ProductDetailed;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

	@Autowired
	ProductDetailedRepository detailedProductRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	ProductCategoryRepository productCategoryRepo;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long addProduct(Map<String, Object> payload) {
		
		Product product = new Product();
		
		product.setTitle((String) payload.get("title"));
		product.setSku((String) payload.get("sku"));
		product.setDescriptiion((String) payload.get("description"));
		product.setActive((boolean) payload.get("active"));
		product.setMrp(BigDecimal.valueOf((Double)payload.get("MRP")));
		product.setListPrice((BigDecimal.valueOf((Double)payload.get("list_price"))));
		product.setCategory(productCategoryRepo.getById(Long.valueOf((Integer)payload.get("category_id" ))));
		product.setUnitsSold(Long.valueOf((Integer)payload.get("units_sold" )));
		product.setRating(BigDecimal.valueOf((Double)payload.get("rating")));
		product.setColor((String) payload.get("color"));
		product.setDisplayImage((String) payload.get("display_image"));
		product.setImageArray((String) payload.get("image_array" ));
		product.setTags((String) payload.get("tags"));
		product= productRepo.save(product);
		
		for (Map<String, Object> map : (List<Map<String, Object>>) payload.get("product_detailed")) {
			ProductDetailed productDetailed = new ProductDetailed();
			productDetailed.setUnitPrice(BigDecimal.valueOf((Double) map.get("unit_price")));
			productDetailed.setActive((Boolean) map.get("active"));
			productDetailed.setUnitsInStock((int) map.get("units_in_stock"));
			productDetailed.setSize((String) map.get("size"));
			productDetailed.setProduct(product);
			detailedProductRepo.save(productDetailed);
		}
		
		return product.getId();
	}
	
	@Override
	public Long addDetailedProduct(Map<String, Object> payload) {
		ProductDetailed productDetailed = new ProductDetailed();
		productDetailed.setUnitPrice(BigDecimal.valueOf((Double) payload.get("unit_price")));
		productDetailed.setActive((Boolean) payload.get("active" ));
		productDetailed.setUnitsInStock((int) payload.get("units_in_stock"));
		productDetailed.setSize((String) payload.get("size"));
		productDetailed.setProduct(productRepo.getById(Long.valueOf((Integer)payload.get("product_id"))));
		detailedProductRepo.save(productDetailed);
		return productDetailed.getId();
	}
	
	@Override
	public Map<String,Object> getAllProductsBean(Long id, Pageable pageable) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProductBean> beans = new ArrayList<ProductBean>();
		Page<Product> page;
		if(id!=null)
			page = productRepo.findAllByCategory_Id(id,pageable);
		else
			page = productRepo.findAll(pageable);
		
		PageBean pageBean = new PageBean();
		pageBean.setNumber(page.getNumber());
		pageBean.setSize(page.getSize());
		pageBean.setTotalElements(page.getTotalElements());
		pageBean.setTotalPages(page.getTotalPages());
				
		for (Product product : page.getContent()) {
			beans.add(convertToProductBean(product));
		}
		
		result.put("products", beans);
		result.put("page",pageBean);
		
		return result;
	}
	
	@Override
	public DetailedProductBean getProductById(Long id) {
		Product product = productRepo.getById(id);
		return convertToDetailedProductBean(product);
	}
	
//	@Override
//	public ProductDetailed getDetailedPorduct(Long id, boolean adminAccess) {
//		if(adminAccess)
//		return detailedProductRepo.getById(id);
//		
//		return null;
//	}
//
//	@Override
//	public Product getProduct(Long id, boolean adminAccess) {
//
//		if(adminAccess)
//		return productRepo.getById(id);
//		
//		return null;
//	}

//	@Override
//	public ProductBean getProductBean(Long id) {
//		ProductDetailed detailedProduct = detailedProductRepo.getById(id);
//		return convertToProductBean(detailedProduct);
//	}
//
//	@Override
//	public List<ProductBean> getAllProductsBean() {
//		List<ProductBean> productBeans=new ArrayList<ProductBean>();
//		
//		for (ProductDetailed detailedProduct : detailedProductRepo.findAll()) {
//			productBeans.add(convertToProductBean(detailedProduct));
//		}
//		return productBeans;
//	}
//	
//	@Override
//	public List<ProductBean> getAllProductsWithSorting(String field, boolean directionUp) {
//		Direction sortDirection = directionUp? Sort.Direction.ASC : Sort.Direction.DESC;
//		List<ProductBean> productBeans=new ArrayList<ProductBean>();
//		
//		
//		for (ProductDetailed detailedProduct : detailedProductRepo.findAll(Sort.by(sortDirection,field))) {
//			productBeans.add(convertToProductBean(detailedProduct));
//		}
//		return productBeans;
//	}
	
//	@Override
//	public List<ProductBean> getProductsByCategoryId(Long id) {
//		List<ProductBean> productBeans=new ArrayList<ProductBean>();
//		
//		for (ProductDetailed detailedProduct : detailedProductRepo.findAllByCategoryId(id)) {
//			productBeans.add(convertToProductBean(detailedProduct));
//		}
//		return productBeans;
//	}
	
	private DetailedProductBean convertToDetailedProductBean(Product product)
	{
		Map<String,Integer> sizesAndQuantity = new HashMap<String, Integer>();
		for (ProductDetailed productDetailed : product.getDetailedProducts()) {
			sizesAndQuantity.put(productDetailed.getSize(), productDetailed.getUnitsInStock());
		}
		DetailedProductBean productBean = new DetailedProductBean();
		productBean.setCategoryId(product.getCategory().getId());
		productBean.setColor(product.getColor());
		productBean.setCreatedDate(product.getDateCreated());
		productBean.setDescription(product.getDescriptiion());
		productBean.setDisplayImage(product.getDisplayImage());
		productBean.setId(product.getId());
		productBean.setImageArray(product.getImageArray().split(","));
		productBean.setMrp(product.getMrp());
		productBean.setPrice(product.getListPrice());
		productBean.setRating(product.getRating());
		productBean.setSizesAndQuantity(sizesAndQuantity);
		productBean.setTitle(product.getTitle());
		productBean.setTags(product.getTags().split(","));
		
		
		return productBean;
		
	}
	
	
	
	private ProductBean convertToProductBean(Product product) {
		
		ProductBean productBean = new ProductBean();
		
		productBean.setCategoryId(product.getCategory().getId());
		productBean.setColor(product.getColor());
		productBean.setCreatedDate(product.getDateCreated());
		productBean.setDisplayImage(product.getDisplayImage());
		productBean.setId(product.getId());
		productBean.setPrice(product.getListPrice());
		productBean.setMrp(product.getMrp());
		productBean.setRating(product.getRating());
		productBean.setTitle(product.getTitle());	
		productBean.setUnitsSold(product.getUnitsSold());
		return productBean;
	}


}
