package com.otsi.retail.inventory.vo;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

import com.otsi.retail.inventory.model.Store;

import lombok.Data;

@Data
public class ProductTextileVo {

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
	private StoresVo store;
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
