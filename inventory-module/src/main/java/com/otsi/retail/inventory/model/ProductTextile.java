package com.otsi.retail.inventory.model;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String barcode;
	private String attr_1;
	private String attr_2;
	private String attr_3;
	private String attr_4;
	private String attr_5;
	private String attr_6;
	private String attr_7;
	private String attr_8;
	private String attr_9;
	private String attr_10;
	private String attr_11;
	private String attr_12;
	private String attr_13;
	private String attr_14;
	private String attr_15;
	private String attr_16;
	private String attr_17;
	private String attr_18;
	private String attr_19;
	private String attr_20;
	private String parentBarcode;
	private float costPrice;
	private float itemMrp;
	private float itemRsp;
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
