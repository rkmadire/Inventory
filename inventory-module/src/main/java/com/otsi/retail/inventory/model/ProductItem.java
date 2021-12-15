/**
 * 
 */
package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vasavi
 *
 */
@Entity
@Table(name = "product_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true)
    private Long productItemId;
	
	@Column(unique=true)
    private String barcodeId;

	private String empId;

	private String tyecode;

	private String defaultImage;

	private int status;
	
	private String batchNo;

	private String title;
	
	private String hsnCode;

	private int stock;

	private String name;

	private float costPrice;

	private float listPrice;

	private String uom;
	
	private String discontinued;

	private Long storeId;

	
	private Long domainDataId;

	private Long puid;
	
	private String parentbarcode;

	private LocalDate creationDate;

	private LocalDate lastModifiedDate;

	@OneToMany(mappedBy = "productItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<ProductItemAv> productItemAvId;

	@OneToMany(mappedBy = "productItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<ProductImage> ProductImage;

	@OneToOne(mappedBy = "productItem")
	private ProductInventory productInventory;

}
