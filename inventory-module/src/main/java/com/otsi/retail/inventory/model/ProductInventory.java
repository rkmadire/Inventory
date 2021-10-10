/**
 * entity for productInventory
 */
package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "productInventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventory {

	@Id
	@GeneratedValue
	private Long productInventoryId;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "productItemId")
	private ProductItem productItem;
	
	private int stockvalue;
	
	private LocalDate creationDate;
	
	private LocalDate lastModified;

}
