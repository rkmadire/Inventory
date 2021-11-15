package com.otsi.retail.inventory.vo;

import java.time.LocalDate;
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

}
