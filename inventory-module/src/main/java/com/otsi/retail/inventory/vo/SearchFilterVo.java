package com.otsi.retail.inventory.vo;

import java.time.LocalDate;

import com.otsi.retail.inventory.commons.ProductStatus;

import lombok.Data;

@Data
public class SearchFilterVo {
	
	private LocalDate fromDate;
	
	private LocalDate toDate;
	
	private Long barcodeTextileId;
	
	private String empId;
	
	private float itemMrpLessThan;
	
	private float itemMrpGreaterThan;
	
	private Long storeId;
	
	private String barcode;
	
	private ProductStatus status;

}
