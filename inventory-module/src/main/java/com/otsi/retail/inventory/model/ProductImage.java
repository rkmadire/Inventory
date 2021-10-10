/**
 * 
 */
package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vasavi
 *
 */
@Entity
@Table(name = "productImage")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productImageId;
	@ManyToOne
	@JoinColumn(name = "puid")
	private ProductItem productItem;
	private String image; 
	private Long pIUID;
	private LocalDate creationDate;
	private LocalDate lastModified;

}
