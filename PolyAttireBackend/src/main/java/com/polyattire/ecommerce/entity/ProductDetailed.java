package com.polyattire.ecommerce.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_detailed")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class ProductDetailed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	@JsonIgnoreProperties("detailedProducts")
	private Product product;
	
	@Column(name = "unit_price")
	private BigDecimal unitPrice;
	
	
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "units_in_stock")
	private int unitsInStock;
	
	@Column(name = "size")
	private String size;
	

}
