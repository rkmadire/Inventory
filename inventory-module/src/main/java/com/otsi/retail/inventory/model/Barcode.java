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

@Entity
@Table(name = "barcode")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Barcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long barcodeId;
	private String barcode;
	@ManyToOne
	@JoinColumn(name = "productItemId")
	private ProductItem productItem;
	private Long defaultCategoryId;
	private String attr1;
	private String attr2;
	private String attr3;
	private LocalDate creationDate;
	private LocalDate lastModified;
}
