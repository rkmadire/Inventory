package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "barcode")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Barcode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long barcodeId;
	private String barcode;
	
	@OneToMany(mappedBy = "barcode")
	@JsonManagedReference
	private List<ProductItem> productItem;
	private Long defaultCategoryId;
	private String attr1;
	private String attr2;
	private String attr3;
	private LocalDate creationDate;
	private LocalDate lastModified;
}
