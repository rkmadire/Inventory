package com.otsi.retail.inventory.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProductTransactionReVo {

	private Long productTransactionId;
	private Long storeId;
	private String barcodeId;
	private int quantity;
	private String natureOfTransaction;
	private String effectingTable;
	private Long effectingTableId;
	private boolean masterFlag;
	private String comment;
	private LocalDate creationDate;
	private LocalDate lastModified;

}
