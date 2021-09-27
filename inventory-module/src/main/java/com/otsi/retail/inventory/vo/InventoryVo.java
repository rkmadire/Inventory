package com.otsi.retail.inventory.vo;

import lombok.Data;

@Data
public class InventoryVo {

	private int inventoryId;

	private String Barcode;

	private String description;

	private int unitPrice;

	private int qtyInStock;

	private int inventoryValue;

	private Boolean discontinued;

}
