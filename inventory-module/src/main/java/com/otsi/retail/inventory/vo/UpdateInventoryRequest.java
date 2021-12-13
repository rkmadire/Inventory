package com.otsi.retail.inventory.vo;

import lombok.Data;

@Data
public class UpdateInventoryRequest {
	
	private String barcode;
	
	private int qty;
	
	private Long lineItemId;
	
	private Long storeId;

}
