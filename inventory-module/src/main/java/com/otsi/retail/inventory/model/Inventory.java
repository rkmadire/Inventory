/**
 * 
 */
package com.otsi.retail.inventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vasavi
 *
 */
@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int inventoryId;

	private String Barcode;

	private String description;

	private int unitPrice;

	private int qtyInStock;
	
	private String uom;

	private int inventoryValue;

	private Boolean discontinued;

}
