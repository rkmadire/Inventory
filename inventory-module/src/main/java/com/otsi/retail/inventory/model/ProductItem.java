/**
 * 
 */
package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vasavi
 *
 */
@Entity
@Table(name = "product_item")
@Data@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productItemId;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Barcode barcode;

	private String tyecode;

	private String defaultImage;

	private int status;

	private String title;

	private int stock;
	
	private String name;

	private float costPrice;

	private float listPrice;

	private String uom;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Store store;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Domaindata domainData;

	private Long puid;

	private LocalDate creationDate;

	private LocalDate lastModifiedDate;

	@OneToMany(mappedBy = "productItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductItemAv> productItemAvId;

	@OneToMany(mappedBy = "productItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductImage> ProductImage;

	@OneToOne(mappedBy = "productItem")
	@JoinColumn(name="productInventoryId")
	private ProductInventory productInventory;

}
