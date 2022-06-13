package com.polyattire.ecommerce.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "sku")
	private String sku;
	
	@Column(name = "description")
	private String descriptiion;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "MRP")
	private BigDecimal mrp;
	
	@Column(name = "list_price")
	private BigDecimal listPrice;
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	@JsonIgnoreProperties("products")
	private ProductCategory category;
	
	@Column(name = "units_sold")
	private Long unitsSold;
	
	@Column(name = "rating")
	private BigDecimal rating;
	
	@CreationTimestamp
	@Column(name = "date_created")
	private Date dateCreated;
	
	@UpdateTimestamp
	@Column(name = "last_updated")
	private Date lastUpdated;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "display_image")
	private String displayImage;
	
	@Column(name = "image_array")
	private String imageArray;
	
	@Column(name = "tags")
	private String tags;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
	@JsonIgnoreProperties("product")
	private Set<ProductDetailed> detailedProducts;
}
