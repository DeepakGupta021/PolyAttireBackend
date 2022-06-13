package com.polyattire.ecommerce.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class DetailedProductBean {
	
	private Long id;
	private String title;
	private String description;
	private Long categoryId;
	private BigDecimal rating;
	private BigDecimal mrp;
	private BigDecimal price;
	private Date createdDate;
	private String color;
	private String displayImage;
	private String[] imageArray;
	private Map<String,Integer> sizesAndQuantity;
	private String[] tags;
}
