package com.polyattire.ecommerce.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcomUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	private String name;
	private String email;
	private String password;
	@ManyToAny(fetch = FetchType.EAGER, metaColumn = @Column)
	private Collection<EcomRole> roles = new ArrayList<EcomRole>();
	
}
