package com.otsi.retail.inventory.vo;

import java.time.LocalDate;

import com.otsi.retail.inventory.model.ProductTextile;

import lombok.Data;

@Data
public class BarcodeTextileVo {

	private Long barcodeTextileId;
	private String barcode;
	private String division;
	private String section;
	private String subSection;
	private String category;
	private String batchNo;
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
	private LocalDate fromDate;
	private LocalDate toDate;
	
   private ProductTextileVo productTextile;
}
