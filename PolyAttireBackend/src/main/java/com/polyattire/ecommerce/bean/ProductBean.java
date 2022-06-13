package com.polyattire.ecommerce.bean;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ProductBean {	
	private Long id;
	private String title;
	private Long categoryId;
	private BigDecimal rating;
	private BigDecimal mrp;
	private BigDecimal price;
	private Date createdDate;
	private String color;
	private String displayImage;
	private Long unitsSold;
}
