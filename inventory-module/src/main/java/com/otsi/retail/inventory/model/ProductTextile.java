package com.otsi.retail.inventory.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_textile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTextile {
	@Id
	@GeneratedValue
	private Long productTextileId;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "barcodeTextileId")
	@JsonManagedReference
	private BarcodeTextile barcodeTextile;
	private String parentBarcode;
	private float costPrice;
	private float itemMrp;
	private float itemRsp;
	private Long empId;
	private int qty;
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Store store;
	private String promoLabel;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	private String uom;
	private int hsnMasterId;
	private String originalBarcode;
	private LocalDate originalBarcodeCreatedAt;
	private int createForLocation;
	private float valueAdditionCp;
	private String itemCode;
	private String itemSku;
	private String attr_21;
	private String attr_22;
	private String attr_23;
	private String attr_24;
	private String attr_25;

}
