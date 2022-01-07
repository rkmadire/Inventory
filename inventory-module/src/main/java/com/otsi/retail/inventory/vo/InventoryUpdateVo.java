package com.otsi.retail.inventory.vo;

import lombok.Data;

@Data
public class InventoryUpdateVo {

	private int quantity;

	private Long lineItemId;

	private String barCode;
	
	private Long storeId;

}

