package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
import com.otsi.retail.inventory.model.ProductItem;
import lombok.Data;

@Data
public class ProductInventoryVo {

	private Long productInventoryId;

	private ProductItem productItem;

	private int stockvalue;

	private Long storeId;

	private String barcodeId;

	private String natureOfTransaction;

	private String effectingTable;

	private Long effectingTableID;

	private boolean masterFlag;

	private String comment;

	private LocalDate creationDate;

	private LocalDate lastModified;

}
