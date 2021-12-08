package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import com.otsi.retail.inventory.commons.NatureOfTransaction;
import lombok.Data;

@Data
public class ProductTransactionVo {

	private Long productTransactionId;
	private Long storeId;
	private Long barcodeId;
	private int quantity;
	private String natureOfTransaction;
	private String effectingTable;
	private Long effectingTableID;
	private boolean masterFlag;
	private String comment;
	private LocalDate creationDate;
	private LocalDate lastModified;

}
