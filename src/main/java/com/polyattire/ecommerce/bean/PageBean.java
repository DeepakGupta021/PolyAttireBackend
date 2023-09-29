package com.polyattire.ecommerce.bean;

import lombok.Data;

@Data
public class PageBean {	
	private int size;
	private Long totalElements;
	private int totalPages;
	private int number;
}
